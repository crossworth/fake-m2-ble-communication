package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.common.C1389k;
import org.json.JSONObject;

public class C1373i extends C1365e {
    private static String f4373a = null;
    private String f4374l = null;
    private String f4375m = null;

    public C1373i(Context context, int i) {
        super(context, i);
        this.f4374l = C1389k.m4150p(context);
        if (f4373a == null) {
            f4373a = C1389k.m4147m(context);
        }
    }

    public C1370f mo2219a() {
        return C1370f.NETWORK_MONITOR;
    }

    public void m4063a(String str) {
        this.f4375m = str;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        C1389k.m4121a(jSONObject, "op", f4373a);
        C1389k.m4121a(jSONObject, "cn", this.f4374l);
        jSONObject.put("sp", this.f4375m);
        return true;
    }
}
