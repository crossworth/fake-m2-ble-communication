package com.baidu.mapapi.search.district;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0539a implements Creator<DistrictResult> {
    C0539a() {
    }

    public DistrictResult m1488a(Parcel parcel) {
        return new DistrictResult(parcel);
    }

    public DistrictResult[] m1489a(int i) {
        return new DistrictResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1488a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1489a(i);
    }
}
