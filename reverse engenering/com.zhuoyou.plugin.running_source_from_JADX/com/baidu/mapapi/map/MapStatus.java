package com.baidu.mapapi.map;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0616D;

public final class MapStatus implements Parcelable {
    public static final Creator<MapStatus> CREATOR = new C0491h();
    C0616D f1153a;
    private double f1154b;
    public final LatLngBounds bound;
    private double f1155c;
    public final float overlook;
    public final float rotate;
    public final LatLng target;
    public final Point targetScreen;
    public final float zoom;

    public static final class Builder {
        private float f1145a = -2.14748365E9f;
        private LatLng f1146b = null;
        private float f1147c = -2.14748365E9f;
        private float f1148d = -2.14748365E9f;
        private Point f1149e = null;
        private LatLngBounds f1150f = null;
        private double f1151g = 0.0d;
        private double f1152h = 0.0d;

        public Builder(MapStatus mapStatus) {
            this.f1145a = mapStatus.rotate;
            this.f1146b = mapStatus.target;
            this.f1147c = mapStatus.overlook;
            this.f1148d = mapStatus.zoom;
            this.f1149e = mapStatus.targetScreen;
            this.f1151g = mapStatus.m1146a();
            this.f1152h = mapStatus.m1147b();
        }

        public MapStatus build() {
            return new MapStatus(this.f1145a, this.f1146b, this.f1147c, this.f1148d, this.f1149e, this.f1150f);
        }

        public Builder overlook(float f) {
            this.f1147c = f;
            return this;
        }

        public Builder rotate(float f) {
            this.f1145a = f;
            return this;
        }

        public Builder target(LatLng latLng) {
            this.f1146b = latLng;
            return this;
        }

        public Builder targetScreen(Point point) {
            this.f1149e = point;
            return this;
        }

        public Builder zoom(float f) {
            this.f1148d = f;
            return this;
        }
    }

    MapStatus(float f, LatLng latLng, float f2, float f3, Point point, double d, double d2, LatLngBounds latLngBounds) {
        this.rotate = f;
        this.target = latLng;
        this.overlook = f2;
        this.zoom = f3;
        this.targetScreen = point;
        this.f1154b = d;
        this.f1155c = d2;
        this.bound = latLngBounds;
    }

    MapStatus(float f, LatLng latLng, float f2, float f3, Point point, LatLngBounds latLngBounds) {
        this.rotate = f;
        this.target = latLng;
        this.overlook = f2;
        this.zoom = f3;
        this.targetScreen = point;
        if (this.target != null) {
            this.f1154b = CoordUtil.ll2mc(this.target).getLongitudeE6();
            this.f1155c = CoordUtil.ll2mc(this.target).getLatitudeE6();
        }
        this.bound = latLngBounds;
    }

    MapStatus(float f, LatLng latLng, float f2, float f3, Point point, C0616D c0616d, double d, double d2, LatLngBounds latLngBounds) {
        this.rotate = f;
        this.target = latLng;
        this.overlook = f2;
        this.zoom = f3;
        this.targetScreen = point;
        this.f1153a = c0616d;
        this.f1154b = d;
        this.f1155c = d2;
        this.bound = latLngBounds;
    }

    protected MapStatus(Parcel parcel) {
        this.rotate = parcel.readFloat();
        this.target = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
        this.overlook = parcel.readFloat();
        this.zoom = parcel.readFloat();
        this.targetScreen = (Point) parcel.readParcelable(Point.class.getClassLoader());
        this.bound = (LatLngBounds) parcel.readParcelable(LatLngBounds.class.getClassLoader());
        this.f1154b = parcel.readDouble();
        this.f1155c = parcel.readDouble();
    }

    static MapStatus m1145a(C0616D c0616d) {
        if (c0616d == null) {
            return null;
        }
        float f = (float) c0616d.f1964b;
        double d = c0616d.f1967e;
        double d2 = c0616d.f1966d;
        LatLng mc2ll = CoordUtil.mc2ll(new GeoPoint(d, d2));
        float f2 = (float) c0616d.f1965c;
        float f3 = c0616d.f1963a;
        Point point = new Point(c0616d.f1968f, c0616d.f1969g);
        LatLng mc2ll2 = CoordUtil.mc2ll(new GeoPoint((double) c0616d.f1973k.f1952e.f1466y, (double) c0616d.f1973k.f1952e.f1465x));
        LatLng mc2ll3 = CoordUtil.mc2ll(new GeoPoint((double) c0616d.f1973k.f1953f.f1466y, (double) c0616d.f1973k.f1953f.f1465x));
        LatLng mc2ll4 = CoordUtil.mc2ll(new GeoPoint((double) c0616d.f1973k.f1955h.f1466y, (double) c0616d.f1973k.f1955h.f1465x));
        LatLng mc2ll5 = CoordUtil.mc2ll(new GeoPoint((double) c0616d.f1973k.f1954g.f1466y, (double) c0616d.f1973k.f1954g.f1465x));
        com.baidu.mapapi.model.LatLngBounds.Builder builder = new com.baidu.mapapi.model.LatLngBounds.Builder();
        builder.include(mc2ll2);
        builder.include(mc2ll3);
        builder.include(mc2ll4);
        builder.include(mc2ll5);
        return new MapStatus(f, mc2ll, f2, f3, point, c0616d, d2, d, builder.build());
    }

    double m1146a() {
        return this.f1154b;
    }

    double m1147b() {
        return this.f1155c;
    }

    C0616D m1148b(C0616D c0616d) {
        if (this.rotate != -2.14748365E9f) {
            c0616d.f1964b = (int) this.rotate;
        }
        if (this.zoom != -2.14748365E9f) {
            c0616d.f1963a = this.zoom;
        }
        if (this.overlook != -2.14748365E9f) {
            c0616d.f1965c = (int) this.overlook;
        }
        if (this.target != null) {
            CoordUtil.ll2mc(this.target);
            c0616d.f1966d = this.f1154b;
            c0616d.f1967e = this.f1155c;
        }
        if (this.targetScreen != null) {
            c0616d.f1968f = this.targetScreen.x;
            c0616d.f1969g = this.targetScreen.y;
        }
        return c0616d;
    }

    C0616D m1149c() {
        return m1148b(new C0616D());
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.target != null) {
            stringBuilder.append("target lat: " + this.target.latitude + "\n");
            stringBuilder.append("target lng: " + this.target.longitude + "\n");
        }
        if (this.targetScreen != null) {
            stringBuilder.append("target screen x: " + this.targetScreen.x + "\n");
            stringBuilder.append("target screen y: " + this.targetScreen.y + "\n");
        }
        stringBuilder.append("zoom: " + this.zoom + "\n");
        stringBuilder.append("rotate: " + this.rotate + "\n");
        stringBuilder.append("overlook: " + this.overlook + "\n");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.rotate);
        parcel.writeParcelable(this.target, i);
        parcel.writeFloat(this.overlook);
        parcel.writeFloat(this.zoom);
        parcel.writeParcelable(this.targetScreen, i);
        parcel.writeParcelable(this.bound, i);
        parcel.writeDouble(this.f1154b);
        parcel.writeDouble(this.f1155c);
    }
}
