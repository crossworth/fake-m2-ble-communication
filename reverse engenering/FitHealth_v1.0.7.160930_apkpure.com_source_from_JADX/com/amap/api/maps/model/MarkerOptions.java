package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public final class MarkerOptions implements Parcelable {
    public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();
    String f876a;
    private LatLng f877b;
    private String f878c;
    private String f879d;
    private float f880e = 0.5f;
    private float f881f = 1.0f;
    private float f882g = 0.0f;
    private boolean f883h = false;
    private boolean f884i = true;
    private boolean f885j = false;
    private int f886k = 0;
    private int f887l = 0;
    private ArrayList<BitmapDescriptor> f888m = new ArrayList();
    private int f889n = 20;
    private boolean f890o = false;
    private boolean f891p = false;

    public MarkerOptions icons(ArrayList<BitmapDescriptor> arrayList) {
        this.f888m = arrayList;
        return this;
    }

    public ArrayList<BitmapDescriptor> getIcons() {
        return this.f888m;
    }

    public MarkerOptions period(int i) {
        if (i <= 1) {
            this.f889n = 1;
        } else {
            this.f889n = i;
        }
        return this;
    }

    public int getPeriod() {
        return this.f889n;
    }

    public boolean isPerspective() {
        return this.f885j;
    }

    public MarkerOptions perspective(boolean z) {
        this.f885j = z;
        return this;
    }

    public MarkerOptions position(LatLng latLng) {
        this.f877b = latLng;
        return this;
    }

    public MarkerOptions setFlat(boolean z) {
        this.f891p = z;
        return this;
    }

    private void m1100a() {
        if (this.f888m == null) {
            this.f888m = new ArrayList();
        }
    }

    public MarkerOptions icon(BitmapDescriptor bitmapDescriptor) {
        m1100a();
        this.f888m.clear();
        this.f888m.add(bitmapDescriptor);
        return this;
    }

    public MarkerOptions anchor(float f, float f2) {
        this.f880e = f;
        this.f881f = f2;
        return this;
    }

    public MarkerOptions setInfoWindowOffset(int i, int i2) {
        this.f886k = i;
        this.f887l = i2;
        return this;
    }

    public MarkerOptions title(String str) {
        this.f878c = str;
        return this;
    }

    public MarkerOptions snippet(String str) {
        this.f879d = str;
        return this;
    }

    public MarkerOptions draggable(boolean z) {
        this.f883h = z;
        return this;
    }

    public MarkerOptions visible(boolean z) {
        this.f884i = z;
        return this;
    }

    public MarkerOptions setGps(boolean z) {
        this.f890o = z;
        return this;
    }

    public LatLng getPosition() {
        return this.f877b;
    }

    public String getTitle() {
        return this.f878c;
    }

    public String getSnippet() {
        return this.f879d;
    }

    public BitmapDescriptor getIcon() {
        if (this.f888m == null || this.f888m.size() == 0) {
            return null;
        }
        return (BitmapDescriptor) this.f888m.get(0);
    }

    public float getAnchorU() {
        return this.f880e;
    }

    public int getInfoWindowOffsetX() {
        return this.f886k;
    }

    public int getInfoWindowOffsetY() {
        return this.f887l;
    }

    public float getAnchorV() {
        return this.f881f;
    }

    public boolean isDraggable() {
        return this.f883h;
    }

    public boolean isVisible() {
        return this.f884i;
    }

    public boolean isGps() {
        return this.f890o;
    }

    public boolean isFlat() {
        return this.f891p;
    }

    public MarkerOptions zIndex(float f) {
        this.f882g = f;
        return this;
    }

    public float getZIndex() {
        return this.f882g;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f877b, i);
        if (!(this.f888m == null || this.f888m.size() == 0)) {
            parcel.writeParcelable((Parcelable) this.f888m.get(0), i);
        }
        parcel.writeString(this.f878c);
        parcel.writeString(this.f879d);
        parcel.writeFloat(this.f880e);
        parcel.writeFloat(this.f881f);
        parcel.writeInt(this.f886k);
        parcel.writeInt(this.f887l);
        parcel.writeBooleanArray(new boolean[]{this.f884i, this.f883h, this.f890o, this.f891p});
        parcel.writeString(this.f876a);
        parcel.writeInt(this.f889n);
        parcel.writeList(this.f888m);
        parcel.writeFloat(this.f882g);
    }
}
