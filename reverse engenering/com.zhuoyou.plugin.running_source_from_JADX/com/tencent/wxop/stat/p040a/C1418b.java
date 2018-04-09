package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatServiceImpl;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class C1418b extends C1416e {
    protected C1419c f4603a = new C1419c();
    private long f4604m = -1;

    public C1418b(Context context, int i, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4603a.f4605a = str;
    }

    private void m4276h() {
        if (this.f4603a.f4605a != null) {
            Map commonKeyValueForKVEvent = StatServiceImpl.getCommonKeyValueForKVEvent(this.f4603a.f4605a);
            if (commonKeyValueForKVEvent != null && commonKeyValueForKVEvent.size() > 0) {
                if (this.f4603a.f4607c == null || this.f4603a.f4607c.length() == 0) {
                    this.f4603a.f4607c = new JSONObject(commonKeyValueForKVEvent);
                    return;
                }
                for (Entry entry : commonKeyValueForKVEvent.entrySet()) {
                    try {
                        this.f4603a.f4607c.put(entry.getKey().toString(), entry.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public C1421f mo2223a() {
        return C1421f.CUSTOM;
    }

    public void m4278a(long j) {
        this.f4604m = j;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        jSONObject.put("ei", this.f4603a.f4605a);
        if (this.f4604m > 0) {
            jSONObject.put("du", this.f4604m);
        }
        if (this.f4603a.f4606b == null) {
            m4276h();
            jSONObject.put("kv", this.f4603a.f4607c);
        } else {
            jSONObject.put("ar", this.f4603a.f4606b);
        }
        return true;
    }

    public C1419c m4280b() {
        return this.f4603a;
    }
}
