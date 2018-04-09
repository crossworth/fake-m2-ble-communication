package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.interfaces.IGeocodeSearch;
import com.amap.api.services.proguard.al;
import java.util.List;

public final class GeocodeSearchWrapper implements IGeocodeSearch {
    private IGeocodeSearch f4268a;

    public GeocodeSearchWrapper(Context context) {
        this.f4268a = new al(context);
    }

    public RegeocodeAddress getFromLocation(RegeocodeQuery regeocodeQuery) throws AMapException {
        if (this.f4268a != null) {
            return this.f4268a.getFromLocation(regeocodeQuery);
        }
        return null;
    }

    public List<GeocodeAddress> getFromLocationName(GeocodeQuery geocodeQuery) throws AMapException {
        if (this.f4268a != null) {
            return this.f4268a.getFromLocationName(geocodeQuery);
        }
        return null;
    }

    public void setOnGeocodeSearchListener(OnGeocodeSearchListener onGeocodeSearchListener) {
        if (this.f4268a != null) {
            this.f4268a.setOnGeocodeSearchListener(onGeocodeSearchListener);
        }
    }

    public void getFromLocationAsyn(RegeocodeQuery regeocodeQuery) {
        if (this.f4268a != null) {
            this.f4268a.getFromLocationAsyn(regeocodeQuery);
        }
    }

    public void getFromLocationNameAsyn(GeocodeQuery geocodeQuery) {
        if (this.f4268a != null) {
            this.f4268a.getFromLocationNameAsyn(geocodeQuery);
        }
    }
}
