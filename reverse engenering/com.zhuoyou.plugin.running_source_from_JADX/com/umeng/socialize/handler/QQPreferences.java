package com.umeng.socialize.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.umeng.socialize.utils.Log;

public class QQPreferences {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String OPENID = "uid";
    private static String mtl = null;
    private String mAccessToken = null;
    private String mUID = null;
    private SharedPreferences sharedPreferences = null;

    public QQPreferences(Context context, String platform) {
        this.sharedPreferences = context.getSharedPreferences(platform, 0);
        this.mAccessToken = this.sharedPreferences.getString("access_token", null);
        this.mUID = this.sharedPreferences.getString("uid", null);
        mtl = this.sharedPreferences.getString("expires_in", null);
    }

    public String getmAccessToken() {
        return this.mAccessToken;
    }

    public static String getExpiresIn() {
        return mtl;
    }

    public String getmUID() {
        return this.mUID;
    }

    public QQPreferences setAuthData(Bundle b) {
        this.mAccessToken = b.getString("access_token");
        mtl = b.getString("expires_in");
        this.mUID = b.getString("uid");
        return this;
    }

    public String getuid() {
        return this.mUID;
    }

    public boolean isAuthValid() {
        return this.mAccessToken != null;
    }

    public String getMtl() {
        return mtl;
    }

    public void commit() {
        this.sharedPreferences.edit().putString("access_token", this.mAccessToken).putString("expires_in", mtl).putString("uid", this.mUID).commit();
        Log.m4551i("save auth succeed");
    }

    public void delete() {
        this.sharedPreferences.edit().clear().commit();
    }
}
