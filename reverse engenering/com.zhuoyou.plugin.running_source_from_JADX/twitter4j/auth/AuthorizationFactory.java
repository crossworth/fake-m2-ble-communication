package twitter4j.auth;

import twitter4j.conf.Configuration;

public final class AuthorizationFactory {
    public static Authorization getInstance(Configuration conf) {
        Authorization auth = null;
        String consumerKey = conf.getOAuthConsumerKey();
        String consumerSecret = conf.getOAuthConsumerSecret();
        if (consumerKey == null || consumerSecret == null) {
            String screenName = conf.getUser();
            String password = conf.getPassword();
            if (!(screenName == null || password == null)) {
                auth = new BasicAuthorization(screenName, password);
            }
        } else if (conf.isApplicationOnlyAuthEnabled()) {
            OAuth2Authorization oauth2 = new OAuth2Authorization(conf);
            String tokenType = conf.getOAuth2TokenType();
            accessToken = conf.getOAuth2AccessToken();
            if (!(tokenType == null || accessToken == null)) {
                oauth2.setOAuth2Token(new OAuth2Token(tokenType, accessToken));
            }
            auth = oauth2;
        } else {
            OAuthAuthorization oauth = new OAuthAuthorization(conf);
            accessToken = conf.getOAuthAccessToken();
            String accessTokenSecret = conf.getOAuthAccessTokenSecret();
            if (!(accessToken == null || accessTokenSecret == null)) {
                oauth.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
            }
            Object auth2 = oauth;
        }
        if (auth == null) {
            return NullAuthorization.getInstance();
        }
        return auth;
    }
}
