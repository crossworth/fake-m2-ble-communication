package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.MassTransitRouteLine.TransitStep;

final class C0564j implements Creator<TransitStep> {
    C0564j() {
    }

    public TransitStep m1676a(Parcel parcel) {
        return new TransitStep(parcel);
    }

    public TransitStep[] m1677a(int i) {
        return new TransitStep[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1676a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1677a(i);
    }
}
