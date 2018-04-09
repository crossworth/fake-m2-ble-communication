package com.umeng.socialize.editorpage.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/* compiled from: SocializeLocationListener */
public class C0960c implements LocationListener {
    private C1811a f3306a;

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onLocationChanged(Location location) {
        if (this.f3306a != null) {
            this.f3306a.m4994a(location);
            this.f3306a.m4998c().m3224a((LocationListener) this);
        }
    }

    public void m3219a(C1811a c1811a) {
        this.f3306a = c1811a;
    }
}
