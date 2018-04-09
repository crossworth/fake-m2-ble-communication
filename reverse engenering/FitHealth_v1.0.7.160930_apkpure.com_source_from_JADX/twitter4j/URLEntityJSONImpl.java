package twitter4j;

final class URLEntityJSONImpl extends EntityIndex implements URLEntity {
    private static final long serialVersionUID = 7333552738058031524L;
    private String displayURL;
    private String expandedURL;
    private String url;

    URLEntityJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    URLEntityJSONImpl(int start, int end, String url, String expandedURL, String displayURL) {
        setStart(start);
        setEnd(end);
        this.url = url;
        this.expandedURL = expandedURL;
        this.displayURL = displayURL;
    }

    URLEntityJSONImpl() {
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));
            this.url = json.getString("url");
            if (json.isNull("expanded_url")) {
                this.expandedURL = this.url;
            } else {
                this.expandedURL = json.getString("expanded_url");
            }
            if (json.isNull("display_url")) {
                this.displayURL = this.url;
            } else {
                this.displayURL = json.getString("display_url");
            }
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public String getText() {
        return this.url;
    }

    public String getURL() {
        return this.url;
    }

    public String getExpandedURL() {
        return this.expandedURL;
    }

    public String getDisplayURL() {
        return this.displayURL;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URLEntityJSONImpl that = (URLEntityJSONImpl) o;
        if (this.displayURL == null ? that.displayURL != null : !this.displayURL.equals(that.displayURL)) {
            return false;
        }
        if (this.expandedURL == null ? that.expandedURL != null : !this.expandedURL.equals(that.expandedURL)) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(that.url)) {
                return true;
            }
        } else if (that.url == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.url != null) {
            result = this.url.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.expandedURL != null) {
            hashCode = this.expandedURL.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.displayURL != null) {
            i = this.displayURL.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "URLEntityJSONImpl{url='" + this.url + '\'' + ", expandedURL='" + this.expandedURL + '\'' + ", displayURL='" + this.displayURL + '\'' + '}';
    }
}
