package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0545c implements Creator<ReverseGeoCodeResult> {
    C0545c() {
    }

    public ReverseGeoCodeResult m1525a(Parcel parcel) {
        return new ReverseGeoCodeResult(parcel);
    }

    public ReverseGeoCodeResult[] m1526a(int i) {
        return new ReverseGeoCodeResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1525a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1526a(i);
    }
}
