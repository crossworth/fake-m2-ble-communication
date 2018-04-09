package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;

final class C0574t implements Creator<WalkingStep> {
    C0574t() {
    }

    public WalkingStep m1721a(Parcel parcel) {
        return new WalkingStep(parcel);
    }

    public WalkingStep[] m1722a(int i) {
        return new WalkingStep[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1721a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1722a(i);
    }
}
