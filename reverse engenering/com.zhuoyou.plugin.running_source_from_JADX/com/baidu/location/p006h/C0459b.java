package com.baidu.location.p006h;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.baidu.android.bbalbs.common.util.C0309b;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.C0455f;
import com.baidu.location.p005a.C0352j;
import com.tencent.connect.common.Constants;
import java.util.Locale;

public class C0459b {
    public static String f835d = null;
    public static String f836e = null;
    public static String f837f = null;
    public static String f838g = null;
    private static C0459b f839h = null;
    public String f840a = null;
    public String f841b = null;
    public String f842c = null;
    private boolean f843i = false;

    private C0459b() {
        if (C0455f.getServiceContext() != null) {
            m983a(C0455f.getServiceContext());
        }
    }

    public static C0459b m980a() {
        if (f839h == null) {
            f839h = new C0459b();
        }
        return f839h;
    }

    public String m981a(boolean z) {
        return m982a(z, null);
    }

    public String m982a(boolean z, String str) {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.01f);
        if (z) {
            if (C0468j.f904f.equals("all")) {
                stringBuffer.append("&addr=all");
            }
            if (C0468j.f905g || C0468j.f907i || C0468j.f908j || C0468j.f906h) {
                stringBuffer.append("&sema=");
                if (C0468j.f905g) {
                    stringBuffer.append("aptag|");
                }
                if (C0468j.f906h) {
                    stringBuffer.append("aptagd|");
                }
                if (C0468j.f907i) {
                    stringBuffer.append("poiregion|");
                }
                if (C0468j.f908j) {
                    stringBuffer.append("regular");
                }
            }
        }
        if (z) {
            if (str == null) {
                stringBuffer.append("&coor=gcj02");
            } else {
                stringBuffer.append("&coor=");
                stringBuffer.append(str);
            }
        }
        if (this.f841b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.f840a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.f841b);
            if (!(this.f840a == null || this.f840a.equals("NULL") || this.f841b.contains(new StringBuffer(this.f840a).reverse().toString()))) {
                stringBuffer.append("&Aim=");
                stringBuffer.append(this.f840a);
            }
        }
        if (this.f842c != null) {
            stringBuffer.append("&Aid=");
            stringBuffer.append(this.f842c);
        }
        stringBuffer.append("&fw=");
        stringBuffer.append(C0455f.getFrameVersion());
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        String b = C0468j.m1020b();
        if (b != null) {
            stringBuffer.append("&laip=");
            stringBuffer.append(b);
        }
        if (C0352j.m308a().m316e() != 0.0f) {
            stringBuffer.append("&altv=");
            stringBuffer.append(String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(r0)}));
        }
        stringBuffer.append("&resid=");
        stringBuffer.append(Constants.VIA_REPORT_TYPE_SET_AVATAR);
        stringBuffer.append("&os=A");
        stringBuffer.append(VERSION.SDK);
        if (z) {
            stringBuffer.append("&sv=");
            b = VERSION.RELEASE;
            if (b != null && b.length() > 6) {
                b = b.substring(0, 6);
            }
            stringBuffer.append(b);
        }
        return stringBuffer.toString();
    }

    public void m983a(Context context) {
        if (context != null && !this.f843i) {
            try {
                this.f840a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception e) {
                this.f840a = "NULL";
            }
            try {
                this.f841b = CommonParam.m69a(context);
            } catch (Exception e2) {
                this.f841b = null;
            }
            try {
                this.f842c = C0309b.m86b(context);
            } catch (Exception e3) {
                this.f842c = null;
            }
            try {
                f835d = context.getPackageName();
            } catch (Exception e4) {
                f835d = null;
            }
            this.f843i = true;
        }
    }

    public void m984a(String str, String str2) {
        f836e = str;
        f835d = str2;
    }

    public String m985b() {
        return this.f841b != null ? "v7.01|" + this.f841b + "|" + Build.MODEL : "v7.01|" + this.f840a + "|" + Build.MODEL;
    }

    public String m986c() {
        return f835d != null ? m985b() + "|" + f835d : m985b();
    }

    public String m987d() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.f841b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.f840a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.f841b);
        }
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&os=A");
        stringBuffer.append(VERSION.SDK);
        stringBuffer.append("&prod=");
        stringBuffer.append(f836e + ":" + f835d);
        stringBuffer.append(C0468j.m1027e(C0455f.getServiceContext()));
        stringBuffer.append("&resid=");
        stringBuffer.append(Constants.VIA_REPORT_TYPE_SET_AVATAR);
        return stringBuffer.toString();
    }
}
