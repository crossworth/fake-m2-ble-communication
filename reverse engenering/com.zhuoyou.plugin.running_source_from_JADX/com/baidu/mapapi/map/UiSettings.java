package com.baidu.mapapi.map;

import com.baidu.platform.comapi.map.C0633e;

public final class UiSettings {
    private C0633e f1371a;

    UiSettings(C0633e c0633e) {
        this.f1371a = c0633e;
    }

    public boolean isCompassEnabled() {
        return this.f1371a.m2028q();
    }

    public boolean isOverlookingGesturesEnabled() {
        return this.f1371a.m2037y();
    }

    public boolean isRotateGesturesEnabled() {
        return this.f1371a.m2036x();
    }

    public boolean isScrollGesturesEnabled() {
        return this.f1371a.m2034v();
    }

    public boolean isZoomGesturesEnabled() {
        return this.f1371a.m2035w();
    }

    public void setAllGesturesEnabled(boolean z) {
        setRotateGesturesEnabled(z);
        setScrollGesturesEnabled(z);
        setOverlookingGesturesEnabled(z);
        setZoomGesturesEnabled(z);
    }

    public void setCompassEnabled(boolean z) {
        this.f1371a.m2010h(z);
    }

    public void setOverlookingGesturesEnabled(boolean z) {
        this.f1371a.m2025p(z);
    }

    public void setRotateGesturesEnabled(boolean z) {
        this.f1371a.m2024o(z);
    }

    public void setScrollGesturesEnabled(boolean z) {
        this.f1371a.m2020m(z);
    }

    public void setZoomGesturesEnabled(boolean z) {
        this.f1371a.m2022n(z);
    }
}
