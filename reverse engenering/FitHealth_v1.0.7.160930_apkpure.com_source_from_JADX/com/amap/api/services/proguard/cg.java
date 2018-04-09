package com.amap.api.services.proguard;

import java.util.HashMap;
import java.util.Map;

@bo(a = "file")
/* compiled from: DynamicSDKFile */
public class cg {
    @bp(a = "filename", b = 6)
    private String f1454a;
    @bp(a = "md5", b = 6)
    private String f1455b;
    @bp(a = "sdkname", b = 6)
    private String f1456c;
    @bp(a = "version", b = 6)
    private String f1457d;
    @bp(a = "dynamicversion", b = 6)
    private String f1458e;
    @bp(a = "status", b = 6)
    private String f1459f;

    /* compiled from: DynamicSDKFile */
    public static class C0375a {
        private String f1448a;
        private String f1449b;
        private String f1450c;
        private String f1451d;
        private String f1452e;
        private String f1453f = "copy";

        public C0375a(String str, String str2, String str3, String str4, String str5) {
            this.f1448a = str;
            this.f1449b = str2;
            this.f1450c = str3;
            this.f1451d = str4;
            this.f1452e = str5;
        }

        public C0375a m1469a(String str) {
            this.f1453f = str;
            return this;
        }

        public cg m1470a() {
            return new cg();
        }
    }

    private cg(C0375a c0375a) {
        this.f1454a = c0375a.f1448a;
        this.f1455b = c0375a.f1449b;
        this.f1456c = c0375a.f1450c;
        this.f1457d = c0375a.f1451d;
        this.f1458e = c0375a.f1452e;
        this.f1459f = c0375a.f1453f;
    }

    private cg() {
    }

    public static String m1472a(String str, String str2) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("dynamicversion", str2);
        return bn.m1391a(hashMap);
    }

    public static String m1475b(String str, String str2) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("status", str2);
        return bn.m1391a(hashMap);
    }

    public static String m1471a(String str) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        return bn.m1391a(hashMap);
    }

    public static String m1474b(String str) {
        Map hashMap = new HashMap();
        hashMap.put("filename", str);
        return bn.m1391a(hashMap);
    }

    public static String m1473a(String str, String str2, String str3, String str4) {
        Map hashMap = new HashMap();
        hashMap.put("filename", str);
        hashMap.put("sdkname", str2);
        hashMap.put("dynamicversion", str4);
        hashMap.put("version", str3);
        return bn.m1391a(hashMap);
    }

    public String m1476a() {
        return this.f1454a;
    }

    public String m1477b() {
        return this.f1455b;
    }

    public String m1478c() {
        return this.f1457d;
    }

    public String m1480d() {
        return this.f1458e;
    }

    public String m1481e() {
        return this.f1459f;
    }

    public void m1479c(String str) {
        this.f1459f = str;
    }
}
