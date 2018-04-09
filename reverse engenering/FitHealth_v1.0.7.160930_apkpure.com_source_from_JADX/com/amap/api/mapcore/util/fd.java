package com.amap.api.mapcore.util;

import java.util.HashMap;
import java.util.Map;

@el(a = "file")
/* compiled from: DynamicSDKFile */
public class fd {
    @em(a = "filename", b = 6)
    private String f593a;
    @em(a = "md5", b = 6)
    private String f594b;
    @em(a = "sdkname", b = 6)
    private String f595c;
    @em(a = "version", b = 6)
    private String f596d;
    @em(a = "dynamicversion", b = 6)
    private String f597e;
    @em(a = "status", b = 6)
    private String f598f;

    /* compiled from: DynamicSDKFile */
    public static class C0256a {
        private String f587a;
        private String f588b;
        private String f589c;
        private String f590d;
        private String f591e;
        private String f592f = "copy";

        public C0256a(String str, String str2, String str3, String str4, String str5) {
            this.f587a = str;
            this.f588b = str2;
            this.f589c = str3;
            this.f590d = str4;
            this.f591e = str5;
        }

        public C0256a m872a(String str) {
            this.f592f = str;
            return this;
        }

        public fd m873a() {
            return new fd();
        }
    }

    private fd(C0256a c0256a) {
        this.f593a = c0256a.f587a;
        this.f594b = c0256a.f588b;
        this.f595c = c0256a.f589c;
        this.f596d = c0256a.f590d;
        this.f597e = c0256a.f591e;
        this.f598f = c0256a.f592f;
    }

    private fd() {
    }

    public static String m875a(String str, String str2) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("dynamicversion", str2);
        return ek.m793a(hashMap);
    }

    public static String m878b(String str, String str2) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("status", str2);
        return ek.m793a(hashMap);
    }

    public static String m874a(String str) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        return ek.m793a(hashMap);
    }

    public static String m877b(String str) {
        Map hashMap = new HashMap();
        hashMap.put("filename", str);
        return ek.m793a(hashMap);
    }

    public static String m876a(String str, String str2, String str3, String str4) {
        Map hashMap = new HashMap();
        hashMap.put("filename", str);
        hashMap.put("sdkname", str2);
        hashMap.put("dynamicversion", str4);
        hashMap.put("version", str3);
        return ek.m793a(hashMap);
    }

    public String m879a() {
        return this.f593a;
    }

    public String m880b() {
        return this.f594b;
    }

    public String m881c() {
        return this.f596d;
    }

    public String m883d() {
        return this.f597e;
    }

    public String m884e() {
        return this.f598f;
    }

    public void m882c(String str) {
        this.f598f = str;
    }
}
