package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0527e implements Creator<PoiInfo> {
    C0527e() {
    }

    public PoiInfo m1445a(Parcel parcel) {
        return new PoiInfo(parcel);
    }

    public PoiInfo[] m1446a(int i) {
        return new PoiInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1445a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1446a(i);
    }
}
