package com.amap.api.maps.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.amap.api.mapcore.util.C0273r;
import com.amap.api.mapcore.util.dh;
import com.amap.api.mapcore.util.dj;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DrivingRouteOverlay extends C0314b {
    private DrivePath f4256a;
    private List<LatLonPoint> f4257b;
    private List<Marker> f4258c;
    private boolean f4259d;
    private Context f4260e;
    private PolylineOptions f4261f;

    public /* bridge */ /* synthetic */ void setNodeIconVisibility(boolean z) {
        super.setNodeIconVisibility(z);
    }

    public /* bridge */ /* synthetic */ void zoomToSpan() {
        super.zoomToSpan();
    }

    public DrivingRouteOverlay(Context context, AMap aMap, DrivePath drivePath, LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
        this(context, aMap, drivePath, latLonPoint, latLonPoint2, null);
        this.f4260e = context;
    }

    public DrivingRouteOverlay(Context context, AMap aMap, DrivePath drivePath, LatLonPoint latLonPoint, LatLonPoint latLonPoint2, List<LatLonPoint> list) {
        super(context);
        this.f4258c = new ArrayList();
        this.f4259d = true;
        this.mAMap = aMap;
        this.f4256a = drivePath;
        this.startPoint = C0313a.m1131a(latLonPoint);
        this.endPoint = C0313a.m1131a(latLonPoint2);
        this.f4257b = list;
        this.f4260e = context;
    }

    public void addToMap() {
        m4339a();
        try {
            List steps = this.f4256a.getSteps();
            int i = 0;
            while (i < steps.size()) {
                DriveStep driveStep = (DriveStep) steps.get(i);
                LatLng a = C0313a.m1131a(m4338a(driveStep));
                if (i < steps.size() - 1 && i == 0) {
                    m4340a(this.startPoint, a);
                }
                m4342a(driveStep, a);
                m4346c(driveStep);
                if (i == steps.size() - 1) {
                    m4341a(m4343b(driveStep), this.endPoint);
                }
                i++;
            }
            addStartAndEndMarker();
            m4345c();
            m4344b();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m4339a() {
        this.f4261f = null;
        this.f4261f = new PolylineOptions();
        this.f4261f.color(getDriveColor()).width(getRouteWidth());
    }

    private void m4344b() {
        addPolyLine(this.f4261f);
    }

    private LatLonPoint m4338a(DriveStep driveStep) {
        return (LatLonPoint) driveStep.getPolyline().get(0);
    }

    private LatLonPoint m4343b(DriveStep driveStep) {
        return (LatLonPoint) driveStep.getPolyline().get(driveStep.getPolyline().size() - 1);
    }

    private void m4341a(LatLonPoint latLonPoint, LatLng latLng) {
        m4340a(C0313a.m1131a(latLonPoint), latLng);
    }

    private void m4340a(LatLng latLng, LatLng latLng2) {
        this.f4261f.add(latLng, latLng2);
    }

    private void m4346c(DriveStep driveStep) {
        this.f4261f.addAll(C0313a.m1132a(driveStep.getPolyline()));
    }

    private void m4342a(DriveStep driveStep, LatLng latLng) {
        addStationMarker(new MarkerOptions().position(latLng).title("方向:" + driveStep.getAction() + "\n道路:" + driveStep.getRoad()).snippet(driveStep.getInstruction()).visible(this.nodeIconVisible).anchor(0.5f, 0.5f).icon(getDriveBitmapDescriptor()));
    }

    private void m4345c() {
        if (this.f4257b != null && this.f4257b.size() > 0) {
            for (int i = 0; i < this.f4257b.size(); i++) {
                LatLonPoint latLonPoint = (LatLonPoint) this.f4257b.get(i);
                if (latLonPoint != null) {
                    this.f4258c.add(this.mAMap.addMarker(new MarkerOptions().position(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude())).visible(this.f4259d).icon(m4347d()).title("途经点")));
                }
            }
        }
    }

    protected LatLngBounds getLatLngBounds() {
        Builder builder = LatLngBounds.builder();
        builder.include(new LatLng(this.startPoint.latitude, this.startPoint.longitude));
        builder.include(new LatLng(this.endPoint.latitude, this.endPoint.longitude));
        if (this.f4257b != null && this.f4257b.size() > 0) {
            for (int i = 0; i < this.f4257b.size(); i++) {
                builder.include(new LatLng(((LatLonPoint) this.f4257b.get(i)).getLatitude(), ((LatLonPoint) this.f4257b.get(i)).getLongitude()));
            }
        }
        return builder.build();
    }

    public void setThroughPointIconVisibility(boolean z) {
        try {
            this.f4259d = z;
            if (this.f4258c != null && this.f4258c.size() > 0) {
                for (int i = 0; i < this.f4258c.size(); i++) {
                    ((Marker) this.f4258c.get(i)).setVisible(z);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private BitmapDescriptor m4347d() {
        InputStream open;
        Throwable th;
        Throwable th2;
        Throwable th3;
        BitmapDescriptor fromBitmap;
        Bitmap a;
        InputStream inputStream = null;
        try {
            Bitmap decodeStream;
            open = dh.m547a(this.f4260e).open("amap_throughpoint.png");
            try {
                decodeStream = BitmapFactory.decodeStream(open);
            } catch (Throwable th22) {
                th = th22;
                Object obj = inputStream;
                th3 = th;
                try {
                    th3.printStackTrace();
                    if (open != null) {
                        try {
                            open.close();
                        } catch (Throwable th32) {
                            th32.printStackTrace();
                        }
                    }
                    fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                    a.recycle();
                    return fromBitmap;
                } catch (Throwable th4) {
                    th22 = th4;
                    if (open != null) {
                        try {
                            open.close();
                        } catch (Throwable th322) {
                            th322.printStackTrace();
                        }
                    }
                    throw th22;
                }
            }
            try {
                a = dj.m565a(decodeStream, C0273r.f694a);
                if (open != null) {
                    try {
                        open.close();
                    } catch (Throwable th3222) {
                        th3222.printStackTrace();
                    }
                }
            } catch (Throwable th222) {
                th = th222;
                a = decodeStream;
                th3222 = th;
                th3222.printStackTrace();
                if (open != null) {
                    open.close();
                }
                fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                a.recycle();
                return fromBitmap;
            }
        } catch (Throwable th5) {
            th222 = th5;
            open = inputStream;
            if (open != null) {
                open.close();
            }
            throw th222;
        }
        fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
        a.recycle();
        return fromBitmap;
    }

    public void removeFromMap() {
        try {
            super.removeFromMap();
            if (this.f4258c != null && this.f4258c.size() > 0) {
                for (int i = 0; i < this.f4258c.size(); i++) {
                    ((Marker) this.f4258c.get(i)).remove();
                }
                this.f4258c.clear();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
