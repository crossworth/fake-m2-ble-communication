package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0561g implements Creator<IndoorRouteLine> {
    C0561g() {
    }

    public IndoorRouteLine m1670a(Parcel parcel) {
        return new IndoorRouteLine(parcel);
    }

    public IndoorRouteLine[] m1671a(int i) {
        return new IndoorRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1670a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1671a(i);
    }
}
