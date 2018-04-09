package com.aps;

import android.content.Context;
import android.util.Log;

public final class be {
    private static String f1808a = "";

    protected static void m1850a(String str) {
        if (!str.equals("GPS_SATELLITE")) {
        }
    }

    protected static boolean m1851a(Context context) {
        if (context != null) {
            f1808a = context.getPackageName();
            return true;
        }
        Log.d(f1808a, "Error: No SD Card!");
        return false;
    }
}
