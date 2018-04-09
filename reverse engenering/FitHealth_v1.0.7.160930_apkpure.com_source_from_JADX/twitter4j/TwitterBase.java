package twitter4j;

import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;
import twitter4j.util.function.Consumer;

public interface TwitterBase {
    void addRateLimitStatusListener(RateLimitStatusListener rateLimitStatusListener);

    Authorization getAuthorization();

    Configuration getConfiguration();

    long getId() throws TwitterException, IllegalStateException;

    String getScreenName() throws TwitterException, IllegalStateException;

    void onRateLimitReached(Consumer<RateLimitStatusEvent> consumer);

    void onRateLimitStatus(Consumer<RateLimitStatusEvent> consumer);
}
