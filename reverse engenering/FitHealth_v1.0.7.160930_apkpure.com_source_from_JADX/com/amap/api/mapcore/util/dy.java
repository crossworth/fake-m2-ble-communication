package com.amap.api.mapcore.util;

import java.util.HashMap;
import java.util.Map;

@Deprecated
/* compiled from: AuthRequest */
class dy extends fw {
    private Map<String, String> f4181a = new HashMap();
    private String f4182b;
    private Map<String, String> f4183c = new HashMap();

    dy() {
    }

    void m4231a(Map<String, String> map) {
        this.f4181a.clear();
        this.f4181a.putAll(map);
    }

    void m4230a(String str) {
        this.f4182b = str;
    }

    void m4233b(Map<String, String> map) {
        this.f4183c.clear();
        this.f4183c.putAll(map);
    }

    public String mo1630a() {
        return this.f4182b;
    }

    public Map<String, String> mo1632c() {
        return this.f4181a;
    }

    public Map<String, String> mo1631b() {
        return this.f4183c;
    }
}
