package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class PlaneInfo extends TransitBaseInfo {
    public static final Creator<PlaneInfo> CREATOR = new C0526d();
    private double f1532a;
    private String f1533b;
    private double f1534c;
    private String f1535d;

    protected PlaneInfo(Parcel parcel) {
        super(parcel);
        this.f1532a = parcel.readDouble();
        this.f1533b = parcel.readString();
        this.f1534c = parcel.readDouble();
        this.f1535d = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String getAirlines() {
        return this.f1533b;
    }

    public String getBooking() {
        return this.f1535d;
    }

    public double getDiscount() {
        return this.f1532a;
    }

    public double getPrice() {
        return this.f1534c;
    }

    public void setAirlines(String str) {
        this.f1533b = str;
    }

    public void setBooking(String str) {
        this.f1535d = str;
    }

    public void setDiscount(double d) {
        this.f1532a = d;
    }

    public void setPrice(double d) {
        this.f1534c = d;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.f1532a);
        parcel.writeString(this.f1533b);
        parcel.writeDouble(this.f1534c);
        parcel.writeString(this.f1535d);
    }
}
