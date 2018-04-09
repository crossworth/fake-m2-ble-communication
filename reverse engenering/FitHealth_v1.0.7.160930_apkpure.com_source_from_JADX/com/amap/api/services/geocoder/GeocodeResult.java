package com.amap.api.services.geocoder;

import java.util.ArrayList;
import java.util.List;

public class GeocodeResult {
    private GeocodeQuery f1171a;
    private List<GeocodeAddress> f1172b = new ArrayList();

    public GeocodeResult(GeocodeQuery geocodeQuery, List<GeocodeAddress> list) {
        this.f1171a = geocodeQuery;
        this.f1172b = list;
    }

    public GeocodeQuery getGeocodeQuery() {
        return this.f1171a;
    }

    public void setGeocodeQuery(GeocodeQuery geocodeQuery) {
        this.f1171a = geocodeQuery;
    }

    public List<GeocodeAddress> getGeocodeAddressList() {
        return this.f1172b;
    }

    public void setGeocodeAddressList(List<GeocodeAddress> list) {
        this.f1172b = list;
    }
}
