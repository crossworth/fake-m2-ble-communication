package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0560f implements Creator<DrivingRouteResult> {
    C0560f() {
    }

    public DrivingRouteResult m1668a(Parcel parcel) {
        return new DrivingRouteResult(parcel);
    }

    public DrivingRouteResult[] m1669a(int i) {
        return new DrivingRouteResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1668a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1669a(i);
    }
}
