package com.baidu.mapapi.search.share;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0578a implements Creator<ShareUrlResult> {
    C0578a() {
    }

    public ShareUrlResult m1749a(Parcel parcel) {
        return new ShareUrlResult(parcel);
    }

    public ShareUrlResult[] m1750a(int i) {
        return new ShareUrlResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1749a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1750a(i);
    }
}
