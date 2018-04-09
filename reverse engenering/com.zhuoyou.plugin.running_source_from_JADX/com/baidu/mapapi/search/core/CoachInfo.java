package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class CoachInfo extends TransitBaseInfo {
    public static final Creator<CoachInfo> CREATOR = new C0525c();
    private double f1528a;
    private String f1529b;
    private String f1530c;
    private String f1531d;

    protected CoachInfo(Parcel parcel) {
        super(parcel);
        this.f1528a = parcel.readDouble();
        this.f1529b = parcel.readString();
        this.f1530c = parcel.readString();
        this.f1531d = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String getBooking() {
        return this.f1529b;
    }

    public double getPrice() {
        return this.f1528a;
    }

    public String getProviderName() {
        return this.f1530c;
    }

    public String getProviderUrl() {
        return this.f1531d;
    }

    public void setBooking(String str) {
        this.f1529b = str;
    }

    public void setPrice(double d) {
        this.f1528a = d;
    }

    public void setProviderName(String str) {
        this.f1530c = str;
    }

    public void setProviderUrl(String str) {
        this.f1531d = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.f1528a);
        parcel.writeString(this.f1529b);
        parcel.writeString(this.f1530c);
        parcel.writeString(this.f1531d);
    }
}
