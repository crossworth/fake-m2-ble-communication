package twitter4j.auth;

import com.sina.weibo.sdk.register.mobile.MobileRegisterActivity;
import java.io.Serializable;
import javax.crypto.spec.SecretKeySpec;
import twitter4j.HttpResponse;
import twitter4j.TwitterException;

abstract class OAuthToken implements Serializable {
    private static final long serialVersionUID = -7841506492508140600L;
    private String[] responseStr;
    private transient SecretKeySpec secretKeySpec;
    private final String token;
    private final String tokenSecret;

    public OAuthToken(String token, String tokenSecret) {
        this.responseStr = null;
        if (token == null) {
            throw new IllegalArgumentException("Token can't be null");
        } else if (tokenSecret == null) {
            throw new IllegalArgumentException("TokenSecret can't be null");
        } else {
            this.token = token;
            this.tokenSecret = tokenSecret;
        }
    }

    OAuthToken(HttpResponse response) throws TwitterException {
        this(response.asString());
    }

    OAuthToken(String string) {
        this.responseStr = null;
        this.responseStr = string.split("&");
        this.tokenSecret = getParameter("oauth_token_secret");
        this.token = getParameter(MobileRegisterActivity.RESPONSE_OAUTH_TOKEN);
    }

    public String getToken() {
        return this.token;
    }

    public String getTokenSecret() {
        return this.tokenSecret;
    }

    void setSecretKeySpec(SecretKeySpec secretKeySpec) {
        this.secretKeySpec = secretKeySpec;
    }

    SecretKeySpec getSecretKeySpec() {
        return this.secretKeySpec;
    }

    public String getParameter(String parameter) {
        for (String str : this.responseStr) {
            if (str.startsWith(parameter + '=')) {
                return str.split("=")[1].trim();
            }
        }
        return null;
    }

    public String getresponse() {
        String value = "";
        for (String str : this.responseStr) {
            value = value + str;
        }
        return value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OAuthToken)) {
            return false;
        }
        OAuthToken that = (OAuthToken) o;
        if (!this.token.equals(that.token)) {
            return false;
        }
        if (this.tokenSecret.equals(that.tokenSecret)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.token.hashCode() * 31) + this.tokenSecret.hashCode();
    }

    public String toString() {
        return "OAuthToken{token='" + this.token + '\'' + ", tokenSecret='" + this.tokenSecret + '\'' + ", secretKeySpec=" + this.secretKeySpec + '}';
    }
}
