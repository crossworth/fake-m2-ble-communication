package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;

public class GeoCodeResult extends SearchResult implements Parcelable {
    public static final Creator<GeoCodeResult> CREATOR = new C0543a();
    private LatLng f1577a;
    private String f1578b;

    GeoCodeResult() {
    }

    protected GeoCodeResult(Parcel parcel) {
        this.f1577a = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.f1578b = parcel.readString();
    }

    GeoCodeResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1492a(LatLng latLng) {
        this.f1577a = latLng;
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.f1578b;
    }

    public LatLng getLocation() {
        return this.f1577a;
    }

    public void setAddress(String str) {
        this.f1578b = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.f1577a);
        parcel.writeString(this.f1578b);
    }
}
