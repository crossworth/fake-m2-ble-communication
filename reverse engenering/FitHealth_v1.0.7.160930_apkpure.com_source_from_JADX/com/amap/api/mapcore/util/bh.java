package com.amap.api.mapcore.util;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: CityObject */
class bh implements Creator<bg> {
    bh() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m271a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m272a(i);
    }

    public bg m271a(Parcel parcel) {
        return new bg(parcel);
    }

    public bg[] m272a(int i) {
        return new bg[i];
    }
}
