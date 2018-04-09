package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.net.PlatformTokenUploadReq;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class UMQQSsoHandler extends UMTencentSSOHandler {
    private static final String TAG = "UMQQSsoHandler";
    private Bundle mParams;
    private IUiListener mShareListener;
    private QQShareContent msharecontent;
    private QQPreferences qqPreferences;

    class C16284 implements Runnable {
        C16284() {
        }

        public void run() {
            if (UMQQSsoHandler.this.mWeakAct.get() != null && !((Activity) UMQQSsoHandler.this.mWeakAct.get()).isFinishing()) {
                UMQQSsoHandler.this.mTencent.shareToQQ((Activity) UMQQSsoHandler.this.mWeakAct.get(), UMQQSsoHandler.this.mParams, UMQQSsoHandler.this.mShareListener);
            }
        }
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        if (!isInstall()) {
            StringBuilder msb = new StringBuilder();
            msb.append("请安装");
            msb.append(Constants.SOURCE_QQ);
            msb.append("客户端");
            if (Config.IsToastTip) {
                Toast.makeText(context.getApplicationContext(), msb.toString(), 0).show();
            }
        }
        if (context != null) {
            this.qqPreferences = new QQPreferences(getContext(), SHARE_MEDIA.QQ.toString());
        }
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        this.mParams = null;
        this.mShareListener = getSharelistener(listener);
        if (this.mShareListener == null) {
            Log.m4546d("listen", "listener is null");
        }
        if (!isInstall()) {
            listener.onError(SHARE_MEDIA.QQ, new Throwable("qq not install"));
        }
        this.msharecontent = new QQShareContent(content);
        shareToQQ();
        return false;
    }

    private IUiListener getSharelistener(final UMShareListener listener) {
        return new IUiListener() {
            public void onError(UiError e) {
                if (e != null) {
                    listener.onError(SHARE_MEDIA.QQ, new Throwable(e.errorMessage));
                } else {
                    listener.onError(SHARE_MEDIA.QQ, new Throwable(e.errorMessage));
                }
            }

            public void onCancel() {
                listener.onCancel(SHARE_MEDIA.QQ);
            }

            public void onComplete(Object response) {
                listener.onResult(SHARE_MEDIA.QQ);
            }
        };
    }

    public boolean isAuthorize() {
        if (this.qqPreferences != null) {
            return this.qqPreferences.isAuthValid();
        }
        return false;
    }

    public void authorize(UMAuthListener listener) {
        this.mAuthListener = listener;
        loginDeal();
    }

    public void setAuthListener(UMAuthListener listener) {
        this.mAuthListenerBackup = listener;
    }

    private IUiListener getAuthlistener(final UMAuthListener listener) {
        return new IUiListener() {
            public void onError(UiError e) {
                if (e != null) {
                    Log.m4546d(UMQQSsoHandler.TAG, "授权失败! ==> errorCode = " + e.errorCode + ", errorMsg = " + e.errorMessage + ", detail = " + e.errorDetail);
                }
                listener.onError(SHARE_MEDIA.QQ, 0, new Throwable("授权失败! ==> errorCode = " + e.errorCode + ", errorMsg = " + e.errorMessage + ", detail = " + e.errorDetail));
                UMQQSsoHandler.this.releaseRefernce();
            }

            public void onCancel() {
                if (listener != null) {
                    listener.onCancel(SHARE_MEDIA.QQ, 0);
                }
                UMQQSsoHandler.this.releaseRefernce();
            }

            public void onComplete(Object response) {
                SocializeUtils.safeCloseDialog(UMQQSsoHandler.this.mProgressDialog);
                Bundle values = UMQQSsoHandler.this.parseOauthData(response);
                if (UMQQSsoHandler.this.qqPreferences == null && UMQQSsoHandler.this.getContext() != null) {
                    UMQQSsoHandler.this.qqPreferences = new QQPreferences(UMQQSsoHandler.this.getContext(), SHARE_MEDIA.QQ.toString());
                }
                if (UMQQSsoHandler.this.qqPreferences != null) {
                    UMQQSsoHandler.this.qqPreferences.setAuthData(values).commit();
                }
                UMQQSsoHandler.this.initOpenidAndToken((JSONObject) response);
                if (listener != null) {
                    listener.onComplete(SHARE_MEDIA.QQ, 0, SocializeUtils.bundleTomap(values));
                }
                UMQQSsoHandler.this.uploadAuthData(values);
                UMQQSsoHandler.this.releaseRefernce();
                if (values != null && Integer.valueOf(values.getString("ret")).intValue() != 0) {
                }
            }
        };
    }

    private void releaseRefernce() {
        this.mAuthListenerBackup = null;
    }

    public boolean isInstall() {
        if (this.mTencent.isSupportSSOLogin((Activity) this.mWeakAct.get())) {
            return true;
        }
        return false;
    }

    public String getSDKVersion() {
        return Constants.SDK_VERSION;
    }

    public boolean isSupport() {
        return this.mTencent.isSupportSSOLogin((Activity) this.mWeakAct.get());
    }

    public void deleteAuth(UMAuthListener listener) {
        this.mTencent.logout(getContext());
        if (this.qqPreferences != null) {
            this.qqPreferences.delete();
        }
        listener.onComplete(SHARE_MEDIA.QQ, 1, null);
    }

    public boolean isSupportAuth() {
        return true;
    }

    private void loginDeal() {
        Log.m4552i(TAG, "QQ oauth login...");
        if (isInstall()) {
            Log.m4546d("qq", "installed qq");
            if (this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
                this.mTencent.login((Activity) this.mWeakAct.get(), "all", getAuthlistener(this.mAuthListener));
                return;
            }
            return;
        }
        this.mAuthListener.onError(SHARE_MEDIA.QQ, 0, new Throwable("qq not install"));
    }

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");
            String openId = jsonObject.getString("openid");
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                this.mTencent.setAccessToken(token, expires);
                this.mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private void uploadAuthData(final Bundle bundle) throws SocializeException {
        new Thread(new Runnable() {
            public void run() {
                if (UMQQSsoHandler.this.getContext() != null && bundle != null && UMQQSsoHandler.this.config != null) {
                    PlatformTokenUploadReq req = new PlatformTokenUploadReq(UMQQSsoHandler.this.getContext());
                    req.addStringParams("to", "qq");
                    req.addStringParams("usid", bundle.getString("uid"));
                    req.addStringParams("access_token", bundle.getString("access_token"));
                    req.addStringParams("refresh_token", bundle.getString("refresh_token"));
                    req.addStringParams("expires_in", bundle.getString("expires_in"));
                    Log.m4548e("upload token resp = " + RestAPI.uploadPlatformToken(req));
                }
            }
        }).start();
    }

    public void shareToQQ() {
        if (validTencent()) {
            defaultShareToQQ();
            return;
        }
        Log.m4546d(TAG, "QQ平台还没有授权");
        authorize(null);
    }

    private void defaultShareToQQ() {
        this.mParams = this.msharecontent.buildParams();
        this.mParams.putString("appName", getAppName());
        if (this.mParams == null) {
            return;
        }
        if (this.mParams.getString("error") != null) {
            this.mShareListener.onError(new UiError(0, this.mParams.getString("error"), this.mParams.getString("error")));
        } else {
            QueuedWork.runInMain(new C16284());
        }
    }

    public int getRequestCode() {
        return 10103;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10103) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this.mShareListener);
        }
        if (requestCode == 11101) {
            if (!(this.mAuthListener != null || this.mAuthListenerBackup == null || this.mTencent == null)) {
                Tencent tencent = this.mTencent;
                Tencent.handleResultData(data, getAuthlistener(this.mAuthListenerBackup));
            }
            Tencent.onActivityResultData(requestCode, resultCode, data, getAuthlistener(this.mAuthListener));
        }
    }

    public void getPlatformInfo(final UMAuthListener listener) {
        if (TextUtils.isEmpty(this.qqPreferences.getmAccessToken())) {
            authorize(new UMAuthListener() {
                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
                    UMQQSsoHandler.this.getPlatformInfo(listener);
                }

                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    listener.onError(SHARE_MEDIA.QQ, 2, t);
                }

                public void onCancel(SHARE_MEDIA platform, int action) {
                    listener.onCancel(SHARE_MEDIA.QQ, 2);
                }
            });
            return;
        }
        try {
            String token = this.qqPreferences.getmAccessToken();
            QQPreferences qQPreferences = this.qqPreferences;
            String expires = QQPreferences.getExpiresIn();
            String openId = this.qqPreferences.getmUID();
            if (this.qqPreferences != null) {
                token = this.qqPreferences.getmAccessToken();
                qQPreferences = this.qqPreferences;
                expires = QQPreferences.getExpiresIn();
                openId = this.qqPreferences.getmUID();
            }
            if (!(TextUtils.isEmpty(token) || TextUtils.isEmpty(expires) || TextUtils.isEmpty(openId))) {
                this.mTencent.setAccessToken(token, expires);
                this.mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
        new UserInfo(getContext(), this.mTencent.getQQToken()).getUserInfo(new IUiListener() {
            public void onCancel() {
            }

            public void onComplete(Object arg) {
                try {
                    JSONObject jsonObject = new JSONObject(arg.toString());
                    Map<String, String> infos = new HashMap();
                    infos.put("screen_name", jsonObject.optString("nickname"));
                    infos.put(SocializeProtocolConstants.PROTOCOL_KEY_GENDER, jsonObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_GENDER));
                    infos.put(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_ICON, jsonObject.optString("figureurl_qq_2"));
                    infos.put("is_yellow_year_vip", jsonObject.optString("is_yellow_year_vip"));
                    infos.put("yellow_vip_level", jsonObject.optString("yellow_vip_level"));
                    infos.put("msg", jsonObject.optString("msg"));
                    infos.put("city", jsonObject.optString("city"));
                    infos.put("vip", jsonObject.optString("vip"));
                    infos.put("level", jsonObject.optString("level"));
                    infos.put("province", jsonObject.optString("province"));
                    infos.put("is_yellow_vip", jsonObject.optString("is_yellow_vip"));
                    if (UMQQSsoHandler.this.qqPreferences != null) {
                        infos.put("openid", UMQQSsoHandler.this.qqPreferences.getuid());
                        infos.put("uid", UMQQSsoHandler.this.qqPreferences.getuid());
                        infos.put("access_token", UMQQSsoHandler.this.qqPreferences.getmAccessToken());
                        infos.put("expires_in", UMQQSsoHandler.this.qqPreferences.getMtl());
                    }
                    listener.onComplete(SHARE_MEDIA.QQ, 2, infos);
                } catch (JSONException e) {
                    listener.onError(SHARE_MEDIA.QQ, 2, new Throwable("json error"));
                }
            }

            public void onError(UiError arg0) {
                listener.onError(SHARE_MEDIA.QQ, 2, new Throwable(arg0.toString()));
            }
        });
    }
}
