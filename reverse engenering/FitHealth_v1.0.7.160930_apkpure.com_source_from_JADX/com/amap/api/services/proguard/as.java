package com.amap.api.services.proguard;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.security.MessageDigest;
import java.util.Locale;

/* compiled from: AppInfo */
public class as {
    private static String f1317a = "";
    private static String f1318b = "";
    private static String f1319c = "";
    private static String f1320d = "";
    private static String f1321e = null;

    public static String m1209a(Context context) {
        try {
            return m1216g(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return f1320d;
        }
    }

    public static String m1211b(Context context) {
        try {
            if (!"".equals(f1317a)) {
                return f1317a;
            }
            PackageManager packageManager = context.getPackageManager();
            f1317a = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
            return f1317a;
        } catch (Throwable th) {
            be.m1340a(th, "AppInfo", "getApplicationName");
        }
    }

    public static String m1212c(Context context) {
        try {
            if (f1318b != null && !"".equals(f1318b)) {
                return f1318b;
            }
            f1318b = context.getApplicationContext().getPackageName();
            return f1318b;
        } catch (Throwable th) {
            be.m1340a(th, "AppInfo", "getPackageName");
        }
    }

    public static String m1213d(Context context) {
        try {
            if (!"".equals(f1319c)) {
                return f1319c;
            }
            f1319c = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return f1319c;
        } catch (Throwable th) {
            be.m1340a(th, "AppInfo", "getApplicationVersion");
        }
    }

    public static String m1214e(Context context) {
        try {
            if (f1321e != null && !"".equals(f1321e)) {
                return f1321e;
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            byte[] digest = MessageDigest.getInstance("SHA1").digest(packageInfo.signatures[0].toByteArray());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toUpperCase = Integer.toHexString(b & 255).toUpperCase(Locale.US);
                if (toUpperCase.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(toUpperCase);
                stringBuffer.append(":");
            }
            stringBuffer.append(packageInfo.packageName);
            f1321e = stringBuffer.toString();
            return f1321e;
        } catch (Throwable th) {
            be.m1340a(th, "AppInfo", "getSHA1AndPackage");
            return f1321e;
        }
    }

    static void m1210a(String str) {
        f1320d = str;
    }

    public static String m1215f(Context context) {
        try {
            return m1216g(context);
        } catch (Throwable th) {
            be.m1340a(th, "AppInfo", "getKey");
            return f1320d;
        }
    }

    private static String m1216g(Context context) throws NameNotFoundException {
        if (f1320d == null || f1320d.equals("")) {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return f1320d;
            }
            f1320d = applicationInfo.metaData.getString("com.amap.api.v2.apikey");
        }
        return f1320d;
    }
}
