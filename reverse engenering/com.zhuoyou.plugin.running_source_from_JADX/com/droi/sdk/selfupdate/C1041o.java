package com.droi.sdk.selfupdate;

import com.droi.sdk.analytics.priv.AnalyticsModule;
import com.droi.sdk.core.SelfUpdateCoreHelper;
import com.droi.sdk.internal.DroiLog;
import com.tencent.stat.DeviceInfo;
import org.json.JSONObject;

class C1041o {
    protected static int f3450a = 0;
    protected static int f3451b = 1;
    protected static int f3452c = 2;
    protected static int f3453d = 3;
    protected static int f3454e = 4;
    protected static int f3455f = 5;
    protected static int f3456g = 6;
    protected static int f3457h = 7;
    protected static int f3458i = 8;
    protected static int f3459j = 9;
    protected static int f3460k = 10;
    protected static int f3461l = 11;
    protected static int f3462m = 12;
    protected static int f3463n = 13;
    private static AnalyticsModule f3464o;

    protected static void m3235a(String str, String str2, int i, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("taskId", str2);
            jSONObject2.put("action", i);
            jSONObject2.put("actionTime", j);
            jSONObject2.put("deviceId", SelfUpdateCoreHelper.getDeviceId(C1032g.f3423a));
            jSONObject.put("mt", str);
            jSONObject.put(DeviceInfo.TAG_MAC, jSONObject2);
            C1041o.m3234a(new C1039m(5, 1, 0, str).toString(), jSONObject.toString());
        } catch (Exception e) {
            DroiLog.m2869e("UpdateAnalyticsModule", e);
        }
    }

    protected static void m3234a(String str, String str2) {
        DroiLog.m2871i("UpdateAnalyticsModule", str2);
        if (f3464o != null) {
            DroiLog.m2871i("UpdateAnalyticsModule", "analyticsModule != null");
            f3464o.send(0, str, str2);
        }
    }
}
