package twitter4j;

import java.io.Serializable;

public final class UploadedMedia implements Serializable {
    private static final long serialVersionUID = 5393092535610604718L;
    private int imageHeight;
    private String imageType;
    private int imageWidth;
    private long mediaId;
    private long size;

    UploadedMedia(JSONObject json) throws TwitterException {
        init(json);
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public String getImageType() {
        return this.imageType;
    }

    public long getMediaId() {
        return this.mediaId;
    }

    public long getSize() {
        return this.size;
    }

    private void init(JSONObject json) throws TwitterException {
        this.mediaId = ParseUtil.getLong("media_id", json);
        this.size = ParseUtil.getLong("size", json);
        try {
            if (!json.isNull("image")) {
                JSONObject image = json.getJSONObject("image");
                this.imageWidth = ParseUtil.getInt("w", image);
                this.imageHeight = ParseUtil.getInt("h", image);
                this.imageType = ParseUtil.getUnescapedString("image_type", image);
            }
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UploadedMedia that = (UploadedMedia) o;
        if (this.imageWidth != that.imageWidth) {
            return false;
        }
        if (this.imageHeight != that.imageHeight) {
            return false;
        }
        if (this.imageType != that.imageType) {
            return false;
        }
        if (this.mediaId != that.mediaId) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((int) (this.mediaId ^ (this.mediaId >>> 32))) * 31) + this.imageWidth) * 31) + this.imageHeight) * 31) + (this.imageType != null ? this.imageType.hashCode() : 0)) * 31) + ((int) (this.size ^ (this.size >>> 32)));
    }

    public String toString() {
        return "UploadedMedia{mediaId=" + this.mediaId + ", imageWidth=" + this.imageWidth + ", imageHeight=" + this.imageHeight + ", imageType='" + this.imageType + '\'' + ", size=" + this.size + '}';
    }
}
