package p031u.aly;

import java.io.Serializable;

/* compiled from: UMCCSystemBuffer */
public class C1508k implements Serializable {
    private static final long f3837a = 1;
    private String f3838b;
    private long f3839c;
    private long f3840d;
    private String f3841e;

    private C1508k() {
        this.f3838b = null;
        this.f3839c = 0;
        this.f3840d = 0;
        this.f3841e = null;
    }

    public C1508k(String str, long j, long j2) {
        this(str, j, j2, null);
    }

    public C1508k(String str, long j, long j2, String str2) {
        this.f3838b = null;
        this.f3839c = 0;
        this.f3840d = 0;
        this.f3841e = null;
        this.f3838b = str;
        this.f3839c = j;
        this.f3840d = j2;
        this.f3841e = str2;
    }

    public C1508k m3792a() {
        this.f3840d++;
        return this;
    }

    public String m3795b() {
        return this.f3841e;
    }

    public void m3794a(String str) {
        this.f3841e = str;
    }

    public String m3797c() {
        return this.f3838b;
    }

    public void m3796b(String str) {
        this.f3838b = str;
    }

    public long m3798d() {
        return this.f3839c;
    }

    public long m3799e() {
        return this.f3840d;
    }

    public C1508k m3793a(C1508k c1508k) {
        this.f3840d = c1508k.m3799e() + this.f3840d;
        this.f3839c = c1508k.m3798d();
        return this;
    }
}
