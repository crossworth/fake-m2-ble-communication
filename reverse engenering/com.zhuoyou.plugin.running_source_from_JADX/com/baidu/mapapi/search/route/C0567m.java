package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0567m implements Creator<PlanNode> {
    C0567m() {
    }

    public PlanNode m1682a(Parcel parcel) {
        return new PlanNode(parcel);
    }

    public PlanNode[] m1683a(int i) {
        return new PlanNode[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1682a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1683a(i);
    }
}
