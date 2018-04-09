package com.baidu.platform.comapi.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.mapapi.VersionInfo;
import com.baidu.platform.comjni.map.commonmemcache.C0675a;
import com.sina.weibo.sdk.exception.WeiboAuthException;
import com.tencent.open.GameAppOperation;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.handler.TwitterPreferences;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

public class C0671f {
    public static String f2194A;
    private static final String f2195B = C0671f.class.getSimpleName();
    private static boolean f2196C = true;
    private static int f2197D = 0;
    private static int f2198E = 0;
    static C0675a f2199a = new C0675a();
    static String f2200b = "02";
    static String f2201c;
    static String f2202d;
    static String f2203e;
    static String f2204f;
    static int f2205g;
    static int f2206h;
    static int f2207i;
    static int f2208j;
    static int f2209k;
    static int f2210l;
    static String f2211m;
    static String f2212n;
    static String f2213o = "baidu";
    static String f2214p = "baidu";
    static String f2215q = "";
    static String f2216r = "";
    static String f2217s = "";
    static String f2218t;
    static String f2219u;
    static String f2220v = WeiboAuthException.DEFAULT_AUTH_ERROR_CODE;
    static String f2221w = WeiboAuthException.DEFAULT_AUTH_ERROR_CODE;
    public static Context f2222x;
    public static final int f2223y = Integer.parseInt(VERSION.SDK);
    public static float f2224z = 1.0f;

    public static Bundle m2166a() {
        Bundle bundle = new Bundle();
        bundle.putString("cpu", f2215q);
        bundle.putString("resid", f2200b);
        bundle.putString(LogBuilder.KEY_CHANNEL, f2213o);
        bundle.putString("glr", f2216r);
        bundle.putString("glv", f2217s);
        bundle.putString("mb", C0671f.m2178f());
        bundle.putString("sv", C0671f.m2181h());
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_OS, C0671f.m2183j());
        bundle.putInt("dpi_x", C0671f.m2184k());
        bundle.putInt("dpi_y", C0671f.m2184k());
        bundle.putString("net", f2211m);
        bundle.putString("cuid", C0671f.m2186m());
        bundle.putByteArray(GameAppOperation.GAME_SIGNATURE, C0671f.m2169a(f2222x));
        bundle.putString("pcn", f2222x.getPackageName());
        bundle.putInt("screen_x", C0671f.m2180g());
        bundle.putInt("screen_y", C0671f.m2182i());
        return bundle;
    }

    public static void m2167a(String str) {
        f2211m = str;
        C0671f.m2176e();
    }

    public static void m2168a(String str, String str2) {
        f2220v = str2;
        f2221w = str;
        C0671f.m2176e();
    }

    public static byte[] m2169a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void m2170b() {
        if (f2199a != null) {
            f2199a.m2257a();
        }
    }

    public static void m2171b(Context context) {
        f2222x = context;
        f2218t = context.getFilesDir().getAbsolutePath();
        f2219u = context.getCacheDir().getAbsolutePath();
        f2202d = Build.MODEL;
        f2203e = SocializeConstants.OS + VERSION.SDK;
        f2201c = context.getPackageName();
        C0671f.m2173c(context);
        C0671f.m2175d(context);
        C0671f.m2177e(context);
        C0671f.m2179f(context);
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(SocializeConstants.KEY_LOCATION);
            f2197D = locationManager.isProviderEnabled("gps") ? 1 : 0;
            f2198E = locationManager.isProviderEnabled("network") ? 1 : 0;
        } catch (Exception e) {
            Log.w("baidumapsdk", "LocationManager error");
        }
    }

    public static String m2172c() {
        return f2199a != null ? f2199a.m2259b() : null;
    }

    private static void m2173c(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            f2204f = VersionInfo.getApiVersion();
            if (!(f2204f == null || f2204f.equals(""))) {
                f2204f = f2204f.replace('_', '.');
            }
            f2205g = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            f2204f = "1.0.0";
            f2205g = 1;
        }
    }

    public static String m2174d() {
        return f2211m;
    }

    private static void m2175d(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display defaultDisplay = windowManager != null ? windowManager.getDefaultDisplay() : null;
        if (defaultDisplay != null) {
            f2206h = defaultDisplay.getWidth();
            f2207i = defaultDisplay.getHeight();
            defaultDisplay.getMetrics(displayMetrics);
        }
        f2224z = displayMetrics.density;
        f2208j = (int) displayMetrics.xdpi;
        f2209k = (int) displayMetrics.ydpi;
        if (f2223y > 3) {
            f2210l = displayMetrics.densityDpi;
        } else {
            f2210l = 160;
        }
        if (f2210l == 0) {
            f2210l = 160;
        }
    }

    public static void m2176e() {
        Bundle bundle = new Bundle();
        bundle.putString("cpu", f2215q);
        bundle.putString("resid", f2200b);
        bundle.putString(LogBuilder.KEY_CHANNEL, f2213o);
        bundle.putString("glr", f2216r);
        bundle.putString("glv", f2217s);
        bundle.putString("mb", C0671f.m2178f());
        bundle.putString("sv", C0671f.m2181h());
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_OS, C0671f.m2183j());
        bundle.putInt("dpi_x", C0671f.m2184k());
        bundle.putInt("dpi_y", C0671f.m2184k());
        bundle.putString("net", f2211m);
        bundle.putString("cuid", C0671f.m2186m());
        bundle.putString("pcn", f2222x.getPackageName());
        bundle.putInt("screen_x", C0671f.m2180g());
        bundle.putInt("screen_y", C0671f.m2182i());
        bundle.putString("appid", f2220v);
        bundle.putString("duid", f2221w);
        if (!TextUtils.isEmpty(f2194A)) {
            bundle.putString(TwitterPreferences.TOKEN, f2194A);
        }
        if (f2199a != null) {
            f2199a.m2258a(bundle);
        }
    }

    private static void m2177e(Context context) {
        f2212n = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
    }

    public static String m2178f() {
        return f2202d;
    }

    private static void m2179f(Context context) {
        f2211m = "0";
    }

    public static int m2180g() {
        return f2206h;
    }

    public static String m2181h() {
        return f2204f;
    }

    public static int m2182i() {
        return f2207i;
    }

    public static String m2183j() {
        return f2203e;
    }

    public static int m2184k() {
        return f2210l;
    }

    public static String m2185l() {
        return f2218t;
    }

    public static String m2186m() {
        String a;
        try {
            a = CommonParam.m69a(f2222x);
        } catch (Exception e) {
            a = "";
        }
        return a == null ? "" : a;
    }
}
