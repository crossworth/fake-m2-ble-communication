package com.tencent.wxop.stat.p023b;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.C0894c;
import com.tencent.wxop.stat.C0898g;
import com.tencent.wxop.stat.C0908t;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import p031u.aly.au;

final class C0880e {
    int f3027L;
    String f3028M;
    String f3029a;
    int aQ;
    String aR;
    String aS;
    String ab;
    String al;
    String f3030b;
    String bq;
    String br;
    String bs;
    String bt;
    DisplayMetrics cA;
    Context cB;
    private String cC;
    private String cD;
    private String cE;
    private String cF;

    private C0880e(Context context) {
        this.f3030b = "2.0.3";
        this.f3027L = VERSION.SDK_INT;
        this.f3028M = Build.MODEL;
        this.ab = Build.MANUFACTURER;
        this.bq = Locale.getDefault().getLanguage();
        this.aQ = 0;
        this.aR = null;
        this.aS = null;
        this.cB = null;
        this.cC = null;
        this.cD = null;
        this.cE = null;
        this.cF = null;
        this.cB = context.getApplicationContext();
        this.cA = C0885l.m2900x(this.cB);
        this.f3029a = C0885l.m2870D(this.cB);
        this.br = C0894c.m2944e(this.cB);
        this.bs = C0885l.m2869C(this.cB);
        this.bt = TimeZone.getDefault().getID();
        Context context2 = this.cB;
        this.aQ = C0885l.au();
        this.al = C0885l.m2874H(this.cB);
        this.aR = this.cB.getPackageName();
        if (this.f3027L >= 14) {
            this.cC = C0885l.m2880M(this.cB);
        }
        context2 = this.cB;
        this.cD = C0885l.az().toString();
        this.cE = C0885l.m2879L(this.cB);
        this.cF = C0885l.ax();
        this.aS = C0885l.m2885R(this.cB);
    }

    final void m2860a(JSONObject jSONObject, Thread thread) {
        if (thread == null) {
            if (this.cA != null) {
                jSONObject.put("sr", this.cA.widthPixels + "*" + this.cA.heightPixels);
                jSONObject.put("dpi", this.cA.xdpi + "*" + this.cA.ydpi);
            }
            if (C0898g.m3012r(this.cB).m3016W()) {
                JSONObject jSONObject2 = new JSONObject();
                C0891r.m2918a(jSONObject2, "bs", C0891r.m2914U(this.cB));
                C0891r.m2918a(jSONObject2, "ss", C0891r.m2915V(this.cB));
                if (jSONObject2.length() > 0) {
                    C0891r.m2918a(jSONObject, "wf", jSONObject2.toString());
                }
            }
            JSONArray X = C0891r.m2917X(this.cB);
            if (X != null && X.length() > 0) {
                C0891r.m2918a(jSONObject, "wflist", X.toString());
            }
            C0891r.m2918a(jSONObject, "sen", this.cC);
        } else {
            C0891r.m2918a(jSONObject, "thn", thread.getName());
            C0891r.m2918a(jSONObject, "qq", C0894c.m2945f(this.cB));
            C0891r.m2918a(jSONObject, "cui", C0894c.m2946g(this.cB));
            if (C0885l.m2894e(this.cE) && this.cE.split("/").length == 2) {
                C0891r.m2918a(jSONObject, "fram", this.cE.split("/")[0]);
            }
            if (C0885l.m2894e(this.cF) && this.cF.split("/").length == 2) {
                C0891r.m2918a(jSONObject, "from", this.cF.split("/")[0]);
            }
            if (C0908t.m3043s(this.cB).m3051t(this.cB) != null) {
                jSONObject.put(DeviceInfo.TAG_IMEI, C0908t.m3043s(this.cB).m3051t(this.cB).m2856b());
            }
            C0891r.m2918a(jSONObject, DeviceInfo.TAG_MID, C0894c.m2947h(this.cB));
        }
        C0891r.m2918a(jSONObject, "pcn", C0885l.m2875I(this.cB));
        C0891r.m2918a(jSONObject, "osn", VERSION.RELEASE);
        C0891r.m2918a(jSONObject, "av", this.f3029a);
        C0891r.m2918a(jSONObject, "ch", this.br);
        C0891r.m2918a(jSONObject, "mf", this.ab);
        C0891r.m2918a(jSONObject, "sv", this.f3030b);
        C0891r.m2918a(jSONObject, "osd", Build.DISPLAY);
        C0891r.m2918a(jSONObject, "prod", Build.PRODUCT);
        C0891r.m2918a(jSONObject, "tags", Build.TAGS);
        C0891r.m2918a(jSONObject, "id", Build.ID);
        C0891r.m2918a(jSONObject, "fng", Build.FINGERPRINT);
        C0891r.m2918a(jSONObject, "lch", this.aS);
        C0891r.m2918a(jSONObject, "ov", Integer.toString(this.f3027L));
        jSONObject.put("os", 1);
        C0891r.m2918a(jSONObject, "op", this.bs);
        C0891r.m2918a(jSONObject, "lg", this.bq);
        C0891r.m2918a(jSONObject, "md", this.f3028M);
        C0891r.m2918a(jSONObject, "tz", this.bt);
        if (this.aQ != 0) {
            jSONObject.put("jb", this.aQ);
        }
        C0891r.m2918a(jSONObject, "sd", this.al);
        C0891r.m2918a(jSONObject, "apn", this.aR);
        C0891r.m2918a(jSONObject, au.f3586o, this.cD);
        C0891r.m2918a(jSONObject, "abi", Build.CPU_ABI);
        C0891r.m2918a(jSONObject, "abi2", Build.CPU_ABI2);
        C0891r.m2918a(jSONObject, "ram", this.cE);
        C0891r.m2918a(jSONObject, "rom", this.cF);
    }
}
