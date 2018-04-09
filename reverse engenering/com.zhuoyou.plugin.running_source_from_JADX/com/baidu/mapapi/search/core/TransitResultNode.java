package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;

public class TransitResultNode implements Parcelable {
    public static final Creator<TransitResultNode> CREATOR = new C0534m();
    private int f1556a;
    private String f1557b = null;
    private LatLng f1558c = null;
    private String f1559d = null;

    public TransitResultNode(int i, String str, LatLng latLng, String str2) {
        this.f1556a = i;
        this.f1557b = str;
        this.f1558c = latLng;
        this.f1559d = str2;
    }

    protected TransitResultNode(Parcel parcel) {
        this.f1556a = parcel.readInt();
        this.f1557b = parcel.readString();
        this.f1558c = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.f1559d = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public int getCityId() {
        return this.f1556a;
    }

    public String getCityName() {
        return this.f1557b;
    }

    public LatLng getLocation() {
        return this.f1558c;
    }

    public String getSearchWord() {
        return this.f1559d;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1556a);
        parcel.writeString(this.f1557b);
        parcel.writeValue(this.f1558c);
        parcel.writeString(this.f1559d);
    }
}
