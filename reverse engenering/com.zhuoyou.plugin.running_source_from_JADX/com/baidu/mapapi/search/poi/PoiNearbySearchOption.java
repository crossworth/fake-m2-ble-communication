package com.baidu.mapapi.search.poi;

import com.baidu.mapapi.model.LatLng;

public class PoiNearbySearchOption {
    String f1636a = null;
    LatLng f1637b = null;
    int f1638c = -1;
    float f1639d = 12.0f;
    int f1640e = 0;
    int f1641f = 10;
    PoiSortType f1642g = PoiSortType.comprehensive;

    public PoiNearbySearchOption keyword(String str) {
        this.f1636a = str;
        return this;
    }

    public PoiNearbySearchOption location(LatLng latLng) {
        this.f1637b = latLng;
        return this;
    }

    public PoiNearbySearchOption pageCapacity(int i) {
        this.f1641f = i;
        return this;
    }

    public PoiNearbySearchOption pageNum(int i) {
        this.f1640e = i;
        return this;
    }

    public PoiNearbySearchOption radius(int i) {
        this.f1638c = i;
        return this;
    }

    public PoiNearbySearchOption sortType(PoiSortType poiSortType) {
        if (poiSortType != null) {
            this.f1642g = poiSortType;
        }
        return this;
    }
}
