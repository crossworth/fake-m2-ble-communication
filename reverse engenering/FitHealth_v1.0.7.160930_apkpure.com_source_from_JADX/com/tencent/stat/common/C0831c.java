package com.tencent.stat.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.tencent.stat.StatConfig;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;
import p031u.aly.au;

class C0831c {
    String f2880a;
    String f2881b;
    DisplayMetrics f2882c;
    int f2883d;
    String f2884e;
    String f2885f;
    String f2886g;
    String f2887h;
    String f2888i;
    String f2889j;
    String f2890k;
    int f2891l;
    String f2892m;
    Context f2893n;
    private String f2894o;
    private String f2895p;
    private String f2896q;
    private String f2897r;

    private C0831c(Context context) {
        this.f2881b = StatConstants.VERSION;
        this.f2883d = VERSION.SDK_INT;
        this.f2884e = Build.MODEL;
        this.f2885f = Build.MANUFACTURER;
        this.f2886g = Locale.getDefault().getLanguage();
        this.f2891l = 0;
        this.f2892m = null;
        this.f2893n = null;
        this.f2894o = null;
        this.f2895p = null;
        this.f2896q = null;
        this.f2897r = null;
        this.f2893n = context;
        this.f2882c = C0837k.m2725d(context);
        this.f2880a = C0837k.m2741n(context);
        this.f2887h = StatConfig.getInstallChannel(context);
        this.f2888i = C0837k.m2740m(context);
        this.f2889j = TimeZone.getDefault().getID();
        this.f2891l = C0837k.m2746s(context);
        this.f2890k = C0837k.m2747t(context);
        this.f2892m = context.getPackageName();
        if (this.f2883d >= 14) {
            this.f2894o = C0837k.m2702A(context);
        }
        this.f2895p = C0837k.m2753z(context).toString();
        this.f2896q = C0837k.m2751x(context);
        this.f2897r = C0837k.m2727e();
    }

    void m2687a(JSONObject jSONObject) {
        jSONObject.put("sr", this.f2882c.widthPixels + "*" + this.f2882c.heightPixels);
        C0837k.m2714a(jSONObject, "av", this.f2880a);
        C0837k.m2714a(jSONObject, "ch", this.f2887h);
        C0837k.m2714a(jSONObject, "mf", this.f2885f);
        C0837k.m2714a(jSONObject, "sv", this.f2881b);
        C0837k.m2714a(jSONObject, "ov", Integer.toString(this.f2883d));
        jSONObject.put("os", 1);
        C0837k.m2714a(jSONObject, "op", this.f2888i);
        C0837k.m2714a(jSONObject, "lg", this.f2886g);
        C0837k.m2714a(jSONObject, "md", this.f2884e);
        C0837k.m2714a(jSONObject, "tz", this.f2889j);
        if (this.f2891l != 0) {
            jSONObject.put("jb", this.f2891l);
        }
        C0837k.m2714a(jSONObject, "sd", this.f2890k);
        C0837k.m2714a(jSONObject, "apn", this.f2892m);
        if (C0837k.m2734h(this.f2893n)) {
            JSONObject jSONObject2 = new JSONObject();
            C0837k.m2714a(jSONObject2, "bs", C0837k.m2704C(this.f2893n));
            C0837k.m2714a(jSONObject2, "ss", C0837k.m2705D(this.f2893n));
            if (jSONObject2.length() > 0) {
                C0837k.m2714a(jSONObject, "wf", jSONObject2.toString());
            }
        }
        C0837k.m2714a(jSONObject, "sen", this.f2894o);
        C0837k.m2714a(jSONObject, au.f3586o, this.f2895p);
        C0837k.m2714a(jSONObject, "ram", this.f2896q);
        C0837k.m2714a(jSONObject, "rom", this.f2897r);
    }
}
