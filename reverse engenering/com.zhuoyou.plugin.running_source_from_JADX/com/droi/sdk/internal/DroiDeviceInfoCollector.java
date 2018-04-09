package com.droi.sdk.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Process;
import com.droi.sdk.analytics.priv.AnalyticsModule;
import com.droi.sdk.core.Core;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.running.tools.Tools;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class DroiDeviceInfoCollector {
    private static final String f3170a = "DroiDeviceInfoCollector";
    private static final String f3171b = "mt";
    private static final String f3172c = "mc";
    private static final String f3173d = "m01";
    private static final String f3174e = "is_first_boot";
    private static final String f3175f = "today_date";
    private static boolean f3176g = false;

    static class C0972a {
        protected static final int f3161b = 1;
        protected static final int f3162c = 0;
        protected static final int f3163d = 1;
        protected static final int f3164e = 0;
        protected static final int f3165f = 1;
        protected static final int f3166g = 2;
        protected static final int f3167h = 3;
        protected static final int f3168i = 4;
        StringBuilder f3169a = new StringBuilder();

        public C0972a(int i, int i2, int i3, String str) {
            this.f3169a.append(i).append("|").append(i2).append("|").append(i3).append("|").append(str);
        }

        public String toString() {
            return this.f3169a.toString();
        }
    }

    private static String m2857a() {
        return new SimpleDateFormat(Tools.BIRTH_FORMAT).format(Core.getTimestamp());
    }

    protected static String m2858a(Context context) {
        try {
            Signature signature = context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0];
            MessageDigest instance = MessageDigest.getInstance("SHA");
            instance.update(signature.toByteArray());
            return m2859a(instance.digest());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String m2859a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            m2861a(bArr[i], stringBuffer);
            if (i < length - 1) {
                stringBuffer.append(":");
            }
        }
        return stringBuffer.toString();
    }

    private static JSONObject m2860a(Context context, String str, String str2, boolean z) throws JSONException {
        if (str == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        putStringToJSONObject(jSONObject, "did", str);
        putStringToJSONObject(jSONObject, "s05", DroiDataCollector.getBaseStationInfo(context));
        putStringToJSONObject(jSONObject, "a03", str2);
        putStringToJSONObject(jSONObject, "a04", Core.getChannelName(context));
        putStringToJSONObject(jSONObject, "a05", getAppVersionName(context));
        putStringToJSONObject(jSONObject, "p14", DroiDataCollector.getCurNetworkType(context));
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, System.currentTimeMillis());
        if (!z) {
            return jSONObject;
        }
        putStringToJSONObject(jSONObject, "s01", DroiDataCollector.getImsi(context));
        putStringToJSONObject(jSONObject, "s02", DroiDataCollector.getSmsSc(context));
        putStringToJSONObject(jSONObject, "s03", DroiDataCollector.getIccid(context));
        putStringToJSONObject(jSONObject, "s04", DroiDataCollector.getPhoneNumber(context));
        putStringToJSONObject(jSONObject, "p01", DroiDataCollector.getImei(context));
        putStringToJSONObject(jSONObject, "p02", DroiDataCollector.getLcdSize(context));
        putStringToJSONObject(jSONObject, "p03", DroiDataCollector.getBuildModel());
        putStringToJSONObject(jSONObject, "p04", DroiDataCollector.getBuildBrand());
        putStringToJSONObject(jSONObject, "p05", DroiDataCollector.getBuildHardware());
        putStringToJSONObject(jSONObject, "p06", DroiDataCollector.getBuildVersionRelease());
        putStringToJSONObject(jSONObject, "p07", DroiDataCollector.getLang(context));
        putStringToJSONObject(jSONObject, "p08", DroiDataCollector.getCustomVersion());
        putStringToJSONObject(jSONObject, "p09", DroiDataCollector.getAndroidID(context));
        putStringToJSONObject(jSONObject, "p10", DroiDataCollector.getTatalRAMSize());
        putStringToJSONObject(jSONObject, "p11", DroiDataCollector.getTotalRomSize());
        jSONObject.put("p12", DroiDataCollector.getIsRoot());
        putStringToJSONObject(jSONObject, "p13", DroiDataCollector.getVmVersion());
        putStringToJSONObject(jSONObject, "a01", getPackageName(context));
        putStringToJSONObject(jSONObject, "a02", getAppName(context));
        jSONObject.put("a06", isSystemApp(context));
        putStringToJSONObject(jSONObject, "a07", m2858a(context));
        return jSONObject;
    }

    private static void m2861a(byte b, StringBuffer stringBuffer) {
        char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int i = b & 15;
        stringBuffer.append(cArr[(b & 240) >> 4]);
        stringBuffer.append(cArr[i]);
    }

    private static boolean m2862b(Context context) {
        return Boolean.valueOf(context.getSharedPreferences("Droi_SharedPref", 0).getBoolean(f3174e, true)).booleanValue();
    }

    private static void m2863c(Context context) {
        String str = f3174e;
        Editor edit = context.getSharedPreferences("Droi_SharedPref", 0).edit();
        edit.putBoolean(str, false);
        edit.apply();
    }

    private static boolean m2864d(Context context) {
        return !context.getSharedPreferences("Droi_SharedPref", 0).getString(f3175f, "").equals(m2857a());
    }

    private static void m2865e(Context context) {
        String str = f3175f;
        Editor edit = context.getSharedPreferences("Droi_SharedPref", 0).edit();
        edit.putString(str, m2857a());
        edit.apply();
    }

    private static String m2866f(Context context) {
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

    public static String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static boolean isSystemApp(Context context) {
        try {
            return (context.getPackageManager().getPackageInfo(getPackageName(context), 0).applicationInfo.flags & 1) != 0;
        } catch (Exception e) {
            DroiLog.m2869e(f3170a, e);
            return false;
        } catch (Exception e2) {
            DroiLog.m2869e(f3170a, e2);
            return false;
        }
    }

    public static synchronized void postDeviceInfo(Context context, String str, String str2, boolean z) {
        synchronized (DroiDeviceInfoCollector.class) {
            String packageName = context.getPackageName();
            if (!z || packageName == null || packageName.equals(m2866f(context))) {
                DroiLog.m2871i(f3170a, "hasSent:" + f3176g);
                if (!f3176g) {
                    boolean b = m2862b(context);
                    boolean d = m2864d(context);
                    DroiLog.m2871i(f3170a, "isCore:" + z + ";isFirstBoot:" + b + ";isTodayFirst:" + d);
                    if (!z || b || d) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            JSONObject a = m2860a(context, str, str2, b);
                            if (a == null) {
                                DroiLog.m2871i(f3170a, "mc == null");
                            } else {
                                jSONObject.put(f3171b, f3173d);
                                jSONObject.put("mc", a);
                                C0972a c0972a = new C0972a(1, 1, 0, f3173d);
                                AnalyticsModule analyticsModule = new AnalyticsModule(context);
                                if (analyticsModule != null) {
                                    DroiLog.m2871i(f3170a, "analyticsModule send Info");
                                    analyticsModule.send(2, c0972a.toString(), jSONObject.toString());
                                    m2863c(context);
                                    m2865e(context);
                                    f3176g = true;
                                } else {
                                    DroiLog.m2871i(f3170a, "analyticsModule == null");
                                }
                            }
                        } catch (Exception e) {
                            DroiLog.m2869e(f3170a, e);
                        }
                    }
                }
            } else {
                DroiLog.m2871i(f3170a, "in other processreturn");
            }
        }
    }

    public static void putStringToJSONObject(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (str2 != null && !str2.equals("")) {
            jSONObject.put(str, str2);
        }
    }
}
