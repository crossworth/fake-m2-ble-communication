package com.amap.api.maps;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.RemoteException;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.RuntimeRemoteException;
import com.amap.api.maps.model.TileProjection;
import com.amap.api.maps.model.VisibleRegion;
import com.autonavi.amap.mapcore.interfaces.IProjectionDelegate;

public class Projection {
    private final IProjectionDelegate f811a;

    Projection(IProjectionDelegate iProjectionDelegate) {
        this.f811a = iProjectionDelegate;
    }

    public LatLng fromScreenLocation(Point point) {
        try {
            return this.f811a.fromScreenLocation(point);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public Point toScreenLocation(LatLng latLng) {
        try {
            return this.f811a.toScreenLocation(latLng);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public PointF toMapLocation(LatLng latLng) {
        try {
            return this.f811a.toMapLocation(latLng);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public PointF toOpenGLLocation(LatLng latLng) {
        try {
            return this.f811a.toMapLocation(latLng);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float toOpenGLWidth(int i) {
        try {
            return this.f811a.toMapLenWithWin(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public VisibleRegion getVisibleRegion() {
        try {
            return this.f811a.getVisibleRegion();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public TileProjection fromBoundsToTile(LatLngBounds latLngBounds, int i, int i2) {
        try {
            return this.f811a.fromBoundsToTile(latLngBounds, i, i2);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public LatLngBounds getMapBounds(LatLng latLng, float f) {
        try {
            return this.f811a.getMapBounds(latLng, f);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
