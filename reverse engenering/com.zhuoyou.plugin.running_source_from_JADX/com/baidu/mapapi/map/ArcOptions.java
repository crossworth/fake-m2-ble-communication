package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;

public final class ArcOptions extends OverlayOptions {
    private static final String f987d = ArcOptions.class.getSimpleName();
    int f988a;
    boolean f989b = true;
    Bundle f990c;
    private int f991e = -16777216;
    private int f992f = 5;
    private LatLng f993g;
    private LatLng f994h;
    private LatLng f995i;

    Overlay mo1760a() {
        Overlay arc = new Arc();
        arc.s = this.f989b;
        arc.r = this.f988a;
        arc.t = this.f990c;
        arc.f982a = this.f991e;
        arc.f983b = this.f992f;
        arc.f984c = this.f993g;
        arc.f985d = this.f994h;
        arc.f986e = this.f995i;
        return arc;
    }

    public ArcOptions color(int i) {
        this.f991e = i;
        return this;
    }

    public ArcOptions extraInfo(Bundle bundle) {
        this.f990c = bundle;
        return this;
    }

    public int getColor() {
        return this.f991e;
    }

    public LatLng getEndPoint() {
        return this.f995i;
    }

    public Bundle getExtraInfo() {
        return this.f990c;
    }

    public LatLng getMiddlePoint() {
        return this.f994h;
    }

    public LatLng getStartPoint() {
        return this.f993g;
    }

    public int getWidth() {
        return this.f992f;
    }

    public int getZIndex() {
        return this.f988a;
    }

    public boolean isVisible() {
        return this.f989b;
    }

    public ArcOptions points(LatLng latLng, LatLng latLng2, LatLng latLng3) {
        if (latLng == null || latLng2 == null || latLng3 == null) {
            throw new IllegalArgumentException("start and middle and end points can not be null");
        } else if (latLng == latLng2 || latLng == latLng3 || latLng2 == latLng3) {
            throw new IllegalArgumentException("start and middle and end points can not be same");
        } else {
            this.f993g = latLng;
            this.f994h = latLng2;
            this.f995i = latLng3;
            return this;
        }
    }

    public ArcOptions visible(boolean z) {
        this.f989b = z;
        return this;
    }

    public ArcOptions width(int i) {
        if (i > 0) {
            this.f992f = i;
        }
        return this;
    }

    public ArcOptions zIndex(int i) {
        this.f988a = i;
        return this;
    }
}
