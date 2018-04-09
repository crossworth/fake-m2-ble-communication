package com.umeng.socialize.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class WeixinPreferences {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_ACCESS_TOKEN_TTL = "expires_in";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_OPENID = "openid";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_REFRESH_TOKEN_TTL = "rt_expires_in";
    private static final String KEY_UID = "unionid";
    private String mAccessToken;
    private long mAccessTokenTTL;
    private String mRefreshToken;
    private long mRefreshTokenTTL;
    private String mUID;
    private String mUnionid;
    private long mexpirein;
    private SharedPreferences sharedPreferences = null;

    public WeixinPreferences(Context context, String name) {
        this.sharedPreferences = context.getSharedPreferences(name, 0);
        this.mUID = this.sharedPreferences.getString("openid", null);
        this.mUnionid = this.sharedPreferences.getString("unionid", null);
        this.mAccessToken = this.sharedPreferences.getString("access_token", null);
        this.mAccessTokenTTL = this.sharedPreferences.getLong("expires_in", 0);
        this.mRefreshToken = this.sharedPreferences.getString("refresh_token", null);
        this.mRefreshTokenTTL = this.sharedPreferences.getLong(KEY_REFRESH_TOKEN_TTL, 0);
        this.mexpirein = this.sharedPreferences.getLong("expires_in", 0);
    }

    public WeixinPreferences setBundle(Bundle bundle) {
        this.mUID = bundle.getString("openid");
        this.mUnionid = bundle.getString("unionid");
        this.mAccessToken = bundle.getString("access_token");
        this.mRefreshToken = bundle.getString("refresh_token");
        String time = bundle.getString("expires_in");
        if (!TextUtils.isEmpty(time)) {
            this.mexpirein = (Long.valueOf(time).longValue() * 1000) + System.currentTimeMillis();
        }
        String accessTTL = bundle.getString("expires_in");
        if (!TextUtils.isEmpty(accessTTL)) {
            this.mAccessTokenTTL = (Long.valueOf(accessTTL).longValue() * 1000) + System.currentTimeMillis();
        }
        String refreshTTL = bundle.getString(KEY_REFRESH_TOKEN_TTL);
        if (!TextUtils.isEmpty(refreshTTL)) {
            this.mRefreshTokenTTL = (Long.valueOf(refreshTTL).longValue() * 1000) + System.currentTimeMillis();
        }
        commit();
        return this;
    }

    public String getUID() {
        return this.mUID;
    }

    public String getmUnionid() {
        return this.mUnionid;
    }

    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public Map<String, String> getmap() {
        Map<String, String> map = new HashMap();
        map.put("access_token", this.mAccessToken);
        map.put("openid", this.mUID);
        map.put("unionid", this.mUnionid);
        map.put("refresh_token", this.mRefreshToken);
        map.put("expires_in", String.valueOf(this.mAccessTokenTTL));
        return map;
    }

    public boolean isAccessTokenAvailable() {
        boolean isNull = TextUtils.isEmpty(this.mAccessToken);
        boolean isExpired;
        if (this.mexpirein - System.currentTimeMillis() <= 0) {
            isExpired = true;
        } else {
            isExpired = false;
        }
        if (isNull || isExpired) {
            return false;
        }
        return true;
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public long getmAccessTokenTTL() {
        return this.mAccessTokenTTL;
    }

    public boolean isAuthValid() {
        boolean isNull = TextUtils.isEmpty(this.mRefreshToken);
        boolean isExpired;
        if (this.mRefreshTokenTTL - System.currentTimeMillis() <= 0) {
            isExpired = true;
        } else {
            isExpired = false;
        }
        if (isNull || isExpired) {
            return false;
        }
        return true;
    }

    public boolean isAuth() {
        return !TextUtils.isEmpty(getAccessToken());
    }

    public void delete() {
        this.sharedPreferences.edit().clear().commit();
    }

    public void commit() {
        this.sharedPreferences.edit().putString("openid", this.mUID).putString("unionid", this.mUnionid).putString("access_token", this.mAccessToken).putLong("expires_in", this.mAccessTokenTTL).putString("refresh_token", this.mRefreshToken).putLong(KEY_REFRESH_TOKEN_TTL, this.mRefreshTokenTTL).putLong("expires_in", this.mexpirein).commit();
    }
}
