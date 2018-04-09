package com.baidu.mapapi.search.geocode;

public class GeoCodeOption {
    String f1575a = null;
    String f1576b = null;

    public GeoCodeOption address(String str) {
        this.f1576b = str;
        return this;
    }

    public GeoCodeOption city(String str) {
        this.f1575a = str;
        return this;
    }
}
