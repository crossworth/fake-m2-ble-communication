package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.common.C1379a;
import com.tencent.stat.common.C1384f;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.C1394p;
import com.tencent.stat.common.StatConstants;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.p039a.C1365e;
import com.tencent.stat.p039a.C1366a;
import com.tencent.stat.p039a.C1367b;
import com.tencent.stat.p039a.C1368c;
import com.tencent.stat.p039a.C1369d;
import com.tencent.stat.p039a.C1371g;
import com.tencent.stat.p039a.C1372h;
import com.tencent.stat.p039a.C1374j;
import com.tencent.stat.p039a.C1375k;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class StatService {
    private static Handler f4328a;
    private static volatile Map<C1368c, Long> f4329b = new ConcurrentHashMap();
    private static volatile long f4330c = 0;
    private static volatile long f4331d = 0;
    private static volatile int f4332e = 0;
    private static volatile String f4333f = "";
    private static volatile String f4334g = "";
    private static Map<String, Long> f4335h = new ConcurrentHashMap();
    private static StatLogger f4336i = C1389k.m4125b();
    private static UncaughtExceptionHandler f4337j = null;
    private static volatile boolean f4338k = true;

    static int m4029a(Context context, boolean z) {
        int i = 1;
        long currentTimeMillis = System.currentTimeMillis();
        if (!z || currentTimeMillis - f4330c < ((long) StatConfig.getSessionTimoutMillis())) {
            boolean z2 = false;
        } else {
            int i2 = 1;
        }
        f4330c = currentTimeMillis;
        if (f4331d == 0) {
            f4331d = C1389k.m4128c();
        }
        if (currentTimeMillis >= f4331d) {
            f4331d = C1389k.m4128c();
            if (C1405n.m4189a(context).m4213b(context).getUserType() != 1) {
                C1405n.m4189a(context).m4213b(context).m3997b(1);
            }
            StatConfig.m4015b(0);
            StatMid.m4021a(context);
            i2 = 1;
        }
        if (!f4338k) {
            i = i2;
        }
        if (i != 0) {
            if (StatConfig.m4020e() < StatConfig.getMaxDaySessionNumbers()) {
                C1389k.m4114F(context);
                m4039d(context);
            } else {
                f4336i.m4085e((Object) "Exceed StatConfig.getMaxDaySessionNumbers().");
            }
        }
        if (f4338k) {
            C1384f.m4102b(context);
            testSpeed(context);
            m4040e(context);
            f4338k = false;
        }
        return f4332e;
    }

    static JSONObject m4030a() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (StatConfig.f4290b.f4390d != 0) {
                jSONObject2.put("v", StatConfig.f4290b.f4390d);
            }
            jSONObject.put(Integer.toString(StatConfig.f4290b.f4387a), jSONObject2);
            jSONObject2 = new JSONObject();
            if (StatConfig.f4289a.f4390d != 0) {
                jSONObject2.put("v", StatConfig.f4289a.f4390d);
            }
            jSONObject.put(Integer.toString(StatConfig.f4289a.f4387a), jSONObject2);
        } catch (Exception e) {
            f4336i.m4084e(e);
        }
        return jSONObject;
    }

    static synchronized void m4031a(Context context) {
        synchronized (StatService.class) {
            if (context != null) {
                if (f4328a == null && m4036b(context)) {
                    if (C1384f.m4101a(context)) {
                        HandlerThread handlerThread = new HandlerThread("StatService");
                        handlerThread.start();
                        f4328a = new Handler(handlerThread.getLooper());
                        C1405n.m4189a(context);
                        C1395d.m4174a(context);
                        C1395d.m4175b();
                        StatConfig.getDeviceInfo(context);
                        f4337j = Thread.getDefaultUncaughtExceptionHandler();
                        if (StatConfig.isAutoExceptionCaught()) {
                            Thread.setDefaultUncaughtExceptionHandler(new C1398g(context.getApplicationContext()));
                        } else {
                            f4336i.warn("MTA SDK AutoExceptionCaught is disable");
                        }
                        if (StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH && C1389k.m4141h(context)) {
                            C1405n.m4189a(context).m4208a(-1);
                        }
                        f4336i.m4083d("Init MTA StatService success.");
                    } else {
                        f4336i.m4085e((Object) "ooh, Compatibility problem was found in this device!");
                        f4336i.m4085e((Object) "If you are on debug mode, please delete apk and try again.");
                        StatConfig.setEnableStatService(false);
                    }
                }
            }
        }
    }

    static void m4032a(Context context, Throwable th) {
        try {
            if (!StatConfig.isEnableStatService()) {
                return;
            }
            if (context == null) {
                f4336i.error((Object) "The Context of StatService.reportSdkSelfException() can not be null!");
                return;
            }
            C1365e c1369d = new C1369d(context, m4029a(context, false), 99, th);
            if (m4037c(context) != null) {
                m4037c(context).post(new C1402k(c1369d));
            }
        } catch (Throwable th2) {
            f4336i.m4085e("reportSdkSelfException error: " + th2);
        }
    }

    static void m4033a(Context context, Map<String, ?> map) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.sendAdditionEvent() can not be null!");
            return;
        }
        try {
            C1365e c1366a = new C1366a(context, m4029a(context, false), map);
            if (m4037c(context) != null) {
                m4037c(context).post(new C1402k(c1366a));
            }
        } catch (Throwable th) {
            f4336i.m4085e((Object) th);
            m4032a(context, th);
        }
    }

    static boolean m4034a(String str) {
        return str == null || str.length() == 0;
    }

    static boolean m4036b(Context context) {
        if (C1389k.m4124b(StatConstants.VERSION) > C1394p.m4167a(context, StatConfig.f4291c, 0)) {
            return true;
        }
        StatConfig.setEnableStatService(false);
        return false;
    }

    static Handler m4037c(Context context) {
        if (f4328a == null) {
            m4031a(context);
        }
        return f4328a;
    }

    public static void commitEvents(Context context, int i) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.commitEvents() can not be null!");
        } else if (i < -1 || i == 0) {
            f4336i.error((Object) "The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
        } else {
            try {
                C1405n.m4189a(context).m4208a(i);
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    static void m4039d(Context context) {
        if (m4037c(context) != null) {
            f4336i.m4083d("start new session.");
            f4332e = C1389k.m4116a();
            StatConfig.m4006a(0);
            StatConfig.m4019d();
            m4037c(context).post(new C1402k(new C1375k(context, f4332e, m4030a())));
        }
    }

    static void m4040e(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.reportNativeCrash() can not be null!");
            return;
        }
        try {
            if (m4037c(context) != null) {
                m4037c(context).post(new C1400i(context));
            }
        } catch (Throwable th) {
            f4336i.m4085e((Object) th);
            m4032a(context, th);
        }
    }

    public static void onPause(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.onPause() can not be null!");
        } else {
            trackEndPage(context, C1389k.m4145k(context));
        }
    }

    public static void onResume(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.onResume() can not be null!");
        } else {
            trackBeginPage(context, C1389k.m4145k(context));
        }
    }

    public static void reportAppMonitorStat(Context context, StatAppMonitor statAppMonitor) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.reportAppMonitorStat() can not be null!");
        } else if (statAppMonitor == null) {
            f4336i.error((Object) "The StatAppMonitor of StatService.reportAppMonitorStat() can not be null!");
        } else if (statAppMonitor.getInterfaceName() == null) {
            f4336i.error((Object) "The interfaceName of StatAppMonitor on StatService.reportAppMonitorStat() can not be null!");
        } else {
            try {
                C1365e c1372h = new C1372h(context, m4029a(context, false), statAppMonitor);
                if (m4037c(context) != null) {
                    m4037c(context).post(new C1402k(c1372h));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void reportError(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.reportError() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "Error message in StatService.reportError() is empty.");
        } else {
            try {
                C1365e c1369d = new C1369d(context, m4029a(context, false), str, 0, StatConfig.getMaxReportEventLength());
                if (m4037c(context) != null) {
                    m4037c(context).post(new C1402k(c1369d));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void reportException(Context context, Throwable th) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.reportException() can not be null!");
        } else if (th == null) {
            f4336i.error((Object) "The Throwable error message of StatService.reportException() can not be null!");
        } else {
            C1365e c1369d = new C1369d(context, m4029a(context, false), 1, th);
            if (m4037c(context) != null) {
                m4037c(context).post(new C1402k(c1369d));
            }
        }
    }

    public static void reportGameUser(Context context, StatGameUser statGameUser) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.reportGameUser() can not be null!");
        } else if (statGameUser == null) {
            f4336i.error((Object) "The gameUser of StatService.reportGameUser() can not be null!");
        } else if (statGameUser.getAccount() == null || statGameUser.getAccount().length() == 0) {
            f4336i.error((Object) "The account of gameUser on StatService.reportGameUser() can not be null or empty!");
        } else {
            try {
                C1365e c1371g = new C1371g(context, m4029a(context, false), statGameUser);
                if (m4037c(context) != null) {
                    m4037c(context).post(new C1402k(c1371g));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void reportQQ(Context context, String str) {
        if (str == null) {
            str = "";
        }
        if (!StatConfig.f4292d.equals(str)) {
            StatConfig.f4292d = new String(str);
            m4033a(context, null);
        }
    }

    public static void setEnvAttributes(Context context, Map<String, String> map) {
        if (map == null || map.size() > 512) {
            f4336i.error((Object) "The map in setEnvAttributes can't be null or its size can't exceed 512.");
            return;
        }
        try {
            C1379a.m4090a(context, map);
        } catch (Exception e) {
            f4336i.m4084e(e);
        }
    }

    public static void startNewSession(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.startNewSession() can not be null!");
            return;
        }
        try {
            stopSession();
            m4029a(context, true);
        } catch (Throwable th) {
            f4336i.m4085e((Object) th);
            m4032a(context, th);
        }
    }

    public static boolean startStatService(Context context, String str, String str2) {
        if (StatConfig.isEnableStatService()) {
            String str3 = StatConstants.VERSION;
            f4336i.m4083d("MTA SDK version, current: " + str3 + " ,required: " + str2);
            String str4 = "";
            if (context == null || str2 == null) {
                str3 = "Context or mtaSdkVersion in StatService.startStatService() is null, please check it!";
                f4336i.error((Object) str3);
                StatConfig.setEnableStatService(false);
                throw new MtaSDkException(str3);
            } else if (C1389k.m4124b(str3) < C1389k.m4124b(str2)) {
                str3 = ("MTA SDK version conflicted, current: " + str3 + ",required: " + str2) + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/";
                f4336i.error((Object) str3);
                StatConfig.setEnableStatService(false);
                throw new MtaSDkException(str3);
            } else {
                try {
                    str3 = StatConfig.getInstallChannel(context);
                    if (str3 == null || str3.length() == 0) {
                        StatConfig.setInstallChannel("-");
                    }
                    if (str != null) {
                        StatConfig.setAppKey(context, str);
                    }
                    m4037c(context);
                    m4029a(context, false);
                    return true;
                } catch (Object th) {
                    f4336i.m4085e(th);
                    return false;
                }
            }
        }
        f4336i.error((Object) "MTA StatService is disable.");
        return false;
    }

    public static void stopSession() {
        f4330c = 0;
    }

    public static void testSpeed(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.testSpeed() can not be null!");
            return;
        }
        try {
            if (m4037c(context) != null) {
                m4037c(context).post(new C1401j(context, null));
            }
        } catch (Throwable th) {
            f4336i.m4085e((Object) th);
            m4032a(context, th);
        }
    }

    public static void testSpeed(Context context, Map<String, Integer> map) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.testSpeed() can not be null!");
        } else if (map == null || map.size() == 0) {
            f4336i.error((Object) "The domainMap of StatService.testSpeed() can not be null or empty!");
        } else {
            try {
                if (m4037c(context) != null) {
                    m4037c(context).post(new C1401j(context, map));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackBeginPage(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null || str == null || str.length() == 0) {
            f4336i.error((Object) "The Context or pageName of StatService.trackBeginPage() can not be null or empty!");
            return;
        }
        try {
            synchronized (f4335h) {
                if (f4335h.size() >= StatConfig.getMaxParallelTimmingEvents()) {
                    f4336i.error("The number of page events exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                    return;
                }
                f4333f = str;
                if (f4335h.containsKey(f4333f)) {
                    f4336i.m4085e("Duplicate PageID : " + f4333f + ", onResume() repeated?");
                    return;
                }
                f4335h.put(f4333f, Long.valueOf(System.currentTimeMillis()));
                m4029a(context, true);
            }
        } catch (Throwable th) {
            f4336i.m4085e((Object) th);
            m4032a(context, th);
        }
    }

    public static void trackCustomBeginEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
        } else {
            try {
                C1368c c1368c = new C1368c(str, strArr, null);
                if (f4329b.containsKey(c1368c)) {
                    f4336i.error("Duplicate CustomEvent key: " + c1368c.toString() + ", trackCustomBeginEvent() repeated?");
                } else if (f4329b.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                    f4329b.put(c1368c, Long.valueOf(System.currentTimeMillis()));
                } else {
                    f4336i.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackCustomBeginKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
        } else {
            try {
                C1368c c1368c = new C1368c(str, null, properties);
                if (f4329b.containsKey(c1368c)) {
                    f4336i.error("Duplicate CustomEvent key: " + c1368c.toString() + ", trackCustomBeginKVEvent() repeated?");
                } else if (f4329b.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                    f4329b.put(c1368c, Long.valueOf(System.currentTimeMillis()));
                } else {
                    f4336i.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackCustomEndEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
        } else {
            try {
                C1368c c1368c = new C1368c(str, strArr, null);
                Long l = (Long) f4329b.remove(c1368c);
                if (l != null) {
                    C1365e c1367b = new C1367b(context, m4029a(context, false), str);
                    c1367b.m4052a(strArr);
                    l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                    c1367b.m4050a(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                    if (m4037c(context) != null) {
                        m4037c(context).post(new C1402k(c1367b));
                        return;
                    }
                    return;
                }
                f4336i.error("No start time found for custom event: " + c1368c.toString() + ", lost trackCustomBeginEvent()?");
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackCustomEndKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
        } else {
            try {
                C1368c c1368c = new C1368c(str, null, properties);
                Long l = (Long) f4329b.remove(c1368c);
                if (l != null) {
                    C1365e c1367b = new C1367b(context, m4029a(context, false), str);
                    c1367b.m4051a(properties);
                    l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                    c1367b.m4050a(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                    if (m4037c(context) != null) {
                        m4037c(context).post(new C1402k(c1367b));
                        return;
                    }
                    return;
                }
                f4336i.error("No start time found for custom event: " + c1368c.toString() + ", lost trackCustomBeginKVEvent()?");
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackCustomEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
        } else {
            try {
                C1365e c1367b = new C1367b(context, m4029a(context, false), str);
                c1367b.m4052a(strArr);
                if (m4037c(context) != null) {
                    m4037c(context).post(new C1402k(c1367b));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackCustomKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f4336i.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
        } else if (m4034a(str)) {
            f4336i.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
        } else {
            try {
                C1365e c1367b = new C1367b(context, m4029a(context, false), str);
                c1367b.m4051a(properties);
                if (m4037c(context) != null) {
                    m4037c(context).post(new C1402k(c1367b));
                }
            } catch (Throwable th) {
                f4336i.m4085e((Object) th);
                m4032a(context, th);
            }
        }
    }

    public static void trackEndPage(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null || str == null || str.length() == 0) {
            f4336i.error((Object) "The Context or pageName of StatService.trackEndPage() can not be null or empty!");
            return;
        }
        try {
            Long l;
            synchronized (f4335h) {
                l = (Long) f4335h.remove(str);
            }
            if (l != null) {
                Long valueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                if (valueOf.longValue() <= 0) {
                    valueOf = Long.valueOf(1);
                }
                String str2 = f4334g;
                if (str2 != null && str2.equals(str)) {
                    str2 = "-";
                }
                if (m4037c(context) != null) {
                    C1365e c1374j = new C1374j(context, str2, str, m4029a(context, false), valueOf);
                    if (!str.equals(f4333f)) {
                        f4336i.warn("Invalid invocation since previous onResume on diff page.");
                    }
                    m4037c(context).post(new C1402k(c1374j));
                }
                f4334g = str;
                return;
            }
            f4336i.m4085e("Starttime for PageID:" + str + " not found, lost onResume()?");
        } catch (Throwable th) {
            f4336i.m4085e((Object) th);
            m4032a(context, th);
        }
    }
}
