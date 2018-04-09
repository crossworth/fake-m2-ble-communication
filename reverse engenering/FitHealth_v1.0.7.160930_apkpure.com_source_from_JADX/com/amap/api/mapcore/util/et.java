package com.amap.api.mapcore.util;

import java.util.HashMap;
import java.util.Map;

/* compiled from: LogInfo */
public abstract class et {
    @em(a = "b2", b = 2)
    protected int f563a = -1;
    @em(a = "b1", b = 6)
    protected String f564b;
    @em(a = "b3", b = 2)
    protected int f565c = 1;
    @em(a = "a1", b = 6)
    private String f566d;

    public int m821a() {
        return this.f563a;
    }

    public void m822a(int i) {
        this.f563a = i;
    }

    public String m824b() {
        return this.f564b;
    }

    public void m823a(String str) {
        this.f564b = str;
    }

    public void m826b(String str) {
        this.f566d = dx.m722b(str);
    }

    public int m827c() {
        return this.f565c;
    }

    public void m825b(int i) {
        this.f565c = i;
    }

    public static String m820c(String str) {
        Map hashMap = new HashMap();
        hashMap.put("b1", str);
        return ek.m793a(hashMap);
    }

    public static String m819c(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("b2").append("=").append(i);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
