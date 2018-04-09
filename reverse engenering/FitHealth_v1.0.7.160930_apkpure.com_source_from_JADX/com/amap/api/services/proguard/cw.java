package com.amap.api.services.proguard;

import android.text.TextUtils;
import java.net.Proxy;
import java.util.Map;

/* compiled from: Request */
public abstract class cw {
    int f1527e = 20000;
    int f1528f = 20000;
    Proxy f1529g = null;

    public abstract Map<String, String> mo1756b();

    public abstract Map<String, String> mo1757c();

    public abstract String mo1759g();

    String m1573i() {
        byte[] f = mo1758f();
        if (f == null || f.length == 0) {
            return mo1759g();
        }
        Map b = mo1756b();
        if (b == null) {
            return mo1759g();
        }
        String a = ct.m1556a(b);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(mo1759g()).append("?").append(a);
        return stringBuffer.toString();
    }

    byte[] m1574j() {
        byte[] f = mo1758f();
        if (f != null && f.length != 0) {
            return f;
        }
        String a = ct.m1556a(mo1756b());
        if (TextUtils.isEmpty(a)) {
            return f;
        }
        return bb.m1321a(a);
    }

    public final void m1566a(int i) {
        this.f1527e = i;
    }

    public final void m1569b(int i) {
        this.f1528f = i;
    }

    public byte[] mo1758f() {
        return null;
    }

    public final void m1567a(Proxy proxy) {
        this.f1529g = proxy;
    }
}
