package com.droi.sdk.analytics;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.support.v4.content.PermissionChecker;
import android.util.Base64;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.droi.sdk.core.Core;
import com.zhuoyou.plugin.running.app.Permissions;
import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Pattern;

class C0755c {
    protected static String m2322a(Context context) {
        if (context == null) {
            return "";
        }
        if (context instanceof Activity) {
            String str = "";
            try {
                return ((Activity) context).getComponentName().getClassName();
            } catch (Exception e) {
                C0753a.m2314c("can not get name", e.toString());
                return str;
            }
        }
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService("activity");
        if (C0755c.m2333b(context, "android.permission.GET_TASKS")) {
            return ((RunningTaskInfo) activityManager.getRunningTasks(1).get(0)).topActivity.getClassName();
        }
        C0753a.m2314c("CommonUtil", "android.permission.GET_TASKS permission should be added into AndroidManifest.xml.");
        return "";
    }

    public static String m2323a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            C0753a.m2311a("CommonUtil", e);
            return "";
        }
    }

    public static String m2324a(String str, int i) {
        if (str == null || str.isEmpty() || i <= 0) {
            return "";
        }
        char[] toCharArray = str.toCharArray();
        int length = toCharArray.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < toCharArray.length) {
            try {
                i3 += String.valueOf(toCharArray[i2]).getBytes("UTF-8").length;
                if (i3 > i) {
                    break;
                }
                i2++;
            } catch (Exception e) {
                C0753a.m2311a("CommonUtil", e);
                return "";
            }
        }
        i2 = length;
        return String.valueOf(toCharArray, 0, i2);
    }

    protected static void m2325a(Context context, String str) {
        new C0779o(context).m2405a("current_page", str);
    }

    protected static boolean m2326a() {
        String externalStorageState = Environment.getExternalStorageState();
        return externalStorageState != null && externalStorageState.equals("mounted");
    }

    protected static boolean m2327a(Context context, long j) {
        try {
            long b = new C0779o(context).m2406b("session_last_record_time", 0);
            C0753a.m2312a("CommonUtil", "currentTime=" + j);
            C0753a.m2312a("CommonUtil", "session_save_time=" + b);
            if (Math.abs(j - b) > StatisticConfig.MIN_UPLOAD_INTERVAL) {
                C0753a.m2312a("CommonUtil", ">30ms,return true,create new session.");
                return true;
            }
            C0753a.m2312a("CommonUtil", "return false.At the same session.");
            return false;
        } catch (Exception e) {
            C0753a.m2311a("CommonUtil", e);
            return true;
        }
    }

    public static boolean m2328a(String str, CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0 && Pattern.matches(str, charSequence);
    }

    public static long m2329b() {
        return Core.getTimestamp().getTime();
    }

    protected static long m2330b(Context context) {
        return new C0779o(context).m2406b("session_last_record_time", C0755c.m2329b());
    }

    protected static String m2331b(Context context, long j) {
        String str = "";
        String b = C0754b.m2318b();
        if (b == null) {
            return str;
        }
        str = C0755c.m2323a(b + AnalyticsCoreHelper.getDeviceId() + j);
        new C0779o(context).m2405a(LogColumns.SESSION_ID, str);
        C0755c.m2335c(context, j);
        return str;
    }

    protected static String m2332b(String str) {
        return Base64.encodeToString(str.getBytes(), 0);
    }

    protected static boolean m2333b(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    protected static String m2334c(Context context) {
        return new C0779o(context).m2407b("current_page", C0755c.m2322a(context));
    }

    protected static void m2335c(Context context, long j) {
        new C0779o(context).m2404a("session_last_record_time", j);
    }

    protected static boolean m2336d(Context context) {
        return VERSION.SDK_INT >= 23 ? PermissionChecker.checkSelfPermission(context, Permissions.PERMISSION_WRITE_STORAGE) == 0 : C0755c.m2333b(context, Permissions.PERMISSION_WRITE_STORAGE);
    }

    public static String m2337e(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        int myPid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (!(runningAppProcesses == null || runningAppProcesses.isEmpty())) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }
}
