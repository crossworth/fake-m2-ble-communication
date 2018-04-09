package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0532k implements Creator<TrainInfo> {
    C0532k() {
    }

    public TrainInfo m1455a(Parcel parcel) {
        return new TrainInfo(parcel);
    }

    public TrainInfo[] m1456a(int i) {
        return new TrainInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1455a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1456a(i);
    }
}
