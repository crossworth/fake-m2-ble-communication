package twitter4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import twitter4j.MediaEntity.Size;
import twitter4j.conf.Configuration;

class TwitterAPIConfigurationJSONImpl extends TwitterResponseImpl implements TwitterAPIConfiguration {
    private static final long serialVersionUID = -3588904550808591686L;
    private int charactersReservedPerMedia;
    private int maxMediaPerUpload;
    private String[] nonUsernamePaths;
    private int photoSizeLimit;
    private Map<Integer, Size> photoSizes;
    private int shortURLLength;
    private int shortURLLengthHttps;

    TwitterAPIConfigurationJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        try {
            JSONObject medium;
            JSONObject json = res.asJSONObject();
            this.photoSizeLimit = ParseUtil.getInt("photo_size_limit", json);
            this.shortURLLength = ParseUtil.getInt("short_url_length", json);
            this.shortURLLengthHttps = ParseUtil.getInt("short_url_length_https", json);
            this.charactersReservedPerMedia = ParseUtil.getInt("characters_reserved_per_media", json);
            JSONObject sizes = json.getJSONObject("photo_sizes");
            this.photoSizes = new HashMap(4);
            this.photoSizes.put(Size.LARGE, new Size(sizes.getJSONObject("large")));
            if (sizes.isNull("med")) {
                medium = sizes.getJSONObject("medium");
            } else {
                medium = sizes.getJSONObject("med");
            }
            this.photoSizes.put(Size.MEDIUM, new Size(medium));
            this.photoSizes.put(Size.SMALL, new Size(sizes.getJSONObject("small")));
            this.photoSizes.put(Size.THUMB, new Size(sizes.getJSONObject("thumb")));
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
                TwitterObjectFactory.registerJSONObject(this, res.asJSONObject());
            }
            JSONArray nonUsernamePathsJSONArray = json.getJSONArray("non_username_paths");
            this.nonUsernamePaths = new String[nonUsernamePathsJSONArray.length()];
            for (int i = 0; i < nonUsernamePathsJSONArray.length(); i++) {
                this.nonUsernamePaths[i] = nonUsernamePathsJSONArray.getString(i);
            }
            this.maxMediaPerUpload = ParseUtil.getInt("max_media_per_upload", json);
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public int getPhotoSizeLimit() {
        return this.photoSizeLimit;
    }

    public int getShortURLLength() {
        return this.shortURLLength;
    }

    public int getShortURLLengthHttps() {
        return this.shortURLLengthHttps;
    }

    public int getCharactersReservedPerMedia() {
        return this.charactersReservedPerMedia;
    }

    public Map<Integer, Size> getPhotoSizes() {
        return this.photoSizes;
    }

    public String[] getNonUsernamePaths() {
        return this.nonUsernamePaths;
    }

    public int getMaxMediaPerUpload() {
        return this.maxMediaPerUpload;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TwitterAPIConfigurationJSONImpl)) {
            return false;
        }
        TwitterAPIConfigurationJSONImpl that = (TwitterAPIConfigurationJSONImpl) o;
        if (this.charactersReservedPerMedia != that.charactersReservedPerMedia) {
            return false;
        }
        if (this.maxMediaPerUpload != that.maxMediaPerUpload) {
            return false;
        }
        if (this.photoSizeLimit != that.photoSizeLimit) {
            return false;
        }
        if (this.shortURLLength != that.shortURLLength) {
            return false;
        }
        if (this.shortURLLengthHttps != that.shortURLLengthHttps) {
            return false;
        }
        if (!Arrays.equals(this.nonUsernamePaths, that.nonUsernamePaths)) {
            return false;
        }
        if (this.photoSizes != null) {
            if (this.photoSizes.equals(that.photoSizes)) {
                return true;
            }
        } else if (that.photoSizes == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int i2 = ((((((this.photoSizeLimit * 31) + this.shortURLLength) * 31) + this.shortURLLengthHttps) * 31) + this.charactersReservedPerMedia) * 31;
        if (this.photoSizes != null) {
            hashCode = this.photoSizes.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.nonUsernamePaths != null) {
            i = Arrays.hashCode(this.nonUsernamePaths);
        }
        return ((hashCode + i) * 31) + this.maxMediaPerUpload;
    }

    public String toString() {
        Object obj;
        StringBuilder append = new StringBuilder().append("TwitterAPIConfigurationJSONImpl{photoSizeLimit=").append(this.photoSizeLimit).append(", shortURLLength=").append(this.shortURLLength).append(", shortURLLengthHttps=").append(this.shortURLLengthHttps).append(", charactersReservedPerMedia=").append(this.charactersReservedPerMedia).append(", photoSizes=").append(this.photoSizes).append(", nonUsernamePaths=");
        if (this.nonUsernamePaths == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.nonUsernamePaths);
        }
        return append.append(obj).append(", maxMediaPerUpload=").append(this.maxMediaPerUpload).append('}').toString();
    }
}
