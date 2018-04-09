package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0570p implements Creator<TransitRouteLine> {
    C0570p() {
    }

    public TransitRouteLine m1713a(Parcel parcel) {
        return new TransitRouteLine(parcel);
    }

    public TransitRouteLine[] m1714a(int i) {
        return new TransitRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1713a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1714a(i);
    }
}
