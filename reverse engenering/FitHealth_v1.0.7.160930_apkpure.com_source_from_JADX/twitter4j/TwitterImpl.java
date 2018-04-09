package twitter4j;

import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.umeng.socialize.editorpage.ShareActivity;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import p031u.aly.au;
import twitter4j.api.DirectMessagesResources;
import twitter4j.api.FavoritesResources;
import twitter4j.api.FriendsFollowersResources;
import twitter4j.api.HelpResources;
import twitter4j.api.HelpResources.Language;
import twitter4j.api.ListsResources;
import twitter4j.api.PlacesGeoResources;
import twitter4j.api.SavedSearchesResources;
import twitter4j.api.SearchResource;
import twitter4j.api.SpamReportingResource;
import twitter4j.api.SuggestedUsersResources;
import twitter4j.api.TimelinesResources;
import twitter4j.api.TrendsResources;
import twitter4j.api.TweetsResources;
import twitter4j.api.UsersResources;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

class TwitterImpl extends TwitterBaseImpl implements Twitter {
    private static final ConcurrentHashMap<Configuration, HttpParameter[]> implicitParamsMap = new ConcurrentHashMap();
    private static final ConcurrentHashMap<Configuration, String> implicitParamsStrMap = new ConcurrentHashMap();
    private static final long serialVersionUID = 9170943084096085770L;
    private final HttpParameter[] IMPLICIT_PARAMS;
    private final String IMPLICIT_PARAMS_STR;
    private final HttpParameter INCLUDE_MY_RETWEET;

    TwitterImpl(Configuration conf, Authorization auth) {
        super(conf, auth);
        this.INCLUDE_MY_RETWEET = new HttpParameter("include_my_retweet", conf.isIncludeMyRetweetEnabled());
        if (implicitParamsMap.containsKey(conf)) {
            this.IMPLICIT_PARAMS = (HttpParameter[]) implicitParamsMap.get(conf);
            this.IMPLICIT_PARAMS_STR = (String) implicitParamsStrMap.get(conf);
            return;
        }
        String implicitParamsStr = conf.isIncludeEntitiesEnabled() ? "include_entities=true" : "";
        boolean contributorsEnabled = conf.getContributingTo() != -1;
        if (contributorsEnabled) {
            if (!"".equals(implicitParamsStr)) {
                implicitParamsStr = implicitParamsStr + "?";
            }
            implicitParamsStr = implicitParamsStr + "contributingto=" + conf.getContributingTo();
        }
        List<HttpParameter> params = new ArrayList(3);
        if (conf.isIncludeEntitiesEnabled()) {
            params.add(new HttpParameter("include_entities", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE));
        }
        if (contributorsEnabled) {
            params.add(new HttpParameter("contributingto", conf.getContributingTo()));
        }
        if (conf.isTrimUserEnabled()) {
            params.add(new HttpParameter("trim_user", "1"));
        }
        HttpParameter[] implicitParams = (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
        implicitParamsStrMap.putIfAbsent(conf, implicitParamsStr);
        implicitParamsMap.putIfAbsent(conf, implicitParams);
        this.IMPLICIT_PARAMS = implicitParams;
        this.IMPLICIT_PARAMS_STR = implicitParamsStr;
    }

    public ResponseList<Status> getMentionsTimeline() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/mentions_timeline.json"));
    }

    public ResponseList<Status> getMentionsTimeline(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/mentions_timeline.json", paging.asPostParameterArray()));
    }

    public ResponseList<Status> getHomeTimeline() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/home_timeline.json", this.INCLUDE_MY_RETWEET));
    }

    public ResponseList<Status> getHomeTimeline(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/home_timeline.json", mergeParameters(paging.asPostParameterArray(), new HttpParameter[]{this.INCLUDE_MY_RETWEET})));
    }

    public ResponseList<Status> getRetweetsOfMe() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/retweets_of_me.json"));
    }

    public ResponseList<Status> getRetweetsOfMe(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/retweets_of_me.json", paging.asPostParameterArray()));
    }

    public ResponseList<Status> getUserTimeline(String screenName, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/user_timeline.json", mergeParameters(new HttpParameter[]{new HttpParameter("screen_name", screenName), this.INCLUDE_MY_RETWEET}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getUserTimeline(long userId, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/user_timeline.json", mergeParameters(new HttpParameter[]{new HttpParameter("user_id", userId), this.INCLUDE_MY_RETWEET}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getUserTimeline(String screenName) throws TwitterException {
        return getUserTimeline(screenName, new Paging());
    }

    public ResponseList<Status> getUserTimeline(long userId) throws TwitterException {
        return getUserTimeline(userId, new Paging());
    }

    public ResponseList<Status> getUserTimeline() throws TwitterException {
        return getUserTimeline(new Paging());
    }

    public ResponseList<Status> getUserTimeline(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/user_timeline.json", mergeParameters(new HttpParameter[]{this.INCLUDE_MY_RETWEET}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getRetweets(long statusId) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/retweets/" + statusId + ".json?count=100"));
    }

    public IDs getRetweeterIds(long statusId, long cursor) throws TwitterException {
        return getRetweeterIds(statusId, 100, cursor);
    }

    public IDs getRetweeterIds(long statusId, int count, long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "statuses/retweeters/ids.json?id=" + statusId + "&cursor=" + cursor + "&count=" + count));
    }

    public Status showStatus(long id) throws TwitterException {
        return this.factory.createStatus(get(this.conf.getRestBaseURL() + "statuses/show/" + id + ".json", this.INCLUDE_MY_RETWEET));
    }

    public Status destroyStatus(long statusId) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/destroy/" + statusId + ".json"));
    }

    public Status updateStatus(String status) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/update.json", new HttpParameter("status", status)));
    }

    public Status updateStatus(StatusUpdate status) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + (status.isForUpdateWithMedia() ? "statuses/update_with_media.json" : "statuses/update.json"), status.asHttpParameterArray()));
    }

    public Status retweetStatus(long statusId) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/retweet/" + statusId + ".json"));
    }

    public OEmbed getOEmbed(OEmbedRequest req) throws TwitterException {
        return this.factory.createOEmbed(get(this.conf.getRestBaseURL() + "statuses/oembed.json", req.asHttpParameterArray()));
    }

    public ResponseList<Status> lookup(long... ids) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/lookup.json?id=" + StringUtil.join(ids)));
    }

    public UploadedMedia uploadMedia(File image) throws TwitterException {
        checkFileValidity(image);
        return new UploadedMedia(post(this.conf.getUploadBaseURL() + "media/upload.json", new HttpParameter(ShareActivity.KEY_PLATFORM, image)).asJSONObject());
    }

    public UploadedMedia uploadMedia(String fileName, InputStream image) throws TwitterException {
        return new UploadedMedia(post(this.conf.getUploadBaseURL() + "media/upload.json", new HttpParameter(ShareActivity.KEY_PLATFORM, fileName, image)).asJSONObject());
    }

    public QueryResult search(Query query) throws TwitterException {
        if (query.nextPage() != null) {
            return this.factory.createQueryResult(get(this.conf.getRestBaseURL() + "search/tweets.json" + query.nextPage()), query);
        }
        return this.factory.createQueryResult(get(this.conf.getRestBaseURL() + "search/tweets.json", query.asHttpParameterArray()), query);
    }

    public ResponseList<DirectMessage> getDirectMessages() throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages.json?full_text=true"));
    }

    public ResponseList<DirectMessage> getDirectMessages(Paging paging) throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages.json", mergeParameters(paging.asPostParameterArray(), new HttpParameter("full_text", true))));
    }

    public ResponseList<DirectMessage> getSentDirectMessages() throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages/sent.json?full_text=true"));
    }

    public ResponseList<DirectMessage> getSentDirectMessages(Paging paging) throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages/sent.json", mergeParameters(paging.asPostParameterArray(), new HttpParameter("full_text", true))));
    }

    public DirectMessage showDirectMessage(long id) throws TwitterException {
        return this.factory.createDirectMessage(get(this.conf.getRestBaseURL() + "direct_messages/show.json?id=" + id + "&full_text=true"));
    }

    public DirectMessage destroyDirectMessage(long id) throws TwitterException {
        return this.factory.createDirectMessage(post(this.conf.getRestBaseURL() + "direct_messages/destroy.json?id=" + id + "&full_text=true"));
    }

    public DirectMessage sendDirectMessage(long userId, String text) throws TwitterException {
        return this.factory.createDirectMessage(post(this.conf.getRestBaseURL() + "direct_messages/new.json", new HttpParameter("user_id", userId), new HttpParameter(MessageObj.SUBTYPE_NOTI, text), new HttpParameter("full_text", true)));
    }

    public DirectMessage sendDirectMessage(String screenName, String text) throws TwitterException {
        return this.factory.createDirectMessage(post(this.conf.getRestBaseURL() + "direct_messages/new.json", new HttpParameter("screen_name", screenName), new HttpParameter(MessageObj.SUBTYPE_NOTI, text), new HttpParameter("full_text", true)));
    }

    public InputStream getDMImageAsStream(String url) throws TwitterException {
        return get(url).asStream();
    }

    public IDs getNoRetweetsFriendships() throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friendships/no_retweets/ids.json"));
    }

    public IDs getFriendsIDs(long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?cursor=" + cursor));
    }

    public IDs getFriendsIDs(long userId, long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?user_id=" + userId + "&cursor=" + cursor));
    }

    public IDs getFriendsIDs(long userId, long cursor, int count) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?user_id=" + userId + "&cursor=" + cursor + "&count=" + count));
    }

    public IDs getFriendsIDs(String screenName, long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor)));
    }

    public IDs getFriendsIDs(String screenName, long cursor, int count) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor), new HttpParameter("count", count)));
    }

    public IDs getFollowersIDs(long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?cursor=" + cursor));
    }

    public IDs getFollowersIDs(long userId, long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?user_id=" + userId + "&cursor=" + cursor));
    }

    public IDs getFollowersIDs(long userId, long cursor, int count) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?user_id=" + userId + "&cursor=" + cursor + "&count=" + count));
    }

    public IDs getFollowersIDs(String screenName, long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor)));
    }

    public IDs getFollowersIDs(String screenName, long cursor, int count) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor), new HttpParameter("count", count)));
    }

    public ResponseList<Friendship> lookupFriendships(long... ids) throws TwitterException {
        return this.factory.createFriendshipList(get(this.conf.getRestBaseURL() + "friendships/lookup.json?user_id=" + StringUtil.join(ids)));
    }

    public ResponseList<Friendship> lookupFriendships(String... screenNames) throws TwitterException {
        return this.factory.createFriendshipList(get(this.conf.getRestBaseURL() + "friendships/lookup.json?screen_name=" + StringUtil.join(screenNames)));
    }

    public IDs getIncomingFriendships(long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friendships/incoming.json?cursor=" + cursor));
    }

    public IDs getOutgoingFriendships(long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friendships/outgoing.json?cursor=" + cursor));
    }

    public User createFriendship(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json?user_id=" + userId));
    }

    public User createFriendship(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json", new HttpParameter("screen_name", screenName)));
    }

    public User createFriendship(long userId, boolean follow) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json?user_id=" + userId + "&follow=" + follow));
    }

    public User createFriendship(String screenName, boolean follow) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json", new HttpParameter("screen_name", screenName), new HttpParameter("follow", follow)));
    }

    public User destroyFriendship(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/destroy.json?user_id=" + userId));
    }

    public User destroyFriendship(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/destroy.json", new HttpParameter("screen_name", screenName)));
    }

    public Relationship updateFriendship(long userId, boolean enableDeviceNotification, boolean retweets) throws TwitterException {
        return this.factory.createRelationship(post(this.conf.getRestBaseURL() + "friendships/update.json", new HttpParameter("user_id", userId), new HttpParameter("device", enableDeviceNotification), new HttpParameter("retweets", retweets)));
    }

    public Relationship updateFriendship(String screenName, boolean enableDeviceNotification, boolean retweets) throws TwitterException {
        return this.factory.createRelationship(post(this.conf.getRestBaseURL() + "friendships/update.json", new HttpParameter("screen_name", screenName), new HttpParameter("device", enableDeviceNotification), new HttpParameter("retweets", retweets)));
    }

    public Relationship showFriendship(long sourceId, long targetId) throws TwitterException {
        return this.factory.createRelationship(get(this.conf.getRestBaseURL() + "friendships/show.json", new HttpParameter("source_id", sourceId), new HttpParameter("target_id", targetId)));
    }

    public Relationship showFriendship(String sourceScreenName, String targetScreenName) throws TwitterException {
        return this.factory.createRelationship(get(this.conf.getRestBaseURL() + "friendships/show.json", HttpParameter.getParameterArray("source_screen_name", sourceScreenName, "target_screen_name", targetScreenName)));
    }

    public PagableResponseList<User> getFriendsList(long userId, long cursor) throws TwitterException {
        return getFriendsList(userId, cursor, 20);
    }

    public PagableResponseList<User> getFriendsList(long userId, long cursor, int count) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json?user_id=" + userId + "&cursor=" + cursor + "&count=" + count));
    }

    public PagableResponseList<User> getFriendsList(String screenName, long cursor) throws TwitterException {
        return getFriendsList(screenName, cursor, 20);
    }

    public PagableResponseList<User> getFriendsList(String screenName, long cursor, int count) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor), new HttpParameter("count", count)));
    }

    public PagableResponseList<User> getFriendsList(long userId, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json?user_id=" + userId + "&cursor=" + cursor + "&count=" + count + "&skip_status=" + skipStatus + "&include_user_entities=" + includeUserEntities));
    }

    public PagableResponseList<User> getFriendsList(String screenName, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor), new HttpParameter("count", count), new HttpParameter("skip_status", skipStatus), new HttpParameter("include_user_entities", includeUserEntities)));
    }

    public PagableResponseList<User> getFollowersList(long userId, long cursor) throws TwitterException {
        return getFollowersList(userId, cursor, 20);
    }

    public PagableResponseList<User> getFollowersList(String screenName, long cursor) throws TwitterException {
        return getFollowersList(screenName, cursor, 20);
    }

    public PagableResponseList<User> getFollowersList(long userId, long cursor, int count) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json?user_id=" + userId + "&cursor=" + cursor + "&count=" + count));
    }

    public PagableResponseList<User> getFollowersList(String screenName, long cursor, int count) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor), new HttpParameter("count", count)));
    }

    public PagableResponseList<User> getFollowersList(long userId, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json?user_id=" + userId + "&cursor=" + cursor + "&count=" + count + "&skip_status=" + skipStatus + "&include_user_entities=" + includeUserEntities));
    }

    public PagableResponseList<User> getFollowersList(String screenName, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json", new HttpParameter("screen_name", screenName), new HttpParameter("cursor", cursor), new HttpParameter("count", count), new HttpParameter("skip_status", skipStatus), new HttpParameter("include_user_entities", includeUserEntities)));
    }

    public AccountSettings getAccountSettings() throws TwitterException {
        return this.factory.createAccountSettings(get(this.conf.getRestBaseURL() + "account/settings.json"));
    }

    public User verifyCredentials() throws TwitterException {
        return super.fillInIDAndScreenName();
    }

    public AccountSettings updateAccountSettings(Integer trend_locationWoeid, Boolean sleep_timeEnabled, String start_sleepTime, String end_sleepTime, String time_zone, String lang) throws TwitterException {
        List<HttpParameter> profile = new ArrayList(6);
        if (trend_locationWoeid != null) {
            profile.add(new HttpParameter("trend_location_woeid", trend_locationWoeid.intValue()));
        }
        if (sleep_timeEnabled != null) {
            profile.add(new HttpParameter("sleep_time_enabled", sleep_timeEnabled.toString()));
        }
        if (start_sleepTime != null) {
            profile.add(new HttpParameter("start_sleep_time", start_sleepTime));
        }
        if (end_sleepTime != null) {
            profile.add(new HttpParameter("end_sleep_time", end_sleepTime));
        }
        if (time_zone != null) {
            profile.add(new HttpParameter("time_zone", time_zone));
        }
        if (lang != null) {
            profile.add(new HttpParameter("lang", lang));
        }
        return this.factory.createAccountSettings(post(this.conf.getRestBaseURL() + "account/settings.json", (HttpParameter[]) profile.toArray(new HttpParameter[profile.size()])));
    }

    public User updateProfile(String name, String url, String location, String description) throws TwitterException {
        List<HttpParameter> profile = new ArrayList(4);
        addParameterToList(profile, "name", name);
        addParameterToList(profile, "url", url);
        addParameterToList(profile, "location", location);
        addParameterToList(profile, "description", description);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile.json", (HttpParameter[]) profile.toArray(new HttpParameter[profile.size()])));
    }

    public User updateProfileBackgroundImage(File image, boolean tile) throws TwitterException {
        checkFileValidity(image);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_background_image.json", new HttpParameter("image", image), new HttpParameter("tile", tile)));
    }

    public User updateProfileBackgroundImage(InputStream image, boolean tile) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_background_image.json", new HttpParameter("image", "image", image), new HttpParameter("tile", tile)));
    }

    public User updateProfileColors(String profileBackgroundColor, String profileTextColor, String profileLinkColor, String profileSidebarFillColor, String profileSidebarBorderColor) throws TwitterException {
        List<HttpParameter> colors = new ArrayList(6);
        addParameterToList(colors, "profile_background_color", profileBackgroundColor);
        addParameterToList(colors, "profile_text_color", profileTextColor);
        addParameterToList(colors, "profile_link_color", profileLinkColor);
        addParameterToList(colors, "profile_sidebar_fill_color", profileSidebarFillColor);
        addParameterToList(colors, "profile_sidebar_border_color", profileSidebarBorderColor);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_colors.json", (HttpParameter[]) colors.toArray(new HttpParameter[colors.size()])));
    }

    private void addParameterToList(List<HttpParameter> colors, String paramName, String color) {
        if (color != null) {
            colors.add(new HttpParameter(paramName, color));
        }
    }

    public User updateProfileImage(File image) throws TwitterException {
        checkFileValidity(image);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_image.json", new HttpParameter("image", image)));
    }

    public User updateProfileImage(InputStream image) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_image.json", new HttpParameter("image", "image", image)));
    }

    private void checkFileValidity(File image) throws TwitterException {
        if (!image.exists()) {
            throw new TwitterException(new FileNotFoundException(image + " is not found."));
        } else if (!image.isFile()) {
            throw new TwitterException(new IOException(image + " is not a file."));
        }
    }

    public PagableResponseList<User> getBlocksList() throws TwitterException {
        return getBlocksList(-1);
    }

    public PagableResponseList<User> getBlocksList(long cursor) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "blocks/list.json?cursor=" + cursor));
    }

    public IDs getBlocksIDs() throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "blocks/ids.json"));
    }

    public IDs getBlocksIDs(long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "blocks/ids.json?cursor=" + cursor));
    }

    public User createBlock(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/create.json?user_id=" + userId));
    }

    public User createBlock(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/create.json", new HttpParameter("screen_name", screenName)));
    }

    public User destroyBlock(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/destroy.json?user_id=" + userId));
    }

    public User destroyBlock(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/destroy.json", new HttpParameter("screen_name", screenName)));
    }

    public PagableResponseList<User> getMutesList(long cursor) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "mutes/users/list.json?cursor=" + cursor));
    }

    public IDs getMutesIDs(long cursor) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "mutes/users/ids.json?cursor=" + cursor));
    }

    public User createMute(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/create.json?user_id=" + userId));
    }

    public User createMute(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/create.json", new HttpParameter("screen_name", screenName)));
    }

    public User destroyMute(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/destroy.json?user_id=" + userId));
    }

    public User destroyMute(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/destroy.json", new HttpParameter("screen_name", screenName)));
    }

    public ResponseList<User> lookupUsers(long... ids) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/lookup.json", new HttpParameter("user_id", StringUtil.join(ids))));
    }

    public ResponseList<User> lookupUsers(String... screenNames) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/lookup.json", new HttpParameter("screen_name", StringUtil.join(screenNames))));
    }

    public User showUser(long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "users/show.json?user_id=" + userId));
    }

    public User showUser(String screenName) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "users/show.json", new HttpParameter("screen_name", screenName)));
    }

    public ResponseList<User> searchUsers(String query, int page) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/search.json", new HttpParameter("q", query), new HttpParameter("per_page", 20), new HttpParameter(ParamKey.PAGE, page)));
    }

    public ResponseList<User> getContributees(long userId) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributees.json?user_id=" + userId));
    }

    public ResponseList<User> getContributees(String screenName) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributees.json", new HttpParameter("screen_name", screenName)));
    }

    public ResponseList<User> getContributors(long userId) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributors.json?user_id=" + userId));
    }

    public ResponseList<User> getContributors(String screenName) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributors.json", new HttpParameter("screen_name", screenName)));
    }

    public void removeProfileBanner() throws TwitterException {
        post(this.conf.getRestBaseURL() + "account/remove_profile_banner.json");
    }

    public void updateProfileBanner(File image) throws TwitterException {
        checkFileValidity(image);
        post(this.conf.getRestBaseURL() + "account/update_profile_banner.json", new HttpParameter("banner", image));
    }

    public void updateProfileBanner(InputStream image) throws TwitterException {
        post(this.conf.getRestBaseURL() + "account/update_profile_banner.json", new HttpParameter("banner", "banner", image));
    }

    public ResponseList<User> getUserSuggestions(String categorySlug) throws TwitterException {
        try {
            return this.factory.createUserListFromJSONArray_Users(get(this.conf.getRestBaseURL() + "users/suggestions/" + URLEncoder.encode(categorySlug, "UTF-8") + ".json"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseList<Category> getSuggestedUserCategories() throws TwitterException {
        return this.factory.createCategoryList(get(this.conf.getRestBaseURL() + "users/suggestions.json"));
    }

    public ResponseList<User> getMemberSuggestions(String categorySlug) throws TwitterException {
        try {
            return this.factory.createUserListFromJSONArray(get(this.conf.getRestBaseURL() + "users/suggestions/" + URLEncoder.encode(categorySlug, "UTF-8") + "/members.json"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseList<Status> getFavorites() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json"));
    }

    public ResponseList<Status> getFavorites(long userId) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json?user_id=" + userId));
    }

    public ResponseList<Status> getFavorites(String screenName) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", new HttpParameter("screen_name", screenName)));
    }

    public ResponseList<Status> getFavorites(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", paging.asPostParameterArray()));
    }

    public ResponseList<Status> getFavorites(long userId, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", mergeParameters(new HttpParameter[]{new HttpParameter("user_id", userId)}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getFavorites(String screenName, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", mergeParameters(new HttpParameter[]{new HttpParameter("screen_name", screenName)}, paging.asPostParameterArray())));
    }

    public Status destroyFavorite(long id) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "favorites/destroy.json?id=" + id));
    }

    public Status createFavorite(long id) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "favorites/create.json?id=" + id));
    }

    public ResponseList<UserList> getUserLists(String listOwnerScreenName) throws TwitterException {
        return getUserLists(listOwnerScreenName, false);
    }

    public ResponseList<UserList> getUserLists(String listOwnerScreenName, boolean reverse) throws TwitterException {
        return this.factory.createUserListList(get(this.conf.getRestBaseURL() + "lists/list.json", new HttpParameter("screen_name", listOwnerScreenName), new HttpParameter("reverse", reverse)));
    }

    public ResponseList<UserList> getUserLists(long listOwnerUserId) throws TwitterException {
        return getUserLists(listOwnerUserId, false);
    }

    public ResponseList<UserList> getUserLists(long listOwnerUserId, boolean reverse) throws TwitterException {
        return this.factory.createUserListList(get(this.conf.getRestBaseURL() + "lists/list.json", new HttpParameter("user_id", listOwnerUserId), new HttpParameter("reverse", reverse)));
    }

    public ResponseList<Status> getUserListStatuses(long listId, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "lists/statuses.json", mergeParameters(paging.asPostParameterArray(Paging.SMCP, "count"), new HttpParameter("list_id", listId))));
    }

    public ResponseList<Status> getUserListStatuses(long ownerId, String slug, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "lists/statuses.json", mergeParameters(paging.asPostParameterArray(Paging.SMCP, "count"), new HttpParameter[]{new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug)})));
    }

    public ResponseList<Status> getUserListStatuses(String ownerScreenName, String slug, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "lists/statuses.json", mergeParameters(paging.asPostParameterArray(Paging.SMCP, "count"), new HttpParameter[]{new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug)})));
    }

    public UserList destroyUserListMember(long listId, long userId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter("list_id", listId), new HttpParameter("user_id", userId)));
    }

    public UserList destroyUserListMember(long ownerId, String slug, long userId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug), new HttpParameter("user_id", userId)));
    }

    public UserList destroyUserListMember(long listId, String screenName) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter("list_id", listId), new HttpParameter("screen_name", screenName)));
    }

    public UserList destroyUserListMember(String ownerScreenName, String slug, long userId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("user_id", userId)));
    }

    public UserList destroyUserListMembers(long listId, String[] screenNames) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy_all.json", new HttpParameter("list_id", listId), new HttpParameter("screen_name", StringUtil.join(screenNames))));
    }

    public UserList destroyUserListMembers(long listId, long[] userIds) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy_all.json", new HttpParameter("list_id", listId), new HttpParameter("user_id", StringUtil.join(userIds))));
    }

    public UserList destroyUserListMembers(String ownerScreenName, String slug, String[] screenNames) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy_all.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("screen_name", StringUtil.join(screenNames))));
    }

    public PagableResponseList<UserList> getUserListMemberships(long cursor) throws TwitterException {
        return getUserListMemberships(20, cursor);
    }

    public PagableResponseList<UserList> getUserListMemberships(int count, long cursor) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/memberships.json", new HttpParameter("cursor", cursor), new HttpParameter("count", count)));
    }

    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, long cursor) throws TwitterException {
        return getUserListMemberships(listMemberScreenName, cursor, false);
    }

    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, int count, long cursor) throws TwitterException {
        return getUserListMemberships(listMemberScreenName, count, cursor, false);
    }

    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, long cursor, boolean filterToOwnedLists) throws TwitterException {
        return getUserListMemberships(listMemberScreenName, 20, cursor, filterToOwnedLists);
    }

    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, int count, long cursor, boolean filterToOwnedLists) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/memberships.json", new HttpParameter("screen_name", listMemberScreenName), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("filter_to_owned_lists", filterToOwnedLists)));
    }

    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, long cursor) throws TwitterException {
        return getUserListMemberships(listMemberId, cursor, false);
    }

    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, int count, long cursor) throws TwitterException {
        return getUserListMemberships(listMemberId, count, cursor, false);
    }

    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, long cursor, boolean filterToOwnedLists) throws TwitterException {
        return getUserListMemberships(listMemberId, 20, cursor, filterToOwnedLists);
    }

    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, int count, long cursor, boolean filterToOwnedLists) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/memberships.json", new HttpParameter("user_id", listMemberId), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("filter_to_owned_lists", filterToOwnedLists)));
    }

    public PagableResponseList<User> getUserListSubscribers(long listId, long cursor) throws TwitterException {
        return getUserListSubscribers(listId, 20, cursor, false);
    }

    public PagableResponseList<User> getUserListSubscribers(long listId, int count, long cursor) throws TwitterException {
        return getUserListSubscribers(listId, count, cursor, false);
    }

    public PagableResponseList<User> getUserListSubscribers(long listId, int count, long cursor, boolean skipStatus) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/subscribers.json", new HttpParameter("list_id", listId), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("skip_status", skipStatus)));
    }

    public PagableResponseList<User> getUserListSubscribers(long ownerId, String slug, long cursor) throws TwitterException {
        return getUserListSubscribers(ownerId, slug, 20, cursor, false);
    }

    public PagableResponseList<User> getUserListSubscribers(long ownerId, String slug, int count, long cursor) throws TwitterException {
        return getUserListSubscribers(ownerId, slug, count, cursor, false);
    }

    public PagableResponseList<User> getUserListSubscribers(long ownerId, String slug, int count, long cursor, boolean skipStatus) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/subscribers.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("skip_status", skipStatus)));
    }

    public PagableResponseList<User> getUserListSubscribers(String ownerScreenName, String slug, long cursor) throws TwitterException {
        return getUserListSubscribers(ownerScreenName, slug, 20, cursor, false);
    }

    public PagableResponseList<User> getUserListSubscribers(String ownerScreenName, String slug, int count, long cursor) throws TwitterException {
        return getUserListSubscribers(ownerScreenName, slug, count, cursor, false);
    }

    public PagableResponseList<User> getUserListSubscribers(String ownerScreenName, String slug, int count, long cursor, boolean skipStatus) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/subscribers.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("skip_status", skipStatus)));
    }

    public UserList createUserListSubscription(long listId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/create.json", new HttpParameter("list_id", listId)));
    }

    public UserList createUserListSubscription(long ownerId, String slug) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/create.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug)));
    }

    public UserList createUserListSubscription(String ownerScreenName, String slug) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/create.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug)));
    }

    public User showUserListSubscription(long listId, long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/subscribers/show.json?list_id=" + listId + "&user_id=" + userId));
    }

    public User showUserListSubscription(long ownerId, String slug, long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/subscribers/show.json?owner_id=" + ownerId + "&slug=" + slug + "&user_id=" + userId));
    }

    public User showUserListSubscription(String ownerScreenName, String slug, long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/subscribers/show.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("user_id", userId)));
    }

    public UserList destroyUserListSubscription(long listId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/destroy.json", new HttpParameter("list_id", listId)));
    }

    public UserList destroyUserListSubscription(long ownerId, String slug) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/destroy.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug)));
    }

    public UserList destroyUserListSubscription(String ownerScreenName, String slug) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/destroy.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug)));
    }

    public UserList createUserListMembers(long listId, long... userIds) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter("list_id", listId), new HttpParameter("user_id", StringUtil.join(userIds))));
    }

    public UserList createUserListMembers(long ownerId, String slug, long... userIds) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug), new HttpParameter("user_id", StringUtil.join(userIds))));
    }

    public UserList createUserListMembers(String ownerScreenName, String slug, long... userIds) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("user_id", StringUtil.join(userIds))));
    }

    public UserList createUserListMembers(long listId, String... screenNames) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter("list_id", listId), new HttpParameter("screen_name", StringUtil.join(screenNames))));
    }

    public UserList createUserListMembers(long ownerId, String slug, String... screenNames) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug), new HttpParameter("screen_name", StringUtil.join(screenNames))));
    }

    public UserList createUserListMembers(String ownerScreenName, String slug, String... screenNames) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("screen_name", StringUtil.join(screenNames))));
    }

    public User showUserListMembership(long listId, long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/members/show.json?list_id=" + listId + "&user_id=" + userId));
    }

    public User showUserListMembership(long ownerId, String slug, long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/members/show.json?owner_id=" + ownerId + "&slug=" + slug + "&user_id=" + userId));
    }

    public User showUserListMembership(String ownerScreenName, String slug, long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/members/show.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("user_id", userId)));
    }

    public PagableResponseList<User> getUserListMembers(long listId, long cursor) throws TwitterException {
        return getUserListMembers(listId, 20, cursor, false);
    }

    public PagableResponseList<User> getUserListMembers(long listId, int count, long cursor) throws TwitterException {
        return getUserListMembers(listId, count, cursor, false);
    }

    public PagableResponseList<User> getUserListMembers(long listId, int count, long cursor, boolean skipStatus) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/members.json", new HttpParameter("list_id", listId), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("skip_status", skipStatus)));
    }

    public PagableResponseList<User> getUserListMembers(long ownerId, String slug, long cursor) throws TwitterException {
        return getUserListMembers(ownerId, slug, 20, cursor, false);
    }

    public PagableResponseList<User> getUserListMembers(long ownerId, String slug, int count, long cursor) throws TwitterException {
        return getUserListMembers(ownerId, slug, count, cursor, false);
    }

    public PagableResponseList<User> getUserListMembers(long ownerId, String slug, int count, long cursor, boolean skipStatus) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/members.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("skip_status", skipStatus)));
    }

    public PagableResponseList<User> getUserListMembers(String ownerScreenName, String slug, long cursor) throws TwitterException {
        return getUserListMembers(ownerScreenName, slug, 20, cursor, false);
    }

    public PagableResponseList<User> getUserListMembers(String ownerScreenName, String slug, int count, long cursor) throws TwitterException {
        return getUserListMembers(ownerScreenName, slug, count, cursor, false);
    }

    public PagableResponseList<User> getUserListMembers(String ownerScreenName, String slug, int count, long cursor, boolean skipStatus) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/members.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug), new HttpParameter("count", count), new HttpParameter("cursor", cursor), new HttpParameter("skip_status", skipStatus)));
    }

    public UserList createUserListMember(long listId, long userId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create.json", new HttpParameter("user_id", userId), new HttpParameter("list_id", listId)));
    }

    public UserList createUserListMember(long ownerId, String slug, long userId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create.json", new HttpParameter("user_id", userId), new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug)));
    }

    public UserList createUserListMember(String ownerScreenName, String slug, long userId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create.json", new HttpParameter("user_id", userId), new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug)));
    }

    public UserList destroyUserList(long listId) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/destroy.json", new HttpParameter("list_id", listId)));
    }

    public UserList destroyUserList(long ownerId, String slug) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/destroy.json", new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug)));
    }

    public UserList destroyUserList(String ownerScreenName, String slug) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/destroy.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug)));
    }

    public UserList updateUserList(long listId, String newListName, boolean isPublicList, String newDescription) throws TwitterException {
        return updateUserList(newListName, isPublicList, newDescription, new HttpParameter("list_id", listId));
    }

    public UserList updateUserList(long ownerId, String slug, String newListName, boolean isPublicList, String newDescription) throws TwitterException {
        return updateUserList(newListName, isPublicList, newDescription, new HttpParameter("owner_id", ownerId), new HttpParameter("slug", slug));
    }

    public UserList updateUserList(String ownerScreenName, String slug, String newListName, boolean isPublicList, String newDescription) throws TwitterException {
        return updateUserList(newListName, isPublicList, newDescription, new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug));
    }

    private UserList updateUserList(String newListName, boolean isPublicList, String newDescription, HttpParameter... params) throws TwitterException {
        List<HttpParameter> httpParams = new ArrayList();
        Collections.addAll(httpParams, params);
        if (newListName != null) {
            httpParams.add(new HttpParameter("name", newListName));
        }
        httpParams.add(new HttpParameter("mode", isPublicList ? "public" : "private"));
        if (newDescription != null) {
            httpParams.add(new HttpParameter("description", newDescription));
        }
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/update.json", (HttpParameter[]) httpParams.toArray(new HttpParameter[httpParams.size()])));
    }

    public UserList createUserList(String listName, boolean isPublicList, String description) throws TwitterException {
        List<HttpParameter> httpParams = new ArrayList();
        httpParams.add(new HttpParameter("name", listName));
        httpParams.add(new HttpParameter("mode", isPublicList ? "public" : "private"));
        if (description != null) {
            httpParams.add(new HttpParameter("description", description));
        }
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/create.json", (HttpParameter[]) httpParams.toArray(new HttpParameter[httpParams.size()])));
    }

    public UserList showUserList(long listId) throws TwitterException {
        return this.factory.createAUserList(get(this.conf.getRestBaseURL() + "lists/show.json?list_id=" + listId));
    }

    public UserList showUserList(long ownerId, String slug) throws TwitterException {
        return this.factory.createAUserList(get(this.conf.getRestBaseURL() + "lists/show.json?owner_id=" + ownerId + "&slug=" + slug));
    }

    public UserList showUserList(String ownerScreenName, String slug) throws TwitterException {
        return this.factory.createAUserList(get(this.conf.getRestBaseURL() + "lists/show.json", new HttpParameter("owner_screen_name", ownerScreenName), new HttpParameter("slug", slug)));
    }

    public PagableResponseList<UserList> getUserListSubscriptions(String listSubscriberScreenName, long cursor) throws TwitterException {
        return getUserListSubscriptions(listSubscriberScreenName, 20, cursor);
    }

    public PagableResponseList<UserList> getUserListSubscriptions(String listSubscriberScreenName, int count, long cursor) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/subscriptions.json", new HttpParameter("screen_name", listSubscriberScreenName), new HttpParameter("count", count), new HttpParameter("cursor", cursor)));
    }

    public PagableResponseList<UserList> getUserListSubscriptions(long listSubscriberId, long cursor) throws TwitterException {
        return getUserListSubscriptions(listSubscriberId, 20, cursor);
    }

    public PagableResponseList<UserList> getUserListSubscriptions(long listSubscriberId, int count, long cursor) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/subscriptions.json", new HttpParameter("user_id", listSubscriberId), new HttpParameter("count", count), new HttpParameter("cursor", cursor)));
    }

    public PagableResponseList<UserList> getUserListsOwnerships(String listOwnerScreenName, long cursor) throws TwitterException {
        return getUserListsOwnerships(listOwnerScreenName, 20, cursor);
    }

    public PagableResponseList<UserList> getUserListsOwnerships(String listOwnerScreenName, int count, long cursor) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/ownerships.json", new HttpParameter("screen_name", listOwnerScreenName), new HttpParameter("count", count), new HttpParameter("cursor", cursor)));
    }

    public PagableResponseList<UserList> getUserListsOwnerships(long listOwnerId, long cursor) throws TwitterException {
        return getUserListsOwnerships(listOwnerId, 20, cursor);
    }

    public PagableResponseList<UserList> getUserListsOwnerships(long listOwnerId, int count, long cursor) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/ownerships.json", new HttpParameter("user_id", listOwnerId), new HttpParameter("count", count), new HttpParameter("cursor", cursor)));
    }

    public ResponseList<SavedSearch> getSavedSearches() throws TwitterException {
        return this.factory.createSavedSearchList(get(this.conf.getRestBaseURL() + "saved_searches/list.json"));
    }

    public SavedSearch showSavedSearch(long id) throws TwitterException {
        return this.factory.createSavedSearch(get(this.conf.getRestBaseURL() + "saved_searches/show/" + id + ".json"));
    }

    public SavedSearch createSavedSearch(String query) throws TwitterException {
        return this.factory.createSavedSearch(post(this.conf.getRestBaseURL() + "saved_searches/create.json", new HttpParameter("query", query)));
    }

    public SavedSearch destroySavedSearch(long id) throws TwitterException {
        return this.factory.createSavedSearch(post(this.conf.getRestBaseURL() + "saved_searches/destroy/" + id + ".json"));
    }

    public Place getGeoDetails(String placeId) throws TwitterException {
        return this.factory.createPlace(get(this.conf.getRestBaseURL() + "geo/id/" + placeId + ".json"));
    }

    public ResponseList<Place> reverseGeoCode(GeoQuery query) throws TwitterException {
        try {
            return this.factory.createPlaceList(get(this.conf.getRestBaseURL() + "geo/reverse_geocode.json", query.asHttpParameterArray()));
        } catch (TwitterException te) {
            if (te.getStatusCode() == 404) {
                return this.factory.createEmptyResponseList();
            }
            throw te;
        }
    }

    public ResponseList<Place> searchPlaces(GeoQuery query) throws TwitterException {
        return this.factory.createPlaceList(get(this.conf.getRestBaseURL() + "geo/search.json", query.asHttpParameterArray()));
    }

    public ResponseList<Place> getSimilarPlaces(GeoLocation location, String name, String containedWithin, String streetAddress) throws TwitterException {
        List<HttpParameter> params = new ArrayList(3);
        params.add(new HttpParameter(au.f3570Y, location.getLatitude()));
        params.add(new HttpParameter("long", location.getLongitude()));
        params.add(new HttpParameter("name", name));
        if (containedWithin != null) {
            params.add(new HttpParameter("contained_within", containedWithin));
        }
        if (streetAddress != null) {
            params.add(new HttpParameter("attribute:street_address", streetAddress));
        }
        return this.factory.createPlaceList(get(this.conf.getRestBaseURL() + "geo/similar_places.json", (HttpParameter[]) params.toArray(new HttpParameter[params.size()])));
    }

    public Trends getPlaceTrends(int woeid) throws TwitterException {
        return this.factory.createTrends(get(this.conf.getRestBaseURL() + "trends/place.json?id=" + woeid));
    }

    public ResponseList<Location> getAvailableTrends() throws TwitterException {
        return this.factory.createLocationList(get(this.conf.getRestBaseURL() + "trends/available.json"));
    }

    public ResponseList<Location> getClosestTrends(GeoLocation location) throws TwitterException {
        return this.factory.createLocationList(get(this.conf.getRestBaseURL() + "trends/closest.json", new HttpParameter(au.f3570Y, location.getLatitude()), new HttpParameter("long", location.getLongitude())));
    }

    public User reportSpam(long userId) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "users/report_spam.json?user_id=" + userId));
    }

    public User reportSpam(String screenName) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "users/report_spam.json", new HttpParameter("screen_name", screenName)));
    }

    public TwitterAPIConfiguration getAPIConfiguration() throws TwitterException {
        return this.factory.createTwitterAPIConfiguration(get(this.conf.getRestBaseURL() + "help/configuration.json"));
    }

    public ResponseList<Language> getLanguages() throws TwitterException {
        return this.factory.createLanguageList(get(this.conf.getRestBaseURL() + "help/languages.json"));
    }

    public String getPrivacyPolicy() throws TwitterException {
        try {
            return get(this.conf.getRestBaseURL() + "help/privacy.json").asJSONObject().getString(ShareConstants.WEB_DIALOG_PARAM_PRIVACY);
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public String getTermsOfService() throws TwitterException {
        try {
            return get(this.conf.getRestBaseURL() + "help/tos.json").asJSONObject().getString("tos");
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    public Map<String, RateLimitStatus> getRateLimitStatus() throws TwitterException {
        return this.factory.createRateLimitStatuses(get(this.conf.getRestBaseURL() + "application/rate_limit_status.json"));
    }

    public Map<String, RateLimitStatus> getRateLimitStatus(String... resources) throws TwitterException {
        return this.factory.createRateLimitStatuses(get(this.conf.getRestBaseURL() + "application/rate_limit_status.json?resources=" + StringUtil.join(resources)));
    }

    public TimelinesResources timelines() {
        return this;
    }

    public TweetsResources tweets() {
        return this;
    }

    public SearchResource search() {
        return this;
    }

    public DirectMessagesResources directMessages() {
        return this;
    }

    public FriendsFollowersResources friendsFollowers() {
        return this;
    }

    public UsersResources users() {
        return this;
    }

    public SuggestedUsersResources suggestedUsers() {
        return this;
    }

    public FavoritesResources favorites() {
        return this;
    }

    public ListsResources list() {
        return this;
    }

    public SavedSearchesResources savedSearches() {
        return this;
    }

    public PlacesGeoResources placesGeo() {
        return this;
    }

    public TrendsResources trends() {
        return this;
    }

    public SpamReportingResource spamReporting() {
        return this;
    }

    public HelpResources help() {
        return this;
    }

    private HttpResponse get(String url) throws TwitterException {
        ensureAuthorizationEnabled();
        if (this.IMPLICIT_PARAMS_STR.length() > 0) {
            if (url.contains("?")) {
                url = url + "&" + this.IMPLICIT_PARAMS_STR;
            } else {
                url = url + "?" + this.IMPLICIT_PARAMS_STR;
            }
        }
        if (!this.conf.isMBeanEnabled()) {
            return this.http.get(url, null, this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.get(url, null, this.auth, this);
            return response;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(url, System.currentTimeMillis() - start, isOk(response));
        }
    }

    private HttpResponse get(String url, HttpParameter... params) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.get(url, mergeImplicitParams(params), this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.get(url, mergeImplicitParams(params), this.auth, this);
            return response;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(url, System.currentTimeMillis() - start, isOk(response));
        }
    }

    private HttpResponse post(String url) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.post(url, this.IMPLICIT_PARAMS, this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.post(url, this.IMPLICIT_PARAMS, this.auth, this);
            return response;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(url, System.currentTimeMillis() - start, isOk(response));
        }
    }

    private HttpResponse post(String url, HttpParameter... params) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.post(url, mergeImplicitParams(params), this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.post(url, mergeImplicitParams(params), this.auth, this);
            return response;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(url, System.currentTimeMillis() - start, isOk(response));
        }
    }

    private HttpParameter[] mergeParameters(HttpParameter[] params1, HttpParameter[] params2) {
        if (params1 != null && params2 != null) {
            HttpParameter[] params = new HttpParameter[(params1.length + params2.length)];
            System.arraycopy(params1, 0, params, 0, params1.length);
            System.arraycopy(params2, 0, params, params1.length, params2.length);
            return params;
        } else if (params1 == null && params2 == null) {
            return new HttpParameter[0];
        } else {
            if (params1 != null) {
                return params1;
            }
            return params2;
        }
    }

    private HttpParameter[] mergeParameters(HttpParameter[] params1, HttpParameter params2) {
        if (params1 != null && params2 != null) {
            HttpParameter[] params = new HttpParameter[(params1.length + 1)];
            System.arraycopy(params1, 0, params, 0, params1.length);
            params[params.length - 1] = params2;
            return params;
        } else if (params1 == null && params2 == null) {
            return new HttpParameter[0];
        } else {
            if (params1 != null) {
                return params1;
            }
            return new HttpParameter[]{params2};
        }
    }

    private HttpParameter[] mergeImplicitParams(HttpParameter... params) {
        return mergeParameters(params, this.IMPLICIT_PARAMS);
    }

    private boolean isOk(HttpResponse response) {
        return response != null && response.getStatusCode() < 300;
    }

    public String toString() {
        return "TwitterImpl{INCLUDE_MY_RETWEET=" + this.INCLUDE_MY_RETWEET + '}';
    }
}
