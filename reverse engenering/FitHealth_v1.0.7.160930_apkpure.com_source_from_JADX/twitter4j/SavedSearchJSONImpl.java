package twitter4j;

import java.util.Date;
import twitter4j.conf.Configuration;

final class SavedSearchJSONImpl extends TwitterResponseImpl implements SavedSearch {
    private static final long serialVersionUID = -2281949861485441692L;
    private Date createdAt;
    private long id;
    private String name;
    private int position;
    private String query;

    SavedSearchJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    SavedSearchJSONImpl(JSONObject savedSearch) throws TwitterException {
        init(savedSearch);
    }

    static ResponseList<SavedSearch> createSavedSearchList(HttpResponse res, Configuration conf) throws TwitterException {
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        JSONArray json = res.asJSONArray();
        try {
            ResponseList<SavedSearch> savedSearches = new ResponseListImpl(json.length(), res);
            for (int i = 0; i < json.length(); i++) {
                JSONObject savedSearchesJSON = json.getJSONObject(i);
                SavedSearch savedSearch = new SavedSearchJSONImpl(savedSearchesJSON);
                savedSearches.add(savedSearch);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(savedSearch, savedSearchesJSON);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(savedSearches, json);
            }
            return savedSearches;
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + res.asString(), jsone);
        }
    }

    private void init(JSONObject savedSearch) throws TwitterException {
        this.createdAt = ParseUtil.getDate(SessionColumns.CREATED_AT, savedSearch, "EEE MMM dd HH:mm:ss z yyyy");
        this.query = ParseUtil.getUnescapedString("query", savedSearch);
        this.position = ParseUtil.getInt("position", savedSearch);
        this.name = ParseUtil.getUnescapedString("name", savedSearch);
        this.id = ParseUtil.getLong("id", savedSearch);
    }

    public int compareTo(SavedSearch that) {
        return (int) (this.id - that.getId());
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getQuery() {
        return this.query;
    }

    public int getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SavedSearch)) {
            return false;
        }
        if (this.id != ((SavedSearch) o).getId()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((this.createdAt.hashCode() * 31) + this.query.hashCode()) * 31) + this.position) * 31) + this.name.hashCode()) * 31) + ((int) this.id);
    }

    public String toString() {
        return "SavedSearchJSONImpl{createdAt=" + this.createdAt + ", query='" + this.query + '\'' + ", position=" + this.position + ", name='" + this.name + '\'' + ", id=" + this.id + '}';
    }
}
