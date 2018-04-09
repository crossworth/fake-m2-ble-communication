package com.baidu.location;

import android.text.TextUtils;

public interface BDGeofence {
    public static final String COORD_TYPE_BD09 = "bd09";
    public static final String COORD_TYPE_BD09LL = "bd09ll";
    public static final String COORD_TYPE_GCJ = "gcj02";
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int RADIUS_TYPE_LARGE = 3;
    public static final int RADIUS_TYPE_MIDDELE = 2;
    public static final int RADIUS_TYPE_SMALL = 1;

    public static final class Builder {
        private int f2085a;
        private String f2086do = null;
        private double f2087for;
        private long f2088if = Long.MIN_VALUE;
        private String f2089int;
        private boolean f2090new = false;
        private double f2091try;

        public BDGeofence build() {
            if (TextUtils.isEmpty(this.f2086do)) {
                throw new IllegalArgumentException("BDGeofence name not set.");
            } else if (!this.f2090new) {
                throw new IllegalArgumentException("BDGeofence region not set.");
            } else if (this.f2088if == Long.MIN_VALUE) {
                throw new IllegalArgumentException("BDGeofence Expiration not set.");
            } else if (!TextUtils.isEmpty(this.f2089int)) {
                return new ah(this.f2086do, this.f2091try, this.f2087for, this.f2085a, this.f2088if, this.f2089int);
            } else {
                throw new IllegalArgumentException("BDGeofence CoordType not set.");
            }
        }

        public Builder setCircularRegion(double d, double d2, int i) {
            this.f2090new = true;
            this.f2091try = d;
            this.f2087for = d2;
            this.f2085a = i;
            return this;
        }

        public Builder setCoordType(String str) {
            this.f2089int = str;
            return this;
        }

        public Builder setExpirationDruation(long j) {
            if (j < 0) {
                this.f2088if = -1;
            } else {
                this.f2088if = j;
            }
            return this;
        }

        public Builder setGeofenceId(String str) {
            this.f2086do = str;
            return this;
        }
    }

    String getGeofenceId();
}
