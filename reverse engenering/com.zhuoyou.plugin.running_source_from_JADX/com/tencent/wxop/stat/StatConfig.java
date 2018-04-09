package com.tencent.wxop.stat;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.droi.sdk.core.priv.PersistSettings;
import com.tencent.p021a.p022a.p023a.p024a.C1146g;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1447p;
import com.tencent.wxop.stat.common.C1448q;
import com.tencent.wxop.stat.common.StatConstants;
import com.tencent.wxop.stat.common.StatLogger;
import com.zhuoyou.plugin.running.baas.Rank;
import java.net.URI;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class StatConfig {
    private static String f4515A = null;
    private static String f4516B;
    private static String f4517C;
    private static String f4518D = "mta_channel";
    private static int f4519E = 180;
    private static int f4520F = 1024;
    private static long f4521G = 0;
    private static long f4522H = 300000;
    private static volatile String f4523I = StatConstants.MTA_REPORT_FULL_URL;
    private static int f4524J = 0;
    private static volatile int f4525K = 0;
    private static int f4526L = 20;
    private static int f4527M = 0;
    private static boolean f4528N = false;
    private static int f4529O = 4096;
    private static boolean f4530P = false;
    private static String f4531Q = null;
    private static boolean f4532R = false;
    private static C1453g f4533S = null;
    static C1452f f4534a = new C1452f(2);
    static C1452f f4535b = new C1452f(1);
    static String f4536c = "__HIBERNATE__";
    static String f4537d = "__HIBERNATE__TIME";
    static String f4538e = "__MTA_KILL__";
    static String f4539f = "";
    static boolean f4540g = false;
    static int f4541h = 100;
    static long f4542i = 10000;
    public static boolean isAutoExceptionCaught = true;
    static boolean f4543j = true;
    static volatile String f4544k = "pingma.qq.com:80";
    static boolean f4545l = true;
    static int f4546m = 0;
    static long f4547n = 10000;
    static int f4548o = 512;
    private static StatLogger f4549p = C1442k.m4416b();
    private static StatReportStrategy f4550q = StatReportStrategy.APP_LAUNCH;
    private static boolean f4551r = false;
    private static boolean f4552s = true;
    private static int f4553t = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    private static int f4554u = Rank.MIN;
    private static int f4555v = 30;
    private static int f4556w = 10;
    private static int f4557x = 100;
    private static int f4558y = 30;
    private static int f4559z = 1;

    static int m4219a() {
        return f4555v;
    }

    static String m4220a(Context context) {
        return C1448q.m4462a(C1447p.m4457a(context, "_mta_ky_tag_", null));
    }

    static String m4221a(String str, String str2) {
        try {
            String string = f4535b.f4818b.getString(str);
            return string != null ? string : str2;
        } catch (Throwable th) {
            f4549p.m4378w("can't find custom key:" + str);
            return str2;
        }
    }

    static synchronized void m4222a(int i) {
        synchronized (StatConfig.class) {
            f4525K = i;
        }
    }

    static void m4223a(long j) {
        C1447p.m4459b(C1454i.m4483a(), f4536c, j);
        setEnableStatService(false);
        f4549p.warn("MTA is disable for current SDK version");
    }

    static void m4224a(Context context, C1452f c1452f) {
        if (c1452f.f4817a == f4535b.f4817a) {
            f4535b = c1452f;
            m4228a(c1452f.f4818b);
            if (!f4535b.f4818b.isNull(PersistSettings.KEY_IPLIST)) {
                C1428a.m4298a(context).m4306a(f4535b.f4818b.getString(PersistSettings.KEY_IPLIST));
            }
        } else if (c1452f.f4817a == f4534a.f4817a) {
            f4534a = c1452f;
        }
    }

    static void m4225a(Context context, C1452f c1452f, JSONObject jSONObject) {
        Object obj = null;
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase("v")) {
                    int i = jSONObject.getInt(str);
                    Object obj2 = c1452f.f4820d != i ? 1 : obj;
                    c1452f.f4820d = i;
                    obj = obj2;
                } else if (str.equalsIgnoreCase("c")) {
                    str = jSONObject.getString("c");
                    if (str.length() > 0) {
                        c1452f.f4818b = new JSONObject(str);
                    }
                } else if (str.equalsIgnoreCase("m")) {
                    c1452f.f4819c = jSONObject.getString("m");
                }
            }
            if (obj == 1) {
                au a = au.m4332a(C1454i.m4483a());
                if (a != null) {
                    a.m4362a(c1452f);
                }
                if (c1452f.f4817a == f4535b.f4817a) {
                    m4228a(c1452f.f4818b);
                    m4235b(c1452f.f4818b);
                }
            }
            m4224a(context, c1452f);
        } catch (Throwable e) {
            f4549p.m4375e(e);
        } catch (Throwable e2) {
            f4549p.m4375e(e2);
        }
    }

    static void m4226a(Context context, String str) {
        if (str != null) {
            C1447p.m4460b(context, "_mta_ky_tag_", C1448q.m4467b(str));
        }
    }

    static void m4227a(Context context, JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase(Integer.toString(f4535b.f4817a))) {
                    m4225a(context, f4535b, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase(Integer.toString(f4534a.f4817a))) {
                    m4225a(context, f4534a, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase("rs")) {
                    StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt(str));
                    if (statReportStrategy != null) {
                        f4550q = statReportStrategy;
                        if (isDebugEnable()) {
                            f4549p.m4373d("Change to ReportStrategy:" + statReportStrategy.name());
                        }
                    }
                } else {
                    return;
                }
            }
        } catch (Throwable e) {
            f4549p.m4375e(e);
        }
    }

    static void m4228a(JSONObject jSONObject) {
        try {
            StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt("rs"));
            if (statReportStrategy != null) {
                setStatSendStrategy(statReportStrategy);
            }
        } catch (JSONException e) {
            if (isDebugEnable()) {
                f4549p.m4376i("rs not found.");
            }
        }
    }

    static boolean m4229a(int i, int i2, int i3) {
        return i >= i2 && i <= i3;
    }

    private static boolean m4230a(String str) {
        if (str == null) {
            return false;
        }
        if (f4516B == null) {
            f4516B = str;
            return true;
        } else if (f4516B.contains(str)) {
            return false;
        } else {
            f4516B += "|" + str;
            return true;
        }
    }

    static boolean m4231a(JSONObject jSONObject, String str, String str2) {
        if (!jSONObject.isNull(str)) {
            String optString = jSONObject.optString(str);
            if (C1442k.m4420c(str2) && C1442k.m4420c(optString) && str2.equalsIgnoreCase(optString)) {
                return true;
            }
        }
        return false;
    }

    static void m4232b() {
        f4527M++;
    }

    static void m4233b(int i) {
        if (i >= 0) {
            f4527M = i;
        }
    }

    static void m4234b(Context context, JSONObject jSONObject) {
        try {
            String optString = jSONObject.optString(f4538e);
            if (C1442k.m4420c(optString)) {
                JSONObject jSONObject2 = new JSONObject(optString);
                if (jSONObject2.length() != 0) {
                    Object obj;
                    if (!jSONObject2.isNull("sm")) {
                        obj = jSONObject2.get("sm");
                        int intValue = obj instanceof Integer ? ((Integer) obj).intValue() : obj instanceof String ? Integer.valueOf((String) obj).intValue() : 0;
                        if (intValue > 0) {
                            if (isDebugEnable()) {
                                f4549p.m4376i("match sleepTime:" + intValue + " minutes");
                            }
                            C1447p.m4459b(context, f4537d, System.currentTimeMillis() + ((long) ((intValue * 60) * 1000)));
                            setEnableStatService(false);
                            f4549p.warn("MTA is disable for current SDK version");
                        }
                    }
                    if (m4231a(jSONObject2, "sv", StatConstants.VERSION)) {
                        f4549p.m4376i("match sdk version:2.0.3");
                        obj = 1;
                    } else {
                        obj = null;
                    }
                    if (m4231a(jSONObject2, "md", Build.MODEL)) {
                        f4549p.m4376i("match MODEL:" + Build.MODEL);
                        obj = 1;
                    }
                    if (m4231a(jSONObject2, "av", C1442k.m4432j(context))) {
                        f4549p.m4376i("match app version:" + C1442k.m4432j(context));
                        obj = 1;
                    }
                    if (m4231a(jSONObject2, "mf", Build.MANUFACTURER)) {
                        f4549p.m4376i("match MANUFACTURER:" + Build.MANUFACTURER);
                        obj = 1;
                    }
                    if (m4231a(jSONObject2, "osv", VERSION.SDK_INT)) {
                        f4549p.m4376i("match android SDK version:" + VERSION.SDK_INT);
                        obj = 1;
                    }
                    if (m4231a(jSONObject2, "ov", VERSION.SDK_INT)) {
                        f4549p.m4376i("match android SDK version:" + VERSION.SDK_INT);
                        obj = 1;
                    }
                    if (m4231a(jSONObject2, DeviceInfo.TAG_IMEI, au.m4332a(context).m4365b(context).m4381b())) {
                        f4549p.m4376i("match imei:" + au.m4332a(context).m4365b(context).m4381b());
                        obj = 1;
                    }
                    if (m4231a(jSONObject2, DeviceInfo.TAG_MID, getLocalMidOnly(context))) {
                        f4549p.m4376i("match mid:" + getLocalMidOnly(context));
                        obj = 1;
                    }
                    if (obj != null) {
                        m4223a(C1442k.m4415b(StatConstants.VERSION));
                    }
                }
            }
        } catch (Throwable e) {
            f4549p.m4375e(e);
        }
    }

    static void m4235b(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0) {
            try {
                m4234b(C1454i.m4483a(), jSONObject);
                String string = jSONObject.getString(f4536c);
                if (isDebugEnable()) {
                    f4549p.m4373d("hibernateVer:" + string + ", current version:2.0.3");
                }
                long b = C1442k.m4415b(string);
                if (C1442k.m4415b(StatConstants.VERSION) <= b) {
                    m4223a(b);
                }
            } catch (JSONException e) {
                f4549p.m4373d("__HIBERNATE__ not found.");
            }
        }
    }

    static int m4236c() {
        return f4527M;
    }

    public static synchronized String getAppKey(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (f4516B != null) {
                str = f4516B;
            } else {
                if (context != null) {
                    if (f4516B == null) {
                        f4516B = C1442k.m4426f(context);
                    }
                }
                if (f4516B == null || f4516B.trim().length() == 0) {
                    f4549p.error((Object) "AppKey can not be null or empty, please read Developer's Guide first!");
                }
                str = f4516B;
            }
        }
        return str;
    }

    public static int getCurSessionStatReportCount() {
        return f4525K;
    }

    public static C1453g getCustomLogger() {
        return f4533S;
    }

    public static String getCustomProperty(String str) {
        try {
            return f4534a.f4818b.getString(str);
        } catch (Throwable th) {
            f4549p.m4375e(th);
            return null;
        }
    }

    public static String getCustomProperty(String str, String str2) {
        try {
            String string = f4534a.f4818b.getString(str);
            return string != null ? string : str2;
        } catch (Throwable th) {
            f4549p.m4375e(th);
            return str2;
        }
    }

    public static String getCustomUserId(Context context) {
        if (context == null) {
            f4549p.error((Object) "Context for getCustomUid is null.");
            return null;
        }
        if (f4531Q == null) {
            f4531Q = C1447p.m4457a(context, "MTA_CUSTOM_UID", "");
        }
        return f4531Q;
    }

    public static long getFlushDBSpaceMS() {
        return f4547n;
    }

    public static synchronized String getInstallChannel(Context context) {
        String str;
        synchronized (StatConfig.class) {
            if (f4517C != null) {
                str = f4517C;
            } else {
                str = C1447p.m4457a(context, f4518D, "");
                f4517C = str;
                if (str == null || f4517C.trim().length() == 0) {
                    f4517C = C1442k.m4427g(context);
                }
                if (f4517C == null || f4517C.trim().length() == 0) {
                    f4549p.m4378w("installChannel can not be null or empty, please read Developer's Guide first!");
                }
                str = f4517C;
            }
        }
        return str;
    }

    public static String getLocalMidOnly(Context context) {
        return context != null ? C1146g.m3331E(context).m3334p().m3324a() : "0";
    }

    public static int getMaxBatchReportCount() {
        return f4558y;
    }

    public static int getMaxDaySessionNumbers() {
        return f4526L;
    }

    public static int getMaxImportantDataSendRetryCount() {
        return f4557x;
    }

    public static int getMaxParallelTimmingEvents() {
        return f4520F;
    }

    public static int getMaxReportEventLength() {
        return f4529O;
    }

    public static int getMaxSendRetryCount() {
        return f4556w;
    }

    public static int getMaxSessionStatReportCount() {
        return f4524J;
    }

    public static int getMaxStoreEventCount() {
        return f4554u;
    }

    public static String getMid(Context context) {
        return getLocalMidOnly(context);
    }

    public static long getMsPeriodForMethodsCalledLimitClear() {
        return f4542i;
    }

    public static int getNumEventsCachedInMemory() {
        return f4546m;
    }

    public static int getNumEventsCommitPerSec() {
        return f4559z;
    }

    public static int getNumOfMethodsCalledLimit() {
        return f4541h;
    }

    public static String getQQ(Context context) {
        return C1447p.m4457a(context, "mta.acc.qq", f4539f);
    }

    public static int getReportCompressedSize() {
        return f4548o;
    }

    public static int getSendPeriodMinutes() {
        return f4519E;
    }

    public static int getSessionTimoutMillis() {
        return f4553t;
    }

    public static String getStatReportHost() {
        return f4544k;
    }

    public static String getStatReportUrl() {
        return f4523I;
    }

    public static StatReportStrategy getStatSendStrategy() {
        return f4550q;
    }

    public static boolean isAutoExceptionCaught() {
        return isAutoExceptionCaught;
    }

    public static boolean isDebugEnable() {
        return f4551r;
    }

    public static boolean isEnableConcurrentProcess() {
        return f4530P;
    }

    public static boolean isEnableSmartReporting() {
        return f4543j;
    }

    public static boolean isEnableStatService() {
        return f4552s;
    }

    public static boolean isReportEventsByOrder() {
        return f4545l;
    }

    public static boolean isXGProMode() {
        return f4532R;
    }

    public static void setAppKey(Context context, String str) {
        if (context == null) {
            f4549p.error((Object) "ctx in StatConfig.setAppKey() is null");
        } else if (str == null || str.length() > 256) {
            f4549p.error((Object) "appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        } else {
            if (f4516B == null) {
                f4516B = m4220a(context);
            }
            if ((m4230a(str) | m4230a(C1442k.m4426f(context))) != 0) {
                m4226a(context, f4516B);
            }
        }
    }

    public static void setAppKey(String str) {
        if (str == null) {
            f4549p.error((Object) "appkey in StatConfig.setAppKey() is null");
        } else if (str.length() > 256) {
            f4549p.error((Object) "The length of appkey cann't exceed 256 bytes.");
        } else {
            f4516B = str;
        }
    }

    public static void setAutoExceptionCaught(boolean z) {
        isAutoExceptionCaught = z;
    }

    public static void setCustomLogger(C1453g c1453g) {
        f4533S = c1453g;
    }

    public static void setCustomUserId(Context context, String str) {
        if (context == null) {
            f4549p.error((Object) "Context for setCustomUid is null.");
            return;
        }
        C1447p.m4460b(context, "MTA_CUSTOM_UID", str);
        f4531Q = str;
    }

    public static void setDebugEnable(boolean z) {
        f4551r = z;
        C1442k.m4416b().setDebugEnable(z);
    }

    public static void setEnableConcurrentProcess(boolean z) {
        f4530P = z;
    }

    public static void setEnableSmartReporting(boolean z) {
        f4543j = z;
    }

    public static void setEnableStatService(boolean z) {
        f4552s = z;
        if (!z) {
            f4549p.warn("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    public static void setFlushDBSpaceMS(long j) {
        if (j > 0) {
            f4547n = j;
        }
    }

    public static void setInstallChannel(Context context, String str) {
        if (str.length() > 128) {
            f4549p.error((Object) "the length of installChannel can not exceed the range of 128 bytes.");
            return;
        }
        f4517C = str;
        C1447p.m4460b(context, f4518D, str);
    }

    public static void setInstallChannel(String str) {
        if (str.length() > 128) {
            f4549p.error((Object) "the length of installChannel can not exceed the range of 128 bytes.");
        } else {
            f4517C = str;
        }
    }

    public static void setMaxBatchReportCount(int i) {
        if (m4229a(i, 2, 1000)) {
            f4558y = i;
        } else {
            f4549p.error((Object) "setMaxBatchReportCount can not exceed the range of [2,1000].");
        }
    }

    public static void setMaxDaySessionNumbers(int i) {
        if (i <= 0) {
            f4549p.m4374e((Object) "maxDaySessionNumbers must be greater than 0.");
        } else {
            f4526L = i;
        }
    }

    public static void setMaxImportantDataSendRetryCount(int i) {
        if (i > 100) {
            f4557x = i;
        }
    }

    public static void setMaxParallelTimmingEvents(int i) {
        if (m4229a(i, 1, 4096)) {
            f4520F = i;
        } else {
            f4549p.error((Object) "setMaxParallelTimmingEvents can not exceed the range of [1, 4096].");
        }
    }

    public static void setMaxReportEventLength(int i) {
        if (i <= 0) {
            f4549p.error((Object) "maxReportEventLength on setMaxReportEventLength() must greater than 0.");
        } else {
            f4529O = i;
        }
    }

    public static void setMaxSendRetryCount(int i) {
        if (m4229a(i, 1, 1000)) {
            f4556w = i;
        } else {
            f4549p.error((Object) "setMaxSendRetryCount can not exceed the range of [1,1000].");
        }
    }

    public static void setMaxSessionStatReportCount(int i) {
        if (i < 0) {
            f4549p.error((Object) "maxSessionStatReportCount cannot be less than 0.");
        } else {
            f4524J = i;
        }
    }

    public static void setMaxStoreEventCount(int i) {
        if (m4229a(i, 0, 500000)) {
            f4554u = i;
        } else {
            f4549p.error((Object) "setMaxStoreEventCount can not exceed the range of [0, 500000].");
        }
    }

    public static void setNumEventsCachedInMemory(int i) {
        if (i >= 0) {
            f4546m = i;
        }
    }

    public static void setNumEventsCommitPerSec(int i) {
        if (i > 0) {
            f4559z = i;
        }
    }

    public static void setNumOfMethodsCalledLimit(int i, long j) {
        f4541h = i;
        if (j >= 1000) {
            f4542i = j;
        }
    }

    public static void setQQ(Context context, String str) {
        C1447p.m4460b(context, "mta.acc.qq", str);
        f4539f = str;
    }

    public static void setReportCompressedSize(int i) {
        if (i > 0) {
            f4548o = i;
        }
    }

    public static void setReportEventsByOrder(boolean z) {
        f4545l = z;
    }

    public static void setSendPeriodMinutes(int i) {
        if (m4229a(i, 1, 10080)) {
            f4519E = i;
        } else {
            f4549p.error((Object) "setSendPeriodMinutes can not exceed the range of [1, 7*24*60] minutes.");
        }
    }

    public static void setSessionTimoutMillis(int i) {
        if (m4229a(i, 1000, 86400000)) {
            f4553t = i;
        } else {
            f4549p.error((Object) "setSessionTimoutMillis can not exceed the range of [1000, 24 * 60 * 60 * 1000].");
        }
    }

    public static void setStatReportUrl(String str) {
        if (str == null || str.length() == 0) {
            f4549p.error((Object) "statReportUrl cannot be null or empty.");
            return;
        }
        f4523I = str;
        try {
            f4544k = new URI(f4523I).getHost();
        } catch (Exception e) {
            f4549p.m4378w(e);
        }
        if (isDebugEnable()) {
            f4549p.m4376i("url:" + f4523I + ", domain:" + f4544k);
        }
    }

    public static void setStatSendStrategy(StatReportStrategy statReportStrategy) {
        f4550q = statReportStrategy;
        if (statReportStrategy != StatReportStrategy.PERIOD) {
            StatServiceImpl.f4567c = 0;
        }
        if (isDebugEnable()) {
            f4549p.m4373d("Change to statSendStrategy: " + statReportStrategy);
        }
    }

    public static void setXGProMode(boolean z) {
        f4532R = z;
    }
}
