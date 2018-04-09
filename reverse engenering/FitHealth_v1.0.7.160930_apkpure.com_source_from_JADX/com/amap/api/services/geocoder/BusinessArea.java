package com.amap.api.services.geocoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class BusinessArea implements Parcelable {
    public static final Creator<BusinessArea> CREATOR = new C03271();
    private LatLonPoint f1157a;
    private String f1158b;

    static class C03271 implements Creator<BusinessArea> {
        C03271() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1174a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1175a(i);
        }

        public BusinessArea m1174a(Parcel parcel) {
            return new BusinessArea(parcel);
        }

        public BusinessArea[] m1175a(int i) {
            return new BusinessArea[i];
        }
    }

    public LatLonPoint getCenterPoint() {
        return this.f1157a;
    }

    public void setCenterPoint(LatLonPoint latLonPoint) {
        this.f1157a = latLonPoint;
    }

    public String getName() {
        return this.f1158b;
    }

    public void setName(String str) {
        this.f1158b = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f1157a, i);
        parcel.writeString(this.f1158b);
    }

    public BusinessArea(Parcel parcel) {
        this.f1157a = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
        this.f1158b = parcel.readString();
    }
}
