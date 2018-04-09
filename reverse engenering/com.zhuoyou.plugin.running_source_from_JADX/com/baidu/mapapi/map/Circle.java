package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;

public final class Circle extends Overlay {
    LatLng f1056a;
    int f1057b;
    int f1058c;
    Stroke f1059d;

    Circle() {
        this.q = C0636h.circle;
    }

    Bundle mo1759a(Bundle bundle) {
        super.mo1759a(bundle);
        GeoPoint ll2mc = CoordUtil.ll2mc(this.f1056a);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("radius", CoordUtil.getMCDistanceByOneLatLngAndRadius(this.f1056a, this.f1058c));
        Overlay.m1057a(this.f1057b, bundle);
        if (this.f1059d == null) {
            bundle.putInt("has_stroke", 0);
        } else {
            bundle.putInt("has_stroke", 1);
            bundle.putBundle("stroke", this.f1059d.m1181a(new Bundle()));
        }
        return bundle;
    }

    public LatLng getCenter() {
        return this.f1056a;
    }

    public int getFillColor() {
        return this.f1057b;
    }

    public int getRadius() {
        return this.f1058c;
    }

    public Stroke getStroke() {
        return this.f1059d;
    }

    public void setCenter(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("circle center can not be null");
        }
        this.f1056a = latLng;
        this.listener.mo1769b(this);
    }

    public void setFillColor(int i) {
        this.f1057b = i;
        this.listener.mo1769b(this);
    }

    public void setRadius(int i) {
        this.f1058c = i;
        this.listener.mo1769b(this);
    }

    public void setStroke(Stroke stroke) {
        this.f1059d = stroke;
        this.listener.mo1769b(this);
    }
}
