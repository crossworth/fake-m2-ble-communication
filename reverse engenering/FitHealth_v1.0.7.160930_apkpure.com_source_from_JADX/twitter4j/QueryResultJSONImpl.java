package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import twitter4j.conf.Configuration;

final class QueryResultJSONImpl extends TwitterResponseImpl implements QueryResult, Serializable {
    private static final long serialVersionUID = -5359566235429947156L;
    private double completedIn;
    private int count;
    private long maxId;
    private String nextResults;
    private String query;
    private String refreshUrl;
    private long sinceId;
    private List<Status> tweets;

    QueryResultJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            JSONObject searchMetaData = json.getJSONObject("search_metadata");
            this.completedIn = ParseUtil.getDouble("completed_in", searchMetaData);
            this.count = ParseUtil.getInt("count", searchMetaData);
            this.maxId = ParseUtil.getLong("max_id", searchMetaData);
            this.nextResults = searchMetaData.has("next_results") ? searchMetaData.getString("next_results") : null;
            this.query = ParseUtil.getURLDecodedString("query", searchMetaData);
            this.refreshUrl = ParseUtil.getUnescapedString("refresh_url", searchMetaData);
            this.sinceId = ParseUtil.getLong("since_id", searchMetaData);
            JSONArray array = json.getJSONArray("statuses");
            this.tweets = new ArrayList(array.length());
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            for (int i = 0; i < array.length(); i++) {
                this.tweets.add(new StatusJSONImpl(array.getJSONObject(i), conf));
            }
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    QueryResultJSONImpl(Query query) {
        this.sinceId = query.getSinceId();
        this.count = query.getCount();
        this.tweets = new ArrayList(0);
    }

    public long getSinceId() {
        return this.sinceId;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public String getRefreshURL() {
        return this.refreshUrl;
    }

    public int getCount() {
        return this.count;
    }

    public double getCompletedIn() {
        return this.completedIn;
    }

    public String getQuery() {
        return this.query;
    }

    public List<Status> getTweets() {
        return this.tweets;
    }

    public Query nextQuery() {
        if (this.nextResults == null) {
            return null;
        }
        return Query.createWithNextPageQuery(this.nextResults);
    }

    public boolean hasNext() {
        return this.nextResults != null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryResult that = (QueryResult) o;
        if (Double.compare(that.getCompletedIn(), this.completedIn) != 0) {
            return false;
        }
        if (this.maxId != that.getMaxId()) {
            return false;
        }
        if (this.count != that.getCount()) {
            return false;
        }
        if (this.sinceId != that.getSinceId()) {
            return false;
        }
        if (!this.query.equals(that.getQuery())) {
            return false;
        }
        if (this.refreshUrl == null ? that.getRefreshURL() != null : !this.refreshUrl.equals(that.getRefreshURL())) {
            return false;
        }
        if (this.tweets != null) {
            if (this.tweets.equals(that.getTweets())) {
                return true;
            }
        } else if (that.getTweets() == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int i2 = ((((int) (this.sinceId ^ (this.sinceId >>> 32))) * 31) + ((int) (this.maxId ^ (this.maxId >>> 32)))) * 31;
        if (this.refreshUrl != null) {
            hashCode = this.refreshUrl.hashCode();
        } else {
            hashCode = 0;
        }
        int result = ((i2 + hashCode) * 31) + this.count;
        long temp = this.completedIn != 0.0d ? Double.doubleToLongBits(this.completedIn) : 0;
        hashCode = ((((result * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + this.query.hashCode()) * 31;
        if (this.tweets != null) {
            i = this.tweets.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "QueryResultJSONImpl{sinceId=" + this.sinceId + ", maxId=" + this.maxId + ", refreshUrl='" + this.refreshUrl + '\'' + ", count=" + this.count + ", completedIn=" + this.completedIn + ", query='" + this.query + '\'' + ", tweets=" + this.tweets + '}';
    }
}
