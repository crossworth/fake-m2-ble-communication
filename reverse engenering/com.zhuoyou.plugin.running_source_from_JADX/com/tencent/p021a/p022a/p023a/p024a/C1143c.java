package com.tencent.p021a.p022a.p023a.p024a;

import android.util.Log;
import com.tencent.stat.DeviceInfo;
import org.json.JSONObject;

public final class C1143c {
    long f3503T = 0;
    String f3504a = null;
    String f3505b = null;
    String f3506c = "0";

    static C1143c m3322e(String str) {
        C1143c c1143c = new C1143c();
        if (C1147h.m3340b(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.isNull(DeviceInfo.TAG_IMEI)) {
                    c1143c.f3504a = jSONObject.getString(DeviceInfo.TAG_IMEI);
                }
                if (!jSONObject.isNull(DeviceInfo.TAG_MAC)) {
                    c1143c.f3505b = jSONObject.getString(DeviceInfo.TAG_MAC);
                }
                if (!jSONObject.isNull(DeviceInfo.TAG_MID)) {
                    c1143c.f3506c = jSONObject.getString(DeviceInfo.TAG_MID);
                }
                if (!jSONObject.isNull(DeviceInfo.TAG_TIMESTAMPS)) {
                    c1143c.f3503T = jSONObject.getLong(DeviceInfo.TAG_TIMESTAMPS);
                }
            } catch (Throwable e) {
                Log.w("MID", e);
            }
        }
        return c1143c;
    }

    private JSONObject m3323n() {
        JSONObject jSONObject = new JSONObject();
        try {
            C1147h.m3337a(jSONObject, DeviceInfo.TAG_IMEI, this.f3504a);
            C1147h.m3337a(jSONObject, DeviceInfo.TAG_MAC, this.f3505b);
            C1147h.m3337a(jSONObject, DeviceInfo.TAG_MID, this.f3506c);
            jSONObject.put(DeviceInfo.TAG_TIMESTAMPS, this.f3503T);
        } catch (Throwable e) {
            Log.w("MID", e);
        }
        return jSONObject;
    }

    public final String m3324a() {
        return this.f3506c;
    }

    public final String toString() {
        return m3323n().toString();
    }
}
