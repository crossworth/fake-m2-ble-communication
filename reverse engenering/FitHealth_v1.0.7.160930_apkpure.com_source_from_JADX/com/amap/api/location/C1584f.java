package com.amap.api.location;

import android.location.Location;
import android.os.Bundle;

/* compiled from: LocationListenerProxy */
public class C1584f implements AMapLocationListener {
    private LocationManagerProxy f3936a;
    private AMapLocationListener f3937b = null;

    public C1584f(LocationManagerProxy locationManagerProxy) {
        this.f3936a = locationManagerProxy;
    }

    public boolean m3968a(AMapLocationListener aMapLocationListener, long j, float f, String str) {
        this.f3937b = aMapLocationListener;
        if (!LocationProviderProxy.AMapNetwork.equals(str)) {
            return false;
        }
        this.f3936a.requestLocationUpdates(str, j, f, (AMapLocationListener) this);
        return true;
    }

    public void m3967a() {
        if (this.f3936a != null) {
            this.f3936a.removeUpdates((AMapLocationListener) this);
        }
        this.f3937b = null;
    }

    public void onLocationChanged(Location location) {
        if (this.f3937b != null) {
            this.f3937b.onLocationChanged(location);
        }
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        if (this.f3937b != null) {
            this.f3937b.onStatusChanged(str, i, bundle);
        }
    }

    public void onProviderEnabled(String str) {
        if (this.f3937b != null) {
            this.f3937b.onProviderEnabled(str);
        }
    }

    public void onProviderDisabled(String str) {
        if (this.f3937b != null) {
            this.f3937b.onProviderDisabled(str);
        }
    }

    public void onLocationChanged(AMapLocation aMapLocation) {
        if (this.f3937b != null) {
            this.f3937b.onLocationChanged(aMapLocation);
        }
    }
}
