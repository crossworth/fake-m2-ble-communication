package com.baidu.mapapi.model.inner;

public class GeoPoint {
    private double f1463a;
    private double f1464b;

    public GeoPoint(double d, double d2) {
        this.f1463a = d;
        this.f1464b = d2;
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        boolean z = this.f1463a == ((GeoPoint) obj).f1463a && this.f1464b == ((GeoPoint) obj).f1464b;
        return z;
    }

    public double getLatitudeE6() {
        return this.f1463a;
    }

    public double getLongitudeE6() {
        return this.f1464b;
    }

    public void setLatitudeE6(double d) {
        this.f1463a = d;
    }

    public void setLongitudeE6(double d) {
        this.f1464b = d;
    }

    public String toString() {
        return "GeoPoint: Latitude: " + this.f1463a + ", Longitude: " + this.f1464b;
    }
}
