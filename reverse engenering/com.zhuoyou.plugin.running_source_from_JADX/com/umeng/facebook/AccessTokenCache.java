package com.umeng.facebook;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

class AccessTokenCache {
    static final String CACHED_ACCESS_TOKEN_KEY = "AccessTokenManager.CachedAccessToken";
    private final SharedPreferences sharedPreferences;
    private LegacyTokenHelper tokenCachingStrategy;
    private final SharedPreferencesTokenCachingStrategyFactory tokenCachingStrategyFactory;

    static class SharedPreferencesTokenCachingStrategyFactory {
        SharedPreferencesTokenCachingStrategyFactory() {
        }

        public LegacyTokenHelper create() {
            return new LegacyTokenHelper(FacebookSdk.getApplicationContext());
        }
    }

    AccessTokenCache(SharedPreferences sharedPreferences, SharedPreferencesTokenCachingStrategyFactory tokenCachingStrategyFactory) {
        this.sharedPreferences = sharedPreferences;
        this.tokenCachingStrategyFactory = tokenCachingStrategyFactory;
    }

    public AccessTokenCache() {
        this(FacebookSdk.getApplicationContext().getSharedPreferences("AccessTokenManager.SharedPreferences", 0), new SharedPreferencesTokenCachingStrategyFactory());
    }

    public AccessToken load() {
        if (hasCachedAccessToken()) {
            return getCachedAccessToken();
        }
        if (!shouldCheckLegacyToken()) {
            return null;
        }
        AccessToken accessToken = getLegacyAccessToken();
        if (accessToken == null) {
            return accessToken;
        }
        save(accessToken);
        getTokenCachingStrategy().clear();
        return accessToken;
    }

    @TargetApi(9)
    public void save(AccessToken accessToken) {
        try {
            this.sharedPreferences.edit().putString(CACHED_ACCESS_TOKEN_KEY, accessToken.toJSONObject().toString()).apply();
        } catch (JSONException e) {
        }
    }

    @TargetApi(9)
    public void clear() {
        this.sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
        if (shouldCheckLegacyToken()) {
            getTokenCachingStrategy().clear();
        }
    }

    private boolean hasCachedAccessToken() {
        return this.sharedPreferences.contains(CACHED_ACCESS_TOKEN_KEY);
    }

    private AccessToken getCachedAccessToken() {
        AccessToken accessToken = null;
        String jsonString = this.sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, accessToken);
        if (jsonString != null) {
            try {
                accessToken = AccessToken.createFromJSONObject(new JSONObject(jsonString));
            } catch (JSONException e) {
            }
        }
        return accessToken;
    }

    private boolean shouldCheckLegacyToken() {
        return FacebookSdk.isLegacyTokenUpgradeSupported();
    }

    private AccessToken getLegacyAccessToken() {
        Bundle bundle = getTokenCachingStrategy().load();
        if (bundle == null || !LegacyTokenHelper.hasTokenInformation(bundle)) {
            return null;
        }
        return AccessToken.createFromLegacyCache(bundle);
    }

    private LegacyTokenHelper getTokenCachingStrategy() {
        if (this.tokenCachingStrategy == null) {
            synchronized (this) {
                if (this.tokenCachingStrategy == null) {
                    this.tokenCachingStrategy = this.tokenCachingStrategyFactory.create();
                }
            }
        }
        return this.tokenCachingStrategy;
    }
}
