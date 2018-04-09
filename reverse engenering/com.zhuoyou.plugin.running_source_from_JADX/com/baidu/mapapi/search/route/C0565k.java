package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.MassTransitRouteLine.TransitStep.TrafficCondition;

final class C0565k implements Creator<TrafficCondition> {
    C0565k() {
    }

    public TrafficCondition m1678a(Parcel parcel) {
        return new TrafficCondition(parcel);
    }

    public TrafficCondition[] m1679a(int i) {
        return new TrafficCondition[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1678a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1679a(i);
    }
}
