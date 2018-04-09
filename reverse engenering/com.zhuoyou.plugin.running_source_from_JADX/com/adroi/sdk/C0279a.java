package com.adroi.sdk;

import com.adroi.sdk.NativeAds.C0275a;

class C0279a implements Runnable {
    final /* synthetic */ C0275a f34a;

    C0279a(C0275a c0275a) {
        this.f34a = c0275a;
    }

    public void run() {
        this.f34a.f21b.onAdReady(null);
    }
}
