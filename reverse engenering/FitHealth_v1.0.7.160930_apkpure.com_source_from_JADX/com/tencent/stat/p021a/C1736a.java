package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.C0837k;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class C1736a extends C0824e {
    Map<String, ?> f4687a = null;

    public C1736a(Context context, int i, Map<String, ?> map) {
        super(context, i);
        this.f4687a = map;
    }

    public C0825f mo2142a() {
        return C0825f.ADDITION;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        C0837k.m2714a(jSONObject, "qq", StatConfig.getQQ());
        if (this.f4687a != null && this.f4687a.size() > 0) {
            for (Entry entry : this.f4687a.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
        }
        return true;
    }
}
