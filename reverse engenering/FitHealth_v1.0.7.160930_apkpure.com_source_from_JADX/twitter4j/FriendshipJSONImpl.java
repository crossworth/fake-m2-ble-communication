package twitter4j;

import twitter4j.conf.Configuration;

class FriendshipJSONImpl implements Friendship {
    private static final long serialVersionUID = 6847273186993125826L;
    private boolean followedBy = false;
    private boolean following = false;
    private final long id;
    private final String name;
    private final String screenName;

    FriendshipJSONImpl(JSONObject json) throws TwitterException {
        try {
            this.id = ParseUtil.getLong("id", json);
            this.name = json.getString("name");
            this.screenName = json.getString("screen_name");
            JSONArray connections = json.getJSONArray("connections");
            for (int i = 0; i < connections.length(); i++) {
                String connection = connections.getString(i);
                if ("following".equals(connection)) {
                    this.following = true;
                } else if ("followed_by".equals(connection)) {
                    this.followedBy = true;
                }
            }
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    static ResponseList<Friendship> createFriendshipList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<Friendship> friendshipList = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Friendship friendship = new FriendshipJSONImpl(json);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(friendship, json);
                }
                friendshipList.add(friendship);
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(friendshipList, list);
            }
            return friendshipList;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
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

    public boolean isFollowing() {
        return this.following;
    }

    public boolean isFollowedBy() {
        return this.followedBy;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FriendshipJSONImpl that = (FriendshipJSONImpl) o;
        if (this.followedBy != that.followedBy) {
            return false;
        }
        if (this.following != that.following) {
            return false;
        }
        if (this.id != that.id) {
            return false;
        }
        if (!this.name.equals(that.name)) {
            return false;
        }
        if (this.screenName.equals(that.screenName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 1;
        int i2 = ((int) (this.id ^ (this.id >>> 32))) * 31;
        if (this.name != null) {
            hashCode = this.name.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.screenName != null) {
            hashCode = this.screenName.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.following) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (!this.followedBy) {
            i = 0;
        }
        return hashCode + i;
    }

    public String toString() {
        return "FriendshipJSONImpl{id=" + this.id + ", name='" + this.name + '\'' + ", screenName='" + this.screenName + '\'' + ", following=" + this.following + ", followedBy=" + this.followedBy + '}';
    }
}
