package com.baidu.lbsapi.auth;

import java.util.Hashtable;

class C0322i implements Runnable {
    final /* synthetic */ int f85a;
    final /* synthetic */ boolean f86b;
    final /* synthetic */ String f87c;
    final /* synthetic */ String f88d;
    final /* synthetic */ Hashtable f89e;
    final /* synthetic */ LBSAuthManager f90f;

    C0322i(LBSAuthManager lBSAuthManager, int i, boolean z, String str, String str2, Hashtable hashtable) {
        this.f90f = lBSAuthManager;
        this.f85a = i;
        this.f86b = z;
        this.f87c = str;
        this.f88d = str2;
        this.f89e = hashtable;
    }

    public void run() {
        if (C0311a.f70a) {
            C0311a.m122a("status = " + this.f85a + "; forced = " + this.f86b + "checkAK = " + this.f90f.m116b(this.f87c));
        }
        if (this.f85a == LBSAuthManager.CODE_UNAUTHENTICATE || this.f86b || this.f85a == -1 || this.f90f.m116b(this.f87c)) {
            if (C0311a.f70a) {
                C0311a.m122a("authenticate sendAuthRequest");
            }
            String[] b = C0313b.m130b(LBSAuthManager.f62a);
            if (C0311a.f70a) {
                C0311a.m122a("authStrings.length:" + b.length);
            }
            if (b == null || b.length <= 1) {
                this.f90f.m112a(this.f86b, this.f88d, this.f89e, this.f87c);
                return;
            }
            if (C0311a.f70a) {
                C0311a.m122a("more sha1 auth");
            }
            this.f90f.m113a(this.f86b, this.f88d, this.f89e, b, this.f87c);
        } else if (LBSAuthManager.CODE_AUTHENTICATING == this.f85a) {
            if (C0311a.f70a) {
                C0311a.m122a("authenticate wait  ");
            }
            LBSAuthManager.f63d.m161b();
            this.f90f.m111a(null, this.f87c);
        } else {
            if (C0311a.f70a) {
                C0311a.m122a("authenticate else  ");
            }
            this.f90f.m111a(null, this.f87c);
        }
    }
}
