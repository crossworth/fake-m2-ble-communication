package com.baidu.mapapi.search.busline;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0521a implements Creator<BusLineResult> {
    C0521a() {
    }

    public BusLineResult m1430a(Parcel parcel) {
        return new BusLineResult(parcel);
    }

    public BusLineResult[] m1431a(int i) {
        return new BusLineResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1430a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1431a(i);
    }
}
