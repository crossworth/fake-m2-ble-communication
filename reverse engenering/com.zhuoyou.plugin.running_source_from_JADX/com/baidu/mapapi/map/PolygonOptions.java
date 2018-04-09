package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;
import java.util.List;

public final class PolygonOptions extends OverlayOptions {
    int f1250a;
    boolean f1251b = true;
    Bundle f1252c;
    private Stroke f1253d;
    private int f1254e = -16777216;
    private List<LatLng> f1255f;

    Overlay mo1760a() {
        Overlay polygon = new Polygon();
        polygon.s = this.f1251b;
        polygon.r = this.f1250a;
        polygon.t = this.f1252c;
        if (this.f1255f == null || this.f1255f.size() < 2) {
            throw new IllegalStateException("when you add polyline, you must at least supply 2 points");
        }
        polygon.f1249c = this.f1255f;
        polygon.f1248b = this.f1254e;
        polygon.f1247a = this.f1253d;
        return polygon;
    }

    public PolygonOptions extraInfo(Bundle bundle) {
        this.f1252c = bundle;
        return this;
    }

    public PolygonOptions fillColor(int i) {
        this.f1254e = i;
        return this;
    }

    public Bundle getExtraInfo() {
        return this.f1252c;
    }

    public int getFillColor() {
        return this.f1254e;
    }

    public List<LatLng> getPoints() {
        return this.f1255f;
    }

    public Stroke getStroke() {
        return this.f1253d;
    }

    public int getZIndex() {
        return this.f1250a;
    }

    public boolean isVisible() {
        return this.f1251b;
    }

    public PolygonOptions points(List<LatLng> list) {
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
            this.f1255f = list;
            return this;
        }
    }

    public PolygonOptions stroke(Stroke stroke) {
        this.f1253d = stroke;
        return this;
    }

    public PolygonOptions visible(boolean z) {
        this.f1251b = z;
        return this;
    }

    public PolygonOptions zIndex(int i) {
        this.f1250a = i;
        return this;
    }
}
