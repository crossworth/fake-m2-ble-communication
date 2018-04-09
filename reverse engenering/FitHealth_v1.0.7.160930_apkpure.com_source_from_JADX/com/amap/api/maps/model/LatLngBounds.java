package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.amap.api.mapcore.util.cu;
import com.amap.api.mapcore.util.dj;

public final class LatLngBounds implements Parcelable {
    public static final LatLngBoundsCreator CREATOR = new LatLngBoundsCreator();
    private final int f874a;
    public final LatLng northeast;
    public final LatLng southwest;

    public static final class Builder {
        private double f870a = Double.POSITIVE_INFINITY;
        private double f871b = Double.NEGATIVE_INFINITY;
        private double f872c = Double.NaN;
        private double f873d = Double.NaN;

        public Builder include(LatLng latLng) {
            this.f870a = Math.min(this.f870a, latLng.latitude);
            this.f871b = Math.max(this.f871b, latLng.latitude);
            double d = latLng.longitude;
            if (Double.isNaN(this.f872c)) {
                this.f872c = d;
                this.f873d = d;
            } else if (!m1090a(d)) {
                if (LatLngBounds.m1096c(this.f872c, d) < LatLngBounds.m1097d(this.f873d, d)) {
                    this.f872c = d;
                } else {
                    this.f873d = d;
                }
            }
            return this;
        }

        private boolean m1090a(double d) {
            boolean z = false;
            if (this.f872c > this.f873d) {
                if (this.f872c <= d || d <= this.f873d) {
                    z = true;
                }
                return z;
            } else if (this.f872c > d || d > this.f873d) {
                return false;
            } else {
                return true;
            }
        }

        public LatLngBounds build() {
            boolean z;
            if (Double.isNaN(this.f872c)) {
                z = false;
            } else {
                z = true;
            }
            cu.m450a(z, (Object) "no included points");
            return new LatLngBounds(new LatLng(this.f870a, this.f872c, false), new LatLng(this.f871b, this.f873d, false));
        }
    }

    LatLngBounds(int i, LatLng latLng, LatLng latLng2) {
        cu.m449a((Object) latLng, (Object) "null southwest");
        cu.m449a((Object) latLng2, (Object) "null northeast");
        cu.m451a(latLng2.latitude >= latLng.latitude, "southern latitude exceeds northern latitude (%s > %s)", new Object[]{Double.valueOf(latLng.latitude), Double.valueOf(latLng2.latitude)});
        this.f874a = i;
        this.southwest = latLng;
        this.northeast = latLng2;
    }

    public LatLngBounds(LatLng latLng, LatLng latLng2) {
        this(1, latLng, latLng2);
    }

    int m1098a() {
        return this.f874a;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean contains(LatLng latLng) {
        return m1092a(latLng.latitude) && m1095b(latLng.longitude);
    }

    public boolean contains(LatLngBounds latLngBounds) {
        if (latLngBounds != null && contains(latLngBounds.southwest) && contains(latLngBounds.northeast)) {
            return true;
        }
        return false;
    }

    public boolean intersects(LatLngBounds latLngBounds) {
        if (latLngBounds == null) {
            return false;
        }
        if (m1093a(latLngBounds) || latLngBounds.m1093a(this)) {
            return true;
        }
        return false;
    }

    private boolean m1093a(LatLngBounds latLngBounds) {
        if (latLngBounds == null || latLngBounds.northeast == null || latLngBounds.southwest == null || this.northeast == null || this.southwest == null) {
            return false;
        }
        double d = ((latLngBounds.northeast.latitude + latLngBounds.southwest.latitude) - this.northeast.latitude) - this.southwest.latitude;
        double d2 = ((this.northeast.latitude - this.southwest.latitude) + latLngBounds.northeast.latitude) - latLngBounds.southwest.latitude;
        if (Math.abs(((latLngBounds.northeast.longitude + latLngBounds.southwest.longitude) - this.northeast.longitude) - this.southwest.longitude) >= ((this.northeast.longitude - this.southwest.longitude) + latLngBounds.northeast.longitude) - this.southwest.longitude || Math.abs(d) >= d2) {
            return false;
        }
        return true;
    }

    public LatLngBounds including(LatLng latLng) {
        double d;
        double min = Math.min(this.southwest.latitude, latLng.latitude);
        double max = Math.max(this.northeast.latitude, latLng.latitude);
        double d2 = this.northeast.longitude;
        double d3 = this.southwest.longitude;
        double d4 = latLng.longitude;
        if (m1095b(d4)) {
            d4 = d3;
            d = d2;
        } else if (m1096c(d3, d4) < m1097d(d2, d4)) {
            d = d2;
        } else {
            d = d4;
            d4 = d3;
        }
        return new LatLngBounds(new LatLng(min, d4, false), new LatLng(max, d, false));
    }

    private static double m1096c(double d, double d2) {
        return ((d - d2) + 360.0d) % 360.0d;
    }

    private static double m1097d(double d, double d2) {
        return ((d2 - d) + 360.0d) % 360.0d;
    }

    private boolean m1092a(double d) {
        return this.southwest.latitude <= d && d <= this.northeast.latitude;
    }

    private boolean m1095b(double d) {
        boolean z = false;
        if (this.southwest.longitude > this.northeast.longitude) {
            if (this.southwest.longitude <= d || d <= this.northeast.longitude) {
                z = true;
            }
            return z;
        } else if (this.southwest.longitude > d || d > this.northeast.longitude) {
            return false;
        } else {
            return true;
        }
    }

    public int hashCode() {
        return dj.m563a(new Object[]{this.southwest, this.northeast});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LatLngBounds)) {
            return false;
        }
        LatLngBounds latLngBounds = (LatLngBounds) obj;
        if (this.southwest.equals(latLngBounds.southwest) && this.northeast.equals(latLngBounds.northeast)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return dj.m574a(dj.m573a("southwest", this.southwest), dj.m573a("northeast", this.northeast));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        LatLngBoundsCreator.m1099a(this, parcel, i);
    }
}
