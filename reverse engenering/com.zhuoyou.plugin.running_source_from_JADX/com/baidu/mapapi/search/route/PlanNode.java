package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;

public class PlanNode implements Parcelable {
    public static final Creator<PlanNode> CREATOR = new C0567m();
    private LatLng f1752a = null;
    private String f1753b = null;
    private String f1754c = null;

    protected PlanNode(Parcel parcel) {
        this.f1752a = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.f1753b = parcel.readString();
        this.f1754c = parcel.readString();
    }

    PlanNode(LatLng latLng, String str, String str2) {
        this.f1752a = latLng;
        this.f1753b = str;
        this.f1754c = str2;
    }

    public static PlanNode withCityCodeAndPlaceName(int i, String str) {
        return new PlanNode(null, String.valueOf(i), str);
    }

    public static PlanNode withCityNameAndPlaceName(String str, String str2) {
        return new PlanNode(null, str, str2);
    }

    public static PlanNode withLocation(LatLng latLng) {
        return new PlanNode(latLng, null, null);
    }

    public int describeContents() {
        return 0;
    }

    public String getCity() {
        return this.f1753b;
    }

    public LatLng getLocation() {
        return this.f1752a;
    }

    public String getName() {
        return this.f1754c;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.f1752a);
        parcel.writeString(this.f1753b);
        parcel.writeString(this.f1754c);
    }
}
