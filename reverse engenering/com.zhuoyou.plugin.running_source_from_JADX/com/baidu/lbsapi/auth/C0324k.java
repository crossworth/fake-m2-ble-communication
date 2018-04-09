package com.baidu.lbsapi.auth;

import com.baidu.lbsapi.auth.C0318e.C0317a;

class C0324k implements C0317a<String> {
    final /* synthetic */ String f93a;
    final /* synthetic */ LBSAuthManager f94b;

    C0324k(LBSAuthManager lBSAuthManager, String str) {
        this.f94b = lBSAuthManager;
        this.f93a = str;
    }

    public void m159a(String str) {
        this.f94b.m111a(str, this.f93a);
    }
}
