package com.amap.api.services.proguard;

import java.util.HashMap;
import java.util.Map;

/* compiled from: LogInfo */
public abstract class bw {
    @bp(a = "b2", b = 2)
    protected int f1425a = -1;
    @bp(a = "b1", b = 6)
    protected String f1426b;
    @bp(a = "b3", b = 2)
    protected int f1427c = 1;
    @bp(a = "a1", b = 6)
    private String f1428d;

    public int m1418a() {
        return this.f1425a;
    }

    public void m1419a(int i) {
        this.f1425a = i;
    }

    public String m1421b() {
        return this.f1426b;
    }

    public void m1420a(String str) {
        this.f1426b = str;
    }

    public void m1423b(String str) {
        this.f1428d = bb.m1322b(str);
    }

    public int m1424c() {
        return this.f1427c;
    }

    public void m1422b(int i) {
        this.f1427c = i;
    }

    public static String m1417c(String str) {
        Map hashMap = new HashMap();
        hashMap.put("b1", str);
        return bn.m1391a(hashMap);
    }

    public static String m1416c(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("b2").append("=").append(i);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
