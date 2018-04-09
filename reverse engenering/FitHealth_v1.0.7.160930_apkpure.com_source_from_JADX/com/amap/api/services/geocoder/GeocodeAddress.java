package com.amap.api.services.geocoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public final class GeocodeAddress implements Parcelable {
    public static final Creator<GeocodeAddress> CREATOR = new C03281();
    private String f1159a;
    private String f1160b;
    private String f1161c;
    private String f1162d;
    private String f1163e;
    private String f1164f;
    private String f1165g;
    private String f1166h;
    private LatLonPoint f1167i;
    private String f1168j;

    static class C03281 implements Creator<GeocodeAddress> {
        C03281() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1176a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1177a(i);
        }

        public GeocodeAddress[] m1177a(int i) {
            return null;
        }

        public GeocodeAddress m1176a(Parcel parcel) {
            return new GeocodeAddress(parcel);
        }
    }

    public String getFormatAddress() {
        return this.f1159a;
    }

    public void setFormatAddress(String str) {
        this.f1159a = str;
    }

    public String getProvince() {
        return this.f1160b;
    }

    public void setProvince(String str) {
        this.f1160b = str;
    }

    public String getCity() {
        return this.f1161c;
    }

    public void setCity(String str) {
        this.f1161c = str;
    }

    public String getDistrict() {
        return this.f1162d;
    }

    public void setDistrict(String str) {
        this.f1162d = str;
    }

    public String getTownship() {
        return this.f1163e;
    }

    public void setTownship(String str) {
        this.f1163e = str;
    }

    public String getNeighborhood() {
        return this.f1164f;
    }

    public void setNeighborhood(String str) {
        this.f1164f = str;
    }

    public String getBuilding() {
        return this.f1165g;
    }

    public void setBuilding(String str) {
        this.f1165g = str;
    }

    public String getAdcode() {
        return this.f1166h;
    }

    public void setAdcode(String str) {
        this.f1166h = str;
    }

    public LatLonPoint getLatLonPoint() {
        return this.f1167i;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.f1167i = latLonPoint;
    }

    public String getLevel() {
        return this.f1168j;
    }

    public void setLevel(String str) {
        this.f1168j = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1159a);
        parcel.writeString(this.f1160b);
        parcel.writeString(this.f1161c);
        parcel.writeString(this.f1162d);
        parcel.writeString(this.f1163e);
        parcel.writeString(this.f1164f);
        parcel.writeString(this.f1165g);
        parcel.writeString(this.f1166h);
        parcel.writeValue(this.f1167i);
        parcel.writeString(this.f1168j);
    }

    private GeocodeAddress(Parcel parcel) {
        this.f1159a = parcel.readString();
        this.f1160b = parcel.readString();
        this.f1161c = parcel.readString();
        this.f1162d = parcel.readString();
        this.f1163e = parcel.readString();
        this.f1164f = parcel.readString();
        this.f1165g = parcel.readString();
        this.f1166h = parcel.readString();
        this.f1167i = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1168j = parcel.readString();
    }
}
