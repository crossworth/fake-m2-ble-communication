package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;
import java.util.ArrayList;

public final class MarkerOptions extends OverlayOptions {
    int f1223a;
    boolean f1224b = true;
    Bundle f1225c;
    private LatLng f1226d;
    private BitmapDescriptor f1227e;
    private float f1228f = 0.5f;
    private float f1229g = 1.0f;
    private boolean f1230h = true;
    private boolean f1231i = false;
    private float f1232j;
    private String f1233k;
    private int f1234l;
    private boolean f1235m = false;
    private ArrayList<BitmapDescriptor> f1236n;
    private int f1237o = 20;
    private float f1238p = 1.0f;
    private int f1239q = MarkerAnimateType.none.ordinal();

    public enum MarkerAnimateType {
        none,
        drop,
        grow
    }

    MarkerOptions m1168a(int i) {
        this.f1234l = i;
        return this;
    }

    Overlay mo1760a() {
        Overlay marker = new Marker();
        marker.s = this.f1224b;
        marker.r = this.f1223a;
        marker.t = this.f1225c;
        if (this.f1226d == null) {
            throw new IllegalStateException("when you add marker, you must set the position");
        }
        marker.f1207a = this.f1226d;
        if (this.f1227e == null && this.f1236n == null) {
            throw new IllegalStateException("when you add marker, you must set the icon or icons");
        }
        marker.f1208b = this.f1227e;
        marker.f1209c = this.f1228f;
        marker.f1210d = this.f1229g;
        marker.f1211e = this.f1230h;
        marker.f1212f = this.f1231i;
        marker.f1213g = this.f1232j;
        marker.f1214h = this.f1233k;
        marker.f1215i = this.f1234l;
        marker.f1216j = this.f1235m;
        marker.f1220n = this.f1236n;
        marker.f1221o = this.f1237o;
        marker.f1218l = this.f1238p;
        marker.f1219m = this.f1239q;
        return marker;
    }

    public MarkerOptions alpha(float f) {
        if (f < 0.0f || f > 1.0f) {
            this.f1238p = 1.0f;
        } else {
            this.f1238p = f;
        }
        return this;
    }

    public MarkerOptions anchor(float f, float f2) {
        if (f >= 0.0f && f <= 1.0f && f2 >= 0.0f && f2 <= 1.0f) {
            this.f1228f = f;
            this.f1229g = f2;
        }
        return this;
    }

    public MarkerOptions animateType(MarkerAnimateType markerAnimateType) {
        if (markerAnimateType == null) {
            markerAnimateType = MarkerAnimateType.none;
        }
        this.f1239q = markerAnimateType.ordinal();
        return this;
    }

    public MarkerOptions draggable(boolean z) {
        this.f1231i = z;
        return this;
    }

    public MarkerOptions extraInfo(Bundle bundle) {
        this.f1225c = bundle;
        return this;
    }

    public MarkerOptions flat(boolean z) {
        this.f1235m = z;
        return this;
    }

    public float getAlpha() {
        return this.f1238p;
    }

    public float getAnchorX() {
        return this.f1228f;
    }

    public float getAnchorY() {
        return this.f1229g;
    }

    public MarkerAnimateType getAnimateType() {
        switch (this.f1239q) {
            case 1:
                return MarkerAnimateType.drop;
            case 2:
                return MarkerAnimateType.grow;
            default:
                return MarkerAnimateType.none;
        }
    }

    public Bundle getExtraInfo() {
        return this.f1225c;
    }

    public BitmapDescriptor getIcon() {
        return this.f1227e;
    }

    public ArrayList<BitmapDescriptor> getIcons() {
        return this.f1236n;
    }

    public int getPeriod() {
        return this.f1237o;
    }

    public LatLng getPosition() {
        return this.f1226d;
    }

    public float getRotate() {
        return this.f1232j;
    }

    public String getTitle() {
        return this.f1233k;
    }

    public int getZIndex() {
        return this.f1223a;
    }

    public MarkerOptions icon(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("marker's icon can not be null");
        }
        this.f1227e = bitmapDescriptor;
        return this;
    }

    public MarkerOptions icons(ArrayList<BitmapDescriptor> arrayList) {
        if (arrayList == null) {
            throw new IllegalArgumentException("marker's icons can not be null");
        }
        if (arrayList.size() != 0) {
            int i = 0;
            while (i < arrayList.size()) {
                if (arrayList.get(i) == null || ((BitmapDescriptor) arrayList.get(i)).f1053a == null) {
                    break;
                }
                i++;
            }
            this.f1236n = arrayList;
        }
        return this;
    }

    public boolean isDraggable() {
        return this.f1231i;
    }

    public boolean isFlat() {
        return this.f1235m;
    }

    public boolean isPerspective() {
        return this.f1230h;
    }

    public boolean isVisible() {
        return this.f1224b;
    }

    public MarkerOptions period(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("marker's period must be greater than zero ");
        }
        this.f1237o = i;
        return this;
    }

    public MarkerOptions perspective(boolean z) {
        this.f1230h = z;
        return this;
    }

    public MarkerOptions position(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("marker's position can not be null");
        }
        this.f1226d = latLng;
        return this;
    }

    public MarkerOptions rotate(float f) {
        while (f < 0.0f) {
            f += 360.0f;
        }
        this.f1232j = f % 360.0f;
        return this;
    }

    public MarkerOptions title(String str) {
        this.f1233k = str;
        return this;
    }

    public MarkerOptions visible(boolean z) {
        this.f1224b = z;
        return this;
    }

    public MarkerOptions zIndex(int i) {
        this.f1223a = i;
        return this;
    }
}
