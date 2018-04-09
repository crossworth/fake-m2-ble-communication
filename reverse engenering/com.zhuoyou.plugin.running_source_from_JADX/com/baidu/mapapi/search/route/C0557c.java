package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0557c implements Creator<BikingRouteLine> {
    C0557c() {
    }

    public BikingRouteLine m1662a(Parcel parcel) {
        return new BikingRouteLine(parcel);
    }

    public BikingRouteLine[] m1663a(int i) {
        return new BikingRouteLine[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1662a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1663a(i);
    }
}
