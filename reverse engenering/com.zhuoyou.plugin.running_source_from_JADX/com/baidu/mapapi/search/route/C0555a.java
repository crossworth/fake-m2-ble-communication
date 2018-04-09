package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0555a implements Creator<BikingRouteLine> {
    C0555a() {
    }

    public BikingRouteLine m1658a(Parcel parcel) {
        return new BikingRouteLine(parcel);
    }

    public BikingRouteLine[] m1659a(int i) {
        return new BikingRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1658a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1659a(i);
    }
}
