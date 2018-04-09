package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0534m implements Creator<TransitResultNode> {
    C0534m() {
    }

    public TransitResultNode m1459a(Parcel parcel) {
        return new TransitResultNode(parcel);
    }

    public TransitResultNode[] m1460a(int i) {
        return new TransitResultNode[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1459a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1460a(i);
    }
}
