package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0531j implements Creator<TaxiInfo> {
    C0531j() {
    }

    public TaxiInfo m1453a(Parcel parcel) {
        return new TaxiInfo(parcel);
    }

    public TaxiInfo[] m1454a(int i) {
        return new TaxiInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1453a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1454a(i);
    }
}
