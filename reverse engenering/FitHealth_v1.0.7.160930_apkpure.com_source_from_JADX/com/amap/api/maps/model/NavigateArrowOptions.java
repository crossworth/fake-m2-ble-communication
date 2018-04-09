package com.amap.api.maps.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class NavigateArrowOptions implements Parcelable {
    public static final NavigateArrowOptionsCreator CREATOR = new NavigateArrowOptionsCreator();
    String f905a;
    private final List<LatLng> f906b = new ArrayList();
    private float f907c = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f908d = Color.argb(221, 87, 235, 204);
    private int f909e = Color.argb(170, 0, 172, 146);
    private float f910f = 0.0f;
    private boolean f911g = true;

    public NavigateArrowOptions add(LatLng latLng) {
        this.f906b.add(latLng);
        return this;
    }

    public NavigateArrowOptions add(LatLng... latLngArr) {
        this.f906b.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public NavigateArrowOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.f906b.add(add);
        }
        return this;
    }

    public NavigateArrowOptions width(float f) {
        this.f907c = f;
        return this;
    }

    public NavigateArrowOptions topColor(int i) {
        this.f908d = i;
        return this;
    }

    @Deprecated
    public NavigateArrowOptions sideColor(int i) {
        this.f909e = i;
        return this;
    }

    public NavigateArrowOptions zIndex(float f) {
        this.f910f = f;
        return this;
    }

    public NavigateArrowOptions visible(boolean z) {
        this.f911g = z;
        return this;
    }

    public List<LatLng> getPoints() {
        return this.f906b;
    }

    public float getWidth() {
        return this.f907c;
    }

    public int getTopColor() {
        return this.f908d;
    }

    @Deprecated
    public int getSideColor() {
        return this.f909e;
    }

    public float getZIndex() {
        return this.f910f;
    }

    public boolean isVisible() {
        return this.f911g;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.f906b);
        parcel.writeFloat(this.f907c);
        parcel.writeInt(this.f908d);
        parcel.writeInt(this.f909e);
        parcel.writeFloat(this.f910f);
        parcel.writeByte((byte) (this.f911g ? 1 : 0));
        parcel.writeString(this.f905a);
    }
}
