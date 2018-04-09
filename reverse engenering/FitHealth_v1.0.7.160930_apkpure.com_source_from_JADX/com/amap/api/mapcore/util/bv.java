package com.amap.api.mapcore.util;

@el(a = "update_item")
/* compiled from: DTInfo */
public class bv {
    @em(a = "title", b = 6)
    protected String f290a = null;
    @em(a = "url", b = 6)
    protected String f291b = null;
    @em(a = "mAdcode", b = 6)
    protected String f292c = null;
    @em(a = "fileName", b = 6)
    protected String f293d = null;
    @em(a = "version", b = 6)
    protected String f294e = "";
    @em(a = "lLocalLength", b = 5)
    protected long f295f = 0;
    @em(a = "lRemoteLength", b = 5)
    protected long f296g = 0;
    @em(a = "localPath", b = 6)
    protected String f297h;
    @em(a = "isProvince", b = 2)
    protected int f298i = 0;
    @em(a = "mCompleteCode", b = 2)
    protected int f299j;
    @em(a = "mCityCode", b = 6)
    protected String f300k = "";
    @em(a = "mState", b = 2)
    public int f301l;

    public String m360e() {
        return this.f290a;
    }

    public String m361f() {
        return this.f294e;
    }

    public String m362g() {
        return this.f292c;
    }

    public String m363h() {
        return this.f291b;
    }

    public long m364i() {
        return this.f296g;
    }

    public void m358a(long j) {
        this.f295f = j;
    }

    public void m357a(int i) {
        this.f299j = i;
    }

    public int m365j() {
        return this.f299j;
    }

    public void m359c(String str) {
        this.f300k = str;
    }

    public static String m356d(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mAdcode");
        stringBuilder.append("='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}
