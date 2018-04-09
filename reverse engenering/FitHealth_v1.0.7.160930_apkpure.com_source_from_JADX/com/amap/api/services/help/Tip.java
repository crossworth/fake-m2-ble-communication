package com.amap.api.services.help;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class Tip implements Parcelable {
    public static final Creator<Tip> CREATOR = new C03341();
    private String f1216a;
    private LatLonPoint f1217b;
    private String f1218c;
    private String f1219d;
    private String f1220e;
    private String f1221f;

    static class C03341 implements Creator<Tip> {
        C03341() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1189a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1190a(i);
        }

        public Tip m1189a(Parcel parcel) {
            return new Tip(parcel);
        }

        public Tip[] m1190a(int i) {
            return null;
        }
    }

    public String getPoiID() {
        return this.f1216a;
    }

    public void setID(String str) {
        this.f1216a = str;
    }

    public LatLonPoint getPoint() {
        return this.f1217b;
    }

    public void setPostion(LatLonPoint latLonPoint) {
        this.f1217b = latLonPoint;
    }

    public String getName() {
        return this.f1218c;
    }

    public void setName(String str) {
        this.f1218c = str;
    }

    public String getDistrict() {
        return this.f1219d;
    }

    public void setDistrict(String str) {
        this.f1219d = str;
    }

    public String getAdcode() {
        return this.f1220e;
    }

    public void setAdcode(String str) {
        this.f1220e = str;
    }

    public String getAddress() {
        return this.f1221f;
    }

    public void setAddress(String str) {
        this.f1221f = str;
    }

    public String toString() {
        return "name:" + this.f1218c + " district:" + this.f1219d + " adcode:" + this.f1220e;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1218c);
        parcel.writeString(this.f1220e);
        parcel.writeString(this.f1219d);
        parcel.writeString(this.f1216a);
        parcel.writeValue(this.f1217b);
        parcel.writeString(this.f1221f);
    }

    private Tip(Parcel parcel) {
        this.f1218c = parcel.readString();
        this.f1220e = parcel.readString();
        this.f1219d = parcel.readString();
        this.f1216a = parcel.readString();
        this.f1217b = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1221f = parcel.readString();
    }
}
