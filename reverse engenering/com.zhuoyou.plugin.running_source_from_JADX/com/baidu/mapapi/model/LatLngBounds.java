package com.baidu.mapapi.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class LatLngBounds implements Parcelable {
    public static final Creator<LatLngBounds> CREATOR = new C0511b();
    public final LatLng northeast;
    public final LatLng southwest;

    public static final class Builder {
        private double f1457a;
        private double f1458b;
        private double f1459c;
        private double f1460d;
        private boolean f1461e = true;

        public LatLngBounds build() {
            return new LatLngBounds(new LatLng(this.f1458b, this.f1460d), new LatLng(this.f1457a, this.f1459c));
        }

        public Builder include(LatLng latLng) {
            if (latLng != null) {
                double d;
                if (this.f1461e) {
                    this.f1461e = false;
                    d = latLng.latitude;
                    this.f1457a = d;
                    this.f1458b = d;
                    d = latLng.longitude;
                    this.f1459c = d;
                    this.f1460d = d;
                }
                d = latLng.latitude;
                double d2 = latLng.longitude;
                if (d < this.f1457a) {
                    this.f1457a = d;
                }
                if (d > this.f1458b) {
                    this.f1458b = d;
                }
                if (d2 < this.f1459c) {
                    this.f1459c = d2;
                }
                if (d2 > this.f1460d) {
                    this.f1460d = d2;
                }
            }
            return this;
        }
    }

    protected LatLngBounds(Parcel parcel) {
        this.northeast = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
        this.southwest = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
    }

    LatLngBounds(LatLng latLng, LatLng latLng2) {
        this.northeast = latLng;
        this.southwest = latLng2;
    }

    public boolean contains(LatLng latLng) {
        if (latLng == null) {
            return false;
        }
        double d = this.southwest.latitude;
        double d2 = this.northeast.latitude;
        double d3 = this.southwest.longitude;
        double d4 = this.northeast.longitude;
        double d5 = latLng.latitude;
        double d6 = latLng.longitude;
        return d5 >= d && d5 <= d2 && d6 >= d3 && d6 <= d4;
    }

    public int describeContents() {
        return 0;
    }

    public LatLng getCenter() {
        return new LatLng(((this.northeast.latitude - this.southwest.latitude) / 2.0d) + this.southwest.latitude, ((this.northeast.longitude - this.southwest.longitude) / 2.0d) + this.southwest.longitude);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("southwest: ");
        stringBuilder.append(this.southwest.latitude);
        stringBuilder.append(", ");
        stringBuilder.append(this.southwest.longitude);
        stringBuilder.append("\n");
        stringBuilder.append("northeast: ");
        stringBuilder.append(this.northeast.latitude);
        stringBuilder.append(", ");
        stringBuilder.append(this.northeast.longitude);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.northeast, i);
        parcel.writeParcelable(this.southwest, i);
    }
}
