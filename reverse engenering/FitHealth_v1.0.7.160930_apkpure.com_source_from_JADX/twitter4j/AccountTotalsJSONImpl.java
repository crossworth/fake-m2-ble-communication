package twitter4j;

import com.facebook.internal.NativeProtocol;
import java.io.Serializable;
import twitter4j.conf.Configuration;

class AccountTotalsJSONImpl extends TwitterResponseImpl implements AccountTotals, Serializable {
    private static final long serialVersionUID = 4199733699237229892L;
    private final int favorites;
    private final int followers;
    private final int friends;
    private final int updates;

    private AccountTotalsJSONImpl(HttpResponse res, JSONObject json) {
        super(res);
        this.updates = ParseUtil.getInt("updates", json);
        this.followers = ParseUtil.getInt("followers", json);
        this.favorites = ParseUtil.getInt("favorites", json);
        this.friends = ParseUtil.getInt(NativeProtocol.AUDIENCE_FRIENDS, json);
    }

    AccountTotalsJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        this(res, res.asJSONObject());
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, res.asJSONObject());
        }
    }

    AccountTotalsJSONImpl(JSONObject json) throws TwitterException {
        this(null, json);
    }

    public int getUpdates() {
        return this.updates;
    }

    public int getFollowers() {
        return this.followers;
    }

    public int getFavorites() {
        return this.favorites;
    }

    public int getFriends() {
        return this.friends;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountTotalsJSONImpl that = (AccountTotalsJSONImpl) o;
        if (this.favorites != that.favorites) {
            return false;
        }
        if (this.followers != that.followers) {
            return false;
        }
        if (this.friends != that.friends) {
            return false;
        }
        if (this.updates != that.updates) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.updates * 31) + this.followers) * 31) + this.favorites) * 31) + this.friends;
    }

    public String toString() {
        return "AccountTotalsJSONImpl{updates=" + this.updates + ", followers=" + this.followers + ", favorites=" + this.favorites + ", friends=" + this.friends + '}';
    }
}
