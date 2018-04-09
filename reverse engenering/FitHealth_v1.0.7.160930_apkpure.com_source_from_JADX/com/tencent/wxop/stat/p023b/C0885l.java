package com.tencent.wxop.stat.p023b;

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
import com.tencent.wxop.stat.C0894c;
import com.tencent.wxop.stat.C0897f;
import com.zhuoyi.system.util.constant.SeparatorConstants;
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
import org.apache.http.HttpHost;
import org.json.JSONObject;

public final class C0885l {
    private static int f3032U = -1;
    private static String f3033W = null;
    private static String f3034a = null;
    private static String aR = null;
    private static String f3035b = null;
    private static int bG = -1;
    private static volatile int bn = -1;
    private static String bq = null;
    private static String br = "";
    private static String bs = "";
    private static String f3036c = null;
    private static String cC = null;
    private static String cE = "";
    private static Random cR = null;
    private static DisplayMetrics cS = null;
    private static C0877b cT = null;
    private static String cU = null;
    private static String cV = null;
    private static long cW = -1;
    private static C0888o cX = null;
    private static String cY = "__MTA_FIRST_ACTIVATE__";
    private static long cZ = -1;
    private static String da = "";
    private static int f3037w = 0;

    public static String m2867A(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                cT.m2853c("Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            cT.m2854d("Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String m2868B(Context context) {
        return context == null ? null : context.getClass().getName();
    }

    public static String m2869C(Context context) {
        if (bq != null) {
            return bq;
        }
        try {
            if (C0891r.m2919a(context, "android.permission.READ_PHONE_STATE")) {
                if ((context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) != 0 ? null : 1) != null) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    if (telephonyManager != null) {
                        bq = telephonyManager.getSimOperator();
                    }
                }
            } else {
                cT.m2854d("Could not get permission of android.permission.READ_PHONE_STATE");
            }
        } catch (Throwable th) {
            cT.m2852b(th);
        }
        return bq;
    }

    public static String m2870D(Context context) {
        if (C0885l.m2894e(br)) {
            return br;
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            br = str;
            if (str == null) {
                return "";
            }
        } catch (Throwable th) {
            cT.m2852b(th);
        }
        return br;
    }

    public static String m2871E(Context context) {
        String str = "";
        try {
            if (C0891r.m2919a(context, "android.permission.INTERNET") && C0891r.m2919a(context, "android.permission.ACCESS_NETWORK_STATE")) {
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
            cT.m2854d("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return str;
        } catch (Throwable th) {
            cT.m2852b(th);
            return str;
        }
    }

    public static Integer m2872F(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String m2873G(Context context) {
        if (C0885l.m2894e(bs)) {
            return bs;
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            bs = str;
            if (str == null || bs.length() == 0) {
                return "unknown";
            }
        } catch (Throwable th) {
            cT.m2852b(th);
        }
        return bs;
    }

    public static String m2874H(Context context) {
        if (C0885l.m2894e(cU)) {
            return cU;
        }
        try {
            if (C0891r.m2919a(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState != null && externalStorageState.equals("mounted")) {
                    externalStorageState = Environment.getExternalStorageDirectory().getPath();
                    if (externalStorageState != null) {
                        StatFs statFs = new StatFs(externalStorageState);
                        long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000;
                        externalStorageState = String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf(blockCount);
                        cU = externalStorageState;
                        return externalStorageState;
                    }
                }
                return null;
            }
            cT.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            return null;
        } catch (Throwable th) {
            cT.m2852b(th);
        }
    }

    static String m2875I(Context context) {
        try {
            if (aR != null) {
                return aR;
            }
            int myPid = Process.myPid();
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    aR = runningAppProcessInfo.processName;
                    break;
                }
            }
            return aR;
        } catch (Throwable th) {
        }
    }

    public static String m2876J(Context context) {
        return C0885l.m2893e(context, C0876a.ct);
    }

    public static synchronized Integer m2878K(Context context) {
        Integer valueOf;
        int i = 0;
        synchronized (C0885l.class) {
            if (bn <= 0) {
                bn = C0890q.m2907a(context, "MTA_EVENT_INDEX", 0);
                C0890q.m2910b(context, "MTA_EVENT_INDEX", bn + 1000);
            } else if (bn % 1000 == 0) {
                try {
                    int i2 = bn + 1000;
                    if (bn < 2147383647) {
                        i = i2;
                    }
                    C0890q.m2910b(context, "MTA_EVENT_INDEX", i);
                } catch (Throwable th) {
                    cT.m2853c(th);
                }
            }
            i = bn + 1;
            bn = i;
            valueOf = Integer.valueOf(i);
        }
        return valueOf;
    }

    public static String m2879L(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            MemoryInfo memoryInfo = new MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return String.valueOf(memoryInfo.availMem / 1000000) + "/" + String.valueOf(C0885l.ay() / 1000000);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String m2880M(Context context) {
        if (C0885l.m2894e(cE)) {
            return cE;
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
                            stringBuilder.append(SeparatorConstants.SEPARATOR_ADS_ID);
                        }
                    }
                    cE = stringBuilder.toString();
                }
            }
        } catch (Throwable th) {
            cT.m2852b(th);
        }
        return cE;
    }

    public static synchronized int m2881N(Context context) {
        int i;
        synchronized (C0885l.class) {
            if (f3032U != -1) {
                i = f3032U;
            } else {
                C0885l.m2882O(context);
                i = f3032U;
            }
        }
        return i;
    }

    public static void m2882O(Context context) {
        int a = C0890q.m2907a(context, cY, 1);
        f3032U = a;
        if (a == 1) {
            C0890q.m2910b(context, cY, 0);
        }
    }

    public static boolean m2883P(Context context) {
        if (cZ < 0) {
            cZ = C0890q.m2912f(context, "mta.qq.com.checktime");
        }
        return Math.abs(System.currentTimeMillis() - cZ) > 86400000;
    }

    public static void m2884Q(Context context) {
        cZ = System.currentTimeMillis();
        C0890q.m2908a(context, "mta.qq.com.checktime", cZ);
    }

    public static String m2885R(Context context) {
        if (context == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        return (resolveActivity.activityInfo == null || resolveActivity.activityInfo.packageName.equals("android")) ? null : resolveActivity.activityInfo.packageName;
    }

    public static int m2886a(Context context, boolean z) {
        if (z) {
            f3037w = C0890q.m2907a(context, "mta.qq.com.difftime", 0);
        }
        return f3037w;
    }

    private static Long m2887a(String str, String str2, Long l) {
        if (str == null || str2 == null) {
            return l;
        }
        if (str2.equalsIgnoreCase(".") || str2.equalsIgnoreCase("|")) {
            str2 = "\\" + str2;
        }
        String[] split = str.split(str2);
        if (split.length != 3) {
            return l;
        }
        try {
            Long valueOf = Long.valueOf(0);
            int i = 0;
            while (i < split.length) {
                Long valueOf2 = Long.valueOf(100 * (valueOf.longValue() + Long.valueOf(split[i]).longValue()));
                i++;
                valueOf = valueOf2;
            }
            return valueOf;
        } catch (NumberFormatException e) {
            return l;
        }
    }

    public static void m2888a(Context context, int i) {
        f3037w = i;
        C0890q.m2910b(context, "mta.qq.com.difftime", i);
    }

    public static boolean m2889a(C0897f c0897f) {
        return c0897f == null ? false : C0885l.m2894e(c0897f.m3004S());
    }

    public static long ad() {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            return instance.getTimeInMillis() + 86400000;
        } catch (Throwable th) {
            cT.m2852b(th);
            return System.currentTimeMillis() + 86400000;
        }
    }

    private static synchronized Random at() {
        Random random;
        synchronized (C0885l.class) {
            if (cR == null) {
                cR = new Random();
            }
            random = cR;
        }
        return random;
    }

    public static int au() {
        if (bG != -1) {
            return bG;
        }
        try {
            if (C0889p.m2905a()) {
                bG = 1;
            }
        } catch (Throwable th) {
            cT.m2852b(th);
        }
        bG = 0;
        return 0;
    }

    public static synchronized C0877b av() {
        C0877b c0877b;
        synchronized (C0885l.class) {
            if (cT == null) {
                c0877b = new C0877b("MtaSDK");
                cT = c0877b;
                c0877b.ap();
            }
            c0877b = cT;
        }
        return c0877b;
    }

    public static String aw() {
        Calendar instance = Calendar.getInstance();
        instance.roll(6, 0);
        return new SimpleDateFormat("yyyyMMdd").format(instance.getTime());
    }

    public static String ax() {
        if (C0885l.m2894e(cC)) {
            return cC;
        }
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000;
        StatFs statFs2 = new StatFs(Environment.getDataDirectory().getPath());
        String str = String.valueOf((((long) statFs2.getAvailableBlocks()) * ((long) statFs2.getBlockSize())) / 1000000) + "/" + String.valueOf(blockCount);
        cC = str;
        return str;
    }

    private static long ay() {
        if (cW > 0) {
            return cW;
        }
        long j = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            j = (long) (Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue() * 1024);
            bufferedReader.close();
        } catch (Exception e) {
        }
        cW = j;
        return j;
    }

    public static JSONObject az() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", C0886m.m2904r());
            String ax = C0886m.ax();
            if (ax != null && ax.length() > 0) {
                jSONObject.put("na", ax);
            }
            int aA = C0886m.aA();
            if (aA > 0) {
                jSONObject.put("fx", aA / 1000000);
            }
            aA = C0886m.m2903D();
            if (aA > 0) {
                jSONObject.put("fn", aA / 1000000);
            }
        } catch (Throwable th) {
            Log.w("MtaSDK", "get cpu error", th);
        }
        return jSONObject;
    }

    public static byte[] m2890b(byte[] bArr) {
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

    public static synchronized String m2891c(Context context) {
        String b;
        synchronized (C0885l.class) {
            if (f3034a == null || f3034a.trim().length() == 0) {
                b = C0891r.m2920b(context);
                f3034a = b;
                if (b == null || f3034a.trim().length() == 0) {
                    f3034a = Integer.toString(C0885l.at().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
                }
                b = f3034a;
            } else {
                b = f3034a;
            }
        }
        return b;
    }

    public static String m2892d(long j) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(j));
    }

    public static String m2893e(Context context, String str) {
        if (!C0894c.m2931E()) {
            return str;
        }
        if (aR == null) {
            aR = C0885l.m2875I(context);
        }
        return aR != null ? str + "_" + aR : str;
    }

    public static boolean m2894e(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static int m2895r() {
        return C0885l.at().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static String m2896t(String str) {
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

    public static long m2897u(String str) {
        return C0885l.m2887a(str, ".", Long.valueOf(0)).longValue();
    }

    public static HttpHost m2898v(Context context) {
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
            cT.m2852b(th);
        }
    }

    public static synchronized String m2899w(Context context) {
        String str;
        synchronized (C0885l.class) {
            if (f3036c == null || f3036c.trim().length() == 0) {
                f3036c = C0891r.m2921c(context);
            }
            str = f3036c;
        }
        return str;
    }

    public static DisplayMetrics m2900x(Context context) {
        if (cS == null) {
            cS = new DisplayMetrics();
            ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(cS);
        }
        return cS;
    }

    public static boolean m2901y(Context context) {
        try {
            if (C0891r.m2919a(context, "android.permission.ACCESS_WIFI_STATE")) {
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
            cT.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return false;
        } catch (Throwable th) {
            cT.m2852b(th);
        }
    }

    public static String m2902z(Context context) {
        if (f3035b != null) {
            return f3035b;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    f3035b = string;
                    return string;
                }
                cT.m2851b((Object) "Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            cT.m2851b((Object) "Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }
}
