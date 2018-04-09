package com.droi.sdk.feedback.p018a;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.umeng.facebook.share.internal.ShareConstants;

public class C0950b {
    private static C0950b f3110a = null;
    private Resources f3111b;
    private String f3112c;

    private C0950b(Context context) {
        this.f3111b = context.getResources();
        this.f3112c = context.getPackageName();
    }

    public static synchronized C0950b m2819a(Context context) {
        C0950b c0950b;
        synchronized (C0950b.class) {
            if (f3110a == null) {
                f3110a = new C0950b(context.getApplicationContext());
            }
            c0950b = f3110a;
        }
        return c0950b;
    }

    public int m2820a(String str) {
        return m2818a(str, ShareConstants.WEB_DIALOG_PARAM_ID);
    }

    public int m2821b(String str) {
        return m2818a(str, "layout");
    }

    public int m2822c(String str) {
        return m2818a(str, "string");
    }

    private int m2818a(String str, String str2) {
        int identifier = this.f3111b.getIdentifier(str, str2, this.f3112c);
        if (identifier != 0) {
            return identifier;
        }
        Log.e("ResourceUtils", "getRes(" + str + "/ " + str2 + ")");
        Log.e("ResourceUtils", "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
        return 0;
    }
}
