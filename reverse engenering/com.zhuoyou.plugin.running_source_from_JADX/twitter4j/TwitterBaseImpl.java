package twitter4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.auth.BasicAuthorization;
import twitter4j.auth.NullAuthorization;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.auth.OAuth2Support;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.OAuthSupport;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

abstract class TwitterBaseImpl implements TwitterBase, Serializable, OAuthSupport, OAuth2Support, HttpResponseListener {
    private static final String WWW_DETAILS = "See http://twitter4j.org/en/configuration.html for details. See and register at http://apps.twitter.com/";
    private static final long serialVersionUID = -7824361938865528554L;
    Authorization auth;
    Configuration conf;
    ObjectFactory factory;
    transient HttpClient http;
    private transient long id = 0;
    private List<RateLimitStatusListener> rateLimitStatusListeners = new ArrayList(0);
    private transient String screenName = null;

    TwitterBaseImpl(Configuration conf, Authorization auth) {
        this.conf = conf;
        this.auth = auth;
        init();
    }

    private void init() {
        if (this.auth == null) {
            String consumerKey = this.conf.getOAuthConsumerKey();
            String consumerSecret = this.conf.getOAuthConsumerSecret();
            if (consumerKey == null || consumerSecret == null) {
                this.auth = NullAuthorization.getInstance();
            } else if (this.conf.isApplicationOnlyAuthEnabled()) {
                OAuth2Authorization oauth2 = new OAuth2Authorization(this.conf);
                String tokenType = this.conf.getOAuth2TokenType();
                accessToken = this.conf.getOAuth2AccessToken();
                if (!(tokenType == null || accessToken == null)) {
                    oauth2.setOAuth2Token(new OAuth2Token(tokenType, accessToken));
                }
                this.auth = oauth2;
            } else {
                OAuthAuthorization oauth = new OAuthAuthorization(this.conf);
                accessToken = this.conf.getOAuthAccessToken();
                String accessTokenSecret = this.conf.getOAuthAccessTokenSecret();
                if (!(accessToken == null || accessTokenSecret == null)) {
                    oauth.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
                }
                this.auth = oauth;
            }
        }
        this.http = HttpClientFactory.getInstance(this.conf.getHttpClientConfiguration());
        setFactory();
    }

    void setFactory() {
        this.factory = new JSONImplFactory(this.conf);
    }

    public String getScreenName() throws TwitterException, IllegalStateException {
        if (this.auth.isEnabled()) {
            if (this.screenName == null) {
                if (this.auth instanceof BasicAuthorization) {
                    this.screenName = ((BasicAuthorization) this.auth).getUserId();
                    if (this.screenName.contains("@")) {
                        this.screenName = null;
                    }
                }
                if (this.screenName == null) {
                    fillInIDAndScreenName();
                }
            }
            return this.screenName;
        }
        throw new IllegalStateException("Neither user ID/password combination nor OAuth consumer key/secret combination supplied");
    }

    public long getId() throws TwitterException, IllegalStateException {
        if (this.auth.isEnabled()) {
            if (0 == this.id) {
                fillInIDAndScreenName();
            }
            return this.id;
        }
        throw new IllegalStateException("Neither user ID/password combination nor OAuth consumer key/secret combination supplied");
    }

    User fillInIDAndScreenName() throws TwitterException {
        return fillInIDAndScreenName(null);
    }

    User fillInIDAndScreenName(HttpParameter[] parameters) throws TwitterException {
        ensureAuthorizationEnabled();
        User user = new UserJSONImpl(this.http.get(this.conf.getRestBaseURL() + "account/verify_credentials.json", parameters, this.auth, this), this.conf);
        this.screenName = user.getScreenName();
        this.id = user.getId();
        return user;
    }

    public void httpResponseReceived(HttpResponseEvent event) {
        if (this.rateLimitStatusListeners.size() != 0) {
            RateLimitStatus rateLimitStatus;
            int statusCode;
            HttpResponse res = event.getResponse();
            TwitterException te = event.getTwitterException();
            if (te != null) {
                rateLimitStatus = te.getRateLimitStatus();
                statusCode = te.getStatusCode();
            } else {
                rateLimitStatus = JSONImplFactory.createRateLimitStatusFromResponseHeader(res);
                statusCode = res.getStatusCode();
            }
            if (rateLimitStatus != null) {
                RateLimitStatusEvent statusEvent = new RateLimitStatusEvent(this, rateLimitStatus, event.isAuthenticated());
                if (statusCode == HttpResponseCode.ENHANCE_YOUR_CLAIM || statusCode == 503 || statusCode == HttpResponseCode.TOO_MANY_REQUESTS) {
                    for (RateLimitStatusListener listener : this.rateLimitStatusListeners) {
                        listener.onRateLimitStatus(statusEvent);
                        listener.onRateLimitReached(statusEvent);
                    }
                    return;
                }
                for (RateLimitStatusListener listener2 : this.rateLimitStatusListeners) {
                    listener2.onRateLimitStatus(statusEvent);
                }
            }
        }
    }

    public final Authorization getAuthorization() {
        return this.auth;
    }

    public Configuration getConfiguration() {
        return this.conf;
    }

    final void ensureAuthorizationEnabled() {
        if (!this.auth.isEnabled()) {
            throw new IllegalStateException("Authentication credentials are missing. See http://twitter4j.org/en/configuration.html for details. See and register at http://apps.twitter.com/");
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.putFields();
        out.writeFields();
        out.writeObject(this.conf);
        out.writeObject(this.auth);
        List<RateLimitStatusListener> serializableRateLimitStatusListeners = new ArrayList(0);
        for (RateLimitStatusListener listener : this.rateLimitStatusListeners) {
            if (listener instanceof Serializable) {
                serializableRateLimitStatusListeners.add(listener);
            }
        }
        out.writeObject(serializableRateLimitStatusListeners);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.readFields();
        this.conf = (Configuration) stream.readObject();
        this.auth = (Authorization) stream.readObject();
        this.rateLimitStatusListeners = (List) stream.readObject();
        this.http = HttpClientFactory.getInstance(this.conf.getHttpClientConfiguration());
        setFactory();
    }

    public synchronized void setOAuthConsumer(String consumerKey, String consumerSecret) {
        if (consumerKey == null) {
            throw new NullPointerException("consumer key is null");
        } else if (consumerSecret == null) {
            throw new NullPointerException("consumer secret is null");
        } else if (this.auth instanceof NullAuthorization) {
            if (this.conf.isApplicationOnlyAuthEnabled()) {
                OAuth2Authorization oauth2 = new OAuth2Authorization(this.conf);
                oauth2.setOAuthConsumer(consumerKey, consumerSecret);
                this.auth = oauth2;
            } else {
                OAuthAuthorization oauth = new OAuthAuthorization(this.conf);
                oauth.setOAuthConsumer(consumerKey, consumerSecret);
                this.auth = oauth;
            }
        } else if (this.auth instanceof BasicAuthorization) {
            XAuthAuthorization xauth = new XAuthAuthorization((BasicAuthorization) this.auth);
            xauth.setOAuthConsumer(consumerKey, consumerSecret);
            this.auth = xauth;
        } else if ((this.auth instanceof OAuthAuthorization) || (this.auth instanceof OAuth2Authorization)) {
            throw new IllegalStateException("consumer key/secret pair already set.");
        }
    }

    public RequestToken getOAuthRequestToken() throws TwitterException {
        return getOAuthRequestToken(null);
    }

    public RequestToken getOAuthRequestToken(String callbackUrl) throws TwitterException {
        return getOAuth().getOAuthRequestToken(callbackUrl);
    }

    public RequestToken getOAuthRequestToken(String callbackUrl, String xAuthAccessType) throws TwitterException {
        return getOAuth().getOAuthRequestToken(callbackUrl, xAuthAccessType);
    }

    public RequestToken getOAuthRequestToken(String callbackUrl, String xAuthAccessType, String xAuthMode) throws TwitterException {
        return getOAuth().getOAuthRequestToken(callbackUrl, xAuthAccessType, xAuthMode);
    }

    public synchronized AccessToken getOAuthAccessToken() throws TwitterException {
        AccessToken oauthAccessToken;
        Authorization auth = getAuthorization();
        if (auth instanceof BasicAuthorization) {
            BasicAuthorization basicAuth = (BasicAuthorization) auth;
            auth = AuthorizationFactory.getInstance(this.conf);
            if (auth instanceof OAuthAuthorization) {
                this.auth = auth;
                oauthAccessToken = ((OAuthAuthorization) auth).getOAuthAccessToken(basicAuth.getUserId(), basicAuth.getPassword());
            } else {
                throw new IllegalStateException("consumer key / secret combination not supplied.");
            }
        } else if (auth instanceof XAuthAuthorization) {
            XAuthAuthorization xauth = (XAuthAuthorization) auth;
            this.auth = xauth;
            OAuthAuthorization oauthAuth = new OAuthAuthorization(this.conf);
            oauthAuth.setOAuthConsumer(xauth.getConsumerKey(), xauth.getConsumerSecret());
            oauthAccessToken = oauthAuth.getOAuthAccessToken(xauth.getUserId(), xauth.getPassword());
        } else {
            oauthAccessToken = getOAuth().getOAuthAccessToken();
        }
        this.screenName = oauthAccessToken.getScreenName();
        this.id = oauthAccessToken.getUserId();
        return oauthAccessToken;
    }

    public synchronized AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
        AccessToken oauthAccessToken;
        oauthAccessToken = getOAuth().getOAuthAccessToken(oauthVerifier);
        this.screenName = oauthAccessToken.getScreenName();
        return oauthAccessToken;
    }

    public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
        AccessToken oauthAccessToken;
        oauthAccessToken = getOAuth().getOAuthAccessToken(requestToken);
        this.screenName = oauthAccessToken.getScreenName();
        return oauthAccessToken;
    }

    public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
        return getOAuth().getOAuthAccessToken(requestToken, oauthVerifier);
    }

    public synchronized void setOAuthAccessToken(AccessToken accessToken) {
        getOAuth().setOAuthAccessToken(accessToken);
    }

    public synchronized AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
        return getOAuth().getOAuthAccessToken(screenName, password);
    }

    private OAuthSupport getOAuth() {
        if (this.auth instanceof OAuthSupport) {
            return (OAuthSupport) this.auth;
        }
        throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
    }

    public synchronized OAuth2Token getOAuth2Token() throws TwitterException {
        return getOAuth2().getOAuth2Token();
    }

    public void setOAuth2Token(OAuth2Token oauth2Token) {
        getOAuth2().setOAuth2Token(oauth2Token);
    }

    public synchronized void invalidateOAuth2Token() throws TwitterException {
        getOAuth2().invalidateOAuth2Token();
    }

    private OAuth2Support getOAuth2() {
        if (this.auth instanceof OAuth2Support) {
            return (OAuth2Support) this.auth;
        }
        throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TwitterBaseImpl)) {
            return false;
        }
        TwitterBaseImpl that = (TwitterBaseImpl) o;
        if (this.auth == null ? that.auth != null : !this.auth.equals(that.auth)) {
            return false;
        }
        if (!this.conf.equals(that.conf)) {
            return false;
        }
        if (this.http == null ? that.http != null : !this.http.equals(that.http)) {
            return false;
        }
        if (this.rateLimitStatusListeners.equals(that.rateLimitStatusListeners)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = this.conf.hashCode() * 31;
        if (this.http != null) {
            hashCode = this.http.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (((hashCode2 + hashCode) * 31) + this.rateLimitStatusListeners.hashCode()) * 31;
        if (this.auth != null) {
            i = this.auth.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "TwitterBase{conf=" + this.conf + ", http=" + this.http + ", rateLimitStatusListeners=" + this.rateLimitStatusListeners + ", auth=" + this.auth + '}';
    }
}
