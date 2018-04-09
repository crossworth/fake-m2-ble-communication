package com.baidu.mapapi.search.geocode;

import com.baidu.mapapi.model.LatLng;

public class ReverseGeoCodeOption {
    LatLng f1585a = null;

    public ReverseGeoCodeOption location(LatLng latLng) {
        this.f1585a = latLng;
        return this;
    }
}
