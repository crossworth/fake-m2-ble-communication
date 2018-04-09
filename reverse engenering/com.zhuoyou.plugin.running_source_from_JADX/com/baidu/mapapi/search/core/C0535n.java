package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0535n implements Creator<VehicleInfo> {
    C0535n() {
    }

    public VehicleInfo m1461a(Parcel parcel) {
        return new VehicleInfo(parcel);
    }

    public VehicleInfo[] m1462a(int i) {
        return new VehicleInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1461a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1462a(i);
    }
}
