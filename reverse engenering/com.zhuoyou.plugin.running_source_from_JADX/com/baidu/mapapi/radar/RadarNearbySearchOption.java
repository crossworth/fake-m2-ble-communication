package com.baidu.mapapi.radar;

import com.baidu.mapapi.model.LatLng;
import java.util.Date;

public class RadarNearbySearchOption {
    int f1476a = -1;
    int f1477b = 0;
    LatLng f1478c;
    int f1479d = 10;
    RadarNearbySearchSortType f1480e = RadarNearbySearchSortType.distance_from_near_to_far;
    Date f1481f;
    Date f1482g;

    public RadarNearbySearchOption centerPt(LatLng latLng) {
        if (latLng != null) {
            this.f1478c = latLng;
        }
        return this;
    }

    public RadarNearbySearchOption pageCapacity(int i) {
        this.f1479d = i;
        return this;
    }

    public RadarNearbySearchOption pageNum(int i) {
        this.f1477b = i;
        return this;
    }

    public RadarNearbySearchOption radius(int i) {
        this.f1476a = i;
        return this;
    }

    public RadarNearbySearchOption sortType(RadarNearbySearchSortType radarNearbySearchSortType) {
        if (radarNearbySearchSortType != null) {
            this.f1480e = radarNearbySearchSortType;
        }
        return this;
    }

    public RadarNearbySearchOption timeRange(Date date, Date date2) {
        if (!(date == null || date2 == null)) {
            this.f1481f = date;
            this.f1482g = date2;
        }
        return this;
    }
}
