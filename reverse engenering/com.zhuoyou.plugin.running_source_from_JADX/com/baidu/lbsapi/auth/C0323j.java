package com.baidu.lbsapi.auth;

import com.baidu.lbsapi.auth.C0315c.C0314a;

class C0323j implements C0314a<String> {
    final /* synthetic */ String f91a;
    final /* synthetic */ LBSAuthManager f92b;

    C0323j(LBSAuthManager lBSAuthManager, String str) {
        this.f92b = lBSAuthManager;
        this.f91a = str;
    }

    public void m157a(String str) {
        this.f92b.m111a(str, this.f91a);
    }
}
