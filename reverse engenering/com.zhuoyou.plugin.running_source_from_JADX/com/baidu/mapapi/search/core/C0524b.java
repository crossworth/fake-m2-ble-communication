package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0524b implements Creator<CityInfo> {
    C0524b() {
    }

    public CityInfo m1439a(Parcel parcel) {
        return new CityInfo(parcel);
    }

    public CityInfo[] m1440a(int i) {
        return new CityInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1439a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1440a(i);
    }
}
