package com.amap.api.services.geocoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public final class StreetNumber implements Parcelable {
    public static final Creator<StreetNumber> CREATOR = new C03311();
    private String f1200a;
    private String f1201b;
    private LatLonPoint f1202c;
    private String f1203d;
    private float f1204e;

    static class C03311 implements Creator<StreetNumber> {
        C03311() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1182a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1183a(i);
        }

        public StreetNumber m1182a(Parcel parcel) {
            return new StreetNumber(parcel);
        }

        public StreetNumber[] m1183a(int i) {
            return null;
        }
    }

    public String getStreet() {
        return this.f1200a;
    }

    public void setStreet(String str) {
        this.f1200a = str;
    }

    public String getNumber() {
        return this.f1201b;
    }

    public void setNumber(String str) {
        this.f1201b = str;
    }

    public LatLonPoint getLatLonPoint() {
        return this.f1202c;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.f1202c = latLonPoint;
    }

    public String getDirection() {
        return this.f1203d;
    }

    public void setDirection(String str) {
        this.f1203d = str;
    }

    public float getDistance() {
        return this.f1204e;
    }

    public void setDistance(float f) {
        this.f1204e = f;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1200a);
        parcel.writeString(this.f1201b);
        parcel.writeValue(this.f1202c);
        parcel.writeString(this.f1203d);
        parcel.writeFloat(this.f1204e);
    }

    private StreetNumber(Parcel parcel) {
        this.f1200a = parcel.readString();
        this.f1201b = parcel.readString();
        this.f1202c = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1203d = parcel.readString();
        this.f1204e = parcel.readFloat();
    }
}
