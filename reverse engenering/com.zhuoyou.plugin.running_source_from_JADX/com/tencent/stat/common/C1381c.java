package com.tencent.stat.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.tencent.stat.StatConfig;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;

class C1381c {
    String f4400a;
    String f4401b;
    DisplayMetrics f4402c;
    int f4403d;
    String f4404e;
    String f4405f;
    String f4406g;
    String f4407h;
    String f4408i;
    String f4409j;
    String f4410k;
    int f4411l;
    String f4412m;
    Context f4413n;
    private String f4414o;
    private String f4415p;
    private String f4416q;
    private String f4417r;

    private C1381c(Context context) {
        this.f4401b = StatConstants.VERSION;
        this.f4403d = VERSION.SDK_INT;
        this.f4404e = Build.MODEL;
        this.f4405f = Build.MANUFACTURER;
        this.f4406g = Locale.getDefault().getLanguage();
        this.f4411l = 0;
        this.f4412m = null;
        this.f4413n = null;
        this.f4414o = null;
        this.f4415p = null;
        this.f4416q = null;
        this.f4417r = null;
        this.f4413n = context;
        this.f4402c = C1389k.m4132d(context);
        this.f4400a = C1389k.m4148n(context);
        this.f4407h = StatConfig.getInstallChannel(context);
        this.f4408i = C1389k.m4147m(context);
        this.f4409j = TimeZone.getDefault().getID();
        this.f4411l = C1389k.m4153s(context);
        this.f4410k = C1389k.m4154t(context);
        this.f4412m = context.getPackageName();
        if (this.f4403d >= 14) {
            this.f4414o = C1389k.m4109A(context);
        }
        this.f4415p = C1389k.m4160z(context).toString();
        this.f4416q = C1389k.m4158x(context);
        this.f4417r = C1389k.m4134e();
    }

    void m4092a(JSONObject jSONObject) {
        jSONObject.put("sr", this.f4402c.widthPixels + "*" + this.f4402c.heightPixels);
        C1389k.m4121a(jSONObject, "av", this.f4400a);
        C1389k.m4121a(jSONObject, "ch", this.f4407h);
        C1389k.m4121a(jSONObject, "mf", this.f4405f);
        C1389k.m4121a(jSONObject, "sv", this.f4401b);
        C1389k.m4121a(jSONObject, "ov", Integer.toString(this.f4403d));
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_OS, 1);
        C1389k.m4121a(jSONObject, "op", this.f4408i);
        C1389k.m4121a(jSONObject, "lg", this.f4406g);
        C1389k.m4121a(jSONObject, "md", this.f4404e);
        C1389k.m4121a(jSONObject, "tz", this.f4409j);
        if (this.f4411l != 0) {
            jSONObject.put("jb", this.f4411l);
        }
        C1389k.m4121a(jSONObject, "sd", this.f4410k);
        C1389k.m4121a(jSONObject, "apn", this.f4412m);
        if (C1389k.m4141h(this.f4413n)) {
            JSONObject jSONObject2 = new JSONObject();
            C1389k.m4121a(jSONObject2, "bs", C1389k.m4111C(this.f4413n));
            C1389k.m4121a(jSONObject2, "ss", C1389k.m4112D(this.f4413n));
            if (jSONObject2.length() > 0) {
                C1389k.m4121a(jSONObject, "wf", jSONObject2.toString());
            }
        }
        C1389k.m4121a(jSONObject, "sen", this.f4414o);
        C1389k.m4121a(jSONObject, "cpu", this.f4415p);
        C1389k.m4121a(jSONObject, "ram", this.f4416q);
        C1389k.m4121a(jSONObject, "rom", this.f4417r);
    }
}
