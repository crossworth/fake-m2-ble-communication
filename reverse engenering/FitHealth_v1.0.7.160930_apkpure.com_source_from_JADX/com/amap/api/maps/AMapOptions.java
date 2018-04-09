package com.amap.api.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.amap.api.maps.model.CameraPosition;

public class AMapOptions implements Parcelable {
    public static final AMapOptionsCreator CREATOR = new AMapOptionsCreator();
    public static final int LOGO_POSITION_BOTTOM_CENTER = 1;
    public static final int LOGO_POSITION_BOTTOM_LEFT = 0;
    public static final int LOGO_POSITION_BOTTOM_RIGHT = 2;
    public static final int ZOOM_POSITION_RIGHT_BUTTOM = 2;
    public static final int ZOOM_POSITION_RIGHT_CENTER = 1;
    private int f787a = 1;
    private boolean f788b = true;
    private boolean f789c = true;
    private boolean f790d = true;
    private boolean f791e = true;
    private boolean f792f = true;
    private boolean f793g = false;
    private CameraPosition f794h;
    private boolean f795i = false;
    private boolean f796j = false;
    private int f797k = 0;

    public AMapOptions logoPosition(int i) {
        this.f797k = i;
        return this;
    }

    public AMapOptions zOrderOnTop(boolean z) {
        this.f793g = z;
        return this;
    }

    public AMapOptions mapType(int i) {
        this.f787a = i;
        return this;
    }

    public AMapOptions camera(CameraPosition cameraPosition) {
        this.f794h = cameraPosition;
        return this;
    }

    public AMapOptions scaleControlsEnabled(boolean z) {
        this.f796j = z;
        return this;
    }

    public AMapOptions zoomControlsEnabled(boolean z) {
        this.f792f = z;
        return this;
    }

    public AMapOptions compassEnabled(boolean z) {
        this.f795i = z;
        return this;
    }

    public AMapOptions scrollGesturesEnabled(boolean z) {
        this.f789c = z;
        return this;
    }

    public AMapOptions zoomGesturesEnabled(boolean z) {
        this.f791e = z;
        return this;
    }

    public AMapOptions tiltGesturesEnabled(boolean z) {
        this.f790d = z;
        return this;
    }

    public AMapOptions rotateGesturesEnabled(boolean z) {
        this.f788b = z;
        return this;
    }

    public int getLogoPosition() {
        return this.f797k;
    }

    public Boolean getZOrderOnTop() {
        return Boolean.valueOf(this.f793g);
    }

    public int getMapType() {
        return this.f787a;
    }

    public CameraPosition getCamera() {
        return this.f794h;
    }

    public Boolean getScaleControlsEnabled() {
        return Boolean.valueOf(this.f796j);
    }

    public Boolean getZoomControlsEnabled() {
        return Boolean.valueOf(this.f792f);
    }

    public Boolean getCompassEnabled() {
        return Boolean.valueOf(this.f795i);
    }

    public Boolean getScrollGesturesEnabled() {
        return Boolean.valueOf(this.f789c);
    }

    public Boolean getZoomGesturesEnabled() {
        return Boolean.valueOf(this.f791e);
    }

    public Boolean getTiltGesturesEnabled() {
        return Boolean.valueOf(this.f790d);
    }

    public Boolean getRotateGesturesEnabled() {
        return Boolean.valueOf(this.f788b);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f794h, i);
        parcel.writeInt(this.f787a);
        parcel.writeBooleanArray(new boolean[]{this.f788b, this.f789c, this.f790d, this.f791e, this.f792f, this.f793g, this.f795i, this.f796j});
    }
}
