package twitter4j;

import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.Map;
import twitter4j.api.HelpResources.Language;
import twitter4j.conf.Configuration;

class JSONImplFactory implements ObjectFactory {
    private static final long serialVersionUID = -1853541456182663343L;
    private final Configuration conf;

    public JSONImplFactory(Configuration conf) {
        this.conf = conf;
    }

    public Status createStatus(JSONObject json) throws TwitterException {
        return new StatusJSONImpl(json);
    }

    public User createUser(JSONObject json) throws TwitterException {
        return new UserJSONImpl(json);
    }

    public UserList createAUserList(JSONObject json) throws TwitterException {
        return new UserListJSONImpl(json);
    }

    public Map<String, RateLimitStatus> createRateLimitStatuses(HttpResponse res) throws TwitterException {
        return RateLimitStatusJSONImpl.createRateLimitStatuses(res, this.conf);
    }

    public Status createStatus(HttpResponse res) throws TwitterException {
        return new StatusJSONImpl(res, this.conf);
    }

    public ResponseList<Status> createStatusList(HttpResponse res) throws TwitterException {
        return StatusJSONImpl.createStatusList(res, this.conf);
    }

    static GeoLocation createGeoLocation(JSONObject json) throws TwitterException {
        try {
            if (json.isNull("coordinates")) {
                return null;
            }
            String coordinates = json.getJSONObject("coordinates").getString("coordinates");
            String[] point = coordinates.substring(1, coordinates.length() - 1).split(SeparatorConstants.SEPARATOR_ADS_ID);
            return new GeoLocation(Double.parseDouble(point[1]), Double.parseDouble(point[0]));
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    static GeoLocation[][] coordinatesAsGeoLocationArray(JSONArray coordinates) throws TwitterException {
        try {
            GeoLocation[][] boundingBox = new GeoLocation[coordinates.length()][];
            for (int i = 0; i < coordinates.length(); i++) {
                JSONArray array = coordinates.getJSONArray(i);
                boundingBox[i] = new GeoLocation[array.length()];
                for (int j = 0; j < array.length(); j++) {
                    JSONArray coordinate = array.getJSONArray(j);
                    boundingBox[i][j] = new GeoLocation(coordinate.getDouble(1), coordinate.getDouble(0));
                }
            }
            return boundingBox;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public static RateLimitStatus createRateLimitStatusFromResponseHeader(HttpResponse res) {
        return RateLimitStatusJSONImpl.createFromResponseHeader(res);
    }

    public Trends createTrends(HttpResponse res) throws TwitterException {
        return new TrendsJSONImpl(res, this.conf);
    }

    public User createUser(HttpResponse res) throws TwitterException {
        return new UserJSONImpl(res, this.conf);
    }

    public ResponseList<User> createUserList(HttpResponse res) throws TwitterException {
        return UserJSONImpl.createUserList(res, this.conf);
    }

    public ResponseList<User> createUserListFromJSONArray(HttpResponse res) throws TwitterException {
        return UserJSONImpl.createUserList(res.asJSONArray(), res, this.conf);
    }

    public ResponseList<User> createUserListFromJSONArray_Users(HttpResponse res) throws TwitterException {
        try {
            return UserJSONImpl.createUserList(res.asJSONObject().getJSONArray("users"), res, this.conf);
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public QueryResult createQueryResult(HttpResponse res, Query query) throws TwitterException {
        try {
            return new QueryResultJSONImpl(res, this.conf);
        } catch (TwitterException te) {
            if (404 == te.getStatusCode()) {
                return new QueryResultJSONImpl(query);
            }
            throw te;
        }
    }

    public IDs createIDs(HttpResponse res) throws TwitterException {
        return new IDsJSONImpl(res, this.conf);
    }

    public PagableResponseList<User> createPagableUserList(HttpResponse res) throws TwitterException {
        return UserJSONImpl.createPagableUserList(res, this.conf);
    }

    public UserList createAUserList(HttpResponse res) throws TwitterException {
        return new UserListJSONImpl(res, this.conf);
    }

    public PagableResponseList<UserList> createPagableUserListList(HttpResponse res) throws TwitterException {
        return UserListJSONImpl.createPagableUserListList(res, this.conf);
    }

    public ResponseList<UserList> createUserListList(HttpResponse res) throws TwitterException {
        return UserListJSONImpl.createUserListList(res, this.conf);
    }

    public ResponseList<Category> createCategoryList(HttpResponse res) throws TwitterException {
        return CategoryJSONImpl.createCategoriesList(res, this.conf);
    }

    public DirectMessage createDirectMessage(HttpResponse res) throws TwitterException {
        return new DirectMessageJSONImpl(res, this.conf);
    }

    public ResponseList<DirectMessage> createDirectMessageList(HttpResponse res) throws TwitterException {
        return DirectMessageJSONImpl.createDirectMessageList(res, this.conf);
    }

    public Relationship createRelationship(HttpResponse res) throws TwitterException {
        return new RelationshipJSONImpl(res, this.conf);
    }

    public ResponseList<Friendship> createFriendshipList(HttpResponse res) throws TwitterException {
        return FriendshipJSONImpl.createFriendshipList(res, this.conf);
    }

    public AccountTotals createAccountTotals(HttpResponse res) throws TwitterException {
        return new AccountTotalsJSONImpl(res, this.conf);
    }

    public AccountSettings createAccountSettings(HttpResponse res) throws TwitterException {
        return new AccountSettingsJSONImpl(res, this.conf);
    }

    public SavedSearch createSavedSearch(HttpResponse res) throws TwitterException {
        return new SavedSearchJSONImpl(res, this.conf);
    }

    public ResponseList<SavedSearch> createSavedSearchList(HttpResponse res) throws TwitterException {
        return SavedSearchJSONImpl.createSavedSearchList(res, this.conf);
    }

    public ResponseList<Location> createLocationList(HttpResponse res) throws TwitterException {
        return LocationJSONImpl.createLocationList(res, this.conf);
    }

    public Place createPlace(HttpResponse res) throws TwitterException {
        return new PlaceJSONImpl(res, this.conf);
    }

    public ResponseList<Place> createPlaceList(HttpResponse res) throws TwitterException {
        try {
            return PlaceJSONImpl.createPlaceList(res, this.conf);
        } catch (TwitterException te) {
            if (te.getStatusCode() == 404) {
                return new ResponseListImpl(0, null);
            }
            throw te;
        }
    }

    public TwitterAPIConfiguration createTwitterAPIConfiguration(HttpResponse res) throws TwitterException {
        return new TwitterAPIConfigurationJSONImpl(res, this.conf);
    }

    public ResponseList<Language> createLanguageList(HttpResponse res) throws TwitterException {
        return LanguageJSONImpl.createLanguageList(res, this.conf);
    }

    public <T> ResponseList<T> createEmptyResponseList() {
        return new ResponseListImpl(0, null);
    }

    public OEmbed createOEmbed(HttpResponse res) throws TwitterException {
        return new OEmbedJSONImpl(res, this.conf);
    }

    public static HashtagEntity createHashtagEntity(int start, int end, String text) {
        return new HashtagEntityJSONImpl(start, end, text);
    }

    public static UserMentionEntity createUserMentionEntity(int start, int end, String name, String screenName, long id) {
        return new UserMentionEntityJSONImpl(start, end, name, screenName, id);
    }

    public static URLEntity createUrlEntity(int start, int end, String url, String expandedURL, String displayURL) {
        return new URLEntityJSONImpl(start, end, url, expandedURL, displayURL);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JSONImplFactory)) {
            return false;
        }
        JSONImplFactory that = (JSONImplFactory) o;
        if (this.conf != null) {
            if (this.conf.equals(that.conf)) {
                return true;
            }
        } else if (that.conf == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.conf != null ? this.conf.hashCode() : 0;
    }

    public String toString() {
        return "JSONImplFactory{conf=" + this.conf + '}';
    }
}
