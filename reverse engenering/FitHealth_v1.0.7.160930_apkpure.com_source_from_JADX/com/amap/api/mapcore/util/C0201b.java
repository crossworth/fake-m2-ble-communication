package com.amap.api.mapcore.util;

import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate.Type;

/* compiled from: AMapCallback */
/* synthetic */ class C0201b {
    static final /* synthetic */ int[] f216a = new int[Type.values().length];

    static {
        try {
            f216a[Type.changeCenter.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            f216a[Type.changeBearing.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            f216a[Type.changeBearingGeoCenter.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            f216a[Type.changeTilt.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            f216a[Type.changeGeoCenterZoom.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
        try {
            f216a[Type.newCameraPosition.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
        try {
            f216a[Type.zoomIn.ordinal()] = 7;
        } catch (NoSuchFieldError e7) {
        }
        try {
            f216a[Type.zoomOut.ordinal()] = 8;
        } catch (NoSuchFieldError e8) {
        }
        try {
            f216a[Type.zoomTo.ordinal()] = 9;
        } catch (NoSuchFieldError e9) {
        }
        try {
            f216a[Type.zoomBy.ordinal()] = 10;
        } catch (NoSuchFieldError e10) {
        }
        try {
            f216a[Type.scrollBy.ordinal()] = 11;
        } catch (NoSuchFieldError e11) {
        }
        try {
            f216a[Type.newLatLngBounds.ordinal()] = 12;
        } catch (NoSuchFieldError e12) {
        }
        try {
            f216a[Type.newLatLngBoundsWithSize.ordinal()] = 13;
        } catch (NoSuchFieldError e13) {
        }
        try {
            f216a[Type.changeGeoCenterZoomTiltBearing.ordinal()] = 14;
        } catch (NoSuchFieldError e14) {
        }
    }
}
