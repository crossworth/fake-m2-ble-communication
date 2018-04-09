package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0550b implements Creator<PoiIndoorResult> {
    C0550b() {
    }

    public PoiIndoorResult m1562a(Parcel parcel) {
        return new PoiIndoorResult(parcel);
    }

    public PoiIndoorResult[] m1563a(int i) {
        return new PoiIndoorResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1562a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1563a(i);
    }
}
