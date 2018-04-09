package twitter4j.conf;

import com.umeng.socialize.bean.StatusCode;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import twitter4j.HttpClientConfiguration;
import twitter4j.Logger;

class ConfigurationBase implements Configuration, Serializable {
    private static final List<ConfigurationBase> instances = new ArrayList();
    private static final long serialVersionUID = 6175546394599249696L;
    private boolean applicationOnlyAuthEnabled = false;
    private int asyncNumThreads = 1;
    private long contributingTo = -1;
    private boolean daemonEnabled = true;
    private boolean debug = false;
    private String dispatcherImpl = "twitter4j.DispatcherImpl";
    private HttpClientConfiguration httpConf = new MyHttpClientConfiguration(null, null, null, -1, 20000, 120000, false, true);
    private int httpRetryCount = 0;
    private int httpRetryIntervalSeconds = 5;
    private int httpStreamingReadTimeout = StatusCode.ST_CODE_ERROR_CANCEL;
    private boolean includeEntitiesEnabled = true;
    private boolean includeMyRetweetEnabled = true;
    private boolean jsonStoreEnabled = false;
    private String loggerFactory = null;
    private boolean mbeanEnabled = false;
    private String mediaProvider = "TWITTER";
    private String mediaProviderAPIKey = null;
    private Properties mediaProviderParameters = null;
    private String oAuth2AccessToken;
    private String oAuth2InvalidateTokenURL = "https://api.twitter.com/oauth2/invalidate_token";
    private String oAuth2Scope;
    private String oAuth2TokenType;
    private String oAuth2TokenURL = "https://api.twitter.com/oauth2/token";
    private String oAuthAccessToken = null;
    private String oAuthAccessTokenSecret = null;
    private String oAuthAccessTokenURL = "https://api.twitter.com/oauth/access_token";
    private String oAuthAuthenticationURL = "https://api.twitter.com/oauth/authenticate";
    private String oAuthAuthorizationURL = "https://api.twitter.com/oauth/authorize";
    private String oAuthConsumerKey = null;
    private String oAuthConsumerSecret = null;
    private String oAuthRequestTokenURL = "https://api.twitter.com/oauth/request_token";
    private String password = null;
    private String restBaseURL = "https://api.twitter.com/1.1/";
    private String siteStreamBaseURL = "https://sitestream.twitter.com/1.1/";
    private boolean stallWarningsEnabled = true;
    private String streamBaseURL = "https://stream.twitter.com/1.1/";
    private boolean trimUserEnabled = false;
    private String uploadBaseURL = "https://upload.twitter.com/1.1/";
    private String user = null;
    private String userStreamBaseURL = "https://userstream.twitter.com/1.1/";
    private boolean userStreamRepliesAllEnabled = false;
    private boolean userStreamWithFollowingsEnabled = true;

    class MyHttpClientConfiguration implements HttpClientConfiguration, Serializable {
        private static final long serialVersionUID = 8226866124868861058L;
        private boolean gzipEnabled = true;
        private int httpConnectionTimeout = 20000;
        private String httpProxyHost = null;
        private String httpProxyPassword = null;
        private int httpProxyPort = -1;
        private String httpProxyUser = null;
        private int httpReadTimeout = 120000;
        private boolean prettyDebug = false;

        MyHttpClientConfiguration(String httpProxyHost, String httpProxyUser, String httpProxyPassword, int httpProxyPort, int httpConnectionTimeout, int httpReadTimeout, boolean prettyDebug, boolean gzipEnabled) {
            this.httpProxyHost = httpProxyHost;
            this.httpProxyUser = httpProxyUser;
            this.httpProxyPassword = httpProxyPassword;
            this.httpProxyPort = httpProxyPort;
            this.httpConnectionTimeout = httpConnectionTimeout;
            this.httpReadTimeout = httpReadTimeout;
            this.prettyDebug = prettyDebug;
            this.gzipEnabled = gzipEnabled;
        }

        public String getHttpProxyHost() {
            return this.httpProxyHost;
        }

        public int getHttpProxyPort() {
            return this.httpProxyPort;
        }

        public String getHttpProxyUser() {
            return this.httpProxyUser;
        }

        public String getHttpProxyPassword() {
            return this.httpProxyPassword;
        }

        public int getHttpConnectionTimeout() {
            return this.httpConnectionTimeout;
        }

        public int getHttpReadTimeout() {
            return this.httpReadTimeout;
        }

        public int getHttpRetryCount() {
            return ConfigurationBase.this.httpRetryCount;
        }

        public int getHttpRetryIntervalSeconds() {
            return ConfigurationBase.this.httpRetryIntervalSeconds;
        }

        public boolean isPrettyDebugEnabled() {
            return this.prettyDebug;
        }

        public boolean isGZIPEnabled() {
            return this.gzipEnabled;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MyHttpClientConfiguration that = (MyHttpClientConfiguration) o;
            if (this.gzipEnabled != that.gzipEnabled) {
                return false;
            }
            if (this.httpConnectionTimeout != that.httpConnectionTimeout) {
                return false;
            }
            if (this.httpProxyPort != that.httpProxyPort) {
                return false;
            }
            if (this.httpReadTimeout != that.httpReadTimeout) {
                return false;
            }
            if (this.prettyDebug != that.prettyDebug) {
                return false;
            }
            if (this.httpProxyHost == null ? that.httpProxyHost != null : !this.httpProxyHost.equals(that.httpProxyHost)) {
                return false;
            }
            if (this.httpProxyPassword == null ? that.httpProxyPassword != null : !this.httpProxyPassword.equals(that.httpProxyPassword)) {
                return false;
            }
            if (this.httpProxyUser != null) {
                if (this.httpProxyUser.equals(that.httpProxyUser)) {
                    return true;
                }
            } else if (that.httpProxyUser == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result;
            int hashCode;
            int i = 1;
            if (this.httpProxyHost != null) {
                result = this.httpProxyHost.hashCode();
            } else {
                result = 0;
            }
            int i2 = result * 31;
            if (this.httpProxyUser != null) {
                hashCode = this.httpProxyUser.hashCode();
            } else {
                hashCode = 0;
            }
            i2 = (i2 + hashCode) * 31;
            if (this.httpProxyPassword != null) {
                hashCode = this.httpProxyPassword.hashCode();
            } else {
                hashCode = 0;
            }
            i2 = (((((((i2 + hashCode) * 31) + this.httpProxyPort) * 31) + this.httpConnectionTimeout) * 31) + this.httpReadTimeout) * 31;
            if (this.prettyDebug) {
                hashCode = 1;
            } else {
                hashCode = 0;
            }
            hashCode = (i2 + hashCode) * 31;
            if (!this.gzipEnabled) {
                i = 0;
            }
            return hashCode + i;
        }

        public String toString() {
            return "MyHttpClientConfiguration{httpProxyHost='" + this.httpProxyHost + '\'' + ", httpProxyUser='" + this.httpProxyUser + '\'' + ", httpProxyPassword='" + this.httpProxyPassword + '\'' + ", httpProxyPort=" + this.httpProxyPort + ", httpConnectionTimeout=" + this.httpConnectionTimeout + ", httpReadTimeout=" + this.httpReadTimeout + ", prettyDebug=" + this.prettyDebug + ", gzipEnabled=" + this.gzipEnabled + '}';
        }
    }

    protected ConfigurationBase() {
    }

    public void dumpConfiguration() {
        Logger log = Logger.getLogger(ConfigurationBase.class);
        if (this.debug) {
            for (Field field : ConfigurationBase.class.getDeclaredFields()) {
                try {
                    Object value = field.get(this);
                    String strValue = String.valueOf(value);
                    if (value != null && field.getName().matches("oAuthConsumerSecret|oAuthAccessTokenSecret|password")) {
                        strValue = String.valueOf(value).replaceAll(".", "*");
                    }
                    log.debug(field.getName() + ": " + strValue);
                } catch (IllegalAccessException e) {
                }
            }
        }
    }

    public final boolean isDebugEnabled() {
        return this.debug;
    }

    protected final void setDebug(boolean debug) {
        this.debug = debug;
    }

    public final String getUser() {
        return this.user;
    }

    protected final void setUser(String user) {
        this.user = user;
    }

    public final String getPassword() {
        return this.password;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return this.httpConf;
    }

    protected final void setPassword(String password) {
        this.password = password;
    }

    protected final void setPrettyDebugEnabled(boolean prettyDebug) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), prettyDebug, this.httpConf.isGZIPEnabled());
    }

    protected final void setGZIPEnabled(boolean gzipEnabled) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), gzipEnabled);
    }

    protected final void setHttpProxyHost(String proxyHost) {
        this.httpConf = new MyHttpClientConfiguration(proxyHost, this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    protected final void setHttpProxyUser(String proxyUser) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), proxyUser, this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    protected final void setHttpProxyPassword(String proxyPassword) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), proxyPassword, this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    protected final void setHttpProxyPort(int proxyPort) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), proxyPort, this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    protected final void setHttpConnectionTimeout(int connectionTimeout) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), connectionTimeout, this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    protected final void setHttpReadTimeout(int readTimeout) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), readTimeout, this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    public int getHttpStreamingReadTimeout() {
        return this.httpStreamingReadTimeout;
    }

    protected final void setHttpStreamingReadTimeout(int httpStreamingReadTimeout) {
        this.httpStreamingReadTimeout = httpStreamingReadTimeout;
    }

    protected final void setHttpRetryCount(int retryCount) {
        this.httpRetryCount = retryCount;
    }

    protected final void setHttpRetryIntervalSeconds(int retryIntervalSeconds) {
        this.httpRetryIntervalSeconds = retryIntervalSeconds;
    }

    public final String getOAuthConsumerKey() {
        return this.oAuthConsumerKey;
    }

    protected final void setOAuthConsumerKey(String oAuthConsumerKey) {
        this.oAuthConsumerKey = oAuthConsumerKey;
    }

    public final String getOAuthConsumerSecret() {
        return this.oAuthConsumerSecret;
    }

    protected final void setOAuthConsumerSecret(String oAuthConsumerSecret) {
        this.oAuthConsumerSecret = oAuthConsumerSecret;
    }

    public String getOAuthAccessToken() {
        return this.oAuthAccessToken;
    }

    protected final void setOAuthAccessToken(String oAuthAccessToken) {
        this.oAuthAccessToken = oAuthAccessToken;
    }

    public String getOAuthAccessTokenSecret() {
        return this.oAuthAccessTokenSecret;
    }

    protected final void setOAuthAccessTokenSecret(String oAuthAccessTokenSecret) {
        this.oAuthAccessTokenSecret = oAuthAccessTokenSecret;
    }

    public String getOAuth2TokenType() {
        return this.oAuth2TokenType;
    }

    protected final void setOAuth2TokenType(String oAuth2TokenType) {
        this.oAuth2TokenType = oAuth2TokenType;
    }

    public String getOAuth2AccessToken() {
        return this.oAuth2AccessToken;
    }

    public String getOAuth2Scope() {
        return this.oAuth2Scope;
    }

    protected final void setOAuth2AccessToken(String oAuth2AccessToken) {
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    protected final void setOAuth2Scope(String oAuth2Scope) {
        this.oAuth2Scope = oAuth2Scope;
    }

    public final int getAsyncNumThreads() {
        return this.asyncNumThreads;
    }

    protected final void setAsyncNumThreads(int asyncNumThreads) {
        this.asyncNumThreads = asyncNumThreads;
    }

    public final long getContributingTo() {
        return this.contributingTo;
    }

    protected final void setContributingTo(long contributingTo) {
        this.contributingTo = contributingTo;
    }

    public String getRestBaseURL() {
        return this.restBaseURL;
    }

    protected final void setRestBaseURL(String restBaseURL) {
        this.restBaseURL = restBaseURL;
    }

    public String getUploadBaseURL() {
        return this.uploadBaseURL;
    }

    protected final void setUploadBaseURL(String uploadBaseURL) {
        this.uploadBaseURL = uploadBaseURL;
    }

    public String getStreamBaseURL() {
        return this.streamBaseURL;
    }

    protected final void setStreamBaseURL(String streamBaseURL) {
        this.streamBaseURL = streamBaseURL;
    }

    public String getUserStreamBaseURL() {
        return this.userStreamBaseURL;
    }

    protected final void setUserStreamBaseURL(String siteStreamBaseURL) {
        this.userStreamBaseURL = siteStreamBaseURL;
    }

    public String getSiteStreamBaseURL() {
        return this.siteStreamBaseURL;
    }

    protected final void setSiteStreamBaseURL(String siteStreamBaseURL) {
        this.siteStreamBaseURL = siteStreamBaseURL;
    }

    public String getOAuthRequestTokenURL() {
        return this.oAuthRequestTokenURL;
    }

    protected final void setOAuthRequestTokenURL(String oAuthRequestTokenURL) {
        this.oAuthRequestTokenURL = oAuthRequestTokenURL;
    }

    public String getOAuthAuthorizationURL() {
        return this.oAuthAuthorizationURL;
    }

    protected final void setOAuthAuthorizationURL(String oAuthAuthorizationURL) {
        this.oAuthAuthorizationURL = oAuthAuthorizationURL;
    }

    public String getOAuthAccessTokenURL() {
        return this.oAuthAccessTokenURL;
    }

    protected final void setOAuthAccessTokenURL(String oAuthAccessTokenURL) {
        this.oAuthAccessTokenURL = oAuthAccessTokenURL;
    }

    public String getOAuthAuthenticationURL() {
        return this.oAuthAuthenticationURL;
    }

    protected final void setOAuthAuthenticationURL(String oAuthAuthenticationURL) {
        this.oAuthAuthenticationURL = oAuthAuthenticationURL;
    }

    public String getOAuth2TokenURL() {
        return this.oAuth2TokenURL;
    }

    protected final void setOAuth2TokenURL(String oAuth2TokenURL) {
        this.oAuth2TokenURL = oAuth2TokenURL;
    }

    public String getOAuth2InvalidateTokenURL() {
        return this.oAuth2InvalidateTokenURL;
    }

    protected final void setOAuth2InvalidateTokenURL(String oAuth2InvalidateTokenURL) {
        this.oAuth2InvalidateTokenURL = oAuth2InvalidateTokenURL;
    }

    public String getDispatcherImpl() {
        return this.dispatcherImpl;
    }

    protected final void setDispatcherImpl(String dispatcherImpl) {
        this.dispatcherImpl = dispatcherImpl;
    }

    public String getLoggerFactory() {
        return this.loggerFactory;
    }

    public boolean isIncludeEntitiesEnabled() {
        return this.includeEntitiesEnabled;
    }

    protected void setIncludeEntitiesEnabled(boolean includeEntitiesEnabled) {
        this.includeEntitiesEnabled = includeEntitiesEnabled;
    }

    protected final void setLoggerFactory(String loggerImpl) {
        this.loggerFactory = loggerImpl;
    }

    public boolean isIncludeMyRetweetEnabled() {
        return this.includeMyRetweetEnabled;
    }

    public void setIncludeMyRetweetEnabled(boolean enabled) {
        this.includeMyRetweetEnabled = enabled;
    }

    public boolean isTrimUserEnabled() {
        return this.trimUserEnabled;
    }

    public boolean isDaemonEnabled() {
        return this.daemonEnabled;
    }

    protected void setDaemonEnabled(boolean daemonEnabled) {
        this.daemonEnabled = daemonEnabled;
    }

    public void setTrimUserEnabled(boolean enabled) {
        this.trimUserEnabled = enabled;
    }

    public boolean isJSONStoreEnabled() {
        return this.jsonStoreEnabled;
    }

    protected final void setJSONStoreEnabled(boolean enabled) {
        this.jsonStoreEnabled = enabled;
    }

    public boolean isMBeanEnabled() {
        return this.mbeanEnabled;
    }

    protected final void setMBeanEnabled(boolean enabled) {
        this.mbeanEnabled = enabled;
    }

    public boolean isUserStreamRepliesAllEnabled() {
        return this.userStreamRepliesAllEnabled;
    }

    public boolean isUserStreamWithFollowingsEnabled() {
        return this.userStreamWithFollowingsEnabled;
    }

    protected final void setUserStreamRepliesAllEnabled(boolean enabled) {
        this.userStreamRepliesAllEnabled = enabled;
    }

    protected final void setUserStreamWithFollowingsEnabled(boolean enabled) {
        this.userStreamWithFollowingsEnabled = enabled;
    }

    public boolean isStallWarningsEnabled() {
        return this.stallWarningsEnabled;
    }

    protected final void setStallWarningsEnabled(boolean stallWarningsEnabled) {
        this.stallWarningsEnabled = stallWarningsEnabled;
    }

    public boolean isApplicationOnlyAuthEnabled() {
        return this.applicationOnlyAuthEnabled;
    }

    protected final void setApplicationOnlyAuthEnabled(boolean applicationOnlyAuthEnabled) {
        this.applicationOnlyAuthEnabled = applicationOnlyAuthEnabled;
    }

    public String getMediaProvider() {
        return this.mediaProvider;
    }

    protected final void setMediaProvider(String mediaProvider) {
        this.mediaProvider = mediaProvider;
    }

    public String getMediaProviderAPIKey() {
        return this.mediaProviderAPIKey;
    }

    protected final void setMediaProviderAPIKey(String mediaProviderAPIKey) {
        this.mediaProviderAPIKey = mediaProviderAPIKey;
    }

    public Properties getMediaProviderParameters() {
        return this.mediaProviderParameters;
    }

    protected final void setMediaProviderParameters(Properties props) {
        this.mediaProviderParameters = props;
    }

    static String fixURL(boolean useSSL, String url) {
        if (url == null) {
            return null;
        }
        int index = url.indexOf("://");
        if (-1 == index) {
            throw new IllegalArgumentException("url should contain '://'");
        }
        String hostAndLater = url.substring(index + 3);
        if (useSSL) {
            return "https://" + hostAndLater;
        }
        return "http://" + hostAndLater;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfigurationBase that = (ConfigurationBase) o;
        if (this.applicationOnlyAuthEnabled != that.applicationOnlyAuthEnabled) {
            return false;
        }
        if (this.asyncNumThreads != that.asyncNumThreads) {
            return false;
        }
        if (this.contributingTo != that.contributingTo) {
            return false;
        }
        if (this.daemonEnabled != that.daemonEnabled) {
            return false;
        }
        if (this.debug != that.debug) {
            return false;
        }
        if (this.httpRetryCount != that.httpRetryCount) {
            return false;
        }
        if (this.httpRetryIntervalSeconds != that.httpRetryIntervalSeconds) {
            return false;
        }
        if (this.httpStreamingReadTimeout != that.httpStreamingReadTimeout) {
            return false;
        }
        if (this.includeEntitiesEnabled != that.includeEntitiesEnabled) {
            return false;
        }
        if (this.includeMyRetweetEnabled != that.includeMyRetweetEnabled) {
            return false;
        }
        if (this.jsonStoreEnabled != that.jsonStoreEnabled) {
            return false;
        }
        if (this.mbeanEnabled != that.mbeanEnabled) {
            return false;
        }
        if (this.stallWarningsEnabled != that.stallWarningsEnabled) {
            return false;
        }
        if (this.trimUserEnabled != that.trimUserEnabled) {
            return false;
        }
        if (this.userStreamRepliesAllEnabled != that.userStreamRepliesAllEnabled) {
            return false;
        }
        if (this.userStreamWithFollowingsEnabled != that.userStreamWithFollowingsEnabled) {
            return false;
        }
        if (this.dispatcherImpl == null ? that.dispatcherImpl != null : !this.dispatcherImpl.equals(that.dispatcherImpl)) {
            return false;
        }
        if (this.httpConf == null ? that.httpConf != null : !this.httpConf.equals(that.httpConf)) {
            return false;
        }
        if (this.loggerFactory == null ? that.loggerFactory != null : !this.loggerFactory.equals(that.loggerFactory)) {
            return false;
        }
        if (this.mediaProvider == null ? that.mediaProvider != null : !this.mediaProvider.equals(that.mediaProvider)) {
            return false;
        }
        if (this.mediaProviderAPIKey == null ? that.mediaProviderAPIKey != null : !this.mediaProviderAPIKey.equals(that.mediaProviderAPIKey)) {
            return false;
        }
        if (this.mediaProviderParameters == null ? that.mediaProviderParameters != null : !this.mediaProviderParameters.equals(that.mediaProviderParameters)) {
            return false;
        }
        if (this.oAuth2AccessToken == null ? that.oAuth2AccessToken != null : !this.oAuth2AccessToken.equals(that.oAuth2AccessToken)) {
            return false;
        }
        if (this.oAuth2InvalidateTokenURL == null ? that.oAuth2InvalidateTokenURL != null : !this.oAuth2InvalidateTokenURL.equals(that.oAuth2InvalidateTokenURL)) {
            return false;
        }
        if (this.oAuth2TokenType == null ? that.oAuth2TokenType != null : !this.oAuth2TokenType.equals(that.oAuth2TokenType)) {
            return false;
        }
        if (this.oAuth2TokenURL == null ? that.oAuth2TokenURL != null : !this.oAuth2TokenURL.equals(that.oAuth2TokenURL)) {
            return false;
        }
        if (this.oAuth2Scope == null ? that.oAuth2Scope != null : !this.oAuth2Scope.equals(that.oAuth2Scope)) {
            return false;
        }
        if (this.oAuthAccessToken == null ? that.oAuthAccessToken != null : !this.oAuthAccessToken.equals(that.oAuthAccessToken)) {
            return false;
        }
        if (this.oAuthAccessTokenSecret == null ? that.oAuthAccessTokenSecret != null : !this.oAuthAccessTokenSecret.equals(that.oAuthAccessTokenSecret)) {
            return false;
        }
        if (this.oAuthAccessTokenURL == null ? that.oAuthAccessTokenURL != null : !this.oAuthAccessTokenURL.equals(that.oAuthAccessTokenURL)) {
            return false;
        }
        if (this.oAuthAuthenticationURL == null ? that.oAuthAuthenticationURL != null : !this.oAuthAuthenticationURL.equals(that.oAuthAuthenticationURL)) {
            return false;
        }
        if (this.oAuthAuthorizationURL == null ? that.oAuthAuthorizationURL != null : !this.oAuthAuthorizationURL.equals(that.oAuthAuthorizationURL)) {
            return false;
        }
        if (this.oAuthConsumerKey == null ? that.oAuthConsumerKey != null : !this.oAuthConsumerKey.equals(that.oAuthConsumerKey)) {
            return false;
        }
        if (this.oAuthConsumerSecret == null ? that.oAuthConsumerSecret != null : !this.oAuthConsumerSecret.equals(that.oAuthConsumerSecret)) {
            return false;
        }
        if (this.oAuthRequestTokenURL == null ? that.oAuthRequestTokenURL != null : !this.oAuthRequestTokenURL.equals(that.oAuthRequestTokenURL)) {
            return false;
        }
        if (this.password == null ? that.password != null : !this.password.equals(that.password)) {
            return false;
        }
        if (this.restBaseURL == null ? that.restBaseURL != null : !this.restBaseURL.equals(that.restBaseURL)) {
            return false;
        }
        if (this.uploadBaseURL == null ? that.uploadBaseURL != null : !this.uploadBaseURL.equals(that.uploadBaseURL)) {
            return false;
        }
        if (this.siteStreamBaseURL == null ? that.siteStreamBaseURL != null : !this.siteStreamBaseURL.equals(that.siteStreamBaseURL)) {
            return false;
        }
        if (this.streamBaseURL == null ? that.streamBaseURL != null : !this.streamBaseURL.equals(that.streamBaseURL)) {
            return false;
        }
        if (this.user == null ? that.user != null : !this.user.equals(that.user)) {
            return false;
        }
        if (this.userStreamBaseURL != null) {
            if (this.userStreamBaseURL.equals(that.userStreamBaseURL)) {
                return true;
            }
        } else if (that.userStreamBaseURL == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 1;
        if (this.debug) {
            result = 1;
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.user != null) {
            hashCode = this.user.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.password != null) {
            hashCode = this.password.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.httpConf != null) {
            hashCode = this.httpConf.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (((((((i2 + hashCode) * 31) + this.httpStreamingReadTimeout) * 31) + this.httpRetryCount) * 31) + this.httpRetryIntervalSeconds) * 31;
        if (this.oAuthConsumerKey != null) {
            hashCode = this.oAuthConsumerKey.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthConsumerSecret != null) {
            hashCode = this.oAuthConsumerSecret.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthAccessToken != null) {
            hashCode = this.oAuthAccessToken.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthAccessTokenSecret != null) {
            hashCode = this.oAuthAccessTokenSecret.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuth2TokenType != null) {
            hashCode = this.oAuth2TokenType.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuth2AccessToken != null) {
            hashCode = this.oAuth2AccessToken.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuth2Scope != null) {
            hashCode = this.oAuth2Scope.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthRequestTokenURL != null) {
            hashCode = this.oAuthRequestTokenURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthAuthorizationURL != null) {
            hashCode = this.oAuthAuthorizationURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthAccessTokenURL != null) {
            hashCode = this.oAuthAccessTokenURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuthAuthenticationURL != null) {
            hashCode = this.oAuthAuthenticationURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuth2TokenURL != null) {
            hashCode = this.oAuth2TokenURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.oAuth2InvalidateTokenURL != null) {
            hashCode = this.oAuth2InvalidateTokenURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.restBaseURL != null) {
            hashCode = this.restBaseURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.uploadBaseURL != null) {
            hashCode = this.uploadBaseURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.streamBaseURL != null) {
            hashCode = this.streamBaseURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.userStreamBaseURL != null) {
            hashCode = this.userStreamBaseURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.siteStreamBaseURL != null) {
            hashCode = this.siteStreamBaseURL.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.dispatcherImpl != null) {
            hashCode = this.dispatcherImpl.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (((i2 + hashCode) * 31) + this.asyncNumThreads) * 31;
        if (this.loggerFactory != null) {
            hashCode = this.loggerFactory.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (((i2 + hashCode) * 31) + ((int) (this.contributingTo ^ (this.contributingTo >>> 32)))) * 31;
        if (this.includeMyRetweetEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.includeEntitiesEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.trimUserEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.jsonStoreEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mbeanEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.userStreamRepliesAllEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.userStreamWithFollowingsEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.stallWarningsEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.applicationOnlyAuthEnabled) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mediaProvider != null) {
            hashCode = this.mediaProvider.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mediaProviderAPIKey != null) {
            hashCode = this.mediaProviderAPIKey.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mediaProviderParameters != null) {
            hashCode = this.mediaProviderParameters.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (!this.daemonEnabled) {
            i = 0;
        }
        return hashCode + i;
    }

    public String toString() {
        return "ConfigurationBase{debug=" + this.debug + ", user='" + this.user + '\'' + ", password='" + this.password + '\'' + ", httpConf=" + this.httpConf + ", httpStreamingReadTimeout=" + this.httpStreamingReadTimeout + ", httpRetryCount=" + this.httpRetryCount + ", httpRetryIntervalSeconds=" + this.httpRetryIntervalSeconds + ", oAuthConsumerKey='" + this.oAuthConsumerKey + '\'' + ", oAuthConsumerSecret='" + this.oAuthConsumerSecret + '\'' + ", oAuthAccessToken='" + this.oAuthAccessToken + '\'' + ", oAuthAccessTokenSecret='" + this.oAuthAccessTokenSecret + '\'' + ", oAuth2TokenType='" + this.oAuth2TokenType + '\'' + ", oAuth2AccessToken='" + this.oAuth2AccessToken + '\'' + ", oAuth2Scope='" + this.oAuth2Scope + '\'' + ", oAuthRequestTokenURL='" + this.oAuthRequestTokenURL + '\'' + ", oAuthAuthorizationURL='" + this.oAuthAuthorizationURL + '\'' + ", oAuthAccessTokenURL='" + this.oAuthAccessTokenURL + '\'' + ", oAuthAuthenticationURL='" + this.oAuthAuthenticationURL + '\'' + ", oAuth2TokenURL='" + this.oAuth2TokenURL + '\'' + ", oAuth2InvalidateTokenURL='" + this.oAuth2InvalidateTokenURL + '\'' + ", restBaseURL='" + this.restBaseURL + '\'' + ", uploadBaseURL='" + this.uploadBaseURL + '\'' + ", streamBaseURL='" + this.streamBaseURL + '\'' + ", userStreamBaseURL='" + this.userStreamBaseURL + '\'' + ", siteStreamBaseURL='" + this.siteStreamBaseURL + '\'' + ", dispatcherImpl='" + this.dispatcherImpl + '\'' + ", asyncNumThreads=" + this.asyncNumThreads + ", loggerFactory='" + this.loggerFactory + '\'' + ", contributingTo=" + this.contributingTo + ", includeMyRetweetEnabled=" + this.includeMyRetweetEnabled + ", includeEntitiesEnabled=" + this.includeEntitiesEnabled + ", trimUserEnabled=" + this.trimUserEnabled + ", jsonStoreEnabled=" + this.jsonStoreEnabled + ", mbeanEnabled=" + this.mbeanEnabled + ", userStreamRepliesAllEnabled=" + this.userStreamRepliesAllEnabled + ", userStreamWithFollowingsEnabled=" + this.userStreamWithFollowingsEnabled + ", stallWarningsEnabled=" + this.stallWarningsEnabled + ", applicationOnlyAuthEnabled=" + this.applicationOnlyAuthEnabled + ", mediaProvider='" + this.mediaProvider + '\'' + ", mediaProviderAPIKey='" + this.mediaProviderAPIKey + '\'' + ", mediaProviderParameters=" + this.mediaProviderParameters + ", daemonEnabled=" + this.daemonEnabled + '}';
    }

    private static void cacheInstance(ConfigurationBase conf) {
        if (!instances.contains(conf)) {
            instances.add(conf);
        }
    }

    protected void cacheInstance() {
        cacheInstance(this);
    }

    private static ConfigurationBase getInstance(ConfigurationBase configurationBase) {
        int index = instances.indexOf(configurationBase);
        if (index != -1) {
            return (ConfigurationBase) instances.get(index);
        }
        instances.add(configurationBase);
        return configurationBase;
    }

    protected Object readResolve() throws ObjectStreamException {
        return getInstance(this);
    }
}
