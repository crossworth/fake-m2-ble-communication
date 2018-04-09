package twitter4j.api;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

public interface TweetsResources {
    Status updateStatus(String str) throws TwitterException;

    Status updateStatus(StatusUpdate statusUpdate) throws TwitterException;
}
