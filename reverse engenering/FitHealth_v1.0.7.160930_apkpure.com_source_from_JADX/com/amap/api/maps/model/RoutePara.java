package com.amap.api.maps.model;

public class RoutePara {
    private int f940a = 0;
    private int f941b = 0;
    private LatLng f942c;
    private LatLng f943d;
    private String f944e;
    private String f945f;

    public int getDrivingRouteStyle() {
        return this.f940a;
    }

    public void setDrivingRouteStyle(int i) {
        if (i >= 0 && i < 9) {
            this.f940a = i;
        }
    }

    public int getTransitRouteStyle() {
        return this.f941b;
    }

    public void setTransitRouteStyle(int i) {
        if (i >= 0 && i < 6) {
            this.f941b = i;
        }
    }

    public LatLng getStartPoint() {
        return this.f942c;
    }

    public void setStartPoint(LatLng latLng) {
        this.f942c = latLng;
    }

    public LatLng getEndPoint() {
        return this.f943d;
    }

    public void setEndPoint(LatLng latLng) {
        this.f943d = latLng;
    }

    public String getEndName() {
        return this.f945f;
    }

    public void setEndName(String str) {
        this.f945f = str;
    }

    public String getStartName() {
        return this.f944e;
    }

    public void setStartName(String str) {
        this.f944e = str;
    }
}
