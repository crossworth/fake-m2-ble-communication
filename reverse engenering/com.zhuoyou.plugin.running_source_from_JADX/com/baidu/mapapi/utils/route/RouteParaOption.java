package com.baidu.mapapi.utils.route;

import com.baidu.mapapi.model.LatLng;

public class RouteParaOption {
    LatLng f1862a;
    LatLng f1863b;
    String f1864c;
    String f1865d;
    String f1866e;
    EBusStrategyType f1867f = EBusStrategyType.bus_recommend_way;

    public enum EBusStrategyType {
        bus_time_first,
        bus_transfer_little,
        bus_walk_little,
        bus_no_subway,
        bus_recommend_way
    }

    public RouteParaOption busStrategyType(EBusStrategyType eBusStrategyType) {
        this.f1867f = eBusStrategyType;
        return this;
    }

    public RouteParaOption cityName(String str) {
        this.f1866e = str;
        return this;
    }

    public RouteParaOption endName(String str) {
        this.f1865d = str;
        return this;
    }

    public RouteParaOption endPoint(LatLng latLng) {
        this.f1863b = latLng;
        return this;
    }

    public EBusStrategyType getBusStrategyType() {
        return this.f1867f;
    }

    public String getCityName() {
        return this.f1866e;
    }

    public String getEndName() {
        return this.f1865d;
    }

    public LatLng getEndPoint() {
        return this.f1863b;
    }

    public String getStartName() {
        return this.f1864c;
    }

    public LatLng getStartPoint() {
        return this.f1862a;
    }

    public RouteParaOption startName(String str) {
        this.f1864c = str;
        return this;
    }

    public RouteParaOption startPoint(LatLng latLng) {
        this.f1862a = latLng;
        return this;
    }
}
