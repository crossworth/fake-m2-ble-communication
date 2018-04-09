package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0526d implements Creator<PlaneInfo> {
    C0526d() {
    }

    public PlaneInfo m1443a(Parcel parcel) {
        return new PlaneInfo(parcel);
    }

    public PlaneInfo[] m1444a(int i) {
        return new PlaneInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1443a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1444a(i);
    }
}
