package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.umeng.facebook.AccessToken;
import com.umeng.facebook.CallbackManager;
import com.umeng.facebook.CallbackManager.Factory;
import com.umeng.facebook.FacebookCallback;
import com.umeng.facebook.FacebookException;
import com.umeng.facebook.FacebookSdk;
import com.umeng.facebook.Profile;
import com.umeng.facebook.login.DefaultAudience;
import com.umeng.facebook.login.LoginBehavior;
import com.umeng.facebook.login.LoginManager;
import com.umeng.facebook.login.LoginResult;
import com.umeng.facebook.media.FacebookShareContent;
import com.umeng.facebook.share.Sharer.Result;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.facebook.share.model.ShareLinkContent;
import com.umeng.facebook.share.model.SharePhoto;
import com.umeng.facebook.share.model.SharePhotoContent;
import com.umeng.facebook.share.model.SharePhotoContent.Builder;
import com.umeng.facebook.share.model.ShareVideo;
import com.umeng.facebook.share.model.ShareVideoContent;
import com.umeng.facebook.share.widget.ShareDialog;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.net.PlatformTokenUploadReq;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UMFacebookHandler extends UMSSOHandler {
    private String PackageName = "com.facebook.katana";
    private Platform config;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.SSO_WITH_FALLBACK;
    private LoginManager loginManager;
    private CallbackManager mCallbackManager;
    private List<String> permissions = Collections.emptyList();
    private ShareDialog shareDialog;

    public void onCreate(Context context, Platform config) {
        super.onCreate(context, config);
        this.config = config;
        FacebookSdk.sdkInitialize(getContext());
        this.mCallbackManager = Factory.create();
    }

    public boolean isAuthorize() {
        Log.m4548e("facebook can`t support isAuthorize");
        return false;
    }

    public void authorize(final UMAuthListener listener) {
        LoginManager loginManager = getLoginManager();
        loginManager.registerCallback(this.mCallbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(LoginResult result) {
                Map<String, String> value = UMFacebookHandler.this.parseAuthData(result);
                Log.m4546d("", "授权FB成功 : ");
                UMFacebookHandler.this.uploadAuthData(value);
                Profile.fetchProfileForCurrentAccessToken();
                listener.onComplete(SHARE_MEDIA.FACEBOOK, 0, value);
            }

            public void onError(FacebookException error) {
                Log.m4546d("", "授权FB出错，错误信息 : " + error.getMessage());
                listener.onError(SHARE_MEDIA.FACEBOOK, 0, error);
            }

            public void onCancel() {
                Log.m4546d("", "### 取消FB授权");
                listener.onCancel(SHARE_MEDIA.FACEBOOK, 0);
            }
        });
        if (AccessToken.getCurrentAccessToken() != null) {
            loginManager.logOut();
        }
        if (this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
            loginManager.logInWithReadPermissions((Activity) this.mWeakAct.get(), Arrays.asList(new String[]{"public_profile", "user_friends"}));
        }
    }

    private void parseUserInfoAndCallback(Profile profile, UMAuthListener listener) {
        Map<String, String> values = new HashMap();
        values.put(ShareConstants.WEB_DIALOG_PARAM_ID, profile.getId());
        values.put("first_name", profile.getFirstName());
        values.put("last_name", profile.getLastName());
        values.put("linkUri", profile.getLinkUri().toString());
        values.put("profilePictureUri", profile.getProfilePictureUri(200, 200).toString());
        values.put("middle_name", profile.getMiddleName());
        values.put("name", profile.getName());
        listener.onComplete(SHARE_MEDIA.FACEBOOK, 2, values);
    }

    private void uploadAuthData(final Map<String, String> bundle) throws SocializeException {
        new Thread(new Runnable() {
            public void run() {
                PlatformTokenUploadReq req = new PlatformTokenUploadReq(UMFacebookHandler.this.getContext());
                req.addStringParams("to", "facebook");
                req.addStringParams("usid", (String) bundle.get("uid"));
                req.addStringParams("access_token", (String) bundle.get("access_token"));
                req.addStringParams("refresh_token", (String) bundle.get("refresh_token"));
                req.addStringParams("expires_in", (String) bundle.get("expires_in"));
                Log.m4548e("upload token resp = " + RestAPI.uploadPlatformToken(req));
            }
        }).start();
    }

    public void getPlatformInfo(final UMAuthListener listener) {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            parseUserInfoAndCallback(profile, listener);
        } else {
            authorize(new UMAuthListener() {
                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                    if (TextUtils.isEmpty((String) data.get("uid")) || Profile.getCurrentProfile() == null) {
                        Log.m4546d("", "###oauth failed...");
                    } else {
                        UMFacebookHandler.this.parseUserInfoAndCallback(Profile.getCurrentProfile(), listener);
                    }
                }

                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    Log.m4546d("", "###oauth failed , message :" + t.getMessage());
                }

                public void onCancel(SHARE_MEDIA platform, int action) {
                }
            });
        }
    }

    public int getRequestCode() {
        return HandlerRequestCode.FACEBOOK_REQUEST_AUTH_CODE;
    }

    public void deleteAuth(UMAuthListener listener) {
        this.loginManager = getLoginManager();
        this.loginManager.logOut();
        listener.onComplete(SHARE_MEDIA.FACEBOOK, 1, null);
    }

    private void doLogin() {
    }

    public boolean isSupportAuth() {
        return true;
    }

    public boolean isInstall() {
        if (DeviceConfig.isAppInstalled(this.PackageName, getContext())) {
            return true;
        }
        return false;
    }

    private boolean isInstall(Platform p) {
        if (DeviceConfig.isAppInstalled(this.PackageName, getContext())) {
            return true;
        }
        StringBuilder msb = new StringBuilder();
        msb.append("请安装");
        msb.append(ResContainer.getString(getContext(), p.getName().toSnsPlatform().mShowWord));
        msb.append("客户端");
        Log.m4554v(msb.toString());
        if (Config.IsToastTip) {
            Toast.makeText(getContext(), msb, 1).show();
        }
        return false;
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        if (isInstall(getConfig())) {
            return shareTo(new FacebookShareContent(content), listener);
        }
        listener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    public boolean shareTo(FacebookShareContent shareContent, UMShareListener listener) {
        if (!Config.isIntentShareFB) {
            final UMShareListener uMShareListener;
            switch (shareContent.getShare_Type()) {
                case 1:
                    Log.m4546d("fb", "facebook share picture");
                    if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                        ShareDialog shareDialog1 = new ShareDialog((Activity) this.mWeakAct.get());
                        SharePhotoContent sharePhotoContent = new Builder().addPhoto(new SharePhoto.Builder().setBitmap(shareContent.getImage().asBitmap()).setCaption(shareContent.getText()).build()).build();
                        uMShareListener = listener;
                        shareDialog1.registerCallback(this.mCallbackManager, new FacebookCallback<Result>() {
                            public void onSuccess(Result result) {
                                Log.m4546d("xxxxxx", "share success=" + result.getPostId());
                                uMShareListener.onResult(SHARE_MEDIA.FACEBOOK);
                            }

                            public void onCancel() {
                                Log.m4546d("xxxxxx", "share cancel");
                                uMShareListener.onCancel(SHARE_MEDIA.FACEBOOK);
                            }

                            public void onError(FacebookException error) {
                                Log.m4546d("xxxxxx", "share error=" + error.getMessage());
                                uMShareListener.onError(SHARE_MEDIA.FACEBOOK, error);
                            }
                        });
                        shareDialog1.show(sharePhotoContent);
                        break;
                    }
                case 2:
                    if (Uri.fromFile(new File(shareContent.getVideo().toUrl())).toString().startsWith("file://")) {
                        Log.m4546d("fb uri", "uri is local uri");
                    } else {
                        Log.m4549e("fb uri", "uri is not local uri, must pass in local uri");
                    }
                    ShareVideoContent shareVideoContent = new ShareVideoContent.Builder().setVideo(new ShareVideo.Builder().setLocalUrl(Uri.fromFile(new File(shareContent.getVideo().toUrl()))).build()).setContentTitle(shareContent.getTitle()).setContentDescription(shareContent.getText()).build();
                    if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                        this.shareDialog = new ShareDialog((Activity) this.mWeakAct.get());
                        uMShareListener = listener;
                        this.shareDialog.registerCallback(this.mCallbackManager, new FacebookCallback<Result>() {
                            public void onSuccess(Result result) {
                                Log.m4546d("xxxxxx", "share success");
                                uMShareListener.onResult(SHARE_MEDIA.FACEBOOK);
                            }

                            public void onCancel() {
                                Log.m4546d("xxxxxx", "share cancel");
                                uMShareListener.onCancel(SHARE_MEDIA.FACEBOOK);
                            }

                            public void onError(FacebookException error) {
                                Log.m4546d("facebook", "share video:" + error.getMessage());
                                uMShareListener.onError(SHARE_MEDIA.FACEBOOK, new Throwable(error.getMessage()));
                            }
                        });
                        com.umeng.facebook.share.model.ShareContent content = shareContent.getContent();
                        if (content != null && ShareDialog.canShow(content.getClass())) {
                            this.shareDialog.show(shareVideoContent);
                            break;
                        }
                        Log.m4549e("", "###分享失败，具体参考Log跟线上文档FB支持的分享类型...");
                        break;
                    }
                    break;
                case 3:
                    ShareLinkContent shareLinkContent;
                    Log.m4546d("share link", "share link");
                    Log.m4548e("xxxxxx sss");
                    if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                        this.shareDialog = new ShareDialog((Activity) this.mWeakAct.get());
                        Log.m4548e("xxxxxx 2222");
                    }
                    if (shareContent.getImage() == null || !shareContent.getImage().isUrlMedia()) {
                        shareLinkContent = ((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentUrl(Uri.parse(shareContent.getTargeturl()))).setContentTitle(shareContent.getTitle()).setContentDescription(shareContent.getText()).build();
                    } else {
                        shareLinkContent = ((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentUrl(Uri.parse(shareContent.getTargeturl()))).setContentTitle(shareContent.getTitle()).setContentDescription(shareContent.getText()).setImageUrl(Uri.parse(shareContent.getImage().asUrlImage())).build();
                    }
                    uMShareListener = listener;
                    this.shareDialog.registerCallback(this.mCallbackManager, new FacebookCallback<Result>() {
                        public void onSuccess(Result result) {
                            Log.m4546d("xxxxxx", "share success");
                            uMShareListener.onResult(SHARE_MEDIA.FACEBOOK);
                        }

                        public void onCancel() {
                            Log.m4546d("xxxxxx", "share cancel");
                            uMShareListener.onCancel(SHARE_MEDIA.FACEBOOK);
                        }

                        public void onError(FacebookException error) {
                            Log.m4546d("xxxxxx", "share error=" + error.getMessage());
                            uMShareListener.onError(SHARE_MEDIA.FACEBOOK, error);
                        }
                    });
                    this.shareDialog.show(shareLinkContent);
                    Log.m4548e("xxxxxx 3333");
                    break;
                default:
                    listener.onError(SHARE_MEDIA.FACEBOOK, new Throwable("分享类型错误，facebook只支持图片 链接，视频"));
                    break;
            }
        }
        Intent shareIntent = new Intent("android.intent.action.SEND");
        if (shareContent.getImage() != null) {
            shareIntent.setType("image/*");
            shareIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(shareContent.getImage().asFileImage()));
        } else if (shareContent.getVideo() != null) {
            shareIntent.setType("video/*");
            shareIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(shareContent.getVideo().toUrl())));
        }
        for (ResolveInfo app : getContext().getPackageManager().queryIntentActivities(shareIntent, 0)) {
            if (app.activityInfo.name.contains("facebook")) {
                ActivityInfo activity1 = app.activityInfo;
                ComponentName name = new ComponentName(activity1.applicationInfo.packageName, activity1.name);
                shareIntent.addCategory("android.intent.category.LAUNCHER");
                shareIntent.setFlags(270532608);
                shareIntent.setComponent(name);
                if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                    ((Activity) this.mWeakAct.get()).startActivity(shareIntent);
                }
            }
        }
        return true;
    }

    public boolean isClientInstalled() {
        return DeviceConfig.isAppInstalled("com.facebook.katana", getContext());
    }

    private Map<String, String> parseAuthData(LoginResult result) {
        Map<String, String> map = new HashMap();
        AccessToken accessToken = result.getAccessToken();
        if (accessToken != null) {
            map.put("uid", accessToken.getUserId());
            map.put("access_token", accessToken.getToken());
            map.put("expires_in", String.valueOf(accessToken.getExpires().getTime() - System.currentTimeMillis()));
        }
        return map;
    }

    LoginManager getLoginManager() {
        if (this.loginManager == null) {
            this.loginManager = LoginManager.getInstance();
        }
        return this.loginManager;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private static Intent getMsgIntent(Context context, String shareContent, UMImage img) {
        Intent sendIntent = new Intent("android.intent.action.SEND");
        sendIntent.setType("image/*;text/plain");
        if (!TextUtils.isEmpty(shareContent)) {
            sendIntent.putExtra("android.intent.extra.TEXT", shareContent);
            sendIntent.putExtra("android.intent.extra.SUBJECT", shareContent);
        }
        if (img != null) {
            sendIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(img.asFileImage()));
        }
        return sendIntent;
    }
}
