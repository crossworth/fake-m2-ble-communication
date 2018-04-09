package com.amap.api.mapcore.util;

import android.os.RemoteException;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.ICircleDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.tencent.open.yyb.TitleBar;
import com.umeng.analytics.C0919a;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: CircleDelegateImp */
class C1969p implements ICircleDelegate {
    private static float f5377m = 4.0075016E7f;
    private static int f5378n = 256;
    private static int f5379o = 20;
    private LatLng f5380a = null;
    private double f5381b = 0.0d;
    private float f5382c = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f5383d = ViewCompat.MEASURED_STATE_MASK;
    private int f5384e = 0;
    private float f5385f = 0.0f;
    private boolean f5386g = true;
    private String f5387h;
    private IAMapDelegate f5388i;
    private FloatBuffer f5389j;
    private int f5390k = 0;
    private boolean f5391l = false;

    public C1969p(IAMapDelegate iAMapDelegate) {
        this.f5388i = iAMapDelegate;
        try {
            this.f5387h = getId();
        } catch (Throwable e) {
            ee.m4243a(e, "CircleDelegateImp", "create");
            e.printStackTrace();
        }
    }

    public boolean checkInBounds() {
        return true;
    }

    public void remove() throws RemoteException {
        this.f5388i.removeGLOverlay(getId());
        this.f5388i.setRunLowFrame(false);
    }

    public String getId() throws RemoteException {
        if (this.f5387h == null) {
            this.f5387h = C0286v.m1022a("Circle");
        }
        return this.f5387h;
    }

    public void setZIndex(float f) throws RemoteException {
        this.f5385f = f;
        this.f5388i.changeGLOverlayIndex();
        this.f5388i.setRunLowFrame(false);
    }

    public float getZIndex() throws RemoteException {
        return this.f5385f;
    }

    public void setVisible(boolean z) throws RemoteException {
        this.f5386g = z;
        this.f5388i.setRunLowFrame(false);
    }

    public boolean isVisible() throws RemoteException {
        return this.f5386g;
    }

    public boolean equalsRemote(IOverlayDelegate iOverlayDelegate) throws RemoteException {
        if (equals(iOverlayDelegate) || iOverlayDelegate.getId().equals(getId())) {
            return true;
        }
        return false;
    }

    public int hashCodeRemote() throws RemoteException {
        return 0;
    }

    public void calMapFPoint() throws RemoteException {
        int i = 0;
        this.f5391l = false;
        LatLng latLng = this.f5380a;
        if (latLng != null) {
            FPoint[] fPointArr = new FPoint[C0919a.f3120q];
            float[] fArr = new float[(fPointArr.length * 3)];
            double b = m5769b(this.f5380a.latitude) * this.f5381b;
            IPoint iPoint = new IPoint();
            MapProjection mapProjection = this.f5388i.getMapProjection();
            MapProjection.lonlat2Geo(latLng.longitude, latLng.latitude, iPoint);
            while (i < C0919a.f3120q) {
                double d = (((double) i) * 3.141592653589793d) / 180.0d;
                double sin = Math.sin(d) * b;
                int i2 = (int) (sin + ((double) iPoint.f2030x));
                int cos = (int) ((Math.cos(d) * b) + ((double) iPoint.f2031y));
                FPoint fPoint = new FPoint();
                mapProjection.geo2Map(i2, cos, fPoint);
                fPointArr[i] = fPoint;
                fArr[i * 3] = fPointArr[i].f2028x;
                fArr[(i * 3) + 1] = fPointArr[i].f2029y;
                fArr[(i * 3) + 2] = 0.0f;
                i++;
            }
            this.f5390k = fPointArr.length;
            this.f5389j = dj.m575a(fArr);
        }
    }

    public void draw(GL10 gl10) throws RemoteException {
        if (this.f5380a != null && this.f5381b > 0.0d && this.f5386g) {
            if (this.f5389j == null || this.f5390k == 0) {
                calMapFPoint();
            }
            if (this.f5389j != null && this.f5390k > 0) {
                C0276t.m1008b(gl10, this.f5384e, this.f5383d, this.f5389j, this.f5382c, this.f5390k);
            }
            this.f5391l = true;
        }
    }

    void m5770a() {
        this.f5390k = 0;
        if (this.f5389j != null) {
            this.f5389j.clear();
        }
        this.f5388i.setRunLowFrame(false);
    }

    public void setCenter(LatLng latLng) throws RemoteException {
        this.f5380a = latLng;
        m5770a();
    }

    public LatLng getCenter() throws RemoteException {
        return this.f5380a;
    }

    public void setRadius(double d) throws RemoteException {
        this.f5381b = d;
        m5770a();
    }

    public double getRadius() throws RemoteException {
        return this.f5381b;
    }

    public void setStrokeWidth(float f) throws RemoteException {
        this.f5382c = f;
        this.f5388i.setRunLowFrame(false);
    }

    public float getStrokeWidth() throws RemoteException {
        return this.f5382c;
    }

    public void setStrokeColor(int i) throws RemoteException {
        this.f5383d = i;
    }

    public int getStrokeColor() throws RemoteException {
        return this.f5383d;
    }

    public void setFillColor(int i) throws RemoteException {
        this.f5384e = i;
        this.f5388i.setRunLowFrame(false);
    }

    public int getFillColor() throws RemoteException {
        return this.f5384e;
    }

    private float m5768a(double d) {
        return (float) ((Math.cos((3.141592653589793d * d) / 180.0d) * ((double) f5377m)) / ((double) (f5378n << f5379o)));
    }

    private double m5769b(double d) {
        return WeightedLatLng.DEFAULT_INTENSITY / ((double) m5768a(d));
    }

    public void destroy() {
        try {
            this.f5380a = null;
            if (this.f5389j != null) {
                this.f5389j.clear();
                this.f5389j = null;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "CircleDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "CircleDelegateImp destroy");
        }
    }

    public boolean contains(LatLng latLng) throws RemoteException {
        if (this.f5381b >= ((double) AMapUtils.calculateLineDistance(this.f5380a, latLng))) {
            return true;
        }
        return false;
    }

    public boolean isDrawFinish() {
        return this.f5391l;
    }
}
