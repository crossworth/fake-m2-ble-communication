package com.amap.api.services.geocoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public final class RegeocodeRoad implements Parcelable {
    public static final Creator<RegeocodeRoad> CREATOR = new C03301();
    private String f1195a;
    private String f1196b;
    private float f1197c;
    private String f1198d;
    private LatLonPoint f1199e;

    static class C03301 implements Creator<RegeocodeRoad> {
        C03301() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1180a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1181a(i);
        }

        public RegeocodeRoad m1180a(Parcel parcel) {
            return new RegeocodeRoad(parcel);
        }

        public RegeocodeRoad[] m1181a(int i) {
            return null;
        }
    }

    public String getId() {
        return this.f1195a;
    }

    public void setId(String str) {
        this.f1195a = str;
    }

    public String getName() {
        return this.f1196b;
    }

    public void setName(String str) {
        this.f1196b = str;
    }

    public float getDistance() {
        return this.f1197c;
    }

    public void setDistance(float f) {
        this.f1197c = f;
    }

    public String getDirection() {
        return this.f1198d;
    }

    public void setDirection(String str) {
        this.f1198d = str;
    }

    public LatLonPoint getLatLngPoint() {
        return this.f1199e;
    }

    public void setLatLngPoint(LatLonPoint latLonPoint) {
        this.f1199e = latLonPoint;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1195a);
        parcel.writeString(this.f1196b);
        parcel.writeFloat(this.f1197c);
        parcel.writeString(this.f1198d);
        parcel.writeValue(this.f1199e);
    }

    private RegeocodeRoad(Parcel parcel) {
        this.f1195a = parcel.readString();
        this.f1196b = parcel.readString();
        this.f1197c = parcel.readFloat();
        this.f1198d = parcel.readString();
        this.f1199e = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
    }
}
