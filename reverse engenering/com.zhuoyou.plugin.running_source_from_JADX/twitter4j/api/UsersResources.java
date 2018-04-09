package twitter4j.api;

import twitter4j.TwitterException;
import twitter4j.User;

public interface UsersResources {
    User showUser(long j) throws TwitterException;
}
