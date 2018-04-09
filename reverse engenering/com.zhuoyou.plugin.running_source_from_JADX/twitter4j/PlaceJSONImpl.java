package twitter4j;

import com.umeng.facebook.share.internal.ShareConstants;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import twitter4j.conf.Configuration;

final class PlaceJSONImpl extends TwitterResponseImpl implements Place, Serializable {
    private static final long serialVersionUID = -6368276880878829754L;
    private GeoLocation[][] boundingBoxCoordinates;
    private String boundingBoxType;
    private Place[] containedWithIn;
    private String country;
    private String countryCode;
    private String fullName;
    private GeoLocation[][] geometryCoordinates;
    private String geometryType;
    private String id;
    private String name;
    private String placeType;
    private String streetAddress;
    private String url;

    PlaceJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    PlaceJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    PlaceJSONImpl() {
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            this.name = ParseUtil.getUnescapedString("name", json);
            this.streetAddress = ParseUtil.getUnescapedString("street_address", json);
            this.countryCode = ParseUtil.getRawString("country_code", json);
            this.id = ParseUtil.getRawString(ShareConstants.WEB_DIALOG_PARAM_ID, json);
            this.country = ParseUtil.getRawString("country", json);
            if (json.isNull("place_type")) {
                this.placeType = ParseUtil.getRawString("type", json);
            } else {
                this.placeType = ParseUtil.getRawString("place_type", json);
            }
            this.url = ParseUtil.getRawString("url", json);
            this.fullName = ParseUtil.getRawString("full_name", json);
            if (json.isNull("bounding_box")) {
                this.boundingBoxType = null;
                this.boundingBoxCoordinates = (GeoLocation[][]) null;
            } else {
                JSONObject boundingBoxJSON = json.getJSONObject("bounding_box");
                this.boundingBoxType = ParseUtil.getRawString("type", boundingBoxJSON);
                this.boundingBoxCoordinates = JSONImplFactory.coordinatesAsGeoLocationArray(boundingBoxJSON.getJSONArray("coordinates"));
            }
            if (json.isNull("geometry")) {
                this.geometryType = null;
                this.geometryCoordinates = (GeoLocation[][]) null;
            } else {
                JSONObject geometryJSON = json.getJSONObject("geometry");
                this.geometryType = ParseUtil.getRawString("type", geometryJSON);
                JSONArray array = geometryJSON.getJSONArray("coordinates");
                if (this.geometryType.equals("Point")) {
                    this.geometryCoordinates = (GeoLocation[][]) Array.newInstance(GeoLocation.class, new int[]{1, 1});
                    this.geometryCoordinates[0][0] = new GeoLocation(array.getDouble(1), array.getDouble(0));
                } else if (this.geometryType.equals("Polygon")) {
                    this.geometryCoordinates = JSONImplFactory.coordinatesAsGeoLocationArray(array);
                } else {
                    this.geometryType = null;
                    this.geometryCoordinates = (GeoLocation[][]) null;
                }
            }
            if (json.isNull("contained_within")) {
                this.containedWithIn = null;
                return;
            }
            JSONArray containedWithInJSON = json.getJSONArray("contained_within");
            this.containedWithIn = new Place[containedWithInJSON.length()];
            for (int i = 0; i < containedWithInJSON.length(); i++) {
                this.containedWithIn[i] = new PlaceJSONImpl(containedWithInJSON.getJSONObject(i));
            }
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    public int compareTo(Place that) {
        return this.id.compareTo(that.getId());
    }

    static ResponseList<Place> createPlaceList(HttpResponse res, Configuration conf) throws TwitterException {
        JSONObject json = null;
        try {
            json = res.asJSONObject();
            return createPlaceList(json.getJSONObject("result").getJSONArray("places"), res, conf);
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    static ResponseList<Place> createPlaceList(JSONArray list, HttpResponse res, Configuration conf) throws TwitterException {
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        try {
            int size = list.length();
            ResponseList<Place> places = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Place place = new PlaceJSONImpl(json);
                places.add(place);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(place, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(places, list);
            }
            return places;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getId() {
        return this.id;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPlaceType() {
        return this.placeType;
    }

    public String getURL() {
        return this.url;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getBoundingBoxType() {
        return this.boundingBoxType;
    }

    public GeoLocation[][] getBoundingBoxCoordinates() {
        return this.boundingBoxCoordinates;
    }

    public String getGeometryType() {
        return this.geometryType;
    }

    public GeoLocation[][] getGeometryCoordinates() {
        return this.geometryCoordinates;
    }

    public Place[] getContainedWithIn() {
        return this.containedWithIn;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Place) && ((Place) obj).getId().equals(this.id)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        Object obj;
        Object obj2 = null;
        StringBuilder append = new StringBuilder().append("PlaceJSONImpl{name='").append(this.name).append('\'').append(", streetAddress='").append(this.streetAddress).append('\'').append(", countryCode='").append(this.countryCode).append('\'').append(", id='").append(this.id).append('\'').append(", country='").append(this.country).append('\'').append(", placeType='").append(this.placeType).append('\'').append(", url='").append(this.url).append('\'').append(", fullName='").append(this.fullName).append('\'').append(", boundingBoxType='").append(this.boundingBoxType).append('\'').append(", boundingBoxCoordinates=");
        if (this.boundingBoxCoordinates == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.boundingBoxCoordinates);
        }
        append = append.append(obj).append(", geometryType='").append(this.geometryType).append('\'').append(", geometryCoordinates=");
        if (this.geometryCoordinates == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.geometryCoordinates);
        }
        StringBuilder append2 = append.append(obj).append(", containedWithIn=");
        if (this.containedWithIn != null) {
            obj2 = Arrays.asList(this.containedWithIn);
        }
        return append2.append(obj2).append('}').toString();
    }
}
