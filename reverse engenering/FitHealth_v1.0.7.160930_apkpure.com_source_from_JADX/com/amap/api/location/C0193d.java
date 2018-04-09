package com.amap.api.location;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import com.amap.api.location.C0182a.C0181a;

/* compiled from: IGPSManager */
public class C0193d {
    public LocationManager f115a = null;
    LocationListener f116b = new C0194e(this);
    private C0181a f117c;
    private C0182a f118d;
    private Context f119e;

    C0193d(Context context, LocationManager locationManager, C0181a c0181a, C0182a c0182a) {
        this.f119e = context;
        this.f115a = locationManager;
        this.f118d = c0182a;
        this.f117c = c0181a;
    }

    void m127a(long j, float f) {
        try {
            Looper mainLooper = this.f119e.getMainLooper();
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            this.f115a.requestLocationUpdates("gps", j, f, this.f116b, mainLooper);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    void m126a() {
    }

    void m128b() {
        if (this.f116b != null) {
            this.f115a.removeUpdates(this.f116b);
        }
    }
}
