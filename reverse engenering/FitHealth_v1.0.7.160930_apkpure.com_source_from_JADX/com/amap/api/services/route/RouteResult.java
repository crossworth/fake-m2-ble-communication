package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class RouteResult implements Parcelable {
    public static final Creator<RouteResult> CREATOR = new C04241();
    private LatLonPoint f1609a;
    private LatLonPoint f1610b;

    static class C04241 implements Creator<RouteResult> {
        C04241() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1697a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1698a(i);
        }

        public RouteResult m1697a(Parcel parcel) {
            return new RouteResult(parcel);
        }

        public RouteResult[] m1698a(int i) {
            return new RouteResult[i];
        }
    }

    public LatLonPoint getStartPos() {
        return this.f1609a;
    }

    public void setStartPos(LatLonPoint latLonPoint) {
        this.f1609a = latLonPoint;
    }

    public LatLonPoint getTargetPos() {
        return this.f1610b;
    }

    public void setTargetPos(LatLonPoint latLonPoint) {
        this.f1610b = latLonPoint;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f1609a, i);
        parcel.writeParcelable(this.f1610b, i);
    }

    public RouteResult(Parcel parcel) {
        this.f1609a = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
        this.f1610b = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
    }
}
