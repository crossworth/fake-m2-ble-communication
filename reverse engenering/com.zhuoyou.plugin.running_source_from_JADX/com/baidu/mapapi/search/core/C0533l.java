package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0533l implements Creator<TransitBaseInfo> {
    C0533l() {
    }

    public TransitBaseInfo m1457a(Parcel parcel) {
        return new TransitBaseInfo(parcel);
    }

    public TransitBaseInfo[] m1458a(int i) {
        return new TransitBaseInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1457a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1458a(i);
    }
}
