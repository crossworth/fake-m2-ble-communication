package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class RouteSearchCity extends SearchCity implements Parcelable {
    public static final Creator<RouteSearchCity> CREATOR = new C04291();
    List<District> f4413a = new ArrayList();

    static class C04291 implements Creator<RouteSearchCity> {
        C04291() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1707a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1708a(i);
        }

        public RouteSearchCity m1707a(Parcel parcel) {
            return new RouteSearchCity(parcel);
        }

        public RouteSearchCity[] m1708a(int i) {
            return null;
        }
    }

    public List<District> getDistricts() {
        return this.f4413a;
    }

    public void setDistricts(List<District> list) {
        this.f4413a = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.f4413a);
    }

    public RouteSearchCity(Parcel parcel) {
        super(parcel);
        this.f4413a = parcel.createTypedArrayList(District.CREATOR);
    }
}
