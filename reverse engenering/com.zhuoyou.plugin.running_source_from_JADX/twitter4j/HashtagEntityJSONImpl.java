package twitter4j;

class HashtagEntityJSONImpl extends EntityIndex implements HashtagEntity, SymbolEntity {
    private static final long serialVersionUID = -5317828991902848906L;
    private String text;

    HashtagEntityJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    HashtagEntityJSONImpl(int start, int end, String text) {
        setStart(start);
        setEnd(end);
        this.text = text;
    }

    HashtagEntityJSONImpl() {
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));
            if (!json.isNull("text")) {
                this.text = json.getString("text");
            }
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public String getText() {
        return this.text;
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
        HashtagEntityJSONImpl that = (HashtagEntityJSONImpl) o;
        if (this.text != null) {
            if (this.text.equals(that.text)) {
                return true;
            }
        } else if (that.text == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.text != null ? this.text.hashCode() : 0;
    }

    public String toString() {
        return "HashtagEntityJSONImpl{text='" + this.text + '\'' + '}';
    }
}
