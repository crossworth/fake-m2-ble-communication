package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0551c implements Creator<PoiResult> {
    C0551c() {
    }

    public PoiResult m1564a(Parcel parcel) {
        return new PoiResult(parcel);
    }

    public PoiResult[] m1565a(int i) {
        return new PoiResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1564a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1565a(i);
    }
}
