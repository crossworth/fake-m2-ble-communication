package twitter4j;

import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.core.DroiQuery.Builder;
import com.umeng.facebook.internal.NativeProtocol;

public final class JSONObjectType {

    public enum Type {
        SENDER,
        STATUS,
        DIRECT_MESSAGE,
        DELETE,
        LIMIT,
        STALL_WARNING,
        SCRUB_GEO,
        FRIENDS,
        FAVORITE,
        UNFAVORITE,
        FOLLOW,
        UNFOLLOW,
        USER_LIST_MEMBER_ADDED,
        USER_LIST_MEMBER_DELETED,
        USER_LIST_SUBSCRIBED,
        USER_LIST_UNSUBSCRIBED,
        USER_LIST_CREATED,
        USER_LIST_UPDATED,
        USER_LIST_DESTROYED,
        USER_UPDATE,
        USER_DELETE,
        USER_SUSPEND,
        BLOCK,
        UNBLOCK,
        DISCONNECTION,
        RETWEETED_RETWEET,
        FAVORITED_RETWEET,
        QUOTED_TWEET,
        UNKNOWN
    }

    public static Type determine(JSONObject json) {
        if (!json.isNull(MessageObj.SENDER)) {
            return Type.SENDER;
        }
        if (!json.isNull("text")) {
            return Type.STATUS;
        }
        if (!json.isNull("direct_message")) {
            return Type.DIRECT_MESSAGE;
        }
        if (!json.isNull("delete")) {
            return Type.DELETE;
        }
        if (!json.isNull(Builder.f2654s)) {
            return Type.LIMIT;
        }
        if (!json.isNull("warning")) {
            return Type.STALL_WARNING;
        }
        if (!json.isNull("scrub_geo")) {
            return Type.SCRUB_GEO;
        }
        if (!json.isNull(NativeProtocol.AUDIENCE_FRIENDS)) {
            return Type.FRIENDS;
        }
        if (!json.isNull("event")) {
            try {
                String event = json.getString("event");
                if ("favorite".equals(event)) {
                    return Type.FAVORITE;
                }
                if ("unfavorite".equals(event)) {
                    return Type.UNFAVORITE;
                }
                if ("follow".equals(event)) {
                    return Type.FOLLOW;
                }
                if ("unfollow".equals(event)) {
                    return Type.UNFOLLOW;
                }
                if (event.startsWith("list")) {
                    if ("list_member_added".equals(event)) {
                        return Type.USER_LIST_MEMBER_ADDED;
                    }
                    if ("list_member_removed".equals(event)) {
                        return Type.USER_LIST_MEMBER_DELETED;
                    }
                    if ("list_user_subscribed".equals(event)) {
                        return Type.USER_LIST_SUBSCRIBED;
                    }
                    if ("list_user_unsubscribed".equals(event)) {
                        return Type.USER_LIST_UNSUBSCRIBED;
                    }
                    if ("list_created".equals(event)) {
                        return Type.USER_LIST_CREATED;
                    }
                    if ("list_updated".equals(event)) {
                        return Type.USER_LIST_UPDATED;
                    }
                    if ("list_destroyed".equals(event)) {
                        return Type.USER_LIST_DESTROYED;
                    }
                } else if ("user_update".equals(event)) {
                    return Type.USER_UPDATE;
                } else {
                    if ("user_delete".equals(event)) {
                        return Type.USER_DELETE;
                    }
                    if ("user_suspend".equals(event)) {
                        return Type.USER_SUSPEND;
                    }
                    if ("block".equals(event)) {
                        return Type.BLOCK;
                    }
                    if ("unblock".equals(event)) {
                        return Type.UNBLOCK;
                    }
                    if ("retweeted_retweet".equals(event)) {
                        return Type.RETWEETED_RETWEET;
                    }
                    if ("favorited_retweet".equals(event)) {
                        return Type.FAVORITED_RETWEET;
                    }
                    if ("quoted_tweet".equals(event)) {
                        return Type.QUOTED_TWEET;
                    }
                }
            } catch (JSONException e) {
            }
        } else if (!json.isNull("disconnect")) {
            return Type.DISCONNECTION;
        }
        return Type.UNKNOWN;
    }
}
