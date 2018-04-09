package com.tencent.stat.common;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.stat.StatConfig;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class C0837k {
    private static String f2903a = null;
    private static String f2904b = null;
    private static String f2905c = null;
    private static String f2906d = null;
    private static Random f2907e = null;
    private static StatLogger f2908f = null;
    private static String f2909g = null;
    private static C0838l f2910h = null;
    private static C0840n f2911i = null;
    private static String f2912j = "__MTA_FIRST_ACTIVATE__";
    private static int f2913k = -1;

    public static String m2702A(Context context) {
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null) {
                List sensorList = sensorManager.getSensorList(-1);
                if (sensorList != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < sensorList.size(); i++) {
                        stringBuilder.append(((Sensor) sensorList.get(i)).getType());
                        if (i != sensorList.size() - 1) {
                            stringBuilder.append(SeparatorConstants.SEPARATOR_ADS_ID);
                        }
                    }
                    return stringBuilder.toString();
                }
            }
        } catch (Object th) {
            f2908f.m2680e(th);
        }
        return "";
    }

    public static WifiInfo m2703B(Context context) {
        if (C0837k.m2715a(context, "android.permission.ACCESS_WIFI_STATE")) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }
        }
        return null;
    }

    public static String m2704C(Context context) {
        try {
            WifiInfo B = C0837k.m2703B(context);
            if (B != null) {
                return B.getBSSID();
            }
        } catch (Object th) {
            f2908f.m2680e(th);
        }
        return null;
    }

    public static String m2705D(Context context) {
        try {
            WifiInfo B = C0837k.m2703B(context);
            if (B != null) {
                return B.getSSID();
            }
        } catch (Object th) {
            f2908f.m2680e(th);
        }
        return null;
    }

    public static synchronized int m2706E(Context context) {
        int i;
        synchronized (C0837k.class) {
            if (f2913k != -1) {
                i = f2913k;
            } else {
                C0837k.m2707F(context);
                i = f2913k;
            }
        }
        return i;
    }

    public static void m2707F(Context context) {
        f2913k = C0842p.m2759a(context, f2912j, 1);
        f2908f.m2680e(Integer.valueOf(f2913k));
        if (f2913k == 1) {
            C0842p.m2763b(context, f2912j, 0);
        }
    }

    private static long m2708G(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static int m2709a() {
        return C0837k.m2733h().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static Long m2710a(String str, String str2, int i, int i2, Long l) {
        if (str == null || str2 == null) {
            return l;
        }
        if (str2.equalsIgnoreCase(".") || str2.equalsIgnoreCase("|")) {
            str2 = "\\" + str2;
        }
        String[] split = str.split(str2);
        if (split.length != i2) {
            return l;
        }
        try {
            Long valueOf = Long.valueOf(0);
            int i3 = 0;
            while (i3 < split.length) {
                Long valueOf2 = Long.valueOf(((long) i) * (valueOf.longValue() + Long.valueOf(split[i3]).longValue()));
                i3++;
                valueOf = valueOf2;
            }
            return valueOf;
        } catch (NumberFormatException e) {
            return l;
        }
    }

    public static String m2711a(long j) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(j));
    }

    public static String m2712a(String str) {
        if (str == null) {
            return "0";
        }
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
        } catch (Throwable th) {
            return "0";
        }
    }

    public static HttpHost m2713a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                return null;
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getTypeName() != null && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                return null;
            }
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (extraInfo == null) {
                return null;
            }
            if (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap")) {
                return new HttpHost("10.0.0.172", 80);
            }
            if (extraInfo.equals("ctwap")) {
                return new HttpHost("10.0.0.200", 80);
            }
            return null;
        } catch (Object th) {
            f2908f.m2680e(th);
        }
    }

    public static void m2714a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Object th) {
                f2908f.m2680e(th);
            }
        }
    }

    public static boolean m2715a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Object th) {
            f2908f.m2680e(th);
            return false;
        }
    }

    public static byte[] m2716a(byte[] bArr) {
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read != -1) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                bArr2 = byteArrayOutputStream.toByteArray();
                byteArrayInputStream.close();
                gZIPInputStream.close();
                byteArrayOutputStream.close();
                return bArr2;
            }
        }
    }

    public static long m2717b(String str) {
        return C0837k.m2710a(str, ".", 100, 3, Long.valueOf(0)).longValue();
    }

    public static synchronized StatLogger m2718b() {
        StatLogger statLogger;
        synchronized (C0837k.class) {
            if (f2908f == null) {
                f2908f = new StatLogger("MtaSDK");
                f2908f.setDebugEnable(false);
            }
            statLogger = f2908f;
        }
        return statLogger;
    }

    public static synchronized String m2719b(Context context) {
        String str;
        synchronized (C0837k.class) {
            if (f2903a == null || f2903a.trim().length() == 0) {
                f2903a = C0837k.m2739l(context);
                if (f2903a == null || f2903a.trim().length() == 0) {
                    f2903a = Integer.toString(C0837k.m2733h().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
                }
                str = f2903a;
            } else {
                str = f2903a;
            }
        }
        return str;
    }

    public static String m2720b(Context context, String str) {
        if (!StatConfig.isEnableConcurrentProcess()) {
            return str;
        }
        if (f2909g == null) {
            f2909g = C0837k.m2748u(context);
        }
        return f2909g != null ? str + "_" + f2909g : str;
    }

    public static long m2721c() {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            return instance.getTimeInMillis() + 86400000;
        } catch (Object th) {
            f2908f.m2680e(th);
            return System.currentTimeMillis() + 86400000;
        }
    }

    public static synchronized String m2722c(Context context) {
        String str;
        synchronized (C0837k.class) {
            if (f2905c == null || "" == f2905c) {
                f2905c = C0837k.m2730f(context);
            }
            str = f2905c;
        }
        return str;
    }

    public static String m2723c(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(C0835g.m2700b(C0833e.m2690a(str.getBytes("UTF-8")), 0), "UTF-8");
        } catch (Object th) {
            f2908f.m2680e(th);
            return str;
        }
    }

    public static int m2724d() {
        return VERSION.SDK_INT;
    }

    public static DisplayMetrics m2725d(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String m2726d(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(C0833e.m2692b(C0835g.m2698a(str.getBytes("UTF-8"), 0)), "UTF-8");
        } catch (Object th) {
            f2908f.m2680e(th);
            return str;
        }
    }

    public static String m2727e() {
        long f = C0837k.m2729f() / 1000000;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1000000) + "/" + String.valueOf(f);
    }

    public static boolean m2728e(Context context) {
        try {
            if (C0837k.m2715a(context, "android.permission.ACCESS_WIFI_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                    if (allNetworkInfo != null) {
                        int i = 0;
                        while (i < allNetworkInfo.length) {
                            if (allNetworkInfo[i].getTypeName().equalsIgnoreCase("WIFI") && allNetworkInfo[i].isConnected()) {
                                return true;
                            }
                            i++;
                        }
                    }
                }
                return false;
            }
            f2908f.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return false;
        } catch (Object th) {
            f2908f.m2680e(th);
        }
    }

    public static long m2729f() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static String m2730f(Context context) {
        if (C0837k.m2715a(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e) {
                f2908f.m2679e(e);
                return "";
            }
        }
        f2908f.m2680e((Object) "Could not get permission of android.permission.ACCESS_WIFI_STATE");
        return "";
    }

    public static boolean m2732g(Context context) {
        try {
            if (C0837k.m2715a(context, "android.permission.INTERNET") && C0837k.m2715a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI");
                }
                return false;
            }
            f2908f.warn("can not get the permisson of android.permission.INTERNET");
            return false;
        } catch (Object th) {
            f2908f.m2680e(th);
        }
    }

    private static synchronized Random m2733h() {
        Random random;
        synchronized (C0837k.class) {
            if (f2907e == null) {
                f2907e = new Random();
            }
            random = f2907e;
        }
        return random;
    }

    public static boolean m2734h(Context context) {
        try {
            if (C0837k.m2715a(context, "android.permission.INTERNET") && C0837k.m2715a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                        return true;
                    }
                    f2908f.m2683w("Network error");
                    return false;
                }
                return false;
            }
            f2908f.warn("can not get the permisson of android.permission.INTERNET");
            return false;
        } catch (Throwable th) {
        }
    }

    private static long m2735i() {
        long j = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            j = (long) (Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue() * 1024);
            bufferedReader.close();
            return j;
        } catch (IOException e) {
            return j;
        }
    }

    public static String m2736i(Context context) {
        if (f2904b != null) {
            return f2904b;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    f2904b = string;
                    return string;
                }
                f2908f.m2683w("Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f2908f.m2683w("Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String m2737j(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                f2908f.m2683w("Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f2908f.m2680e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String m2738k(Context context) {
        return context == null ? null : context.getClass().getName();
    }

    public static String m2739l(Context context) {
        try {
            if (C0837k.m2715a(context, "android.permission.READ_PHONE_STATE")) {
                String str = "";
                if (C0837k.m2742o(context)) {
                    str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                }
                if (str != null) {
                    return str;
                }
            }
            f2908f.m2680e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
        } catch (Object th) {
            f2908f.m2680e(th);
        }
        return null;
    }

    public static String m2740m(Context context) {
        try {
            if (!C0837k.m2715a(context, "android.permission.READ_PHONE_STATE")) {
                f2908f.m2680e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
                return null;
            } else if (!C0837k.m2742o(context)) {
                return null;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                return telephonyManager != null ? telephonyManager.getSimOperator() : null;
            }
        } catch (Object th) {
            f2908f.m2680e(th);
            return null;
        }
    }

    public static String m2741n(Context context) {
        String str;
        Object th;
        String str2 = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str != null) {
                return str;
            }
            try {
                return "";
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            str = str2;
            th = th4;
            f2908f.m2680e(th);
            return str;
        }
    }

    public static boolean m2742o(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    public static String m2743p(Context context) {
        try {
            if (C0837k.m2715a(context, "android.permission.INTERNET") && C0837k.m2715a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    String typeName = activeNetworkInfo.getTypeName();
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (typeName != null) {
                        return typeName.equalsIgnoreCase("WIFI") ? "WIFI" : typeName.equalsIgnoreCase("MOBILE") ? extraInfo == null ? "MOBILE" : extraInfo : extraInfo == null ? typeName : extraInfo;
                    }
                }
                return null;
            }
            f2908f.m2680e((Object) "can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return null;
        } catch (Object th) {
            f2908f.m2680e(th);
        }
    }

    public static Integer m2744q(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String m2745r(Context context) {
        String str;
        Object th;
        String str2 = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str != null) {
                try {
                    if (str.length() != 0) {
                        return str;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    f2908f.m2680e(th);
                    return str;
                }
            }
            return "unknown";
        } catch (Throwable th3) {
            Throwable th4 = th3;
            str = str2;
            th = th4;
            f2908f.m2680e(th);
            return str;
        }
    }

    public static int m2746s(Context context) {
        try {
            if (C0841o.m2758a()) {
                return 1;
            }
        } catch (Object th) {
            f2908f.m2680e(th);
        }
        return 0;
    }

    public static String m2747t(Context context) {
        try {
            if (C0837k.m2715a(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState == null || !externalStorageState.equals("mounted")) {
                    return null;
                }
                externalStorageState = Environment.getExternalStorageDirectory().getPath();
                if (externalStorageState == null) {
                    return null;
                }
                StatFs statFs = new StatFs(externalStorageState);
                long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000;
                return String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf(blockCount);
            }
            f2908f.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            return null;
        } catch (Object th) {
            f2908f.m2680e(th);
            return null;
        }
    }

    static String m2748u(Context context) {
        try {
            int myPid = Process.myPid();
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String m2749v(Context context) {
        return C0837k.m2720b(context, StatConstants.f2871a);
    }

    public static synchronized Integer m2750w(Context context) {
        Integer valueOf;
        int i = 0;
        synchronized (C0837k.class) {
            try {
                int a = C0842p.m2759a(context, "MTA_EVENT_INDEX", 0);
                if (a < 2147483646) {
                    i = a;
                }
                C0842p.m2763b(context, "MTA_EVENT_INDEX", i + 1);
            } catch (Object th) {
                f2908f.m2680e(th);
            }
            valueOf = Integer.valueOf(i + 1);
        }
        return valueOf;
    }

    public static String m2751x(Context context) {
        return String.valueOf(C0837k.m2708G(context) / 1000000) + "/" + String.valueOf(C0837k.m2735i() / 1000000);
    }

    public static synchronized C0838l m2752y(Context context) {
        C0838l c0838l;
        synchronized (C0837k.class) {
            if (f2910h == null) {
                f2910h = new C0838l();
            }
            c0838l = f2910h;
        }
        return c0838l;
    }

    public static JSONObject m2753z(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            C0837k.m2752y(context);
            int b = C0838l.m2755b();
            if (b > 0) {
                jSONObject.put("fx", b / 1000000);
            }
            C0837k.m2752y(context);
            b = C0838l.m2756c();
            if (b > 0) {
                jSONObject.put("fn", b / 1000000);
            }
            C0837k.m2752y(context);
            b = C0838l.m2754a();
            if (b > 0) {
                jSONObject.put("n", b);
            }
            C0837k.m2752y(context);
            String d = C0838l.m2757d();
            if (d != null && d.length() == 0) {
                C0837k.m2752y(context);
                jSONObject.put("na", C0838l.m2757d());
            }
        } catch (Exception e) {
            f2908f.m2679e(e);
        }
        return jSONObject;
    }
}
