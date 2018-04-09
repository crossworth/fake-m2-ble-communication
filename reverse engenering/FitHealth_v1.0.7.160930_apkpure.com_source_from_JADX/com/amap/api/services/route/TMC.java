package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TMC implements Parcelable {
    public static final Creator<TMC> CREATOR = new C04311();
    private int f1630a;
    private String f1631b;

    static class C04311 implements Creator<TMC> {
        C04311() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1711a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1712a(i);
        }

        public TMC m1711a(Parcel parcel) {
            return new TMC(parcel);
        }

        public TMC[] m1712a(int i) {
            return null;
        }
    }

    public int getDistance() {
        return this.f1630a;
    }

    public String getStatus() {
        return this.f1631b;
    }

    public void setDistance(int i) {
        this.f1630a = i;
    }

    public void setStatus(String str) {
        this.f1631b = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1630a);
        parcel.writeString(this.f1631b);
    }

    public TMC(Parcel parcel) {
        this.f1630a = parcel.readInt();
        this.f1631b = parcel.readString();
    }
}
