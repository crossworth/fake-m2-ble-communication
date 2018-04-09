package com.baidu.lbsapi.auth;

class C0316d implements Runnable {
    final /* synthetic */ C0315c f75a;

    C0316d(C0315c c0315c) {
        this.f75a = c0315c;
    }

    public void run() {
        C0311a.m122a("postWithHttps start Thread id = " + String.valueOf(Thread.currentThread().getId()));
        this.f75a.m139a(new C0320g(this.f75a.f72a).m154a(this.f75a.f73b));
    }
}
