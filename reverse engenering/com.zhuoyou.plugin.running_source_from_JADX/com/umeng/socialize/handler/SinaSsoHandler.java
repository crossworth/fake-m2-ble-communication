package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.PlatformConfig.SinaWeibo;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.net.PlatformTokenUploadReq;
import com.umeng.socialize.net.PlatformTokenUploadResponse;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SinaSsoHandler extends UMSSOHandler {
    private static final int REQUEST_CODE = 5659;
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";
    private static final String TAG = "SinaSsoHandler";
    private SinaWeibo config = null;
    private AuthInfo mAuthInfo;
    private AuthListener mAuthListener;
    private Context mContext;
    private SsoHandler mSsoHandler;
    private IWeiboShareAPI mWeiboShareAPI;
    private UMShareListener shareListener;
    private SinaPreferences sinaPreferences;

    class AuthListener implements WeiboAuthListener {
        private UMAuthListener mListener = null;

        AuthListener(UMAuthListener listener) {
            this.mListener = listener;
        }

        public void onComplete(Bundle values) {
            SinaSsoHandler.this.sinaPreferences.setAuthData(values).commit();
            SinaSsoHandler.this.uploadAuthData(values);
            if (this.mListener != null) {
                this.mListener.onComplete(SHARE_MEDIA.SINA, 0, SocializeUtils.bundleTomap(values));
            }
        }

        public void onCancel() {
            if (this.mListener != null) {
                this.mListener.onCancel(SHARE_MEDIA.SINA, 0);
            }
        }

        public void onWeiboException(WeiboException e) {
            if (this.mListener != null) {
                this.mListener.onError(SHARE_MEDIA.SINA, 0, new Throwable(e));
            }
        }
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.mContext = context.getApplicationContext();
        this.config = (SinaWeibo) p;
        this.sinaPreferences = new SinaPreferences(this.mContext, "sina");
        this.mAuthInfo = new AuthInfo(context, ((SinaWeibo) p).appKey, Config.REDIRECT_URL, SCOPE);
        if (context instanceof Activity) {
            this.mSsoHandler = new SsoHandler((Activity) context, this.mAuthInfo);
            this.mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context.getApplicationContext(), this.config.appKey);
            this.mWeiboShareAPI.registerApp();
            Log.m4549e(TAG, "handleid=" + this);
        }
    }

    public IWeiboShareAPI getmWeiboShareAPI() {
        return this.mWeiboShareAPI;
    }

    public boolean isInstall() {
        return isClientInstalled();
    }

    public boolean isAuthorize() {
        return this.sinaPreferences.isAuthorized();
    }

    public boolean isClientInstalled() {
        return this.mWeiboShareAPI.isWeiboAppInstalled();
    }

    public boolean isSupport() {
        return this.mWeiboShareAPI.isWeiboAppSupportAPI();
    }

    public String getSDKVersion() {
        return "3.1.4";
    }

    public void authorize(UMAuthListener listener) {
        this.mAuthListener = new AuthListener(listener);
        this.mSsoHandler.authorize(this.mAuthListener);
    }

    protected void requestAsync(String url, WeiboParameters params, String httpMethod, RequestListener listener, String mAccessToken) {
        if (mAccessToken == null || TextUtils.isEmpty(url) || params == null || TextUtils.isEmpty(httpMethod) || listener == null) {
            LogUtil.m3308e(TAG, "Argument error!");
            return;
        }
        params.put("access_token", mAccessToken);
        new AsyncWeiboRunner(this.mContext).requestAsync(url, params, httpMethod, listener);
    }

    public void getPlatformInfo(final UMAuthListener listener) {
        if (this.sinaPreferences.getUID() != null) {
            WeiboParameters params = new WeiboParameters(this.config.appKey);
            params.put("uid", this.sinaPreferences.getUID());
            requestAsync("https://api.weibo.com/2/users/show.json", params, Constants.HTTP_GET, new RequestListener() {
                public void onComplete(String s) {
                    HashMap hashMap = new HashMap();
                    Map<String, String> map = SocializeUtils.jsonToMap(s);
                    if (SinaSsoHandler.this.sinaPreferences != null) {
                        map.put("uid", SinaSsoHandler.this.sinaPreferences.getUID());
                        map.put("access_token", SinaSsoHandler.this.sinaPreferences.getmAccessToken());
                        map.put("refresh_token", SinaSsoHandler.this.sinaPreferences.getmRefreshToken());
                        map.put("expires_in", String.valueOf(SinaSsoHandler.this.sinaPreferences.getmTTL()));
                    }
                    listener.onComplete(SHARE_MEDIA.SINA, 2, map);
                }

                public void onWeiboException(WeiboException e) {
                    listener.onError(SHARE_MEDIA.SINA, 2, new Throwable(e));
                }
            }, this.sinaPreferences.getmAccessToken());
            return;
        }
        authorize(new UMAuthListener() {

            class C16051 implements Runnable {
                C16051() {
                }

                public void run() {
                    SinaSsoHandler.this.getPlatformInfo(listener);
                }
            }

            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
                QueuedWork.runInBack(new C16051());
            }

            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            }

            public void onCancel(SHARE_MEDIA platform, int action) {
            }
        });
    }

    public void deleteAuth(UMAuthListener listener) {
        this.sinaPreferences.delete();
        listener.onComplete(SHARE_MEDIA.SINA, 1, null);
    }

    public boolean share(ShareContent content, final UMShareListener listener) {
        SinaShareContent mSharecontent = new SinaShareContent(content);
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = mSharecontent.getMessage();
        AuthInfo authInfo = new AuthInfo(getContext(), this.config.appKey, Config.REDIRECT_URL, SCOPE);
        String token = "";
        if (this.sinaPreferences != null) {
            token = this.sinaPreferences.getmAccessToken();
        }
        this.shareListener = listener;
        if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
            this.mWeiboShareAPI.sendRequest((Activity) this.mWeakAct.get(), request, authInfo, token, new WeiboAuthListener() {
                public void onWeiboException(WeiboException arg0) {
                    Log.m4546d("sina_share", "weibo share exception");
                    if (listener != null) {
                        listener.onError(SHARE_MEDIA.SINA, new Throwable(arg0));
                    }
                }

                public void onComplete(Bundle bundle) {
                    SinaSsoHandler.this.uploadAuthData(bundle);
                    if (listener != null) {
                        listener.onResult(SHARE_MEDIA.SINA);
                    }
                    if (bundle != null) {
                        Log.m4549e("sinashare", "msg = " + bundle.getString("msg"));
                    }
                    SinaSsoHandler.this.sinaPreferences.setAuthData(bundle).commit();
                }

                public void onCancel() {
                    Log.m4546d("sina_share", "weibo share cancel");
                    if (listener != null) {
                        listener.onCancel(SHARE_MEDIA.SINA);
                    }
                }
            });
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        this.mSsoHandler = null;
    }

    public boolean isSupportAuth() {
        return true;
    }

    public int getRequestCode() {
        return 5659;
    }

    public void setScope(String[] permissions) {
    }

    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case 0:
                Log.m4546d("sina_share", "weibo share error ok");
                if (isClientInstalled()) {
                    this.shareListener.onResult(SHARE_MEDIA.SINA);
                    return;
                }
                return;
            case 1:
                Log.m4546d("sina_share", "weibo share cancel");
                this.shareListener.onCancel(SHARE_MEDIA.SINA);
                return;
            case 2:
                Log.m4546d("sina_share", "weibo share fail");
                this.shareListener.onError(SHARE_MEDIA.SINA, new Throwable(baseResponse.errMsg));
                return;
            default:
                return;
        }
    }

    private Map<String, String> bundleTomap(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return null;
        }
        Set<String> keys = bundle.keySet();
        Map<String, String> map = new HashMap();
        for (String key : keys) {
            Log.m4548e("authxx key=" + key + "   value=" + bundle.getString(key));
            map.put(key, bundle.getString(key));
        }
        return map;
    }

    private void uploadAuthData(final Bundle bundle) throws SocializeException {
        new Thread(new Runnable() {
            public void run() {
                PlatformTokenUploadReq req = new PlatformTokenUploadReq(SinaSsoHandler.this.getContext());
                req.addStringParams("to", "sina");
                req.addStringParams("usid", bundle.getString("uid"));
                req.addStringParams("access_token", bundle.getString("access_token"));
                req.addStringParams("refresh_token", bundle.getString("refresh_token"));
                req.addStringParams("expires_in", bundle.getString("expires_in"));
                PlatformTokenUploadResponse resp = RestAPI.uploadPlatformToken(req);
            }
        }).start();
    }
}
