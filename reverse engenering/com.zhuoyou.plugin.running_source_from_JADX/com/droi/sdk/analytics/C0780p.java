package com.droi.sdk.analytics;

import android.content.Context;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.droi.sdk.internal.DroiDataCollector;
import com.droi.sdk.internal.DroiDeviceInfoCollector;
import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

class C0780p {
    private static String f2350a;
    private static boolean f2351b;

    private static JSONObject m2408a(Context context, long j, long j2, long j3) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("did", AnalyticsCoreHelper.getDeviceId());
        jSONObject.put("lst", j);
        jSONObject.put("let", j2);
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, j3);
        jSONObject.put("s05", DroiDataCollector.getBaseStationInfo(context));
        jSONObject.put("a03", C0754b.m2318b());
        jSONObject.put("a04", C0754b.m2319c());
        jSONObject.put("a05", C0754b.m2320d());
        jSONObject.put("a06", C0754b.m2321e());
        jSONObject.put("p14", DroiDataCollector.getCurNetworkType(context));
        return jSONObject;
    }

    private static JSONObject m2409a(Context context, long j, long j2, long j3, String str) {
        JSONObject jSONObject = new JSONObject();
        if (f2350a == null) {
            try {
                f2350a = C0755c.m2331b(context, j2);
            } catch (Exception e) {
                C0753a.m2311a("UsinglogManager", e);
            }
        }
        jSONObject.put("did", AnalyticsCoreHelper.getDeviceId());
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, f2350a);
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, j);
        jSONObject.put("et", j2);
        jSONObject.put("du", j3);
        jSONObject.put("ac", str);
        jSONObject.put("a03", C0754b.m2318b());
        jSONObject.put("a04", C0754b.m2319c());
        jSONObject.put("a05", C0754b.m2320d());
        return jSONObject;
    }

    private static void m2410a(Context context) {
        C0753a.m2312a("UsinglogManager", "Start postInfo");
        DroiDeviceInfoCollector.postDeviceInfo(context, AnalyticsCoreHelper.getDeviceId(), AnalyticsCoreHelper.getAppId(), false);
    }

    protected static void m2411a(Context context, long j) {
        C0753a.m2312a("UsinglogManager", "Call onResume()");
        f2351b = true;
        if (context == null) {
            C0753a.m2314c("UsinglogManager", "context is null");
            return;
        }
        C0780p.m2410a(context);
        if (C0755c.m2327a(context, j)) {
            C0779o c0779o = new C0779o(context);
            long b = c0779o.m2406b("last_start_time", 0);
            long b2 = c0779o.m2406b("last_end_time", 0);
            c0779o.m2404a("last_start_time", j);
            try {
                f2350a = C0755c.m2331b(context, j);
                C0753a.m2312a("UsinglogManager", "New Sessionid is " + f2350a);
                JSONObject jSONObject = new JSONObject();
                JSONObject a = C0780p.m2408a(context, b, b2, j);
                if (a != null) {
                    jSONObject.put("mt", "m02");
                    jSONObject.put(DeviceInfo.TAG_MAC, a);
                    C0770f.m2357c(new C0775k(1, 1, 1, "m02").toString(), jSONObject.toString());
                } else {
                    return;
                }
            } catch (Exception e) {
                C0753a.m2311a("UsinglogManager", e);
            } catch (Exception e2) {
                C0753a.m2311a("UsinglogManager", e2);
            }
        }
        C0755c.m2335c(context, j);
        C0755c.m2325a(context, C0755c.m2322a(context));
    }

    protected static void m2412a(Context context, String str, long j) {
        C0753a.m2312a("UsinglogManager", "Call onFragmentStart()");
        if (context == null) {
            C0753a.m2314c("UsinglogManager", "context is null");
        } else if (str == null || C0755c.m2328a("^[0-9a-zA-Z\\-_.]{1,128}$", (CharSequence) str)) {
            C0753a.m2314c("UsinglogManager", "pageName is invalid, please check!");
        } else {
            new C0779o(context).m2404a(str + "_start_time", j);
        }
    }

    private static JSONObject m2413b(Context context, long j, long j2, long j3, String str) {
        JSONObject jSONObject = new JSONObject();
        if (f2350a == null) {
            try {
                f2350a = C0755c.m2331b(context, j2);
            } catch (Exception e) {
                C0753a.m2311a("UsinglogManager", e);
            }
        }
        jSONObject.put("did", AnalyticsCoreHelper.getDeviceId());
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, f2350a);
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, j);
        jSONObject.put("et", j2);
        jSONObject.put("du", j3);
        jSONObject.put("pa", str);
        jSONObject.put("ac", C0755c.m2322a(context));
        jSONObject.put("a03", C0754b.m2318b());
        jSONObject.put("a04", C0754b.m2319c());
        jSONObject.put("a05", C0754b.m2320d());
        return jSONObject;
    }

    protected static synchronized void m2414b(Context context, long j) {
        synchronized (C0780p.class) {
            if (f2351b) {
                f2351b = false;
                C0753a.m2312a("UsinglogManager", "Call onPause()");
                if (context == null) {
                    C0753a.m2314c("UsinglogManager", "context is null");
                } else {
                    C0779o c0779o = new C0779o(context);
                    String c = C0755c.m2334c(context);
                    long b = C0755c.m2330b(context);
                    long j2 = j - b;
                    c0779o.m2404a("last_end_time", j);
                    C0755c.m2335c(context, j);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        JSONObject a = C0780p.m2409a(context, b, j, j2, c);
                        if (a != null) {
                            jSONObject.put("mt", "m03");
                            jSONObject.put(DeviceInfo.TAG_MAC, a);
                            C0770f.m2354a(new C0775k(1, 0, 1, "m03").toString(), jSONObject.toString());
                        }
                    } catch (Exception e) {
                        C0753a.m2311a("UsinglogManager", e);
                    }
                }
            }
        }
    }

    protected static void m2415b(Context context, String str, long j) {
        C0753a.m2312a("UsinglogManager", "Call onFragmentEnd()");
        if (context == null) {
            C0753a.m2314c("UsinglogManager", "context is null");
        } else if (str == null || C0755c.m2328a("^[0-9a-zA-Z\\-_.]{1,128}$", (CharSequence) str)) {
            C0753a.m2314c("UsinglogManager", "pageName is invalid, please check!");
        } else {
            long b = new C0779o(context).m2406b(str + "_start_time", 0);
            if (b != 0) {
                long j2 = j - b;
                JSONObject jSONObject = new JSONObject();
                try {
                    JSONObject b2 = C0780p.m2413b(context, b, j, j2, str);
                    if (b2 != null) {
                        jSONObject.put("mt", "m04");
                        jSONObject.put(DeviceInfo.TAG_MAC, b2);
                        C0770f.m2354a(new C0775k(1, 0, 1, "m04").toString(), jSONObject.toString());
                    }
                } catch (Exception e) {
                    C0753a.m2311a("UsinglogManager", e);
                }
            }
        }
    }
}
