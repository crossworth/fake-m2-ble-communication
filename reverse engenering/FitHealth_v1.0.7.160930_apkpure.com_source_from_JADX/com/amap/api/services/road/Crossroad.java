package com.amap.api.services.road;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class Crossroad extends Road implements Parcelable {
    public static final Creator<Crossroad> CREATOR = new C04111();
    private float f4385a;
    private String f4386b;
    private String f4387c;
    private String f4388d;
    private String f4389e;
    private String f4390f;

    static class C04111 implements Creator<Crossroad> {
        C04111() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1671a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1672a(i);
        }

        public Crossroad[] m1672a(int i) {
            return null;
        }

        public Crossroad m1671a(Parcel parcel) {
            return new Crossroad(parcel);
        }
    }

    public float getDistance() {
        return this.f4385a;
    }

    public void setDistance(float f) {
        this.f4385a = f;
    }

    public String getDirection() {
        return this.f4386b;
    }

    public void setDirection(String str) {
        this.f4386b = str;
    }

    public String getFirstRoadId() {
        return this.f4387c;
    }

    public void setFirstRoadId(String str) {
        this.f4387c = str;
    }

    public String getFirstRoadName() {
        return this.f4388d;
    }

    public void setFirstRoadName(String str) {
        this.f4388d = str;
    }

    public String getSecondRoadId() {
        return this.f4389e;
    }

    public void setSecondRoadId(String str) {
        this.f4389e = str;
    }

    public String getSecondRoadName() {
        return this.f4390f;
    }

    public void setSecondRoadName(String str) {
        this.f4390f = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.f4385a);
        parcel.writeString(this.f4386b);
        parcel.writeString(this.f4387c);
        parcel.writeString(this.f4388d);
        parcel.writeString(this.f4389e);
        parcel.writeString(this.f4390f);
    }

    private Crossroad(Parcel parcel) {
        super(parcel);
        this.f4385a = parcel.readFloat();
        this.f4386b = parcel.readString();
        this.f4387c = parcel.readString();
        this.f4388d = parcel.readString();
        this.f4389e = parcel.readString();
        this.f4390f = parcel.readString();
    }
}
