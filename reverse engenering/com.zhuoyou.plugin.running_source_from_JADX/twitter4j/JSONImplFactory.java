package twitter4j;

import twitter4j.conf.Configuration;

class JSONImplFactory implements ObjectFactory {
    private static final long serialVersionUID = -1853541456182663343L;
    private final Configuration conf;

    public JSONImplFactory(Configuration conf) {
        this.conf = conf;
    }

    public Status createStatus(HttpResponse res) throws TwitterException {
        return new StatusJSONImpl(res, this.conf);
    }

    static GeoLocation createGeoLocation(JSONObject json) throws TwitterException {
        try {
            if (json.isNull("coordinates")) {
                return null;
            }
            String coordinates = json.getJSONObject("coordinates").getString("coordinates");
            String[] point = coordinates.substring(1, coordinates.length() - 1).split(",");
            return new GeoLocation(Double.parseDouble(point[1]), Double.parseDouble(point[0]));
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    static GeoLocation[][] coordinatesAsGeoLocationArray(JSONArray coordinates) throws TwitterException {
        try {
            GeoLocation[][] boundingBox = new GeoLocation[coordinates.length()][];
            for (int i = 0; i < coordinates.length(); i++) {
                JSONArray array = coordinates.getJSONArray(i);
                boundingBox[i] = new GeoLocation[array.length()];
                for (int j = 0; j < array.length(); j++) {
                    JSONArray coordinate = array.getJSONArray(j);
                    boundingBox[i][j] = new GeoLocation(coordinate.getDouble(1), coordinate.getDouble(0));
                }
            }
            return boundingBox;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public static RateLimitStatus createRateLimitStatusFromResponseHeader(HttpResponse res) {
        return RateLimitStatusJSONImpl.createFromResponseHeader(res);
    }

    public User createUser(HttpResponse res) throws TwitterException {
        return new UserJSONImpl(res, this.conf);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JSONImplFactory)) {
            return false;
        }
        JSONImplFactory that = (JSONImplFactory) o;
        if (this.conf != null) {
            if (this.conf.equals(that.conf)) {
                return true;
            }
        } else if (that.conf == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.conf != null ? this.conf.hashCode() : 0;
    }

    public String toString() {
        return "JSONImplFactory{conf=" + this.conf + '}';
    }
}
