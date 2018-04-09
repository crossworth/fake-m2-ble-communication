package com.amap.api.services.core;

import com.amap.api.services.proguard.au;

public class ServiceSettings {
    public static final String CHINESE = "zh-CN";
    public static final String ENGLISH = "en";
    public static final int HTTP = 1;
    public static final int HTTPS = 2;
    private static ServiceSettings f1124c;
    private String f1125a = "zh-CN";
    private int f1126b = 1;
    private int f1127d = 20000;
    private int f1128e = 20000;

    public int getConnectionTimeOut() {
        return this.f1127d;
    }

    public int getSoTimeOut() {
        return this.f1128e;
    }

    public void setConnectionTimeOut(int i) {
        if (i < 5000) {
            this.f1127d = 5000;
        } else if (i > 30000) {
            this.f1127d = 30000;
        } else {
            this.f1127d = i;
        }
    }

    public void setSoTimeOut(int i) {
        if (i < 5000) {
            this.f1128e = 5000;
        } else if (i > 30000) {
            this.f1128e = 30000;
        } else {
            this.f1128e = i;
        }
    }

    private ServiceSettings() {
    }

    public static ServiceSettings getInstance() {
        if (f1124c == null) {
            f1124c = new ServiceSettings();
        }
        return f1124c;
    }

    public void setLanguage(String str) {
        if ("en".equals(str) || "zh-CN".equals(str)) {
            this.f1125a = str;
        }
    }

    public void setProtocol(int i) {
        this.f1126b = i;
    }

    public String getLanguage() {
        return this.f1125a;
    }

    public int getProtocol() {
        return this.f1126b;
    }

    public void setApiKey(String str) {
        au.m1222a(str);
    }
}
