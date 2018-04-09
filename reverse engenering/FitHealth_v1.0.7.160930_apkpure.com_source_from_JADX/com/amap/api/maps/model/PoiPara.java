package com.amap.api.maps.model;

public class PoiPara {
    private LatLng f915a;
    private String f916b;

    public LatLng getCenter() {
        return this.f915a;
    }

    public void setCenter(LatLng latLng) {
        this.f915a = latLng;
    }

    public String getKeywords() {
        return this.f916b;
    }

    public void setKeywords(String str) {
        this.f916b = str;
    }
}
