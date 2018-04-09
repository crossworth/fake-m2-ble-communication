package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;
import java.util.List;

public final class Polygon extends Overlay {
    Stroke f1247a;
    int f1248b;
    List<LatLng> f1249c;

    Polygon() {
        this.q = C0636h.polygon;
    }

    Bundle mo1759a(Bundle bundle) {
        super.mo1759a(bundle);
        GeoPoint ll2mc = CoordUtil.ll2mc((LatLng) this.f1249c.get(0));
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        Overlay.m1058a(this.f1249c, bundle);
        Overlay.m1057a(this.f1248b, bundle);
        if (this.f1247a == null) {
            bundle.putInt("has_stroke", 0);
        } else {
            bundle.putInt("has_stroke", 1);
            bundle.putBundle("stroke", this.f1247a.m1181a(new Bundle()));
        }
        return bundle;
    }

    public int getFillColor() {
        return this.f1248b;
    }

    public List<LatLng> getPoints() {
        return this.f1249c;
    }

    public Stroke getStroke() {
        return this.f1247a;
    }

    public void setFillColor(int i) {
        this.f1248b = i;
        this.listener.mo1769b(this);
    }

    public void setPoints(List<LatLng> list) {
        if (list == null) {
            throw new IllegalArgumentException("points list can not be null");
        } else if (list.size() <= 2) {
            throw new IllegalArgumentException("points count can not less than three");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("points list can not contains null");
        } else {
            for (int i = 0; i < list.size(); i++) {
                for (int i2 = i + 1; i2 < list.size(); i2++) {
                    if (((LatLng) list.get(i)) == ((LatLng) list.get(i2))) {
                        throw new IllegalArgumentException("points list can not has same points");
                    }
                }
            }
            this.f1249c = list;
            this.listener.mo1769b(this);
        }
    }

    public void setStroke(Stroke stroke) {
        this.f1247a = stroke;
        this.listener.mo1769b(this);
    }
}
