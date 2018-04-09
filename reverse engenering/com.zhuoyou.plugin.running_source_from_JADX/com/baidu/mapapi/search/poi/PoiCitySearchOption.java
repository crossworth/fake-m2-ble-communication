package com.baidu.mapapi.search.poi;

import com.baidu.mapapi.model.LatLng;

public class PoiCitySearchOption {
    String f1596a = null;
    String f1597b = null;
    float f1598c = 12.0f;
    LatLng f1599d = null;
    int f1600e = 0;
    int f1601f = 10;

    public PoiCitySearchOption city(String str) {
        this.f1596a = str;
        return this;
    }

    public PoiCitySearchOption keyword(String str) {
        this.f1597b = str;
        return this;
    }

    public PoiCitySearchOption pageCapacity(int i) {
        this.f1601f = i;
        return this;
    }

    public PoiCitySearchOption pageNum(int i) {
        this.f1600e = i;
        return this;
    }
}
