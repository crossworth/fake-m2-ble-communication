package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.p004a.p005a.p006a.p007a.C0669h;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.C0894c;
import com.tencent.wxop.stat.C0897f;
import com.tencent.wxop.stat.C0908t;
import com.tencent.wxop.stat.p023b.C0878c;
import com.tencent.wxop.stat.p023b.C0885l;
import com.tencent.wxop.stat.p023b.C0891r;
import org.json.JSONObject;

public abstract class C0873d {
    protected static String bt = null;
    protected int f3006L;
    protected long aZ;
    protected String f3007b = null;
    protected int bf;
    protected C0878c bp = null;
    protected String bq = null;
    protected String br = null;
    protected String bs = null;
    protected boolean bu = false;
    protected Context bv;
    private C0897f bw = null;

    C0873d(Context context, int i, C0897f c0897f) {
        this.bv = context;
        this.aZ = System.currentTimeMillis() / 1000;
        this.f3006L = i;
        this.br = C0894c.m2944e(context);
        this.bs = C0885l.m2870D(context);
        this.f3007b = C0894c.m2943d(context);
        if (c0897f != null) {
            this.bw = c0897f;
            if (C0885l.m2894e(c0897f.m3004S())) {
                this.f3007b = c0897f.m3004S();
            }
            if (C0885l.m2894e(c0897f.m3005T())) {
                this.br = c0897f.m3005T();
            }
            if (C0885l.m2894e(c0897f.getVersion())) {
                this.bs = c0897f.getVersion();
            }
            this.bu = c0897f.m3006U();
        }
        this.bq = C0894c.m2946g(context);
        this.bp = C0908t.m3043s(context).m3051t(context);
        if (ac() != C0874e.NETWORK_DETECTOR) {
            this.bf = C0885l.m2878K(context).intValue();
        } else {
            this.bf = -C0874e.NETWORK_DETECTOR.m2838r();
        }
        if (!C0669h.m2239e(bt)) {
            String h = C0894c.m2947h(context);
            bt = h;
            if (!C0885l.m2894e(h)) {
                bt = "0";
            }
        }
    }

    private boolean m2834c(JSONObject jSONObject) {
        boolean z = false;
        try {
            C0891r.m2918a(jSONObject, "ky", this.f3007b);
            jSONObject.put("et", ac().m2838r());
            if (this.bp != null) {
                jSONObject.put(DeviceInfo.TAG_IMEI, this.bp.m2856b());
                C0891r.m2918a(jSONObject, "mc", this.bp.ar());
                int as = this.bp.as();
                jSONObject.put("ut", as);
                if (as == 0 && C0885l.m2881N(this.bv) == 1) {
                    jSONObject.put("ia", 1);
                }
            }
            C0891r.m2918a(jSONObject, "cui", this.bq);
            if (ac() != C0874e.SESSION_ENV) {
                C0891r.m2918a(jSONObject, "av", this.bs);
                C0891r.m2918a(jSONObject, "ch", this.br);
            }
            if (this.bu) {
                jSONObject.put("impt", 1);
            }
            C0891r.m2918a(jSONObject, DeviceInfo.TAG_MID, bt);
            jSONObject.put("idx", this.bf);
            jSONObject.put("si", this.f3006L);
            jSONObject.put("ts", this.aZ);
            jSONObject.put("dts", C0885l.m2886a(this.bv, false));
            z = mo2147b(jSONObject);
        } catch (Throwable th) {
        }
        return z;
    }

    public final Context m2835J() {
        return this.bv;
    }

    public final boolean m2836X() {
        return this.bu;
    }

    public abstract C0874e ac();

    public final long ad() {
        return this.aZ;
    }

    public final C0897f ae() {
        return this.bw;
    }

    public final String af() {
        try {
            JSONObject jSONObject = new JSONObject();
            m2834c(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            return "";
        }
    }

    public abstract boolean mo2147b(JSONObject jSONObject);
}
