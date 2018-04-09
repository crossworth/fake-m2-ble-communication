package com.baidu.location;

import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.baidu.location.p001b.p002a.C0512a;

class ap implements an, C1619j {
    public static String g7 = null;
    public static String g8 = null;
    public static String g9 = null;
    public static String hb = null;
    private static ap he = null;
    String ha = null;
    String hc = null;
    String hd = null;

    ap() {
        try {
            this.hc = ((TelephonyManager) C1976f.getServiceContext().getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            this.hc = "NULL";
        }
        try {
            this.ha = C0512a.m2169if(C1976f.getServiceContext());
        } catch (Exception e2) {
            this.ha = null;
        }
    }

    public static ap bD() {
        if (he == null) {
            he = new ap();
        }
        return he;
    }

    public String bC() {
        return this.ha != null ? "v4.0|" + this.ha + "|" + Build.MODEL : "v4.0" + this.hc + "|" + Build.MODEL;
    }

    public String bE() {
        return hb != null ? bC() + "|" + hb : bC();
    }

    public void m5890int(String str, String str2) {
        g7 = str;
        hb = str2;
    }

    public String m5891try(boolean z) {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&sdk=");
        stringBuffer.append(4.0f);
        if (z && C1974b.ar.equals("all")) {
            stringBuffer.append("&addr=all");
        }
        if (z) {
            stringBuffer.append("&coor=gcj02");
        }
        if (this.ha == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.hc);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.ha);
        }
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&resid=");
        stringBuffer.append("13");
        stringBuffer.append("&os=A");
        stringBuffer.append(VERSION.SDK);
        if (z) {
            stringBuffer.append("&sv=");
            String str = VERSION.RELEASE;
            if (str != null && str.length() > 6) {
                str = str.substring(0, 6);
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }
}
