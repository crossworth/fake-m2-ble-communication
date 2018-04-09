package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;

public final class CircleOptions extends OverlayOptions {
    private static final String f1060d = CircleOptions.class.getSimpleName();
    int f1061a;
    boolean f1062b = true;
    Bundle f1063c;
    private LatLng f1064e;
    private int f1065f = -16777216;
    private int f1066g;
    private Stroke f1067h;

    Overlay mo1760a() {
        Overlay circle = new Circle();
        circle.s = this.f1062b;
        circle.r = this.f1061a;
        circle.t = this.f1063c;
        circle.f1057b = this.f1065f;
        circle.f1056a = this.f1064e;
        circle.f1058c = this.f1066g;
        circle.f1059d = this.f1067h;
        return circle;
    }

    public CircleOptions center(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("circle center can not be null");
        }
        this.f1064e = latLng;
        return this;
    }

    public CircleOptions extraInfo(Bundle bundle) {
        this.f1063c = bundle;
        return this;
    }

    public CircleOptions fillColor(int i) {
        this.f1065f = i;
        return this;
    }

    public LatLng getCenter() {
        return this.f1064e;
    }

    public Bundle getExtraInfo() {
        return this.f1063c;
    }

    public int getFillColor() {
        return this.f1065f;
    }

    public int getRadius() {
        return this.f1066g;
    }

    public Stroke getStroke() {
        return this.f1067h;
    }

    public int getZIndex() {
        return this.f1061a;
    }

    public boolean isVisible() {
        return this.f1062b;
    }

    public CircleOptions radius(int i) {
        this.f1066g = i;
        return this;
    }

    public CircleOptions stroke(Stroke stroke) {
        this.f1067h = stroke;
        return this;
    }

    public CircleOptions visible(boolean z) {
        this.f1062b = z;
        return this;
    }

    public CircleOptions zIndex(int i) {
        this.f1061a = i;
        return this;
    }
}
