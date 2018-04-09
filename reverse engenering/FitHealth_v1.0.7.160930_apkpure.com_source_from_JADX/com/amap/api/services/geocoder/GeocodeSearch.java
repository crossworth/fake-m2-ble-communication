package com.amap.api.services.geocoder;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IGeocodeSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.al;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;
import java.util.List;

public final class GeocodeSearch {
    public static final String AMAP = "autonavi";
    public static final String GPS = "gps";
    private IGeocodeSearch f1173a;

    public interface OnGeocodeSearchListener {
        void onGeocodeSearched(GeocodeResult geocodeResult, int i);

        void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i);
    }

    public GeocodeSearch(Context context) {
        try {
            Context context2 = context;
            this.f1173a = (IGeocodeSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.GeocodeSearchWrapper", al.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1173a == null) {
            this.f1173a = new al(context);
        }
    }

    public RegeocodeAddress getFromLocation(RegeocodeQuery regeocodeQuery) throws AMapException {
        if (this.f1173a != null) {
            return this.f1173a.getFromLocation(regeocodeQuery);
        }
        return null;
    }

    public List<GeocodeAddress> getFromLocationName(GeocodeQuery geocodeQuery) throws AMapException {
        if (this.f1173a != null) {
            return this.f1173a.getFromLocationName(geocodeQuery);
        }
        return null;
    }

    public void setOnGeocodeSearchListener(OnGeocodeSearchListener onGeocodeSearchListener) {
        if (this.f1173a != null) {
            this.f1173a.setOnGeocodeSearchListener(onGeocodeSearchListener);
        }
    }

    public void getFromLocationAsyn(RegeocodeQuery regeocodeQuery) {
        if (this.f1173a != null) {
            this.f1173a.getFromLocationAsyn(regeocodeQuery);
        }
    }

    public void getFromLocationNameAsyn(GeocodeQuery geocodeQuery) {
        if (this.f1173a != null) {
            this.f1173a.getFromLocationNameAsyn(geocodeQuery);
        }
    }
}
