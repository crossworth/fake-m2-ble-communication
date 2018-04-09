package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;

public final class Dot extends Overlay {
    LatLng f1068a;
    int f1069b;
    int f1070c;

    Dot() {
        this.q = C0636h.dot;
    }

    Bundle mo1759a(Bundle bundle) {
        super.mo1759a(bundle);
        GeoPoint ll2mc = CoordUtil.ll2mc(this.f1068a);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("radius", this.f1070c);
        Overlay.m1057a(this.f1069b, bundle);
        return bundle;
    }

    public LatLng getCenter() {
        return this.f1068a;
    }

    public int getColor() {
        return this.f1069b;
    }

    public int getRadius() {
        return this.f1070c;
    }

    public void setCenter(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("dot center can not be null");
        }
        this.f1068a = latLng;
        this.listener.mo1769b(this);
    }

    public void setColor(int i) {
        this.f1069b = i;
        this.listener.mo1769b(this);
    }

    public void setRadius(int i) {
        if (i > 0) {
            this.f1070c = i;
            this.listener.mo1769b(this);
        }
    }
}
