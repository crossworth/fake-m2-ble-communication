package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: OfflineMapProvince */
class C0311c implements Creator<OfflineMapProvince> {
    C0311c() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1121a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1122a(i);
    }

    public OfflineMapProvince m1121a(Parcel parcel) {
        return new OfflineMapProvince(parcel);
    }

    public OfflineMapProvince[] m1122a(int i) {
        return new OfflineMapProvince[i];
    }
}
