package com.amap.api.services.poisearch;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IndoorData implements Parcelable {
    public static final Creator<IndoorData> CREATOR = new C03361();
    private String f1241a;
    private int f1242b;
    private String f1243c;

    static class C03361 implements Creator<IndoorData> {
        C03361() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1192a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1193a(i);
        }

        public IndoorData m1192a(Parcel parcel) {
            return new IndoorData(parcel);
        }

        public IndoorData[] m1193a(int i) {
            return new IndoorData[i];
        }
    }

    public IndoorData(String str, int i, String str2) {
        this.f1241a = str;
        this.f1242b = i;
        this.f1243c = str2;
    }

    public String getPoiId() {
        return this.f1241a;
    }

    public void setPoiId(String str) {
        this.f1241a = str;
    }

    public int getFloor() {
        return this.f1242b;
    }

    public void setFloor(int i) {
        this.f1242b = i;
    }

    public String getFloorName() {
        return this.f1243c;
    }

    public void setFloorName(String str) {
        this.f1243c = str;
    }

    protected IndoorData(Parcel parcel) {
        this.f1241a = parcel.readString();
        this.f1242b = parcel.readInt();
        this.f1243c = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1241a);
        parcel.writeInt(this.f1242b);
        parcel.writeString(this.f1243c);
    }
}
