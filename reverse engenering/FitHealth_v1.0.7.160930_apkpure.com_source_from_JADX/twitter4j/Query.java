package twitter4j;

import com.facebook.internal.ServerProtocol;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Query implements Serializable {
    public static final Unit KILOMETERS = Unit.km;
    public static final Unit MILES = Unit.mi;
    public static final ResultType MIXED = ResultType.mixed;
    public static final ResultType POPULAR = ResultType.popular;
    public static final ResultType RECENT = ResultType.recent;
    private static final HttpParameter WITH_TWITTER_USER_ID = new HttpParameter("with_twitter_user_id", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
    private static final long serialVersionUID = 7196404519192910019L;
    private int count = -1;
    private String geocode = null;
    private String lang = null;
    private String locale = null;
    private long maxId = -1;
    private String nextPageQuery = null;
    private String query = null;
    private ResultType resultType = null;
    private String since = null;
    private long sinceId = -1;
    private String until = null;

    public enum ResultType {
        popular,
        mixed,
        recent
    }

    public enum Unit {
        mi,
        km
    }

    public Query(String query) {
        this.query = query;
    }

    static Query createWithNextPageQuery(String nextPageQuery) {
        Query query = new Query();
        query.nextPageQuery = nextPageQuery;
        if (nextPageQuery != null) {
            String nextPageParameters = nextPageQuery.substring(1, nextPageQuery.length());
            Map<String, String> params = new LinkedHashMap();
            for (HttpParameter param : HttpParameter.decodeParameters(nextPageParameters)) {
                params.put(param.getName(), param.getValue());
            }
            if (params.containsKey("q")) {
                query.setQuery((String) params.get("q"));
            }
            if (params.containsKey("lang")) {
                query.setLang((String) params.get("lang"));
            }
            if (params.containsKey("locale")) {
                query.setLocale((String) params.get("locale"));
            }
            if (params.containsKey("max_id")) {
                query.setMaxId(Long.parseLong((String) params.get("max_id")));
            }
            if (params.containsKey("count")) {
                query.setCount(Integer.parseInt((String) params.get("count")));
            }
            if (params.containsKey("geocode")) {
                String[] parts = ((String) params.get("geocode")).split(SeparatorConstants.SEPARATOR_ADS_ID);
                double latitude = Double.parseDouble(parts[0]);
                double longitude = Double.parseDouble(parts[1]);
                double radius = 0.0d;
                Unit unit = null;
                String radiusstr = parts[2];
                for (Unit value : Unit.values()) {
                    if (radiusstr.endsWith(value.name())) {
                        radius = Double.parseDouble(radiusstr.substring(0, radiusstr.length() - 2));
                        unit = value;
                        break;
                    }
                }
                if (unit == null) {
                    throw new IllegalArgumentException("unrecognized geocode radius: " + radiusstr);
                }
                query.setGeoCode(new GeoLocation(latitude, longitude), radius, unit);
            }
            if (params.containsKey("result_type")) {
                query.setResultType(ResultType.valueOf((String) params.get("result_type")));
            }
        }
        return query;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Query query(String query) {
        setQuery(query);
        return this;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Query lang(String lang) {
        setLang(lang);
        return this;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Query locale(String locale) {
        setLocale(locale);
        return this;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public Query maxId(long maxId) {
        setMaxId(maxId);
        return this;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Query count(int count) {
        setCount(count);
        return this;
    }

    public String getSince() {
        return this.since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public Query since(String since) {
        setSince(since);
        return this;
    }

    public long getSinceId() {
        return this.sinceId;
    }

    public void setSinceId(long sinceId) {
        this.sinceId = sinceId;
    }

    public Query sinceId(long sinceId) {
        setSinceId(sinceId);
        return this;
    }

    public String getGeocode() {
        return this.geocode;
    }

    public void setGeoCode(GeoLocation location, double radius, Unit unit) {
        this.geocode = location.getLatitude() + SeparatorConstants.SEPARATOR_ADS_ID + location.getLongitude() + SeparatorConstants.SEPARATOR_ADS_ID + radius + unit.name();
    }

    public void setGeoCode(GeoLocation location, double radius, String unit) {
        this.geocode = location.getLatitude() + SeparatorConstants.SEPARATOR_ADS_ID + location.getLongitude() + SeparatorConstants.SEPARATOR_ADS_ID + radius + unit;
    }

    public Query geoCode(GeoLocation location, double radius, String unit) {
        setGeoCode(location, radius, unit);
        return this;
    }

    public String getUntil() {
        return this.until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public Query until(String until) {
        setUntil(until);
        return this;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Query resultType(ResultType resultType) {
        setResultType(resultType);
        return this;
    }

    HttpParameter[] asHttpParameterArray() {
        List params = new ArrayList(12);
        appendParameter("q", this.query, params);
        appendParameter("lang", this.lang, params);
        appendParameter("locale", this.locale, params);
        appendParameter("max_id", this.maxId, params);
        appendParameter("count", (long) this.count, params);
        appendParameter("since", this.since, params);
        appendParameter("since_id", this.sinceId, params);
        appendParameter("geocode", this.geocode, params);
        appendParameter("until", this.until, params);
        if (this.resultType != null) {
            params.add(new HttpParameter("result_type", this.resultType.name()));
        }
        params.add(WITH_TWITTER_USER_ID);
        return (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
    }

    private void appendParameter(String name, String value, List<HttpParameter> params) {
        if (value != null) {
            params.add(new HttpParameter(name, value));
        }
    }

    private void appendParameter(String name, long value, List<HttpParameter> params) {
        if (0 <= value) {
            params.add(new HttpParameter(name, String.valueOf(value)));
        }
    }

    String nextPage() {
        return this.nextPageQuery;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Query query1 = (Query) o;
        if (this.maxId != query1.maxId) {
            return false;
        }
        if (this.count != query1.count) {
            return false;
        }
        if (this.sinceId != query1.sinceId) {
            return false;
        }
        if (this.geocode == null ? query1.geocode != null : !this.geocode.equals(query1.geocode)) {
            return false;
        }
        if (this.lang == null ? query1.lang != null : !this.lang.equals(query1.lang)) {
            return false;
        }
        if (this.locale == null ? query1.locale != null : !this.locale.equals(query1.locale)) {
            return false;
        }
        if (this.nextPageQuery == null ? query1.nextPageQuery != null : !this.nextPageQuery.equals(query1.nextPageQuery)) {
            return false;
        }
        if (this.query == null ? query1.query != null : !this.query.equals(query1.query)) {
            return false;
        }
        if (this.resultType == null ? query1.resultType != null : !this.resultType.equals(query1.resultType)) {
            return false;
        }
        if (this.since == null ? query1.since != null : !this.since.equals(query1.since)) {
            return false;
        }
        if (this.until != null) {
            if (this.until.equals(query1.until)) {
                return true;
            }
        } else if (query1.until == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.query != null) {
            result = this.query.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.lang != null) {
            hashCode = this.lang.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.locale != null) {
            hashCode = this.locale.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (((((i2 + hashCode) * 31) + ((int) (this.maxId ^ (this.maxId >>> 32)))) * 31) + this.count) * 31;
        if (this.since != null) {
            hashCode = this.since.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (((i2 + hashCode) * 31) + ((int) (this.sinceId ^ (this.sinceId >>> 32)))) * 31;
        if (this.geocode != null) {
            hashCode = this.geocode.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.until != null) {
            hashCode = this.until.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.resultType != null) {
            hashCode = this.resultType.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.nextPageQuery != null) {
            i = this.nextPageQuery.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "Query{query='" + this.query + '\'' + ", lang='" + this.lang + '\'' + ", locale='" + this.locale + '\'' + ", maxId=" + this.maxId + ", count=" + this.count + ", since='" + this.since + '\'' + ", sinceId=" + this.sinceId + ", geocode='" + this.geocode + '\'' + ", until='" + this.until + '\'' + ", resultType='" + this.resultType + '\'' + ", nextPageQuery='" + this.nextPageQuery + '\'' + '}';
    }
}
