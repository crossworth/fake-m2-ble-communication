package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.PlatformConfig.Twitter;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.media.TwitterShareContent;
import com.umeng.socialize.net.PlatformTokenUploadReq;
import com.umeng.socialize.net.PlatformTokenUploadResponse;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import java.util.HashMap;
import java.util.Map;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHandler extends UMSSOHandler {
    private static final String PackageName = "com.twitter.android";
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    private Twitter config;
    private twitter4j.Twitter mTwitter;
    private RequestToken requestToken;
    private TwitterShareContent shareContent;
    private TwitterPreferences twitterpreferences;
    private UMAuthListener umAuthListener;

    class SaveDateThread implements Runnable {
        String oauthVerifier;

        public SaveDateThread(String oauthVerifier) {
            this.oauthVerifier = oauthVerifier;
        }

        public void run() {
            try {
                AccessToken at = TwitterHandler.this.mTwitter.getOAuthAccessToken(TwitterHandler.this.requestToken, this.oauthVerifier);
                String accessToken = at.getToken();
                String accessTokenSecret = at.getTokenSecret();
                TwitterHandler.this.mTwitter.setOAuthAccessToken(at);
                TwitterHandler.this.twitterpreferences.setAuthData(accessToken, accessTokenSecret).commit();
                long userID = at.getUserId();
                User user = TwitterHandler.this.mTwitter.showUser(userID);
                PlatformTokenUploadReq req = new PlatformTokenUploadReq(TwitterHandler.this.getContext());
                req.addStringParams("to", "twitter");
                req.addStringParams("usid", userID + "");
                req.addStringParams("access_token", accessToken);
                req.addStringParams("app_id", TwitterHandler.this.config.appKey);
                req.addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_APP_KEY, TwitterHandler.this.config.appSecret);
                PlatformTokenUploadResponse resp = RestAPI.uploadPlatformToken(req);
                final Map<String, String> data = new HashMap();
                data.put("usid", userID + "");
                data.put("uid", userID + "");
                data.put("access_token", accessToken);
                data.put("access_token_secret", accessTokenSecret);
                data.put(SocializeProtocolConstants.PROTOCOL_KEY_USER_NAME2, at.getScreenName());
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        TwitterHandler.this.umAuthListener.onComplete(SHARE_MEDIA.TWITTER, 0, data);
                    }
                });
                Log.m4548e("upload token resp = " + resp);
            } catch (TwitterException e) {
                Log.m4546d("TEST", e.getMessage());
            }
        }
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.config = (Twitter) p;
        this.mTwitter = new TwitterFactory().getInstance();
        Log.m4546d("twitter", "onCreate");
        this.twitterpreferences = new TwitterPreferences(context, p.getName().toString());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.m4546d("TEST", "Twitter onActivityResult()");
        if (data != null) {
            new Thread(new SaveDateThread(data.getStringExtra(URL_TWITTER_OAUTH_VERIFIER))).start();
        } else {
            this.umAuthListener.onError(SHARE_MEDIA.TWITTER, 0, new Throwable("no data"));
        }
    }

    public void authorize(UMAuthListener listener) {
        this.umAuthListener = listener;
        loginToTwitter();
    }

    public boolean isSupportAuth() {
        return true;
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        if (isInstall(getConfig())) {
            this.shareContent = new TwitterShareContent(content);
            return shareTo(new TwitterShareContent(content), listener);
        }
        listener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    public boolean isInstall() {
        if (DeviceConfig.isAppInstalled(PackageName, getContext())) {
            return true;
        }
        return false;
    }

    private void uploadAuthData(final Map<String, String> bundle) throws SocializeException {
        new Thread(new Runnable() {
            public void run() {
                PlatformTokenUploadReq req = new PlatformTokenUploadReq(TwitterHandler.this.getContext());
                req.addStringParams("to", "twitter");
                req.addStringParams("usid", (String) bundle.get("uid"));
                req.addStringParams("access_token", (String) bundle.get("access_token"));
                req.addStringParams("refresh_token", (String) bundle.get("refresh_token"));
                req.addStringParams("expires_in", (String) bundle.get("expires_in"));
                Log.m4548e("upload token resp = " + RestAPI.uploadPlatformToken(req));
            }
        }).start();
    }

    private boolean isInstall(Platform p) {
        if (DeviceConfig.isAppInstalled(PackageName, getContext())) {
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

    private void InitializeTwitter() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(this.config.appKey);
        builder.setOAuthConsumerSecret(this.config.appSecret);
        this.mTwitter = new TwitterFactory(builder.build()).getInstance();
    }

    private void loginToTwitter() {
        Log.m4546d("twitter", "onAuth2");
        InitializeTwitter();
        try {
            this.mTwitter.setOAuthAccessToken(null);
            this.requestToken = this.mTwitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
            if (this.requestToken != null && this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
                Intent i = new Intent((Context) this.mWeakAct.get(), TwitterWebActivity.class);
                i.putExtra(URL_TWITTER_AUTH, this.requestToken.getAuthenticationURL());
                ((Activity) this.mWeakAct.get()).startActivityForResult(i, HandlerRequestCode.TWITTER_REQUEST_AUTH_CODE);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public int getRequestCode() {
        return HandlerRequestCode.TWITTER_REQUEST_AUTH_CODE;
    }

    private boolean isTwitterLoggedInAlready() {
        return (this.twitterpreferences.getString(TwitterPreferences.TOKEN) == null || this.twitterpreferences.getString(TwitterPreferences.TOKEN_SECRET) == null) ? false : true;
    }

    public boolean shareTo(TwitterShareContent shareContent, UMShareListener listener) {
        updateStatus(shareContent.getText(), listener);
        return true;
    }

    public void getPlatformInfo(UMAuthListener listener) {
        authorize(listener);
    }

    private void updateStatus(final String status, final UMShareListener listener) {
        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(this.config.appKey);
            builder.setOAuthConsumerSecret(this.config.appSecret);
            String access_token = this.twitterpreferences.getString(TwitterPreferences.TOKEN);
            String access_token_secret = this.twitterpreferences.getString(TwitterPreferences.TOKEN_SECRET);
            if (access_token == null || access_token_secret == null) {
                authorize(new UMAuthListener() {
                    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
                        TwitterHandler.this.updateStatus(status, listener);
                    }

                    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                        listener.onError(platform, new Throwable("auth fail"));
                    }

                    public void onCancel(SHARE_MEDIA platform, int action) {
                        listener.onCancel(platform);
                    }
                });
                return;
            }
            twitter4j.Twitter twitter = new TwitterFactory(builder.build()).getInstance(new AccessToken(access_token, access_token_secret));
            if (this.shareContent.isHasPic) {
                StatusUpdate mstatus = new StatusUpdate(status);
                mstatus.setMedia(this.shareContent.getImage().asFileImage());
                Log.m4546d("Status", "> " + twitter.updateStatus(mstatus).getText());
                listener.onResult(SHARE_MEDIA.TWITTER);
                return;
            }
            Log.m4546d("Status", "> " + twitter.updateStatus(status).getText());
            listener.onResult(SHARE_MEDIA.TWITTER);
        } catch (TwitterException e) {
            Log.m4548e("xxxxxx e=" + e);
            listener.onError(SHARE_MEDIA.TWITTER, new Throwable(e.getMessage()));
        } catch (Exception e2) {
            Log.m4548e("xxxxxx e=" + e2);
            listener.onError(SHARE_MEDIA.TWITTER, new Throwable(e2.getMessage()));
        }
    }

    public void deleteAuth(UMAuthListener listener) {
        logoutFromTwitter();
        listener.onComplete(SHARE_MEDIA.TWITTER, 1, null);
    }

    private void logoutFromTwitter() {
        this.twitterpreferences.delete();
    }
}
