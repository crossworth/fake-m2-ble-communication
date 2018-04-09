package com.tencent.p021a.p022a.p023a.p024a;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.tencent.wxop.stat.common.C1437f;
import com.zhuoyou.plugin.running.app.Permissions;
import org.json.JSONObject;

public final class C1147h {
    static String m3335a(Context context) {
        try {
            if (C1147h.m3338a(context, Permissions.PERMISSION_PHONE)) {
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

    private static void m3336a(String str, Throwable th) {
        Log.e("MID", str, th);
    }

    static void m3337a(JSONObject jSONObject, String str, String str2) {
        if (C1147h.m3340b(str2)) {
            jSONObject.put(str, str2);
        }
    }

    static boolean m3338a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            C1147h.m3336a("checkPermission error", th);
            return false;
        }
    }

    static String m3339b(Context context) {
        if (C1147h.m3338a(context, "android.permission.ACCESS_WIFI_STATE")) {
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

    static boolean m3340b(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static boolean m3341c(String str) {
        return str != null && str.trim().length() >= 40;
    }

    static String m3342f(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(C1437f.m4392b(Base64.decode(str.getBytes("UTF-8"), 0)), "UTF-8").trim().replace("\t", "").replace("\n", "").replace("\r", "");
        } catch (Throwable th) {
            C1147h.m3336a("decode error", th);
            return str;
        }
    }

    static String m3343g(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(Base64.encode(C1437f.m4390a(str.getBytes("UTF-8")), 0), "UTF-8").trim().replace("\t", "").replace("\n", "").replace("\r", "");
        } catch (Throwable th) {
            C1147h.m3336a("decode error", th);
            return str;
        }
    }
}
