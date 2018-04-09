package com.amap.api.services.nearby;

import com.amap.api.services.core.LatLonPoint;

public class NearbyInfo {
    private String f1222a;
    private LatLonPoint f1223b;
    private long f1224c;
    private int f1225d;
    private int f1226e;

    public void setUserID(String str) {
        this.f1222a = str;
    }

    public String getUserID() {
        return this.f1222a;
    }

    public LatLonPoint getPoint() {
        return this.f1223b;
    }

    public void setPoint(LatLonPoint latLonPoint) {
        this.f1223b = latLonPoint;
    }

    public void setTimeStamp(long j) {
        this.f1224c = j;
    }

    public long getTimeStamp() {
        return this.f1224c;
    }

    public void setDistance(int i) {
        this.f1225d = i;
    }

    public int getDistance() {
        return this.f1225d;
    }

    public void setDrivingDistance(int i) {
        this.f1226e = i;
    }

    public int getDrivingDistance() {
        return this.f1226e;
    }
}
