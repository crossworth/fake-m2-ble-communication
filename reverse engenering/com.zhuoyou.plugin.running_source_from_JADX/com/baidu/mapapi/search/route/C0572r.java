package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0572r implements Creator<TransitRouteResult> {
    C0572r() {
    }

    public TransitRouteResult m1717a(Parcel parcel) {
        return new TransitRouteResult(parcel);
    }

    public TransitRouteResult[] m1718a(int i) {
        return new TransitRouteResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1717a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1718a(i);
    }
}
