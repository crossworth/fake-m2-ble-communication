package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TransitBaseInfo implements Parcelable {
    public static final Creator<TransitBaseInfo> CREATOR = new C0533l();
    private String f1521a;
    private String f1522b;
    private String f1523c;
    private String f1524d;
    private String f1525e;

    protected TransitBaseInfo(Parcel parcel) {
        this.f1521a = parcel.readString();
        this.f1522b = parcel.readString();
        this.f1523c = parcel.readString();
        this.f1524d = parcel.readString();
        this.f1525e = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String getArriveStation() {
        return this.f1523c;
    }

    public String getArriveTime() {
        return this.f1525e;
    }

    public String getDepartureStation() {
        return this.f1522b;
    }

    public String getDepartureTime() {
        return this.f1524d;
    }

    public String getName() {
        return this.f1521a;
    }

    public void setArriveStation(String str) {
        this.f1523c = str;
    }

    public void setArriveTime(String str) {
        this.f1525e = str;
    }

    public void setDepartureStation(String str) {
        this.f1522b = str;
    }

    public void setDepartureTime(String str) {
        this.f1524d = str;
    }

    public void setName(String str) {
        this.f1521a = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1521a);
        parcel.writeString(this.f1522b);
        parcel.writeString(this.f1523c);
        parcel.writeString(this.f1524d);
        parcel.writeString(this.f1525e);
    }
}
