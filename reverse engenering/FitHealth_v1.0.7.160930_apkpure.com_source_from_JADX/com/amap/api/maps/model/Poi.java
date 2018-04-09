package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Poi implements Parcelable {
    public static final PoiCreator CREATOR = new PoiCreator();
    private final String f912a;
    private final LatLng f913b;
    private final String f914c;

    public Poi(String str, LatLng latLng, String str2) {
        this.f912a = str;
        this.f913b = latLng;
        this.f914c = str2;
    }

    public String getName() {
        return this.f912a;
    }

    public LatLng getCoordinate() {
        return this.f913b;
    }

    public String getPoiId() {
        return this.f914c;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Poi)) {
            return false;
        }
        Poi poi = (Poi) obj;
        if (poi.getName().equals(this.f912a) && poi.getCoordinate().equals(this.f913b) && poi.getPoiId().equals(this.f914c)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "poiid " + this.f914c + " name:" + this.f912a + "  coordinate:" + this.f913b.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f912a);
        parcel.writeParcelable(this.f913b, i);
        parcel.writeString(this.f914c);
    }
}
