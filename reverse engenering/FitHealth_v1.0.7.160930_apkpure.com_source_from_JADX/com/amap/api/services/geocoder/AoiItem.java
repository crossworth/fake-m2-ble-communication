package com.amap.api.services.geocoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class AoiItem implements Parcelable {
    public static final Creator<AoiItem> CREATOR = new C03261();
    private String f1152a;
    private String f1153b;
    private String f1154c;
    private LatLonPoint f1155d;
    private Float f1156e;

    static class C03261 implements Creator<AoiItem> {
        C03261() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1172a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1173a(i);
        }

        public AoiItem m1172a(Parcel parcel) {
            return new AoiItem(parcel);
        }

        public AoiItem[] m1173a(int i) {
            return new AoiItem[i];
        }
    }

    public String getAoiId() {
        return this.f1152a;
    }

    public String getAoiName() {
        return this.f1153b;
    }

    public String getAdCode() {
        return this.f1154c;
    }

    public LatLonPoint getAoiCenterPoint() {
        return this.f1155d;
    }

    public Float getAoiArea() {
        return this.f1156e;
    }

    public void setId(String str) {
        this.f1152a = str;
    }

    public void setName(String str) {
        this.f1153b = str;
    }

    public void setAdcode(String str) {
        this.f1154c = str;
    }

    public void setLocation(LatLonPoint latLonPoint) {
        this.f1155d = latLonPoint;
    }

    public void setArea(Float f) {
        this.f1156e = f;
    }

    public AoiItem(Parcel parcel) {
        this.f1152a = parcel.readString();
        this.f1153b = parcel.readString();
        this.f1154c = parcel.readString();
        this.f1155d = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
        this.f1156e = Float.valueOf(parcel.readFloat());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1152a);
        parcel.writeString(this.f1153b);
        parcel.writeString(this.f1154c);
        parcel.writeParcelable(this.f1155d, i);
        parcel.writeFloat(this.f1156e.floatValue());
    }
}
