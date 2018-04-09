package com.amap.api.mapcore.util;

import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IPolygonDelegate;
import com.tencent.open.yyb.TitleBar;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: PolygonDelegateImp */
class an implements IPolygonDelegate {
    private static float f5247u = 1.0E10f;
    private IAMapDelegate f5248a;
    private float f5249b = 0.0f;
    private boolean f5250c = true;
    private boolean f5251d;
    private String f5252e;
    private float f5253f;
    private int f5254g;
    private int f5255h;
    private List<LatLng> f5256i;
    private List<LatLng> f5257j;
    private CopyOnWriteArrayList<IPoint> f5258k = new CopyOnWriteArrayList();
    private List<FPoint> f5259l = new ArrayList();
    private FloatBuffer f5260m;
    private FloatBuffer f5261n;
    private int f5262o = 0;
    private int f5263p = 0;
    private LatLngBounds f5264q = null;
    private boolean f5265r = false;
    private float f5266s = 0.0f;
    private Object f5267t = new Object();

    public an(IAMapDelegate iAMapDelegate) {
        this.f5248a = iAMapDelegate;
        try {
            this.f5252e = getId();
        } catch (Throwable e) {
            ee.m4243a(e, "PolygonDelegateImp", "create");
            e.printStackTrace();
        }
    }

    public void remove() throws RemoteException {
        this.f5248a.removeGLOverlay(getId());
        this.f5248a.setRunLowFrame(false);
    }

    public String getId() throws RemoteException {
        if (this.f5252e == null) {
            this.f5252e = C0286v.m1022a("Polygon");
        }
        return this.f5252e;
    }

    public void setPoints(List<LatLng> list) throws RemoteException {
        synchronized (this.f5267t) {
            this.f5257j = list;
            m5652a((List) list);
            calMapFPoint();
            this.f5248a.setRunLowFrame(false);
        }
    }

    public List<LatLng> getPoints() throws RemoteException {
        return this.f5257j;
    }

    public void setZIndex(float f) throws RemoteException {
        this.f5249b = f;
        this.f5248a.changeGLOverlayIndex();
        this.f5248a.setRunLowFrame(false);
    }

    public float getZIndex() throws RemoteException {
        return this.f5249b;
    }

    public void setVisible(boolean z) throws RemoteException {
        this.f5250c = z;
        this.f5248a.setRunLowFrame(false);
    }

    public boolean isVisible() throws RemoteException {
        return this.f5250c;
    }

    public boolean equalsRemote(IOverlayDelegate iOverlayDelegate) throws RemoteException {
        if (equals(iOverlayDelegate) || iOverlayDelegate.getId().equals(getId())) {
            return true;
        }
        return false;
    }

    void m5652a(List<LatLng> list) throws RemoteException {
        Builder builder = LatLngBounds.builder();
        this.f5258k.clear();
        if (list != null) {
            Object obj = null;
            for (LatLng latLng : list) {
                if (!latLng.equals(obj)) {
                    IPoint iPoint = new IPoint();
                    this.f5248a.latlon2Geo(latLng.latitude, latLng.longitude, iPoint);
                    this.f5258k.add(iPoint);
                    builder.include(latLng);
                    obj = latLng;
                }
            }
            int size = this.f5258k.size();
            if (size > 1) {
                IPoint iPoint2 = (IPoint) this.f5258k.get(0);
                IPoint iPoint3 = (IPoint) this.f5258k.get(size - 1);
                if (iPoint2.f2030x == iPoint3.f2030x && iPoint2.f2031y == iPoint3.f2031y) {
                    this.f5258k.remove(size - 1);
                }
            }
        }
        this.f5264q = builder.build();
        if (this.f5260m != null) {
            this.f5260m.clear();
        }
        if (this.f5261n != null) {
            this.f5261n.clear();
        }
        this.f5262o = 0;
        this.f5263p = 0;
        this.f5248a.setRunLowFrame(false);
    }

    public void calMapFPoint() throws RemoteException {
        synchronized (this.f5267t) {
            this.f5259l.clear();
            this.f5265r = false;
            Iterator it = this.f5258k.iterator();
            while (it.hasNext()) {
                IPoint iPoint = (IPoint) it.next();
                FPoint fPoint = new FPoint();
                this.f5248a.geo2Map(iPoint.f2031y, iPoint.f2030x, fPoint);
                this.f5259l.add(fPoint);
            }
            m5650b();
        }
    }

    public int hashCodeRemote() throws RemoteException {
        return super.hashCode();
    }

    public boolean checkInBounds() {
        if (this.f5264q == null) {
            return false;
        }
        LatLngBounds mapBounds = this.f5248a.getMapBounds();
        if (mapBounds == null) {
            return true;
        }
        if (this.f5264q.contains(mapBounds) || this.f5264q.intersects(mapBounds)) {
            return true;
        }
        return false;
    }

    public void draw(GL10 gl10) throws RemoteException {
        if (this.f5258k != null && this.f5258k.size() != 0) {
            if (this.f5260m == null || this.f5261n == null || this.f5262o == 0 || this.f5263p == 0) {
                calMapFPoint();
            }
            List list = this.f5259l;
            if (m5647a()) {
                synchronized (this.f5267t) {
                    list = dj.m577a(this.f5248a, this.f5259l, true);
                }
            }
            if (list.size() > 2) {
                m5651b(list);
                if (this.f5260m != null && this.f5261n != null && this.f5262o > 0 && this.f5263p > 0) {
                    C0276t.m1007a(gl10, this.f5254g, this.f5255h, this.f5260m, this.f5253f, this.f5261n, this.f5262o, this.f5263p);
                }
            }
            this.f5265r = true;
        }
    }

    private boolean m5647a() {
        float zoomLevel = this.f5248a.getZoomLevel();
        m5650b();
        if (zoomLevel <= TitleBar.SHAREBTN_RIGHT_MARGIN) {
            return false;
        }
        try {
            if (this.f5248a == null) {
                return false;
            }
            Rect rect = new Rect(-100, -100, this.f5248a.getMapWidth() + 100, this.f5248a.getMapHeight() + 100);
            LatLng latLng = this.f5264q.northeast;
            LatLng latLng2 = this.f5264q.southwest;
            IPoint iPoint = new IPoint();
            this.f5248a.getLatLng2Pixel(latLng.latitude, latLng2.longitude, iPoint);
            IPoint iPoint2 = new IPoint();
            this.f5248a.getLatLng2Pixel(latLng.latitude, latLng.longitude, iPoint2);
            IPoint iPoint3 = new IPoint();
            this.f5248a.getLatLng2Pixel(latLng2.latitude, latLng.longitude, iPoint3);
            IPoint iPoint4 = new IPoint();
            this.f5248a.getLatLng2Pixel(latLng2.latitude, latLng2.longitude, iPoint4);
            if (rect.contains(iPoint.f2030x, iPoint.f2031y) && rect.contains(iPoint2.f2030x, iPoint2.f2031y) && rect.contains(iPoint3.f2030x, iPoint3.f2031y) && rect.contains(iPoint4.f2030x, iPoint4.f2031y)) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private void m5651b(List<FPoint> list) throws RemoteException {
        int i = 0;
        m5650b();
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        if (size >= 2) {
            FPoint fPoint = (FPoint) list.get(0);
            arrayList.add(fPoint);
            int i2 = 1;
            FPoint fPoint2 = fPoint;
            while (i2 < size - 1) {
                fPoint = (FPoint) list.get(i2);
                if (m5648a(fPoint2, fPoint)) {
                    arrayList.add(fPoint);
                } else {
                    fPoint = fPoint2;
                }
                i2++;
                fPoint2 = fPoint;
            }
            arrayList.add(list.get(size - 1));
            float[] fArr = new float[(arrayList.size() * 3)];
            FPoint[] fPointArr = new FPoint[arrayList.size()];
            Iterator it = arrayList.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                fPoint = (FPoint) it.next();
                fArr[i3 * 3] = fPoint.f2028x;
                fArr[(i3 * 3) + 1] = fPoint.f2029y;
                fArr[(i3 * 3) + 2] = 0.0f;
                fPointArr[i3] = fPoint;
                i3++;
            }
            FPoint[] a = m5649a(fPointArr);
            if (a.length == 0) {
                if (f5247u == 1.0E10f) {
                    f5247u = 1.0E8f;
                } else {
                    f5247u = 1.0E10f;
                }
                a = m5649a(fPointArr);
            }
            float[] fArr2 = new float[(a.length * 3)];
            int length = a.length;
            i3 = 0;
            while (i < length) {
                FPoint fPoint3 = a[i];
                fArr2[i3 * 3] = fPoint3.f2028x;
                fArr2[(i3 * 3) + 1] = fPoint3.f2029y;
                fArr2[(i3 * 3) + 2] = 0.0f;
                i3++;
                i++;
            }
            this.f5262o = fPointArr.length;
            this.f5263p = a.length;
            this.f5260m = dj.m575a(fArr);
            this.f5261n = dj.m575a(fArr2);
        }
    }

    private boolean m5648a(FPoint fPoint, FPoint fPoint2) {
        return Math.abs(fPoint2.f2028x - fPoint.f2028x) >= this.f5266s || Math.abs(fPoint2.f2029y - fPoint.f2029y) >= this.f5266s;
    }

    private void m5650b() {
        float zoomLevel = this.f5248a.getZoomLevel();
        if (this.f5258k.size() <= 5000 || zoomLevel > 12.0f) {
            this.f5266s = this.f5248a.getMapProjection().getMapLenWithWin(10);
            return;
        }
        zoomLevel = (zoomLevel / 2.0f) + (this.f5253f / 2.0f);
        if (zoomLevel > 200.0f) {
            zoomLevel = 200.0f;
        }
        this.f5266s = this.f5248a.getMapProjection().getMapLenWithWin((int) zoomLevel);
    }

    public void setStrokeWidth(float f) throws RemoteException {
        this.f5253f = f;
        this.f5248a.setRunLowFrame(false);
    }

    public float getStrokeWidth() throws RemoteException {
        return this.f5253f;
    }

    public void setFillColor(int i) throws RemoteException {
        this.f5254g = i;
        this.f5248a.setRunLowFrame(false);
    }

    public int getFillColor() throws RemoteException {
        return this.f5254g;
    }

    public void setStrokeColor(int i) throws RemoteException {
        this.f5255h = i;
        this.f5248a.setRunLowFrame(false);
    }

    public int getStrokeColor() throws RemoteException {
        return this.f5255h;
    }

    public void setHoles(List<LatLng> list) throws RemoteException {
        this.f5256i = list;
        this.f5248a.setRunLowFrame(false);
    }

    public List<LatLng> getHoles() {
        return this.f5256i;
    }

    public void setGeodesic(boolean z) {
        this.f5251d = z;
        this.f5248a.setRunLowFrame(false);
    }

    public boolean isGeodesic() {
        return this.f5251d;
    }

    static FPoint[] m5649a(FPoint[] fPointArr) {
        int i = 0;
        int length = fPointArr.length;
        float[] fArr = new float[(length * 2)];
        for (int i2 = 0; i2 < length; i2++) {
            fArr[i2 * 2] = fPointArr[i2].f2028x * f5247u;
            fArr[(i2 * 2) + 1] = fPointArr[i2].f2029y * f5247u;
        }
        di a = new cz().m487a(fArr);
        length = a.f453b;
        FPoint[] fPointArr2 = new FPoint[length];
        while (i < length) {
            fPointArr2[i] = new FPoint();
            fPointArr2[i].f2028x = fArr[a.m548a(i) * 2] / f5247u;
            fPointArr2[i].f2029y = fArr[(a.m548a(i) * 2) + 1] / f5247u;
            i++;
        }
        return fPointArr2;
    }

    public void destroy() {
        try {
            if (this.f5260m != null) {
                this.f5260m.clear();
                this.f5260m = null;
            }
            if (this.f5261n != null) {
                this.f5261n = null;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "PolygonDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "PolygonDelegateImp destroy");
        }
    }

    public boolean contains(LatLng latLng) throws RemoteException {
        try {
            return dj.m583a(latLng, getPoints());
        } catch (Throwable th) {
            ee.m4243a(th, "PolygonDelegateImp", "contains");
            th.printStackTrace();
            return false;
        }
    }

    public boolean isDrawFinish() {
        return this.f5265r;
    }
}
