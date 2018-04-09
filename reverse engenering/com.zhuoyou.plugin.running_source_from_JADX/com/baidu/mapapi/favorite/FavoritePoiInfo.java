package com.baidu.mapapi.favorite;

import com.baidu.mapapi.model.LatLng;

public class FavoritePoiInfo {
    String f955a;
    String f956b;
    LatLng f957c;
    String f958d;
    String f959e;
    String f960f;
    long f961g;

    public FavoritePoiInfo addr(String str) {
        this.f958d = str;
        return this;
    }

    public FavoritePoiInfo cityName(String str) {
        this.f959e = str;
        return this;
    }

    public String getAddr() {
        return this.f958d;
    }

    public String getCityName() {
        return this.f959e;
    }

    public String getID() {
        return this.f955a;
    }

    public String getPoiName() {
        return this.f956b;
    }

    public LatLng getPt() {
        return this.f957c;
    }

    public long getTimeStamp() {
        return this.f961g;
    }

    public String getUid() {
        return this.f960f;
    }

    public FavoritePoiInfo poiName(String str) {
        this.f956b = str;
        return this;
    }

    public FavoritePoiInfo pt(LatLng latLng) {
        this.f957c = latLng;
        return this;
    }

    public FavoritePoiInfo uid(String str) {
        this.f960f = str;
        return this;
    }
}
