package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0528f implements Creator<PriceInfo> {
    C0528f() {
    }

    public PriceInfo m1447a(Parcel parcel) {
        return new PriceInfo(parcel);
    }

    public PriceInfo[] m1448a(int i) {
        return new PriceInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1447a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1448a(i);
    }
}
