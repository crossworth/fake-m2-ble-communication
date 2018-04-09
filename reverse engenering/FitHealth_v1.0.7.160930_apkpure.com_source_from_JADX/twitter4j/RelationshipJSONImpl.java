package twitter4j;

import com.tencent.open.SocialConstants;
import java.io.Serializable;
import twitter4j.conf.Configuration;

class RelationshipJSONImpl extends TwitterResponseImpl implements Relationship, Serializable {
    private static final long serialVersionUID = -2001484553401916448L;
    private final boolean sourceBlockingTarget;
    private final boolean sourceCanDm;
    private final boolean sourceFollowedByTarget;
    private final boolean sourceFollowingTarget;
    private final boolean sourceMutingTarget;
    private final boolean sourceNotificationsEnabled;
    private final long sourceUserId;
    private final String sourceUserScreenName;
    private final long targetUserId;
    private final String targetUserScreenName;
    private boolean wantRetweets;

    RelationshipJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        this(res, res.asJSONObject());
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, res.asJSONObject());
        }
    }

    RelationshipJSONImpl(JSONObject json) throws TwitterException {
        this(null, json);
    }

    RelationshipJSONImpl(HttpResponse res, JSONObject json) throws TwitterException {
        super(res);
        try {
            JSONObject relationship = json.getJSONObject("relationship");
            JSONObject sourceJson = relationship.getJSONObject(SocialConstants.PARAM_SOURCE);
            JSONObject targetJson = relationship.getJSONObject("target");
            this.sourceUserId = ParseUtil.getLong("id", sourceJson);
            this.targetUserId = ParseUtil.getLong("id", targetJson);
            this.sourceUserScreenName = ParseUtil.getUnescapedString("screen_name", sourceJson);
            this.targetUserScreenName = ParseUtil.getUnescapedString("screen_name", targetJson);
            this.sourceBlockingTarget = ParseUtil.getBoolean("blocking", sourceJson);
            this.sourceFollowingTarget = ParseUtil.getBoolean("following", sourceJson);
            this.sourceFollowedByTarget = ParseUtil.getBoolean("followed_by", sourceJson);
            this.sourceCanDm = ParseUtil.getBoolean("can_dm", sourceJson);
            this.sourceMutingTarget = ParseUtil.getBoolean("muting", sourceJson);
            this.sourceNotificationsEnabled = ParseUtil.getBoolean("notifications_enabled", sourceJson);
            this.wantRetweets = ParseUtil.getBoolean("want_retweets", sourceJson);
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    static ResponseList<Relationship> createRelationshipList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<Relationship> relationships = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Relationship relationship = new RelationshipJSONImpl(json);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(relationship, json);
                }
                relationships.add(relationship);
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(relationships, list);
            }
            return relationships;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public long getSourceUserId() {
        return this.sourceUserId;
    }

    public long getTargetUserId() {
        return this.targetUserId;
    }

    public boolean isSourceBlockingTarget() {
        return this.sourceBlockingTarget;
    }

    public String getSourceUserScreenName() {
        return this.sourceUserScreenName;
    }

    public String getTargetUserScreenName() {
        return this.targetUserScreenName;
    }

    public boolean isSourceFollowingTarget() {
        return this.sourceFollowingTarget;
    }

    public boolean isTargetFollowingSource() {
        return this.sourceFollowedByTarget;
    }

    public boolean isSourceFollowedByTarget() {
        return this.sourceFollowedByTarget;
    }

    public boolean isTargetFollowedBySource() {
        return this.sourceFollowingTarget;
    }

    public boolean canSourceDm() {
        return this.sourceCanDm;
    }

    public boolean isSourceMutingTarget() {
        return this.sourceMutingTarget;
    }

    public boolean isSourceNotificationsEnabled() {
        return this.sourceNotificationsEnabled;
    }

    public boolean isSourceWantRetweets() {
        return this.wantRetweets;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipJSONImpl that = (RelationshipJSONImpl) o;
        if (this.sourceBlockingTarget != that.sourceBlockingTarget) {
            return false;
        }
        if (this.sourceCanDm != that.sourceCanDm) {
            return false;
        }
        if (this.sourceFollowedByTarget != that.sourceFollowedByTarget) {
            return false;
        }
        if (this.sourceFollowingTarget != that.sourceFollowingTarget) {
            return false;
        }
        if (this.sourceMutingTarget != that.sourceMutingTarget) {
            return false;
        }
        if (this.sourceNotificationsEnabled != that.sourceNotificationsEnabled) {
            return false;
        }
        if (this.sourceUserId != that.sourceUserId) {
            return false;
        }
        if (this.targetUserId != that.targetUserId) {
            return false;
        }
        if (this.wantRetweets != that.wantRetweets) {
            return false;
        }
        if (this.sourceUserScreenName == null ? that.sourceUserScreenName != null : !this.sourceUserScreenName.equals(that.sourceUserScreenName)) {
            return false;
        }
        if (this.targetUserScreenName != null) {
            if (this.targetUserScreenName.equals(that.targetUserScreenName)) {
                return true;
            }
        } else if (that.targetUserScreenName == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int hashCode = ((((int) (this.targetUserId ^ (this.targetUserId >>> 32))) * 31) + (this.targetUserScreenName != null ? this.targetUserScreenName.hashCode() : 0)) * 31;
        if (this.sourceBlockingTarget) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (hashCode + i) * 31;
        if (this.sourceNotificationsEnabled) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (hashCode + i) * 31;
        if (this.sourceFollowingTarget) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (hashCode + i) * 31;
        if (this.sourceFollowedByTarget) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (hashCode + i) * 31;
        if (this.sourceCanDm) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (hashCode + i) * 31;
        if (this.sourceMutingTarget) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (((hashCode + i) * 31) + ((int) (this.sourceUserId ^ (this.sourceUserId >>> 32)))) * 31;
        if (this.sourceUserScreenName != null) {
            i = this.sourceUserScreenName.hashCode();
        } else {
            i = 0;
        }
        i = (hashCode + i) * 31;
        if (!this.wantRetweets) {
            i2 = 0;
        }
        return i + i2;
    }

    public String toString() {
        return "RelationshipJSONImpl{targetUserId=" + this.targetUserId + ", targetUserScreenName='" + this.targetUserScreenName + '\'' + ", sourceBlockingTarget=" + this.sourceBlockingTarget + ", sourceNotificationsEnabled=" + this.sourceNotificationsEnabled + ", sourceFollowingTarget=" + this.sourceFollowingTarget + ", sourceFollowedByTarget=" + this.sourceFollowedByTarget + ", sourceCanDm=" + this.sourceCanDm + ", sourceMutingTarget=" + this.sourceMutingTarget + ", sourceUserId=" + this.sourceUserId + ", sourceUserScreenName='" + this.sourceUserScreenName + '\'' + ", wantRetweets=" + this.wantRetweets + '}';
    }
}
