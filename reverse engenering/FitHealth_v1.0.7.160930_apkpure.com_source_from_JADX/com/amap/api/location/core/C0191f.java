package com.amap.api.location.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: GeoPoint */
class C0191f implements Creator<GeoPoint> {
    C0191f() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m120a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m121a(i);
    }

    public GeoPoint m120a(Parcel parcel) {
        return new GeoPoint(parcel);
    }

    public GeoPoint[] m121a(int i) {
        return new GeoPoint[i];
    }
}
