package com.baidu.mapapi.map;

import android.view.View;
import com.baidu.mapapi.model.LatLng;

public class InfoWindow {
    BitmapDescriptor f1127a;
    View f1128b;
    LatLng f1129c;
    OnInfoWindowClickListener f1130d;
    int f1131e;

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick();
    }

    public InfoWindow(View view, LatLng latLng, int i) {
        if (view == null || latLng == null) {
            throw new IllegalArgumentException("view and position can not be null");
        }
        this.f1128b = view;
        this.f1129c = latLng;
        this.f1131e = i;
    }

    public InfoWindow(BitmapDescriptor bitmapDescriptor, LatLng latLng, int i, OnInfoWindowClickListener onInfoWindowClickListener) {
        if (bitmapDescriptor == null || latLng == null) {
            throw new IllegalArgumentException("bitmapDescriptor and position can not be null");
        }
        this.f1127a = bitmapDescriptor;
        this.f1129c = latLng;
        this.f1130d = onInfoWindowClickListener;
        this.f1131e = i;
    }
}
