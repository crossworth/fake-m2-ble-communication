package com.umeng.analytics.social;

import com.zhuoyou.plugin.bluetooth.data.BMessage;

/* compiled from: UMResult */
public class C0941d {
    private int f3186a = -1;
    private String f3187b = "";
    private String f3188c = "";
    private Exception f3189d = null;

    public C0941d(int i) {
        this.f3186a = i;
    }

    public C0941d(int i, Exception exception) {
        this.f3186a = i;
        this.f3189d = exception;
    }

    public Exception m3150a() {
        return this.f3189d;
    }

    public int m3153b() {
        return this.f3186a;
    }

    public void m3151a(int i) {
        this.f3186a = i;
    }

    public String m3155c() {
        return this.f3187b;
    }

    public void m3152a(String str) {
        this.f3187b = str;
    }

    public String m3156d() {
        return this.f3188c;
    }

    public void m3154b(String str) {
        this.f3188c = str;
    }

    public String toString() {
        return "status=" + this.f3186a + BMessage.CRLF + "msg:  " + this.f3187b + BMessage.CRLF + "data:  " + this.f3188c;
    }
}
