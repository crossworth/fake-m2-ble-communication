package com.baidu.mapapi.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0488e implements Creator<BaiduMapOptions> {
    C0488e() {
    }

    public BaiduMapOptions m1293a(Parcel parcel) {
        return new BaiduMapOptions(parcel);
    }

    public BaiduMapOptions[] m1294a(int i) {
        return new BaiduMapOptions[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1293a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1294a(i);
    }
}
