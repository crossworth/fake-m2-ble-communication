package com.baidu.mapapi.map;

import android.graphics.Point;
import android.graphics.PointF;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0616D;
import com.baidu.platform.comapi.map.C0633e;

public final class Projection {
    private C0633e f1279a;

    Projection(C0633e c0633e) {
        this.f1279a = c0633e;
    }

    public LatLng fromScreenLocation(Point point) {
        return (point == null || this.f1279a == null) ? null : CoordUtil.mc2ll(this.f1279a.m1988b(point.x, point.y));
    }

    public float metersToEquatorPixels(float f) {
        return f <= 0.0f ? 0.0f : (float) (((double) f) / this.f1279a.m1958I());
    }

    public PointF toOpenGLLocation(LatLng latLng, MapStatus mapStatus) {
        if (latLng == null || mapStatus == null) {
            return null;
        }
        GeoPoint ll2mc = CoordUtil.ll2mc(latLng);
        C0616D c0616d = mapStatus.f1153a;
        return new PointF((float) ((ll2mc.getLongitudeE6() - c0616d.f1966d) / c0616d.f1976n), (float) ((ll2mc.getLatitudeE6() - c0616d.f1967e) / c0616d.f1976n));
    }

    public Point toScreenLocation(LatLng latLng) {
        if (latLng == null || this.f1279a == null) {
            return null;
        }
        return this.f1279a.m1966a(CoordUtil.ll2mc(latLng));
    }
}
