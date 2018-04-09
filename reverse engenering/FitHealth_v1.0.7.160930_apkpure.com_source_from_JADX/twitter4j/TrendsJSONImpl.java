package twitter4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import p031u.aly.au;
import twitter4j.conf.Configuration;

final class TrendsJSONImpl extends TwitterResponseImpl implements Trends, Serializable {
    private static final long serialVersionUID = 2054973282133379835L;
    private Date asOf;
    private Location location;
    private Date trendAt;
    private Trend[] trends;

    public int compareTo(Trends that) {
        return this.trendAt.compareTo(that.getTrendAt());
    }

    TrendsJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        init(res.asString(), conf.isJSONStoreEnabled());
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, res.asString());
        }
    }

    TrendsJSONImpl(String jsonStr) throws TwitterException {
        this(jsonStr, false);
    }

    TrendsJSONImpl(String jsonStr, boolean storeJSON) throws TwitterException {
        init(jsonStr, storeJSON);
    }

    void init(String jsonStr, boolean storeJSON) throws TwitterException {
        try {
            JSONArray array;
            JSONObject json;
            if (jsonStr.startsWith("[")) {
                array = new JSONArray(jsonStr);
                if (array.length() > 0) {
                    json = array.getJSONObject(0);
                } else {
                    throw new TwitterException("No trends found on the specified woeid");
                }
            }
            json = new JSONObject(jsonStr);
            this.asOf = ParseUtil.parseTrendsDate(json.getString("as_of"));
            this.location = extractLocation(json, storeJSON);
            array = json.getJSONArray("trends");
            this.trendAt = this.asOf;
            this.trends = jsonArrayToTrendArray(array, storeJSON);
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage(), jsone);
        }
    }

    TrendsJSONImpl(Date asOf, Location location, Date trendAt, Trend[] trends) {
        this.asOf = asOf;
        this.location = location;
        this.trendAt = trendAt;
        this.trends = trends;
    }

    static ResponseList<Trends> createTrendsList(HttpResponse res, boolean storeJSON) throws TwitterException {
        JSONObject json = res.asJSONObject();
        try {
            Date asOf = ParseUtil.parseTrendsDate(json.getString("as_of"));
            JSONObject trendsJson = json.getJSONObject("trends");
            Location location = extractLocation(json, storeJSON);
            ResponseList<Trends> trends = new ResponseListImpl(trendsJson.length(), res);
            Iterator ite = trendsJson.keys();
            while (ite.hasNext()) {
                String key = (String) ite.next();
                Trend[] trendsArray = jsonArrayToTrendArray(trendsJson.getJSONArray(key), storeJSON);
                if (key.length() == 19) {
                    trends.add(new TrendsJSONImpl(asOf, location, ParseUtil.getDate(key, "yyyy-MM-dd HH:mm:ss"), trendsArray));
                } else if (key.length() == 16) {
                    trends.add(new TrendsJSONImpl(asOf, location, ParseUtil.getDate(key, "yyyy-MM-dd HH:mm"), trendsArray));
                } else if (key.length() == 10) {
                    trends.add(new TrendsJSONImpl(asOf, location, ParseUtil.getDate(key, "yyyy-MM-dd"), trendsArray));
                }
            }
            Collections.sort(trends);
            return trends;
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + res.asString(), jsone);
        }
    }

    private static Location extractLocation(JSONObject json, boolean storeJSON) throws TwitterException {
        if (json.isNull(au.f3569X)) {
            return null;
        }
        try {
            ResponseList<Location> locations = LocationJSONImpl.createLocationList(json.getJSONArray(au.f3569X), storeJSON);
            if (locations.size() != 0) {
                return (Location) locations.get(0);
            }
            return null;
        } catch (JSONException e) {
            throw new AssertionError("locations can't be null");
        }
    }

    private static Trend[] jsonArrayToTrendArray(JSONArray array, boolean storeJSON) throws JSONException {
        Trend[] trends = new Trend[array.length()];
        for (int i = 0; i < array.length(); i++) {
            trends[i] = new TrendJSONImpl(array.getJSONObject(i), storeJSON);
        }
        return trends;
    }

    public Trend[] getTrends() {
        return this.trends;
    }

    public Location getLocation() {
        return this.location;
    }

    public Date getAsOf() {
        return this.asOf;
    }

    public Date getTrendAt() {
        return this.trendAt;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trends)) {
            return false;
        }
        Trends trends1 = (Trends) o;
        if (this.asOf == null ? trends1.getAsOf() != null : !this.asOf.equals(trends1.getAsOf())) {
            return false;
        }
        if (this.trendAt == null ? trends1.getTrendAt() != null : !this.trendAt.equals(trends1.getTrendAt())) {
            return false;
        }
        if (Arrays.equals(this.trends, trends1.getTrends())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.asOf != null) {
            result = this.asOf.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.trendAt != null) {
            hashCode = this.trendAt.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.trends != null) {
            i = Arrays.hashCode(this.trends);
        }
        return hashCode + i;
    }

    public String toString() {
        Object obj;
        StringBuilder append = new StringBuilder().append("TrendsJSONImpl{asOf=").append(this.asOf).append(", trendAt=").append(this.trendAt).append(", trends=");
        if (this.trends == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.trends);
        }
        return append.append(obj).append('}').toString();
    }
}
