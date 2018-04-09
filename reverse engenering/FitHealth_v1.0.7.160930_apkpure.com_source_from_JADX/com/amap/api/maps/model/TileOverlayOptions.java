package com.amap.api.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public final class TileOverlayOptions implements Parcelable {
    public static final TileOverlayOptionsCreator CREATOR = new TileOverlayOptionsCreator();
    private final int f962a;
    private TileProvider f963b;
    private boolean f964c;
    private float f965d;
    private int f966e;
    private int f967f;
    private String f968g;
    private boolean f969h;
    private boolean f970i;

    public TileOverlayOptions() {
        this.f964c = true;
        this.f966e = 5242880;
        this.f967f = 20971520;
        this.f968g = null;
        this.f969h = true;
        this.f970i = true;
        this.f962a = 1;
    }

    TileOverlayOptions(int i, IBinder iBinder, boolean z, float f) {
        this.f964c = true;
        this.f966e = 5242880;
        this.f967f = 20971520;
        this.f968g = null;
        this.f969h = true;
        this.f970i = true;
        this.f962a = i;
        this.f964c = z;
        this.f965d = f;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        int i3 = 1;
        parcel.writeInt(this.f962a);
        parcel.writeValue(this.f963b);
        parcel.writeByte((byte) (this.f964c ? 1 : 0));
        parcel.writeFloat(this.f965d);
        parcel.writeInt(this.f966e);
        parcel.writeInt(this.f967f);
        parcel.writeString(this.f968g);
        if (this.f969h) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
        if (!this.f970i) {
            i3 = 0;
        }
        parcel.writeByte((byte) i3);
    }

    public TileOverlayOptions tileProvider(TileProvider tileProvider) {
        this.f963b = tileProvider;
        return this;
    }

    public TileOverlayOptions zIndex(float f) {
        this.f965d = f;
        return this;
    }

    public TileOverlayOptions visible(boolean z) {
        this.f964c = z;
        return this;
    }

    public TileOverlayOptions memCacheSize(int i) {
        this.f966e = i;
        return this;
    }

    public TileOverlayOptions diskCacheSize(int i) {
        this.f967f = i * 1024;
        return this;
    }

    public TileOverlayOptions diskCacheDir(String str) {
        this.f968g = str;
        return this;
    }

    public TileOverlayOptions memoryCacheEnabled(boolean z) {
        this.f969h = z;
        return this;
    }

    public TileOverlayOptions diskCacheEnabled(boolean z) {
        this.f970i = z;
        return this;
    }

    public TileProvider getTileProvider() {
        return this.f963b;
    }

    public float getZIndex() {
        return this.f965d;
    }

    public boolean isVisible() {
        return this.f964c;
    }

    public int getMemCacheSize() {
        return this.f966e;
    }

    public int getDiskCacheSize() {
        return this.f967f;
    }

    public String getDiskCacheDir() {
        return this.f968g;
    }

    public boolean getMemoryCacheEnabled() {
        return this.f969h;
    }

    public boolean getDiskCacheEnabled() {
        return this.f970i;
    }
}
