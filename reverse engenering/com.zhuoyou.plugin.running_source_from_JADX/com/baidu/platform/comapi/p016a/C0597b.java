package com.baidu.platform.comapi.p016a;

import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.mapapi.http.HttpClient.ProtoResultCallback;
import com.baidu.platform.comapi.p016a.C0596a.C0592a;

class C0597b extends ProtoResultCallback {
    final /* synthetic */ C0592a f1884a;
    final /* synthetic */ C0596a f1885b;

    C0597b(C0596a c0596a, C0592a c0592a) {
        this.f1885b = c0596a;
        this.f1884a = c0592a;
    }

    public void onFailed(HttpStateError httpStateError) {
        this.f1884a.mo1826a(httpStateError);
    }

    public void onSuccess(String str) {
        this.f1884a.mo1827a(this.f1885b.m1843a(str));
    }
}
