package com.amap.api.services.road;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class Road implements Parcelable {
    public static final Creator<Road> CREATOR = new C04121();
    private String f1580a;
    private String f1581b;
    private String f1582c;
    private float f1583d;
    private String f1584e;
    private LatLonPoint f1585f;

    static class C04121 implements Creator<Road> {
        C04121() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1673a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1674a(i);
        }

        public Road m1673a(Parcel parcel) {
            return new Road(parcel);
        }

        public Road[] m1674a(int i) {
            return null;
        }
    }

    public Road(String str, String str2) {
        this.f1580a = str;
        this.f1581b = str2;
    }

    public void setId(String str) {
        this.f1580a = str;
    }

    public void setName(String str) {
        this.f1581b = str;
    }

    public String getCityCode() {
        return this.f1582c;
    }

    public void setCityCode(String str) {
        this.f1582c = str;
    }

    public float getRoadWidth() {
        return this.f1583d;
    }

    public void setRoadWidth(float f) {
        this.f1583d = f;
    }

    public String getType() {
        return this.f1584e;
    }

    public void setType(String str) {
        this.f1584e = str;
    }

    public LatLonPoint getCenterPoint() {
        return this.f1585f;
    }

    public void setCenterPoint(LatLonPoint latLonPoint) {
        this.f1585f = latLonPoint;
    }

    public String getId() {
        return this.f1580a;
    }

    public String getName() {
        return this.f1581b;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1580a);
        parcel.writeString(this.f1581b);
        parcel.writeString(this.f1582c);
        parcel.writeFloat(this.f1583d);
        parcel.writeString(this.f1584e);
        parcel.writeValue(this.f1585f);
    }

    protected Road(Parcel parcel) {
        this.f1580a = parcel.readString();
        this.f1581b = parcel.readString();
        this.f1582c = parcel.readString();
        this.f1583d = parcel.readFloat();
        this.f1584e = parcel.readString();
        this.f1585f = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
    }
}
