package com.sina.weibo.sdk.auth;

import android.content.Context;
import android.os.Bundle;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.NetworkHelper;
import com.sina.weibo.sdk.utils.ResourceManager;
import com.sina.weibo.sdk.utils.UIUtils;
import com.sina.weibo.sdk.utils.Utility;

public class WeiboAuth {
    private static final String OAUTH2_BASE_URL = "https://open.weibo.cn/oauth2/authorize?";
    public static final int OBTAIN_AUTH_CODE = 0;
    public static final int OBTAIN_AUTH_TOKEN = 1;
    public static final String TAG = "Weibo_web_login";
    private AuthInfo mAuthInfo;
    private Context mContext;

    public static class AuthInfo {
        private String mAppKey = "";
        private Bundle mBundle = null;
        private String mKeyHash = "";
        private String mPackageName = "";
        private String mRedirectUrl = "";
        private String mScope = "";

        public AuthInfo(Context context, String appKey, String redirectUrl, String scope) {
            this.mAppKey = appKey;
            this.mRedirectUrl = redirectUrl;
            this.mScope = scope;
            this.mPackageName = context.getPackageName();
            this.mKeyHash = Utility.getSign(context, this.mPackageName);
            initAuthBundle();
        }

        public String getAppKey() {
            return this.mAppKey;
        }

        public String getRedirectUrl() {
            return this.mRedirectUrl;
        }

        public String getScope() {
            return this.mScope;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getKeyHash() {
            return this.mKeyHash;
        }

        public Bundle getAuthBundle() {
            return this.mBundle;
        }

        private void initAuthBundle() {
            this.mBundle = new Bundle();
            this.mBundle.putString("appKey", this.mAppKey);
            this.mBundle.putString("redirectUri", this.mRedirectUrl);
            this.mBundle.putString("scope", this.mScope);
            this.mBundle.putString("packagename", this.mPackageName);
            this.mBundle.putString("key_hash", this.mKeyHash);
        }
    }

    public WeiboAuth(Context context, String appKey, String redirectUrl, String scope) {
        this.mContext = context;
        this.mAuthInfo = new AuthInfo(context, appKey, redirectUrl, scope);
    }

    public WeiboAuth(Context context, AuthInfo authInfo) {
        this.mContext = context;
        this.mAuthInfo = authInfo;
    }

    public AuthInfo getAuthInfo() {
        return this.mAuthInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.mAuthInfo = authInfo;
    }

    public void anthorize(WeiboAuthListener listener) {
        authorize(listener, 1);
    }

    public void authorize(WeiboAuthListener listener, int type) {
        startDialog(listener, type);
    }

    private void startDialog(WeiboAuthListener listener, int type) {
        if (listener != null) {
            WeiboParameters requestParams = new WeiboParameters();
            requestParams.put("client_id", this.mAuthInfo.mAppKey);
            requestParams.put("redirect_uri", this.mAuthInfo.mRedirectUrl);
            requestParams.put("scope", this.mAuthInfo.mScope);
            requestParams.put("response_type", "code");
            requestParams.put("display", "mobile");
            if (1 == type) {
                requestParams.put("packagename", this.mAuthInfo.mPackageName);
                requestParams.put("key_hash", this.mAuthInfo.mKeyHash);
            }
            String url = new StringBuilder(OAUTH2_BASE_URL).append(requestParams.encodeUrl()).toString();
            if (!NetworkHelper.hasInternetPermission(this.mContext)) {
                UIUtils.showAlert(this.mContext, "Error", "Application requires permission to access the Internet");
            } else if (NetworkHelper.isNetworkAvailable(this.mContext)) {
                new WeiboDialog(this.mContext, url, listener, this).show();
            } else {
                CharSequence networkNotAvailable = ResourceManager.getString(this.mContext, 2);
                LogUtil.m2216i(TAG, "String: " + networkNotAvailable);
                UIUtils.showToast(this.mContext, networkNotAvailable, 0);
            }
        }
    }
}
