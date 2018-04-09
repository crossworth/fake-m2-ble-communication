package com.baidu.mapapi.search.route;

import com.baidu.mapapi.model.LatLng;

public class IndoorPlanNode {
    private LatLng f1699a = null;
    private String f1700b = null;

    public IndoorPlanNode(LatLng latLng, String str) {
        this.f1699a = latLng;
        this.f1700b = str;
    }

    LatLng m1591a() {
        return this.f1699a;
    }

    String m1592b() {
        return this.f1700b;
    }
}
