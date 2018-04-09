package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0563i implements Creator<MassTransitRouteLine> {
    C0563i() {
    }

    public MassTransitRouteLine m1674a(Parcel parcel) {
        return new MassTransitRouteLine(parcel);
    }

    public MassTransitRouteLine[] m1675a(int i) {
        return new MassTransitRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1674a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1675a(i);
    }
}
