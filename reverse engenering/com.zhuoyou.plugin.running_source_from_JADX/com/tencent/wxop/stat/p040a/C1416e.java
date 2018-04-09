package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.p021a.p022a.p023a.p024a.C1147h;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.au;
import com.tencent.wxop.stat.common.C1432a;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public abstract class C1416e {
    protected static String f4590j = null;
    private StatSpecifyReportedInfo f4591a = null;
    protected String f4592b = null;
    protected long f4593c;
    protected int f4594d;
    protected C1432a f4595e = null;
    protected int f4596f;
    protected String f4597g = null;
    protected String f4598h = null;
    protected String f4599i = null;
    protected boolean f4600k = false;
    protected Context f4601l;

    C1416e(Context context, int i, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4601l = context;
        this.f4593c = System.currentTimeMillis() / 1000;
        this.f4594d = i;
        this.f4598h = StatConfig.getInstallChannel(context);
        this.f4599i = C1442k.m4432j(context);
        this.f4592b = StatConfig.getAppKey(context);
        if (statSpecifyReportedInfo != null) {
            this.f4591a = statSpecifyReportedInfo;
            if (C1442k.m4420c(statSpecifyReportedInfo.getAppKey())) {
                this.f4592b = statSpecifyReportedInfo.getAppKey();
            }
            if (C1442k.m4420c(statSpecifyReportedInfo.getInstallChannel())) {
                this.f4598h = statSpecifyReportedInfo.getInstallChannel();
            }
            if (C1442k.m4420c(statSpecifyReportedInfo.getVersion())) {
                this.f4599i = statSpecifyReportedInfo.getVersion();
            }
            this.f4600k = statSpecifyReportedInfo.isImportant();
        }
        this.f4597g = StatConfig.getCustomUserId(context);
        this.f4595e = au.m4332a(context).m4365b(context);
        if (mo2223a() != C1421f.NETWORK_DETECTOR) {
            this.f4596f = C1442k.m4441s(context).intValue();
        } else {
            this.f4596f = -C1421f.NETWORK_DETECTOR.m4284a();
        }
        if (!C1147h.m3341c(f4590j)) {
            String localMidOnly = StatConfig.getLocalMidOnly(context);
            f4590j = localMidOnly;
            if (!C1442k.m4420c(localMidOnly)) {
                f4590j = "0";
            }
        }
    }

    public abstract C1421f mo2223a();

    public abstract boolean mo2224a(JSONObject jSONObject);

    public boolean m4268b(JSONObject jSONObject) {
        boolean z = false;
        try {
            C1448q.m4464a(jSONObject, "ky", this.f4592b);
            jSONObject.put("et", mo2223a().m4284a());
            if (this.f4595e != null) {
                jSONObject.put(DeviceInfo.TAG_IMEI, this.f4595e.m4381b());
                C1448q.m4464a(jSONObject, DeviceInfo.TAG_MAC, this.f4595e.m4382c());
                int d = this.f4595e.m4383d();
                jSONObject.put("ut", d);
                if (d == 0 && C1442k.m4445w(this.f4601l) == 1) {
                    jSONObject.put("ia", 1);
                }
            }
            C1448q.m4464a(jSONObject, "cui", this.f4597g);
            if (mo2223a() != C1421f.SESSION_ENV) {
                C1448q.m4464a(jSONObject, "av", this.f4599i);
                C1448q.m4464a(jSONObject, "ch", this.f4598h);
            }
            if (this.f4600k) {
                jSONObject.put("impt", 1);
            }
            C1448q.m4464a(jSONObject, DeviceInfo.TAG_MID, f4590j);
            jSONObject.put("idx", this.f4596f);
            jSONObject.put("si", this.f4594d);
            jSONObject.put(DeviceInfo.TAG_TIMESTAMPS, this.f4593c);
            jSONObject.put("dts", C1442k.m4405a(this.f4601l, false));
            z = mo2224a(jSONObject);
        } catch (Throwable th) {
        }
        return z;
    }

    public long m4269c() {
        return this.f4593c;
    }

    public StatSpecifyReportedInfo m4270d() {
        return this.f4591a;
    }

    public Context m4271e() {
        return this.f4601l;
    }

    public boolean m4272f() {
        return this.f4600k;
    }

    public String m4273g() {
        try {
            JSONObject jSONObject = new JSONObject();
            m4268b(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
