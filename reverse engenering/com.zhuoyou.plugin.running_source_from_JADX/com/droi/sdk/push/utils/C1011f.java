package com.droi.sdk.push.utils;

import android.content.Context;
import android.os.Environment;

public class C1011f {
    public static String m3134a() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/.droipush/";
    }

    public static String m3135a(Context context) {
        if (context != null) {
            return context.getFilesDir().getAbsolutePath() + "/";
        }
        C1012g.m3138a("getPushAppPath ---context is null!");
        return null;
    }

    public static String m3136b() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/.droipush/" + "/db/";
    }
}
