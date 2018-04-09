package twitter4j;

import java.io.Serializable;
import twitter4j.conf.Configuration;

final class CategoryJSONImpl implements Category, Serializable {
    private static final long serialVersionUID = 3811335888122469876L;
    private String name;
    private int size;
    private String slug;

    CategoryJSONImpl(JSONObject json) throws JSONException {
        init(json);
    }

    void init(JSONObject json) throws JSONException {
        this.name = json.getString("name");
        this.slug = json.getString("slug");
        this.size = ParseUtil.getInt("size", json);
    }

    static ResponseList<Category> createCategoriesList(HttpResponse res, Configuration conf) throws TwitterException {
        return createCategoriesList(res.asJSONArray(), res, conf);
    }

    static ResponseList<Category> createCategoriesList(JSONArray array, HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            ResponseList<Category> categories = new ResponseListImpl(array.length(), res);
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                Category category = new CategoryJSONImpl(json);
                categories.add(category);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(category, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(categories, array);
            }
            return categories;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getSlug() {
        return this.slug;
    }

    public int getSize() {
        return this.size;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryJSONImpl that = (CategoryJSONImpl) o;
        if (this.size != that.size) {
            return false;
        }
        if (this.name == null ? that.name != null : !this.name.equals(that.name)) {
            return false;
        }
        if (this.slug != null) {
            if (this.slug.equals(that.slug)) {
                return true;
            }
        } else if (that.slug == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.name != null) {
            result = this.name.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.slug != null) {
            i = this.slug.hashCode();
        }
        return ((i2 + i) * 31) + this.size;
    }

    public String toString() {
        return "CategoryJSONImpl{name='" + this.name + '\'' + ", slug='" + this.slug + '\'' + ", size=" + this.size + '}';
    }
}
