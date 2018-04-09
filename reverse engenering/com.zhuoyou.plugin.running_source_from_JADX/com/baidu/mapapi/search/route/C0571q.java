package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;

final class C0571q implements Creator<TransitStep> {
    C0571q() {
    }

    public TransitStep m1715a(Parcel parcel) {
        return new TransitStep(parcel);
    }

    public TransitStep[] m1716a(int i) {
        return new TransitStep[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1715a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1716a(i);
    }
}
