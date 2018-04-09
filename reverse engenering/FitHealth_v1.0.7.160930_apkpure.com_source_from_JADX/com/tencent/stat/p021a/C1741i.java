package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import org.json.JSONObject;

public class C1741i extends C0824e {
    private static String f4697a = null;
    private String f4698l = null;
    private String f4699m = null;

    public C1741i(Context context, int i) {
        super(context, i);
        this.f4698l = C0837k.m2743p(context);
        if (f4697a == null) {
            f4697a = C0837k.m2740m(context);
        }
    }

    public C0825f mo2142a() {
        return C0825f.NETWORK_MONITOR;
    }

    public void m4871a(String str) {
        this.f4699m = str;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        C0837k.m2714a(jSONObject, "op", f4697a);
        C0837k.m2714a(jSONObject, "cn", this.f4698l);
        jSONObject.put("sp", this.f4699m);
        return true;
    }
}
