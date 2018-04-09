package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class City implements Parcelable {
    public static final Creator<City> CREATOR = new C0309a();
    private String f977a = "";
    private String f978b = "";
    private String f979c;
    private String f980d;
    private String f981e = "";

    public void setCity(String str) {
        this.f977a = str;
    }

    public String getCity() {
        return this.f977a;
    }

    public void setCode(String str) {
        if (str != null && !str.equals("[]")) {
            this.f978b = str;
        }
    }

    public String getCode() {
        return this.f978b;
    }

    public String getJianpin() {
        return this.f979c;
    }

    public void setJianpin(String str) {
        this.f979c = str;
    }

    public String getPinyin() {
        return this.f980d;
    }

    public void setPinyin(String str) {
        this.f980d = str;
    }

    public String getAdcode() {
        return this.f981e;
    }

    public void setAdcode(String str) {
        this.f981e = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f977a);
        parcel.writeString(this.f978b);
        parcel.writeString(this.f979c);
        parcel.writeString(this.f980d);
        parcel.writeString(this.f981e);
    }

    public City(Parcel parcel) {
        this.f977a = parcel.readString();
        this.f978b = parcel.readString();
        this.f979c = parcel.readString();
        this.f980d = parcel.readString();
        this.f981e = parcel.readString();
    }
}
