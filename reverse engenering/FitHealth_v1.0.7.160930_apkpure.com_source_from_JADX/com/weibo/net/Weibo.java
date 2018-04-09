package com.weibo.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.handler.TwitterPreferences;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import oauth.signpost.OAuth;
import org.apache.http.HttpHeaders;

public class Weibo {
    private static String APP_KEY = "";
    private static String APP_SECRET = "";
    private static final int DEFAULT_AUTH_ACTIVITY_CODE = 32973;
    public static final String DEFAULT_CANCEL_URI = "wbconnect://cancel";
    public static final String DEFAULT_REDIRECT_URI = "wbconnect://success";
    public static final String EXPIRES = "expires_in";
    public static String SERVER = "https://api.weibo.com/2/";
    public static final String TOKEN = "access_token";
    public static String URL_ACCESS_TOKEN = "http://api.t.sina.com.cn/oauth/access_token";
    public static String URL_AUTHENTICATION = "http://api.t.sina.com.cn/oauth/authenticate";
    public static String URL_AUTHORIZE = "http://api.t.sina.com.cn/oauth/authorize";
    public static String URL_OAUTH2_ACCESS_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";
    public static String URL_OAUTH2_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token";
    public static String URL_OAUTH_TOKEN = "http://api.t.sina.com.cn/oauth/request_token";
    private static Weibo mWeiboInstance = null;
    private Token mAccessToken = null;
    private WeiboDialogListener mAuthDialogListener;
    private String mRedirectUrl;
    private RequestToken mRequestToken = null;

    class C18331 implements WeiboDialogListener {
        C18331() {
        }

        public void onComplete(Bundle values) {
            CookieSyncManager.getInstance().sync();
            if (Weibo.this.mAccessToken == null) {
                Weibo.this.mAccessToken = new Token();
            }
            Weibo.this.mAccessToken.setToken(values.getString("access_token"));
            Weibo.this.mAccessToken.setExpiresIn(values.getString("expires_in"));
            if (Weibo.this.isSessionValid()) {
                Log.d("Weibo-authorize", "Login Success! access_token=" + Weibo.this.mAccessToken.getToken() + " expires=" + Weibo.this.mAccessToken.getExpiresIn());
                Weibo.this.mAuthDialogListener.onComplete(values);
                return;
            }
            Log.d("Weibo-authorize", "Failed to receive access token");
            Weibo.this.mAuthDialogListener.onWeiboException(new WeiboException("Failed to receive access token."));
        }

        public void onError(DialogError error) {
            Log.d("Weibo-authorize", "Login failed: " + error);
            Weibo.this.mAuthDialogListener.onError(error);
        }

        public void onWeiboException(WeiboException error) {
            Log.d("Weibo-authorize", "Login failed: " + error);
            Weibo.this.mAuthDialogListener.onWeiboException(error);
        }

        public void onCancel() {
            Log.d("Weibo-authorize", "Login canceled");
            Weibo.this.mAuthDialogListener.onCancel();
        }
    }

    class C18342 implements WeiboDialogListener {
        C18342() {
        }

        public void onComplete(Bundle values) {
            CookieSyncManager.getInstance().sync();
            if (Weibo.this.mAccessToken == null) {
                Weibo.this.mAccessToken = new Token();
            }
            Weibo.this.mAccessToken.setToken(values.getString("access_token"));
            Weibo.this.mAccessToken.setExpiresIn(values.getString("expires_in"));
            if (Weibo.this.isSessionValid()) {
                Log.d("Weibo-authorize", "Login Success! access_token=" + Weibo.this.mAccessToken.getToken() + " expires=" + Weibo.this.mAccessToken.getExpiresIn());
                Weibo.this.mAuthDialogListener.onComplete(values);
                return;
            }
            Log.d("Weibo-authorize", "Failed to receive access token");
            Weibo.this.mAuthDialogListener.onWeiboException(new WeiboException("Failed to receive access token."));
        }

        public void onError(DialogError error) {
            Log.d("Weibo-authorize", "Login failed: " + error);
            Weibo.this.mAuthDialogListener.onError(error);
        }

        public void onWeiboException(WeiboException error) {
            Log.d("Weibo-authorize", "Login failed: " + error);
            Weibo.this.mAuthDialogListener.onWeiboException(error);
        }

        public void onCancel() {
            Log.d("Weibo-authorize", "Login canceled");
            Weibo.this.mAuthDialogListener.onCancel();
        }
    }

    private Weibo() {
        Utility.setRequestHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
        Utility.setTokenObject(this.mRequestToken);
        this.mRedirectUrl = DEFAULT_REDIRECT_URI;
    }

    public static synchronized Weibo getInstance() {
        Weibo weibo;
        synchronized (Weibo.class) {
            if (mWeiboInstance == null) {
                mWeiboInstance = new Weibo();
            }
            weibo = mWeiboInstance;
        }
        return weibo;
    }

    public void setAccessToken(AccessToken token) {
        this.mAccessToken = token;
    }

    public Token getAccessToken() {
        return this.mAccessToken;
    }

    public void setupConsumerConfig(String consumer_key, String consumer_secret) {
        APP_KEY = consumer_key;
        APP_SECRET = consumer_secret;
    }

    public static String getAppKey() {
        return APP_KEY;
    }

    public static String getAppSecret() {
        return APP_SECRET;
    }

    public void setRequestToken(RequestToken token) {
        this.mRequestToken = token;
    }

    public static String getSERVER() {
        return SERVER;
    }

    public static void setSERVER(String sERVER) {
        SERVER = sERVER;
    }

    public void addOauthverifier(String verifier) {
        this.mRequestToken.setVerifier(verifier);
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public void setRedirectUrl(String mRedirectUrl) {
        this.mRedirectUrl = mRedirectUrl;
    }

    public String request(Context context, String url, WeiboParameters params, String httpMethod, Token token) throws WeiboException {
        return Utility.openUrl(context, url, httpMethod, params, this.mAccessToken);
    }

    public RequestToken getRequestToken(Context context, String key, String secret, String callback_url) throws WeiboException {
        Utility.setAuthorization(new RequestTokenHeader());
        WeiboParameters postParams = new WeiboParameters();
        postParams.add(OAuth.OAUTH_CALLBACK, callback_url);
        RequestToken request = new RequestToken(Utility.openUrl(context, URL_OAUTH_TOKEN, "POST", postParams, null));
        this.mRequestToken = request;
        return request;
    }

    public AccessToken generateAccessToken(Context context, RequestToken requestToken) throws WeiboException {
        Utility.setAuthorization(new AccessTokenHeader());
        WeiboParameters authParam = new WeiboParameters();
        authParam.add(OAuth.OAUTH_VERIFIER, this.mRequestToken.getVerifier());
        authParam.add(SocialConstants.PARAM_SOURCE, APP_KEY);
        AccessToken accessToken = new AccessToken(Utility.openUrl(context, URL_ACCESS_TOKEN, "POST", authParam, this.mRequestToken));
        this.mAccessToken = accessToken;
        return accessToken;
    }

    public AccessToken getXauthAccessToken(Context context, String app_key, String app_secret, String usrname, String password) throws WeiboException {
        Utility.setAuthorization(new XAuthHeader());
        WeiboParameters postParams = new WeiboParameters();
        postParams.add("x_auth_username", usrname);
        postParams.add("x_auth_password", password);
        postParams.add("oauth_consumer_key", APP_KEY);
        AccessToken accessToken = new AccessToken(Utility.openUrl(context, URL_ACCESS_TOKEN, "POST", postParams, null));
        this.mAccessToken = accessToken;
        return accessToken;
    }

    public Oauth2AccessToken getOauth2AccessToken(Context context, String app_key, String app_secret, String usrname, String password) throws WeiboException {
        Utility.setAuthorization(new Oauth2AccessTokenHeader());
        WeiboParameters postParams = new WeiboParameters();
        postParams.add("username", usrname);
        postParams.add("password", password);
        postParams.add("client_id", app_key);
        postParams.add("client_secret", app_secret);
        postParams.add("grant_type", "password");
        Oauth2AccessToken accessToken = new Oauth2AccessToken(Utility.openUrl(context, URL_OAUTH2_ACCESS_TOKEN, "POST", postParams, null));
        this.mAccessToken = accessToken;
        return accessToken;
    }

    public boolean share2weibo(Activity activity, String accessToken, String tokenSecret, String content, String picPath) throws WeiboException {
        if (TextUtils.isEmpty(accessToken)) {
            throw new WeiboException("token can not be null!");
        } else if (TextUtils.isEmpty(content) && TextUtils.isEmpty(picPath)) {
            throw new WeiboException("weibo content can not be null!");
        } else {
            Intent i = new Intent(activity, ShareActivity.class);
            i.putExtra(ShareActivity.EXTRA_ACCESS_TOKEN, accessToken);
            i.putExtra(ShareActivity.EXTRA_TOKEN_SECRET, tokenSecret);
            i.putExtra(ShareActivity.EXTRA_WEIBO_CONTENT, content);
            i.putExtra(ShareActivity.EXTRA_PIC_URI, picPath);
            activity.startActivity(i);
            return true;
        }
    }

    private boolean startSingleSignOn(Activity activity, String applicationId, String[] permissions, int activityCode) {
        return false;
    }

    private void startDialogAuth(Activity activity, String[] permissions) {
        WeiboParameters params = new WeiboParameters();
        if (permissions.length > 0) {
            params.add("scope", TextUtils.join(SeparatorConstants.SEPARATOR_ADS_ID, permissions));
        }
        CookieSyncManager.createInstance(activity);
        dialog(activity, params, new C18331());
    }

    private void startWebViewAuth(Activity activity, WebView webview, String[] permissions) {
        WeiboParameters params = new WeiboParameters();
        if (permissions.length > 0) {
            params.add("scope", TextUtils.join(SeparatorConstants.SEPARATOR_ADS_ID, permissions));
        }
        CookieSyncManager.createInstance(activity);
        webview(activity, webview, params, new C18342());
    }

    public void authorize(Activity activity, WeiboDialogListener listener) {
        authorize(activity, null, new String[0], 32973, listener);
    }

    public void authorize(Activity activity, WebView webview, WeiboDialogListener listener) {
        authorize(activity, webview, new String[0], 32973, listener);
    }

    private void authorize(Activity activity, String[] permissions, WeiboDialogListener listener) {
        authorize(activity, null, permissions, 32973, listener);
    }

    private void authorize(Activity activity, WebView webview, String[] permissions, WeiboDialogListener listener) {
        authorize(activity, webview, permissions, 32973, listener);
    }

    private void authorize(Activity activity, WebView webview, String[] permissions, int activityCode, WeiboDialogListener listener) {
        Utility.setAuthorization(new Oauth2AccessTokenHeader());
        boolean singleSignOnStarted = false;
        this.mAuthDialogListener = listener;
        if (activityCode >= 0) {
            singleSignOnStarted = startSingleSignOn(activity, APP_KEY, permissions, activityCode);
        }
        if (!singleSignOnStarted) {
            if (webview == null) {
                startDialogAuth(activity, permissions);
            } else {
                startWebViewAuth(activity, webview, permissions);
            }
        }
    }

    private void authorizeCallBack(int requestCode, int resultCode, Intent data) {
    }

    public void dialog(Context context, WeiboParameters parameters, WeiboDialogListener listener) {
        parameters.add("client_id", APP_KEY);
        parameters.add("response_type", TwitterPreferences.TOKEN);
        parameters.add("redirect_uri", this.mRedirectUrl);
        parameters.add("display", "mobile");
        if (isSessionValid()) {
            parameters.add("access_token", this.mAccessToken.getToken());
        }
        String url = URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(parameters);
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Utility.showAlert(context, "Error", "Application requires permission to access the Internet");
        } else {
            new WeiboDialog(this, context, url, listener).show();
        }
    }

    public void webview(Context context, WebView webview, WeiboParameters parameters, WeiboDialogListener listener) {
        parameters.add("client_id", APP_KEY);
        parameters.add("response_type", TwitterPreferences.TOKEN);
        parameters.add("redirect_uri", this.mRedirectUrl);
        parameters.add("display", "mobile");
        if (isSessionValid()) {
            parameters.add("access_token", this.mAccessToken.getToken());
        }
        String url = URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(parameters);
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Utility.showAlert(context, "Error", "Application requires permission to access the Internet");
        } else {
            WeiboWebView weiboWebView = new WeiboWebView(this, webview, context, url, listener);
        }
    }

    public boolean isSessionValid() {
        if (this.mAccessToken == null || TextUtils.isEmpty(this.mAccessToken.getToken())) {
            return false;
        }
        if (this.mAccessToken.getExpiresIn() == 0 || System.currentTimeMillis() < this.mAccessToken.getExpiresIn()) {
            return true;
        }
        return false;
    }
}
