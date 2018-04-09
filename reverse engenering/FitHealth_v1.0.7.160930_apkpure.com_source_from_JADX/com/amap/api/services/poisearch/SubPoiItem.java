package com.amap.api.services.poisearch;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class SubPoiItem implements Parcelable {
    public static final Creator<SubPoiItem> CREATOR = new C03371();
    private String f1267a;
    private String f1268b;
    private String f1269c;
    private int f1270d;
    private LatLonPoint f1271e;
    private String f1272f;
    private String f1273g;

    static class C03371 implements Creator<SubPoiItem> {
        C03371() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1200a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1201a(i);
        }

        public SubPoiItem m1200a(Parcel parcel) {
            return new SubPoiItem(parcel);
        }

        public SubPoiItem[] m1201a(int i) {
            return null;
        }
    }

    public SubPoiItem(String str, LatLonPoint latLonPoint, String str2, String str3) {
        this.f1267a = str;
        this.f1271e = latLonPoint;
        this.f1268b = str2;
        this.f1272f = str3;
    }

    public SubPoiItem(Parcel parcel) {
        this.f1267a = parcel.readString();
        this.f1268b = parcel.readString();
        this.f1269c = parcel.readString();
        this.f1270d = parcel.readInt();
        this.f1271e = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1272f = parcel.readString();
        this.f1273g = parcel.readString();
    }

    public String getPoiId() {
        return this.f1267a;
    }

    public void setPoiId(String str) {
        this.f1267a = str;
    }

    public String getTitle() {
        return this.f1268b;
    }

    public void setTitle(String str) {
        this.f1268b = str;
    }

    public String getSubName() {
        return this.f1269c;
    }

    public void setSubName(String str) {
        this.f1269c = str;
    }

    public int getDistance() {
        return this.f1270d;
    }

    public void setDistance(int i) {
        this.f1270d = i;
    }

    public LatLonPoint getLatLonPoint() {
        return this.f1271e;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.f1271e = latLonPoint;
    }

    public String getSnippet() {
        return this.f1272f;
    }

    public void setSnippet(String str) {
        this.f1272f = str;
    }

    public String getSubTypeDes() {
        return this.f1273g;
    }

    public void setSubTypeDes(String str) {
        this.f1273g = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1267a);
        parcel.writeString(this.f1268b);
        parcel.writeString(this.f1269c);
        parcel.writeInt(this.f1270d);
        parcel.writeValue(this.f1271e);
        parcel.writeString(this.f1272f);
        parcel.writeString(this.f1273g);
    }
}
