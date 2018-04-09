package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class TrainInfo extends TransitBaseInfo {
    public static final Creator<TrainInfo> CREATOR = new C0532k();
    private double f1554a;
    private String f1555b;

    protected TrainInfo(Parcel parcel) {
        super(parcel);
        this.f1554a = parcel.readDouble();
        this.f1555b = parcel.readString();
    }

    public void m1435a(double d) {
        this.f1554a = d;
    }

    public void m1436a(String str) {
        this.f1555b = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.f1554a);
        parcel.writeString(this.f1555b);
    }
}
