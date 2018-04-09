package com.amap.api.maps.model;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import p031u.aly.au;

public final class TextOptions implements Parcelable {
    public static final TextOptionsCreator CREATOR = new TextOptionsCreator();
    String f947a;
    private LatLng f948b;
    private String f949c;
    private Typeface f950d = Typeface.DEFAULT;
    private float f951e;
    private int f952f = 4;
    private int f953g = 32;
    private int f954h = -1;
    private int f955i = ViewCompat.MEASURED_STATE_MASK;
    private Object f956j;
    private int f957k = 20;
    private float f958l = 0.0f;
    private boolean f959m = true;

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f947a);
        Bundle bundle = new Bundle();
        if (this.f948b != null) {
            bundle.putDouble(au.f3570Y, this.f948b.latitude);
            bundle.putDouble(au.f3571Z, this.f948b.longitude);
        }
        parcel.writeBundle(bundle);
        parcel.writeString(this.f949c);
        parcel.writeInt(this.f950d.getStyle());
        parcel.writeFloat(this.f951e);
        parcel.writeInt(this.f952f);
        parcel.writeInt(this.f953g);
        parcel.writeInt(this.f954h);
        parcel.writeInt(this.f955i);
        parcel.writeInt(this.f957k);
        parcel.writeFloat(this.f958l);
        parcel.writeByte((byte) (this.f959m ? 1 : 0));
        if (this.f956j instanceof Parcelable) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("obj", (Parcelable) this.f956j);
            parcel.writeBundle(bundle2);
        }
    }

    public int describeContents() {
        return 0;
    }

    public TextOptions position(LatLng latLng) {
        this.f948b = latLng;
        return this;
    }

    public TextOptions text(String str) {
        this.f949c = str;
        return this;
    }

    public TextOptions typeface(Typeface typeface) {
        if (typeface != null) {
            this.f950d = typeface;
        }
        return this;
    }

    public TextOptions visible(boolean z) {
        this.f959m = z;
        return this;
    }

    public TextOptions zIndex(float f) {
        this.f958l = f;
        return this;
    }

    public TextOptions rotate(float f) {
        this.f951e = f;
        return this;
    }

    public TextOptions align(int i, int i2) {
        this.f952f = i;
        this.f953g = i2;
        return this;
    }

    public TextOptions backgroundColor(int i) {
        this.f954h = i;
        return this;
    }

    public TextOptions setObject(Object obj) {
        this.f956j = obj;
        return this;
    }

    public TextOptions fontColor(int i) {
        this.f955i = i;
        return this;
    }

    public TextOptions fontSize(int i) {
        this.f957k = i;
        return this;
    }

    public LatLng getPosition() {
        return this.f948b;
    }

    public String getText() {
        return this.f949c;
    }

    public Typeface getTypeface() {
        return this.f950d;
    }

    public float getRotate() {
        return this.f951e;
    }

    public int getAlignX() {
        return this.f952f;
    }

    public int getAlignY() {
        return this.f953g;
    }

    public int getBackgroundColor() {
        return this.f954h;
    }

    public int getFontColor() {
        return this.f955i;
    }

    public Object getObject() {
        return this.f956j;
    }

    public int getFontSize() {
        return this.f957k;
    }

    public float getZIndex() {
        return this.f958l;
    }

    public boolean isVisible() {
        return this.f959m;
    }
}
