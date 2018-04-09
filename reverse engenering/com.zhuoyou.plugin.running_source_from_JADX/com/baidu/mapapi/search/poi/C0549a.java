package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0549a implements Creator<PoiDetailResult> {
    C0549a() {
    }

    public PoiDetailResult m1560a(Parcel parcel) {
        return new PoiDetailResult(parcel);
    }

    public PoiDetailResult[] m1561a(int i) {
        return new PoiDetailResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1560a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1561a(i);
    }
}
