package com.zhuoyou.plugin.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.weibo.net.AccessToken;

public class AccessTokenKeeper {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    public static void writeAccessToken(Context context, AccessToken token) {
        if (context != null && token != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 32768).edit();
            editor.putString("access_token", token.getToken());
            editor.putLong("expires_in", token.getExpiresIn());
            editor.commit();
        }
    }

    public static AccessToken readAccessToken(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, 32768);
        String token = pref.getString("access_token", "");
        long expires = pref.getLong("expires_in", 0);
        AccessToken accesstoken = new AccessToken(token, WeiboConstant.CONSUMER_SECRET);
        accesstoken.setExpiresIn(expires);
        return accesstoken;
    }

    public static void clear(Context context) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 32768).edit();
            editor.clear();
            editor.commit();
        }
    }
}
