package twitter4j.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import twitter4j.BASE64Encoder;
import twitter4j.HttpClient;
import twitter4j.HttpClientFactory;
import twitter4j.HttpParameter;
import twitter4j.HttpRequest;
import twitter4j.HttpResponse;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

public class OAuth2Authorization implements Authorization, Serializable, OAuth2Support {
    private static final long serialVersionUID = -2895232598422218647L;
    private final Configuration conf;
    private String consumerKey;
    private String consumerSecret;
    private final HttpClient http;
    private OAuth2Token token;

    public OAuth2Authorization(Configuration conf) {
        this.conf = conf;
        setOAuthConsumer(conf.getOAuthConsumerKey(), conf.getOAuthConsumerSecret());
        this.http = HttpClientFactory.getInstance(conf.getHttpClientConfiguration());
    }

    public void setOAuthConsumer(String consumerKey, String consumerSecret) {
        if (consumerKey == null) {
            consumerKey = "";
        }
        this.consumerKey = consumerKey;
        if (consumerSecret == null) {
            consumerSecret = "";
        }
        this.consumerSecret = consumerSecret;
    }

    public OAuth2Token getOAuth2Token() throws TwitterException {
        if (this.token != null) {
            throw new IllegalStateException("OAuth 2 Bearer Token is already available.");
        }
        HttpParameter[] params = new HttpParameter[(this.conf.getOAuth2Scope() == null ? 1 : 2)];
        params[0] = new HttpParameter("grant_type", "client_credentials");
        if (this.conf.getOAuth2Scope() != null) {
            params[1] = new HttpParameter("scope", this.conf.getOAuth2Scope());
        }
        HttpResponse res = this.http.post(this.conf.getOAuth2TokenURL(), params, this, null);
        if (res.getStatusCode() != 200) {
            throw new TwitterException("Obtaining OAuth 2 Bearer Token failed.", res);
        }
        this.token = new OAuth2Token(res);
        return this.token;
    }

    public void setOAuth2Token(OAuth2Token oauth2Token) {
        this.token = oauth2Token;
    }

    public void invalidateOAuth2Token() throws TwitterException {
        if (this.token == null) {
            throw new IllegalStateException("OAuth 2 Bearer Token is not available.");
        }
        HttpParameter[] params = new HttpParameter[]{new HttpParameter("access_token", this.token.getAccessToken())};
        OAuth2Token _token = this.token;
        boolean succeed = false;
        try {
            this.token = null;
            HttpResponse res = this.http.post(this.conf.getOAuth2InvalidateTokenURL(), params, this, null);
            if (res.getStatusCode() != 200) {
                throw new TwitterException("Invalidating OAuth 2 Bearer Token failed.", res);
            }
            succeed = true;
        } finally {
            if (!succeed) {
                this.token = _token;
            }
        }
    }

    public String getAuthorizationHeader(HttpRequest req) {
        if (this.token != null) {
            return this.token.generateAuthorizationHeader();
        }
        String credentials = "";
        try {
            credentials = URLEncoder.encode(this.consumerKey, "UTF-8") + ":" + URLEncoder.encode(this.consumerSecret, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "Basic " + BASE64Encoder.encode(credentials.getBytes());
    }

    public boolean isEnabled() {
        return this.token != null;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OAuth2Authorization)) {
            return false;
        }
        OAuth2Authorization that = (OAuth2Authorization) obj;
        if (this.consumerKey != null) {
            if (!this.consumerKey.equals(that.consumerKey)) {
                return false;
            }
        } else if (that.consumerKey != null) {
            return false;
        }
        if (this.consumerSecret != null) {
            if (!this.consumerSecret.equals(that.consumerSecret)) {
                return false;
            }
        } else if (that.consumerSecret != null) {
            return false;
        }
        if (this.token != null) {
            if (!this.token.equals(that.token)) {
                return false;
            }
        } else if (that.token != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.consumerKey != null) {
            result = this.consumerKey.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.consumerSecret != null) {
            hashCode = this.consumerSecret.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.token != null) {
            i = this.token.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        String str;
        StringBuilder append = new StringBuilder().append("OAuth2Authorization{consumerKey='").append(this.consumerKey).append('\'').append(", consumerSecret='******************************************'").append(", token=");
        if (this.token == null) {
            str = "null";
        } else {
            str = this.token.toString();
        }
        return append.append(str).append('}').toString();
    }
}
