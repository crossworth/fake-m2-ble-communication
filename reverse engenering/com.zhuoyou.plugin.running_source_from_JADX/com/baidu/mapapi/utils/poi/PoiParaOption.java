package com.baidu.mapapi.utils.poi;

import com.baidu.mapapi.model.LatLng;

public class PoiParaOption {
    String f1855a;
    String f1856b;
    LatLng f1857c;
    int f1858d;

    public PoiParaOption center(LatLng latLng) {
        this.f1857c = latLng;
        return this;
    }

    public LatLng getCenter() {
        return this.f1857c;
    }

    public String getKey() {
        return this.f1856b;
    }

    public int getRadius() {
        return this.f1858d;
    }

    public String getUid() {
        return this.f1855a;
    }

    public PoiParaOption key(String str) {
        this.f1856b = str;
        return this;
    }

    public PoiParaOption radius(int i) {
        this.f1858d = i;
        return this;
    }

    public PoiParaOption uid(String str) {
        this.f1855a = str;
        return this;
    }
}
