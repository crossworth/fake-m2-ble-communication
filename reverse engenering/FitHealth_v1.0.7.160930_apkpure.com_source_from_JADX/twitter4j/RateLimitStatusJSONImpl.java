package twitter4j;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import twitter4j.conf.Configuration;

final class RateLimitStatusJSONImpl implements RateLimitStatus, Serializable {
    private static final long serialVersionUID = 7790337632915862445L;
    private int limit;
    private int remaining;
    private int resetTimeInSeconds;
    private int secondsUntilReset;

    static Map<String, RateLimitStatus> createRateLimitStatuses(HttpResponse res, Configuration conf) throws TwitterException {
        JSONObject json = res.asJSONObject();
        Map<String, RateLimitStatus> map = createRateLimitStatuses(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(map, json);
        }
        return map;
    }

    static Map<String, RateLimitStatus> createRateLimitStatuses(JSONObject json) throws TwitterException {
        Map<String, RateLimitStatus> map = new HashMap();
        try {
            JSONObject resources = json.getJSONObject("resources");
            Iterator resourceKeys = resources.keys();
            while (resourceKeys.hasNext()) {
                JSONObject resource = resources.getJSONObject((String) resourceKeys.next());
                Iterator endpointKeys = resource.keys();
                while (endpointKeys.hasNext()) {
                    String endpoint = (String) endpointKeys.next();
                    map.put(endpoint, new RateLimitStatusJSONImpl(resource.getJSONObject(endpoint)));
                }
            }
            return Collections.unmodifiableMap(map);
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    private RateLimitStatusJSONImpl(int limit, int remaining, int resetTimeInSeconds) {
        this.limit = limit;
        this.remaining = remaining;
        this.resetTimeInSeconds = resetTimeInSeconds;
        this.secondsUntilReset = (int) (((((long) resetTimeInSeconds) * 1000) - System.currentTimeMillis()) / 1000);
    }

    RateLimitStatusJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    void init(JSONObject json) throws TwitterException {
        this.limit = ParseUtil.getInt("limit", json);
        this.remaining = ParseUtil.getInt("remaining", json);
        this.resetTimeInSeconds = ParseUtil.getInt("reset", json);
        this.secondsUntilReset = (int) (((((long) this.resetTimeInSeconds) * 1000) - System.currentTimeMillis()) / 1000);
    }

    static RateLimitStatus createFromResponseHeader(HttpResponse res) {
        if (res == null) {
            return null;
        }
        String strLimit = res.getResponseHeader("X-Rate-Limit-Limit");
        if (strLimit == null) {
            return null;
        }
        int limit = Integer.parseInt(strLimit);
        String remaining = res.getResponseHeader("X-Rate-Limit-Remaining");
        if (remaining == null) {
            return null;
        }
        int remainingHits = Integer.parseInt(remaining);
        String reset = res.getResponseHeader("X-Rate-Limit-Reset");
        if (reset != null) {
            return new RateLimitStatusJSONImpl(limit, remainingHits, (int) Long.parseLong(reset));
        }
        return null;
    }

    public int getRemaining() {
        return this.remaining;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getResetTimeInSeconds() {
        return this.resetTimeInSeconds;
    }

    public int getSecondsUntilReset() {
        return this.secondsUntilReset;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateLimitStatusJSONImpl that = (RateLimitStatusJSONImpl) o;
        if (this.limit != that.limit) {
            return false;
        }
        if (this.remaining != that.remaining) {
            return false;
        }
        if (this.resetTimeInSeconds != that.resetTimeInSeconds) {
            return false;
        }
        if (this.secondsUntilReset != that.secondsUntilReset) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.remaining * 31) + this.limit) * 31) + this.resetTimeInSeconds) * 31) + this.secondsUntilReset;
    }

    public String toString() {
        return "RateLimitStatusJSONImpl{remaining=" + this.remaining + ", limit=" + this.limit + ", resetTimeInSeconds=" + this.resetTimeInSeconds + ", secondsUntilReset=" + this.secondsUntilReset + '}';
    }
}
