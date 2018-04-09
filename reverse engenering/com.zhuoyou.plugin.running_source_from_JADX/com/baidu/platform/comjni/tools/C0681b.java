package com.baidu.platform.comjni.tools;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0681b implements Creator<ParcelItem> {
    C0681b() {
    }

    public ParcelItem m2306a(Parcel parcel) {
        ParcelItem parcelItem = new ParcelItem();
        parcelItem.setBundle(parcel.readBundle());
        return parcelItem;
    }

    public ParcelItem[] m2307a(int i) {
        return new ParcelItem[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m2306a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m2307a(i);
    }
}
