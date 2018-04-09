package twitter4j.conf;

public final class ConfigurationBuilder {
    private ConfigurationBase configurationBean = new PropertyConfiguration();

    public ConfigurationBuilder setUser(String user) {
        checkNotBuilt();
        this.configurationBean.setUser(user);
        return this;
    }

    public ConfigurationBuilder setOAuthConsumerKey(String oAuthConsumerKey) {
        checkNotBuilt();
        this.configurationBean.setOAuthConsumerKey(oAuthConsumerKey);
        return this;
    }

    public ConfigurationBuilder setOAuthConsumerSecret(String oAuthConsumerSecret) {
        checkNotBuilt();
        this.configurationBean.setOAuthConsumerSecret(oAuthConsumerSecret);
        return this;
    }

    public Configuration build() {
        checkNotBuilt();
        this.configurationBean.cacheInstance();
        try {
            Configuration configuration = this.configurationBean;
            return configuration;
        } finally {
            this.configurationBean = null;
        }
    }

    private void checkNotBuilt() {
        if (this.configurationBean == null) {
            throw new IllegalStateException("Cannot use this builder any longer, build() has already been called");
        }
    }
}
