package com.baidu.mapapi.model;

import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.platform.comapi.util.C0667b;
import com.baidu.platform.comjni.tools.C0680a;
import java.util.List;

public class CoordUtil {
    public static Point Coordinate_encryptEx(float f, float f2, String str) {
        return C0667b.m2144a(f, f2, str);
    }

    public static LatLng decodeLocation(String str) {
        return C0667b.m2142a(str);
    }

    public static List<LatLng> decodeLocationList(String str) {
        return C0667b.m2148c(str);
    }

    public static List<List<LatLng>> decodeLocationList2D(String str) {
        return C0667b.m2149d(str);
    }

    public static LatLng decodeNodeLocation(String str) {
        return C0667b.m2146b(str);
    }

    public static double getDistance(Point point, Point point2) {
        return C0680a.m2302a(point, point2);
    }

    public static int getMCDistanceByOneLatLngAndRadius(LatLng latLng, int i) {
        return C0667b.m2140a(latLng, i);
    }

    public static GeoPoint ll2mc(LatLng latLng) {
        return C0667b.m2143a(latLng);
    }

    public static Point ll2point(LatLng latLng) {
        return C0667b.m2147b(latLng);
    }

    public static LatLng mc2ll(GeoPoint geoPoint) {
        return C0667b.m2141a(geoPoint);
    }
}
