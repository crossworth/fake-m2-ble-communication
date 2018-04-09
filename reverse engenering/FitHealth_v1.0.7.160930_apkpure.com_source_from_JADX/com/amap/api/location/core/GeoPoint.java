package com.amap.api.location.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.zhuoyi.system.util.constant.SeparatorConstants;

public class GeoPoint implements Parcelable {
    public static final Creator<GeoPoint> CREATOR = new C0191f();
    private long f86a;
    private long f87b;
    private double f88c;
    private double f89d;

    public GeoPoint() {
        this.f86a = Long.MIN_VALUE;
        this.f87b = Long.MIN_VALUE;
        this.f88c = Double.MIN_VALUE;
        this.f89d = Double.MIN_VALUE;
        this.f86a = 0;
        this.f87b = 0;
    }

    public GeoPoint(int i, int i2) {
        this.f86a = Long.MIN_VALUE;
        this.f87b = Long.MIN_VALUE;
        this.f88c = Double.MIN_VALUE;
        this.f89d = Double.MIN_VALUE;
        this.f86a = (long) i;
        this.f87b = (long) i2;
    }

    public GeoPoint(long j, long j2) {
        this.f86a = Long.MIN_VALUE;
        this.f87b = Long.MIN_VALUE;
        this.f88c = Double.MIN_VALUE;
        this.f89d = Double.MIN_VALUE;
        this.f86a = j;
        this.f87b = j2;
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        GeoPoint geoPoint = (GeoPoint) obj;
        if (this.f88c == geoPoint.f88c && this.f89d == geoPoint.f89d && this.f86a == geoPoint.f86a && this.f87b == geoPoint.f87b) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (int) ((this.f89d * 7.0d) + (this.f88c * 11.0d));
    }

    public String toString() {
        return "" + this.f86a + SeparatorConstants.SEPARATOR_ADS_ID + this.f87b;
    }

    public int getLongitudeE6() {
        return (int) this.f87b;
    }

    public int getLatitudeE6() {
        return (int) this.f86a;
    }

    private GeoPoint(Parcel parcel) {
        this.f86a = Long.MIN_VALUE;
        this.f87b = Long.MIN_VALUE;
        this.f88c = Double.MIN_VALUE;
        this.f89d = Double.MIN_VALUE;
        this.f86a = parcel.readLong();
        this.f87b = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f86a);
        parcel.writeLong(this.f87b);
    }
}
