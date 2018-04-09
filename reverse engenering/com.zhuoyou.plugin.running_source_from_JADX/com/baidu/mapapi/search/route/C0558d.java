package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0558d implements Creator<DrivingRouteLine> {
    C0558d() {
    }

    public DrivingRouteLine m1664a(Parcel parcel) {
        return new DrivingRouteLine(parcel);
    }

    public DrivingRouteLine[] m1665a(int i) {
        return new DrivingRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1664a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1665a(i);
    }
}
