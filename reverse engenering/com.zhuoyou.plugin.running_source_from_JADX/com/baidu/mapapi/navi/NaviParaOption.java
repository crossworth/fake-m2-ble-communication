package com.baidu.mapapi.navi;

import com.baidu.mapapi.model.LatLng;

public class NaviParaOption {
    LatLng f1472a;
    String f1473b;
    LatLng f1474c;
    String f1475d;

    public NaviParaOption endName(String str) {
        this.f1475d = str;
        return this;
    }

    public NaviParaOption endPoint(LatLng latLng) {
        this.f1474c = latLng;
        return this;
    }

    public String getEndName() {
        return this.f1475d;
    }

    public LatLng getEndPoint() {
        return this.f1474c;
    }

    public String getStartName() {
        return this.f1473b;
    }

    public LatLng getStartPoint() {
        return this.f1472a;
    }

    public NaviParaOption startName(String str) {
        this.f1473b = str;
        return this;
    }

    public NaviParaOption startPoint(LatLng latLng) {
        this.f1472a = latLng;
        return this;
    }
}
