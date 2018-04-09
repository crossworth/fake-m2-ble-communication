package com.tencent.wxop.stat.common;

import com.tencent.stat.DeviceInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class C1432a {
    private String f4736a = null;
    private String f4737b = null;
    private String f4738c = null;
    private String f4739d = "0";
    private int f4740e;
    private int f4741f = 0;
    private long f4742g = 0;

    public C1432a(String str, String str2, int i) {
        this.f4736a = str;
        this.f4737b = str2;
        this.f4740e = i;
    }

    JSONObject m4379a() {
        JSONObject jSONObject = new JSONObject();
        try {
            C1448q.m4464a(jSONObject, DeviceInfo.TAG_IMEI, this.f4736a);
            C1448q.m4464a(jSONObject, DeviceInfo.TAG_MAC, this.f4737b);
            C1448q.m4464a(jSONObject, DeviceInfo.TAG_MID, this.f4739d);
            C1448q.m4464a(jSONObject, "aid", this.f4738c);
            jSONObject.put(DeviceInfo.TAG_TIMESTAMPS, this.f4742g);
            jSONObject.put(DeviceInfo.TAG_VERSION, this.f4741f);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public void m4380a(int i) {
        this.f4740e = i;
    }

    public String m4381b() {
        return this.f4736a;
    }

    public String m4382c() {
        return this.f4737b;
    }

    public int m4383d() {
        return this.f4740e;
    }

    public String toString() {
        return m4379a().toString();
    }
}
