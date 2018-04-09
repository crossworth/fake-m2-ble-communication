package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions implements Parcelable {
    public static final PolygonOptionsCreator CREATOR = new PolygonOptionsCreator();
    String f918a;
    private final List<LatLng> f919b = new ArrayList();
    private float f920c = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f921d = ViewCompat.MEASURED_STATE_MASK;
    private int f922e = ViewCompat.MEASURED_STATE_MASK;
    private float f923f = 0.0f;
    private boolean f924g = true;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.f919b);
        parcel.writeFloat(this.f920c);
        parcel.writeInt(this.f921d);
        parcel.writeInt(this.f922e);
        parcel.writeFloat(this.f923f);
        parcel.writeByte((byte) (this.f924g ? 1 : 0));
        parcel.writeString(this.f918a);
    }

    public PolygonOptions add(LatLng latLng) {
        this.f919b.add(latLng);
        return this;
    }

    public PolygonOptions add(LatLng... latLngArr) {
        this.f919b.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public PolygonOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.f919b.add(add);
        }
        return this;
    }

    public PolygonOptions strokeWidth(float f) {
        this.f920c = f;
        return this;
    }

    public PolygonOptions strokeColor(int i) {
        this.f921d = i;
        return this;
    }

    public PolygonOptions fillColor(int i) {
        this.f922e = i;
        return this;
    }

    public PolygonOptions zIndex(float f) {
        this.f923f = f;
        return this;
    }

    public PolygonOptions visible(boolean z) {
        this.f924g = z;
        return this;
    }

    public List<LatLng> getPoints() {
        return this.f919b;
    }

    public float getStrokeWidth() {
        return this.f920c;
    }

    public int getStrokeColor() {
        return this.f921d;
    }

    public int getFillColor() {
        return this.f922e;
    }

    public float getZIndex() {
        return this.f923f;
    }

    public boolean isVisible() {
        return this.f924g;
    }
}
