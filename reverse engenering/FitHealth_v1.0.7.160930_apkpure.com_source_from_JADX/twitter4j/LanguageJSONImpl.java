package twitter4j;

import twitter4j.api.HelpResources.Language;
import twitter4j.conf.Configuration;

public class LanguageJSONImpl implements Language {
    private String code;
    private String name;
    private String status;

    LanguageJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            this.name = json.getString("name");
            this.code = json.getString("code");
            this.status = json.getString("status");
        } catch (Throwable jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getStatus() {
        return this.status;
    }

    static ResponseList<Language> createLanguageList(HttpResponse res, Configuration conf) throws TwitterException {
        return createLanguageList(res.asJSONArray(), res, conf);
    }

    static ResponseList<Language> createLanguageList(JSONArray list, HttpResponse res, Configuration conf) throws TwitterException {
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        try {
            int size = list.length();
            ResponseList<Language> languages = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Language language = new LanguageJSONImpl(json);
                languages.add(language);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(language, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(languages, list);
            }
            return languages;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }
}
