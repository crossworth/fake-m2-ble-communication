package com.tencent.p004a.p005a.p006a.p007a;

import android.util.Log;
import com.tencent.stat.DeviceInfo;
import org.json.JSONObject;

public final class C0666c {
    String f2312a = null;
    String f2313b = null;
    String f2314c = "0";
    long f2315d = 0;

    static C0666c m2221c(String str) {
        C0666c c0666c = new C0666c();
        if (C0669h.m2238d(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.isNull(DeviceInfo.TAG_IMEI)) {
                    c0666c.f2312a = jSONObject.getString(DeviceInfo.TAG_IMEI);
                }
                if (!jSONObject.isNull("mc")) {
                    c0666c.f2313b = jSONObject.getString("mc");
                }
                if (!jSONObject.isNull(DeviceInfo.TAG_MID)) {
                    c0666c.f2314c = jSONObject.getString(DeviceInfo.TAG_MID);
                }
                if (!jSONObject.isNull("ts")) {
                    c0666c.f2315d = jSONObject.getLong("ts");
                }
            } catch (Throwable e) {
                Log.w("MID", e);
            }
        }
        return c0666c;
    }

    private JSONObject m2222d() {
        JSONObject jSONObject = new JSONObject();
        try {
            C0669h.m2234a(jSONObject, DeviceInfo.TAG_IMEI, this.f2312a);
            C0669h.m2234a(jSONObject, "mc", this.f2313b);
            C0669h.m2234a(jSONObject, DeviceInfo.TAG_MID, this.f2314c);
            jSONObject.put("ts", this.f2315d);
        } catch (Throwable e) {
            Log.w("MID", e);
        }
        return jSONObject;
    }

    public final String m2223c() {
        return this.f2314c;
    }

    public final String toString() {
        return m2222d().toString();
    }
}
