package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions implements Parcelable {
    public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();
    String f926a;
    private final List<LatLng> f927b = new ArrayList();
    private float f928c = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f929d = ViewCompat.MEASURED_STATE_MASK;
    private float f930e = 0.0f;
    private boolean f931f = true;
    private BitmapDescriptor f932g;
    private List<BitmapDescriptor> f933h;
    private List<Integer> f934i;
    private List<Integer> f935j;
    private boolean f936k = true;
    private boolean f937l = false;
    private boolean f938m = false;
    private boolean f939n = false;

    public PolylineOptions setUseTexture(boolean z) {
        this.f936k = z;
        return this;
    }

    public PolylineOptions setCustomTexture(BitmapDescriptor bitmapDescriptor) {
        this.f932g = bitmapDescriptor;
        return this;
    }

    public BitmapDescriptor getCustomTexture() {
        return this.f932g;
    }

    public PolylineOptions setCustomTextureList(List<BitmapDescriptor> list) {
        this.f933h = list;
        return this;
    }

    public List<BitmapDescriptor> getCustomTextureList() {
        return this.f933h;
    }

    public PolylineOptions setCustomTextureIndex(List<Integer> list) {
        this.f935j = list;
        return this;
    }

    public List<Integer> getCustomTextureIndex() {
        return this.f935j;
    }

    public PolylineOptions colorValues(List<Integer> list) {
        this.f934i = list;
        return this;
    }

    public List<Integer> getColorValues() {
        return this.f934i;
    }

    public PolylineOptions useGradient(boolean z) {
        this.f939n = z;
        return this;
    }

    public boolean isUseGradient() {
        return this.f939n;
    }

    public boolean isUseTexture() {
        return this.f936k;
    }

    public boolean isGeodesic() {
        return this.f937l;
    }

    public PolylineOptions add(LatLng latLng) {
        this.f927b.add(latLng);
        return this;
    }

    public PolylineOptions add(LatLng... latLngArr) {
        this.f927b.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.f927b.add(add);
        }
        return this;
    }

    public PolylineOptions width(float f) {
        this.f928c = f;
        return this;
    }

    public PolylineOptions color(int i) {
        this.f929d = i;
        return this;
    }

    public PolylineOptions zIndex(float f) {
        this.f930e = f;
        return this;
    }

    public PolylineOptions visible(boolean z) {
        this.f931f = z;
        return this;
    }

    public PolylineOptions geodesic(boolean z) {
        this.f937l = z;
        return this;
    }

    public PolylineOptions setDottedLine(boolean z) {
        this.f938m = z;
        return this;
    }

    public boolean isDottedLine() {
        return this.f938m;
    }

    public List<LatLng> getPoints() {
        return this.f927b;
    }

    public float getWidth() {
        return this.f928c;
    }

    public int getColor() {
        return this.f929d;
    }

    public float getZIndex() {
        return this.f930e;
    }

    public boolean isVisible() {
        return this.f931f;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.f927b);
        parcel.writeFloat(this.f928c);
        parcel.writeInt(this.f929d);
        parcel.writeFloat(this.f930e);
        parcel.writeString(this.f926a);
        parcel.writeBooleanArray(new boolean[]{this.f931f, this.f938m, this.f937l, this.f939n});
        if (this.f932g != null) {
            parcel.writeParcelable(this.f932g, i);
        }
        if (this.f933h != null) {
            parcel.writeList(this.f933h);
        }
        if (this.f935j != null) {
            parcel.writeList(this.f935j);
        }
        if (this.f934i != null) {
            parcel.writeList(this.f934i);
        }
    }
}
