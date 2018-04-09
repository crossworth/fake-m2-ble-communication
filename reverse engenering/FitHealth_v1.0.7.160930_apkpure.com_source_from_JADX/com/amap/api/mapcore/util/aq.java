package com.amap.api.mapcore.util;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.RemoteException;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.TileProjection;
import com.amap.api.maps.model.VisibleRegion;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IProjectionDelegate;

/* compiled from: ProjectionDelegateImp */
class aq implements IProjectionDelegate {
    private IAMapDelegate f4006a;

    public aq(IAMapDelegate iAMapDelegate) {
        this.f4006a = iAMapDelegate;
    }

    public LatLng fromScreenLocation(Point point) throws RemoteException {
        if (point == null) {
            return null;
        }
        DPoint dPoint = new DPoint();
        this.f4006a.getPixel2LatLng(point.x, point.y, dPoint);
        return new LatLng(dPoint.f2027y, dPoint.f2026x);
    }

    public Point toScreenLocation(LatLng latLng) throws RemoteException {
        if (latLng == null) {
            return null;
        }
        IPoint iPoint = new IPoint();
        this.f4006a.getLatLng2Pixel(latLng.latitude, latLng.longitude, iPoint);
        return new Point(iPoint.f2030x, iPoint.f2031y);
    }

    public VisibleRegion getVisibleRegion() throws RemoteException {
        int mapWidth = this.f4006a.getMapWidth();
        int mapHeight = this.f4006a.getMapHeight();
        LatLng fromScreenLocation = fromScreenLocation(new Point(0, 0));
        LatLng fromScreenLocation2 = fromScreenLocation(new Point(mapWidth, 0));
        LatLng fromScreenLocation3 = fromScreenLocation(new Point(0, mapHeight));
        LatLng fromScreenLocation4 = fromScreenLocation(new Point(mapWidth, mapHeight));
        return new VisibleRegion(fromScreenLocation3, fromScreenLocation4, fromScreenLocation, fromScreenLocation2, LatLngBounds.builder().include(fromScreenLocation3).include(fromScreenLocation4).include(fromScreenLocation).include(fromScreenLocation2).build());
    }

    public PointF toMapLocation(LatLng latLng) throws RemoteException {
        if (latLng == null) {
            return null;
        }
        FPoint fPoint = new FPoint();
        this.f4006a.getLatLng2Map(latLng.latitude, latLng.longitude, fPoint);
        return new PointF(fPoint.f2028x, fPoint.f2029y);
    }

    public float toMapLenWithWin(int i) {
        if (i <= 0) {
            return 0.0f;
        }
        return this.f4006a.toMapLenWithWin(i);
    }

    public TileProjection fromBoundsToTile(LatLngBounds latLngBounds, int i, int i2) throws RemoteException {
        if (latLngBounds == null || i < 0 || i > 20 || i2 <= 0) {
            return null;
        }
        IPoint iPoint = new IPoint();
        IPoint iPoint2 = new IPoint();
        this.f4006a.latlon2Geo(latLngBounds.southwest.latitude, latLngBounds.southwest.longitude, iPoint);
        this.f4006a.latlon2Geo(latLngBounds.northeast.latitude, latLngBounds.northeast.longitude, iPoint2);
        int i3 = (iPoint.f2030x >> (20 - i)) / i2;
        int i4 = (iPoint2.f2031y >> (20 - i)) / i2;
        return new TileProjection((iPoint.f2030x - ((i3 << (20 - i)) * i2)) >> (20 - i), (iPoint2.f2031y - ((i4 << (20 - i)) * i2)) >> (20 - i), i3, (iPoint2.f2030x >> (20 - i)) / i2, i4, (iPoint.f2031y >> (20 - i)) / i2);
    }

    public LatLngBounds getMapBounds(LatLng latLng, float f) throws RemoteException {
        if (this.f4006a == null || latLng == null) {
            return null;
        }
        return this.f4006a.getMapBounds(latLng, dj.m557a(f));
    }
}
