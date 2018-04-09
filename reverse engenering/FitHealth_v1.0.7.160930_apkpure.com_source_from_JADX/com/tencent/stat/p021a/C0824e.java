package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.C0850n;
import com.tencent.stat.DeviceInfo;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.C0837k;
import org.json.JSONObject;

public abstract class C0824e {
    private static volatile boolean f2840a = false;
    protected String f2841b = null;
    protected long f2842c;
    protected int f2843d;
    protected DeviceInfo f2844e = null;
    protected int f2845f;
    protected String f2846g = null;
    protected String f2847h = null;
    protected String f2848i = null;
    protected String f2849j = null;
    protected Context f2850k;

    C0824e(Context context, int i) {
        this.f2850k = context;
        this.f2842c = System.currentTimeMillis() / 1000;
        this.f2843d = i;
        this.f2841b = StatConfig.getAppKey(context);
        this.f2846g = StatConfig.getCustomUserId(context);
        this.f2844e = C0850n.m2778a(context).m2802b(context);
        this.f2845f = C0837k.m2750w(context).intValue();
        this.f2848i = C0837k.m2741n(context);
        this.f2847h = StatConfig.getInstallChannel(context);
    }

    public abstract C0825f mo2142a();

    public abstract boolean mo2143a(JSONObject jSONObject);

    public long m2659b() {
        return this.f2842c;
    }

    public boolean m2660b(JSONObject jSONObject) {
        try {
            C0837k.m2714a(jSONObject, "ky", this.f2841b);
            jSONObject.put("et", mo2142a().m2663a());
            if (this.f2844e != null) {
                jSONObject.put(DeviceInfo.TAG_IMEI, this.f2844e.getImei());
                C0837k.m2714a(jSONObject, "mc", this.f2844e.getMac());
                jSONObject.put("ut", this.f2844e.getUserType());
            }
            C0837k.m2714a(jSONObject, "cui", this.f2846g);
            if (mo2142a() != C0825f.SESSION_ENV) {
                C0837k.m2714a(jSONObject, "av", this.f2848i);
                C0837k.m2714a(jSONObject, "ch", this.f2847h);
            }
            C0837k.m2714a(jSONObject, DeviceInfo.TAG_MID, StatConfig.getMid(this.f2850k));
            jSONObject.put("idx", this.f2845f);
            jSONObject.put("si", this.f2843d);
            jSONObject.put("ts", this.f2842c);
            if (this.f2844e.getUserType() == 0 && C0837k.m2706E(this.f2850k) == 1) {
                jSONObject.put("ia", 1);
            }
            return mo2143a(jSONObject);
        } catch (Throwable th) {
            return false;
        }
    }

    public Context m2661c() {
        return this.f2850k;
    }

    public String m2662d() {
        try {
            JSONObject jSONObject = new JSONObject();
            m2660b(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
