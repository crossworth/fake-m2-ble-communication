package com.amap.api.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.amap.api.mapcore.util.cu;
import com.amap.api.mapcore.util.dg;
import com.amap.api.mapcore.util.dj;

public final class CameraPosition implements Parcelable {
    public static final CameraPositionCreator CREATOR = new CameraPositionCreator();
    public final float bearing;
    public final boolean isAbroad;
    public final LatLng target;
    public final float tilt;
    public final float zoom;

    public static final class Builder {
        private LatLng f831a;
        private float f832b;
        private float f833c;
        private float f834d;

        public Builder(CameraPosition cameraPosition) {
            target(cameraPosition.target).bearing(cameraPosition.bearing).tilt(cameraPosition.tilt).zoom(cameraPosition.zoom);
        }

        public Builder target(LatLng latLng) {
            this.f831a = latLng;
            return this;
        }

        public Builder zoom(float f) {
            this.f832b = f;
            return this;
        }

        public Builder tilt(float f) {
            this.f833c = f;
            return this;
        }

        public Builder bearing(float f) {
            this.f834d = f;
            return this;
        }

        public CameraPosition build() {
            cu.m448a(this.f831a);
            return new CameraPosition(this.f831a, this.f832b, this.f833c, this.f834d);
        }
    }

    public CameraPosition(LatLng latLng, float f, float f2, float f3) {
        cu.m449a((Object) latLng, (Object) "CameraPosition 位置不能为null ");
        this.target = latLng;
        this.zoom = dj.m557a(f);
        this.tilt = dj.m558a(f2, this.zoom);
        if (((double) f3) <= 0.0d) {
            f3 = (f3 % 360.0f) + 360.0f;
        }
        this.bearing = f3 % 360.0f;
        this.isAbroad = !dg.m545a(latLng.latitude, latLng.longitude);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.bearing);
        parcel.writeFloat((float) this.target.latitude);
        parcel.writeFloat((float) this.target.longitude);
        parcel.writeFloat(this.tilt);
        parcel.writeFloat(this.zoom);
    }

    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public static final CameraPosition fromLatLngZoom(LatLng latLng, float f) {
        return new CameraPosition(latLng, f, 0.0f, 0.0f);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition cameraPosition) {
        return new Builder(cameraPosition);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) obj;
        if (this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return dj.m574a(dj.m573a("target", this.target), dj.m573a("zoom", Float.valueOf(this.zoom)), dj.m573a("tilt", Float.valueOf(this.tilt)), dj.m573a("bearing", Float.valueOf(this.bearing)));
    }
}
