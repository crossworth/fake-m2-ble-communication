package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;

final class C0559e implements Creator<DrivingStep> {
    C0559e() {
    }

    public DrivingStep m1666a(Parcel parcel) {
        return new DrivingStep(parcel);
    }

    public DrivingStep[] m1667a(int i) {
        return new DrivingStep[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1666a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1667a(i);
    }
}
