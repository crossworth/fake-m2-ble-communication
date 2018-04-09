package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0523a implements Creator<BusInfo> {
    C0523a() {
    }

    public BusInfo m1437a(Parcel parcel) {
        return new BusInfo(parcel);
    }

    public BusInfo[] m1438a(int i) {
        return new BusInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1437a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1438a(i);
    }
}
