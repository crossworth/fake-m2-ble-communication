package com.amap.api.maps.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RouteOverlay */
class C0314b {
    private Context f1016a;
    protected List<Polyline> allPolyLines = new ArrayList();
    private Bitmap f1017b;
    private Bitmap f1018c;
    private Bitmap f1019d;
    private Bitmap f1020e;
    protected Marker endMarker;
    protected LatLng endPoint;
    private Bitmap f1021f;
    protected AMap mAMap;
    protected boolean nodeIconVisible = true;
    protected Marker startMarker;
    protected LatLng startPoint;
    protected List<Marker> stationMarkers = new ArrayList();

    public C0314b(Context context) {
        this.f1016a = context;
    }

    public void removeFromMap() {
        if (this.startMarker != null) {
            this.startMarker.remove();
        }
        if (this.endMarker != null) {
            this.endMarker.remove();
        }
        for (Marker remove : this.stationMarkers) {
            remove.remove();
        }
        for (Polyline remove2 : this.allPolyLines) {
            remove2.remove();
        }
        m1134a();
    }

    private void m1134a() {
        if (this.f1017b != null) {
            this.f1017b.recycle();
            this.f1017b = null;
        }
        if (this.f1018c != null) {
            this.f1018c.recycle();
            this.f1018c = null;
        }
        if (this.f1019d != null) {
            this.f1019d.recycle();
            this.f1019d = null;
        }
        if (this.f1020e != null) {
            this.f1020e.recycle();
            this.f1020e = null;
        }
        if (this.f1021f != null) {
            this.f1021f.recycle();
            this.f1021f = null;
        }
    }

    protected BitmapDescriptor getStartBitmapDescriptor() {
        return m1133a(this.f1017b, "amap_start.png");
    }

    protected BitmapDescriptor getEndBitmapDescriptor() {
        return m1133a(this.f1018c, "amap_end.png");
    }

    protected BitmapDescriptor getBusBitmapDescriptor() {
        return m1133a(this.f1019d, "amap_bus.png");
    }

    protected BitmapDescriptor getWalkBitmapDescriptor() {
        return m1133a(this.f1020e, "amap_man.png");
    }

    protected BitmapDescriptor getDriveBitmapDescriptor() {
        return m1133a(this.f1021f, "amap_car.png");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.amap.api.maps.model.BitmapDescriptor m1133a(android.graphics.Bitmap r4, java.lang.String r5) {
        /*
        r3 = this;
        r1 = 0;
        r0 = r3.f1016a;	 Catch:{ IOException -> 0x0027, all -> 0x0039 }
        r0 = com.amap.api.mapcore.util.dh.m547a(r0);	 Catch:{ IOException -> 0x0027, all -> 0x0039 }
        r1 = r0.open(r5);	 Catch:{ IOException -> 0x0027, all -> 0x0039 }
        r4 = android.graphics.BitmapFactory.decodeStream(r1);	 Catch:{ IOException -> 0x0048, all -> 0x0039 }
        r0 = com.amap.api.mapcore.util.C0273r.f694a;	 Catch:{ IOException -> 0x0048, all -> 0x0039 }
        r0 = com.amap.api.mapcore.util.dj.m565a(r4, r0);	 Catch:{ IOException -> 0x0048, all -> 0x0039 }
        if (r1 == 0) goto L_0x001a;
    L_0x0017:
        r1.close();	 Catch:{ IOException -> 0x0022 }
    L_0x001a:
        r1 = com.amap.api.maps.model.BitmapDescriptorFactory.fromBitmap(r0);
        r0.recycle();
        return r1;
    L_0x0022:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001a;
    L_0x0027:
        r0 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r4;
    L_0x002b:
        r1.printStackTrace();	 Catch:{ all -> 0x0045 }
        if (r2 == 0) goto L_0x001a;
    L_0x0030:
        r2.close();	 Catch:{ IOException -> 0x0034 }
        goto L_0x001a;
    L_0x0034:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001a;
    L_0x0039:
        r0 = move-exception;
    L_0x003a:
        if (r1 == 0) goto L_0x003f;
    L_0x003c:
        r1.close();	 Catch:{ IOException -> 0x0040 }
    L_0x003f:
        throw r0;
    L_0x0040:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x003f;
    L_0x0045:
        r0 = move-exception;
        r1 = r2;
        goto L_0x003a;
    L_0x0048:
        r0 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.maps.overlay.b.a(android.graphics.Bitmap, java.lang.String):com.amap.api.maps.model.BitmapDescriptor");
    }

    protected void addStartAndEndMarker() {
        this.startMarker = this.mAMap.addMarker(new MarkerOptions().position(this.startPoint).icon(getStartBitmapDescriptor()).title("起点"));
        this.endMarker = this.mAMap.addMarker(new MarkerOptions().position(this.endPoint).icon(getEndBitmapDescriptor()).title("终点"));
    }

    public void zoomToSpan() {
        if (this.startPoint != null && this.mAMap != null) {
            try {
                this.mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(), 50));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    protected LatLngBounds getLatLngBounds() {
        Builder builder = LatLngBounds.builder();
        builder.include(new LatLng(this.startPoint.latitude, this.startPoint.longitude));
        builder.include(new LatLng(this.endPoint.latitude, this.endPoint.longitude));
        return builder.build();
    }

    public void setNodeIconVisibility(boolean z) {
        try {
            this.nodeIconVisible = z;
            if (this.stationMarkers != null && this.stationMarkers.size() > 0) {
                for (int i = 0; i < this.stationMarkers.size(); i++) {
                    ((Marker) this.stationMarkers.get(i)).setVisible(z);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    protected void addStationMarker(MarkerOptions markerOptions) {
        if (markerOptions != null) {
            Marker addMarker = this.mAMap.addMarker(markerOptions);
            if (addMarker != null) {
                this.stationMarkers.add(addMarker);
            }
        }
    }

    protected void addPolyLine(PolylineOptions polylineOptions) {
        if (polylineOptions != null) {
            Polyline addPolyline = this.mAMap.addPolyline(polylineOptions);
            if (addPolyline != null) {
                this.allPolyLines.add(addPolyline);
            }
        }
    }

    protected float getRouteWidth() {
        return 18.0f;
    }

    protected int getWalkColor() {
        return Color.parseColor("#6db74d");
    }

    protected int getBusColor() {
        return Color.parseColor("#537edc");
    }

    protected int getDriveColor() {
        return Color.parseColor("#537edc");
    }
}
