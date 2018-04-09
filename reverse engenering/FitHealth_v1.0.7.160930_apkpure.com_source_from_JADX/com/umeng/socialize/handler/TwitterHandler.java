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
import com.umeng.socialize.utils.Dummy;
import com.umeng.socialize.utils.Log;
import java.util.HashMap;
import java.util.Map;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHandler extends UMSSOHandler {
    private static final String PackageName = "com.twitter.android";
    private static final String ShareTarget = "com.twitter.android.composer.ComposerActivity";
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    private Twitter config;
    private Context context;
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
                Log.m3248d("TEST", "username=" + TwitterHandler.this.mTwitter.showUser(userID).getName());
                PlatformTokenUploadReq req = new PlatformTokenUploadReq(TwitterHandler.this.getContext());
                req.addStringParams("to", "twitter");
                req.addStringParams("usid", userID + "");
                req.addStringParams("access_token", accessToken);
                req.addStringParams("app_id", TwitterHandler.this.config.appKey);
                req.addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_APP_KEY, TwitterHandler.this.config.appSecret);
                PlatformTokenUploadResponse resp = RestAPI.uploadPlatformToken(req);
                final Map<String, String> data = new HashMap();
                data.put("usid", userID + "");
                data.put("access_token", accessToken);
                data.put("username", at.getScreenName());
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        TwitterHandler.this.umAuthListener.onComplete(SHARE_MEDIA.TWITTER, 0, data);
                    }
                });
                Log.m3250e("upload token resp = " + resp);
            } catch (TwitterException e) {
                Log.m3248d("TEST", e.getMessage());
            }
        }
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.config = (Twitter) p;
        this.context = context;
        this.mTwitter = new TwitterFactory().getInstance();
        Log.m3248d("twitter", "onCreate");
        this.twitterpreferences = new TwitterPreferences(context, p.getName().toString());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.m3248d("TEST", "Twitter onActivityResult()");
        if (data != null) {
            new Thread(new SaveDateThread(data.getStringExtra("oauth_verifier"))).start();
        } else {
            this.umAuthListener.onError(SHARE_MEDIA.TWITTER, 0, new Throwable("no data"));
        }
    }

    public void authorize(Activity act, UMAuthListener listener) {
        this.umAuthListener = (UMAuthListener) Dummy.get(UMAuthListener.class, listener);
        loginToTwitter(act);
    }

    public boolean isSupportAuth() {
        return true;
    }

    public boolean share(Activity activity, ShareContent content, UMShareListener listener) {
        UMShareListener safelistener = (UMShareListener) Dummy.get(UMShareListener.class, listener);
        if (isInstall(activity, getConfig())) {
            this.shareContent = new TwitterShareContent(content);
            return shareTo(activity, new TwitterShareContent(content), safelistener);
        }
        safelistener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    public boolean isInstall(Context mContext) {
        if (DeviceConfig.isAppInstalled(PackageName, mContext)) {
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
                req.addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_REFRESH_TOKEN, (String) bundle.get(SocializeProtocolConstants.PROTOCOL_KEY_REFRESH_TOKEN));
                req.addStringParams("expires_in", (String) bundle.get("expires_in"));
                Log.m3250e("upload token resp = " + RestAPI.uploadPlatformToken(req));
            }
        }).start();
    }

    private boolean isInstall(Context mContext, Platform p) {
        if (DeviceConfig.isAppInstalled(PackageName, mContext)) {
            return true;
        }
        StringBuilder msb = new StringBuilder();
        msb.append("请安装");
        msb.append(ResContainer.getString(mContext, p.getName().toSnsPlatform().mShowWord));
        msb.append("客户端");
        Log.m3256v(msb.toString());
        if (Config.IsToastTip) {
            Toast.makeText(mContext, msb, 1).show();
        }
        return false;
    }

    private void InitializeTwitter() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(this.config.appKey);
        builder.setOAuthConsumerSecret(this.config.appSecret);
        this.mTwitter = new TwitterFactory(builder.build()).getInstance();
    }

    private void loginToTwitter(Activity act) {
        Log.m3248d("twitter", "onAuth");
        if (isTwitterLoggedInAlready()) {
            this.umAuthListener.onComplete(SHARE_MEDIA.TWITTER, 0, null);
            return;
        }
        Log.m3248d("twitter", "onAuth2");
        InitializeTwitter();
        try {
            String access_token = this.twitterpreferences.getString(TwitterPreferences.TOKEN);
            String access_token_secret = this.twitterpreferences.getString(TwitterPreferences.TOKEN_SECRET);
            if (access_token == null || access_token_secret == null) {
                this.mTwitter.setOAuthAccessToken(null);
            } else {
                this.mTwitter.setOAuthAccessToken(this.mTwitter.getOAuthAccessToken(access_token, access_token_secret));
            }
            this.requestToken = this.mTwitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
            if (this.requestToken != null) {
                Intent i = new Intent(act, TwitterWebActivity.class);
                i.putExtra(URL_TWITTER_AUTH, this.requestToken.getAuthenticationURL());
                act.startActivityForResult(i, HandlerRequestCode.TWITTER_REQUEST_AUTH_CODE);
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

    public boolean shareTo(Activity activity, TwitterShareContent shareContent, UMShareListener listener) {
        updateStatus(activity, shareContent.getText(), listener);
        return true;
    }

    private void test() {
    }

    private void updateStatus(Activity act, String status, UMShareListener listener) {
        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(this.config.appKey);
            builder.setOAuthConsumerSecret(this.config.appSecret);
            String access_token = this.twitterpreferences.getString(TwitterPreferences.TOKEN);
            String access_token_secret = this.twitterpreferences.getString(TwitterPreferences.TOKEN_SECRET);
            if (access_token == null || access_token_secret == null) {
                authorize(act, null);
                return;
            }
            twitter4j.Twitter twitter = new TwitterFactory(builder.build()).getInstance(new AccessToken(access_token, access_token_secret));
            if (this.shareContent.isHasPic) {
                StatusUpdate mstatus = new StatusUpdate(status);
                mstatus.setMedia(this.shareContent.getImage().asFileImage());
                Log.m3248d("Status", "> " + twitter.updateStatus(mstatus).getText());
                listener.onResult(SHARE_MEDIA.TWITTER);
                return;
            }
            Log.m3248d("Status", "> " + twitter.updateStatus(status).getText());
            listener.onResult(SHARE_MEDIA.TWITTER);
        } catch (TwitterException e) {
            listener.onError(SHARE_MEDIA.TWITTER, new Throwable(e.getMessage()));
        } catch (Exception e2) {
            listener.onError(SHARE_MEDIA.TWITTER, new Throwable(e2.getMessage()));
        }
    }

    public void deleteAuth(Context context, UMAuthListener listener) {
        super.deleteAuth(context, listener);
        logoutFromTwitter();
        listener.onComplete(SHARE_MEDIA.TWITTER, 1, null);
    }

    private void logoutFromTwitter() {
        this.twitterpreferences.delete();
    }
}
