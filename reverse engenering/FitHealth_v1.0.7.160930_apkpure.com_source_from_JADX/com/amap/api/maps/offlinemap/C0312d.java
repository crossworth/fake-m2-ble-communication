package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: Province */
class C0312d implements Creator<Province> {
    C0312d() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1123a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1124a(i);
    }

    public Province m1123a(Parcel parcel) {
        return new Province(parcel);
    }

    public Province[] m1124a(int i) {
        return new Province[i];
    }
}
