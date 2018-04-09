package com.umeng.socialize.editorpage.location;

import android.location.LocationListener;

/* compiled from: SocializeLocationManager */
class C0962e implements Runnable {
    final /* synthetic */ String f3308a;
    final /* synthetic */ long f3309b;
    final /* synthetic */ float f3310c;
    final /* synthetic */ LocationListener f3311d;
    final /* synthetic */ C0961d f3312e;

    C0962e(C0961d c0961d, String str, long j, float f, LocationListener locationListener) {
        this.f3312e = c0961d;
        this.f3308a = str;
        this.f3309b = j;
        this.f3310c = f;
        this.f3311d = locationListener;
    }

    public void run() {
        this.f3312e.f3307a.requestLocationUpdates(this.f3308a, this.f3309b, this.f3310c, this.f3311d);
    }
}
