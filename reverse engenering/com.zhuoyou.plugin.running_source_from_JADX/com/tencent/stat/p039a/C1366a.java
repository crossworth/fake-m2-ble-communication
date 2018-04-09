package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.C1389k;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class C1366a extends C1365e {
    Map<String, ?> f4350a = null;

    public C1366a(Context context, int i, Map<String, ?> map) {
        super(context, i);
        this.f4350a = map;
    }

    public C1370f mo2219a() {
        return C1370f.ADDITION;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        C1389k.m4121a(jSONObject, "qq", StatConfig.getQQ());
        if (this.f4350a != null && this.f4350a.size() > 0) {
            for (Entry entry : this.f4350a.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
        }
        return true;
    }
}
