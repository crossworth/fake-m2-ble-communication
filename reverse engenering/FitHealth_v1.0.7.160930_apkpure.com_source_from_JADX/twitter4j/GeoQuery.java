package twitter4j;

import com.zhuoyou.plugin.database.DataBaseContants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import p031u.aly.au;

public final class GeoQuery implements Serializable {
    private static final long serialVersionUID = 5434503339001056634L;
    private String accuracy = null;
    private String granularity = null;
    private String ip = null;
    private GeoLocation location;
    private int maxResults = -1;
    private String query = null;

    public GeoQuery(GeoLocation location) {
        this.location = location;
    }

    public GeoQuery(String ip) {
        this.ip = ip;
    }

    public GeoLocation getLocation() {
        return this.location;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getIp() {
        return this.ip;
    }

    public String getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public GeoQuery accuracy(String accuracy) {
        setAccuracy(accuracy);
        return this;
    }

    public String getGranularity() {
        return this.granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public GeoQuery granularity(String granularity) {
        setGranularity(granularity);
        return this;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public GeoQuery maxResults(int maxResults) {
        setMaxResults(maxResults);
        return this;
    }

    HttpParameter[] asHttpParameterArray() {
        List params = new ArrayList();
        if (this.location != null) {
            appendParameter(au.f3570Y, this.location.getLatitude(), params);
            appendParameter("long", this.location.getLongitude(), params);
        }
        if (this.ip != null) {
            appendParameter("ip", this.ip, params);
        }
        appendParameter(DataBaseContants.ACCURACY, this.accuracy, params);
        appendParameter("query", this.query, params);
        appendParameter("granularity", this.granularity, params);
        appendParameter("max_results", this.maxResults, params);
        return (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
    }

    private void appendParameter(String name, String value, List<HttpParameter> params) {
        if (value != null) {
            params.add(new HttpParameter(name, value));
        }
    }

    private void appendParameter(String name, int value, List<HttpParameter> params) {
        if (value > 0) {
            params.add(new HttpParameter(name, String.valueOf(value)));
        }
    }

    private void appendParameter(String name, double value, List<HttpParameter> params) {
        params.add(new HttpParameter(name, String.valueOf(value)));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeoQuery geoQuery = (GeoQuery) o;
        if (this.maxResults != geoQuery.maxResults) {
            return false;
        }
        if (this.accuracy == null ? geoQuery.accuracy != null : !this.accuracy.equals(geoQuery.accuracy)) {
            return false;
        }
        if (this.granularity == null ? geoQuery.granularity != null : !this.granularity.equals(geoQuery.granularity)) {
            return false;
        }
        if (this.ip == null ? geoQuery.ip != null : !this.ip.equals(geoQuery.ip)) {
            return false;
        }
        if (this.location != null) {
            if (this.location.equals(geoQuery.location)) {
                return true;
            }
        } else if (geoQuery.location == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.location != null) {
            result = this.location.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.ip != null) {
            hashCode = this.ip.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.accuracy != null) {
            hashCode = this.accuracy.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.granularity != null) {
            i = this.granularity.hashCode();
        }
        return ((hashCode + i) * 31) + this.maxResults;
    }

    public String toString() {
        return "GeoQuery{location=" + this.location + ", query='" + this.query + '\'' + ", ip='" + this.ip + '\'' + ", accuracy='" + this.accuracy + '\'' + ", granularity='" + this.granularity + '\'' + ", maxResults=" + this.maxResults + '}';
    }
}
