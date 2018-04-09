package com.amap.api.services.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.zhuoyi.system.util.constant.SeparatorConstants;

public class LatLonSharePoint extends LatLonPoint implements Parcelable {
    public static final Creator<LatLonSharePoint> CREATOR = new C03211();
    private String f4263a;

    static class C03211 implements Creator<LatLonSharePoint> {
        C03211() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1162a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1163a(i);
        }

        public LatLonSharePoint m1162a(Parcel parcel) {
            return new LatLonSharePoint(parcel);
        }

        public LatLonSharePoint[] m1163a(int i) {
            return new LatLonSharePoint[i];
        }
    }

    public LatLonSharePoint(double d, double d2, String str) {
        super(d, d2);
        this.f4263a = str;
    }

    public String getSharePointName() {
        return this.f4263a;
    }

    public void setSharePointName(String str) {
        this.f4263a = str;
    }

    protected LatLonSharePoint(Parcel parcel) {
        super(parcel);
        this.f4263a = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f4263a);
    }

    public int hashCode() {
        int i;
        int hashCode = super.hashCode() * 31;
        if (this.f4263a == null) {
            i = 0;
        } else {
            i = this.f4263a.hashCode();
        }
        return i + hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LatLonSharePoint latLonSharePoint = (LatLonSharePoint) obj;
        if (this.f4263a == null) {
            if (latLonSharePoint.f4263a != null) {
                return false;
            }
            return true;
        } else if (this.f4263a.equals(latLonSharePoint.f4263a)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return super.toString() + SeparatorConstants.SEPARATOR_ADS_ID + this.f4263a;
    }
}
