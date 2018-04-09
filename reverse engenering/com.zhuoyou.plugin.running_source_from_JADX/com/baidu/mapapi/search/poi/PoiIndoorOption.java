package com.baidu.mapapi.search.poi;

public class PoiIndoorOption {
    String f1628a;
    String f1629b;
    String f1630c;
    int f1631d = 0;
    int f1632e = 10;

    public PoiIndoorOption poiCurrentPage(int i) {
        this.f1631d = i;
        return this;
    }

    public PoiIndoorOption poiFloor(String str) {
        this.f1630c = str;
        return this;
    }

    public PoiIndoorOption poiIndoorBid(String str) {
        this.f1628a = str;
        return this;
    }

    public PoiIndoorOption poiIndoorWd(String str) {
        this.f1629b = str;
        return this;
    }

    public PoiIndoorOption poiPageSize(int i) {
        this.f1632e = i;
        return this;
    }
}
