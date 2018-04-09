package twitter4j;

import twitter4j.conf.Configuration;

final class LocationJSONImpl implements Location {
    private static final long serialVersionUID = -1312752311160422264L;
    private final String countryCode;
    private final String countryName;
    private final String name;
    private final int placeCode;
    private final String placeName;
    private final String url;
    private final int woeid;

    LocationJSONImpl(JSONObject location) throws TwitterException {
        try {
            this.woeid = ParseUtil.getInt("woeid", location);
            this.countryName = ParseUtil.getUnescapedString("country", location);
            this.countryCode = ParseUtil.getRawString("countryCode", location);
            if (location.isNull("placeType")) {
                this.placeName = null;
                this.placeCode = -1;
            } else {
                JSONObject placeJSON = location.getJSONObject("placeType");
                this.placeName = ParseUtil.getUnescapedString("name", placeJSON);
                this.placeCode = ParseUtil.getInt("code", placeJSON);
            }
            this.name = ParseUtil.getUnescapedString("name", location);
            this.url = ParseUtil.getUnescapedString("url", location);
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    static ResponseList<Location> createLocationList(HttpResponse res, Configuration conf) throws TwitterException {
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        return createLocationList(res.asJSONArray(), conf.isJSONStoreEnabled());
    }

    static ResponseList<Location> createLocationList(JSONArray list, boolean storeJSON) throws TwitterException {
        try {
            int size = list.length();
            ResponseList<Location> locations = new ResponseListImpl(size, null);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Location location = new LocationJSONImpl(json);
                locations.add(location);
                if (storeJSON) {
                    TwitterObjectFactory.registerJSONObject(location, json);
                }
            }
            if (storeJSON) {
                TwitterObjectFactory.registerJSONObject(locations, list);
            }
            return locations;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public int getWoeid() {
        return this.woeid;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public int getPlaceCode() {
        return this.placeCode;
    }

    public String getName() {
        return this.name;
    }

    public String getURL() {
        return this.url;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationJSONImpl)) {
            return false;
        }
        if (this.woeid != ((LocationJSONImpl) o).woeid) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.woeid;
    }

    public String toString() {
        return "LocationJSONImpl{woeid=" + this.woeid + ", countryName='" + this.countryName + '\'' + ", countryCode='" + this.countryCode + '\'' + ", placeName='" + this.placeName + '\'' + ", placeCode='" + this.placeCode + '\'' + ", name='" + this.name + '\'' + ", url='" + this.url + '\'' + '}';
    }
}
