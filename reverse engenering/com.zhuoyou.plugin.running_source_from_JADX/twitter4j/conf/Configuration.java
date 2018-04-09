package twitter4j.conf;

import java.io.Serializable;
import twitter4j.HttpClientConfiguration;
import twitter4j.auth.AuthorizationConfiguration;

public interface Configuration extends AuthorizationConfiguration, Serializable {
    int getAsyncNumThreads();

    long getContributingTo();

    String getDispatcherImpl();

    HttpClientConfiguration getHttpClientConfiguration();

    int getHttpStreamingReadTimeout();

    String getOAuth2AccessToken();

    String getOAuth2InvalidateTokenURL();

    String getOAuth2Scope();

    String getOAuth2TokenType();

    String getOAuth2TokenURL();

    String getOAuthAccessToken();

    String getOAuthAccessTokenSecret();

    String getOAuthAccessTokenURL();

    String getOAuthAuthenticationURL();

    String getOAuthAuthorizationURL();

    String getOAuthConsumerKey();

    String getOAuthConsumerSecret();

    String getOAuthRequestTokenURL();

    String getPassword();

    String getRestBaseURL();

    String getUploadBaseURL();

    String getUser();

    boolean isApplicationOnlyAuthEnabled();

    boolean isDaemonEnabled();

    boolean isIncludeEmailEnabled();

    boolean isIncludeEntitiesEnabled();

    boolean isIncludeExtAltTextEnabled();

    boolean isIncludeMyRetweetEnabled();

    boolean isJSONStoreEnabled();

    boolean isMBeanEnabled();

    boolean isTrimUserEnabled();
}
