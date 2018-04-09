package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: City */
class C0309a implements Creator<City> {
    C0309a() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1117a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1118a(i);
    }

    public City m1117a(Parcel parcel) {
        return new City(parcel);
    }

    public City[] m1118a(int i) {
        return new City[i];
    }
}
