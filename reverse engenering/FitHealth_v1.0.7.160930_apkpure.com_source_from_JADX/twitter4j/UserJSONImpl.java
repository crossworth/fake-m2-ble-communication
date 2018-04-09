package twitter4j;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import twitter4j.conf.Configuration;

final class UserJSONImpl extends TwitterResponseImpl implements User, Serializable {
    private static final long serialVersionUID = -5448266606847617015L;
    private Date createdAt;
    private String description;
    private URLEntity[] descriptionURLEntities;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long id;
    private boolean isContributorsEnabled;
    private boolean isDefaultProfile;
    private boolean isDefaultProfileImage;
    private boolean isFollowRequestSent;
    private boolean isGeoEnabled;
    private boolean isProtected;
    private boolean isVerified;
    private String lang;
    private int listedCount;
    private String location;
    private String name;
    private String profileBackgroundColor;
    private String profileBackgroundImageUrl;
    private String profileBackgroundImageUrlHttps;
    private boolean profileBackgroundTiled;
    private String profileBannerImageUrl;
    private String profileImageUrl;
    private String profileImageUrlHttps;
    private String profileLinkColor;
    private String profileSidebarBorderColor;
    private String profileSidebarFillColor;
    private String profileTextColor;
    private boolean profileUseBackgroundImage;
    private String screenName;
    private boolean showAllInlineMedia;
    private Status status;
    private int statusesCount;
    private String timeZone;
    private boolean translator;
    private String url;
    private URLEntity urlEntity;
    private int utcOffset;
    private String[] withheldInCountries;

    UserJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    UserJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    UserJSONImpl() {
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            this.id = ParseUtil.getLong("id", json);
            this.name = ParseUtil.getRawString("name", json);
            this.screenName = ParseUtil.getRawString("screen_name", json);
            this.location = ParseUtil.getRawString("location", json);
            this.descriptionURLEntities = getURLEntitiesFromJSON(json, "description");
            URLEntity[] urlEntities = getURLEntitiesFromJSON(json, "url");
            if (urlEntities.length > 0) {
                this.urlEntity = urlEntities[0];
            }
            this.description = ParseUtil.getRawString("description", json);
            if (this.description != null) {
                this.description = HTMLEntity.unescapeAndSlideEntityIncdices(this.description, null, this.descriptionURLEntities, null, null);
            }
            this.isContributorsEnabled = ParseUtil.getBoolean("contributors_enabled", json);
            this.profileImageUrl = ParseUtil.getRawString(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_ICON, json);
            this.profileImageUrlHttps = ParseUtil.getRawString("profile_image_url_https", json);
            this.isDefaultProfileImage = ParseUtil.getBoolean("default_profile_image", json);
            this.url = ParseUtil.getRawString("url", json);
            this.isProtected = ParseUtil.getBoolean("protected", json);
            this.isGeoEnabled = ParseUtil.getBoolean("geo_enabled", json);
            this.isVerified = ParseUtil.getBoolean("verified", json);
            this.translator = ParseUtil.getBoolean("is_translator", json);
            this.followersCount = ParseUtil.getInt("followers_count", json);
            this.profileBackgroundColor = ParseUtil.getRawString("profile_background_color", json);
            this.profileTextColor = ParseUtil.getRawString("profile_text_color", json);
            this.profileLinkColor = ParseUtil.getRawString("profile_link_color", json);
            this.profileSidebarFillColor = ParseUtil.getRawString("profile_sidebar_fill_color", json);
            this.profileSidebarBorderColor = ParseUtil.getRawString("profile_sidebar_border_color", json);
            this.profileUseBackgroundImage = ParseUtil.getBoolean("profile_use_background_image", json);
            this.isDefaultProfile = ParseUtil.getBoolean("default_profile", json);
            this.showAllInlineMedia = ParseUtil.getBoolean("show_all_inline_media", json);
            this.friendsCount = ParseUtil.getInt("friends_count", json);
            this.createdAt = ParseUtil.getDate(SessionColumns.CREATED_AT, json, "EEE MMM dd HH:mm:ss z yyyy");
            this.favouritesCount = ParseUtil.getInt("favourites_count", json);
            this.utcOffset = ParseUtil.getInt("utc_offset", json);
            this.timeZone = ParseUtil.getRawString("time_zone", json);
            this.profileBackgroundImageUrl = ParseUtil.getRawString("profile_background_image_url", json);
            this.profileBackgroundImageUrlHttps = ParseUtil.getRawString("profile_background_image_url_https", json);
            this.profileBannerImageUrl = ParseUtil.getRawString("profile_banner_url", json);
            this.profileBackgroundTiled = ParseUtil.getBoolean("profile_background_tile", json);
            this.lang = ParseUtil.getRawString("lang", json);
            this.statusesCount = ParseUtil.getInt("statuses_count", json);
            this.listedCount = ParseUtil.getInt("listed_count", json);
            this.isFollowRequestSent = ParseUtil.getBoolean("follow_request_sent", json);
            if (!json.isNull("status")) {
                this.status = new StatusJSONImpl(json.getJSONObject("status"));
            }
            if (!json.isNull("withheld_in_countries")) {
                JSONArray withheld_in_countries = json.getJSONArray("withheld_in_countries");
                int length = withheld_in_countries.length();
                this.withheldInCountries = new String[length];
                for (int i = 0; i < length; i++) {
                    this.withheldInCountries[i] = withheld_in_countries.getString(i);
                }
            }
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    private static URLEntity[] getURLEntitiesFromJSON(JSONObject json, String category) throws JSONException, TwitterException {
        if (!json.isNull("entities")) {
            JSONObject entitiesJSON = json.getJSONObject("entities");
            if (!entitiesJSON.isNull(category)) {
                JSONObject descriptionEntitiesJSON = entitiesJSON.getJSONObject(category);
                if (!descriptionEntitiesJSON.isNull("urls")) {
                    JSONArray urlsArray = descriptionEntitiesJSON.getJSONArray("urls");
                    int len = urlsArray.length();
                    URLEntity[] uRLEntityArr = new URLEntity[len];
                    for (int i = 0; i < len; i++) {
                        uRLEntityArr[i] = new URLEntityJSONImpl(urlsArray.getJSONObject(i));
                    }
                    return uRLEntityArr;
                }
            }
        }
        return new URLEntity[0];
    }

    public int compareTo(User that) {
        return (int) (this.id - that.getId());
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isContributorsEnabled() {
        return this.isContributorsEnabled;
    }

    public String getProfileImageURL() {
        return this.profileImageUrl;
    }

    public String getBiggerProfileImageURL() {
        return toResizedURL(this.profileImageUrl, "_bigger");
    }

    public String getMiniProfileImageURL() {
        return toResizedURL(this.profileImageUrl, "_mini");
    }

    public String getOriginalProfileImageURL() {
        return toResizedURL(this.profileImageUrl, "");
    }

    private String toResizedURL(String originalURL, String sizeSuffix) {
        if (originalURL == null) {
            return null;
        }
        int index = originalURL.lastIndexOf("_");
        int suffixIndex = originalURL.lastIndexOf(".");
        int slashIndex = originalURL.lastIndexOf("/");
        String url = originalURL.substring(0, index) + sizeSuffix;
        if (suffixIndex > slashIndex) {
            return url + originalURL.substring(suffixIndex);
        }
        return url;
    }

    public String getProfileImageURLHttps() {
        return this.profileImageUrlHttps;
    }

    public String getBiggerProfileImageURLHttps() {
        return toResizedURL(this.profileImageUrlHttps, "_bigger");
    }

    public String getMiniProfileImageURLHttps() {
        return toResizedURL(this.profileImageUrlHttps, "_mini");
    }

    public String getOriginalProfileImageURLHttps() {
        return toResizedURL(this.profileImageUrlHttps, "");
    }

    public boolean isDefaultProfileImage() {
        return this.isDefaultProfileImage;
    }

    public String getURL() {
        return this.url;
    }

    public boolean isProtected() {
        return this.isProtected;
    }

    public int getFollowersCount() {
        return this.followersCount;
    }

    public String getProfileBackgroundColor() {
        return this.profileBackgroundColor;
    }

    public String getProfileTextColor() {
        return this.profileTextColor;
    }

    public String getProfileLinkColor() {
        return this.profileLinkColor;
    }

    public String getProfileSidebarFillColor() {
        return this.profileSidebarFillColor;
    }

    public String getProfileSidebarBorderColor() {
        return this.profileSidebarBorderColor;
    }

    public boolean isProfileUseBackgroundImage() {
        return this.profileUseBackgroundImage;
    }

    public boolean isDefaultProfile() {
        return this.isDefaultProfile;
    }

    public boolean isShowAllInlineMedia() {
        return this.showAllInlineMedia;
    }

    public int getFriendsCount() {
        return this.friendsCount;
    }

    public Status getStatus() {
        return this.status;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public int getFavouritesCount() {
        return this.favouritesCount;
    }

    public int getUtcOffset() {
        return this.utcOffset;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public String getProfileBackgroundImageURL() {
        return this.profileBackgroundImageUrl;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return this.profileBackgroundImageUrlHttps;
    }

    public String getProfileBannerURL() {
        return this.profileBannerImageUrl != null ? this.profileBannerImageUrl + "/web" : null;
    }

    public String getProfileBannerRetinaURL() {
        return this.profileBannerImageUrl != null ? this.profileBannerImageUrl + "/web_retina" : null;
    }

    public String getProfileBannerIPadURL() {
        return this.profileBannerImageUrl != null ? this.profileBannerImageUrl + "/ipad" : null;
    }

    public String getProfileBannerIPadRetinaURL() {
        return this.profileBannerImageUrl != null ? this.profileBannerImageUrl + "/ipad_retina" : null;
    }

    public String getProfileBannerMobileURL() {
        return this.profileBannerImageUrl != null ? this.profileBannerImageUrl + "/mobile" : null;
    }

    public String getProfileBannerMobileRetinaURL() {
        return this.profileBannerImageUrl != null ? this.profileBannerImageUrl + "/mobile_retina" : null;
    }

    public boolean isProfileBackgroundTiled() {
        return this.profileBackgroundTiled;
    }

    public String getLang() {
        return this.lang;
    }

    public int getStatusesCount() {
        return this.statusesCount;
    }

    public boolean isGeoEnabled() {
        return this.isGeoEnabled;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    public boolean isTranslator() {
        return this.translator;
    }

    public int getListedCount() {
        return this.listedCount;
    }

    public boolean isFollowRequestSent() {
        return this.isFollowRequestSent;
    }

    public URLEntity[] getDescriptionURLEntities() {
        return this.descriptionURLEntities;
    }

    public URLEntity getURLEntity() {
        if (this.urlEntity == null) {
            String plainURL = this.url == null ? "" : this.url;
            this.urlEntity = new URLEntityJSONImpl(0, plainURL.length(), plainURL, plainURL, plainURL);
        }
        return this.urlEntity;
    }

    public String[] getWithheldInCountries() {
        return this.withheldInCountries;
    }

    static PagableResponseList<User> createPagableUserList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONObject json = res.asJSONObject();
            JSONArray list = json.getJSONArray("users");
            int size = list.length();
            PagableResponseList<User> users = new PagableResponseListImpl(size, json, res);
            for (int i = 0; i < size; i++) {
                JSONObject userJson = list.getJSONObject(i);
                User user = new UserJSONImpl(userJson);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(user, userJson);
                }
                users.add(user);
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(users, json);
            }
            return users;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    static ResponseList<User> createUserList(HttpResponse res, Configuration conf) throws TwitterException {
        return createUserList(res.asJSONArray(), res, conf);
    }

    static ResponseList<User> createUserList(JSONArray list, HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            int size = list.length();
            ResponseList<User> users = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                User user = new UserJSONImpl(json);
                users.add(user);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(user, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(users, list);
            }
            return users;
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
        if ((obj instanceof User) && ((User) obj).getId() == this.id) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "UserJSONImpl{id=" + this.id + ", name='" + this.name + '\'' + ", screenName='" + this.screenName + '\'' + ", location='" + this.location + '\'' + ", description='" + this.description + '\'' + ", isContributorsEnabled=" + this.isContributorsEnabled + ", profileImageUrl='" + this.profileImageUrl + '\'' + ", profileImageUrlHttps='" + this.profileImageUrlHttps + '\'' + ", isDefaultProfileImage=" + this.isDefaultProfileImage + ", url='" + this.url + '\'' + ", isProtected=" + this.isProtected + ", followersCount=" + this.followersCount + ", status=" + this.status + ", profileBackgroundColor='" + this.profileBackgroundColor + '\'' + ", profileTextColor='" + this.profileTextColor + '\'' + ", profileLinkColor='" + this.profileLinkColor + '\'' + ", profileSidebarFillColor='" + this.profileSidebarFillColor + '\'' + ", profileSidebarBorderColor='" + this.profileSidebarBorderColor + '\'' + ", profileUseBackgroundImage=" + this.profileUseBackgroundImage + ", isDefaultProfile=" + this.isDefaultProfile + ", showAllInlineMedia=" + this.showAllInlineMedia + ", friendsCount=" + this.friendsCount + ", createdAt=" + this.createdAt + ", favouritesCount=" + this.favouritesCount + ", utcOffset=" + this.utcOffset + ", timeZone='" + this.timeZone + '\'' + ", profileBackgroundImageUrl='" + this.profileBackgroundImageUrl + '\'' + ", profileBackgroundImageUrlHttps='" + this.profileBackgroundImageUrlHttps + '\'' + ", profileBackgroundTiled=" + this.profileBackgroundTiled + ", lang='" + this.lang + '\'' + ", statusesCount=" + this.statusesCount + ", isGeoEnabled=" + this.isGeoEnabled + ", isVerified=" + this.isVerified + ", translator=" + this.translator + ", listedCount=" + this.listedCount + ", isFollowRequestSent=" + this.isFollowRequestSent + ", withheldInCountries=" + Arrays.toString(this.withheldInCountries) + '}';
    }
}
