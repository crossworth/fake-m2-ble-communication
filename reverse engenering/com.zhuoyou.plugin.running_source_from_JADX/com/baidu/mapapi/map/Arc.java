package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;
import java.util.ArrayList;
import java.util.List;
import org.andengine.util.level.constants.LevelConstants;

public final class Arc extends Overlay {
    private static final String f981f = Arc.class.getSimpleName();
    int f982a;
    int f983b;
    LatLng f984c;
    LatLng f985d;
    LatLng f986e;

    Arc() {
        this.q = C0636h.arc;
    }

    Bundle mo1759a(Bundle bundle) {
        super.mo1759a(bundle);
        List arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(this.f984c);
        arrayList.add(this.f985d);
        arrayList.add(this.f986e);
        GeoPoint ll2mc = CoordUtil.ll2mc((LatLng) arrayList.get(0));
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH, this.f983b);
        Overlay.m1058a(arrayList, bundle);
        Overlay.m1057a(this.f982a, bundle);
        return bundle;
    }

    public int getColor() {
        return this.f982a;
    }

    public LatLng getEndPoint() {
        return this.f986e;
    }

    public LatLng getMiddlePoint() {
        return this.f985d;
    }

    public LatLng getStartPoint() {
        return this.f984c;
    }

    public int getWidth() {
        return this.f983b;
    }

    public void setColor(int i) {
        this.f982a = i;
        this.listener.mo1769b(this);
    }

    public void setPoints(LatLng latLng, LatLng latLng2, LatLng latLng3) {
        if (latLng == null || latLng2 == null || latLng3 == null) {
            throw new IllegalArgumentException("start and middle and end points can not be null");
        } else if (latLng == latLng2 || latLng == latLng3 || latLng2 == latLng3) {
            throw new IllegalArgumentException("start and middle and end points can not be same");
        } else {
            this.f984c = latLng;
            this.f985d = latLng2;
            this.f986e = latLng3;
            this.listener.mo1769b(this);
        }
    }

    public void setWidth(int i) {
        if (i > 0) {
            this.f983b = i;
            this.listener.mo1769b(this);
        }
    }
}
