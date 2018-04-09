package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class Doorway implements Parcelable {
    public static final Creator<Doorway> CREATOR = new C04171();
    private String f1592a;
    private LatLonPoint f1593b;

    static class C04171 implements Creator<Doorway> {
        C04171() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1683a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1684a(i);
        }

        public Doorway m1683a(Parcel parcel) {
            return new Doorway(parcel);
        }

        public Doorway[] m1684a(int i) {
            return null;
        }
    }

    public String getName() {
        return this.f1592a;
    }

    public void setName(String str) {
        this.f1592a = str;
    }

    public LatLonPoint getLatLonPoint() {
        return this.f1593b;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.f1593b = latLonPoint;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1592a);
        parcel.writeParcelable(this.f1593b, i);
    }

    public Doorway(Parcel parcel) {
        this.f1592a = parcel.readString();
        this.f1593b = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
    }
}
