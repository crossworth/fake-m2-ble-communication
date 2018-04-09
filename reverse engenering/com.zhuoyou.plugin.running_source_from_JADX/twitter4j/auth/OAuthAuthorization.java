package twitter4j.auth;

import com.sina.weibo.sdk.register.mobile.MobileRegisterActivity;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import twitter4j.BASE64Encoder;
import twitter4j.HttpClient;
import twitter4j.HttpClientFactory;
import twitter4j.HttpParameter;
import twitter4j.HttpRequest;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

public class OAuthAuthorization implements Authorization, Serializable, OAuthSupport {
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final HttpParameter OAUTH_SIGNATURE_METHOD = new HttpParameter("oauth_signature_method", "HMAC-SHA1");
    private static final Random RAND = new Random();
    private static transient HttpClient http = null;
    private static final long serialVersionUID = -886869424811858868L;
    private final Configuration conf;
    private String consumerKey = "";
    private String consumerSecret;
    private OAuthToken oauthToken = null;
    private String realm = null;

    public OAuthAuthorization(Configuration conf) {
        this.conf = conf;
        http = HttpClientFactory.getInstance(conf.getHttpClientConfiguration());
        setOAuthConsumer(conf.getOAuthConsumerKey(), conf.getOAuthConsumerSecret());
        if (conf.getOAuthAccessToken() != null && conf.getOAuthAccessTokenSecret() != null) {
            setOAuthAccessToken(new AccessToken(conf.getOAuthAccessToken(), conf.getOAuthAccessTokenSecret()));
        }
    }

    public String getAuthorizationHeader(HttpRequest req) {
        return generateAuthorizationHeader(req.getMethod().name(), req.getURL(), req.getParameters(), this.oauthToken);
    }

    private void ensureTokenIsAvailable() {
        if (this.oauthToken == null) {
            throw new IllegalStateException("No Token available.");
        }
    }

    public boolean isEnabled() {
        return this.oauthToken != null && (this.oauthToken instanceof AccessToken);
    }

    public RequestToken getOAuthRequestToken() throws TwitterException {
        return getOAuthRequestToken(null, null, null);
    }

    public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException {
        return getOAuthRequestToken(callbackURL, null, null);
    }

    public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) throws TwitterException {
        return getOAuthRequestToken(callbackURL, xAuthAccessType, null);
    }

    public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType, String xAuthMode) throws TwitterException {
        if (this.oauthToken instanceof AccessToken) {
            throw new IllegalStateException("Access token already available.");
        }
        List<HttpParameter> params = new ArrayList();
        if (callbackURL != null) {
            params.add(new HttpParameter("oauth_callback", callbackURL));
        }
        if (xAuthAccessType != null) {
            params.add(new HttpParameter("x_auth_access_type", xAuthAccessType));
        }
        if (xAuthMode != null) {
            params.add(new HttpParameter("x_auth_mode", xAuthMode));
        }
        this.oauthToken = new RequestToken(http.post(this.conf.getOAuthRequestTokenURL(), (HttpParameter[]) params.toArray(new HttpParameter[params.size()]), this, null), (OAuthSupport) this);
        return (RequestToken) this.oauthToken;
    }

    public AccessToken getOAuthAccessToken() throws TwitterException {
        ensureTokenIsAvailable();
        if (this.oauthToken instanceof AccessToken) {
            return (AccessToken) this.oauthToken;
        }
        this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), null, this, null));
        return (AccessToken) this.oauthToken;
    }

    public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
        ensureTokenIsAvailable();
        this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), new HttpParameter[]{new HttpParameter("oauth_verifier", oauthVerifier)}, this, null));
        return (AccessToken) this.oauthToken;
    }

    public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
        this.oauthToken = requestToken;
        return getOAuthAccessToken();
    }

    public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
        this.oauthToken = requestToken;
        return getOAuthAccessToken(oauthVerifier);
    }

    public AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
        try {
            String url = this.conf.getOAuthAccessTokenURL();
            if (url.indexOf("http://") == 0) {
                url = "https://" + url.substring(7);
            }
            this.oauthToken = new AccessToken(http.post(url, new HttpParameter[]{new HttpParameter("x_auth_username", screenName), new HttpParameter("x_auth_password", password), new HttpParameter("x_auth_mode", "client_auth")}, this, null));
            return (AccessToken) this.oauthToken;
        } catch (TwitterException te) {
            throw new TwitterException("The screen name / password combination seems to be invalid.", te, te.getStatusCode());
        }
    }

    public void setOAuthAccessToken(AccessToken accessToken) {
        this.oauthToken = accessToken;
    }

    public void setOAuthRealm(String realm) {
        this.realm = realm;
    }

    String generateAuthorizationHeader(String method, String url, HttpParameter[] params, String nonce, String timestamp, OAuthToken otoken) {
        if (params == null) {
            params = new HttpParameter[0];
        }
        List<HttpParameter> oauthHeaderParams = new ArrayList(5);
        oauthHeaderParams.add(new HttpParameter("oauth_consumer_key", this.consumerKey));
        oauthHeaderParams.add(OAUTH_SIGNATURE_METHOD);
        oauthHeaderParams.add(new HttpParameter("oauth_timestamp", timestamp));
        oauthHeaderParams.add(new HttpParameter("oauth_nonce", nonce));
        oauthHeaderParams.add(new HttpParameter("oauth_version", "1.0"));
        if (otoken != null) {
            oauthHeaderParams.add(new HttpParameter(MobileRegisterActivity.RESPONSE_OAUTH_TOKEN, otoken.getToken()));
        }
        List signatureBaseParams = new ArrayList(oauthHeaderParams.size() + params.length);
        signatureBaseParams.addAll(oauthHeaderParams);
        if (!HttpParameter.containsFile(params)) {
            signatureBaseParams.addAll(toParamList(params));
        }
        parseGetParameters(url, signatureBaseParams);
        StringBuilder base = new StringBuilder(method).append("&").append(HttpParameter.encode(constructRequestURL(url))).append("&");
        base.append(HttpParameter.encode(normalizeRequestParameters(signatureBaseParams)));
        oauthHeaderParams.add(new HttpParameter("oauth_signature", generateSignature(base.toString(), otoken)));
        if (this.realm != null) {
            oauthHeaderParams.add(new HttpParameter("realm", this.realm));
        }
        return "OAuth " + encodeParameters(oauthHeaderParams, ",", true);
    }

    private void parseGetParameters(String url, List<HttpParameter> signatureBaseParams) {
        int queryStart = url.indexOf("?");
        if (-1 != queryStart) {
            url.split("&");
            try {
                for (String query : url.substring(queryStart + 1).split("&")) {
                    String[] split = query.split("=");
                    if (split.length == 2) {
                        signatureBaseParams.add(new HttpParameter(URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(split[1], "UTF-8")));
                    } else {
                        signatureBaseParams.add(new HttpParameter(URLDecoder.decode(split[0], "UTF-8"), ""));
                    }
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
    }

    String generateAuthorizationHeader(String method, String url, HttpParameter[] params, OAuthToken token) {
        long timestamp = System.currentTimeMillis() / 1000;
        return generateAuthorizationHeader(method, url, params, String.valueOf(timestamp + ((long) RAND.nextInt())), String.valueOf(timestamp), token);
    }

    public List<HttpParameter> generateOAuthSignatureHttpParams(String method, String url) {
        long timestamp = System.currentTimeMillis() / 1000;
        long nonce = timestamp + ((long) RAND.nextInt());
        List<HttpParameter> oauthHeaderParams = new ArrayList(5);
        oauthHeaderParams.add(new HttpParameter("oauth_consumer_key", this.consumerKey));
        oauthHeaderParams.add(OAUTH_SIGNATURE_METHOD);
        oauthHeaderParams.add(new HttpParameter("oauth_timestamp", timestamp));
        oauthHeaderParams.add(new HttpParameter("oauth_nonce", nonce));
        oauthHeaderParams.add(new HttpParameter("oauth_version", "1.0"));
        if (this.oauthToken != null) {
            oauthHeaderParams.add(new HttpParameter(MobileRegisterActivity.RESPONSE_OAUTH_TOKEN, this.oauthToken.getToken()));
        }
        List signatureBaseParams = new ArrayList(oauthHeaderParams.size());
        signatureBaseParams.addAll(oauthHeaderParams);
        parseGetParameters(url, signatureBaseParams);
        StringBuilder base = new StringBuilder(method).append("&").append(HttpParameter.encode(constructRequestURL(url))).append("&");
        base.append(HttpParameter.encode(normalizeRequestParameters(signatureBaseParams)));
        String str = "oauth_signature";
        oauthHeaderParams.add(new HttpParameter(str, generateSignature(base.toString(), this.oauthToken)));
        return oauthHeaderParams;
    }

    String generateSignature(String data, OAuthToken token) {
        try {
            SecretKeySpec spec;
            Mac mac = Mac.getInstance(HMAC_SHA1);
            if (token == null) {
                spec = new SecretKeySpec((HttpParameter.encode(this.consumerSecret) + "&").getBytes(), HMAC_SHA1);
            } else {
                spec = token.getSecretKeySpec();
                if (spec == null) {
                    spec = new SecretKeySpec((HttpParameter.encode(this.consumerSecret) + "&" + HttpParameter.encode(token.getTokenSecret())).getBytes(), HMAC_SHA1);
                    token.setSecretKeySpec(spec);
                }
            }
            mac.init(spec);
            return BASE64Encoder.encode(mac.doFinal(data.getBytes()));
        } catch (InvalidKeyException ike) {
            throw new AssertionError(ike);
        } catch (NoSuchAlgorithmException nsae) {
            throw new AssertionError(nsae);
        }
    }

    String generateSignature(String data) {
        return generateSignature(data, null);
    }

    static String normalizeRequestParameters(HttpParameter[] params) {
        return normalizeRequestParameters(toParamList(params));
    }

    private static String normalizeRequestParameters(List<HttpParameter> params) {
        Collections.sort(params);
        return encodeParameters(params);
    }

    private static List<HttpParameter> toParamList(HttpParameter[] params) {
        List<HttpParameter> paramList = new ArrayList(params.length);
        paramList.addAll(Arrays.asList(params));
        return paramList;
    }

    public static String encodeParameters(List<HttpParameter> httpParams) {
        return encodeParameters(httpParams, "&", false);
    }

    public static String encodeParameters(List<HttpParameter> httpParams, String splitter, boolean quot) {
        StringBuilder buf = new StringBuilder();
        for (HttpParameter param : httpParams) {
            if (!param.isFile()) {
                if (buf.length() != 0) {
                    if (quot) {
                        buf.append("\"");
                    }
                    buf.append(splitter);
                }
                buf.append(HttpParameter.encode(param.getName())).append("=");
                if (quot) {
                    buf.append("\"");
                }
                buf.append(HttpParameter.encode(param.getValue()));
            }
        }
        if (buf.length() != 0 && quot) {
            buf.append("\"");
        }
        return buf.toString();
    }

    static String constructRequestURL(String url) {
        int index = url.indexOf("?");
        if (-1 != index) {
            url = url.substring(0, index);
        }
        int slashIndex = url.indexOf("/", 8);
        String baseURL = url.substring(0, slashIndex).toLowerCase();
        int colonIndex = baseURL.indexOf(":", 8);
        if (-1 != colonIndex) {
            if (baseURL.startsWith("http://") && baseURL.endsWith(":80")) {
                baseURL = baseURL.substring(0, colonIndex);
            } else if (baseURL.startsWith("https://") && baseURL.endsWith(":443")) {
                baseURL = baseURL.substring(0, colonIndex);
            }
        }
        return baseURL + url.substring(slashIndex);
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OAuthSupport)) {
            return false;
        }
        OAuthAuthorization that = (OAuthAuthorization) o;
        if (this.consumerKey == null ? that.consumerKey != null : !this.consumerKey.equals(that.consumerKey)) {
            return false;
        }
        if (this.consumerSecret == null ? that.consumerSecret != null : !this.consumerSecret.equals(that.consumerSecret)) {
            return false;
        }
        if (this.oauthToken != null) {
            if (this.oauthToken.equals(that.oauthToken)) {
                return true;
            }
        } else if (that.oauthToken == null) {
            return true;
        }
        return false;
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
        if (this.oauthToken != null) {
            i = this.oauthToken.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "OAuthAuthorization{consumerKey='" + this.consumerKey + '\'' + ", consumerSecret='******************************************'" + ", oauthToken=" + this.oauthToken + '}';
    }
}
