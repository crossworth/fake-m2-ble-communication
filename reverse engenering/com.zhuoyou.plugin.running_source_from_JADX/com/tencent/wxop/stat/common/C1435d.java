package com.tencent.wxop.stat.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.C1428a;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.au;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

class C1435d {
    String f4748a;
    String f4749b;
    DisplayMetrics f4750c;
    int f4751d;
    String f4752e;
    String f4753f;
    String f4754g;
    String f4755h;
    String f4756i;
    String f4757j;
    String f4758k;
    int f4759l;
    String f4760m;
    String f4761n;
    Context f4762o;
    private String f4763p;
    private String f4764q;
    private String f4765r;
    private String f4766s;

    private C1435d(Context context) {
        this.f4749b = StatConstants.VERSION;
        this.f4751d = VERSION.SDK_INT;
        this.f4752e = Build.MODEL;
        this.f4753f = Build.MANUFACTURER;
        this.f4754g = Locale.getDefault().getLanguage();
        this.f4759l = 0;
        this.f4760m = null;
        this.f4761n = null;
        this.f4762o = null;
        this.f4763p = null;
        this.f4764q = null;
        this.f4765r = null;
        this.f4766s = null;
        this.f4762o = context.getApplicationContext();
        this.f4750c = C1442k.m4421d(this.f4762o);
        this.f4748a = C1442k.m4432j(this.f4762o);
        this.f4755h = StatConfig.getInstallChannel(this.f4762o);
        this.f4756i = C1442k.m4431i(this.f4762o);
        this.f4757j = TimeZone.getDefault().getID();
        this.f4759l = C1442k.m4437o(this.f4762o);
        this.f4758k = C1442k.m4438p(this.f4762o);
        this.f4760m = this.f4762o.getPackageName();
        if (this.f4751d >= 14) {
            this.f4763p = C1442k.m4444v(this.f4762o);
        }
        this.f4764q = C1442k.m4443u(this.f4762o).toString();
        this.f4765r = C1442k.m4442t(this.f4762o);
        this.f4766s = C1442k.m4422d();
        this.f4761n = C1442k.m4402C(this.f4762o);
    }

    void m4387a(JSONObject jSONObject, Thread thread) {
        if (thread == null) {
            if (this.f4750c != null) {
                jSONObject.put("sr", this.f4750c.widthPixels + "*" + this.f4750c.heightPixels);
                jSONObject.put("dpi", this.f4750c.xdpi + "*" + this.f4750c.ydpi);
            }
            if (C1428a.m4298a(this.f4762o).m4310e()) {
                JSONObject jSONObject2 = new JSONObject();
                C1448q.m4464a(jSONObject2, "bs", C1448q.m4469d(this.f4762o));
                C1448q.m4464a(jSONObject2, "ss", C1448q.m4470e(this.f4762o));
                if (jSONObject2.length() > 0) {
                    C1448q.m4464a(jSONObject, "wf", jSONObject2.toString());
                }
            }
            JSONArray a = C1448q.m4463a(this.f4762o, 10);
            if (a != null && a.length() > 0) {
                C1448q.m4464a(jSONObject, "wflist", a.toString());
            }
            C1448q.m4464a(jSONObject, "sen", this.f4763p);
        } else {
            C1448q.m4464a(jSONObject, "thn", thread.getName());
            C1448q.m4464a(jSONObject, "qq", StatConfig.getQQ(this.f4762o));
            C1448q.m4464a(jSONObject, "cui", StatConfig.getCustomUserId(this.f4762o));
            if (C1442k.m4420c(this.f4765r) && this.f4765r.split("/").length == 2) {
                C1448q.m4464a(jSONObject, "fram", this.f4765r.split("/")[0]);
            }
            if (C1442k.m4420c(this.f4766s) && this.f4766s.split("/").length == 2) {
                C1448q.m4464a(jSONObject, "from", this.f4766s.split("/")[0]);
            }
            if (au.m4332a(this.f4762o).m4365b(this.f4762o) != null) {
                jSONObject.put(DeviceInfo.TAG_IMEI, au.m4332a(this.f4762o).m4365b(this.f4762o).m4381b());
            }
            C1448q.m4464a(jSONObject, DeviceInfo.TAG_MID, StatConfig.getLocalMidOnly(this.f4762o));
        }
        C1448q.m4464a(jSONObject, "pcn", C1442k.m4439q(this.f4762o));
        C1448q.m4464a(jSONObject, "osn", VERSION.RELEASE);
        C1448q.m4464a(jSONObject, "av", this.f4748a);
        C1448q.m4464a(jSONObject, "ch", this.f4755h);
        C1448q.m4464a(jSONObject, "mf", this.f4753f);
        C1448q.m4464a(jSONObject, "sv", this.f4749b);
        C1448q.m4464a(jSONObject, "osd", Build.DISPLAY);
        C1448q.m4464a(jSONObject, "prod", Build.PRODUCT);
        C1448q.m4464a(jSONObject, "tags", Build.TAGS);
        C1448q.m4464a(jSONObject, ShareConstants.WEB_DIALOG_PARAM_ID, Build.ID);
        C1448q.m4464a(jSONObject, "fng", Build.FINGERPRINT);
        C1448q.m4464a(jSONObject, "lch", this.f4761n);
        C1448q.m4464a(jSONObject, "ov", Integer.toString(this.f4751d));
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_OS, 1);
        C1448q.m4464a(jSONObject, "op", this.f4756i);
        C1448q.m4464a(jSONObject, "lg", this.f4754g);
        C1448q.m4464a(jSONObject, "md", this.f4752e);
        C1448q.m4464a(jSONObject, "tz", this.f4757j);
        if (this.f4759l != 0) {
            jSONObject.put("jb", this.f4759l);
        }
        C1448q.m4464a(jSONObject, "sd", this.f4758k);
        C1448q.m4464a(jSONObject, "apn", this.f4760m);
        C1448q.m4464a(jSONObject, "cpu", this.f4764q);
        C1448q.m4464a(jSONObject, "abi", Build.CPU_ABI);
        C1448q.m4464a(jSONObject, "abi2", Build.CPU_ABI2);
        C1448q.m4464a(jSONObject, "ram", this.f4765r);
        C1448q.m4464a(jSONObject, "rom", this.f4766s);
    }
}
