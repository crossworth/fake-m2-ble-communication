package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: TileCreator */
class C0303d implements Creator<Tile> {
    C0303d() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1108a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1109a(i);
    }

    public Tile m1108a(Parcel parcel) {
        return new Tile(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
    }

    public Tile[] m1109a(int i) {
        return new Tile[i];
    }
}
