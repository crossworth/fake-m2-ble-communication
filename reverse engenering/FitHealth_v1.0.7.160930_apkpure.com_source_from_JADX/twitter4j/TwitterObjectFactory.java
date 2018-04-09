package twitter4j;

import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.util.HashMap;
import java.util.Map;

public final class TwitterObjectFactory {
    private static final ThreadLocal<Map> rawJsonMap = new C14571();
    private static boolean registeredAtleastOnce = false;

    static class C14571 extends ThreadLocal<Map> {
        C14571() {
        }

        protected Map initialValue() {
            return new HashMap();
        }
    }

    private TwitterObjectFactory() {
        throw new AssertionError("not intended to be instantiated.");
    }

    public static String getRawJSON(Object obj) {
        if (registeredAtleastOnce) {
            Object json = ((Map) rawJsonMap.get()).get(obj);
            if (json instanceof String) {
                return (String) json;
            }
            if (json != null) {
                return json.toString();
            }
            return null;
        }
        throw new IllegalStateException("Apparently jsonStoreEnabled is not set to true.");
    }

    public static Status createStatus(String rawJSON) throws TwitterException {
        try {
            return new StatusJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static User createUser(String rawJSON) throws TwitterException {
        try {
            return new UserJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static AccountTotals createAccountTotals(String rawJSON) throws TwitterException {
        try {
            return new AccountTotalsJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Relationship createRelationship(String rawJSON) throws TwitterException {
        try {
            return new RelationshipJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Place createPlace(String rawJSON) throws TwitterException {
        try {
            return new PlaceJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static SavedSearch createSavedSearch(String rawJSON) throws TwitterException {
        try {
            return new SavedSearchJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Trend createTrend(String rawJSON) throws TwitterException {
        try {
            return new TrendJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Trends createTrends(String rawJSON) throws TwitterException {
        return new TrendsJSONImpl(rawJSON);
    }

    public static IDs createIDs(String rawJSON) throws TwitterException {
        return new IDsJSONImpl(rawJSON);
    }

    public static Map<String, RateLimitStatus> createRateLimitStatus(String rawJSON) throws TwitterException {
        try {
            return RateLimitStatusJSONImpl.createRateLimitStatuses(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Category createCategory(String rawJSON) throws TwitterException {
        try {
            return new CategoryJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static DirectMessage createDirectMessage(String rawJSON) throws TwitterException {
        try {
            return new DirectMessageJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Location createLocation(String rawJSON) throws TwitterException {
        try {
            return new LocationJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static UserList createUserList(String rawJSON) throws TwitterException {
        try {
            return new UserListJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static OEmbed createOEmbed(String rawJSON) throws TwitterException {
        try {
            return new OEmbedJSONImpl(new JSONObject(rawJSON));
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public static Object createObject(String rawJSON) throws TwitterException {
        try {
            JSONObject json = new JSONObject(rawJSON);
            switch (JSONObjectType.determine(json)) {
                case SENDER:
                    return registerJSONObject(new DirectMessageJSONImpl(json.getJSONObject("direct_message")), json);
                case STATUS:
                    return registerJSONObject(new StatusJSONImpl(json), json);
                case DIRECT_MESSAGE:
                    return registerJSONObject(new DirectMessageJSONImpl(json.getJSONObject("direct_message")), json);
                case DELETE:
                    return registerJSONObject(new StatusDeletionNoticeImpl(json.getJSONObject(MessageObj.ACTION_DEL).getJSONObject("status")), json);
                default:
                    return json;
            }
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    static void clearThreadLocalMap() {
        ((Map) rawJsonMap.get()).clear();
    }

    static <T> T registerJSONObject(T key, Object json) {
        registeredAtleastOnce = true;
        ((Map) rawJsonMap.get()).put(key, json);
        return key;
    }
}
