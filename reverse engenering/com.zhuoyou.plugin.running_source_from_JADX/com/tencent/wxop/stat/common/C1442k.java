package com.tencent.wxop.stat.common;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.zhuoyou.plugin.running.app.Permissions;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
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
import twitter4j.HttpResponseCode;

public class C1442k {
    private static String f4786a = null;
    private static String f4787b = null;
    private static String f4788c = null;
    private static String f4789d = null;
    private static Random f4790e = null;
    private static DisplayMetrics f4791f = null;
    private static String f4792g = null;
    private static String f4793h = "";
    private static String f4794i = "";
    private static int f4795j = -1;
    private static StatLogger f4796k = null;
    private static String f4797l = null;
    private static String f4798m = null;
    private static volatile int f4799n = -1;
    private static String f4800o = null;
    private static String f4801p = null;
    private static long f4802q = -1;
    private static String f4803r = "";
    private static C1445n f4804s = null;
    private static String f4805t = "__MTA_FIRST_ACTIVATE__";
    private static int f4806u = -1;
    private static long f4807v = -1;
    private static int f4808w = 0;
    private static String f4809x = "";

    public static int m4400A(Context context) {
        return C1447p.m4454a(context, "mta.qq.com.difftime", 0);
    }

    public static boolean m4401B(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.startsWith(packageName)) {
                return runningAppProcessInfo.importance == HttpResponseCode.BAD_REQUEST;
            }
        }
        return false;
    }

    public static String m4402C(Context context) {
        if (context == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        return (resolveActivity.activityInfo == null || resolveActivity.activityInfo.packageName.equals("android")) ? null : resolveActivity.activityInfo.packageName;
    }

    private static long m4403D(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static int m4404a() {
        return C1442k.m4428g().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static int m4405a(Context context, boolean z) {
        if (z) {
            f4808w = C1442k.m4400A(context);
        }
        return f4808w;
    }

    public static Long m4406a(String str, String str2, int i, int i2, Long l) {
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

    public static String m4407a(int i) {
        Calendar instance = Calendar.getInstance();
        instance.roll(6, i);
        return new SimpleDateFormat(Tools.DEFAULT_FORMAT_DATE).format(instance.getTime());
    }

    public static String m4408a(long j) {
        return new SimpleDateFormat(Tools.DEFAULT_FORMAT_DATE).format(new Date(j));
    }

    public static String m4409a(Context context, String str) {
        if (!StatConfig.isEnableConcurrentProcess()) {
            return str;
        }
        if (f4798m == null) {
            f4798m = C1442k.m4439q(context);
        }
        return f4798m != null ? str + "_" + f4798m : str;
    }

    public static String m4410a(String str) {
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

    public static HttpHost m4411a(Context context) {
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
            String defaultHost = Proxy.getDefaultHost();
            if (defaultHost != null && defaultHost.trim().length() > 0) {
                return new HttpHost(defaultHost, Proxy.getDefaultPort());
            }
            return null;
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
    }

    public static void m4412a(Context context, int i) {
        f4808w = i;
        C1447p.m4458b(context, "mta.qq.com.difftime", i);
    }

    public static boolean m4413a(StatSpecifyReportedInfo statSpecifyReportedInfo) {
        return statSpecifyReportedInfo == null ? false : C1442k.m4420c(statSpecifyReportedInfo.getAppKey());
    }

    public static byte[] m4414a(byte[] bArr) {
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

    public static long m4415b(String str) {
        return C1442k.m4406a(str, ".", 100, 3, Long.valueOf(0)).longValue();
    }

    public static synchronized StatLogger m4416b() {
        StatLogger statLogger;
        synchronized (C1442k.class) {
            if (f4796k == null) {
                statLogger = new StatLogger(StatConstants.LOG_TAG);
                f4796k = statLogger;
                statLogger.setDebugEnable(false);
            }
            statLogger = f4796k;
        }
        return statLogger;
    }

    public static synchronized String m4417b(Context context) {
        String a;
        synchronized (C1442k.class) {
            if (f4786a == null || f4786a.trim().length() == 0) {
                a = C1448q.m4461a(context);
                f4786a = a;
                if (a == null || f4786a.trim().length() == 0) {
                    f4786a = Integer.toString(C1442k.m4428g().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
                }
                a = f4786a;
            } else {
                a = f4786a;
            }
        }
        return a;
    }

    public static long m4418c() {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            return instance.getTimeInMillis() + LogBuilder.MAX_INTERVAL;
        } catch (Throwable th) {
            f4796k.m4375e(th);
            return System.currentTimeMillis() + LogBuilder.MAX_INTERVAL;
        }
    }

    public static synchronized String m4419c(Context context) {
        String str;
        synchronized (C1442k.class) {
            if (f4788c == null || f4788c.trim().length() == 0) {
                f4788c = C1448q.m4466b(context);
            }
            str = f4788c;
        }
        return str;
    }

    public static boolean m4420c(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static DisplayMetrics m4421d(Context context) {
        if (f4791f == null) {
            f4791f = new DisplayMetrics();
            ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(f4791f);
        }
        return f4791f;
    }

    public static String m4422d() {
        if (C1442k.m4420c(f4801p)) {
            return f4801p;
        }
        long e = C1442k.m4423e() / TimeConstants.NANOSECONDS_PER_MILLISECOND;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        String str = String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / TimeConstants.NANOSECONDS_PER_MILLISECOND) + "/" + String.valueOf(e);
        f4801p = str;
        return str;
    }

    public static long m4423e() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static boolean m4424e(Context context) {
        try {
            if (C1448q.m4465a(context, "android.permission.ACCESS_WIFI_STATE")) {
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
            f4796k.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return false;
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
    }

    public static String m4426f(Context context) {
        if (f4787b != null) {
            return f4787b;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    f4787b = string;
                    return string;
                }
                f4796k.m4376i("Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f4796k.m4376i("Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String m4427g(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                f4796k.m4378w("Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f4796k.m4374e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    private static synchronized Random m4428g() {
        Random random;
        synchronized (C1442k.class) {
            if (f4790e == null) {
                f4790e = new Random();
            }
            random = f4790e;
        }
        return random;
    }

    private static long m4429h() {
        if (f4802q > 0) {
            return f4802q;
        }
        long j = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            j = (long) (Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue() * 1024);
            bufferedReader.close();
        } catch (Exception e) {
        }
        f4802q = j;
        return j;
    }

    public static String m4430h(Context context) {
        return context == null ? null : context.getClass().getName();
    }

    public static String m4431i(Context context) {
        if (f4792g != null) {
            return f4792g;
        }
        try {
            if (!C1448q.m4465a(context, Permissions.PERMISSION_PHONE)) {
                f4796k.m4374e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
            } else if (C1442k.m4433k(context)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    f4792g = telephonyManager.getSimOperator();
                }
            }
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
        return f4792g;
    }

    public static String m4432j(Context context) {
        if (C1442k.m4420c(f4793h)) {
            return f4793h;
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            f4793h = str;
            if (str == null) {
                return "";
            }
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
        return f4793h;
    }

    public static boolean m4433k(Context context) {
        return context.getPackageManager().checkPermission(Permissions.PERMISSION_PHONE, context.getPackageName()) == 0;
    }

    public static String m4434l(Context context) {
        String str = "";
        try {
            if (C1448q.m4465a(context, "android.permission.INTERNET") && C1448q.m4465a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                String typeName;
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    typeName = activeNetworkInfo.getTypeName();
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (typeName != null) {
                        if (typeName.equalsIgnoreCase("WIFI")) {
                            return "WIFI";
                        }
                        if (typeName.equalsIgnoreCase("MOBILE")) {
                            return extraInfo != null ? extraInfo : "MOBILE";
                        } else {
                            if (extraInfo != null) {
                                return extraInfo;
                            }
                            return typeName;
                        }
                    }
                }
                typeName = str;
                return typeName;
            }
            f4796k.m4374e((Object) "can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return str;
        } catch (Throwable th) {
            f4796k.m4375e(th);
            return str;
        }
    }

    public static Integer m4435m(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String m4436n(Context context) {
        if (C1442k.m4420c(f4794i)) {
            return f4794i;
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            f4794i = str;
            if (str == null || f4794i.length() == 0) {
                return "unknown";
            }
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
        return f4794i;
    }

    public static int m4437o(Context context) {
        if (f4795j != -1) {
            return f4795j;
        }
        try {
            if (C1446o.m4453a()) {
                f4795j = 1;
            }
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
        f4795j = 0;
        return 0;
    }

    public static String m4438p(Context context) {
        if (C1442k.m4420c(f4797l)) {
            return f4797l;
        }
        try {
            if (C1448q.m4465a(context, Permissions.PERMISSION_WRITE_STORAGE)) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState != null && externalStorageState.equals("mounted")) {
                    externalStorageState = Environment.getExternalStorageDirectory().getPath();
                    if (externalStorageState != null) {
                        StatFs statFs = new StatFs(externalStorageState);
                        long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / TimeConstants.NANOSECONDS_PER_MILLISECOND;
                        externalStorageState = String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / TimeConstants.NANOSECONDS_PER_MILLISECOND) + "/" + String.valueOf(blockCount);
                        f4797l = externalStorageState;
                        return externalStorageState;
                    }
                }
                return null;
            }
            f4796k.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            return null;
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
    }

    static String m4439q(Context context) {
        try {
            if (f4798m != null) {
                return f4798m;
            }
            int myPid = Process.myPid();
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    f4798m = runningAppProcessInfo.processName;
                    break;
                }
            }
            return f4798m;
        } catch (Throwable th) {
        }
    }

    public static String m4440r(Context context) {
        return C1442k.m4409a(context, StatConstants.DATABASE_NAME);
    }

    public static synchronized Integer m4441s(Context context) {
        Integer valueOf;
        int i = 0;
        synchronized (C1442k.class) {
            if (f4799n <= 0) {
                f4799n = C1447p.m4454a(context, "MTA_EVENT_INDEX", 0);
                C1447p.m4458b(context, "MTA_EVENT_INDEX", f4799n + 1000);
            } else if (f4799n % 1000 == 0) {
                try {
                    int i2 = f4799n + 1000;
                    if (f4799n < 2147383647) {
                        i = i2;
                    }
                    C1447p.m4458b(context, "MTA_EVENT_INDEX", i);
                } catch (Throwable th) {
                    f4796k.m4378w(th);
                }
            }
            i = f4799n + 1;
            f4799n = i;
            valueOf = Integer.valueOf(i);
        }
        return valueOf;
    }

    public static String m4442t(Context context) {
        try {
            return String.valueOf(C1442k.m4403D(context) / TimeConstants.NANOSECONDS_PER_MILLISECOND) + "/" + String.valueOf(C1442k.m4429h() / TimeConstants.NANOSECONDS_PER_MILLISECOND);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static JSONObject m4443u(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", C1443l.m4449a());
            String d = C1443l.m4452d();
            if (d != null && d.length() > 0) {
                jSONObject.put("na", d);
            }
            int b = C1443l.m4450b();
            if (b > 0) {
                jSONObject.put("fx", b / TimeConstants.MICROSECONDS_PER_SECOND);
            }
            b = C1443l.m4451c();
            if (b > 0) {
                jSONObject.put("fn", b / TimeConstants.MICROSECONDS_PER_SECOND);
            }
        } catch (Throwable th) {
            Log.w(StatConstants.LOG_TAG, "get cpu error", th);
        }
        return jSONObject;
    }

    public static String m4444v(Context context) {
        if (C1442k.m4420c(f4803r)) {
            return f4803r;
        }
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null) {
                List sensorList = sensorManager.getSensorList(-1);
                if (sensorList != null) {
                    StringBuilder stringBuilder = new StringBuilder(sensorList.size() * 10);
                    for (int i = 0; i < sensorList.size(); i++) {
                        stringBuilder.append(((Sensor) sensorList.get(i)).getType());
                        if (i != sensorList.size() - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    f4803r = stringBuilder.toString();
                }
            }
        } catch (Throwable th) {
            f4796k.m4375e(th);
        }
        return f4803r;
    }

    public static synchronized int m4445w(Context context) {
        int i;
        synchronized (C1442k.class) {
            if (f4806u != -1) {
                i = f4806u;
            } else {
                C1442k.m4446x(context);
                i = f4806u;
            }
        }
        return i;
    }

    public static void m4446x(Context context) {
        int a = C1447p.m4454a(context, f4805t, 1);
        f4806u = a;
        if (a == 1) {
            C1447p.m4458b(context, f4805t, 0);
        }
    }

    public static boolean m4447y(Context context) {
        if (f4807v < 0) {
            f4807v = C1447p.m4455a(context, "mta.qq.com.checktime", 0);
        }
        return Math.abs(System.currentTimeMillis() - f4807v) > LogBuilder.MAX_INTERVAL;
    }

    public static void m4448z(Context context) {
        f4807v = System.currentTimeMillis();
        C1447p.m4459b(context, "mta.qq.com.checktime", f4807v);
    }
}
