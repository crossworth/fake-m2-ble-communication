package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0566l implements Creator<MassTransitRouteResult> {
    C0566l() {
    }

    public MassTransitRouteResult m1680a(Parcel parcel) {
        return new MassTransitRouteResult(parcel);
    }

    public MassTransitRouteResult[] m1681a(int i) {
        return new MassTransitRouteResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1680a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1681a(i);
    }
}
