package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0530h implements Creator<RouteStep> {
    C0530h() {
    }

    public RouteStep m1451a(Parcel parcel) {
        return new RouteStep(parcel);
    }

    public RouteStep[] m1452a(int i) {
        return new RouteStep[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1451a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1452a(i);
    }
}
