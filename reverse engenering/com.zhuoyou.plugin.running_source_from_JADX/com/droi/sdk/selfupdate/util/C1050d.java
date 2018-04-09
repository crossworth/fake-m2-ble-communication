package com.droi.sdk.selfupdate.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.umeng.facebook.share.internal.ShareConstants;

public class C1050d {
    private static C1050d f3485a = null;
    private Resources f3486b;
    private String f3487c;

    private C1050d(Context context) {
        this.f3486b = context.getResources();
        this.f3487c = context.getPackageName();
    }

    public static synchronized C1050d m3292a(Context context) {
        C1050d c1050d;
        synchronized (C1050d.class) {
            if (f3485a == null) {
                f3485a = new C1050d(context.getApplicationContext());
            }
            c1050d = f3485a;
        }
        return c1050d;
    }

    public int m3293a(String str) {
        return m3291a(str, ShareConstants.WEB_DIALOG_PARAM_ID);
    }

    public int m3294b(String str) {
        return m3291a(str, "layout");
    }

    public int m3295c(String str) {
        return m3291a(str, "string");
    }

    private int m3291a(String str, String str2) {
        int identifier = this.f3486b.getIdentifier(str, str2, this.f3487c);
        if (identifier != 0) {
            return identifier;
        }
        Log.e("ResourceUtils", "getRes(" + str + "/ " + str2 + ")");
        Log.e("ResourceUtils", "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
        return 0;
    }
}
