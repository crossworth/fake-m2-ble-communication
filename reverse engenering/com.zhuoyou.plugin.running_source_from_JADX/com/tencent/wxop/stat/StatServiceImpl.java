package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1433b;
import com.tencent.wxop.stat.common.C1436e;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1447p;
import com.tencent.wxop.stat.common.StatConstants;
import com.tencent.wxop.stat.common.StatLogger;
import com.tencent.wxop.stat.p040a.C1417a;
import com.tencent.wxop.stat.p040a.C1419c;
import com.tencent.wxop.stat.p040a.C1424i;
import com.tencent.wxop.stat.p040a.C1427l;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class StatServiceImpl {
    static volatile int f4565a = 0;
    static volatile long f4566b = 0;
    static volatile long f4567c = 0;
    private static C1436e f4568d;
    private static volatile Map<C1419c, Long> f4569e = new ConcurrentHashMap();
    private static volatile Map<String, Properties> f4570f = new ConcurrentHashMap();
    private static volatile Map<Integer, Integer> f4571g = new ConcurrentHashMap(10);
    private static volatile long f4572h = 0;
    private static volatile long f4573i = 0;
    private static volatile long f4574j = 0;
    private static String f4575k = "";
    private static volatile int f4576l = 0;
    private static volatile String f4577m = "";
    private static volatile String f4578n = "";
    private static Map<String, Long> f4579o = new ConcurrentHashMap();
    private static Map<String, Long> f4580p = new ConcurrentHashMap();
    private static StatLogger f4581q = C1442k.m4416b();
    private static UncaughtExceptionHandler f4582r = null;
    private static volatile boolean f4583s = true;
    private static Context f4584t = null;

    static int m4238a(Context context, boolean z, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        int i = 1;
        long currentTimeMillis = System.currentTimeMillis();
        if (!z || currentTimeMillis - f4573i < ((long) StatConfig.getSessionTimoutMillis())) {
            boolean z2 = false;
        } else {
            int i2 = 1;
        }
        f4573i = currentTimeMillis;
        if (f4574j == 0) {
            f4574j = C1442k.m4418c();
        }
        if (currentTimeMillis >= f4574j) {
            f4574j = C1442k.m4418c();
            if (au.m4332a(context).m4365b(context).m4383d() != 1) {
                au.m4332a(context).m4365b(context).m4380a(1);
            }
            StatConfig.m4233b(0);
            f4565a = 0;
            f4575k = C1442k.m4407a(0);
            i2 = 1;
        }
        Object obj = f4575k;
        if (C1442k.m4413a(statSpecifyReportedInfo)) {
            obj = statSpecifyReportedInfo.getAppKey() + f4575k;
        }
        if (f4580p.containsKey(obj)) {
            i = i2;
        }
        if (i != 0) {
            if (C1442k.m4413a(statSpecifyReportedInfo)) {
                m4243a(context, statSpecifyReportedInfo);
            } else if (StatConfig.m4236c() < StatConfig.getMaxDaySessionNumbers()) {
                C1442k.m4446x(context);
                m4243a(context, null);
            } else {
                f4581q.m4374e((Object) "Exceed StatConfig.getMaxDaySessionNumbers().");
            }
            f4580p.put(obj, Long.valueOf(1));
        }
        if (f4583s) {
            testSpeed(context);
            f4583s = false;
        }
        return f4576l;
    }

    static synchronized void m4241a(Context context) {
        synchronized (StatServiceImpl.class) {
            if (context != null) {
                if (f4568d == null && m4250b(context)) {
                    Context applicationContext = context.getApplicationContext();
                    f4584t = applicationContext;
                    f4568d = new C1436e();
                    f4575k = C1442k.m4407a(0);
                    f4572h = System.currentTimeMillis() + StatConfig.f4542i;
                    f4568d.m4388a(new C1457l(applicationContext));
                }
            }
        }
    }

    static void m4243a(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (m4251c(context) != null) {
            if (StatConfig.isDebugEnable()) {
                f4581q.m4373d("start new session.");
            }
            if (statSpecifyReportedInfo == null || f4576l == 0) {
                f4576l = C1442k.m4404a();
            }
            StatConfig.m4222a(0);
            StatConfig.m4232b();
            new aq(new C1427l(context, f4576l, m4248b(), statSpecifyReportedInfo)).m4323a();
        }
    }

    static void m4244a(Context context, Throwable th) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.reportSdkSelfException() can not be null!");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new C1462q(context2, th));
            }
        }
    }

    static boolean m4245a() {
        if (f4565a < 2) {
            return false;
        }
        f4566b = System.currentTimeMillis();
        return true;
    }

    static boolean m4246a(String str) {
        return str == null || str.length() == 0;
    }

    static JSONObject m4248b() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (StatConfig.f4535b.f4820d != 0) {
                jSONObject2.put("v", StatConfig.f4535b.f4820d);
            }
            jSONObject.put(Integer.toString(StatConfig.f4535b.f4817a), jSONObject2);
            jSONObject2 = new JSONObject();
            if (StatConfig.f4534a.f4820d != 0) {
                jSONObject2.put("v", StatConfig.f4534a.f4820d);
            }
            jSONObject.put(Integer.toString(StatConfig.f4534a.f4817a), jSONObject2);
        } catch (Throwable e) {
            f4581q.m4375e(e);
        }
        return jSONObject;
    }

    private static void m4249b(Context context, StatAccount statAccount, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        try {
            new aq(new C1417a(context, m4238a(context, false, statSpecifyReportedInfo), statAccount, statSpecifyReportedInfo)).m4323a();
        } catch (Throwable th) {
            f4581q.m4375e(th);
            m4244a(context, th);
        }
    }

    static boolean m4250b(Context context) {
        boolean z = false;
        long a = C1447p.m4455a(context, StatConfig.f4536c, 0);
        long b = C1442k.m4415b(StatConstants.VERSION);
        boolean z2 = true;
        if (b <= a) {
            f4581q.error("MTA is disable for current version:" + b + ",wakeup version:" + a);
            z2 = false;
        }
        a = C1447p.m4455a(context, StatConfig.f4537d, 0);
        if (a > System.currentTimeMillis()) {
            f4581q.error("MTA is disable for current time:" + System.currentTimeMillis() + ",wakeup time:" + a);
        } else {
            z = z2;
        }
        StatConfig.setEnableStatService(z);
        return z;
    }

    static C1436e m4251c(Context context) {
        if (f4568d == null) {
            synchronized (StatServiceImpl.class) {
                if (f4568d == null) {
                    try {
                        m4241a(context);
                    } catch (Throwable th) {
                        f4581q.error(th);
                        StatConfig.setEnableStatService(false);
                    }
                }
            }
        }
        return f4568d;
    }

    static void m4253c() {
        f4565a = 0;
        f4566b = 0;
    }

    public static void commitEvents(Context context, int i) {
        if (StatConfig.isEnableStatService()) {
            if (StatConfig.isDebugEnable()) {
                f4581q.m4376i("commitEvents, maxNumber=" + i);
            }
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.commitEvents() can not be null!");
            } else if (i < -1 || i == 0) {
                f4581q.error((Object) "The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
            } else if (C1428a.m4298a(f4584t).m4311f() && m4251c(context2) != null) {
                f4568d.m4388a(new ad(context2, i));
            }
        }
    }

    static void m4254d() {
        f4565a++;
        f4566b = System.currentTimeMillis();
        flushDataToDB(f4584t);
    }

    static void m4255d(Context context) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.sendNetworkDetector() can not be null!");
                return;
            }
            try {
                C1454i.m4486b(context2).m4487a(new C1424i(context2), new C1465t());
            } catch (Throwable th) {
                f4581q.m4375e(th);
            }
        }
    }

    static void m4257e(Context context) {
        f4567c = System.currentTimeMillis() + ((long) (60000 * StatConfig.getSendPeriodMinutes()));
        C1447p.m4459b(context, "last_period_ts", f4567c);
        commitEvents(context, -1);
    }

    public static void flushDataToDB(Context context) {
        if (StatConfig.isEnableStatService() && StatConfig.f4546m > 0) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.testSpeed() can not be null!");
            } else {
                au.m4332a(context2).m4366c();
            }
        }
    }

    public static Properties getCommonKeyValueForKVEvent(String str) {
        return (Properties) f4570f.get(str);
    }

    public static Context getContext(Context context) {
        return context != null ? context : f4584t;
    }

    public static void onLowMemory(Context context) {
        if (StatConfig.isEnableStatService() && m4251c(getContext(context)) != null) {
            f4568d.m4388a(new C1460o(context));
        }
    }

    public static void onPause(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService() && m4251c(context) != null) {
            f4568d.m4388a(new C1458m(context, statSpecifyReportedInfo));
        }
    }

    public static void onResume(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService() && m4251c(context) != null) {
            f4568d.m4388a(new aj(context, statSpecifyReportedInfo));
        }
    }

    public static void onStop(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new C1459n(context2));
            }
        }
    }

    public static void reportAccount(Context context, StatAccount statAccount, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.m4374e((Object) "context is null in reportAccount.");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new al(statAccount, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void reportAppMonitorStat(Context context, StatAppMonitor statAppMonitor, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.reportAppMonitorStat() can not be null!");
            } else if (statAppMonitor == null) {
                f4581q.error((Object) "The StatAppMonitor of StatService.reportAppMonitorStat() can not be null!");
            } else if (statAppMonitor.getInterfaceName() == null) {
                f4581q.error((Object) "The interfaceName of StatAppMonitor on StatService.reportAppMonitorStat() can not be null!");
            } else {
                StatAppMonitor clone = statAppMonitor.clone();
                if (m4251c(context2) != null) {
                    f4568d.m4388a(new aa(context2, statSpecifyReportedInfo, clone));
                }
            }
        }
    }

    public static void reportError(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.reportError() can not be null!");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new C1461p(str, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void reportException(Context context, Throwable th, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.reportException() can not be null!");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new C1463r(th, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void reportGameUser(Context context, StatGameUser statGameUser, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.reportGameUser() can not be null!");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new am(statGameUser, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void reportQQ(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "context is null in reportQQ()");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new ak(str, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void setCommonKeyValueForKVEvent(String str, Properties properties) {
        if (!C1442k.m4420c(str)) {
            f4581q.m4374e((Object) "event_id or commonProp for setCommonKeyValueForKVEvent is invalid.");
        } else if (properties == null || properties.size() <= 0) {
            f4570f.remove(str);
        } else {
            f4570f.put(str, (Properties) properties.clone());
        }
    }

    public static void setContext(Context context) {
        if (context != null) {
            f4584t = context.getApplicationContext();
        }
    }

    public static void setEnvAttributes(Context context, Map<String, String> map) {
        if (map == null || map.size() > 512) {
            f4581q.error((Object) "The map in setEnvAttributes can't be null or its size can't exceed 512.");
            return;
        }
        try {
            C1433b.m4385a(context, (Map) map);
        } catch (Throwable e) {
            f4581q.m4375e(e);
        }
    }

    public static void startNewSession(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.startNewSession() can not be null!");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new ai(context2, statSpecifyReportedInfo));
            }
        }
    }

    public static boolean startStatService(Context context, String str, String str2, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        try {
            if (StatConfig.isEnableStatService()) {
                String str3 = StatConstants.VERSION;
                if (StatConfig.isDebugEnable()) {
                    f4581q.m4373d("MTA SDK version, current: " + str3 + " ,required: " + str2);
                }
                if (context == null || str2 == null) {
                    f4581q.error((Object) "Context or mtaSdkVersion in StatService.startStatService() is null, please check it!");
                    StatConfig.setEnableStatService(false);
                    return false;
                } else if (C1442k.m4415b(str3) < C1442k.m4415b(str2)) {
                    f4581q.error(("MTA SDK version conflicted, current: " + str3 + ",required: " + str2) + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/");
                    StatConfig.setEnableStatService(false);
                    return false;
                } else {
                    str3 = StatConfig.getInstallChannel(context);
                    if (str3 == null || str3.length() == 0) {
                        StatConfig.setInstallChannel("-");
                    }
                    if (str != null) {
                        StatConfig.setAppKey(context, str);
                    }
                    if (m4251c(context) != null) {
                        f4568d.m4388a(new an(context, statSpecifyReportedInfo));
                    }
                    return true;
                }
            }
            f4581q.error((Object) "MTA StatService is disable.");
            return false;
        } catch (Throwable th) {
            f4581q.m4375e(th);
            return false;
        }
    }

    public static void stopSession() {
        f4573i = 0;
    }

    public static void testSpeed(Context context) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.testSpeed() can not be null!");
            } else if (m4251c(context2) != null) {
                f4568d.m4388a(new ae(context2));
            }
        }
    }

    public static void testSpeed(Context context, Map<String, Integer> map, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.testSpeed() can not be null!");
            } else if (map == null || map.size() == 0) {
                f4581q.error((Object) "The domainMap of StatService.testSpeed() can not be null or empty!");
            } else {
                Map hashMap = new HashMap(map);
                if (m4251c(context2) != null) {
                    f4568d.m4388a(new af(context2, hashMap, statSpecifyReportedInfo));
                }
            }
        }
    }

    public static void trackBeginPage(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null || str == null || str.length() == 0) {
                f4581q.error((Object) "The Context or pageName of StatService.trackBeginPage() can not be null or empty!");
                return;
            }
            String str2 = new String(str);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new C1468w(str2, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void trackCustomBeginEvent(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo, String... strArr) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
                return;
            }
            C1419c c1419c = new C1419c(str, strArr, null);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new C1467v(str, c1419c, context2));
            }
        }
    }

    public static void trackCustomBeginKVEvent(Context context, String str, Properties properties, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
                return;
            }
            C1419c c1419c = new C1419c(str, null, properties);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new C1470y(str, c1419c, context2));
            }
        }
    }

    public static void trackCustomEndEvent(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo, String... strArr) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
                return;
            }
            C1419c c1419c = new C1419c(str, strArr, null);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new C1469x(str, c1419c, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void trackCustomEndKVEvent(Context context, String str, Properties properties, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
                return;
            }
            C1419c c1419c = new C1419c(str, null, properties);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new C1471z(str, c1419c, context2, statSpecifyReportedInfo));
            }
        }
    }

    public static void trackCustomEvent(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo, String... strArr) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
            } else if (m4246a(str)) {
                f4581q.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
            } else {
                C1419c c1419c = new C1419c(str, strArr, null);
                if (m4251c(context2) != null) {
                    f4568d.m4388a(new C1464s(context2, statSpecifyReportedInfo, c1419c));
                }
            }
        }
    }

    public static void trackCustomKVEvent(Context context, String str, Properties properties, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
            } else if (m4246a(str)) {
                f4581q.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
            } else {
                C1419c c1419c = new C1419c(str, null, properties);
                if (m4251c(context2) != null) {
                    f4568d.m4388a(new C1466u(context2, statSpecifyReportedInfo, c1419c));
                }
            }
        }
    }

    public static void trackCustomKVTimeIntervalEvent(Context context, String str, Properties properties, int i, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null) {
                f4581q.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
            } else if (m4246a(str)) {
                f4581q.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
            } else {
                C1419c c1419c = new C1419c(str, null, properties);
                if (m4251c(context2) != null) {
                    f4568d.m4388a(new ac(context2, statSpecifyReportedInfo, c1419c, i));
                }
            }
        }
    }

    public static void trackCustomTimeIntervalEvent(Context context, int i, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (i <= 0) {
            f4581q.error((Object) "The intervalSecond of StatService.trackCustomTimeIntervalEvent() can must bigger than 0!");
            return;
        }
        Context context2 = getContext(context);
        if (context2 == null) {
            f4581q.error((Object) "The Context of StatService.trackCustomTimeIntervalEvent() can not be null!");
        } else if (m4251c(context2) != null) {
            f4568d.m4388a(new ab());
        }
    }

    public static void trackEndPage(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (StatConfig.isEnableStatService()) {
            Context context2 = getContext(context);
            if (context2 == null || str == null || str.length() == 0) {
                f4581q.error((Object) "The Context or pageName of StatService.trackEndPage() can not be null or empty!");
                return;
            }
            String str2 = new String(str);
            if (m4251c(context2) != null) {
                f4568d.m4388a(new ah(context2, str2, statSpecifyReportedInfo));
            }
        }
    }
}
