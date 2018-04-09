package com.tencent.p004a.p005a;

/* compiled from: ProGuard */
public class C1711d extends C0671c {
    public static C1711d f4506d = null;

    public static C1711d m4641f() {
        if (f4506d == null) {
            synchronized (C1711d.class) {
                if (f4506d == null) {
                    f4506d = new C1711d();
                }
            }
        }
        return f4506d;
    }

    public static final void m4636a(String str, String str2) {
        C1711d.m4641f().m2245a(1, str, str2, null);
    }

    public static final void m4638b(String str, String str2) {
        C1711d.m4641f().m2245a(2, str, str2, null);
    }

    public static final void m4639c(String str, String str2) {
        C1711d.m4641f().m2245a(4, str, str2, null);
    }

    public static final void m4640d(String str, String str2) {
        C1711d.m4641f().m2245a(16, str, str2, null);
    }

    public static final void m4637a(String str, String str2, Throwable th) {
        C1711d.m4641f().m2245a(16, str, str2, th);
    }

    public C1711d() {
        this.c = new C1710a(a);
    }

    public void mo2081b() {
        synchronized (C1711d.class) {
            if (this.c != null) {
                this.c.m4630a();
                this.c.m4634b();
                this.c = null;
                f4506d = null;
            }
        }
    }
}
