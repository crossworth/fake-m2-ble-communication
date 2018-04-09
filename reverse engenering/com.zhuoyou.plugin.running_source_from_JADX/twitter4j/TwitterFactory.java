package twitter4j;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

public class TwitterFactory implements Serializable {
    static final Authorization DEFAULT_AUTHORIZATION = AuthorizationFactory.getInstance(ConfigurationContext.getInstance());
    private static final Twitter SINGLETON;
    private static final Constructor<Twitter> TWITTER_CONSTRUCTOR;
    private static final long serialVersionUID = -563983536986910054L;
    private final Configuration conf;

    static {
        boolean gaeDetected;
        try {
            Class.forName("com.google.appengine.api.urlfetch.URLFetchService");
            gaeDetected = true;
        } catch (ClassNotFoundException e) {
            gaeDetected = false;
        }
        String className = null;
        if (gaeDetected) {
            String APP_ENGINE_TWITTER_IMPL = "twitter4j.AppEngineTwitterImpl";
            try {
                Class.forName("twitter4j.AppEngineTwitterImpl");
                className = "twitter4j.AppEngineTwitterImpl";
            } catch (ClassNotFoundException e2) {
            }
        }
        if (className == null) {
            className = "twitter4j.TwitterImpl";
        }
        try {
            TWITTER_CONSTRUCTOR = Class.forName(className).getDeclaredConstructor(new Class[]{Configuration.class, Authorization.class});
            try {
                SINGLETON = (Twitter) TWITTER_CONSTRUCTOR.newInstance(new Object[]{ConfigurationContext.getInstance(), DEFAULT_AUTHORIZATION});
            } catch (InstantiationException e3) {
                throw new AssertionError(e3);
            } catch (IllegalAccessException e4) {
                throw new AssertionError(e4);
            } catch (InvocationTargetException e5) {
                throw new AssertionError(e5);
            }
        } catch (NoSuchMethodException e6) {
            throw new AssertionError(e6);
        } catch (ClassNotFoundException e7) {
            throw new AssertionError(e7);
        }
    }

    public TwitterFactory() {
        this(ConfigurationContext.getInstance());
    }

    public TwitterFactory(Configuration conf) {
        if (conf == null) {
            throw new NullPointerException("configuration cannot be null");
        }
        this.conf = conf;
    }

    public TwitterFactory(String configTreePath) {
        this(ConfigurationContext.getInstance(configTreePath));
    }

    public Twitter getInstance() {
        return getInstance(AuthorizationFactory.getInstance(this.conf));
    }

    public Twitter getInstance(AccessToken accessToken) {
        String consumerKey = this.conf.getOAuthConsumerKey();
        String consumerSecret = this.conf.getOAuthConsumerSecret();
        if (consumerKey == null && consumerSecret == null) {
            throw new IllegalStateException("Consumer key and Consumer secret not supplied.");
        }
        Authorization oauth = new OAuthAuthorization(this.conf);
        oauth.setOAuthAccessToken(accessToken);
        return getInstance(oauth);
    }

    public Twitter getInstance(Authorization auth) {
        try {
            return (Twitter) TWITTER_CONSTRUCTOR.newInstance(new Object[]{this.conf, auth});
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (InvocationTargetException e3) {
            throw new AssertionError(e3);
        }
    }

    public static Twitter getSingleton() {
        return SINGLETON;
    }
}
