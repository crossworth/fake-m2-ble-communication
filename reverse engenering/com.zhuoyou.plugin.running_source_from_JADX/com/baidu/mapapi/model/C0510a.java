package com.baidu.mapapi.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0510a implements Creator<LatLng> {
    C0510a() {
    }

    public LatLng m1371a(Parcel parcel) {
        return new LatLng(parcel);
    }

    public LatLng[] m1372a(int i) {
        return new LatLng[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1371a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1372a(i);
    }
}
