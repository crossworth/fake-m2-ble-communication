package com.tencent.p004a.p005a.p006a.p007a;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.tencent.wxop.stat.p023b.C0882g;
import org.json.JSONObject;

public final class C0669h {
    private static void m2233a(String str, Throwable th) {
        Log.e("MID", str, th);
    }

    static void m2234a(JSONObject jSONObject, String str, String str2) {
        if (C0669h.m2238d(str2)) {
            jSONObject.put(str, str2);
        }
    }

    static boolean m2235a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            C0669h.m2233a("checkPermission error", th);
            return false;
        }
    }

    static String m2236b(Context context) {
        try {
            if (C0669h.m2235a(context, "android.permission.READ_PHONE_STATE")) {
                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (deviceId != null) {
                    return deviceId;
                }
            }
            Log.i("MID", "Could not get permission of android.permission.READ_PHONE_STATE");
        } catch (Throwable th) {
            Log.w("MID", th);
        }
        return "";
    }

    static String m2237c(Context context) {
        if (C0669h.m2235a(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e) {
                Log.i("MID", "get wifi address error" + e);
                return "";
            }
        }
        Log.i("MID", "Could not get permission of android.permission.ACCESS_WIFI_STATE");
        return "";
    }

    static boolean m2238d(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static boolean m2239e(String str) {
        return str != null && str.trim().length() >= 40;
    }

    static String m2240f(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(C0882g.m2864c(Base64.decode(str.getBytes("UTF-8"), 0)), "UTF-8").trim().replace("\t", "").replace("\n", "").replace("\r", "");
        } catch (Throwable th) {
            C0669h.m2233a("decode error", th);
            return str;
        }
    }

    static String m2241g(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(Base64.encode(C0882g.m2863b(str.getBytes("UTF-8")), 0), "UTF-8").trim().replace("\t", "").replace("\n", "").replace("\r", "");
        } catch (Throwable th) {
            C0669h.m2233a("decode error", th);
            return str;
        }
    }
}
