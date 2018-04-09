package com.droi.sdk.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.droi.sdk.core.Core;

class C0754b {
    private static String f2269a;
    private static String f2270b;
    private static String f2271c;
    private static boolean f2272d;

    static void m2317a() {
        Context context = C0770f.f2324a;
        f2270b = AnalyticsCoreHelper.getAppId();
        f2271c = Core.getChannelName(context);
        if (!C0755c.m2328a("^[0-9a-zA-Z\\-_]{1,128}$", f2271c)) {
            C0753a.m2314c("AppInfo", "channel name invalid");
        }
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            f2269a = packageManager.getPackageInfo(packageName, 16384).versionName;
            if (!C0755c.m2328a("^[0-9a-zA-Z\\-_.]{1,128}$", f2269a)) {
                C0753a.m2314c("AppInfo", "version name invalid");
            }
        } catch (NameNotFoundException e) {
            C0753a.m2314c("AppInfo", "can't get version name." + e);
            f2269a = "";
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            boolean z = ((packageInfo.applicationInfo.flags & 1) == 0 && (packageInfo.applicationInfo.flags & 128) == 0) ? false : true;
            f2272d = z;
        } catch (Exception e2) {
            C0753a.m2311a("AppInfo", e2);
        } catch (Exception e22) {
            C0753a.m2311a("AppInfo", e22);
        }
    }

    protected static String m2318b() {
        return f2270b;
    }

    protected static String m2319c() {
        return f2271c;
    }

    protected static String m2320d() {
        return f2269a;
    }

    protected static boolean m2321e() {
        return f2272d;
    }
}
