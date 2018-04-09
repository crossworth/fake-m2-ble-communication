package com.amap.api.mapcore.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.security.MessageDigest;
import java.util.Locale;

/* compiled from: AppInfo */
public class dl {
    private static String f460a = "";
    private static String f461b = "";
    private static String f462c = "";
    private static String f463d = "";
    private static String f464e = null;

    public static String m601a(Context context) {
        try {
            return m608g(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return f463d;
        }
    }

    public static String m603b(Context context) {
        try {
            if (!"".equals(f460a)) {
                return f460a;
            }
            PackageManager packageManager = context.getPackageManager();
            f460a = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
            return f460a;
        } catch (Throwable th) {
            eb.m742a(th, "AppInfo", "getApplicationName");
        }
    }

    public static String m604c(Context context) {
        try {
            if (f461b != null && !"".equals(f461b)) {
                return f461b;
            }
            f461b = context.getApplicationContext().getPackageName();
            return f461b;
        } catch (Throwable th) {
            eb.m742a(th, "AppInfo", "getPackageName");
        }
    }

    public static String m605d(Context context) {
        try {
            if (!"".equals(f462c)) {
                return f462c;
            }
            f462c = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return f462c;
        } catch (Throwable th) {
            eb.m742a(th, "AppInfo", "getApplicationVersion");
        }
    }

    public static String m606e(Context context) {
        try {
            if (f464e != null && !"".equals(f464e)) {
                return f464e;
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
            f464e = stringBuffer.toString();
            return f464e;
        } catch (Throwable th) {
            eb.m742a(th, "AppInfo", "getSHA1AndPackage");
            return f464e;
        }
    }

    static void m602a(String str) {
        f463d = str;
    }

    public static String m607f(Context context) {
        try {
            return m608g(context);
        } catch (Throwable th) {
            eb.m742a(th, "AppInfo", "getKey");
            return f463d;
        }
    }

    private static String m608g(Context context) throws NameNotFoundException {
        if (f463d == null || f463d.equals("")) {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return f463d;
            }
            f463d = applicationInfo.metaData.getString("com.amap.api.v2.apikey");
        }
        return f463d;
    }
}
