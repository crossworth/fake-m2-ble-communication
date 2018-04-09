package com.baidu.mapapi.map;

import android.graphics.Point;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0633e;

public final class MapStatusUpdate {
    private static final String f1156l = MapStatusUpdate.class.getSimpleName();
    int f1157a;
    MapStatus f1158b;
    LatLng f1159c;
    LatLngBounds f1160d;
    int f1161e;
    int f1162f;
    float f1163g;
    int f1164h;
    int f1165i;
    float f1166j;
    Point f1167k;

    MapStatusUpdate() {
    }

    MapStatusUpdate(int i) {
        this.f1157a = i;
    }

    MapStatus m1150a(C0633e c0633e, MapStatus mapStatus) {
        GeoPoint ll2mc;
        GeoPoint ll2mc2;
        float a;
        switch (this.f1157a) {
            case 1:
                return this.f1158b;
            case 2:
                return new MapStatus(mapStatus.rotate, this.f1159c, mapStatus.overlook, mapStatus.zoom, mapStatus.targetScreen, null);
            case 3:
                ll2mc = CoordUtil.ll2mc(this.f1160d.southwest);
                ll2mc2 = CoordUtil.ll2mc(this.f1160d.northeast);
                double longitudeE6 = ll2mc.getLongitudeE6();
                double latitudeE6 = ll2mc2.getLatitudeE6();
                double longitudeE62 = ll2mc2.getLongitudeE6();
                a = c0633e.m1963a((int) longitudeE6, (int) latitudeE6, (int) longitudeE62, (int) ll2mc.getLatitudeE6(), mapStatus.f1153a.f1972j.f1958b - mapStatus.f1153a.f1972j.f1957a, mapStatus.f1153a.f1972j.f1960d - mapStatus.f1153a.f1972j.f1959c);
                return new MapStatus(mapStatus.rotate, this.f1160d.getCenter(), mapStatus.overlook, a, mapStatus.targetScreen, null);
            case 4:
                return new MapStatus(mapStatus.rotate, this.f1159c, mapStatus.overlook, this.f1163g, mapStatus.targetScreen, null);
            case 5:
                c0633e.m1955F();
                GeoPoint b = c0633e.m1988b((c0633e.m1955F() / 2) + this.f1164h, (c0633e.m1956G() / 2) + this.f1165i);
                return new MapStatus(mapStatus.rotate, CoordUtil.mc2ll(b), mapStatus.overlook, mapStatus.zoom, mapStatus.targetScreen, b.getLongitudeE6(), b.getLatitudeE6(), null);
            case 6:
                return new MapStatus(mapStatus.rotate, mapStatus.target, mapStatus.overlook, mapStatus.zoom + this.f1166j, mapStatus.targetScreen, mapStatus.m1146a(), mapStatus.m1147b(), null);
            case 7:
                return new MapStatus(mapStatus.rotate, CoordUtil.mc2ll(c0633e.m1988b(this.f1167k.x, this.f1167k.y)), mapStatus.overlook, mapStatus.zoom + this.f1166j, this.f1167k, null);
            case 8:
                return new MapStatus(mapStatus.rotate, mapStatus.target, mapStatus.overlook, this.f1163g, mapStatus.targetScreen, mapStatus.m1146a(), mapStatus.m1147b(), null);
            case 9:
                ll2mc = CoordUtil.ll2mc(this.f1160d.southwest);
                ll2mc2 = CoordUtil.ll2mc(this.f1160d.northeast);
                C0633e c0633e2 = c0633e;
                a = c0633e2.m1963a((int) ll2mc.getLongitudeE6(), (int) ll2mc2.getLatitudeE6(), (int) ll2mc2.getLongitudeE6(), (int) ll2mc.getLatitudeE6(), this.f1161e, this.f1162f);
                return new MapStatus(mapStatus.rotate, this.f1160d.getCenter(), mapStatus.overlook, a, mapStatus.targetScreen, null);
            default:
                return null;
        }
    }
}
