package com.umeng.facebook;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

final class ProfileCache {
    static final String CACHED_PROFILE_KEY = "ProfileManager.CachedProfile";
    static final String SHARED_PREFERENCES_NAME = "AccessTokenManager.SharedPreferences";
    private final SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

    ProfileCache() {
    }

    Profile load() {
        String jsonString = this.sharedPreferences.getString(CACHED_PROFILE_KEY, null);
        if (jsonString != null) {
            try {
                return new Profile(new JSONObject(jsonString));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    @TargetApi(9)
    void save(Profile profile) {
        JSONObject jsonObject = profile.toJSONObject();
        if (jsonObject != null) {
            this.sharedPreferences.edit().putString(CACHED_PROFILE_KEY, jsonObject.toString()).apply();
        }
    }

    @TargetApi(9)
    void clear() {
        this.sharedPreferences.edit().remove(CACHED_PROFILE_KEY).apply();
    }
}
