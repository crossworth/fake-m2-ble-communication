package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.C1405n;
import com.tencent.stat.DeviceInfo;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.C1389k;
import org.json.JSONObject;

public abstract class C1365e {
    private static volatile boolean f4339a = false;
    protected String f4340b = null;
    protected long f4341c;
    protected int f4342d;
    protected DeviceInfo f4343e = null;
    protected int f4344f;
    protected String f4345g = null;
    protected String f4346h = null;
    protected String f4347i = null;
    protected String f4348j = null;
    protected Context f4349k;

    C1365e(Context context, int i) {
        this.f4349k = context;
        this.f4341c = System.currentTimeMillis() / 1000;
        this.f4342d = i;
        this.f4340b = StatConfig.getAppKey(context);
        this.f4345g = StatConfig.getCustomUserId(context);
        this.f4343e = C1405n.m4189a(context).m4213b(context);
        this.f4344f = C1389k.m4157w(context).intValue();
        this.f4347i = C1389k.m4148n(context);
        this.f4346h = StatConfig.getInstallChannel(context);
    }

    public abstract C1370f mo2219a();

    public abstract boolean mo2220a(JSONObject jSONObject);

    public long m4043b() {
        return this.f4341c;
    }

    public boolean m4044b(JSONObject jSONObject) {
        try {
            C1389k.m4121a(jSONObject, "ky", this.f4340b);
            jSONObject.put("et", mo2219a().m4057a());
            if (this.f4343e != null) {
                jSONObject.put(DeviceInfo.TAG_IMEI, this.f4343e.getImei());
                C1389k.m4121a(jSONObject, DeviceInfo.TAG_MAC, this.f4343e.getMac());
                jSONObject.put("ut", this.f4343e.getUserType());
            }
            C1389k.m4121a(jSONObject, "cui", this.f4345g);
            if (mo2219a() != C1370f.SESSION_ENV) {
                C1389k.m4121a(jSONObject, "av", this.f4347i);
                C1389k.m4121a(jSONObject, "ch", this.f4346h);
            }
            C1389k.m4121a(jSONObject, DeviceInfo.TAG_MID, StatConfig.getMid(this.f4349k));
            jSONObject.put("idx", this.f4344f);
            jSONObject.put("si", this.f4342d);
            jSONObject.put(DeviceInfo.TAG_TIMESTAMPS, this.f4341c);
            if (this.f4343e.getUserType() == 0 && C1389k.m4113E(this.f4349k) == 1) {
                jSONObject.put("ia", 1);
            }
            return mo2220a(jSONObject);
        } catch (Throwable th) {
            return false;
        }
    }

    public Context m4045c() {
        return this.f4349k;
    }

    public String m4046d() {
        try {
            JSONObject jSONObject = new JSONObject();
            m4044b(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
