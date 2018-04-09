package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class BusInfo extends TransitBaseInfo {
    public static final Creator<BusInfo> CREATOR = new C0523a();
    private int f1526a;
    private int f1527b;

    protected BusInfo(Parcel parcel) {
        super(parcel);
        this.f1526a = parcel.readInt();
        this.f1527b = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public int getStopNum() {
        return this.f1527b;
    }

    public int getType() {
        return this.f1526a;
    }

    public void setStopNum(int i) {
        this.f1527b = i;
    }

    public void setType(int i) {
        this.f1526a = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.f1526a);
        parcel.writeInt(this.f1527b);
    }
}
