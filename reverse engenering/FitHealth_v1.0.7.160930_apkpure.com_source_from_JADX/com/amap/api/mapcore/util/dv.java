package com.amap.api.mapcore.util;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

@el(a = "a")
/* compiled from: SDKInfo */
public class dv {
    @em(a = "a1", b = 6)
    private String f520a;
    @em(a = "a2", b = 6)
    private String f521b;
    @em(a = "a6", b = 2)
    private int f522c;
    @em(a = "a3", b = 6)
    private String f523d;
    @em(a = "a4", b = 6)
    private String f524e;
    @em(a = "a5", b = 6)
    private String f525f;
    private String f526g;
    private String f527h;
    private String f528i;
    private String f529j;
    private String[] f530k;

    /* compiled from: SDKInfo */
    public static class C0248a {
        private String f514a;
        private String f515b;
        private String f516c;
        private boolean f517d = true;
        private String f518e = "standard";
        private String[] f519f = null;

        public C0248a(String str, String str2, String str3) {
            this.f514a = str2;
            this.f516c = str3;
            this.f515b = str;
        }

        public C0248a m700a(String[] strArr) {
            this.f519f = (String[]) strArr.clone();
            return this;
        }

        public dv m701a() throws dk {
            if (this.f519f != null) {
                return new dv();
            }
            throw new dk("sdk packages is null");
        }
    }

    private dv() {
        this.f522c = 1;
        this.f530k = null;
    }

    private dv(C0248a c0248a) {
        int i = 1;
        this.f522c = 1;
        this.f530k = null;
        this.f526g = c0248a.f514a;
        this.f528i = c0248a.f515b;
        this.f527h = c0248a.f516c;
        if (!c0248a.f517d) {
            i = 0;
        }
        this.f522c = i;
        this.f529j = c0248a.f518e;
        this.f530k = c0248a.f519f;
        this.f521b = dx.m722b(this.f526g);
        this.f520a = dx.m722b(this.f528i);
        this.f523d = dx.m722b(this.f527h);
        this.f524e = dx.m722b(m703a(this.f530k));
        this.f525f = dx.m722b(this.f529j);
    }

    public void m707a(boolean z) {
        this.f522c = z ? 1 : 0;
    }

    public String m706a() {
        if (TextUtils.isEmpty(this.f528i) && !TextUtils.isEmpty(this.f520a)) {
            this.f528i = dx.m725c(this.f520a);
        }
        return this.f528i;
    }

    public String m708b() {
        if (TextUtils.isEmpty(this.f526g) && !TextUtils.isEmpty(this.f521b)) {
            this.f526g = dx.m725c(this.f521b);
        }
        return this.f526g;
    }

    public String m709c() {
        if (TextUtils.isEmpty(this.f527h) && !TextUtils.isEmpty(this.f523d)) {
            this.f527h = dx.m725c(this.f523d);
        }
        return this.f527h;
    }

    public String m710d() {
        if (TextUtils.isEmpty(this.f529j) && !TextUtils.isEmpty(this.f525f)) {
            this.f529j = dx.m725c(this.f525f);
        }
        if (TextUtils.isEmpty(this.f529j)) {
            this.f529j = "standard";
        }
        return this.f529j;
    }

    public String[] m711e() {
        if ((this.f530k == null || this.f530k.length == 0) && !TextUtils.isEmpty(this.f524e)) {
            this.f530k = m704b(dx.m725c(this.f524e));
        }
        return (String[]) this.f530k.clone();
    }

    private String[] m704b(String str) {
        try {
            return str.split(";");
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private String m703a(String[] strArr) {
        String str = null;
        if (strArr != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (String append : strArr) {
                    stringBuilder.append(append).append(";");
                }
                str = stringBuilder.toString();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return str;
    }

    public static String m702a(String str) {
        Map hashMap = new HashMap();
        hashMap.put("a1", dx.m722b(str));
        return ek.m793a(hashMap);
    }

    public static String m705f() {
        return "a6=1";
    }
}
