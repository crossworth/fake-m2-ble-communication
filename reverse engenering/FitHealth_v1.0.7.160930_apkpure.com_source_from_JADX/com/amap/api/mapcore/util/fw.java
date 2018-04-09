package com.amap.api.mapcore.util;

import android.text.TextUtils;
import java.net.Proxy;
import java.util.Map;

/* compiled from: Request */
public abstract class fw {
    int f666g = 20000;
    int f667h = 20000;
    Proxy f668i = null;

    public abstract String mo1630a();

    public abstract Map<String, String> mo1631b();

    public abstract Map<String, String> mo1632c();

    String m977f() {
        byte[] a_ = a_();
        if (a_ == null || a_.length == 0) {
            return mo1630a();
        }
        Map b = mo1631b();
        if (b == null) {
            return mo1630a();
        }
        String a = fs.m960a(b);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(mo1630a()).append("?").append(a);
        return stringBuffer.toString();
    }

    byte[] m978g() {
        byte[] a_ = a_();
        if (a_ != null && a_.length != 0) {
            return a_;
        }
        String a = fs.m960a(mo1631b());
        if (TextUtils.isEmpty(a)) {
            return a_;
        }
        return dx.m721a(a);
    }

    public final void m972a(int i) {
        this.f666g = i;
    }

    public final void m975b(int i) {
        this.f667h = i;
    }

    public byte[] a_() {
        return null;
    }

    public final void m973a(Proxy proxy) {
        this.f668i = proxy;
    }
}
