package com.amap.api.location;

import android.location.Location;
import com.amap.api.location.core.AMapLocException;

public class AMapLocation extends Location {
    private String f29a;
    private String f30b;
    private String f31c;
    private String f32d;
    private String f33e;
    private String f34f;
    private String f35g;
    private String f36h;
    private String f37i;
    private AMapLocException f38j = new AMapLocException();

    public AMapLocException getAMapException() {
        return this.f38j;
    }

    public void setAMapException(AMapLocException aMapLocException) {
        this.f38j = aMapLocException;
    }

    void m29a(String str) {
        this.f36h = str;
    }

    void m30b(String str) {
        this.f37i = str;
    }

    public String getPoiId() {
        return this.f34f;
    }

    public void setPoiId(String str) {
        this.f34f = str;
    }

    public String getFloor() {
        return this.f35g;
    }

    public void setFloor(String str) {
        this.f35g = str;
    }

    public AMapLocation(String str) {
        super(str);
    }

    public AMapLocation(Location location) {
        super(location);
    }

    public String getProvince() {
        return this.f29a;
    }

    public void setProvince(String str) {
        this.f29a = str;
    }

    public String getCity() {
        return this.f30b;
    }

    public void setCity(String str) {
        this.f30b = str;
    }

    public String getDistrict() {
        return this.f31c;
    }

    public void setDistrict(String str) {
        this.f31c = str;
    }

    public String getCityCode() {
        return this.f32d;
    }

    public void setCityCode(String str) {
        this.f32d = str;
    }

    public String getAdCode() {
        return this.f33e;
    }

    public void setAdCode(String str) {
        this.f33e = str;
    }

    public String getAddress() {
        return this.f37i;
    }

    public String getStreet() {
        return this.f36h;
    }
}
