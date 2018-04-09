package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.BikingRouteLine.BikingStep;

final class C0556b implements Creator<BikingStep> {
    C0556b() {
    }

    public BikingStep m1660a(Parcel parcel) {
        return new BikingStep(parcel);
    }

    public BikingStep[] m1661a(int i) {
        return new BikingStep[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1660a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1661a(i);
    }
}
