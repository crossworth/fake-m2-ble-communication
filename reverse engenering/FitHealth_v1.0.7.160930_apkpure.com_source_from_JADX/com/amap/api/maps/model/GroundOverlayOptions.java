package com.amap.api.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.amap.api.mapcore.util.cu;

public final class GroundOverlayOptions implements Parcelable {
    public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
    public static final float NO_DIMENSION = -1.0f;
    private final int f853a;
    private BitmapDescriptor f854b;
    private LatLng f855c;
    private float f856d;
    private float f857e;
    private LatLngBounds f858f;
    private float f859g;
    private float f860h;
    private boolean f861i;
    private float f862j;
    private float f863k;
    private float f864l;

    GroundOverlayOptions(int i, IBinder iBinder, LatLng latLng, float f, float f2, LatLngBounds latLngBounds, float f3, float f4, boolean z, float f5, float f6, float f7) {
        this.f860h = 0.0f;
        this.f861i = true;
        this.f862j = 0.0f;
        this.f863k = 0.5f;
        this.f864l = 0.5f;
        this.f853a = i;
        this.f854b = BitmapDescriptorFactory.fromBitmap(null);
        this.f855c = latLng;
        this.f856d = f;
        this.f857e = f2;
        this.f858f = latLngBounds;
        this.f859g = f3;
        this.f860h = f4;
        this.f861i = z;
        this.f862j = f5;
        this.f863k = f6;
        this.f864l = f7;
    }

    public GroundOverlayOptions() {
        this.f860h = 0.0f;
        this.f861i = true;
        this.f862j = 0.0f;
        this.f863k = 0.5f;
        this.f864l = 0.5f;
        this.f853a = 1;
    }

    public GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        this.f854b = bitmapDescriptor;
        return this;
    }

    public GroundOverlayOptions anchor(float f, float f2) {
        this.f863k = f;
        this.f864l = f2;
        return this;
    }

    public GroundOverlayOptions position(LatLng latLng, float f) {
        boolean z;
        boolean z2 = true;
        if (this.f858f == null) {
            z = true;
        } else {
            z = false;
        }
        cu.m450a(z, (Object) "Position has already been set using positionFromBounds");
        if (latLng != null) {
            z = true;
        } else {
            z = false;
        }
        cu.m452b(z, "Location must be specified");
        if (f < 0.0f) {
            z2 = false;
        }
        cu.m452b(z2, "Width must be non-negative");
        return m1084a(latLng, f, f);
    }

    public GroundOverlayOptions position(LatLng latLng, float f, float f2) {
        boolean z;
        boolean z2 = true;
        if (this.f858f == null) {
            z = true;
        } else {
            z = false;
        }
        cu.m450a(z, (Object) "Position has already been set using positionFromBounds");
        if (latLng != null) {
            z = true;
        } else {
            z = false;
        }
        cu.m452b(z, "Location must be specified");
        if (f >= 0.0f) {
            z = true;
        } else {
            z = false;
        }
        cu.m452b(z, "Width must be non-negative");
        if (f2 < 0.0f) {
            z2 = false;
        }
        cu.m452b(z2, "Height must be non-negative");
        return m1084a(latLng, f, f2);
    }

    private GroundOverlayOptions m1084a(LatLng latLng, float f, float f2) {
        this.f855c = latLng;
        this.f856d = f;
        this.f857e = f2;
        return this;
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        cu.m450a(this.f855c == null, "Position has already been set using position: " + this.f855c);
        this.f858f = latLngBounds;
        return this;
    }

    public GroundOverlayOptions bearing(float f) {
        this.f859g = f;
        return this;
    }

    public GroundOverlayOptions zIndex(float f) {
        this.f860h = f;
        return this;
    }

    public GroundOverlayOptions visible(boolean z) {
        this.f861i = z;
        return this;
    }

    public GroundOverlayOptions transparency(float f) {
        boolean z = f >= 0.0f && f <= 1.0f;
        cu.m452b(z, "Transparency must be in the range [0..1]");
        this.f862j = f;
        return this;
    }

    public BitmapDescriptor getImage() {
        return this.f854b;
    }

    public LatLng getLocation() {
        return this.f855c;
    }

    public float getWidth() {
        return this.f856d;
    }

    public float getHeight() {
        return this.f857e;
    }

    public LatLngBounds getBounds() {
        return this.f858f;
    }

    public float getBearing() {
        return this.f859g;
    }

    public float getZIndex() {
        return this.f860h;
    }

    public float getTransparency() {
        return this.f862j;
    }

    public float getAnchorU() {
        return this.f863k;
    }

    public float getAnchorV() {
        return this.f864l;
    }

    public boolean isVisible() {
        return this.f861i;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f853a);
        parcel.writeParcelable(this.f854b, i);
        parcel.writeParcelable(this.f855c, i);
        parcel.writeFloat(this.f856d);
        parcel.writeFloat(this.f857e);
        parcel.writeParcelable(this.f858f, i);
        parcel.writeFloat(this.f859g);
        parcel.writeFloat(this.f860h);
        parcel.writeByte((byte) (this.f861i ? 1 : 0));
        parcel.writeFloat(this.f862j);
        parcel.writeFloat(this.f863k);
        parcel.writeFloat(this.f864l);
    }
}
