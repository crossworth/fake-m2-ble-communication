package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0569o implements Creator<SuggestAddrInfo> {
    C0569o() {
    }

    public SuggestAddrInfo m1711a(Parcel parcel) {
        return new SuggestAddrInfo(parcel);
    }

    public SuggestAddrInfo[] m1712a(int i) {
        return new SuggestAddrInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1711a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1712a(i);
    }
}
