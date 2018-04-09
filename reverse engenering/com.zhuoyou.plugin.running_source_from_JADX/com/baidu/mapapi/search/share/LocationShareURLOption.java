package com.baidu.mapapi.search.share;

import com.baidu.mapapi.model.LatLng;

public class LocationShareURLOption {
    LatLng f1796a = null;
    String f1797b = null;
    String f1798c = null;

    public LocationShareURLOption location(LatLng latLng) {
        this.f1796a = latLng;
        return this;
    }

    public LocationShareURLOption name(String str) {
        this.f1797b = str;
        return this;
    }

    public LocationShareURLOption snippet(String str) {
        this.f1798c = str;
        return this;
    }
}
