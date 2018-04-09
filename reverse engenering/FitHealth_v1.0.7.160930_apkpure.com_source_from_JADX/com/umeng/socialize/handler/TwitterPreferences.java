package com.umeng.socialize.handler;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.socialize.utils.Log;
import java.util.HashMap;
import java.util.Map;

public class TwitterPreferences {
    public static final String TOKEN = "token";
    public static final String TOKEN_SECRET = "tokenSecret";
    private String mSecret;
    private String mToken;
    private SharedPreferences sharedPreferences = null;

    public TwitterPreferences(Context context, String platform) {
        this.sharedPreferences = context.getSharedPreferences(platform, 0);
        this.mToken = this.sharedPreferences.getString(TOKEN, null);
        this.mSecret = this.sharedPreferences.getString(TOKEN_SECRET, null);
    }

    public TwitterPreferences setAuthData(Map<String, String> data) {
        this.mToken = (String) data.get(TOKEN);
        this.mSecret = (String) data.get(TOKEN_SECRET);
        return this;
    }

    public TwitterPreferences setAuthData(String token, String secret) {
        this.mToken = token;
        this.mSecret = secret;
        return this;
    }

    public void commit() {
        this.sharedPreferences.edit().putString(TOKEN, this.mToken).putString(TOKEN_SECRET, this.mSecret).commit();
        Log.m3253i("save auth succeed");
    }

    public Map<String, String> getAuthData() {
        Map<String, String> map = new HashMap();
        map.put(TOKEN_SECRET, this.mSecret);
        map.put(TOKEN, this.mToken);
        return map;
    }

    public String getString(String msg) {
        if (msg.equals(TOKEN)) {
            return this.mToken;
        }
        return this.mSecret;
    }

    public void delete() {
        this.mToken = null;
        this.mSecret = null;
        this.sharedPreferences.edit().clear().commit();
    }
}
