package twitter4j.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import twitter4j.HttpResponse;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.TwitterException;

public class OAuth2Token implements Serializable {
    private static final long serialVersionUID = -8985359441959903216L;
    private String accessToken;
    private String tokenType;

    OAuth2Token(HttpResponse res) throws TwitterException {
        JSONObject json = res.asJSONObject();
        this.tokenType = getRawString("token_type", json);
        try {
            this.accessToken = URLDecoder.decode(getRawString("access_token", json), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    public OAuth2Token(String tokenType, String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    String generateAuthorizationHeader() {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(this.accessToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "Bearer " + encoded;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OAuth2Token)) {
            return false;
        }
        OAuth2Token that = (OAuth2Token) obj;
        if (this.tokenType != null) {
            if (!this.tokenType.equals(that.tokenType)) {
                return false;
            }
        } else if (that.tokenType != null) {
            return false;
        }
        if (this.accessToken != null) {
            if (!this.accessToken.equals(that.accessToken)) {
                return false;
            }
        } else if (that.accessToken != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.tokenType != null) {
            result = this.tokenType.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.accessToken != null) {
            i = this.accessToken.hashCode();
        }
        return i2 + i;
    }

    public String toString() {
        return "OAuth2Token{tokenType='" + this.tokenType + '\'' + ", accessToken='" + this.accessToken + '\'' + '}';
    }

    private static String getRawString(String name, JSONObject json) {
        String str = null;
        try {
            if (!json.isNull(name)) {
                str = json.getString(name);
            }
        } catch (JSONException e) {
        }
        return str;
    }
}
