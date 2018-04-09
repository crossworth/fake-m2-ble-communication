package com.baidu.mapapi.search.poi;

import com.baidu.mapapi.model.LatLngBounds;

public class PoiBoundSearchOption {
    LatLngBounds f1591a = null;
    String f1592b = null;
    float f1593c = 12.0f;
    int f1594d = 0;
    int f1595e = 10;

    public PoiBoundSearchOption bound(LatLngBounds latLngBounds) {
        this.f1591a = latLngBounds;
        return this;
    }

    public PoiBoundSearchOption keyword(String str) {
        this.f1592b = str;
        return this;
    }

    public PoiBoundSearchOption pageCapacity(int i) {
        this.f1595e = i;
        return this;
    }

    public PoiBoundSearchOption pageNum(int i) {
        this.f1594d = i;
        return this;
    }
}
