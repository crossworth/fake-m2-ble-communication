package com.amap.api.maps.overlay;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AMapServicesUtil */
class C0313a {
    public static int f1015a = 2048;

    C0313a() {
    }

    public static LatLng m1131a(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    public static ArrayList<LatLng> m1132a(List<LatLonPoint> list) {
        ArrayList<LatLng> arrayList = new ArrayList();
        for (LatLonPoint a : list) {
            arrayList.add(C0313a.m1131a(a));
        }
        return arrayList;
    }
}
