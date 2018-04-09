package com.umeng.socialize.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.utils.Log;
import java.util.HashMap;
import java.util.Map;

public class SinaPreferences {
    private static final String FOLLOW = "isfollow";
    private static final String KEY_ACCESS_KEY = "access_key";
    private static final String KEY_ACCESS_SECRET = "access_secret";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_SSO_TTL = "expires_in";
    private static final String KEY_SSO_UID = "uid";
    private static final String KEY_TTL = "expires_in";
    private static final String KEY_UID = "uid";
    private static final String KEY_USER_NAME = "userName";
    private boolean isfollow = false;
    private String mAccessKey = null;
    private String mAccessSecret = null;
    private String mAccessToken = null;
    private String mRefreshToken = null;
    private long mTTL = 0;
    private String mUID = null;
    private String mUserName = null;
    private SharedPreferences sharedPreferences = null;

    public SinaPreferences(Context context, String platform) {
        this.sharedPreferences = context.getSharedPreferences(platform, 0);
        this.mAccessKey = this.sharedPreferences.getString(KEY_ACCESS_KEY, null);
        this.mRefreshToken = this.sharedPreferences.getString("refresh_token", null);
        this.mAccessSecret = this.sharedPreferences.getString(KEY_ACCESS_SECRET, null);
        this.mAccessToken = this.sharedPreferences.getString("access_token", null);
        this.mUID = this.sharedPreferences.getString("uid", null);
        this.mTTL = this.sharedPreferences.getLong("expires_in", 0);
        this.isfollow = this.sharedPreferences.getBoolean(FOLLOW, false);
    }

    public SinaPreferences setAuthData(Map<String, String> data) {
        this.mAccessKey = (String) data.get(KEY_ACCESS_KEY);
        this.mAccessSecret = (String) data.get(KEY_ACCESS_SECRET);
        this.mAccessToken = (String) data.get("access_token");
        this.mRefreshToken = (String) data.get("refresh_token");
        this.mUID = (String) data.get("uid");
        if (!TextUtils.isEmpty((CharSequence) data.get("expires_in"))) {
            this.mTTL = (Long.valueOf((String) data.get("expires_in")).longValue() * 1000) + System.currentTimeMillis();
        }
        return this;
    }

    public String getmAccessToken() {
        return this.mAccessToken;
    }

    public long getmTTL() {
        return this.mTTL;
    }

    public String getmRefreshToken() {
        return this.mRefreshToken;
    }

    public SinaPreferences setAuthData(Bundle bundle) {
        this.mAccessToken = bundle.getString("access_token");
        this.mRefreshToken = bundle.getString("refresh_token");
        this.mUID = bundle.getString("uid");
        if (!TextUtils.isEmpty(bundle.getString("expires_in"))) {
            this.mTTL = (Long.valueOf(bundle.getString("expires_in")).longValue() * 1000) + System.currentTimeMillis();
        }
        return this;
    }

    public Map<String, String> getAuthData() {
        Map<String, String> map = new HashMap();
        map.put(KEY_ACCESS_KEY, this.mAccessKey);
        map.put(KEY_ACCESS_SECRET, this.mAccessSecret);
        map.put("uid", this.mUID);
        map.put("expires_in", String.valueOf(this.mTTL));
        return map;
    }

    public String getUID() {
        return this.mUID;
    }

    public boolean isAuthorized() {
        return !TextUtils.isEmpty(this.mAccessToken);
    }

    public boolean isAuthValid() {
        boolean isAuthorized = isAuthorized();
        boolean isExpired;
        if (this.mTTL - System.currentTimeMillis() <= 0) {
            isExpired = true;
        } else {
            isExpired = false;
        }
        if (!isAuthorized || isExpired) {
            return false;
        }
        return true;
    }

    public boolean Isfollow() {
        return this.isfollow;
    }

    public void setIsfollow(boolean isfollow) {
        this.sharedPreferences.edit().putBoolean(FOLLOW, isfollow).commit();
    }

    public void commit() {
        this.sharedPreferences.edit().putString(KEY_ACCESS_KEY, this.mAccessKey).putString(KEY_ACCESS_SECRET, this.mAccessSecret).putString("access_token", this.mAccessToken).putString("refresh_token", this.mRefreshToken).putString("uid", this.mUID).putLong("expires_in", this.mTTL).commit();
        Log.m4551i("save auth succeed");
    }

    public void delete() {
        this.mAccessKey = null;
        this.mAccessSecret = null;
        this.mAccessToken = null;
        this.mUID = null;
        this.mTTL = 0;
        this.sharedPreferences.edit().clear().commit();
    }
}
