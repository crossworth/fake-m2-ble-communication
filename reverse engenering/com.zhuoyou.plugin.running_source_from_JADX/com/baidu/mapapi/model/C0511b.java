package com.baidu.mapapi.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0511b implements Creator<LatLngBounds> {
    C0511b() {
    }

    public LatLngBounds m1373a(Parcel parcel) {
        return new LatLngBounds(parcel);
    }

    public LatLngBounds[] m1374a(int i) {
        return new LatLngBounds[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1373a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1374a(i);
    }
}
