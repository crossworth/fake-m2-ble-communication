package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Message;
import android.os.RemoteException;
import com.amap.api.mapcore.indoor.IndoorBuilding;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.WeightedLatLng;
import com.autonavi.amap.mapcore.BaseMapCallImplement;
import com.autonavi.amap.mapcore.Convert;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapCore;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.MapSourceGridData;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate.Type;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: AMapCallback */
class C1965a extends BaseMapCallImplement {
    IPoint f5218a = new IPoint();
    float f5219b;
    float f5220c;
    float f5221d;
    IPoint f5222e = new IPoint();
    private C1592c f5223f;
    private float f5224g = GroundOverlayOptions.NO_DIMENSION;
    private int f5225h;
    private int f5226i;

    public String getMapSvrAddress() {
        return "http://mps.amap.com";
    }

    public C1965a(C1592c c1592c) {
        this.f5223f = c1592c;
    }

    public void OnMapSurfaceCreate(GL10 gl10, MapCore mapCore) {
        super.OnMapSurfaceCreate(mapCore);
    }

    public void OnMapSurfaceRenderer(GL10 gl10, MapCore mapCore, int i) {
        super.OnMapSurfaceRenderer(gl10, mapCore, i);
        if (i == 3) {
            this.f5223f.f4120h.m1030a(gl10, true, this.f5223f.getMapTextZIndex());
            if (this.f5223f.f4125m != null) {
                this.f5223f.f4125m.onDrawFrame(gl10);
            }
        }
    }

    public void OnMapSufaceChanged(GL10 gl10, MapCore mapCore, int i, int i2) {
    }

    public void OnMapProcessEvent(MapCore mapCore) {
        float f = 0.0f;
        if (this.f5223f != null && this.f5223f.isNeedRunDestroy()) {
            this.f5223f.runDestroy();
        }
        if (this.f5223f != null) {
            float zoomLevel = this.f5223f.getZoomLevel();
            m5643a(mapCore);
            while (true) {
                ac a = this.f5223f.f4117e.m133a();
                if (a == null) {
                    break;
                } else if (a.f134a == 2) {
                    if (a.m132a()) {
                        mapCore.setParameter(2010, 4, 0, 0, 0);
                    } else {
                        mapCore.setParameter(2010, 0, 0, 0, 0);
                    }
                }
            }
            mapCore.setMapstate(this.f5223f.getMapProjection());
            if (!(this.f5219b < this.f5223f.getMinZoomLevel() || this.f5224g == zoomLevel || this.f5223f.f4124l == null)) {
                this.f5223f.f4124l.obtainMessage(21).sendToTarget();
            }
            f = zoomLevel;
        }
        this.f5224g = f;
    }

    void m5643a(MapCore mapCore) {
        Object obj = null;
        MapProjection mapProjection = this.f5223f.getMapProjection();
        float mapZoomer = mapProjection.getMapZoomer();
        float cameraHeaderAngle = mapProjection.getCameraHeaderAngle();
        float mapAngle = mapProjection.getMapAngle();
        mapProjection.getGeoCenter(this.f5222e);
        int i = 0;
        while (this.f5223f.isDrawOnce()) {
            CameraUpdateFactoryDelegate c = this.f5223f.f4117e.m137c();
            if (c == null) {
                break;
            }
            try {
                m5644a(c);
                i |= c.isChangeFinished;
            } catch (Throwable e) {
                ee.m4243a(e, "AMapCallback", "runMessage");
                e.printStackTrace();
            }
        }
        this.f5219b = mapProjection.getMapZoomer();
        this.f5220c = mapProjection.getCameraHeaderAngle();
        this.f5221d = mapProjection.getMapAngle();
        mapProjection.getGeoCenter(this.f5218a);
        if (!(mapZoomer == this.f5219b && this.f5220c == cameraHeaderAngle && this.f5221d == mapAngle && this.f5218a.f2030x == this.f5222e.f2030x && this.f5218a.f2031y == this.f5222e.f2031y)) {
            obj = 1;
        }
        if (obj != null) {
            try {
                this.f5223f.setRunLowFrame(false);
                if (this.f5223f.getOnCameraChangeListener() != null) {
                    DPoint dPoint = new DPoint();
                    MapProjection.geo2LonLat(this.f5218a.f2030x, this.f5218a.f2031y, dPoint);
                    this.f5223f.m4144a(new CameraPosition(new LatLng(dPoint.f2027y, dPoint.f2026x, false), this.f5219b, this.f5220c, this.f5221d));
                }
                this.f5223f.m4151e();
            } catch (Throwable e2) {
                ee.m4243a(e2, "AMapCallback", "runMessage cameraChange");
                e2.printStackTrace();
                return;
            }
        }
        this.f5223f.setRunLowFrame(true);
        if (i != 0) {
            if (i != 0) {
                this.f5223f.m4146a(true);
            } else {
                this.f5223f.m4146a(false);
            }
            Message message = new Message();
            message.what = 17;
            this.f5223f.f4124l.sendMessage(message);
        }
        if (!(this.f5220c == cameraHeaderAngle && this.f5221d == mapAngle) && this.f5223f.getUiSettings().isCompassEnabled()) {
            this.f5223f.m4139a();
        }
        if (this.f5223f.getUiSettings().isScaleControlsEnabled()) {
            this.f5223f.m4148b();
        }
    }

    private void m5639b(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) {
        MapCore mapCore = this.f5223f.getMapCore();
        MapProjection mapProjection = this.f5223f.getMapProjection();
        LatLngBounds latLngBounds = cameraUpdateFactoryDelegate.bounds;
        int i = cameraUpdateFactoryDelegate.width;
        int i2 = cameraUpdateFactoryDelegate.height;
        int i3 = cameraUpdateFactoryDelegate.padding;
        IPoint iPoint = new IPoint();
        IPoint iPoint2 = new IPoint();
        MapProjection.lonlat2Geo(latLngBounds.northeast.longitude, latLngBounds.northeast.latitude, iPoint);
        MapProjection.lonlat2Geo(latLngBounds.southwest.longitude, latLngBounds.southwest.latitude, iPoint2);
        int i4 = iPoint2.f2031y - iPoint.f2031y;
        int i5 = i - (i3 * 2);
        i = i2 - (i3 * 2);
        if (iPoint.f2030x - iPoint2.f2030x >= 0 || i4 >= 0) {
            if (i5 <= 0) {
                i5 = 1;
            }
            if (i <= 0) {
                i = 1;
            }
            float a = m5629a(latLngBounds.northeast, latLngBounds.southwest, i5, i);
            i5 = (iPoint.f2030x + iPoint2.f2030x) / 2;
            int i6 = (iPoint.f2031y + iPoint2.f2031y) / 2;
            mapProjection.setMapZoomer(a);
            mapProjection.setGeoCenter(i5, i6);
            mapProjection.setCameraHeaderAngle(0.0f);
            mapProjection.setMapAngle(0.0f);
            mapCore.setMapstate(mapProjection);
        }
    }

    private float m5629a(LatLng latLng, LatLng latLng2, int i, int i2) {
        float a;
        MapProjection mapProjection = this.f5223f.getMapProjection();
        mapProjection.setMapAngle(0.0f);
        mapProjection.setCameraHeaderAngle(0.0f);
        mapProjection.recalculate();
        IPoint iPoint = new IPoint();
        IPoint iPoint2 = new IPoint();
        this.f5223f.getLatLng2Pixel(latLng.latitude, latLng.longitude, iPoint);
        this.f5223f.getLatLng2Pixel(latLng2.latitude, latLng2.longitude, iPoint2);
        double d = (double) (iPoint.f2030x - iPoint2.f2030x);
        double d2 = (double) (iPoint2.f2031y - iPoint.f2031y);
        if (d <= 0.0d) {
            d = WeightedLatLng.DEFAULT_INTENSITY;
        }
        if (d2 <= 0.0d) {
            d2 = WeightedLatLng.DEFAULT_INTENSITY;
        }
        d = Math.log(((double) i) / d) / Math.log(2.0d);
        double min = Math.min(d, Math.log(((double) i2) / d2) / Math.log(2.0d));
        Object obj = Math.abs(min - d) < 1.0E-7d ? 1 : null;
        float a2 = dj.m557a((float) (((double) mapProjection.getMapZoomer()) + Math.floor(min)));
        while (true) {
            a = dj.m557a((float) (((double) a2) + 0.1d));
            mapProjection.setMapZoomer(a);
            mapProjection.recalculate();
            this.f5223f.getLatLng2Pixel(latLng.latitude, latLng.longitude, iPoint);
            this.f5223f.getLatLng2Pixel(latLng2.latitude, latLng2.longitude, iPoint2);
            d = (double) (iPoint.f2030x - iPoint2.f2030x);
            min = (double) (iPoint2.f2031y - iPoint.f2031y);
            if (obj != null) {
                if (d >= ((double) i)) {
                    break;
                }
                if (a < C0273r.f699f) {
                    return a;
                }
                a2 = a;
            } else {
                if (min >= ((double) i2)) {
                    break;
                }
                if (a < C0273r.f699f) {
                    return a;
                }
                a2 = a;
            }
        }
        return (float) (((double) a) - 0.1d);
    }

    void m5644a(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) throws RemoteException {
        MapCore mapCore = this.f5223f.getMapCore();
        MapProjection mapProjection = this.f5223f.getMapProjection();
        mapProjection.recalculate();
        cameraUpdateFactoryDelegate.zoom = this.f5223f.checkZoomLevel(cameraUpdateFactoryDelegate.zoom);
        float checkZoomLevel;
        switch (C0201b.f216a[cameraUpdateFactoryDelegate.nowType.ordinal()]) {
            case 1:
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5634a(mapProjection, cameraUpdateFactoryDelegate.geoPoint);
                } else {
                    mapProjection.setGeoCenter(cameraUpdateFactoryDelegate.geoPoint.f2030x, cameraUpdateFactoryDelegate.geoPoint.f2031y);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 2:
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5641d(mapProjection, cameraUpdateFactoryDelegate);
                } else {
                    mapProjection.setMapAngle(cameraUpdateFactoryDelegate.bearing);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 3:
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5637a(mapProjection, cameraUpdateFactoryDelegate);
                } else {
                    mapProjection.setMapAngle(cameraUpdateFactoryDelegate.bearing);
                    mapProjection.setGeoCenter(cameraUpdateFactoryDelegate.geoPoint.f2030x, cameraUpdateFactoryDelegate.geoPoint.f2031y);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 4:
                cameraUpdateFactoryDelegate.tilt = dj.m558a(cameraUpdateFactoryDelegate.tilt, mapProjection.getMapZoomer());
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5640c(mapProjection, cameraUpdateFactoryDelegate);
                } else {
                    mapProjection.setCameraHeaderAngle(cameraUpdateFactoryDelegate.tilt);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 5:
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5638b(mapProjection, cameraUpdateFactoryDelegate);
                } else {
                    mapProjection.setGeoCenter(cameraUpdateFactoryDelegate.geoPoint.f2030x, cameraUpdateFactoryDelegate.geoPoint.f2031y);
                    mapProjection.setMapZoomer(cameraUpdateFactoryDelegate.zoom);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 6:
                LatLng latLng = cameraUpdateFactoryDelegate.cameraPosition.target;
                IPoint iPoint = new IPoint();
                MapProjection.lonlat2Geo(latLng.longitude, latLng.latitude, iPoint);
                float a = dj.m557a(cameraUpdateFactoryDelegate.cameraPosition.zoom);
                float f = cameraUpdateFactoryDelegate.cameraPosition.bearing;
                float a2 = dj.m558a(cameraUpdateFactoryDelegate.cameraPosition.tilt, a);
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5635a(mapProjection, iPoint, a, f, a2);
                } else {
                    mapProjection.setGeoCenter(iPoint.f2030x, iPoint.f2031y);
                    mapProjection.setMapZoomer(a);
                    mapProjection.setMapAngle(f);
                    mapProjection.setCameraHeaderAngle(a2);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 7:
                checkZoomLevel = this.f5223f.checkZoomLevel(mapProjection.getMapZoomer() + 1.0f);
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5632a(mapProjection, checkZoomLevel);
                } else {
                    mapProjection.setMapZoomer(checkZoomLevel);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 8:
                checkZoomLevel = this.f5223f.checkZoomLevel(mapProjection.getMapZoomer() - 1.0f);
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5632a(mapProjection, checkZoomLevel);
                } else {
                    mapProjection.setMapZoomer(checkZoomLevel);
                }
                mapProjection.setMapZoomer(checkZoomLevel);
                mapCore.setMapstate(mapProjection);
                return;
            case 9:
                checkZoomLevel = cameraUpdateFactoryDelegate.zoom;
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5632a(mapProjection, checkZoomLevel);
                } else {
                    mapProjection.setMapZoomer(checkZoomLevel);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 10:
                checkZoomLevel = this.f5223f.checkZoomLevel(mapProjection.getMapZoomer() + cameraUpdateFactoryDelegate.amount);
                Point point = cameraUpdateFactoryDelegate.focus;
                if (point != null) {
                    m5633a(mapProjection, checkZoomLevel, point.x, point.y);
                } else if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5632a(mapProjection, checkZoomLevel);
                } else {
                    mapProjection.setMapZoomer(checkZoomLevel);
                }
                mapCore.setMapstate(mapProjection);
                return;
            case 11:
                checkZoomLevel = cameraUpdateFactoryDelegate.xPixel;
                checkZoomLevel += ((float) this.f5223f.m4149c()) / 2.0f;
                float d = cameraUpdateFactoryDelegate.yPixel + (((float) this.f5223f.m4150d()) / 2.0f);
                IPoint iPoint2 = new IPoint();
                this.f5223f.getPixel2Geo((int) checkZoomLevel, (int) d, iPoint2);
                mapProjection.setGeoCenter(iPoint2.f2030x, iPoint2.f2031y);
                mapCore.setMapstate(mapProjection);
                return;
            case 12:
                cameraUpdateFactoryDelegate.nowType = Type.newLatLngBoundsWithSize;
                cameraUpdateFactoryDelegate.width = this.f5223f.m4149c();
                cameraUpdateFactoryDelegate.height = this.f5223f.m4150d();
                m5639b(cameraUpdateFactoryDelegate);
                return;
            case 13:
                m5639b(cameraUpdateFactoryDelegate);
                return;
            case 14:
                cameraUpdateFactoryDelegate.tilt = dj.m558a(cameraUpdateFactoryDelegate.tilt, cameraUpdateFactoryDelegate.zoom);
                if (cameraUpdateFactoryDelegate.isUseAnchor) {
                    m5635a(mapProjection, cameraUpdateFactoryDelegate.geoPoint, cameraUpdateFactoryDelegate.zoom, cameraUpdateFactoryDelegate.bearing, cameraUpdateFactoryDelegate.tilt);
                } else {
                    mapProjection.setGeoCenter(cameraUpdateFactoryDelegate.geoPoint.f2030x, cameraUpdateFactoryDelegate.geoPoint.f2031y);
                    mapProjection.setMapZoomer(cameraUpdateFactoryDelegate.zoom);
                    mapProjection.setMapAngle(cameraUpdateFactoryDelegate.bearing);
                    mapProjection.setCameraHeaderAngle(cameraUpdateFactoryDelegate.tilt);
                }
                mapCore.setMapstate(mapProjection);
                return;
            default:
                mapCore.setMapstate(mapProjection);
                return;
        }
    }

    private void m5637a(MapProjection mapProjection, CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) {
        mapProjection.setMapAngle(cameraUpdateFactoryDelegate.bearing);
        m5634a(mapProjection, cameraUpdateFactoryDelegate.geoPoint);
    }

    private void m5632a(MapProjection mapProjection, float f) {
        m5633a(mapProjection, f, this.f5225h, this.f5226i);
    }

    private void m5633a(MapProjection mapProjection, float f, int i, int i2) {
        IPoint a = m5631a(mapProjection, i, i2);
        mapProjection.setMapZoomer(f);
        m5636a(mapProjection, a, i, i2);
    }

    private void m5635a(MapProjection mapProjection, IPoint iPoint, float f, float f2, float f3) {
        mapProjection.setMapZoomer(f);
        mapProjection.setMapAngle(f2);
        mapProjection.setCameraHeaderAngle(f3);
        m5634a(mapProjection, iPoint);
    }

    private void m5638b(MapProjection mapProjection, CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) {
        mapProjection.setMapZoomer(cameraUpdateFactoryDelegate.zoom);
        m5634a(mapProjection, cameraUpdateFactoryDelegate.geoPoint);
    }

    private void m5640c(MapProjection mapProjection, CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) {
        IPoint a = m5630a(mapProjection);
        mapProjection.setCameraHeaderAngle(cameraUpdateFactoryDelegate.tilt);
        m5634a(mapProjection, a);
    }

    private void m5641d(MapProjection mapProjection, CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) {
        IPoint a = m5630a(mapProjection);
        mapProjection.setMapAngle(cameraUpdateFactoryDelegate.bearing);
        m5634a(mapProjection, a);
    }

    private void m5634a(MapProjection mapProjection, IPoint iPoint) {
        m5636a(mapProjection, iPoint, this.f5225h, this.f5226i);
    }

    private void m5636a(MapProjection mapProjection, IPoint iPoint, int i, int i2) {
        mapProjection.recalculate();
        IPoint a = m5631a(mapProjection, i, i2);
        IPoint iPoint2 = new IPoint();
        mapProjection.getGeoCenter(iPoint2);
        mapProjection.setGeoCenter((iPoint2.f2030x + iPoint.f2030x) - a.f2030x, (iPoint2.f2031y + iPoint.f2031y) - a.f2031y);
    }

    private IPoint m5630a(MapProjection mapProjection) {
        return m5631a(mapProjection, this.f5225h, this.f5226i);
    }

    private IPoint m5631a(MapProjection mapProjection, int i, int i2) {
        FPoint fPoint = new FPoint();
        mapProjection.win2Map(i, i2, fPoint);
        IPoint iPoint = new IPoint();
        mapProjection.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
        return iPoint;
    }

    public void OnMapDestory(GL10 gl10, MapCore mapCore) {
        super.OnMapDestory(mapCore);
    }

    public void OnMapReferencechanged(MapCore mapCore, String str, String str2) {
        try {
            if (this.f5223f.getUiSettings().isCompassEnabled()) {
                this.f5223f.m4139a();
            }
            if (this.f5223f.getUiSettings().isScaleControlsEnabled()) {
                this.f5223f.m4148b();
            }
            this.f5223f.m4146a(true);
        } catch (Throwable e) {
            ee.m4243a(e, "AMapCallback", "OnMapReferencechanged");
            e.printStackTrace();
        }
        this.f5223f.m4152f();
    }

    public Context getContext() {
        return this.f5223f.m4153g();
    }

    public boolean isMapEngineValid() {
        if (this.f5223f.getMapCore() != null) {
            return this.f5223f.getMapCore().isMapEngineValid();
        }
        return false;
    }

    public void OnMapLoaderError(int i) {
    }

    public void m5642a(int i, int i2) {
        this.f5225h = i;
        this.f5226i = i2;
    }

    public void requestRender() {
        this.f5223f.setRunLowFrame(false);
    }

    public void onIndoorBuildingActivity(MapCore mapCore, byte[] bArr) {
        IndoorBuilding indoorBuilding = null;
        if (bArr != null) {
            try {
                IndoorBuilding indoorBuilding2 = new IndoorBuilding();
                byte b = bArr[0];
                indoorBuilding2.name_cn = new String(bArr, 1, b);
                int i = b + 1;
                int i2 = i + 1;
                b = bArr[i];
                indoorBuilding2.name_en = new String(bArr, i2, b);
                i = b + i2;
                i2 = i + 1;
                b = bArr[i];
                indoorBuilding2.activeFloorName = new String(bArr, i2, b);
                i = b + i2;
                indoorBuilding2.activeFloorIndex = Convert.getInt(bArr, i);
                i += 4;
                i2 = i + 1;
                b = bArr[i];
                indoorBuilding2.poiid = new String(bArr, i2, b);
                i = b + i2;
                indoorBuilding2.numberofFloor = Convert.getInt(bArr, i);
                i += 4;
                indoorBuilding2.floor_indexs = new int[indoorBuilding2.numberofFloor];
                indoorBuilding2.floor_names = new String[indoorBuilding2.numberofFloor];
                indoorBuilding2.floor_nonas = new String[indoorBuilding2.numberofFloor];
                for (int i3 = 0; i3 < indoorBuilding2.numberofFloor; i3++) {
                    indoorBuilding2.floor_indexs[i3] = Convert.getInt(bArr, i);
                    i2 = i + 4;
                    i = i2 + 1;
                    byte b2 = bArr[i2];
                    if (b2 > (byte) 0) {
                        indoorBuilding2.floor_names[i3] = new String(bArr, i, b2);
                        i2 = i + b2;
                    } else {
                        i2 = i;
                    }
                    i = i2 + 1;
                    b2 = bArr[i2];
                    if (b2 > (byte) 0) {
                        indoorBuilding2.floor_nonas[i3] = new String(bArr, i, b2);
                        i += b2;
                    }
                }
                indoorBuilding2.numberofParkFloor = Convert.getInt(bArr, i);
                i += 4;
                if (indoorBuilding2.numberofParkFloor > 0) {
                    indoorBuilding2.park_floor_indexs = new int[indoorBuilding2.numberofParkFloor];
                    int i4 = i;
                    for (i = 0; i < indoorBuilding2.numberofParkFloor; i++) {
                        indoorBuilding2.park_floor_indexs[i] = Convert.getInt(bArr, i4);
                        i4 += 4;
                    }
                }
                indoorBuilding = indoorBuilding2;
            } catch (Throwable th) {
                th.printStackTrace();
                ee.m4243a(th, "AMapCallback", "onIndoorBuildingActivity");
                return;
            }
        }
        this.f5223f.m4141a(indoorBuilding);
    }

    public void onIndoorDataRequired(MapCore mapCore, int i, String[] strArr, int[] iArr, int[] iArr2) {
        if (strArr != null && strArr.length != 0) {
            ArrayList reqGridList = getReqGridList(i);
            if (reqGridList != null) {
                reqGridList.clear();
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    reqGridList.add(new MapSourceGridData(strArr[i2], i, iArr[i2], iArr2[i2]));
                }
                if (i != 5) {
                    proccessRequiredData(mapCore, reqGridList, i);
                }
            }
        }
    }
}
