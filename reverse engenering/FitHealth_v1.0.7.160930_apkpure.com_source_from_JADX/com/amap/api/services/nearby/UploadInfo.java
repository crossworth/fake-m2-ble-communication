package com.amap.api.services.nearby;

import com.amap.api.services.core.LatLonPoint;

public class UploadInfo {
    private int f1238a = 1;
    private String f1239b;
    private LatLonPoint f1240c;

    public void setPoint(LatLonPoint latLonPoint) {
        this.f1240c = latLonPoint;
    }

    public LatLonPoint getPoint() {
        return this.f1240c;
    }

    public void setUserID(String str) {
        this.f1239b = str;
    }

    public String getUserID() {
        return this.f1239b;
    }

    public int getCoordType() {
        return this.f1238a;
    }

    public void setCoordType(int i) {
        if (i == 0 || i == 1) {
            this.f1238a = i;
        } else {
            this.f1238a = 1;
        }
    }
}
