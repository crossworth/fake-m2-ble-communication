package com.tencent.wxop.stat.p023b;

import com.tencent.stat.DeviceInfo;
import org.json.JSONException;
import org.json.JSONObject;

public final class C0878c {
    private String f3022W = "0";
    private String f3023a = null;
    private String f3024b = null;
    private int bf = 0;
    private String f3025c = null;
    private int cu;
    private long cv = 0;

    public C0878c(String str, String str2, int i) {
        this.f3023a = str;
        this.f3024b = str2;
        this.cu = i;
    }

    private JSONObject aq() {
        JSONObject jSONObject = new JSONObject();
        try {
            C0891r.m2918a(jSONObject, DeviceInfo.TAG_IMEI, this.f3023a);
            C0891r.m2918a(jSONObject, "mc", this.f3024b);
            C0891r.m2918a(jSONObject, DeviceInfo.TAG_MID, this.f3022W);
            C0891r.m2918a(jSONObject, DeviceInfo.TAG_ANDROID_ID, this.f3025c);
            jSONObject.put("ts", this.cv);
            jSONObject.put(DeviceInfo.TAG_VERSION, this.bf);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final String ar() {
        return this.f3024b;
    }

    public final int as() {
        return this.cu;
    }

    public final String m2856b() {
        return this.f3023a;
    }

    public final String toString() {
        return aq().toString();
    }

    public final void m2857z() {
        this.cu = 1;
    }
}
