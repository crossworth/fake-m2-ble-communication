package com.amap.api.mapcore.util;

import android.graphics.Color;
import android.os.RemoteException;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.autonavi.amap.mapcore.AMapNativeRenderer;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.INavigateArrowDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: NavigateArrowDelegateImp */
class ak implements INavigateArrowDelegate {
    float f5227a;
    float f5228b;
    float f5229c;
    float f5230d;
    float f5231e;
    float f5232f;
    float f5233g;
    float f5234h;
    float[] f5235i;
    private IAMapDelegate f5236j;
    private float f5237k = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f5238l = ViewCompat.MEASURED_STATE_MASK;
    private int f5239m = ViewCompat.MEASURED_STATE_MASK;
    private float f5240n = 0.0f;
    private boolean f5241o = true;
    private String f5242p;
    private CopyOnWriteArrayList<IPoint> f5243q = new CopyOnWriteArrayList();
    private int f5244r = 0;
    private boolean f5245s = false;
    private LatLngBounds f5246t = null;

    public ak(IAMapDelegate iAMapDelegate) {
        this.f5236j = iAMapDelegate;
        try {
            this.f5242p = getId();
        } catch (Throwable e) {
            ee.m4243a(e, "NavigateArrowDelegateImp", "create");
            e.printStackTrace();
        }
    }

    void m5646a(List<LatLng> list) throws RemoteException {
        Builder builder = LatLngBounds.builder();
        this.f5243q.clear();
        if (list != null) {
            Object obj = null;
            for (LatLng latLng : list) {
                if (!(latLng == null || latLng.equals(r1))) {
                    IPoint iPoint = new IPoint();
                    this.f5236j.latlon2Geo(latLng.latitude, latLng.longitude, iPoint);
                    this.f5243q.add(iPoint);
                    builder.include(latLng);
                    obj = latLng;
                }
            }
        }
        this.f5246t = builder.build();
        this.f5244r = 0;
        this.f5236j.setRunLowFrame(false);
    }

    public void remove() throws RemoteException {
        this.f5236j.removeGLOverlay(getId());
        this.f5236j.setRunLowFrame(false);
    }

    public String getId() throws RemoteException {
        if (this.f5242p == null) {
            this.f5242p = C0286v.m1022a("NavigateArrow");
        }
        return this.f5242p;
    }

    public void setPoints(List<LatLng> list) throws RemoteException {
        m5646a(list);
    }

    public List<LatLng> getPoints() throws RemoteException {
        return m5645a();
    }

    private List<LatLng> m5645a() throws RemoteException {
        if (this.f5243q == null) {
            return null;
        }
        List<LatLng> arrayList = new ArrayList();
        Iterator it = this.f5243q.iterator();
        while (it.hasNext()) {
            IPoint iPoint = (IPoint) it.next();
            if (iPoint != null) {
                DPoint dPoint = new DPoint();
                this.f5236j.geo2Latlng(iPoint.f2030x, iPoint.f2031y, dPoint);
                arrayList.add(new LatLng(dPoint.f2027y, dPoint.f2026x));
            }
        }
        return arrayList;
    }

    public void setWidth(float f) throws RemoteException {
        this.f5237k = f;
        this.f5236j.setRunLowFrame(false);
    }

    public float getWidth() throws RemoteException {
        return this.f5237k;
    }

    public void setTopColor(int i) throws RemoteException {
        this.f5238l = i;
        this.f5227a = ((float) Color.alpha(i)) / 255.0f;
        this.f5228b = ((float) Color.red(i)) / 255.0f;
        this.f5229c = ((float) Color.green(i)) / 255.0f;
        this.f5230d = ((float) Color.blue(i)) / 255.0f;
        this.f5236j.setRunLowFrame(false);
    }

    public int getTopColor() throws RemoteException {
        return this.f5238l;
    }

    public void setSideColor(int i) throws RemoteException {
        this.f5239m = i;
        this.f5231e = ((float) Color.alpha(i)) / 255.0f;
        this.f5232f = ((float) Color.red(i)) / 255.0f;
        this.f5233g = ((float) Color.green(i)) / 255.0f;
        this.f5234h = ((float) Color.blue(i)) / 255.0f;
        this.f5236j.setRunLowFrame(false);
    }

    public int getSideColor() throws RemoteException {
        return this.f5239m;
    }

    public void setZIndex(float f) throws RemoteException {
        this.f5240n = f;
        this.f5236j.changeGLOverlayIndex();
        this.f5236j.setRunLowFrame(false);
    }

    public float getZIndex() throws RemoteException {
        return this.f5240n;
    }

    public void setVisible(boolean z) throws RemoteException {
        this.f5241o = z;
        this.f5236j.setRunLowFrame(false);
    }

    public boolean isVisible() throws RemoteException {
        return this.f5241o;
    }

    public boolean equalsRemote(IOverlayDelegate iOverlayDelegate) throws RemoteException {
        if (equals(iOverlayDelegate) || iOverlayDelegate.getId().equals(getId())) {
            return true;
        }
        return false;
    }

    public int hashCodeRemote() throws RemoteException {
        return super.hashCode();
    }

    public boolean checkInBounds() {
        if (this.f5246t == null) {
            return false;
        }
        LatLngBounds mapBounds = this.f5236j.getMapBounds();
        if (mapBounds == null) {
            return true;
        }
        if (mapBounds.contains(this.f5246t) || this.f5246t.intersects(mapBounds)) {
            return true;
        }
        return false;
    }

    public void calMapFPoint() throws RemoteException {
        this.f5245s = false;
        FPoint fPoint = new FPoint();
        this.f5235i = new float[(this.f5243q.size() * 3)];
        Iterator it = this.f5243q.iterator();
        int i = 0;
        while (it.hasNext()) {
            IPoint iPoint = (IPoint) it.next();
            this.f5236j.geo2Map(iPoint.f2031y, iPoint.f2030x, fPoint);
            this.f5235i[i * 3] = fPoint.f2028x;
            this.f5235i[(i * 3) + 1] = fPoint.f2029y;
            this.f5235i[(i * 3) + 2] = 0.0f;
            i++;
        }
        this.f5244r = this.f5243q.size();
    }

    public void draw(GL10 gl10) throws RemoteException {
        if (this.f5243q != null && this.f5243q.size() != 0 && this.f5237k > 0.0f) {
            if (this.f5244r == 0) {
                calMapFPoint();
            }
            if (this.f5235i != null && this.f5244r > 0) {
                float mapLenWithWin = this.f5236j.getMapProjection().getMapLenWithWin((int) this.f5237k);
                this.f5236j.getMapProjection().getMapLenWithWin(1);
                AMapNativeRenderer.nativeDrawLineByTextureID(this.f5235i, this.f5235i.length, mapLenWithWin, this.f5236j.getLineTextureID(), this.f5228b, this.f5229c, this.f5230d, this.f5227a, 0.0f, false, true, true);
            }
            this.f5245s = true;
        }
    }

    public void destroy() {
        try {
            if (this.f5235i != null) {
                this.f5235i = null;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "NavigateArrowDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "NavigateArrowDelegateImp destroy");
        }
    }

    public boolean isDrawFinish() {
        return this.f5245s;
    }
}
