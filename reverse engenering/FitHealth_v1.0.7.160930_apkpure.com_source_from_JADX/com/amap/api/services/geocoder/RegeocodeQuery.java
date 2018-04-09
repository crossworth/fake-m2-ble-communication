package com.amap.api.services.geocoder;

import com.amap.api.services.core.LatLonPoint;

public class RegeocodeQuery {
    private LatLonPoint f1190a;
    private float f1191b;
    private String f1192c = GeocodeSearch.AMAP;

    public RegeocodeQuery(LatLonPoint latLonPoint, float f, String str) {
        this.f1190a = latLonPoint;
        this.f1191b = f;
        setLatLonType(str);
    }

    public LatLonPoint getPoint() {
        return this.f1190a;
    }

    public void setPoint(LatLonPoint latLonPoint) {
        this.f1190a = latLonPoint;
    }

    public float getRadius() {
        return this.f1191b;
    }

    public void setRadius(float f) {
        this.f1191b = f;
    }

    public String getLatLonType() {
        return this.f1192c;
    }

    public void setLatLonType(String str) {
        if (str == null) {
            return;
        }
        if (str.equals(GeocodeSearch.AMAP) || str.equals("gps")) {
            this.f1192c = str;
        }
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        if (this.f1192c == null) {
            i = 0;
        } else {
            i = this.f1192c.hashCode();
        }
        i = (i + 31) * 31;
        if (this.f1190a != null) {
            i2 = this.f1190a.hashCode();
        }
        return ((i + i2) * 31) + Float.floatToIntBits(this.f1191b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RegeocodeQuery regeocodeQuery = (RegeocodeQuery) obj;
        if (this.f1192c == null) {
            if (regeocodeQuery.f1192c != null) {
                return false;
            }
        } else if (!this.f1192c.equals(regeocodeQuery.f1192c)) {
            return false;
        }
        if (this.f1190a == null) {
            if (regeocodeQuery.f1190a != null) {
                return false;
            }
        } else if (!this.f1190a.equals(regeocodeQuery.f1190a)) {
            return false;
        }
        if (Float.floatToIntBits(this.f1191b) != Float.floatToIntBits(regeocodeQuery.f1191b)) {
            return false;
        }
        return true;
    }
}
