package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p022a.C0872b;
import com.tencent.wxop.stat.p022a.C1757f;
import com.tencent.wxop.stat.p022a.C1760i;
import com.tencent.wxop.stat.p023b.C0877b;
import com.tencent.wxop.stat.p023b.C0881f;
import com.tencent.wxop.stat.p023b.C0885l;
import com.tencent.wxop.stat.p023b.C0890q;
import com.umeng.socialize.common.SocializeConstants;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class C0896e {
    private static volatile boolean f3063S = true;
    static volatile int aI = 0;
    private static C0881f aK;
    private static volatile Map<C0872b, Long> aL = new ConcurrentHashMap();
    private static volatile Map<String, Properties> aM = new ConcurrentHashMap();
    private static volatile Map<Integer, Integer> aN = new ConcurrentHashMap(10);
    private static volatile long aO = 0;
    private static volatile long aP = 0;
    private static volatile int aQ = 0;
    private static volatile String aR = "";
    private static volatile String aS = "";
    private static Map<String, Long> aT = new ConcurrentHashMap();
    private static Map<String, Long> aU = new ConcurrentHashMap();
    private static C0877b aV = C0885l.av();
    private static UncaughtExceptionHandler aW = null;
    static volatile long aX = 0;
    private static Context aY = null;
    static volatile long aZ = 0;
    private static volatile long af = 0;
    private static String al = "";

    private static JSONObject m2970G() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (C0894c.f3047P.f3013L != 0) {
                jSONObject2.put("v", C0894c.f3047P.f3013L);
            }
            jSONObject.put(Integer.toString(C0894c.f3047P.aI), jSONObject2);
            jSONObject2 = new JSONObject();
            if (C0894c.f3046O.f3013L != 0) {
                jSONObject2.put("v", C0894c.f3046O.f3013L);
            }
            jSONObject.put(Integer.toString(C0894c.f3046O.aI), jSONObject2);
        } catch (Throwable e) {
            aV.m2852b(e);
        }
        return jSONObject;
    }

    static void m2971H() {
        aI = 0;
        aX = 0;
    }

    static void m2972I() {
        aI++;
        aX = System.currentTimeMillis();
        C0896e.m2999p(aY);
    }

    static int m2981a(Context context, boolean z, C0897f c0897f) {
        boolean z2 = true;
        long currentTimeMillis = System.currentTimeMillis();
        boolean z3 = z && currentTimeMillis - af >= ((long) C0894c.m2952m());
        af = currentTimeMillis;
        if (aP == 0) {
            aP = C0885l.ad();
        }
        if (currentTimeMillis >= aP) {
            aP = C0885l.ad();
            if (C0908t.m3043s(context).m3051t(context).as() != 1) {
                C0908t.m3043s(context).m3051t(context).m2857z();
            }
            C0894c.m2929C();
            aI = 0;
            al = C0885l.aw();
            z3 = true;
        }
        Object obj = al;
        if (C0885l.m2889a(c0897f)) {
            obj = c0897f.m3004S() + al;
        }
        if (aU.containsKey(obj)) {
            z2 = z3;
        }
        if (z2) {
            if (C0885l.m2889a(c0897f)) {
                C0896e.m2983a(context, c0897f);
            } else if (C0894c.m2930D() < C0894c.m2927A()) {
                C0885l.m2882O(context);
                C0896e.m2983a(context, null);
            } else {
                aV.m2854d("Exceed StatConfig.getMaxDaySessionNumbers().");
            }
            aU.put(obj, Long.valueOf(1));
        }
        if (f3063S) {
            if (C0894c.m2951l()) {
                Context i = C0896e.m2991i(context);
                if (i == null) {
                    aV.error("The Context of StatService.testSpeed() can not be null!");
                } else if (C0896e.m2993k(i) != null) {
                    aK.m2861a(new C0900i(i));
                }
            }
            f3063S = false;
        }
        return aQ;
    }

    private static void m2983a(Context context, C0897f c0897f) {
        if (C0896e.m2993k(context) != null) {
            if (C0894c.m2949k()) {
                aV.m2855e("start new session.");
            }
            if (c0897f == null || aQ == 0) {
                aQ = C0885l.m2895r();
            }
            C0894c.m2968z();
            C0894c.m2928B();
            new C0907p(new C1760i(context, aQ, C0896e.m2970G(), c0897f)).ah();
        }
    }

    public static void m2984a(Context context, String str, C0897f c0897f) {
        if (C0894c.m2951l()) {
            Context i = C0896e.m2991i(context);
            if (i == null || str == null || str.length() == 0) {
                aV.error("The Context or pageName of StatService.trackBeginPage() can not be null or empty!");
                return;
            }
            String str2 = new String(str);
            if (C0896e.m2993k(i) != null) {
                aK.m2861a(new as(str2, i, c0897f));
            }
        }
    }

    static void m2985a(Context context, Throwable th) {
        if (C0894c.m2951l()) {
            Context i = C0896e.m2991i(context);
            if (i == null) {
                aV.error("The Context of StatService.reportSdkSelfException() can not be null!");
            } else if (C0896e.m2993k(i) != null) {
                aK.m2861a(new ap(i, th));
            }
        }
    }

    static boolean m2986a() {
        if (aI < 2) {
            return false;
        }
        aX = System.currentTimeMillis();
        return true;
    }

    public static boolean m2987a(Context context, String str, String str2) {
        try {
            if (C0894c.m2951l()) {
                String str3 = "2.0.3";
                if (C0894c.m2949k()) {
                    aV.m2855e("MTA SDK version, current: " + str3 + " ,required: " + str2);
                }
                if (context == null || str2 == null) {
                    aV.error("Context or mtaSdkVersion in StatService.startStatService() is null, please check it!");
                    C0894c.m2938a(false);
                    return false;
                } else if (C0885l.m2897u(str3) < C0885l.m2897u(str2)) {
                    aV.error(("MTA SDK version conflicted, current: " + str3 + ",required: " + str2) + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/");
                    C0894c.m2938a(false);
                    return false;
                } else {
                    str3 = C0894c.m2944e(context);
                    if (str3 == null || str3.length() == 0) {
                        C0894c.m2955n(SocializeConstants.OP_DIVIDER_MINUS);
                    }
                    if (str != null) {
                        C0894c.m2940b(context, str);
                    }
                    if (C0896e.m2993k(context) != null) {
                        aK.m2861a(new C0904m(context));
                    }
                    return true;
                }
            }
            aV.error("MTA StatService is disable.");
            return false;
        } catch (Throwable th) {
            aV.m2852b(th);
            return false;
        }
    }

    public static void m2988b(Context context, String str, C0897f c0897f) {
        if (C0894c.m2951l()) {
            Context i = C0896e.m2991i(context);
            if (i == null || str == null || str.length() == 0) {
                aV.error("The Context or pageName of StatService.trackEndPage() can not be null or empty!");
                return;
            }
            String str2 = new String(str);
            if (C0896e.m2993k(i) != null) {
                aK.m2861a(new C0902k(i, str2, c0897f));
            }
        }
    }

    public static void m2990d(Context context, String str) {
        if (C0894c.m2951l()) {
            Context i = C0896e.m2991i(context);
            if (i == null) {
                aV.error("The Context of StatService.trackCustomEvent() can not be null!");
                return;
            }
            Object obj = (str == null || str.length() == 0) ? 1 : null;
            if (obj != null) {
                aV.error("The event_id of StatService.trackCustomEvent() can not be null or empty.");
                return;
            }
            C0872b c0872b = new C0872b(str);
            if (C0896e.m2993k(i) != null) {
                aK.m2861a(new ar(i, c0872b));
            }
        }
    }

    private static Context m2991i(Context context) {
        return context != null ? context : aY;
    }

    private static synchronized void m2992j(Context context) {
        boolean z = false;
        synchronized (C0896e.class) {
            if (context != null) {
                if (aK == null) {
                    long f = C0890q.m2912f(context, C0894c.f3058c);
                    long u = C0885l.m2897u("2.0.3");
                    boolean z2 = true;
                    if (u <= f) {
                        aV.error("MTA is disable for current version:" + u + ",wakeup version:" + f);
                        z2 = false;
                    }
                    f = C0890q.m2912f(context, C0894c.f3054W);
                    if (f > System.currentTimeMillis()) {
                        aV.error("MTA is disable for current time:" + System.currentTimeMillis() + ",wakeup time:" + f);
                    } else {
                        z = z2;
                    }
                    C0894c.m2938a(z);
                    if (z) {
                        Context applicationContext = context.getApplicationContext();
                        aY = applicationContext;
                        aK = new C0881f();
                        al = C0885l.aw();
                        aO = System.currentTimeMillis() + C0894c.af;
                        aK.m2861a(new an(applicationContext));
                    }
                }
            }
        }
    }

    private static C0881f m2993k(Context context) {
        if (aK == null) {
            synchronized (C0896e.class) {
                if (aK == null) {
                    try {
                        C0896e.m2992j(context);
                    } catch (Throwable th) {
                        aV.m2850a(th);
                        C0894c.m2938a(false);
                    }
                }
            }
        }
        return aK;
    }

    public static void m2994l(Context context) {
        if (C0894c.m2951l() && C0896e.m2993k(context) != null) {
            aK.m2861a(new C0903l(context));
        }
    }

    public static void m2995m(Context context) {
        if (C0894c.m2951l() && C0896e.m2993k(context) != null) {
            aK.m2861a(new ao(context));
        }
    }

    static void m2996n(Context context) {
        if (C0894c.m2951l()) {
            Context i = C0896e.m2991i(context);
            if (i == null) {
                aV.error("The Context of StatService.sendNetworkDetector() can not be null!");
                return;
            }
            try {
                ak.m2844Z(i).m2846a(new C1757f(i), new aq());
            } catch (Throwable th) {
                aV.m2852b(th);
            }
        }
    }

    public static void m2997o(Context context) {
        if (C0894c.m2951l()) {
            if (C0894c.m2949k()) {
                aV.m2851b((Object) "commitEvents, maxNumber=-1");
            }
            Context i = C0896e.m2991i(context);
            if (i == null) {
                aV.error("The Context of StatService.commitEvents() can not be null!");
            } else if (C0898g.m3012r(aY).m3017X() && C0896e.m2993k(i) != null) {
                aK.m2861a(new C0899h(i));
            }
        }
    }

    public static Properties m2998p(String str) {
        return (Properties) aM.get(str);
    }

    public static void m2999p(Context context) {
        if (C0894c.m2951l() && C0894c.ay > 0) {
            Context i = C0896e.m2991i(context);
            if (i == null) {
                aV.error("The Context of StatService.testSpeed() can not be null!");
            } else {
                C0908t.m3043s(i).m3044H();
            }
        }
    }

    static void m3001q(Context context) {
        aZ = System.currentTimeMillis() + ((long) (60000 * C0894c.m2963u()));
        C0890q.m2908a(context, "last_period_ts", aZ);
        C0896e.m2997o(context);
    }
}
