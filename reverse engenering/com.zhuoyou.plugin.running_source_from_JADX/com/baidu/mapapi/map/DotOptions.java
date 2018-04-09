package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;

public final class DotOptions extends OverlayOptions {
    int f1071a;
    boolean f1072b = true;
    Bundle f1073c;
    private LatLng f1074d;
    private int f1075e = -16777216;
    private int f1076f = 5;

    Overlay mo1760a() {
        Overlay dot = new Dot();
        dot.s = this.f1072b;
        dot.r = this.f1071a;
        dot.t = this.f1073c;
        dot.f1069b = this.f1075e;
        dot.f1068a = this.f1074d;
        dot.f1070c = this.f1076f;
        return dot;
    }

    public DotOptions center(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("dot center can not be null");
        }
        this.f1074d = latLng;
        return this;
    }

    public DotOptions color(int i) {
        this.f1075e = i;
        return this;
    }

    public DotOptions extraInfo(Bundle bundle) {
        this.f1073c = bundle;
        return this;
    }

    public LatLng getCenter() {
        return this.f1074d;
    }

    public int getColor() {
        return this.f1075e;
    }

    public Bundle getExtraInfo() {
        return this.f1073c;
    }

    public int getRadius() {
        return this.f1076f;
    }

    public int getZIndex() {
        return this.f1071a;
    }

    public boolean isVisible() {
        return this.f1072b;
    }

    public DotOptions radius(int i) {
        if (i > 0) {
            this.f1076f = i;
        }
        return this;
    }

    public DotOptions visible(boolean z) {
        this.f1072b = z;
        return this;
    }

    public DotOptions zIndex(int i) {
        this.f1071a = i;
        return this;
    }
}
