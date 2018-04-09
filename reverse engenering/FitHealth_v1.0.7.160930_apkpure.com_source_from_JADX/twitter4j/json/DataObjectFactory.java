package twitter4j.json;

import java.util.Map;
import twitter4j.AccountTotals;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.OEmbed;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;
import twitter4j.UserList;

public final class DataObjectFactory {
    private DataObjectFactory() {
        throw new AssertionError("not intended to be instantiated.");
    }

    public static String getRawJSON(Object obj) {
        return TwitterObjectFactory.getRawJSON(obj);
    }

    public static Status createStatus(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createStatus(rawJSON);
    }

    public static User createUser(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createUser(rawJSON);
    }

    public static AccountTotals createAccountTotals(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createAccountTotals(rawJSON);
    }

    public static Relationship createRelationship(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createRelationship(rawJSON);
    }

    public static Place createPlace(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createPlace(rawJSON);
    }

    public static SavedSearch createSavedSearch(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createSavedSearch(rawJSON);
    }

    public static Trend createTrend(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createTrend(rawJSON);
    }

    public static Trends createTrends(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createTrends(rawJSON);
    }

    public static IDs createIDs(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createIDs(rawJSON);
    }

    public static Map<String, RateLimitStatus> createRateLimitStatus(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createRateLimitStatus(rawJSON);
    }

    public static Category createCategory(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createCategory(rawJSON);
    }

    public static DirectMessage createDirectMessage(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createDirectMessage(rawJSON);
    }

    public static Location createLocation(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createLocation(rawJSON);
    }

    public static UserList createUserList(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createUserList(rawJSON);
    }

    public static OEmbed createOEmbed(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createOEmbed(rawJSON);
    }

    public static Object createObject(String rawJSON) throws TwitterException {
        return TwitterObjectFactory.createObject(rawJSON);
    }
}
