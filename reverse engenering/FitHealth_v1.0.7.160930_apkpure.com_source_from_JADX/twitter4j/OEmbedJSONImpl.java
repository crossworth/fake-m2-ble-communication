package twitter4j;

import java.io.Serializable;
import twitter4j.conf.Configuration;

public class OEmbedJSONImpl extends TwitterResponseImpl implements OEmbed, Serializable {
    private static final long serialVersionUID = -2207801480251709819L;
    private String authorName;
    private String authorURL;
    private long cacheAge;
    private String html;
    private String url;
    private String version;
    private int width;

    public /* bridge */ /* synthetic */ int getAccessLevel() {
        return super.getAccessLevel();
    }

    public /* bridge */ /* synthetic */ RateLimitStatus getRateLimitStatus() {
        return super.getRateLimitStatus();
    }

    OEmbedJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    OEmbedJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            this.html = json.getString("html");
            this.authorName = json.getString("author_name");
            this.url = json.getString("url");
            this.version = json.getString("version");
            this.cacheAge = json.getLong("cache_age");
            this.authorURL = json.getString("author_url");
            this.width = json.getInt("width");
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public String getHtml() {
        return this.html;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public String getURL() {
        return this.url;
    }

    public String getVersion() {
        return this.version;
    }

    public long getCacheAge() {
        return this.cacheAge;
    }

    public String getAuthorURL() {
        return this.authorURL;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OEmbedJSONImpl that = (OEmbedJSONImpl) o;
        if (this.cacheAge != that.cacheAge) {
            return false;
        }
        if (this.width != that.width) {
            return false;
        }
        if (this.authorName == null ? that.authorName != null : !this.authorName.equals(that.authorName)) {
            return false;
        }
        if (this.authorURL == null ? that.authorURL != null : !this.authorURL.equals(that.authorURL)) {
            return false;
        }
        if (this.html == null ? that.html != null : !this.html.equals(that.html)) {
            return false;
        }
        if (this.url == null ? that.url != null : !this.url.equals(that.url)) {
            return false;
        }
        if (this.version != null) {
            if (this.version.equals(that.version)) {
                return true;
            }
        } else if (that.version == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.html != null) {
            result = this.html.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.authorName != null) {
            hashCode = this.authorName.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.url != null) {
            hashCode = this.url.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.version != null) {
            hashCode = this.version.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (((i2 + hashCode) * 31) + ((int) (this.cacheAge ^ (this.cacheAge >>> 32)))) * 31;
        if (this.authorURL != null) {
            i = this.authorURL.hashCode();
        }
        return ((hashCode + i) * 31) + this.width;
    }

    public String toString() {
        return "OEmbedJSONImpl{html='" + this.html + '\'' + ", authorName='" + this.authorName + '\'' + ", url='" + this.url + '\'' + ", version='" + this.version + '\'' + ", cacheAge=" + this.cacheAge + ", authorURL='" + this.authorURL + '\'' + ", width=" + this.width + '}';
    }
}
