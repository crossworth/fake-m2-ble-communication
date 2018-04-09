package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;

final class C0546d implements Creator<AddressComponent> {
    C0546d() {
    }

    public AddressComponent m1527a(Parcel parcel) {
        return new AddressComponent(parcel);
    }

    public AddressComponent[] m1528a(int i) {
        return new AddressComponent[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1527a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1528a(i);
    }
}
