package com.tencent.stat;

import android.content.Context;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.C1394p;
import com.tencent.stat.common.StatLogger;
import com.tencent.wxop.stat.common.StatConstants;
import java.util.Iterator;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class StatConfig {
    private static int f4286A = 4096;
    private static boolean f4287B = false;
    private static String f4288C = null;
    static C1377b f4289a = new C1377b(2);
    static C1377b f4290b = new C1377b(1);
    static String f4291c = "__HIBERNATE__";
    static String f4292d = "";
    private static StatLogger f4293e = C1389k.m4125b();
    private static StatReportStrategy f4294f = StatReportStrategy.APP_LAUNCH;
    private static boolean f4295g = true;
    private static int f4296h = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    private static int f4297i = 1024;
    public static boolean isAutoExceptionCaught = true;
    private static int f4298j = 30;
    private static int f4299k = 3;
    private static int f4300l = 30;
    private static String f4301m = null;
    private static String f4302n;
    private static String f4303o;
    private static int f4304p = 1440;
    private static int f4305q = 1024;
    private static boolean f4306r = true;
    private static long f4307s = 0;
    private static long f4308t = 300000;
    private static String f4309u = StatConstants.MTA_REPORT_FULL_URL;
    private static int f4310v = 0;
    private static volatile int f4311w = 0;
    private static int f4312x = 20;
    private static int f4313y = 0;
    private static boolean f4314z = false;

    static int m4003a() {
        return f4298j;
    }

    static String m4004a(Context context) {
        return C1389k.m4133d(C1394p.m4169a(context, "_mta_ky_tag_", null));
    }

    static String m4005a(String str, String str2) {
        try {
            String string = f4290b.f4388b.getString(str);
            return string != null ? string : str2;
        } catch (Throwable th) {
            f4293e.m4088w(th);
            return str2;
        }
    }

    static synchronized void m4006a(int i) {
        synchronized (StatConfig.class) {
            f4311w = i;
        }
    }

    static void m4007a(Context context, String str) {
        if (str != null) {
            C1394p.m4172b(context, "_mta_ky_tag_", C1389k.m4130c(str));
        }
    }

    static void m4008a(C1377b c1377b) {
        if (c1377b.f4387a == f4290b.f4387a) {
            f4290b = c1377b;
            m4016b(f4290b.f4388b);
        } else if (c1377b.f4387a == f4289a.f4387a) {
            f4289a = c1377b;
        }
    }

    static void m4009a(C1377b c1377b, JSONObject jSONObject) {
        Object obj;
        Object obj2 = null;
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase("v")) {
                    int i = jSONObject.getInt(str);
                    obj = c1377b.f4390d != i ? 1 : obj2;
                    c1377b.f4390d = i;
                } else if (str.equalsIgnoreCase("c")) {
                    str = jSONObject.getString("c");
                    if (str.length() > 0) {
                        c1377b.f4388b = new JSONObject(str);
                    }
                    obj = obj2;
                } else {
                    if (str.equalsIgnoreCase("m")) {
                        c1377b.f4389c = jSONObject.getString("m");
                    }
                    obj = obj2;
                }
                obj2 = obj;
            }
            if (obj2 == 1) {
                C1405n a = C1405n.m4189a(C1395d.m4173a());
                if (a != null) {
                    a.m4210a(c1377b);
                }
                if (c1377b.f4387a == f4290b.f4387a) {
                    m4016b(c1377b.f4388b);
                    m4018c(c1377b.f4388b);
                }
            }
        } catch (Exception e) {
            f4293e.m4084e(e);
        } catch (Object obj3) {
            f4293e.m4085e(obj3);
        }
    }

    static void m4010a(JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase(Integer.toString(f4290b.f4387a))) {
                    m4009a(f4290b, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase(Integer.toString(f4289a.f4387a))) {
                    m4009a(f4289a, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase("rs")) {
                    StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt(str));
                    if (statReportStrategy != null) {
                        f4294f = statReportStrategy;
                        f4293e.m4083d("Change to ReportStrategy:" + statReportStrategy.name());
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            f4293e.m4084e(e);
        }
    }

    static void m4011a(boolean z) {
        StatNativeCrashReport.setNativeCrashDebugEnable(z);
    }

    static boolean m4012a(int i, int i2, int i3) {
        return i >= i2 && i <= i3;
    }

    private static boolean m4013a(String str) {
        if (str == null) {
            return false;
        }
        if (f4302n == null) {
            f4302n = str;
            return true;
        } else if (f4302n.contains(str)) {
            return false;
        } else {
            f4302n += "|" + str;
            return true;
        }
    }

    static HttpHost m4014b() {
        if (f4301m == null || f4301m.length() <= 0) {
            return null;
        }
        String str = f4301m;
        String[] split = str.split(":");
        int i = 80;
        if (split.length == 2) {
            str = split[0];
            i = Integer.parseInt(split[1]);
        }
        return new HttpHost(str, i);
    }

    static void m4015b(int i) {
        if (i >= 0) {
            f4313y = i;
        }
    }

    static void m4016b(JSONObject jSONObject) {
        try {
            StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt("rs"));
            if (statReportStrategy != null) {
                setStatSendStrategy(statReportStrategy);
            }
        } catch (JSONException e) {
            f4293e.m4083d("rs not found.");
        }
    }

    static synchronized void m4017c() {
        synchronized (StatConfig.class) {
            f4311w++;
        }
    }

    static void m4018c(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString(f4291c);
            f4293e.m4083d("hibernateVer:" + string + ", current version:" + com.tencent.stat.common.StatConstants.VERSION);
            long b = C1389k.m4124b(string);
            if (C1389k.m4124b(com.tencent.stat.common.StatConstants.VERSION) <= b) {
                C1394p.m4171b(C1395d.m4173a(), f4291c, b);
                setEnableStatService(false);
                f4293e.warn("MTA has disable for SDK version of " + string + " or lower.");
            }
        } catch (JSONException e) {
            f4293e.m4083d("__HIBERNATE__ not found.");
        }
    }

    static void m4019d() {
        f4313y++;
    }

    static int m4020e() {
        return f4313y;
    }

    public static synchronized String getAppKey(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (f4302n != null) {
                str = f4302n;
            } else {
                if (context != null) {
                    if (f4302n == null) {
                        f4302n = C1389k.m4143i(context);
                    }
                }
                if (f4302n == null || f4302n.trim().length() == 0) {
                    f4293e.error((Object) "AppKey can not be null or empty, please read Developer's Guide first!");
                }
                str = f4302n;
            }
        }
        return str;
    }

    public static int getCurSessionStatReportCount() {
        return f4311w;
    }

    public static String getCustomProperty(String str) {
        try {
            return f4289a.f4388b.getString(str);
        } catch (Object th) {
            f4293e.m4085e(th);
            return null;
        }
    }

    public static String getCustomProperty(String str, String str2) {
        try {
            String string = f4289a.f4388b.getString(str);
            return string != null ? string : str2;
        } catch (Object th) {
            f4293e.m4085e(th);
            return str2;
        }
    }

    public static String getCustomUserId(Context context) {
        if (context == null) {
            f4293e.error((Object) "Context for getCustomUid is null.");
            return null;
        }
        if (f4288C == null) {
            f4288C = C1394p.m4169a(context, "MTA_CUSTOM_UID", "");
        }
        return f4288C;
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        return StatMid.getDeviceInfo(context);
    }

    public static synchronized String getInstallChannel(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (f4303o != null) {
                str = f4303o;
            } else {
                f4303o = C1389k.m4144j(context);
                if (f4303o == null || f4303o.trim().length() == 0) {
                    f4293e.m4088w("installChannel can not be null or empty, please read Developer's Guide first!");
                }
                str = f4303o;
            }
        }
        return str;
    }

    public static int getMaxBatchReportCount() {
        return f4300l;
    }

    public static int getMaxDaySessionNumbers() {
        return f4312x;
    }

    public static int getMaxParallelTimmingEvents() {
        return f4305q;
    }

    public static int getMaxReportEventLength() {
        return f4286A;
    }

    public static int getMaxSendRetryCount() {
        return f4299k;
    }

    public static int getMaxSessionStatReportCount() {
        return f4310v;
    }

    public static int getMaxStoreEventCount() {
        return f4297i;
    }

    public static String getMid(Context context) {
        return StatMid.getMid(context);
    }

    public static String getQQ() {
        return f4292d;
    }

    public static int getSendPeriodMinutes() {
        return f4304p;
    }

    public static int getSessionTimoutMillis() {
        return f4296h;
    }

    public static String getStatReportUrl() {
        return f4309u;
    }

    public static StatReportStrategy getStatSendStrategy() {
        return f4294f;
    }

    public static void initNativeCrashReport(Context context, String str) {
        if (!isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4293e.error((Object) "The Context of StatConfig.initNativeCrashReport() can not be null!");
        } else {
            StatNativeCrashReport.initNativeCrash(context, str);
        }
    }

    public static boolean isAutoExceptionCaught() {
        return isAutoExceptionCaught;
    }

    public static boolean isDebugEnable() {
        return C1389k.m4125b().isDebugEnable();
    }

    public static boolean isEnableConcurrentProcess() {
        return f4287B;
    }

    public static boolean isEnableSmartReporting() {
        return f4306r;
    }

    public static boolean isEnableStatService() {
        return f4295g;
    }

    public static void setAppKey(Context context, String str) {
        if (context == null) {
            f4293e.error((Object) "ctx in StatConfig.setAppKey() is null");
        } else if (str == null || str.length() > 256) {
            f4293e.error((Object) "appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        } else {
            if (f4302n == null) {
                f4302n = m4004a(context);
            }
            if ((m4013a(str) | m4013a(C1389k.m4143i(context))) != 0) {
                m4007a(context, f4302n);
            }
        }
    }

    public static void setAppKey(String str) {
        if (str == null) {
            f4293e.error((Object) "appkey in StatConfig.setAppKey() is null");
        } else if (str.length() > 256) {
            f4293e.error((Object) "The length of appkey cann't exceed 256 bytes.");
        } else {
            f4302n = str;
        }
    }

    public static void setAutoExceptionCaught(boolean z) {
        isAutoExceptionCaught = z;
    }

    public static void setCustomUserId(Context context, String str) {
        if (context == null) {
            f4293e.error((Object) "Context for setCustomUid is null.");
            return;
        }
        C1394p.m4172b(context, "MTA_CUSTOM_UID", str);
        f4288C = str;
    }

    public static void setDebugEnable(boolean z) {
        C1389k.m4125b().setDebugEnable(z);
        m4011a(z);
    }

    public static void setEnableConcurrentProcess(boolean z) {
        f4287B = z;
    }

    public static void setEnableSmartReporting(boolean z) {
        f4306r = z;
    }

    public static void setEnableStatService(boolean z) {
        f4295g = z;
        if (!z) {
            f4293e.warn("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    public static void setInstallChannel(String str) {
        if (str.length() > 128) {
            f4293e.error((Object) "the length of installChannel can not exceed the range of 128 bytes.");
        } else {
            f4303o = str;
        }
    }

    public static void setMaxBatchReportCount(int i) {
        if (m4012a(i, 2, 1000)) {
            f4300l = i;
        } else {
            f4293e.error((Object) "setMaxBatchReportCount can not exceed the range of [2,1000].");
        }
    }

    public static void setMaxDaySessionNumbers(int i) {
        if (i <= 0) {
            f4293e.m4085e((Object) "maxDaySessionNumbers must be greater than 0.");
        } else {
            f4312x = i;
        }
    }

    public static void setMaxParallelTimmingEvents(int i) {
        if (m4012a(i, 1, 4096)) {
            f4305q = i;
        } else {
            f4293e.error((Object) "setMaxParallelTimmingEvents can not exceed the range of [1, 4096].");
        }
    }

    public static void setMaxReportEventLength(int i) {
        if (i <= 0) {
            f4293e.error((Object) "maxReportEventLength on setMaxReportEventLength() must greater than 0.");
        } else {
            f4286A = i;
        }
    }

    public static void setMaxSendRetryCount(int i) {
        if (m4012a(i, 1, 1000)) {
            f4299k = i;
        } else {
            f4293e.error((Object) "setMaxSendRetryCount can not exceed the range of [1,1000].");
        }
    }

    public static void setMaxSessionStatReportCount(int i) {
        if (i < 0) {
            f4293e.error((Object) "maxSessionStatReportCount cannot be less than 0.");
        } else {
            f4310v = i;
        }
    }

    public static void setMaxStoreEventCount(int i) {
        if (m4012a(i, 0, 500000)) {
            f4297i = i;
        } else {
            f4293e.error((Object) "setMaxStoreEventCount can not exceed the range of [0, 500000].");
        }
    }

    public static void setQQ(Context context, String str) {
        StatService.reportQQ(context, str);
    }

    public static void setSendPeriodMinutes(int i) {
        if (m4012a(i, 1, 10080)) {
            f4304p = i;
        } else {
            f4293e.error((Object) "setSendPeriodMinutes can not exceed the range of [1, 7*24*60] minutes.");
        }
    }

    public static void setSessionTimoutMillis(int i) {
        if (m4012a(i, 1000, 86400000)) {
            f4296h = i;
        } else {
            f4293e.error((Object) "setSessionTimoutMillis can not exceed the range of [1000, 24 * 60 * 60 * 1000].");
        }
    }

    public static void setStatReportUrl(String str) {
        if (str == null || str.length() == 0) {
            f4293e.error((Object) "statReportUrl cannot be null or empty.");
        } else {
            f4309u = str;
        }
    }

    public static void setStatSendStrategy(StatReportStrategy statReportStrategy) {
        f4294f = statReportStrategy;
        f4293e.m4083d("Change to statSendStrategy: " + statReportStrategy);
    }
}
