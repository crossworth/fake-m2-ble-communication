package com.droi.sdk.selfupdate.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.PermissionChecker;
import android.support.v4.media.session.PlaybackStateCompat;
import com.droi.sdk.internal.DroiLog;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class C1047b {
    public static String m3259a(Context context) {
        String str;
        if (C1047b.m3263a() && C1047b.m3280n(context)) {
            str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "droiSelfUpdate" + File.separator + context.getPackageName();
        } else {
            str = context.getFilesDir().getAbsolutePath() + File.separator + "droiSelfUpdate";
        }
        DroiLog.m2871i("CommonUtils", "path:" + str);
        File file = new File(str);
        if (file.mkdirs() || file.isDirectory()) {
            return str;
        }
        throw new IOException("There is no permission to create path");
    }

    public static void m3262a(Context context, String str) {
        context.getApplicationContext().getSharedPreferences("droi_update", 0).edit().putString("ignore_mds", str).commit();
    }

    public static String m3264b(Context context) {
        String string = context.getApplicationContext().getSharedPreferences("droi_update", 0).getString("ignore_mds", "");
        return "".equals(string) ? null : string;
    }

    public static void m3265b(Context context, String str) {
        context.getApplicationContext().getSharedPreferences("droi_update", 0).edit().putString("task_id", str).commit();
    }

    public static String m3266c(Context context) {
        String string = context.getApplicationContext().getSharedPreferences("droi_update", 0).getString("task_id", "");
        return "".equals(string) ? null : string;
    }

    public static void m3267c(Context context, String str) {
        context.getApplicationContext().getSharedPreferences("droi_update", 0).edit().putString("version_code", str).commit();
    }

    public static String m3268d(Context context) {
        String string = context.getApplicationContext().getSharedPreferences("droi_update", 0).getString("version_code", "");
        return "".equals(string) ? null : string;
    }

    public static void m3261a(Context context, int i) {
        context.getApplicationContext().getSharedPreferences("droi_update", 0).edit().putInt("delta_code", i).commit();
    }

    public static int m3270e(Context context) {
        return context.getApplicationContext().getSharedPreferences("droi_update", 0).getInt("delta_code", 0);
    }

    public static String m3272f(Context context) {
        return context.getPackageName();
    }

    public static String m3273g(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static String m3274h(Context context) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static String m3275i(Context context) {
        return C1051e.m3297a(C1046a.m3253a(context));
    }

    public static boolean m3276j(Context context) {
        return "Wi-Fi".equals(C1047b.m3278l(context)[0]);
    }

    public static String m3277k(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static String[] m3278l(Context context) {
        String[] strArr = new String[]{"", ""};
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                strArr[0] = "";
                return strArr;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                strArr[0] = "";
                return strArr;
            } else if (connectivityManager.getNetworkInfo(1).getState() == State.CONNECTED) {
                strArr[0] = "Wi-Fi";
                return strArr;
            } else {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
                if (networkInfo.getState() == State.CONNECTED) {
                    strArr[0] = "2G/3G";
                    strArr[1] = networkInfo.getSubtypeName();
                    return strArr;
                }
                return strArr;
            }
        } catch (Exception e) {
            DroiLog.m2869e("CommonUtils", e);
        }
    }

    public static String m3260a(String str) {
        String str2 = "";
        try {
            long longValue = Long.valueOf(str).longValue();
            if (longValue < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                return ((int) longValue) + "B";
            }
            if (longValue < 1048576) {
                return new DecimalFormat("#0.00").format(((double) ((float) longValue)) / 1024.0d) + "K";
            } else if (longValue < 1073741824) {
                return new DecimalFormat("#0.00").format(((double) ((float) longValue)) / 1048576.0d) + "M";
            } else {
                return new DecimalFormat("#0.00").format(((double) ((float) longValue)) / 1.073741824E9d) + "G";
            }
        } catch (NumberFormatException e) {
            return str;
        }
    }

    protected static boolean m3263a() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState == null || !externalStorageState.equals("mounted")) {
            return false;
        }
        return true;
    }

    public static void m3269d(Context context, String str) {
        context.getApplicationContext().getSharedPreferences("droi_update", 0).edit().putString("test_channel", str).commit();
    }

    public static String m3279m(Context context) {
        if (!context.getPackageName().equalsIgnoreCase("com.droi.sdk.selfupdatedemo")) {
            return null;
        }
        String string = context.getApplicationContext().getSharedPreferences("droi_update", 0).getString("test_channel", "");
        if ("".equals(string)) {
            return null;
        }
        return string;
    }

    protected static boolean m3280n(Context context) {
        if (VERSION.SDK_INT >= 23) {
            return PermissionChecker.checkSelfPermission(context, Permissions.PERMISSION_WRITE_STORAGE) == 0;
        } else {
            return C1047b.m3271e(context, Permissions.PERMISSION_WRITE_STORAGE);
        }
    }

    protected static boolean m3271e(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}
