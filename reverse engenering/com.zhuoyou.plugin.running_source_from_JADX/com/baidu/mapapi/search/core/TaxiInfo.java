package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TaxiInfo implements Parcelable {
    public static final Creator<TaxiInfo> CREATOR = new C0531j();
    private float f1548a;
    private String f1549b;
    private int f1550c;
    private int f1551d;
    private float f1552e;
    private float f1553f;

    TaxiInfo(Parcel parcel) {
        this.f1548a = parcel.readFloat();
        this.f1549b = parcel.readString();
        this.f1550c = parcel.readInt();
        this.f1551d = parcel.readInt();
        this.f1552e = parcel.readFloat();
        this.f1553f = parcel.readFloat();
    }

    public int describeContents() {
        return 0;
    }

    public String getDesc() {
        return this.f1549b;
    }

    public int getDistance() {
        return this.f1550c;
    }

    public int getDuration() {
        return this.f1551d;
    }

    public float getPerKMPrice() {
        return this.f1552e;
    }

    public float getStartPrice() {
        return this.f1553f;
    }

    public float getTotalPrice() {
        return this.f1548a;
    }

    public void setDesc(String str) {
        this.f1549b = str;
    }

    public void setDistance(int i) {
        this.f1550c = i;
    }

    public void setDuration(int i) {
        this.f1551d = i;
    }

    public void setPerKMPrice(float f) {
        this.f1552e = f;
    }

    public void setStartPrice(float f) {
        this.f1553f = f;
    }

    public void setTotalPrice(float f) {
        this.f1548a = f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.f1548a);
        parcel.writeString(this.f1549b);
        parcel.writeInt(this.f1550c);
        parcel.writeInt(this.f1551d);
        parcel.writeFloat(this.f1552e);
        parcel.writeFloat(this.f1553f);
    }
}
