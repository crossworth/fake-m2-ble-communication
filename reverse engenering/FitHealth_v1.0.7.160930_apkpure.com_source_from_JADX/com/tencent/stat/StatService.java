package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.common.C0829a;
import com.tencent.stat.common.C0834f;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.C0842p;
import com.tencent.stat.common.StatConstants;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.p021a.C0823c;
import com.tencent.stat.p021a.C0824e;
import com.tencent.stat.p021a.C1736a;
import com.tencent.stat.p021a.C1737b;
import com.tencent.stat.p021a.C1738d;
import com.tencent.stat.p021a.C1739g;
import com.tencent.stat.p021a.C1740h;
import com.tencent.stat.p021a.C1742j;
import com.tencent.stat.p021a.C1743k;
import com.umeng.socialize.common.SocializeConstants;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class StatService {
    private static Handler f2826a;
    private static volatile Map<C0823c, Long> f2827b = new ConcurrentHashMap();
    private static volatile long f2828c = 0;
    private static volatile long f2829d = 0;
    private static volatile int f2830e = 0;
    private static volatile String f2831f = "";
    private static volatile String f2832g = "";
    private static Map<String, Long> f2833h = new ConcurrentHashMap();
    private static StatLogger f2834i = C0837k.m2718b();
    private static UncaughtExceptionHandler f2835j = null;
    private static volatile boolean f2836k = true;

    static int m2645a(Context context, boolean z) {
        int i = 1;
        long currentTimeMillis = System.currentTimeMillis();
        if (!z || currentTimeMillis - f2828c < ((long) StatConfig.getSessionTimoutMillis())) {
            boolean z2 = false;
        } else {
            int i2 = 1;
        }
        f2828c = currentTimeMillis;
        if (f2829d == 0) {
            f2829d = C0837k.m2721c();
        }
        if (currentTimeMillis >= f2829d) {
            f2829d = C0837k.m2721c();
            if (C0850n.m2778a(context).m2802b(context).getUserType() != 1) {
                C0850n.m2778a(context).m2802b(context).m2613b(1);
            }
            StatConfig.m2631b(0);
            StatMid.m2637a(context);
            i2 = 1;
        }
        if (!f2836k) {
            i = i2;
        }
        if (i != 0) {
            if (StatConfig.m2636e() < StatConfig.getMaxDaySessionNumbers()) {
                C0837k.m2707F(context);
                m2655d(context);
            } else {
                f2834i.m2680e((Object) "Exceed StatConfig.getMaxDaySessionNumbers().");
            }
        }
        if (f2836k) {
            C0834f.m2697b(context);
            testSpeed(context);
            m2656e(context);
            f2836k = false;
        }
        return f2830e;
    }

    static JSONObject m2646a() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (StatConfig.f2788b.f2870d != 0) {
                jSONObject2.put("v", StatConfig.f2788b.f2870d);
            }
            jSONObject.put(Integer.toString(StatConfig.f2788b.f2867a), jSONObject2);
            jSONObject2 = new JSONObject();
            if (StatConfig.f2787a.f2870d != 0) {
                jSONObject2.put("v", StatConfig.f2787a.f2870d);
            }
            jSONObject.put(Integer.toString(StatConfig.f2787a.f2867a), jSONObject2);
        } catch (Exception e) {
            f2834i.m2679e(e);
        }
        return jSONObject;
    }

    static synchronized void m2647a(Context context) {
        synchronized (StatService.class) {
            if (context != null) {
                if (f2826a == null && m2652b(context)) {
                    if (C0834f.m2696a(context)) {
                        HandlerThread handlerThread = new HandlerThread("StatService");
                        handlerThread.start();
                        f2826a = new Handler(handlerThread.getLooper());
                        C0850n.m2778a(context);
                        C0843d.m2767a(context);
                        C0843d.m2768b();
                        StatConfig.getDeviceInfo(context);
                        f2835j = Thread.getDefaultUncaughtExceptionHandler();
                        if (StatConfig.isAutoExceptionCaught()) {
                            Thread.setDefaultUncaughtExceptionHandler(new C0845g(context.getApplicationContext()));
                        } else {
                            f2834i.warn("MTA SDK AutoExceptionCaught is disable");
                        }
                        if (StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH && C0837k.m2734h(context)) {
                            C0850n.m2778a(context).m2797a(-1);
                        }
                        f2834i.m2678d("Init MTA StatService success.");
                    } else {
                        f2834i.m2680e((Object) "ooh, Compatibility problem was found in this device!");
                        f2834i.m2680e((Object) "If you are on debug mode, please delete apk and try again.");
                        StatConfig.setEnableStatService(false);
                    }
                }
            }
        }
    }

    static void m2648a(Context context, Throwable th) {
        try {
            if (!StatConfig.isEnableStatService()) {
                return;
            }
            if (context == null) {
                f2834i.error((Object) "The Context of StatService.reportSdkSelfException() can not be null!");
                return;
            }
            C0824e c1738d = new C1738d(context, m2645a(context, false), 99, th);
            if (m2653c(context) != null) {
                m2653c(context).post(new C0849k(c1738d));
            }
        } catch (Throwable th2) {
            f2834i.m2680e("reportSdkSelfException error: " + th2);
        }
    }

    static void m2649a(Context context, Map<String, ?> map) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.sendAdditionEvent() can not be null!");
            return;
        }
        try {
            C0824e c1736a = new C1736a(context, m2645a(context, false), map);
            if (m2653c(context) != null) {
                m2653c(context).post(new C0849k(c1736a));
            }
        } catch (Throwable th) {
            f2834i.m2680e((Object) th);
            m2648a(context, th);
        }
    }

    static boolean m2650a(String str) {
        return str == null || str.length() == 0;
    }

    static boolean m2652b(Context context) {
        if (C0837k.m2717b(StatConstants.VERSION) > C0842p.m2760a(context, StatConfig.f2789c, 0)) {
            return true;
        }
        StatConfig.setEnableStatService(false);
        return false;
    }

    static Handler m2653c(Context context) {
        if (f2826a == null) {
            m2647a(context);
        }
        return f2826a;
    }

    public static void commitEvents(Context context, int i) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.commitEvents() can not be null!");
        } else if (i < -1 || i == 0) {
            f2834i.error((Object) "The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
        } else {
            try {
                C0850n.m2778a(context).m2797a(i);
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    static void m2655d(Context context) {
        if (m2653c(context) != null) {
            f2834i.m2678d("start new session.");
            f2830e = C0837k.m2709a();
            StatConfig.m2622a(0);
            StatConfig.m2635d();
            m2653c(context).post(new C0849k(new C1743k(context, f2830e, m2646a())));
        }
    }

    static void m2656e(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.reportNativeCrash() can not be null!");
            return;
        }
        try {
            if (m2653c(context) != null) {
                m2653c(context).post(new C0847i(context));
            }
        } catch (Throwable th) {
            f2834i.m2680e((Object) th);
            m2648a(context, th);
        }
    }

    public static void onPause(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.onPause() can not be null!");
        } else {
            trackEndPage(context, C0837k.m2738k(context));
        }
    }

    public static void onResume(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.onResume() can not be null!");
        } else {
            trackBeginPage(context, C0837k.m2738k(context));
        }
    }

    public static void reportAppMonitorStat(Context context, StatAppMonitor statAppMonitor) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.reportAppMonitorStat() can not be null!");
        } else if (statAppMonitor == null) {
            f2834i.error((Object) "The StatAppMonitor of StatService.reportAppMonitorStat() can not be null!");
        } else if (statAppMonitor.getInterfaceName() == null) {
            f2834i.error((Object) "The interfaceName of StatAppMonitor on StatService.reportAppMonitorStat() can not be null!");
        } else {
            try {
                C0824e c1740h = new C1740h(context, m2645a(context, false), statAppMonitor);
                if (m2653c(context) != null) {
                    m2653c(context).post(new C0849k(c1740h));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void reportError(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.reportError() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "Error message in StatService.reportError() is empty.");
        } else {
            try {
                C0824e c1738d = new C1738d(context, m2645a(context, false), str, 0, StatConfig.getMaxReportEventLength());
                if (m2653c(context) != null) {
                    m2653c(context).post(new C0849k(c1738d));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void reportException(Context context, Throwable th) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.reportException() can not be null!");
        } else if (th == null) {
            f2834i.error((Object) "The Throwable error message of StatService.reportException() can not be null!");
        } else {
            C0824e c1738d = new C1738d(context, m2645a(context, false), 1, th);
            if (m2653c(context) != null) {
                m2653c(context).post(new C0849k(c1738d));
            }
        }
    }

    public static void reportGameUser(Context context, StatGameUser statGameUser) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.reportGameUser() can not be null!");
        } else if (statGameUser == null) {
            f2834i.error((Object) "The gameUser of StatService.reportGameUser() can not be null!");
        } else if (statGameUser.getAccount() == null || statGameUser.getAccount().length() == 0) {
            f2834i.error((Object) "The account of gameUser on StatService.reportGameUser() can not be null or empty!");
        } else {
            try {
                C0824e c1739g = new C1739g(context, m2645a(context, false), statGameUser);
                if (m2653c(context) != null) {
                    m2653c(context).post(new C0849k(c1739g));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void reportQQ(Context context, String str) {
        if (str == null) {
            str = "";
        }
        if (!StatConfig.f2790d.equals(str)) {
            StatConfig.f2790d = new String(str);
            m2649a(context, null);
        }
    }

    public static void setEnvAttributes(Context context, Map<String, String> map) {
        if (map == null || map.size() > 512) {
            f2834i.error((Object) "The map in setEnvAttributes can't be null or its size can't exceed 512.");
            return;
        }
        try {
            C0829a.m2685a(context, map);
        } catch (Exception e) {
            f2834i.m2679e(e);
        }
    }

    public static void startNewSession(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.startNewSession() can not be null!");
            return;
        }
        try {
            stopSession();
            m2645a(context, true);
        } catch (Throwable th) {
            f2834i.m2680e((Object) th);
            m2648a(context, th);
        }
    }

    public static boolean startStatService(Context context, String str, String str2) {
        if (StatConfig.isEnableStatService()) {
            String str3 = StatConstants.VERSION;
            f2834i.m2678d("MTA SDK version, current: " + str3 + " ,required: " + str2);
            String str4 = "";
            if (context == null || str2 == null) {
                str3 = "Context or mtaSdkVersion in StatService.startStatService() is null, please check it!";
                f2834i.error((Object) str3);
                StatConfig.setEnableStatService(false);
                throw new MtaSDkException(str3);
            } else if (C0837k.m2717b(str3) < C0837k.m2717b(str2)) {
                str3 = ("MTA SDK version conflicted, current: " + str3 + ",required: " + str2) + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/";
                f2834i.error((Object) str3);
                StatConfig.setEnableStatService(false);
                throw new MtaSDkException(str3);
            } else {
                try {
                    str3 = StatConfig.getInstallChannel(context);
                    if (str3 == null || str3.length() == 0) {
                        StatConfig.setInstallChannel(SocializeConstants.OP_DIVIDER_MINUS);
                    }
                    if (str != null) {
                        StatConfig.setAppKey(context, str);
                    }
                    m2653c(context);
                    m2645a(context, false);
                    return true;
                } catch (Object th) {
                    f2834i.m2680e(th);
                    return false;
                }
            }
        }
        f2834i.error((Object) "MTA StatService is disable.");
        return false;
    }

    public static void stopSession() {
        f2828c = 0;
    }

    public static void testSpeed(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.testSpeed() can not be null!");
            return;
        }
        try {
            if (m2653c(context) != null) {
                m2653c(context).post(new C0848j(context, null));
            }
        } catch (Throwable th) {
            f2834i.m2680e((Object) th);
            m2648a(context, th);
        }
    }

    public static void testSpeed(Context context, Map<String, Integer> map) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.testSpeed() can not be null!");
        } else if (map == null || map.size() == 0) {
            f2834i.error((Object) "The domainMap of StatService.testSpeed() can not be null or empty!");
        } else {
            try {
                if (m2653c(context) != null) {
                    m2653c(context).post(new C0848j(context, map));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackBeginPage(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null || str == null || str.length() == 0) {
            f2834i.error((Object) "The Context or pageName of StatService.trackBeginPage() can not be null or empty!");
            return;
        }
        try {
            synchronized (f2833h) {
                if (f2833h.size() >= StatConfig.getMaxParallelTimmingEvents()) {
                    f2834i.error("The number of page events exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                    return;
                }
                f2831f = str;
                if (f2833h.containsKey(f2831f)) {
                    f2834i.m2680e("Duplicate PageID : " + f2831f + ", onResume() repeated?");
                    return;
                }
                f2833h.put(f2831f, Long.valueOf(System.currentTimeMillis()));
                m2645a(context, true);
            }
        } catch (Throwable th) {
            f2834i.m2680e((Object) th);
            m2648a(context, th);
        }
    }

    public static void trackCustomBeginEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
        } else {
            try {
                C0823c c0823c = new C0823c(str, strArr, null);
                if (f2827b.containsKey(c0823c)) {
                    f2834i.error("Duplicate CustomEvent key: " + c0823c.toString() + ", trackCustomBeginEvent() repeated?");
                } else if (f2827b.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                    f2827b.put(c0823c, Long.valueOf(System.currentTimeMillis()));
                } else {
                    f2834i.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackCustomBeginKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
        } else {
            try {
                C0823c c0823c = new C0823c(str, null, properties);
                if (f2827b.containsKey(c0823c)) {
                    f2834i.error("Duplicate CustomEvent key: " + c0823c.toString() + ", trackCustomBeginKVEvent() repeated?");
                } else if (f2827b.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                    f2827b.put(c0823c, Long.valueOf(System.currentTimeMillis()));
                } else {
                    f2834i.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackCustomEndEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
        } else {
            try {
                C0823c c0823c = new C0823c(str, strArr, null);
                Long l = (Long) f2827b.remove(c0823c);
                if (l != null) {
                    C0824e c1737b = new C1737b(context, m2645a(context, false), str);
                    c1737b.m4861a(strArr);
                    l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                    c1737b.m4859a(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                    if (m2653c(context) != null) {
                        m2653c(context).post(new C0849k(c1737b));
                        return;
                    }
                    return;
                }
                f2834i.error("No start time found for custom event: " + c0823c.toString() + ", lost trackCustomBeginEvent()?");
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackCustomEndKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
        } else {
            try {
                C0823c c0823c = new C0823c(str, null, properties);
                Long l = (Long) f2827b.remove(c0823c);
                if (l != null) {
                    C0824e c1737b = new C1737b(context, m2645a(context, false), str);
                    c1737b.m4860a(properties);
                    l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                    c1737b.m4859a(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                    if (m2653c(context) != null) {
                        m2653c(context).post(new C0849k(c1737b));
                        return;
                    }
                    return;
                }
                f2834i.error("No start time found for custom event: " + c0823c.toString() + ", lost trackCustomBeginKVEvent()?");
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackCustomEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
        } else {
            try {
                C0824e c1737b = new C1737b(context, m2645a(context, false), str);
                c1737b.m4861a(strArr);
                if (m2653c(context) != null) {
                    m2653c(context).post(new C0849k(c1737b));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackCustomKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            f2834i.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
        } else if (m2650a(str)) {
            f2834i.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
        } else {
            try {
                C0824e c1737b = new C1737b(context, m2645a(context, false), str);
                c1737b.m4860a(properties);
                if (m2653c(context) != null) {
                    m2653c(context).post(new C0849k(c1737b));
                }
            } catch (Throwable th) {
                f2834i.m2680e((Object) th);
                m2648a(context, th);
            }
        }
    }

    public static void trackEndPage(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null || str == null || str.length() == 0) {
            f2834i.error((Object) "The Context or pageName of StatService.trackEndPage() can not be null or empty!");
            return;
        }
        try {
            Long l;
            synchronized (f2833h) {
                l = (Long) f2833h.remove(str);
            }
            if (l != null) {
                Long valueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                if (valueOf.longValue() <= 0) {
                    valueOf = Long.valueOf(1);
                }
                String str2 = f2832g;
                if (str2 != null && str2.equals(str)) {
                    str2 = SocializeConstants.OP_DIVIDER_MINUS;
                }
                if (m2653c(context) != null) {
                    C0824e c1742j = new C1742j(context, str2, str, m2645a(context, false), valueOf);
                    if (!str.equals(f2831f)) {
                        f2834i.warn("Invalid invocation since previous onResume on diff page.");
                    }
                    m2653c(context).post(new C0849k(c1742j));
                }
                f2832g = str;
                return;
            }
            f2834i.m2680e("Starttime for PageID:" + str + " not found, lost onResume()?");
        } catch (Throwable th) {
            f2834i.m2680e((Object) th);
            m2648a(context, th);
        }
    }
}
