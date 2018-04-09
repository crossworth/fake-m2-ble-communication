package com.baidu.mapapi.utils;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.platform.comapi.location.CoordinateType;

public class CoordinateConverter {
    private LatLng f1824a;
    private CoordType f1825b;

    public enum CoordType {
        GPS,
        COMMON
    }

    private static LatLng m1777a(LatLng latLng) {
        return m1778a(latLng, CoordinateType.WGS84);
    }

    private static LatLng m1778a(LatLng latLng, String str) {
        if (latLng == null) {
            return null;
        }
        Point Coordinate_encryptEx = CoordUtil.Coordinate_encryptEx((float) latLng.longitude, (float) latLng.latitude, str);
        return Coordinate_encryptEx != null ? CoordUtil.mc2ll(new GeoPoint((double) Coordinate_encryptEx.getmPty(), (double) Coordinate_encryptEx.getmPtx())) : null;
    }

    private static LatLng m1779b(LatLng latLng) {
        return m1778a(latLng, CoordinateType.GCJ02);
    }

    public LatLng convert() {
        if (this.f1824a == null) {
            return null;
        }
        if (this.f1825b == null) {
            this.f1825b = CoordType.GPS;
        }
        switch (this.f1825b) {
            case COMMON:
                return m1779b(this.f1824a);
            case GPS:
                return m1777a(this.f1824a);
            default:
                return null;
        }
    }

    public CoordinateConverter coord(LatLng latLng) {
        this.f1824a = latLng;
        return this;
    }

    public CoordinateConverter from(CoordType coordType) {
        this.f1825b = coordType;
        return this;
    }
}
