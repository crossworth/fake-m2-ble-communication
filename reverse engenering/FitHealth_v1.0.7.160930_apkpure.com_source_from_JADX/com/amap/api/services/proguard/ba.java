package com.amap.api.services.proguard;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

@bo(a = "a")
/* compiled from: SDKInfo */
public class ba {
    @bp(a = "a1", b = 6)
    private String f1383a;
    @bp(a = "a2", b = 6)
    private String f1384b;
    @bp(a = "a6", b = 2)
    private int f1385c;
    @bp(a = "a3", b = 6)
    private String f1386d;
    @bp(a = "a4", b = 6)
    private String f1387e;
    @bp(a = "a5", b = 6)
    private String f1388f;
    private String f1389g;
    private String f1390h;
    private String f1391i;
    private String f1392j;
    private String[] f1393k;

    /* compiled from: SDKInfo */
    public static class C0369a {
        private String f1377a;
        private String f1378b;
        private String f1379c;
        private boolean f1380d = true;
        private String f1381e = "standard";
        private String[] f1382f = null;

        public C0369a(String str, String str2, String str3) {
            this.f1377a = str2;
            this.f1379c = str3;
            this.f1378b = str;
        }

        public C0369a m1301a(boolean z) {
            this.f1380d = z;
            return this;
        }

        public C0369a m1302a(String[] strArr) {
            this.f1382f = (String[]) strArr.clone();
            return this;
        }

        public ba m1303a() throws ar {
            if (this.f1382f != null) {
                return new ba();
            }
            throw new ar("sdk packages is null");
        }
    }

    private ba() {
        this.f1385c = 1;
        this.f1393k = null;
    }

    private ba(C0369a c0369a) {
        int i = 1;
        this.f1385c = 1;
        this.f1393k = null;
        this.f1389g = c0369a.f1377a;
        this.f1391i = c0369a.f1378b;
        this.f1390h = c0369a.f1379c;
        if (!c0369a.f1380d) {
            i = 0;
        }
        this.f1385c = i;
        this.f1392j = c0369a.f1381e;
        this.f1393k = c0369a.f1382f;
        this.f1384b = bb.m1322b(this.f1389g);
        this.f1383a = bb.m1322b(this.f1391i);
        this.f1386d = bb.m1322b(this.f1390h);
        this.f1387e = bb.m1322b(m1305a(this.f1393k));
        this.f1388f = bb.m1322b(this.f1392j);
    }

    public String m1308a() {
        if (TextUtils.isEmpty(this.f1391i) && !TextUtils.isEmpty(this.f1383a)) {
            this.f1391i = bb.m1324c(this.f1383a);
        }
        return this.f1391i;
    }

    public String m1309b() {
        if (TextUtils.isEmpty(this.f1389g) && !TextUtils.isEmpty(this.f1384b)) {
            this.f1389g = bb.m1324c(this.f1384b);
        }
        return this.f1389g;
    }

    public String m1310c() {
        if (TextUtils.isEmpty(this.f1392j) && !TextUtils.isEmpty(this.f1388f)) {
            this.f1392j = bb.m1324c(this.f1388f);
        }
        if (TextUtils.isEmpty(this.f1392j)) {
            this.f1392j = "standard";
        }
        return this.f1392j;
    }

    public String[] m1311d() {
        if ((this.f1393k == null || this.f1393k.length == 0) && !TextUtils.isEmpty(this.f1387e)) {
            this.f1393k = m1306b(bb.m1324c(this.f1387e));
        }
        return (String[]) this.f1393k.clone();
    }

    private String[] m1306b(String str) {
        try {
            return str.split(";");
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private String m1305a(String[] strArr) {
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

    public static String m1304a(String str) {
        Map hashMap = new HashMap();
        hashMap.put("a1", bb.m1322b(str));
        return bn.m1391a(hashMap);
    }

    public static String m1307e() {
        return "a6=1";
    }
}
