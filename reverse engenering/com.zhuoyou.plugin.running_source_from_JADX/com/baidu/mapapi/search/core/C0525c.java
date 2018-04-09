package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0525c implements Creator<CoachInfo> {
    C0525c() {
    }

    public CoachInfo m1441a(Parcel parcel) {
        return new CoachInfo(parcel);
    }

    public CoachInfo[] m1442a(int i) {
        return new CoachInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1441a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1442a(i);
    }
}
