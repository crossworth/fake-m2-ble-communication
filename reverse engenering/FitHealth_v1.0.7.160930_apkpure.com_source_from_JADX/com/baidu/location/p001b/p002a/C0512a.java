package com.baidu.location.p001b.p002a;

import android.content.Context;
import android.text.TextUtils;

public class C0512a {
    private static final boolean f2242a = false;
    private static final String f2243if = C0512a.class.getSimpleName();

    private static String m2168a(Context context) {
        return C0514b.m2172a(context);
    }

    public static String m2169if(Context context) {
        String a = C0512a.m2168a(context);
        String str = C0514b.m2176do(context);
        if (TextUtils.isEmpty(str)) {
            str = "0";
        }
        return a + "|" + new StringBuffer(str).reverse().toString();
    }
}
