package com.amap.api.services.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.zhuoyi.system.util.constant.SeparatorConstants;

public class LatLonPoint implements Parcelable {
    public static final Creator<LatLonPoint> CREATOR = new C03201();
    private double f1101a;
    private double f1102b;

    static class C03201 implements Creator<LatLonPoint> {
        C03201() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1160a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1161a(i);
        }

        public LatLonPoint m1160a(Parcel parcel) {
            return new LatLonPoint(parcel);
        }

        public LatLonPoint[] m1161a(int i) {
            return new LatLonPoint[i];
        }
    }

    public LatLonPoint(double d, double d2) {
        this.f1101a = d;
        this.f1102b = d2;
    }

    public double getLongitude() {
        return this.f1102b;
    }

    public void setLongitude(double d) {
        this.f1102b = d;
    }

    public double getLatitude() {
        return this.f1101a;
    }

    public void setLatitude(double d) {
        this.f1101a = d;
    }

    public LatLonPoint copy() {
        return new LatLonPoint(this.f1101a, this.f1102b);
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.f1101a);
        int i = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31;
        long doubleToLongBits2 = Double.doubleToLongBits(this.f1102b);
        return (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LatLonPoint latLonPoint = (LatLonPoint) obj;
        if (Double.doubleToLongBits(this.f1101a) != Double.doubleToLongBits(latLonPoint.f1101a)) {
            return false;
        }
        if (Double.doubleToLongBits(this.f1102b) != Double.doubleToLongBits(latLonPoint.f1102b)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "" + this.f1101a + SeparatorConstants.SEPARATOR_ADS_ID + this.f1102b;
    }

    protected LatLonPoint(Parcel parcel) {
        this.f1101a = parcel.readDouble();
        this.f1102b = parcel.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.f1101a);
        parcel.writeDouble(this.f1102b);
    }
}
