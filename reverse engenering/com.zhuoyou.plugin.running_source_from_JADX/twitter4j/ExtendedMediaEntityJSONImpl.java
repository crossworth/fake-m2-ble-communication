package twitter4j;

import java.util.Arrays;

public class ExtendedMediaEntityJSONImpl extends MediaEntityJSONImpl implements ExtendedMediaEntity {
    private static final long serialVersionUID = -3889082303259253211L;
    private String extAltText;
    private int videoAspectRatioHeight;
    private int videoAspectRatioWidth;
    private long videoDurationMillis;
    private Variant[] videoVariants;

    static class Variant implements twitter4j.ExtendedMediaEntity.Variant {
        private static final long serialVersionUID = 1027236588556797980L;
        int bitrate;
        String contentType;
        String url;

        Variant(JSONObject json) throws JSONException {
            this.bitrate = json.has("bitrate") ? json.getInt("bitrate") : 0;
            this.contentType = json.getString("content_type");
            this.url = json.getString("url");
        }

        Variant() {
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public String getContentType() {
            return this.contentType;
        }

        public String getUrl() {
            return this.url;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Variant)) {
                return false;
            }
            Variant variant = (Variant) o;
            if (this.bitrate != variant.bitrate) {
                return false;
            }
            if (!this.contentType.equals(variant.contentType)) {
                return false;
            }
            if (this.url.equals(variant.url)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int hashCode;
            int i = 0;
            int i2 = this.bitrate * 31;
            if (this.contentType != null) {
                hashCode = this.contentType.hashCode();
            } else {
                hashCode = 0;
            }
            hashCode = (i2 + hashCode) * 31;
            if (this.url != null) {
                i = this.url.hashCode();
            }
            return hashCode + i;
        }

        public String toString() {
            return "Variant{bitrate=" + this.bitrate + ", contentType=" + this.contentType + ", url=" + this.url + '}';
        }
    }

    ExtendedMediaEntityJSONImpl(JSONObject json) throws TwitterException {
        super(json);
        try {
            if (json.has("video_info")) {
                JSONObject videoInfo = json.getJSONObject("video_info");
                JSONArray aspectRatio = videoInfo.getJSONArray("aspect_ratio");
                this.videoAspectRatioWidth = aspectRatio.getInt(0);
                this.videoAspectRatioHeight = aspectRatio.getInt(1);
                if (!videoInfo.isNull("duration_millis")) {
                    this.videoDurationMillis = videoInfo.getLong("duration_millis");
                }
                JSONArray variants = videoInfo.getJSONArray("variants");
                this.videoVariants = new Variant[variants.length()];
                for (int i = 0; i < variants.length(); i++) {
                    this.videoVariants[i] = new Variant(variants.getJSONObject(i));
                }
            } else {
                this.videoVariants = new Variant[0];
            }
            if (json.has("ext_alt_text")) {
                this.extAltText = json.getString("ext_alt_text");
            }
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    ExtendedMediaEntityJSONImpl() {
    }

    public int getVideoAspectRatioWidth() {
        return this.videoAspectRatioWidth;
    }

    public int getVideoAspectRatioHeight() {
        return this.videoAspectRatioHeight;
    }

    public long getVideoDurationMillis() {
        return this.videoDurationMillis;
    }

    public String getExtAltText() {
        return this.extAltText;
    }

    public twitter4j.ExtendedMediaEntity.Variant[] getVideoVariants() {
        return this.videoVariants;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendedMediaEntityJSONImpl)) {
            return false;
        }
        if (this.id != ((ExtendedMediaEntityJSONImpl) o).id) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (int) (this.id ^ (this.id >>> 32));
    }

    public String toString() {
        return "ExtendedMediaEntityJSONImpl{id=" + this.id + ", url=" + this.url + ", mediaURL=" + this.mediaURL + ", mediaURLHttps=" + this.mediaURLHttps + ", expandedURL=" + this.expandedURL + ", displayURL='" + this.displayURL + '\'' + ", sizes=" + this.sizes + ", type=" + this.type + ", videoAspectRatioWidth=" + this.videoAspectRatioWidth + ", videoAspectRatioHeight=" + this.videoAspectRatioHeight + ", videoDurationMillis=" + this.videoDurationMillis + ", extAltText=" + this.extAltText + ", videoVariants=" + Arrays.toString(this.videoVariants) + '}';
    }
}
