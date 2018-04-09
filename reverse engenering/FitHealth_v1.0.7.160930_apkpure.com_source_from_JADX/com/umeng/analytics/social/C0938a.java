package com.umeng.analytics.social;

/* compiled from: UMException */
public class C0938a extends RuntimeException {
    private static final long f3183b = -4656673116019167471L;
    protected int f3184a = 5000;
    private String f3185c = "";

    public int m3136a() {
        return this.f3184a;
    }

    public C0938a(int i, String str) {
        super(str);
        this.f3184a = i;
        this.f3185c = str;
    }

    public C0938a(String str, Throwable th) {
        super(str, th);
        this.f3185c = str;
    }

    public C0938a(String str) {
        super(str);
        this.f3185c = str;
    }

    public String getMessage() {
        return this.f3185c;
    }
}
