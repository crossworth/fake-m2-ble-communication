package com.baidu.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0408d implements Creator<Poi> {
    C0408d() {
    }

    public Poi createFromParcel(Parcel parcel) {
        return new Poi(parcel.readString(), parcel.readString(), parcel.readDouble());
    }

    public Poi[] newArray(int i) {
        return new Poi[i];
    }
}
