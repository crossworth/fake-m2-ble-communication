package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class VehicleInfo implements Parcelable {
    public static final Creator<VehicleInfo> CREATOR = new C0535n();
    private String f1560a;
    private int f1561b;
    private String f1562c;
    private int f1563d;
    private int f1564e;

    protected VehicleInfo(Parcel parcel) {
        this.f1560a = parcel.readString();
        this.f1561b = parcel.readInt();
        this.f1562c = parcel.readString();
        this.f1563d = parcel.readInt();
        this.f1564e = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public int getPassStationNum() {
        return this.f1561b;
    }

    public String getTitle() {
        return this.f1562c;
    }

    public int getTotalPrice() {
        return this.f1564e;
    }

    public String getUid() {
        return this.f1560a;
    }

    public int getZonePrice() {
        return this.f1563d;
    }

    public void setPassStationNum(int i) {
        this.f1561b = i;
    }

    public void setTitle(String str) {
        this.f1562c = str;
    }

    public void setTotalPrice(int i) {
        this.f1564e = i;
    }

    public void setUid(String str) {
        this.f1560a = str;
    }

    public void setZonePrice(int i) {
        this.f1563d = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1560a);
        parcel.writeInt(this.f1561b);
        parcel.writeString(this.f1562c);
        parcel.writeInt(this.f1563d);
        parcel.writeInt(this.f1564e);
    }
}
