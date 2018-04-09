package com.sina.weibo.sdk.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class AccessTokenKeeper {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_UID = "uid";
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (context != null && token != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 32768).edit();
            editor.putString("uid", token.getUid());
            editor.putString("access_token", token.getToken());
            editor.putLong("expires_in", token.getExpiresTime());
            editor.commit();
        }
    }

    public static Oauth2AccessToken readAccessToken(Context context) {
        if (context == null) {
            return null;
        }
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, 32768);
        token.setUid(pref.getString("uid", ""));
        token.setToken(pref.getString("access_token", ""));
        token.setExpiresTime(pref.getLong("expires_in", 0));
        return token;
    }

    public static void clear(Context context) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 32768).edit();
            editor.clear();
            editor.commit();
        }
    }
}
