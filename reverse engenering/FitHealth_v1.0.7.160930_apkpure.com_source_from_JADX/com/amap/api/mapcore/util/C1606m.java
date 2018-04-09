package com.amap.api.mapcore.util;

import android.location.Location;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;

/* compiled from: AMapOnLocationChangedListener */
class C1606m implements OnLocationChangedListener {
    Location f4223a;
    private IAMapDelegate f4224b;

    C1606m(IAMapDelegate iAMapDelegate) {
        this.f4224b = iAMapDelegate;
    }

    public void onLocationChanged(Location location) {
        this.f4223a = location;
        try {
            if (this.f4224b.isMyLocationEnabled()) {
                this.f4224b.showMyLocationOverlay(location);
            }
        } catch (Throwable e) {
            ee.m4243a(e, "AMapOnLocationChangedListener", "onLocationChanged");
            e.printStackTrace();
        }
    }
}
