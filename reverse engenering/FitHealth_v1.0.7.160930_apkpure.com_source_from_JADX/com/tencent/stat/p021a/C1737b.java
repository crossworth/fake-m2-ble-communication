package com.tencent.stat.p021a;

import android.content.Context;
import java.util.Map.Entry;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;
import p031u.aly.au;

public class C1737b extends C0824e {
    protected C0823c f4688a = new C0823c();
    private long f4689l = -1;

    public C1737b(Context context, int i, String str) {
        super(context, i);
        this.f4688a.f2837a = str;
    }

    public C0825f mo2142a() {
        return C0825f.CUSTOM;
    }

    public void m4859a(long j) {
        this.f4689l = j;
    }

    public void m4860a(Properties properties) {
        if (properties != null) {
            this.f4688a.f2839c = (Properties) properties.clone();
        }
    }

    public void m4861a(String[] strArr) {
        if (strArr != null) {
            this.f4688a.f2838b = (String[]) strArr.clone();
        }
    }

    public boolean mo2143a(JSONObject jSONObject) {
        jSONObject.put("ei", this.f4688a.f2837a);
        if (this.f4689l > 0) {
            jSONObject.put(au.aI, this.f4689l);
        }
        if (this.f4688a.f2839c == null && this.f4688a.f2838b == null) {
            jSONObject.put("kv", new JSONObject());
        }
        if (this.f4688a.f2838b != null) {
            JSONArray jSONArray = new JSONArray();
            for (Object put : this.f4688a.f2838b) {
                jSONArray.put(put);
            }
            jSONObject.put("ar", jSONArray);
        }
        if (this.f4688a.f2839c != null) {
            Object jSONObject2;
            JSONObject jSONObject3 = new JSONObject();
            try {
                for (Entry entry : this.f4688a.f2839c.entrySet()) {
                    jSONObject3.put(entry.getKey().toString(), entry.getValue().toString());
                }
                JSONObject jSONObject4 = jSONObject3;
            } catch (Exception e) {
                jSONObject2 = new JSONObject(this.f4688a.f2839c);
            }
            jSONObject.put("kv", jSONObject2);
        }
        return true;
    }
}
