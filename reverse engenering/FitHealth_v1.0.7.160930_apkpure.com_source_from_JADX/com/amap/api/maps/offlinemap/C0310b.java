package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: OfflineMapCity */
class C0310b implements Creator<OfflineMapCity> {
    C0310b() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1119a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1120a(i);
    }

    public OfflineMapCity m1119a(Parcel parcel) {
        return new OfflineMapCity(parcel);
    }

    public OfflineMapCity[] m1120a(int i) {
        return new OfflineMapCity[i];
    }
}
