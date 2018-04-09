package com.amap.api.maps.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

public class MyLocationStyle implements Parcelable {
    private BitmapDescriptor f892a;
    private float f893b = 0.5f;
    private float f894c = 0.5f;
    private int f895d = Color.argb(100, 0, 0, 180);
    private int f896e = Color.argb(255, 0, 0, 220);
    private float f897f = 1.0f;

    public MyLocationStyle myLocationIcon(BitmapDescriptor bitmapDescriptor) {
        this.f892a = bitmapDescriptor;
        return this;
    }

    public MyLocationStyle anchor(float f, float f2) {
        this.f893b = f;
        this.f894c = f2;
        return this;
    }

    public MyLocationStyle radiusFillColor(int i) {
        this.f895d = i;
        return this;
    }

    public MyLocationStyle strokeColor(int i) {
        this.f896e = i;
        return this;
    }

    public MyLocationStyle strokeWidth(float f) {
        this.f897f = f;
        return this;
    }

    public BitmapDescriptor getMyLocationIcon() {
        return this.f892a;
    }

    public float getAnchorU() {
        return this.f893b;
    }

    public float getAnchorV() {
        return this.f894c;
    }

    public int getRadiusFillColor() {
        return this.f895d;
    }

    public int getStrokeColor() {
        return this.f896e;
    }

    public float getStrokeWidth() {
        return this.f897f;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f892a, i);
        parcel.writeFloat(this.f893b);
        parcel.writeFloat(this.f894c);
        parcel.writeInt(this.f895d);
        parcel.writeInt(this.f896e);
        parcel.writeFloat(this.f897f);
    }
}
