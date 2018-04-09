package twitter4j;

import java.io.Serializable;

final class TrendJSONImpl implements Trend, Serializable {
    private static final long serialVersionUID = -4353426776065521132L;
    private final String name;
    private String query;
    private String url;

    TrendJSONImpl(JSONObject json, boolean storeJSON) {
        this.url = null;
        this.query = null;
        this.name = ParseUtil.getRawString("name", json);
        this.url = ParseUtil.getRawString("url", json);
        this.query = ParseUtil.getRawString("query", json);
        if (storeJSON) {
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    TrendJSONImpl(JSONObject json) {
        this(json, false);
    }

    public String getName() {
        return this.name;
    }

    public String getURL() {
        return this.url;
    }

    public String getQuery() {
        return this.query;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trend)) {
            return false;
        }
        Trend trend = (Trend) o;
        if (!this.name.equals(trend.getName())) {
            return false;
        }
        if (this.query == null ? trend.getQuery() != null : !this.query.equals(trend.getQuery())) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(trend.getURL())) {
                return true;
            }
        } else if (trend.getURL() == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = this.name.hashCode() * 31;
        if (this.url != null) {
            hashCode = this.url.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (hashCode2 + hashCode) * 31;
        if (this.query != null) {
            i = this.query.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "TrendJSONImpl{name='" + this.name + '\'' + ", url='" + this.url + '\'' + ", query='" + this.query + '\'' + '}';
    }
}
