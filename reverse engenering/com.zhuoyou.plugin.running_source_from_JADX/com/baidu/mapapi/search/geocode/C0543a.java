package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0543a implements Creator<GeoCodeResult> {
    C0543a() {
    }

    public GeoCodeResult m1518a(Parcel parcel) {
        return new GeoCodeResult(parcel);
    }

    public GeoCodeResult[] m1519a(int i) {
        return new GeoCodeResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1518a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1519a(i);
    }
}
