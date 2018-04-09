package com.baidu.mapapi.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0491h implements Creator<MapStatus> {
    C0491h() {
    }

    public MapStatus m1300a(Parcel parcel) {
        return new MapStatus(parcel);
    }

    public MapStatus[] m1301a(int i) {
        return new MapStatus[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1300a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1301a(i);
    }
}
