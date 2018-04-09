package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0562h implements Creator<IndoorRouteResult> {
    C0562h() {
    }

    public IndoorRouteResult m1672a(Parcel parcel) {
        return new IndoorRouteResult(parcel);
    }

    public IndoorRouteResult[] m1673a(int i) {
        return new IndoorRouteResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1672a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1673a(i);
    }
}
