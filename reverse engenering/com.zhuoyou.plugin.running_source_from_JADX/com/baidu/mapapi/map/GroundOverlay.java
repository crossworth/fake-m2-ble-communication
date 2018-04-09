package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;

public final class GroundOverlay extends Overlay {
    private static final String f1084j = GroundOverlay.class.getSimpleName();
    int f1085a;
    BitmapDescriptor f1086b;
    LatLng f1087c;
    double f1088d;
    double f1089e;
    float f1090f;
    float f1091g;
    LatLngBounds f1092h;
    float f1093i;

    GroundOverlay() {
        this.q = C0636h.ground;
    }

    Bundle mo1759a(Bundle bundle) {
        super.mo1759a(bundle);
        bundle.putBundle("image_info", this.f1086b.m1105b());
        if (this.f1085a == 1) {
            GeoPoint ll2mc = CoordUtil.ll2mc(this.f1092h.southwest);
            double longitudeE6 = ll2mc.getLongitudeE6();
            double latitudeE6 = ll2mc.getLatitudeE6();
            GeoPoint ll2mc2 = CoordUtil.ll2mc(this.f1092h.northeast);
            double longitudeE62 = ll2mc2.getLongitudeE6();
            double latitudeE62 = ll2mc2.getLatitudeE6();
            this.f1088d = longitudeE62 - longitudeE6;
            this.f1089e = latitudeE62 - latitudeE6;
            this.f1087c = CoordUtil.mc2ll(new GeoPoint(latitudeE6 + (this.f1089e / 2.0d), longitudeE6 + (this.f1088d / 2.0d)));
            this.f1090f = 0.5f;
            this.f1091g = 0.5f;
        }
        if (this.f1088d <= 0.0d || this.f1089e <= 0.0d) {
            throw new IllegalStateException("when you add ground overlay, the width and height must greater than 0");
        }
        bundle.putDouble("x_distance", this.f1088d);
        if (this.f1089e == 2.147483647E9d) {
            this.f1089e = (double) ((int) ((this.f1088d * ((double) this.f1086b.f1053a.getHeight())) / ((double) ((float) this.f1086b.f1053a.getWidth()))));
        }
        bundle.putDouble("y_distance", this.f1089e);
        ll2mc = CoordUtil.ll2mc(this.f1087c);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putFloat("anchor_x", this.f1090f);
        bundle.putFloat("anchor_y", this.f1091g);
        bundle.putFloat("transparency", this.f1093i);
        return bundle;
    }

    public float getAnchorX() {
        return this.f1090f;
    }

    public float getAnchorY() {
        return this.f1091g;
    }

    public LatLngBounds getBounds() {
        return this.f1092h;
    }

    public double getHeight() {
        return this.f1089e;
    }

    public BitmapDescriptor getImage() {
        return this.f1086b;
    }

    public LatLng getPosition() {
        return this.f1087c;
    }

    public float getTransparency() {
        return this.f1093i;
    }

    public double getWidth() {
        return this.f1088d;
    }

    public void setAnchor(float f, float f2) {
        if (f >= 0.0f && f <= 1.0f && f2 >= 0.0f && f2 <= 1.0f) {
            this.f1090f = f;
            this.f1091g = f2;
            this.listener.mo1769b(this);
        }
    }

    public void setDimensions(int i) {
        this.f1088d = (double) i;
        this.f1089e = 2.147483647E9d;
        this.listener.mo1769b(this);
    }

    public void setDimensions(int i, int i2) {
        this.f1088d = (double) i;
        this.f1089e = (double) i2;
        this.listener.mo1769b(this);
    }

    public void setImage(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("image can not be null");
        }
        this.f1086b = bitmapDescriptor;
        this.listener.mo1769b(this);
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.f1085a = 2;
        this.f1087c = latLng;
        this.listener.mo1769b(this);
    }

    public void setPositionFromBounds(LatLngBounds latLngBounds) {
        if (latLngBounds == null) {
            throw new IllegalArgumentException("bounds can not be null");
        }
        this.f1085a = 1;
        this.f1092h = latLngBounds;
        this.listener.mo1769b(this);
    }

    public void setTransparency(float f) {
        if (f <= 1.0f && f >= 0.0f) {
            this.f1093i = f;
            this.listener.mo1769b(this);
        }
    }
}
