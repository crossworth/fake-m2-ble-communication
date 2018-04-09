package twitter4j;

import com.tencent.open.SocialConstants;
import com.umeng.socialize.editorpage.ShareActivity;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import twitter4j.conf.Configuration;

final class StatusJSONImpl extends TwitterResponseImpl implements Status, Serializable {
    private static final Logger logger = Logger.getLogger(StatusJSONImpl.class);
    private static final long serialVersionUID = -6461195536943679985L;
    private long[] contributorsIDs;
    private Date createdAt;
    private long currentUserRetweetId = -1;
    private ExtendedMediaEntity[] extendedMediaEntities;
    private int favoriteCount;
    private GeoLocation geoLocation = null;
    private HashtagEntity[] hashtagEntities;
    private long id;
    private String inReplyToScreenName;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private boolean isFavorited;
    private boolean isPossiblySensitive;
    private boolean isRetweeted;
    private boolean isTruncated;
    private String lang;
    private MediaEntity[] mediaEntities;
    private Place place = null;
    private Status quotedStatus;
    private long quotedStatusId = -1;
    private long retweetCount;
    private Status retweetedStatus;
    private Scopes scopes;
    private String source;
    private SymbolEntity[] symbolEntities;
    private String text;
    private URLEntity[] urlEntities;
    private User user = null;
    private UserMentionEntity[] userMentionEntities;
    private String[] withheldInCountries = null;

    StatusJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    StatusJSONImpl(JSONObject json, Configuration conf) throws TwitterException {
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    StatusJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    StatusJSONImpl() {
    }

    private void init(JSONObject json) throws TwitterException {
        this.id = ParseUtil.getLong("id", json);
        this.source = ParseUtil.getUnescapedString(SocialConstants.PARAM_SOURCE, json);
        this.createdAt = ParseUtil.getDate(SessionColumns.CREATED_AT, json);
        this.isTruncated = ParseUtil.getBoolean("truncated", json);
        this.inReplyToStatusId = ParseUtil.getLong("in_reply_to_status_id", json);
        this.inReplyToUserId = ParseUtil.getLong("in_reply_to_user_id", json);
        this.isFavorited = ParseUtil.getBoolean("favorited", json);
        this.isRetweeted = ParseUtil.getBoolean("retweeted", json);
        this.inReplyToScreenName = ParseUtil.getUnescapedString("in_reply_to_screen_name", json);
        this.retweetCount = ParseUtil.getLong("retweet_count", json);
        this.favoriteCount = ParseUtil.getInt("favorite_count", json);
        this.isPossiblySensitive = ParseUtil.getBoolean("possibly_sensitive", json);
        try {
            int i;
            int len;
            JSONArray mediaArray;
            ExtendedMediaEntity[] extendedMediaEntityArr;
            if (!json.isNull("user")) {
                this.user = new UserJSONImpl(json.getJSONObject("user"));
            }
            this.geoLocation = JSONImplFactory.createGeoLocation(json);
            if (!json.isNull("place")) {
                this.place = new PlaceJSONImpl(json.getJSONObject("place"));
            }
            if (!json.isNull("retweeted_status")) {
                this.retweetedStatus = new StatusJSONImpl(json.getJSONObject("retweeted_status"));
            }
            if (json.isNull("contributors")) {
                this.contributorsIDs = new long[0];
            } else {
                JSONArray contributorsArray = json.getJSONArray("contributors");
                this.contributorsIDs = new long[contributorsArray.length()];
                for (i = 0; i < contributorsArray.length(); i++) {
                    this.contributorsIDs[i] = Long.parseLong(contributorsArray.getString(i));
                }
            }
            if (!json.isNull("entities")) {
                JSONArray hashtagsArray;
                JSONObject entities = json.getJSONObject("entities");
                if (!entities.isNull("user_mentions")) {
                    JSONArray userMentionsArray = entities.getJSONArray("user_mentions");
                    len = userMentionsArray.length();
                    this.userMentionEntities = new UserMentionEntity[len];
                    for (i = 0; i < len; i++) {
                        this.userMentionEntities[i] = new UserMentionEntityJSONImpl(userMentionsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("urls")) {
                    JSONArray urlsArray = entities.getJSONArray("urls");
                    len = urlsArray.length();
                    this.urlEntities = new URLEntity[len];
                    for (i = 0; i < len; i++) {
                        this.urlEntities[i] = new URLEntityJSONImpl(urlsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("hashtags")) {
                    hashtagsArray = entities.getJSONArray("hashtags");
                    len = hashtagsArray.length();
                    this.hashtagEntities = new HashtagEntity[len];
                    for (i = 0; i < len; i++) {
                        this.hashtagEntities[i] = new HashtagEntityJSONImpl(hashtagsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("symbols")) {
                    hashtagsArray = entities.getJSONArray("symbols");
                    len = hashtagsArray.length();
                    this.symbolEntities = new SymbolEntity[len];
                    for (i = 0; i < len; i++) {
                        this.symbolEntities[i] = new HashtagEntityJSONImpl(hashtagsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull(ShareActivity.KEY_PLATFORM)) {
                    mediaArray = entities.getJSONArray(ShareActivity.KEY_PLATFORM);
                    len = mediaArray.length();
                    this.mediaEntities = new MediaEntity[len];
                    for (i = 0; i < len; i++) {
                        this.mediaEntities[i] = new MediaEntityJSONImpl(mediaArray.getJSONObject(i));
                    }
                }
            }
            if (!json.isNull("extended_entities")) {
                JSONObject extendedEntities = json.getJSONObject("extended_entities");
                if (!extendedEntities.isNull(ShareActivity.KEY_PLATFORM)) {
                    mediaArray = extendedEntities.getJSONArray(ShareActivity.KEY_PLATFORM);
                    len = mediaArray.length();
                    this.extendedMediaEntities = new ExtendedMediaEntity[len];
                    for (i = 0; i < len; i++) {
                        this.extendedMediaEntities[i] = new ExtendedMediaEntityJSONImpl(mediaArray.getJSONObject(i));
                    }
                }
            }
            if (!json.isNull("quoted_status")) {
                this.quotedStatus = new StatusJSONImpl(json.getJSONObject("quoted_status"));
            }
            if (!json.isNull("quoted_status_id")) {
                this.quotedStatusId = ParseUtil.getLong("quoted_status_id", json);
            }
            if (!json.isNull("quoted_status")) {
                this.quotedStatus = new StatusJSONImpl(json.getJSONObject("quoted_status"));
            }
            if (!json.isNull("quoted_status_id")) {
                this.quotedStatusId = ParseUtil.getLong("quoted_status_id", json);
            }
            this.userMentionEntities = this.userMentionEntities == null ? new UserMentionEntity[0] : this.userMentionEntities;
            this.urlEntities = this.urlEntities == null ? new URLEntity[0] : this.urlEntities;
            this.hashtagEntities = this.hashtagEntities == null ? new HashtagEntity[0] : this.hashtagEntities;
            this.symbolEntities = this.symbolEntities == null ? new SymbolEntity[0] : this.symbolEntities;
            this.mediaEntities = this.mediaEntities == null ? new MediaEntity[0] : this.mediaEntities;
            if (this.extendedMediaEntities == null) {
                extendedMediaEntityArr = new ExtendedMediaEntity[0];
            } else {
                extendedMediaEntityArr = this.extendedMediaEntities;
            }
            this.extendedMediaEntities = extendedMediaEntityArr;
            this.text = HTMLEntity.unescapeAndSlideEntityIncdices(json.getString(MessageObj.SUBTYPE_NOTI), this.userMentionEntities, this.urlEntities, this.hashtagEntities, this.mediaEntities);
            if (!json.isNull("current_user_retweet")) {
                this.currentUserRetweetId = json.getJSONObject("current_user_retweet").getLong("id");
            }
            if (!json.isNull("lang")) {
                this.lang = ParseUtil.getUnescapedString("lang", json);
            }
            if (!json.isNull("scopes")) {
                JSONObject scopesJson = json.getJSONObject("scopes");
                if (!scopesJson.isNull("place_ids")) {
                    JSONArray placeIdsArray = scopesJson.getJSONArray("place_ids");
                    len = placeIdsArray.length();
                    String[] placeIds = new String[len];
                    for (i = 0; i < len; i++) {
                        placeIds[i] = placeIdsArray.getString(i);
                    }
                    this.scopes = new ScopesImpl(placeIds);
                }
            }
            if (!json.isNull("withheld_in_countries")) {
                JSONArray withheld_in_countries = json.getJSONArray("withheld_in_countries");
                int length = withheld_in_countries.length();
                this.withheldInCountries = new String[length];
                for (i = 0; i < length; i++) {
                    this.withheldInCountries[i] = withheld_in_countries.getString(i);
                }
            }
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public int compareTo(Status that) {
        long delta = this.id - that.getId();
        if (delta < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        if (delta > 2147483647L) {
            return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        return (int) delta;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public String getSource() {
        return this.source;
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public long getInReplyToStatusId() {
        return this.inReplyToStatusId;
    }

    public long getInReplyToUserId() {
        return this.inReplyToUserId;
    }

    public String getInReplyToScreenName() {
        return this.inReplyToScreenName;
    }

    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    public Place getPlace() {
        return this.place;
    }

    public long[] getContributors() {
        return this.contributorsIDs;
    }

    public boolean isFavorited() {
        return this.isFavorited;
    }

    public boolean isRetweeted() {
        return this.isRetweeted;
    }

    public int getFavoriteCount() {
        return this.favoriteCount;
    }

    public User getUser() {
        return this.user;
    }

    public boolean isRetweet() {
        return this.retweetedStatus != null;
    }

    public Status getRetweetedStatus() {
        return this.retweetedStatus;
    }

    public int getRetweetCount() {
        return (int) this.retweetCount;
    }

    public boolean isRetweetedByMe() {
        return this.currentUserRetweetId != -1;
    }

    public long getCurrentUserRetweetId() {
        return this.currentUserRetweetId;
    }

    public boolean isPossiblySensitive() {
        return this.isPossiblySensitive;
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return this.userMentionEntities;
    }

    public URLEntity[] getURLEntities() {
        return this.urlEntities;
    }

    public HashtagEntity[] getHashtagEntities() {
        return this.hashtagEntities;
    }

    public MediaEntity[] getMediaEntities() {
        return this.mediaEntities;
    }

    public ExtendedMediaEntity[] getExtendedMediaEntities() {
        return this.extendedMediaEntities;
    }

    public SymbolEntity[] getSymbolEntities() {
        return this.symbolEntities;
    }

    public Scopes getScopes() {
        return this.scopes;
    }

    public String[] getWithheldInCountries() {
        return this.withheldInCountries;
    }

    public long getQuotedStatusId() {
        return this.quotedStatusId;
    }

    public Status getQuotedStatus() {
        return this.quotedStatus;
    }

    public String getLang() {
        return this.lang;
    }

    static ResponseList<Status> createStatusList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<Status> statuses = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Status status = new StatusJSONImpl(json);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(status, json);
                }
                statuses.add(status);
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(statuses, list);
            }
            return statuses;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public int hashCode() {
        return (int) this.id;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Status) && ((Status) obj).getId() == this.id) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "StatusJSONImpl{createdAt=" + this.createdAt + ", id=" + this.id + ", text='" + this.text + '\'' + ", source='" + this.source + '\'' + ", isTruncated=" + this.isTruncated + ", inReplyToStatusId=" + this.inReplyToStatusId + ", inReplyToUserId=" + this.inReplyToUserId + ", isFavorited=" + this.isFavorited + ", isRetweeted=" + this.isRetweeted + ", favoriteCount=" + this.favoriteCount + ", inReplyToScreenName='" + this.inReplyToScreenName + '\'' + ", geoLocation=" + this.geoLocation + ", place=" + this.place + ", retweetCount=" + this.retweetCount + ", isPossiblySensitive=" + this.isPossiblySensitive + ", lang='" + this.lang + '\'' + ", contributorsIDs=" + Arrays.toString(this.contributorsIDs) + ", retweetedStatus=" + this.retweetedStatus + ", userMentionEntities=" + Arrays.toString(this.userMentionEntities) + ", urlEntities=" + Arrays.toString(this.urlEntities) + ", hashtagEntities=" + Arrays.toString(this.hashtagEntities) + ", mediaEntities=" + Arrays.toString(this.mediaEntities) + ", symbolEntities=" + Arrays.toString(this.symbolEntities) + ", currentUserRetweetId=" + this.currentUserRetweetId + ", user=" + this.user + ", withHeldInCountries=" + Arrays.toString(this.withheldInCountries) + ", quotedStatusId=" + this.quotedStatusId + ", quotedStatus=" + this.quotedStatus + '}';
    }
}
