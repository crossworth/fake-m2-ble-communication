package com.droi.sdk.push.utils;

import android.text.TextUtils;
import android.util.Base64;

public class C1008c {
    public static String m3126a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2;
        try {
            str2 = new String(Base64.decode(str, 0), "UTF-8");
        } catch (Exception e) {
            C1012g.m3139b(e);
            str2 = null;
        }
        return str2;
    }

    public static String m3127b(String str) {
        String str2 = null;
        if (!(str == null || str.length() == 0)) {
            try {
                str2 = Base64.encodeToString(str.getBytes("UTF-8"), 0);
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return str2;
    }
}
