package com.tencent.stat.p039a;

import android.content.Context;
import java.util.Map.Entry;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class C1367b extends C1365e {
    protected C1368c f4351a = new C1368c();
    private long f4352l = -1;

    public C1367b(Context context, int i, String str) {
        super(context, i);
        this.f4351a.f4353a = str;
    }

    public C1370f mo2219a() {
        return C1370f.CUSTOM;
    }

    public void m4050a(long j) {
        this.f4352l = j;
    }

    public void m4051a(Properties properties) {
        if (properties != null) {
            this.f4351a.f4355c = (Properties) properties.clone();
        }
    }

    public void m4052a(String[] strArr) {
        if (strArr != null) {
            this.f4351a.f4354b = (String[]) strArr.clone();
        }
    }

    public boolean mo2220a(JSONObject jSONObject) {
        jSONObject.put("ei", this.f4351a.f4353a);
        if (this.f4352l > 0) {
            jSONObject.put("du", this.f4352l);
        }
        if (this.f4351a.f4355c == null && this.f4351a.f4354b == null) {
            jSONObject.put("kv", new JSONObject());
        }
        if (this.f4351a.f4354b != null) {
            JSONArray jSONArray = new JSONArray();
            for (Object put : this.f4351a.f4354b) {
                jSONArray.put(put);
            }
            jSONObject.put("ar", jSONArray);
        }
        if (this.f4351a.f4355c != null) {
            Object jSONObject2;
            JSONObject jSONObject3 = new JSONObject();
            try {
                for (Entry entry : this.f4351a.f4355c.entrySet()) {
                    jSONObject3.put(entry.getKey().toString(), entry.getValue().toString());
                }
                JSONObject jSONObject4 = jSONObject3;
            } catch (Exception e) {
                jSONObject2 = new JSONObject(this.f4351a.f4355c);
            }
            jSONObject.put("kv", jSONObject2);
        }
        return true;
    }
}
