package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Province implements Parcelable {
    public static final Creator<Province> CREATOR = new C0312d();
    private String f999a = "";
    private String f1000b;
    private String f1001c;
    private String f1002d = "";

    public String getProvinceName() {
        return this.f999a;
    }

    public String getJianpin() {
        return this.f1000b;
    }

    public String getPinyin() {
        return this.f1001c;
    }

    public void setProvinceName(String str) {
        this.f999a = str;
    }

    public void setJianpin(String str) {
        this.f1000b = str;
    }

    public void setPinyin(String str) {
        this.f1001c = str;
    }

    public void setProvinceCode(String str) {
        this.f1002d = str;
    }

    public String getProvinceCode() {
        return this.f1002d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f999a);
        parcel.writeString(this.f1000b);
        parcel.writeString(this.f1001c);
        parcel.writeString(this.f1002d);
    }

    public Province(Parcel parcel) {
        this.f999a = parcel.readString();
        this.f1000b = parcel.readString();
        this.f1001c = parcel.readString();
        this.f1002d = parcel.readString();
    }
}
