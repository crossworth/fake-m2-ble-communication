package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0529g implements Creator<RouteNode> {
    C0529g() {
    }

    public RouteNode m1449a(Parcel parcel) {
        return new RouteNode(parcel);
    }

    public RouteNode[] m1450a(int i) {
        return new RouteNode[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1449a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1450a(i);
    }
}
