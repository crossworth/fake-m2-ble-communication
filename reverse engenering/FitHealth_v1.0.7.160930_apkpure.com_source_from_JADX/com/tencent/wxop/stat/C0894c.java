package com.tencent.wxop.stat;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.tencent.p004a.p005a.p006a.p007a.C0668g;
import com.tencent.stat.DeviceInfo;
import com.tencent.stat.common.StatConstants;
import com.tencent.wxop.stat.p023b.C0877b;
import com.tencent.wxop.stat.p023b.C0885l;
import com.tencent.wxop.stat.p023b.C0890q;
import com.tencent.wxop.stat.p023b.C0891r;
import java.net.URI;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public final class C0894c {
    static String f3044M = "__MTA_KILL__";
    private static C0877b f3045N = C0885l.av();
    static ah f3046O = new ah(2);
    static ah f3047P = new ah(1);
    private static C0895d f3048Q = C0895d.APP_LAUNCH;
    private static boolean f3049R = false;
    private static boolean f3050S = true;
    private static int f3051T = 30000;
    private static int f3052U = 100000;
    private static int f3053V = 30;
    static String f3054W = "__HIBERNATE__TIME";
    private static String f3055X = null;
    private static String f3056Y;
    private static String f3057Z;
    static int aA = 512;
    private static String aa = "mta_channel";
    static String ab = "";
    private static int ac = 180;
    static boolean ad = false;
    static int ae = 100;
    static long af = 10000;
    private static int ag = 1024;
    static boolean ah = true;
    private static long ai = 0;
    private static long aj = 300000;
    public static boolean ak = true;
    static volatile String al = StatConstants.MTA_SERVER;
    private static volatile String am = "http://pingma.qq.com:80/mstat/report";
    private static int an = 0;
    private static volatile int ao = 0;
    private static int ap = 20;
    private static int aq = 0;
    private static boolean ar = false;
    private static int as = 4096;
    private static boolean at = false;
    private static String au = null;
    private static boolean av = false;
    private static ai aw = null;
    static boolean ax = true;
    static int ay = 0;
    static long az = 10000;
    static String f3058c = "__HIBERNATE__";
    private static int f3059w = 10;
    private static int f3060x = 100;
    private static int f3061y = 30;
    private static int f3062z = 1;

    public static int m2927A() {
        return ap;
    }

    static void m2928B() {
        aq++;
    }

    static void m2929C() {
        aq = 0;
    }

    static int m2930D() {
        return aq;
    }

    public static boolean m2931E() {
        return at;
    }

    public static ai m2932F() {
        return aw;
    }

    static void m2933a(Context context, ah ahVar) {
        if (ahVar.aI == f3047P.aI) {
            f3047P = ahVar;
            C0894c.m2937a(ahVar.df);
            if (!f3047P.df.isNull("iplist")) {
                C0898g.m3012r(context).m3020b(f3047P.df.getString("iplist"));
            }
        } else if (ahVar.aI == f3046O.aI) {
            f3046O = ahVar;
        }
    }

    private static void m2934a(Context context, ah ahVar, JSONObject jSONObject) {
        try {
            String str;
            Object obj;
            Iterator keys = jSONObject.keys();
            Object obj2 = null;
            while (keys.hasNext()) {
                str = (String) keys.next();
                if (str.equalsIgnoreCase("v")) {
                    int i = jSONObject.getInt(str);
                    obj = ahVar.f3013L != i ? 1 : obj2;
                    ahVar.f3013L = i;
                    obj2 = obj;
                } else if (str.equalsIgnoreCase("c")) {
                    str = jSONObject.getString("c");
                    if (str.length() > 0) {
                        ahVar.df = new JSONObject(str);
                    }
                } else {
                    try {
                        if (str.equalsIgnoreCase("m")) {
                            ahVar.f3014c = jSONObject.getString("m");
                        }
                    } catch (JSONException e) {
                        f3045N.m2855e("__HIBERNATE__ not found.");
                    } catch (Throwable th) {
                        f3045N.m2852b(th);
                    }
                }
            }
            if (obj2 == 1) {
                C0908t s = C0908t.m3043s(ak.aB());
                if (s != null) {
                    s.m3047b(ahVar);
                }
                if (ahVar.aI == f3047P.aI) {
                    C0894c.m2937a(ahVar.df);
                    JSONObject jSONObject2 = ahVar.df;
                    if (!(jSONObject2 == null || jSONObject2.length() == 0)) {
                        Context aB = ak.aB();
                        try {
                            str = jSONObject2.optString(f3044M);
                            if (C0885l.m2894e(str)) {
                                JSONObject jSONObject3 = new JSONObject(str);
                                if (jSONObject3.length() != 0) {
                                    if (!jSONObject3.isNull("sm")) {
                                        obj = jSONObject3.get("sm");
                                        int intValue = obj instanceof Integer ? ((Integer) obj).intValue() : obj instanceof String ? Integer.valueOf((String) obj).intValue() : 0;
                                        if (intValue > 0) {
                                            if (f3049R) {
                                                f3045N.m2851b("match sleepTime:" + intValue + " minutes");
                                            }
                                            C0890q.m2908a(aB, f3054W, System.currentTimeMillis() + ((long) ((intValue * 60) * 1000)));
                                            C0894c.m2938a(false);
                                            f3045N.warn("MTA is disable for current SDK version");
                                        }
                                    }
                                    if (C0894c.m2941b(jSONObject3, "sv", "2.0.3")) {
                                        f3045N.m2851b((Object) "match sdk version:2.0.3");
                                        obj = 1;
                                    } else {
                                        obj = null;
                                    }
                                    if (C0894c.m2941b(jSONObject3, "md", Build.MODEL)) {
                                        f3045N.m2851b("match MODEL:" + Build.MODEL);
                                        obj = 1;
                                    }
                                    if (C0894c.m2941b(jSONObject3, "av", C0885l.m2870D(aB))) {
                                        f3045N.m2851b("match app version:" + C0885l.m2870D(aB));
                                        obj = 1;
                                    }
                                    if (C0894c.m2941b(jSONObject3, "mf", Build.MANUFACTURER)) {
                                        f3045N.m2851b("match MANUFACTURER:" + Build.MANUFACTURER);
                                        obj = 1;
                                    }
                                    if (C0894c.m2941b(jSONObject3, "osv", VERSION.SDK_INT)) {
                                        f3045N.m2851b("match android SDK version:" + VERSION.SDK_INT);
                                        obj = 1;
                                    }
                                    if (C0894c.m2941b(jSONObject3, "ov", VERSION.SDK_INT)) {
                                        f3045N.m2851b("match android SDK version:" + VERSION.SDK_INT);
                                        obj = 1;
                                    }
                                    if (C0894c.m2941b(jSONObject3, DeviceInfo.TAG_IMEI, C0908t.m3043s(aB).m3051t(aB).m2856b())) {
                                        f3045N.m2851b("match imei:" + C0908t.m3043s(aB).m3051t(aB).m2856b());
                                        obj = 1;
                                    }
                                    if (C0894c.m2941b(jSONObject3, DeviceInfo.TAG_MID, C0894c.m2947h(aB))) {
                                        f3045N.m2851b("match mid:" + C0894c.m2947h(aB));
                                        obj = 1;
                                    }
                                    if (obj != null) {
                                        C0894c.m2939b(C0885l.m2897u("2.0.3"));
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            f3045N.m2852b(th2);
                        }
                        str = jSONObject2.getString(f3058c);
                        if (f3049R) {
                            f3045N.m2855e("hibernateVer:" + str + ", current version:2.0.3");
                        }
                        long u = C0885l.m2897u(str);
                        if (C0885l.m2897u("2.0.3") <= u) {
                            C0894c.m2939b(u);
                        }
                    }
                }
            }
            C0894c.m2933a(context, ahVar);
        } catch (Throwable th22) {
            f3045N.m2852b(th22);
        } catch (Throwable th222) {
            f3045N.m2852b(th222);
        }
    }

    static void m2935a(Context context, JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase(Integer.toString(f3047P.aI))) {
                    C0894c.m2934a(context, f3047P, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase(Integer.toString(f3046O.aI))) {
                    C0894c.m2934a(context, f3046O, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase("rs")) {
                    C0895d a = C0895d.m2969a(jSONObject.getInt(str));
                    if (a != null) {
                        f3048Q = a;
                        if (f3049R) {
                            f3045N.m2855e("Change to ReportStrategy:" + a.name());
                        }
                    }
                } else {
                    return;
                }
            }
        } catch (Throwable e) {
            f3045N.m2852b(e);
        }
    }

    public static void m2936a(C0895d c0895d) {
        f3048Q = c0895d;
        if (c0895d != C0895d.PERIOD) {
            C0896e.aZ = 0;
        }
        if (f3049R) {
            f3045N.m2855e("Change to statSendStrategy: " + c0895d);
        }
    }

    private static void m2937a(JSONObject jSONObject) {
        try {
            C0895d a = C0895d.m2969a(jSONObject.getInt("rs"));
            if (a != null) {
                C0894c.m2936a(a);
            }
        } catch (JSONException e) {
            if (f3049R) {
                f3045N.m2851b((Object) "rs not found.");
            }
        }
    }

    public static void m2938a(boolean z) {
        f3050S = z;
        if (!z) {
            f3045N.warn("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    private static void m2939b(long j) {
        C0890q.m2908a(ak.aB(), f3058c, j);
        C0894c.m2938a(false);
        f3045N.warn("MTA is disable for current SDK version");
    }

    public static void m2940b(Context context, String str) {
        if (context == null) {
            f3045N.error("ctx in StatConfig.setAppKey() is null");
        } else if (str == null || str.length() > 256) {
            f3045N.error("appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        } else {
            if (f3056Y == null) {
                f3056Y = C0891r.m2923t(C0890q.m2909b(context, "_mta_ky_tag_", null));
            }
            if ((C0894c.m2953m(str) | C0894c.m2953m(C0885l.m2902z(context))) != 0) {
                String str2 = f3056Y;
                if (str2 != null) {
                    C0890q.m2911c(context, "_mta_ky_tag_", C0891r.m2922q(str2));
                }
            }
        }
    }

    private static boolean m2941b(JSONObject jSONObject, String str, String str2) {
        if (!jSONObject.isNull(str)) {
            String optString = jSONObject.optString(str);
            if (C0885l.m2894e(str2) && C0885l.m2894e(optString) && str2.equalsIgnoreCase(optString)) {
                return true;
            }
        }
        return false;
    }

    public static void m2942c(Context context, String str) {
        if (str.length() > 128) {
            f3045N.error("the length of installChannel can not exceed the range of 128 bytes.");
            return;
        }
        f3057Z = str;
        C0890q.m2911c(context, aa, str);
    }

    public static synchronized String m2943d(Context context) {
        String str;
        synchronized (C0894c.class) {
            if (f3056Y != null) {
                str = f3056Y;
            } else {
                if (context != null) {
                    if (f3056Y == null) {
                        f3056Y = C0885l.m2902z(context);
                    }
                }
                if (f3056Y == null || f3056Y.trim().length() == 0) {
                    f3045N.error("AppKey can not be null or empty, please read Developer's Guide first!");
                }
                str = f3056Y;
            }
        }
        return str;
    }

    public static synchronized String m2944e(Context context) {
        String str;
        synchronized (C0894c.class) {
            if (f3057Z != null) {
                str = f3057Z;
            } else {
                str = C0890q.m2909b(context, aa, "");
                f3057Z = str;
                if (str == null || f3057Z.trim().length() == 0) {
                    f3057Z = C0885l.m2867A(context);
                }
                if (f3057Z == null || f3057Z.trim().length() == 0) {
                    f3045N.m2853c("installChannel can not be null or empty, please read Developer's Guide first!");
                }
                str = f3057Z;
            }
        }
        return str;
    }

    public static String m2945f(Context context) {
        return C0890q.m2909b(context, "mta.acc.qq", ab);
    }

    public static String m2946g(Context context) {
        if (context == null) {
            f3045N.error("Context for getCustomUid is null.");
            return null;
        }
        if (au == null) {
            au = C0890q.m2909b(context, "MTA_CUSTOM_UID", "");
        }
        return au;
    }

    public static String m2947h(Context context) {
        return context != null ? C0668g.m2230a(context).m2232f().m2223c() : "0";
    }

    public static C0895d m2948j() {
        return f3048Q;
    }

    public static boolean m2949k() {
        return f3049R;
    }

    static String m2950l(String str) {
        try {
            String string = f3047P.df.getString(str);
            if (string != null) {
                return string;
            }
        } catch (Throwable th) {
            f3045N.m2853c("can't find custom key:" + str);
        }
        return null;
    }

    public static boolean m2951l() {
        return f3050S;
    }

    public static int m2952m() {
        return f3051T;
    }

    private static boolean m2953m(String str) {
        if (str == null) {
            return false;
        }
        if (f3056Y == null) {
            f3056Y = str;
            return true;
        } else if (f3056Y.contains(str)) {
            return false;
        } else {
            f3056Y += "|" + str;
            return true;
        }
    }

    public static int m2954n() {
        return f3060x;
    }

    public static void m2955n(String str) {
        if (str.length() > 128) {
            f3045N.error("the length of installChannel can not exceed the range of 128 bytes.");
        } else {
            f3057Z = str;
        }
    }

    public static int m2956o() {
        return f3061y;
    }

    public static void m2957o(String str) {
        if (str == null || str.length() == 0) {
            f3045N.error("statReportUrl cannot be null or empty.");
            return;
        }
        am = str;
        try {
            al = new URI(am).getHost();
        } catch (Exception e) {
            f3045N.m2853c(e);
        }
        if (f3049R) {
            f3045N.m2851b("url:" + am + ", domain:" + al);
        }
    }

    public static int m2958p() {
        return f3059w;
    }

    public static int m2959q() {
        return f3062z;
    }

    static int m2960r() {
        return f3053V;
    }

    public static int m2961s() {
        return f3052U;
    }

    public static void m2962t() {
        ac = 60;
    }

    public static int m2963u() {
        return ac;
    }

    public static int m2964v() {
        return ag;
    }

    public static void m2965w() {
        ah = true;
    }

    public static boolean m2966x() {
        return ak;
    }

    public static String m2967y() {
        return am;
    }

    static synchronized void m2968z() {
        synchronized (C0894c.class) {
            ao = 0;
        }
    }
}
