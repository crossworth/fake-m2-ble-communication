package com.baidu.mapapi.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0512c implements Creator<ParcelItem> {
    C0512c() {
    }

    public ParcelItem m1375a(Parcel parcel) {
        ParcelItem parcelItem = new ParcelItem();
        parcelItem.setBundle(parcel.readBundle());
        return parcelItem;
    }

    public ParcelItem[] m1376a(int i) {
        return new ParcelItem[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1375a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1376a(i);
    }
}
