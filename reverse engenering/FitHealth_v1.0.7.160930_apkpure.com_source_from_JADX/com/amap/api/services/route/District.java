package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class District implements Parcelable {
    public static final Creator<District> CREATOR = new C04161();
    private String f1590a;
    private String f1591b;

    static class C04161 implements Creator<District> {
        C04161() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1681a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1682a(i);
        }

        public District m1681a(Parcel parcel) {
            return new District(parcel);
        }

        public District[] m1682a(int i) {
            return null;
        }
    }

    public String getDistrictName() {
        return this.f1590a;
    }

    public void setDistrictName(String str) {
        this.f1590a = str;
    }

    public String getDistrictAdcode() {
        return this.f1591b;
    }

    public void setDistrictAdcode(String str) {
        this.f1591b = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1590a);
        parcel.writeString(this.f1591b);
    }

    public District(Parcel parcel) {
        this.f1590a = parcel.readString();
        this.f1591b = parcel.readString();
    }
}
