package com.amap.api.maps.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import com.tencent.open.yyb.TitleBar;
import p031u.aly.au;

public final class CircleOptions implements Parcelable {
    public static final CircleOptionsCreator CREATOR = new CircleOptionsCreator();
    String f836a;
    private LatLng f837b = null;
    private double f838c = 0.0d;
    private float f839d = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f840e = ViewCompat.MEASURED_STATE_MASK;
    private int f841f = 0;
    private float f842g = 0.0f;
    private boolean f843h = true;

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        if (this.f837b != null) {
            bundle.putDouble(au.f3570Y, this.f837b.latitude);
            bundle.putDouble(au.f3571Z, this.f837b.longitude);
        }
        parcel.writeBundle(bundle);
        parcel.writeDouble(this.f838c);
        parcel.writeFloat(this.f839d);
        parcel.writeInt(this.f840e);
        parcel.writeInt(this.f841f);
        parcel.writeFloat(this.f842g);
        parcel.writeByte((byte) (this.f843h ? 1 : 0));
        parcel.writeString(this.f836a);
    }

    public int describeContents() {
        return 0;
    }

    public CircleOptions center(LatLng latLng) {
        this.f837b = latLng;
        return this;
    }

    public CircleOptions radius(double d) {
        this.f838c = d;
        return this;
    }

    public CircleOptions strokeWidth(float f) {
        this.f839d = f;
        return this;
    }

    public CircleOptions strokeColor(int i) {
        this.f840e = i;
        return this;
    }

    public CircleOptions fillColor(int i) {
        this.f841f = i;
        return this;
    }

    public CircleOptions zIndex(float f) {
        this.f842g = f;
        return this;
    }

    public CircleOptions visible(boolean z) {
        this.f843h = z;
        return this;
    }

    public LatLng getCenter() {
        return this.f837b;
    }

    public double getRadius() {
        return this.f838c;
    }

    public float getStrokeWidth() {
        return this.f839d;
    }

    public int getStrokeColor() {
        return this.f840e;
    }

    public int getFillColor() {
        return this.f841f;
    }

    public float getZIndex() {
        return this.f842g;
    }

    public boolean isVisible() {
        return this.f843h;
    }
}
