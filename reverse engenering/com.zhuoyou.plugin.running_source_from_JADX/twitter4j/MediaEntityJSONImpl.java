package twitter4j;

import com.umeng.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import java.util.Map;

public class MediaEntityJSONImpl extends EntityIndex implements MediaEntity {
    private static final long serialVersionUID = 3609683338035442290L;
    protected String displayURL;
    protected String expandedURL;
    protected long id;
    protected String mediaURL;
    protected String mediaURLHttps;
    protected Map<Integer, twitter4j.MediaEntity.Size> sizes;
    protected String type;
    protected String url;

    static class Size implements twitter4j.MediaEntity.Size {
        private static final long serialVersionUID = -2515842281909325169L;
        int height;
        int resize;
        int width;

        Size() {
        }

        Size(JSONObject json) throws JSONException {
            this.width = json.getInt("w");
            this.height = json.getInt("h");
            this.resize = "fit".equals(json.getString("resize")) ? 100 : 101;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getResize() {
            return this.resize;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Size)) {
                return false;
            }
            Size size = (Size) o;
            if (this.height != size.height) {
                return false;
            }
            if (this.resize != size.resize) {
                return false;
            }
            if (this.width != size.width) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.width * 31) + this.height) * 31) + this.resize;
        }

        public String toString() {
            return "Size{width=" + this.width + ", height=" + this.height + ", resize=" + this.resize + '}';
        }
    }

    public /* bridge */ /* synthetic */ int compareTo(EntityIndex entityIndex) {
        return super.compareTo(entityIndex);
    }

    MediaEntityJSONImpl(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));
            this.id = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, json);
            this.url = json.getString("url");
            this.expandedURL = json.getString("expanded_url");
            this.mediaURL = json.getString("media_url");
            this.mediaURLHttps = json.getString("media_url_https");
            this.displayURL = json.getString("display_url");
            JSONObject sizes = json.getJSONObject("sizes");
            this.sizes = new HashMap(4);
            addMediaEntitySizeIfNotNull(this.sizes, sizes, twitter4j.MediaEntity.Size.LARGE, "large");
            addMediaEntitySizeIfNotNull(this.sizes, sizes, twitter4j.MediaEntity.Size.MEDIUM, "medium");
            addMediaEntitySizeIfNotNull(this.sizes, sizes, twitter4j.MediaEntity.Size.SMALL, "small");
            addMediaEntitySizeIfNotNull(this.sizes, sizes, twitter4j.MediaEntity.Size.THUMB, "thumb");
            if (!json.isNull("type")) {
                this.type = json.getString("type");
            }
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    private void addMediaEntitySizeIfNotNull(Map<Integer, twitter4j.MediaEntity.Size> sizes, JSONObject sizesJSON, Integer size, String key) throws JSONException {
        if (!sizesJSON.isNull(key)) {
            sizes.put(size, new Size(sizesJSON.getJSONObject(key)));
        }
    }

    MediaEntityJSONImpl() {
    }

    public long getId() {
        return this.id;
    }

    public String getMediaURL() {
        return this.mediaURL;
    }

    public String getMediaURLHttps() {
        return this.mediaURLHttps;
    }

    public String getText() {
        return this.url;
    }

    public String getURL() {
        return this.url;
    }

    public String getDisplayURL() {
        return this.displayURL;
    }

    public String getExpandedURL() {
        return this.expandedURL;
    }

    public Map<Integer, twitter4j.MediaEntity.Size> getSizes() {
        return this.sizes;
    }

    public String getType() {
        return this.type;
    }

    public int getStart() {
        return super.getStart();
    }

    public int getEnd() {
        return super.getEnd();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaEntityJSONImpl)) {
            return false;
        }
        if (this.id != ((MediaEntityJSONImpl) o).id) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (int) (this.id ^ (this.id >>> 32));
    }

    public String toString() {
        return "MediaEntityJSONImpl{id=" + this.id + ", url=" + this.url + ", mediaURL=" + this.mediaURL + ", mediaURLHttps=" + this.mediaURLHttps + ", expandedURL=" + this.expandedURL + ", displayURL='" + this.displayURL + '\'' + ", sizes=" + this.sizes + ", type=" + this.type + '}';
    }
}
