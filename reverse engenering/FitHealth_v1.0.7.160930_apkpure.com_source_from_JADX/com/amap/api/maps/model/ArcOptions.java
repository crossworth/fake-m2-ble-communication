package com.amap.api.maps.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import com.tencent.open.yyb.TitleBar;

public final class ArcOptions implements Parcelable {
    public static final ArcOptionsCreator CREATOR = new ArcOptionsCreator();
    String f820a;
    private LatLng f821b;
    private LatLng f822c;
    private LatLng f823d;
    private float f824e = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f825f = ViewCompat.MEASURED_STATE_MASK;
    private float f826g = 0.0f;
    private boolean f827h = true;

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        if (this.f821b != null) {
            bundle.putDouble("startlat", this.f821b.latitude);
            bundle.putDouble("startlng", this.f821b.longitude);
        }
        if (this.f822c != null) {
            bundle.putDouble("passedlat", this.f822c.latitude);
            bundle.putDouble("passedlng", this.f822c.longitude);
        }
        if (this.f823d != null) {
            bundle.putDouble("endlat", this.f823d.latitude);
            bundle.putDouble("endlng", this.f823d.longitude);
        }
        parcel.writeBundle(bundle);
        parcel.writeFloat(this.f824e);
        parcel.writeInt(this.f825f);
        parcel.writeFloat(this.f826g);
        parcel.writeByte((byte) (this.f827h ? 1 : 0));
        parcel.writeString(this.f820a);
    }

    public int describeContents() {
        return 0;
    }

    public ArcOptions point(LatLng latLng, LatLng latLng2, LatLng latLng3) {
        this.f821b = latLng;
        this.f822c = latLng2;
        this.f823d = latLng3;
        return this;
    }

    public ArcOptions strokeWidth(float f) {
        this.f824e = f;
        return this;
    }

    public ArcOptions strokeColor(int i) {
        this.f825f = i;
        return this;
    }

    public ArcOptions zIndex(float f) {
        this.f826g = f;
        return this;
    }

    public ArcOptions visible(boolean z) {
        this.f827h = z;
        return this;
    }

    public float getStrokeWidth() {
        return this.f824e;
    }

    public int getStrokeColor() {
        return this.f825f;
    }

    public float getZIndex() {
        return this.f826g;
    }

    public boolean isVisible() {
        return this.f827h;
    }

    public LatLng getStart() {
        return this.f821b;
    }

    public LatLng getPassed() {
        return this.f822c;
    }

    public LatLng getEnd() {
        return this.f823d;
    }
}
