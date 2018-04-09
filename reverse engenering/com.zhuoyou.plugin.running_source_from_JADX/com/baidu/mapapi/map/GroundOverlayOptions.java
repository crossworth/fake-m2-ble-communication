package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

public final class GroundOverlayOptions extends OverlayOptions {
    int f1094a;
    boolean f1095b = true;
    Bundle f1096c;
    private BitmapDescriptor f1097d;
    private LatLng f1098e;
    private int f1099f;
    private int f1100g;
    private float f1101h = 0.5f;
    private float f1102i = 0.5f;
    private LatLngBounds f1103j;
    private float f1104k = 1.0f;

    Overlay mo1760a() {
        Overlay groundOverlay = new GroundOverlay();
        groundOverlay.s = this.f1095b;
        groundOverlay.r = this.f1094a;
        groundOverlay.t = this.f1096c;
        if (this.f1097d == null) {
            throw new IllegalStateException("when you add ground overlay, you must set the image");
        }
        groundOverlay.f1086b = this.f1097d;
        if (this.f1103j != null || this.f1098e == null) {
            if (this.f1098e != null || this.f1103j == null) {
                throw new IllegalStateException("when you add ground overlay, you must set one of position or bounds");
            }
            groundOverlay.f1092h = this.f1103j;
            groundOverlay.f1085a = 1;
        } else if (this.f1099f <= 0 || this.f1100g <= 0) {
            throw new IllegalArgumentException("when you add ground overlay, the width and height must greater than 0");
        } else {
            groundOverlay.f1087c = this.f1098e;
            groundOverlay.f1090f = this.f1101h;
            groundOverlay.f1091g = this.f1102i;
            groundOverlay.f1088d = (double) this.f1099f;
            groundOverlay.f1089e = (double) this.f1100g;
            groundOverlay.f1085a = 2;
        }
        groundOverlay.f1093i = this.f1104k;
        return groundOverlay;
    }

    public GroundOverlayOptions anchor(float f, float f2) {
        if (f >= 0.0f && f <= 1.0f && f2 >= 0.0f && f2 <= 1.0f) {
            this.f1101h = f;
            this.f1102i = f2;
        }
        return this;
    }

    public GroundOverlayOptions dimensions(int i) {
        this.f1099f = i;
        this.f1100g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        return this;
    }

    public GroundOverlayOptions dimensions(int i, int i2) {
        this.f1099f = i;
        this.f1100g = i2;
        return this;
    }

    public GroundOverlayOptions extraInfo(Bundle bundle) {
        this.f1096c = bundle;
        return this;
    }

    public float getAnchorX() {
        return this.f1101h;
    }

    public float getAnchorY() {
        return this.f1102i;
    }

    public LatLngBounds getBounds() {
        return this.f1103j;
    }

    public Bundle getExtraInfo() {
        return this.f1096c;
    }

    public int getHeight() {
        return this.f1100g == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED ? (int) (((float) (this.f1099f * this.f1097d.f1053a.getHeight())) / ((float) this.f1097d.f1053a.getWidth())) : this.f1100g;
    }

    public BitmapDescriptor getImage() {
        return this.f1097d;
    }

    public LatLng getPosition() {
        return this.f1098e;
    }

    public float getTransparency() {
        return this.f1104k;
    }

    public int getWidth() {
        return this.f1099f;
    }

    public int getZIndex() {
        return this.f1094a;
    }

    public GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("image can not be null");
        }
        this.f1097d = bitmapDescriptor;
        return this;
    }

    public boolean isVisible() {
        return this.f1095b;
    }

    public GroundOverlayOptions position(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.f1098e = latLng;
        return this;
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        if (latLngBounds == null) {
            throw new IllegalArgumentException("bounds can not be null");
        }
        this.f1103j = latLngBounds;
        return this;
    }

    public GroundOverlayOptions transparency(float f) {
        if (f <= 1.0f && f >= 0.0f) {
            this.f1104k = f;
        }
        return this;
    }

    public GroundOverlayOptions visible(boolean z) {
        this.f1095b = z;
        return this;
    }

    public GroundOverlayOptions zIndex(int i) {
        this.f1094a = i;
        return this;
    }
}
