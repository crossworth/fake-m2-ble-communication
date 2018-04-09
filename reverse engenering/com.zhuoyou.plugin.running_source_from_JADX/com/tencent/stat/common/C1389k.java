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
import com.tencent.wxop.stat.common.StatConstants;
import com.zhuoyou.plugin.running.app.Permissions;
import com.zhuoyou.plugin.running.tools.Tools;
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
import org.andengine.util.time.TimeConstants;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class C1389k {
    private static String f4438a = null;
    private static String f4439b = null;
    private static String f4440c = null;
    private static String f4441d = null;
    private static Random f4442e = null;
    private static StatLogger f4443f = null;
    private static String f4444g = null;
    private static C1390l f4445h = null;
    private static C1392n f4446i = null;
    private static String f4447j = "__MTA_FIRST_ACTIVATE__";
    private static int f4448k = -1;

    public static String m4109A(Context context) {
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null) {
                List sensorList = sensorManager.getSensorList(-1);
                if (sensorList != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < sensorList.size(); i++) {
                        stringBuilder.append(((Sensor) sensorList.get(i)).getType());
                        if (i != sensorList.size() - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    return stringBuilder.toString();
                }
            }
        } catch (Object th) {
            f4443f.m4085e(th);
        }
        return "";
    }

    public static WifiInfo m4110B(Context context) {
        if (C1389k.m4122a(context, "android.permission.ACCESS_WIFI_STATE")) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }
        }
        return null;
    }

    public static String m4111C(Context context) {
        try {
            WifiInfo B = C1389k.m4110B(context);
            if (B != null) {
                return B.getBSSID();
            }
        } catch (Object th) {
            f4443f.m4085e(th);
        }
        return null;
    }

    public static String m4112D(Context context) {
        try {
            WifiInfo B = C1389k.m4110B(context);
            if (B != null) {
                return B.getSSID();
            }
        } catch (Object th) {
            f4443f.m4085e(th);
        }
        return null;
    }

    public static synchronized int m4113E(Context context) {
        int i;
        synchronized (C1389k.class) {
            if (f4448k != -1) {
                i = f4448k;
            } else {
                C1389k.m4114F(context);
                i = f4448k;
            }
        }
        return i;
    }

    public static void m4114F(Context context) {
        f4448k = C1394p.m4166a(context, f4447j, 1);
        f4443f.m4085e(Integer.valueOf(f4448k));
        if (f4448k == 1) {
            C1394p.m4170b(context, f4447j, 0);
        }
    }

    private static long m4115G(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static int m4116a() {
        return C1389k.m4140h().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static Long m4117a(String str, String str2, int i, int i2, Long l) {
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

    public static String m4118a(long j) {
        return new SimpleDateFormat(Tools.DEFAULT_FORMAT_DATE).format(new Date(j));
    }

    public static String m4119a(String str) {
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

    public static HttpHost m4120a(Context context) {
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
            f4443f.m4085e(th);
        }
    }

    public static void m4121a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Object th) {
                f4443f.m4085e(th);
            }
        }
    }

    public static boolean m4122a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Object th) {
            f4443f.m4085e(th);
            return false;
        }
    }

    public static byte[] m4123a(byte[] bArr) {
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

    public static long m4124b(String str) {
        return C1389k.m4117a(str, ".", 100, 3, Long.valueOf(0)).longValue();
    }

    public static synchronized StatLogger m4125b() {
        StatLogger statLogger;
        synchronized (C1389k.class) {
            if (f4443f == null) {
                f4443f = new StatLogger(StatConstants.LOG_TAG);
                f4443f.setDebugEnable(false);
            }
            statLogger = f4443f;
        }
        return statLogger;
    }

    public static synchronized String m4126b(Context context) {
        String str;
        synchronized (C1389k.class) {
            if (f4438a == null || f4438a.trim().length() == 0) {
                f4438a = C1389k.m4146l(context);
                if (f4438a == null || f4438a.trim().length() == 0) {
                    f4438a = Integer.toString(C1389k.m4140h().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
                }
                str = f4438a;
            } else {
                str = f4438a;
            }
        }
        return str;
    }

    public static String m4127b(Context context, String str) {
        if (!StatConfig.isEnableConcurrentProcess()) {
            return str;
        }
        if (f4444g == null) {
            f4444g = C1389k.m4155u(context);
        }
        return f4444g != null ? str + "_" + f4444g : str;
    }

    public static long m4128c() {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            return instance.getTimeInMillis() + LogBuilder.MAX_INTERVAL;
        } catch (Object th) {
            f4443f.m4085e(th);
            return System.currentTimeMillis() + LogBuilder.MAX_INTERVAL;
        }
    }

    public static synchronized String m4129c(Context context) {
        String str;
        synchronized (C1389k.class) {
            if (f4440c == null || "" == f4440c) {
                f4440c = C1389k.m4137f(context);
            }
            str = f4440c;
        }
        return str;
    }

    public static String m4130c(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(C1385g.m4105b(C1383e.m4095a(str.getBytes("UTF-8")), 0), "UTF-8");
        } catch (Object th) {
            f4443f.m4085e(th);
            return str;
        }
    }

    public static int m4131d() {
        return VERSION.SDK_INT;
    }

    public static DisplayMetrics m4132d(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String m4133d(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(C1383e.m4097b(C1385g.m4103a(str.getBytes("UTF-8"), 0)), "UTF-8");
        } catch (Object th) {
            f4443f.m4085e(th);
            return str;
        }
    }

    public static String m4134e() {
        long f = C1389k.m4136f() / TimeConstants.NANOSECONDS_PER_MILLISECOND;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / TimeConstants.NANOSECONDS_PER_MILLISECOND) + "/" + String.valueOf(f);
    }

    public static boolean m4135e(Context context) {
        try {
            if (C1389k.m4122a(context, "android.permission.ACCESS_WIFI_STATE")) {
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
            f4443f.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return false;
        } catch (Object th) {
            f4443f.m4085e(th);
        }
    }

    public static long m4136f() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static String m4137f(Context context) {
        if (C1389k.m4122a(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e) {
                f4443f.m4084e(e);
                return "";
            }
        }
        f4443f.m4085e((Object) "Could not get permission of android.permission.ACCESS_WIFI_STATE");
        return "";
    }

    public static boolean m4139g(Context context) {
        try {
            if (C1389k.m4122a(context, "android.permission.INTERNET") && C1389k.m4122a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI");
                }
                return false;
            }
            f4443f.warn("can not get the permisson of android.permission.INTERNET");
            return false;
        } catch (Object th) {
            f4443f.m4085e(th);
        }
    }

    private static synchronized Random m4140h() {
        Random random;
        synchronized (C1389k.class) {
            if (f4442e == null) {
                f4442e = new Random();
            }
            random = f4442e;
        }
        return random;
    }

    public static boolean m4141h(Context context) {
        try {
            if (C1389k.m4122a(context, "android.permission.INTERNET") && C1389k.m4122a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                        return true;
                    }
                    f4443f.m4088w("Network error");
                    return false;
                }
                return false;
            }
            f4443f.warn("can not get the permisson of android.permission.INTERNET");
            return false;
        } catch (Throwable th) {
        }
    }

    private static long m4142i() {
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

    public static String m4143i(Context context) {
        if (f4439b != null) {
            return f4439b;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    f4439b = string;
                    return string;
                }
                f4443f.m4088w("Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f4443f.m4088w("Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String m4144j(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                f4443f.m4088w("Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f4443f.m4085e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String m4145k(Context context) {
        return context == null ? null : context.getClass().getName();
    }

    public static String m4146l(Context context) {
        try {
            if (C1389k.m4122a(context, Permissions.PERMISSION_PHONE)) {
                String str = "";
                if (C1389k.m4149o(context)) {
                    str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                }
                if (str != null) {
                    return str;
                }
            }
            f4443f.m4085e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
        } catch (Object th) {
            f4443f.m4085e(th);
        }
        return null;
    }

    public static String m4147m(Context context) {
        try {
            if (!C1389k.m4122a(context, Permissions.PERMISSION_PHONE)) {
                f4443f.m4085e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
                return null;
            } else if (!C1389k.m4149o(context)) {
                return null;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                return telephonyManager != null ? telephonyManager.getSimOperator() : null;
            }
        } catch (Object th) {
            f4443f.m4085e(th);
            return null;
        }
    }

    public static String m4148n(Context context) {
        Object th;
        String str = "";
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str2 != null) {
                return str2;
            }
            try {
                return "";
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            str2 = str;
            th = th4;
            f4443f.m4085e(th);
            return str2;
        }
    }

    public static boolean m4149o(Context context) {
        return context.getPackageManager().checkPermission(Permissions.PERMISSION_PHONE, context.getPackageName()) == 0;
    }

    public static String m4150p(Context context) {
        try {
            if (C1389k.m4122a(context, "android.permission.INTERNET") && C1389k.m4122a(context, "android.permission.ACCESS_NETWORK_STATE")) {
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
            f4443f.m4085e((Object) "can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return null;
        } catch (Object th) {
            f4443f.m4085e(th);
        }
    }

    public static Integer m4151q(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String m4152r(Context context) {
        Object th;
        String str = "";
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str2 != null) {
                try {
                    if (str2.length() != 0) {
                        return str2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    f4443f.m4085e(th);
                    return str2;
                }
            }
            return "unknown";
        } catch (Throwable th3) {
            Throwable th4 = th3;
            str2 = str;
            th = th4;
            f4443f.m4085e(th);
            return str2;
        }
    }

    public static int m4153s(Context context) {
        try {
            if (C1393o.m4165a()) {
                return 1;
            }
        } catch (Object th) {
            f4443f.m4085e(th);
        }
        return 0;
    }

    public static String m4154t(Context context) {
        try {
            if (C1389k.m4122a(context, Permissions.PERMISSION_WRITE_STORAGE)) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState == null || !externalStorageState.equals("mounted")) {
                    return null;
                }
                externalStorageState = Environment.getExternalStorageDirectory().getPath();
                if (externalStorageState == null) {
                    return null;
                }
                StatFs statFs = new StatFs(externalStorageState);
                long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / TimeConstants.NANOSECONDS_PER_MILLISECOND;
                return String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / TimeConstants.NANOSECONDS_PER_MILLISECOND) + "/" + String.valueOf(blockCount);
            }
            f4443f.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            return null;
        } catch (Object th) {
            f4443f.m4085e(th);
            return null;
        }
    }

    static String m4155u(Context context) {
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

    public static String m4156v(Context context) {
        return C1389k.m4127b(context, StatConstants.f4391a);
    }

    public static synchronized Integer m4157w(Context context) {
        Integer valueOf;
        int i = 0;
        synchronized (C1389k.class) {
            try {
                int a = C1394p.m4166a(context, "MTA_EVENT_INDEX", 0);
                if (a < 2147483646) {
                    i = a;
                }
                C1394p.m4170b(context, "MTA_EVENT_INDEX", i + 1);
            } catch (Object th) {
                f4443f.m4085e(th);
            }
            valueOf = Integer.valueOf(i + 1);
        }
        return valueOf;
    }

    public static String m4158x(Context context) {
        return String.valueOf(C1389k.m4115G(context) / TimeConstants.NANOSECONDS_PER_MILLISECOND) + "/" + String.valueOf(C1389k.m4142i() / TimeConstants.NANOSECONDS_PER_MILLISECOND);
    }

    public static synchronized C1390l m4159y(Context context) {
        C1390l c1390l;
        synchronized (C1389k.class) {
            if (f4445h == null) {
                f4445h = new C1390l();
            }
            c1390l = f4445h;
        }
        return c1390l;
    }

    public static JSONObject m4160z(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            C1389k.m4159y(context);
            int b = C1390l.m4162b();
            if (b > 0) {
                jSONObject.put("fx", b / TimeConstants.MICROSECONDS_PER_SECOND);
            }
            C1389k.m4159y(context);
            b = C1390l.m4163c();
            if (b > 0) {
                jSONObject.put("fn", b / TimeConstants.MICROSECONDS_PER_SECOND);
            }
            C1389k.m4159y(context);
            b = C1390l.m4161a();
            if (b > 0) {
                jSONObject.put("n", b);
            }
            C1389k.m4159y(context);
            String d = C1390l.m4164d();
            if (d != null && d.length() == 0) {
                C1389k.m4159y(context);
                jSONObject.put("na", C1390l.m4164d());
            }
        } catch (Exception e) {
            f4443f.m4084e(e);
        }
        return jSONObject;
    }
}
