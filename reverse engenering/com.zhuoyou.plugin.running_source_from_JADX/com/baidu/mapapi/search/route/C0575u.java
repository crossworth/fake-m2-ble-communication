package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0575u implements Creator<WalkingRouteResult> {
    C0575u() {
    }

    public WalkingRouteResult m1723a(Parcel parcel) {
        return new WalkingRouteResult(parcel);
    }

    public WalkingRouteResult[] m1724a(int i) {
        return new WalkingRouteResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1723a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1724a(i);
    }
}
