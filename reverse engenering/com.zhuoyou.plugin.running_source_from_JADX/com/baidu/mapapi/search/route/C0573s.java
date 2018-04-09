package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0573s implements Creator<WalkingRouteLine> {
    C0573s() {
    }

    public WalkingRouteLine m1719a(Parcel parcel) {
        return new WalkingRouteLine(parcel);
    }

    public WalkingRouteLine[] m1720a(int i) {
        return new WalkingRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1719a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1720a(i);
    }
}
