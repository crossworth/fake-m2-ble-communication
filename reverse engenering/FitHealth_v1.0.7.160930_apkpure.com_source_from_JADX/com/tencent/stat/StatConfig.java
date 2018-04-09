package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.C0842p;
import com.tencent.stat.common.StatConstants;
import com.tencent.stat.common.StatLogger;
import java.util.Iterator;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class StatConfig {
    private static int f2784A = 4096;
    private static boolean f2785B = false;
    private static String f2786C = null;
    static C0827b f2787a = new C0827b(2);
    static C0827b f2788b = new C0827b(1);
    static String f2789c = "__HIBERNATE__";
    static String f2790d = "";
    private static StatLogger f2791e = C0837k.m2718b();
    private static StatReportStrategy f2792f = StatReportStrategy.APP_LAUNCH;
    private static boolean f2793g = true;
    private static int f2794h = 30000;
    private static int f2795i = 1024;
    public static boolean isAutoExceptionCaught = true;
    private static int f2796j = 30;
    private static int f2797k = 3;
    private static int f2798l = 30;
    private static String f2799m = null;
    private static String f2800n;
    private static String f2801o;
    private static int f2802p = 1440;
    private static int f2803q = 1024;
    private static boolean f2804r = true;
    private static long f2805s = 0;
    private static long f2806t = 300000;
    private static String f2807u = "http://pingma.qq.com:80/mstat/report";
    private static int f2808v = 0;
    private static volatile int f2809w = 0;
    private static int f2810x = 20;
    private static int f2811y = 0;
    private static boolean f2812z = false;

    static int m2619a() {
        return f2796j;
    }

    static String m2620a(Context context) {
        return C0837k.m2726d(C0842p.m2762a(context, "_mta_ky_tag_", null));
    }

    static String m2621a(String str, String str2) {
        try {
            String string = f2788b.f2868b.getString(str);
            return string != null ? string : str2;
        } catch (Throwable th) {
            f2791e.m2683w(th);
            return str2;
        }
    }

    static synchronized void m2622a(int i) {
        synchronized (StatConfig.class) {
            f2809w = i;
        }
    }

    static void m2623a(Context context, String str) {
        if (str != null) {
            C0842p.m2765b(context, "_mta_ky_tag_", C0837k.m2723c(str));
        }
    }

    static void m2624a(C0827b c0827b) {
        if (c0827b.f2867a == f2788b.f2867a) {
            f2788b = c0827b;
            m2632b(f2788b.f2868b);
        } else if (c0827b.f2867a == f2787a.f2867a) {
            f2787a = c0827b;
        }
    }

    static void m2625a(C0827b c0827b, JSONObject jSONObject) {
        Object obj;
        Object obj2 = null;
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase("v")) {
                    int i = jSONObject.getInt(str);
                    obj = c0827b.f2870d != i ? 1 : obj2;
                    c0827b.f2870d = i;
                } else if (str.equalsIgnoreCase("c")) {
                    str = jSONObject.getString("c");
                    if (str.length() > 0) {
                        c0827b.f2868b = new JSONObject(str);
                    }
                    obj = obj2;
                } else {
                    if (str.equalsIgnoreCase("m")) {
                        c0827b.f2869c = jSONObject.getString("m");
                    }
                    obj = obj2;
                }
                obj2 = obj;
            }
            if (obj2 == 1) {
                C0850n a = C0850n.m2778a(C0843d.m2766a());
                if (a != null) {
                    a.m2799a(c0827b);
                }
                if (c0827b.f2867a == f2788b.f2867a) {
                    m2632b(c0827b.f2868b);
                    m2634c(c0827b.f2868b);
                }
            }
        } catch (Exception e) {
            f2791e.m2679e(e);
        } catch (Object obj3) {
            f2791e.m2680e(obj3);
        }
    }

    static void m2626a(JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase(Integer.toString(f2788b.f2867a))) {
                    m2625a(f2788b, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase(Integer.toString(f2787a.f2867a))) {
                    m2625a(f2787a, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase("rs")) {
                    StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt(str));
                    if (statReportStrategy != null) {
                        f2792f = statReportStrategy;
                        f2791e.m2678d("Change to ReportStrategy:" + statReportStrategy.name());
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            f2791e.m2679e(e);
        }
    }

    static void m2627a(boolean z) {
        StatNativeCrashReport.setNativeCrashDebugEnable(z);
    }

    static boolean m2628a(int i, int i2, int i3) {
        return i >= i2 && i <= i3;
    }

    private static boolean m2629a(String str) {
        if (str == null) {
            return false;
        }
        if (f2800n == null) {
            f2800n = str;
            return true;
        } else if (f2800n.contains(str)) {
            return false;
        } else {
            f2800n += "|" + str;
            return true;
        }
    }

    static HttpHost m2630b() {
        if (f2799m == null || f2799m.length() <= 0) {
            return null;
        }
        String str = f2799m;
        String[] split = str.split(":");
        int i = 80;
        if (split.length == 2) {
            str = split[0];
            i = Integer.parseInt(split[1]);
        }
        return new HttpHost(str, i);
    }

    static void m2631b(int i) {
        if (i >= 0) {
            f2811y = i;
        }
    }

    static void m2632b(JSONObject jSONObject) {
        try {
            StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt("rs"));
            if (statReportStrategy != null) {
                setStatSendStrategy(statReportStrategy);
            }
        } catch (JSONException e) {
            f2791e.m2678d("rs not found.");
        }
    }

    static synchronized void m2633c() {
        synchronized (StatConfig.class) {
            f2809w++;
        }
    }

    static void m2634c(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString(f2789c);
            f2791e.m2678d("hibernateVer:" + string + ", current version:" + StatConstants.VERSION);
            long b = C0837k.m2717b(string);
            if (C0837k.m2717b(StatConstants.VERSION) <= b) {
                C0842p.m2764b(C0843d.m2766a(), f2789c, b);
                setEnableStatService(false);
                f2791e.warn("MTA has disable for SDK version of " + string + " or lower.");
            }
        } catch (JSONException e) {
            f2791e.m2678d("__HIBERNATE__ not found.");
        }
    }

    static void m2635d() {
        f2811y++;
    }

    static int m2636e() {
        return f2811y;
    }

    public static synchronized String getAppKey(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (f2800n != null) {
                str = f2800n;
            } else {
                if (context != null) {
                    if (f2800n == null) {
                        f2800n = C0837k.m2736i(context);
                    }
                }
                if (f2800n == null || f2800n.trim().length() == 0) {
                    f2791e.error((Object) "AppKey can not be null or empty, please read Developer's Guide first!");
                }
                str = f2800n;
            }
        }
        return str;
    }

    public static int getCurSessionStatReportCount() {
        return f2809w;
    }

    public static String getCustomProperty(String str) {
        try {
            return f2787a.f2868b.getString(str);
        } catch (Object th) {
            f2791e.m2680e(th);
            return null;
        }
    }

    public static String getCustomProperty(String str, String str2) {
        try {
            String string = f2787a.f2868b.getString(str);
            return string != null ? string : str2;
        } catch (Object th) {
            f2791e.m2680e(th);
            return str2;
        }
    }

    public static String getCustomUserId(Context context) {
        if (context == null) {
            f2791e.error((Object) "Context for getCustomUid is null.");
            return null;
        }
        if (f2786C == null) {
            f2786C = C0842p.m2762a(context, "MTA_CUSTOM_UID", "");
        }
        return f2786C;
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        return StatMid.getDeviceInfo(context);
    }

    public static synchronized String getInstallChannel(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (f2801o != null) {
                str = f2801o;
            } else {
                f2801o = C0837k.m2737j(context);
                if (f2801o == null || f2801o.trim().length() == 0) {
                    f2791e.m2683w("installChannel can not be null or empty, please read Developer's Guide first!");
                }
                str = f2801o;
            }
        }
        return str;
    }

    public static int getMaxBatchReportCount() {
        return f2798l;
    }

    public static int getMaxDaySessionNumbers() {
        return f2810x;
    }

    public static int getMaxParallelTimmingEvents() {
        return f2803q;
    }

    public static int getMaxReportEventLength() {
        return f2784A;
    }

    public static int getMaxSendRetryCount() {
        return f2797k;
    }

    public static int getMaxSessionStatReportCount() {
        return f2808v;
    }

    public static int getMaxStoreEventCount() {
        return f2795i;
    }

    public static String getMid(Context context) {
        return StatMid.getMid(context);
    }

    public static String getQQ() {
        return f2790d;
    }

    public static int getSendPeriodMinutes() {
        return f2802p;
    }

    public static int getSessionTimoutMillis() {
        return f2794h;
    }

    public static String getStatReportUrl() {
        return f2807u;
    }

    public static StatReportStrategy getStatSendStrategy() {
        return f2792f;
    }

    public static void initNativeCrashReport(Context context, String str) {
        if (!isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2791e.error((Object) "The Context of StatConfig.initNativeCrashReport() can not be null!");
        } else {
            StatNativeCrashReport.initNativeCrash(context, str);
        }
    }

    public static boolean isAutoExceptionCaught() {
        return isAutoExceptionCaught;
    }

    public static boolean isDebugEnable() {
        return C0837k.m2718b().isDebugEnable();
    }

    public static boolean isEnableConcurrentProcess() {
        return f2785B;
    }

    public static boolean isEnableSmartReporting() {
        return f2804r;
    }

    public static boolean isEnableStatService() {
        return f2793g;
    }

    public static void setAppKey(Context context, String str) {
        if (context == null) {
            f2791e.error((Object) "ctx in StatConfig.setAppKey() is null");
        } else if (str == null || str.length() > 256) {
            f2791e.error((Object) "appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        } else {
            if (f2800n == null) {
                f2800n = m2620a(context);
            }
            if ((m2629a(str) | m2629a(C0837k.m2736i(context))) != 0) {
                m2623a(context, f2800n);
            }
        }
    }

    public static void setAppKey(String str) {
        if (str == null) {
            f2791e.error((Object) "appkey in StatConfig.setAppKey() is null");
        } else if (str.length() > 256) {
            f2791e.error((Object) "The length of appkey cann't exceed 256 bytes.");
        } else {
            f2800n = str;
        }
    }

    public static void setAutoExceptionCaught(boolean z) {
        isAutoExceptionCaught = z;
    }

    public static void setCustomUserId(Context context, String str) {
        if (context == null) {
            f2791e.error((Object) "Context for setCustomUid is null.");
            return;
        }
        C0842p.m2765b(context, "MTA_CUSTOM_UID", str);
        f2786C = str;
    }

    public static void setDebugEnable(boolean z) {
        C0837k.m2718b().setDebugEnable(z);
        m2627a(z);
    }

    public static void setEnableConcurrentProcess(boolean z) {
        f2785B = z;
    }

    public static void setEnableSmartReporting(boolean z) {
        f2804r = z;
    }

    public static void setEnableStatService(boolean z) {
        f2793g = z;
        if (!z) {
            f2791e.warn("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    public static void setInstallChannel(String str) {
        if (str.length() > 128) {
            f2791e.error((Object) "the length of installChannel can not exceed the range of 128 bytes.");
        } else {
            f2801o = str;
        }
    }

    public static void setMaxBatchReportCount(int i) {
        if (m2628a(i, 2, 1000)) {
            f2798l = i;
        } else {
            f2791e.error((Object) "setMaxBatchReportCount can not exceed the range of [2,1000].");
        }
    }

    public static void setMaxDaySessionNumbers(int i) {
        if (i <= 0) {
            f2791e.m2680e((Object) "maxDaySessionNumbers must be greater than 0.");
        } else {
            f2810x = i;
        }
    }

    public static void setMaxParallelTimmingEvents(int i) {
        if (m2628a(i, 1, 4096)) {
            f2803q = i;
        } else {
            f2791e.error((Object) "setMaxParallelTimmingEvents can not exceed the range of [1, 4096].");
        }
    }

    public static void setMaxReportEventLength(int i) {
        if (i <= 0) {
            f2791e.error((Object) "maxReportEventLength on setMaxReportEventLength() must greater than 0.");
        } else {
            f2784A = i;
        }
    }

    public static void setMaxSendRetryCount(int i) {
        if (m2628a(i, 1, 1000)) {
            f2797k = i;
        } else {
            f2791e.error((Object) "setMaxSendRetryCount can not exceed the range of [1,1000].");
        }
    }

    public static void setMaxSessionStatReportCount(int i) {
        if (i < 0) {
            f2791e.error((Object) "maxSessionStatReportCount cannot be less than 0.");
        } else {
            f2808v = i;
        }
    }

    public static void setMaxStoreEventCount(int i) {
        if (m2628a(i, 0, 500000)) {
            f2795i = i;
        } else {
            f2791e.error((Object) "setMaxStoreEventCount can not exceed the range of [0, 500000].");
        }
    }

    public static void setQQ(Context context, String str) {
        StatService.reportQQ(context, str);
    }

    public static void setSendPeriodMinutes(int i) {
        if (m2628a(i, 1, 10080)) {
            f2802p = i;
        } else {
            f2791e.error((Object) "setSendPeriodMinutes can not exceed the range of [1, 7*24*60] minutes.");
        }
    }

    public static void setSessionTimoutMillis(int i) {
        if (m2628a(i, 1000, 86400000)) {
            f2794h = i;
        } else {
            f2791e.error((Object) "setSessionTimoutMillis can not exceed the range of [1000, 24 * 60 * 60 * 1000].");
        }
    }

    public static void setStatReportUrl(String str) {
        if (str == null || str.length() == 0) {
            f2791e.error((Object) "statReportUrl cannot be null or empty.");
        } else {
            f2807u = str;
        }
    }

    public static void setStatSendStrategy(StatReportStrategy statReportStrategy) {
        f2792f = statReportStrategy;
        f2791e.m2678d("Change to statSendStrategy: " + statReportStrategy);
    }
}
