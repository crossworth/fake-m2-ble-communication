package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PriceInfo implements Parcelable {
    public static final Creator<PriceInfo> CREATOR = new C0528f();
    private int f1537a;
    private double f1538b;

    protected PriceInfo(Parcel parcel) {
        this.f1537a = parcel.readInt();
        this.f1538b = parcel.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public double getTicketPrice() {
        return this.f1538b;
    }

    public int getTicketType() {
        return this.f1537a;
    }

    public void setTicketPrice(double d) {
        this.f1538b = d;
    }

    public void setTicketType(int i) {
        this.f1537a = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1537a);
        parcel.writeDouble(this.f1538b);
    }
}
