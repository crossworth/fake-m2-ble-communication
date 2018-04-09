package com.amap.api.mapcore.util;

import android.graphics.Color;
import android.os.RemoteException;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.autonavi.amap.mapcore.AMapNativeRenderer;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IArcDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.tencent.open.yyb.TitleBar;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: ArcDelegateImp */
class C1968n implements IArcDelegate {
    float f5358a;
    float f5359b;
    float f5360c;
    float f5361d;
    private LatLng f5362e;
    private LatLng f5363f;
    private LatLng f5364g;
    private float f5365h = TitleBar.SHAREBTN_RIGHT_MARGIN;
    private int f5366i = ViewCompat.MEASURED_STATE_MASK;
    private float f5367j = 0.0f;
    private boolean f5368k = true;
    private String f5369l;
    private IAMapDelegate f5370m;
    private float[] f5371n;
    private int f5372o = 0;
    private boolean f5373p = false;
    private double f5374q = 0.0d;
    private double f5375r = 0.0d;
    private double f5376s = 0.0d;

    public C1968n(IAMapDelegate iAMapDelegate) {
        this.f5370m = iAMapDelegate;
        try {
            this.f5369l = getId();
        } catch (Throwable e) {
            ee.m4243a(e, "ArcDelegateImp", "create");
            e.printStackTrace();
        }
    }

    public boolean checkInBounds() {
        return true;
    }

    public void remove() throws RemoteException {
        this.f5370m.removeGLOverlay(getId());
        this.f5370m.setRunLowFrame(false);
    }

    public String getId() throws RemoteException {
        if (this.f5369l == null) {
            this.f5369l = C0286v.m1022a("Arc");
        }
        return this.f5369l;
    }

    public void setZIndex(float f) throws RemoteException {
        this.f5367j = f;
        this.f5370m.changeGLOverlayIndex();
        this.f5370m.setRunLowFrame(false);
    }

    public float getZIndex() throws RemoteException {
        return this.f5367j;
    }

    public void setVisible(boolean z) throws RemoteException {
        this.f5368k = z;
        this.f5370m.setRunLowFrame(false);
    }

    public boolean isVisible() throws RemoteException {
        return this.f5368k;
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
        if (this.f5362e != null && this.f5363f != null && this.f5364g != null && this.f5368k) {
            try {
                this.f5373p = false;
                MapProjection mapProjection = this.f5370m.getMapProjection();
                FPoint fPoint;
                if (m5766a()) {
                    DPoint b = m5767b();
                    int abs = (int) ((Math.abs(this.f5376s - this.f5375r) * 180.0d) / 3.141592653589793d);
                    double d = (this.f5376s - this.f5375r) / ((double) abs);
                    FPoint[] fPointArr = new FPoint[(abs + 1)];
                    this.f5371n = new float[(fPointArr.length * 3)];
                    for (int i2 = 0; i2 <= abs; i2++) {
                        MapProjection mapProjection2;
                        if (i2 == abs) {
                            fPoint = new FPoint();
                            this.f5370m.getLatLng2Map(this.f5364g.latitude, this.f5364g.longitude, fPoint);
                            fPointArr[i2] = fPoint;
                        } else {
                            mapProjection2 = mapProjection;
                            fPointArr[i2] = m5765a(mapProjection2, (((double) i2) * d) + this.f5375r, b.f2026x, b.f2027y);
                        }
                        mapProjection2 = mapProjection;
                        fPointArr[i2] = m5765a(mapProjection2, (((double) i2) * d) + this.f5375r, b.f2026x, b.f2027y);
                        this.f5371n[i2 * 3] = fPointArr[i2].f2028x;
                        this.f5371n[(i2 * 3) + 1] = fPointArr[i2].f2029y;
                        this.f5371n[(i2 * 3) + 2] = 0.0f;
                    }
                    this.f5372o = fPointArr.length;
                    return;
                }
                FPoint[] fPointArr2 = new FPoint[3];
                this.f5371n = new float[(fPointArr2.length * 3)];
                fPoint = new FPoint();
                this.f5370m.getLatLng2Map(this.f5362e.latitude, this.f5362e.longitude, fPoint);
                fPointArr2[0] = fPoint;
                fPoint = new FPoint();
                this.f5370m.getLatLng2Map(this.f5363f.latitude, this.f5363f.longitude, fPoint);
                fPointArr2[1] = fPoint;
                fPoint = new FPoint();
                this.f5370m.getLatLng2Map(this.f5364g.latitude, this.f5364g.longitude, fPoint);
                fPointArr2[2] = fPoint;
                while (i < 3) {
                    this.f5371n[i * 3] = fPointArr2[i].f2028x;
                    this.f5371n[(i * 3) + 1] = fPointArr2[i].f2029y;
                    this.f5371n[(i * 3) + 2] = 0.0f;
                    i++;
                }
                this.f5372o = fPointArr2.length;
            } catch (Throwable th) {
                ee.m4243a(th, "ArcDelegateImp", "calMapFPoint");
                th.printStackTrace();
            }
        }
    }

    private FPoint m5765a(MapProjection mapProjection, double d, double d2, double d3) {
        int cos = (int) ((Math.cos(d) * this.f5374q) + d2);
        int i = (int) (((-Math.sin(d)) * this.f5374q) + d3);
        FPoint fPoint = new FPoint();
        mapProjection.geo2Map(cos, i, fPoint);
        return fPoint;
    }

    private boolean m5766a() {
        if (Math.abs(((this.f5362e.latitude - this.f5363f.latitude) * (this.f5363f.longitude - this.f5364g.longitude)) - ((this.f5362e.longitude - this.f5363f.longitude) * (this.f5363f.latitude - this.f5364g.latitude))) < 1.0E-6d) {
            return false;
        }
        return true;
    }

    private DPoint m5767b() {
        IPoint iPoint = new IPoint();
        this.f5370m.latlon2Geo(this.f5362e.latitude, this.f5362e.longitude, iPoint);
        IPoint iPoint2 = new IPoint();
        this.f5370m.latlon2Geo(this.f5363f.latitude, this.f5363f.longitude, iPoint2);
        IPoint iPoint3 = new IPoint();
        this.f5370m.latlon2Geo(this.f5364g.latitude, this.f5364g.longitude, iPoint3);
        double d = (double) iPoint.f2030x;
        double d2 = (double) iPoint.f2031y;
        double d3 = (double) iPoint2.f2030x;
        double d4 = (double) iPoint2.f2031y;
        double d5 = (double) iPoint3.f2030x;
        double d6 = (double) iPoint3.f2031y;
        double d7 = (((d6 - d2) * ((((d4 * d4) - (d2 * d2)) + (d3 * d3)) - (d * d))) + ((d4 - d2) * ((((d2 * d2) - (d6 * d6)) + (d * d)) - (d5 * d5)))) / (((2.0d * (d3 - d)) * (d6 - d2)) - ((2.0d * (d5 - d)) * (d4 - d2)));
        double d8 = (((d5 - d) * ((((d3 * d3) - (d * d)) + (d4 * d4)) - (d2 * d2))) + ((d3 - d) * ((((d * d) - (d5 * d5)) + (d2 * d2)) - (d6 * d6)))) / (((2.0d * (d4 - d2)) * (d5 - d)) - ((2.0d * (d6 - d2)) * (d3 - d)));
        this.f5374q = Math.sqrt(((d - d7) * (d - d7)) + ((d2 - d8) * (d2 - d8)));
        this.f5375r = m5764a(d7, d8, d, d2);
        d = m5764a(d7, d8, d3, d4);
        this.f5376s = m5764a(d7, d8, d5, d6);
        if (this.f5375r < this.f5376s) {
            if (d <= this.f5375r || d >= this.f5376s) {
                this.f5376s -= 6.283185307179586d;
            }
        } else if (d <= this.f5376s || d >= this.f5375r) {
            this.f5376s += 6.283185307179586d;
        }
        return new DPoint(d7, d8);
    }

    private double m5764a(double d, double d2, double d3, double d4) {
        double d5 = (d2 - d4) / this.f5374q;
        if (Math.abs(d5) > WeightedLatLng.DEFAULT_INTENSITY) {
            d5 = Math.signum(d5);
        }
        d5 = Math.asin(d5);
        if (d5 >= 0.0d) {
            if (d3 < d) {
                return 3.141592653589793d - Math.abs(d5);
            }
            return d5;
        } else if (d3 < d) {
            return 3.141592653589793d - d5;
        } else {
            return d5 + 6.283185307179586d;
        }
    }

    public void draw(GL10 gl10) throws RemoteException {
        if (this.f5362e != null && this.f5363f != null && this.f5364g != null && this.f5368k) {
            if (this.f5371n == null || this.f5372o == 0) {
                calMapFPoint();
            }
            if (this.f5371n != null && this.f5372o > 0) {
                float mapLenWithWin = this.f5370m.getMapProjection().getMapLenWithWin((int) this.f5365h);
                this.f5370m.getMapProjection().getMapLenWithWin(1);
                AMapNativeRenderer.nativeDrawLineByTextureID(this.f5371n, this.f5371n.length, mapLenWithWin, this.f5370m.getLineTextureID(), this.f5359b, this.f5360c, this.f5361d, this.f5358a, 0.0f, false, true, false);
            }
            this.f5373p = true;
        }
    }

    public void setStrokeWidth(float f) throws RemoteException {
        this.f5365h = f;
        this.f5370m.setRunLowFrame(false);
    }

    public float getStrokeWidth() throws RemoteException {
        return this.f5365h;
    }

    public void setStrokeColor(int i) throws RemoteException {
        this.f5366i = i;
        this.f5358a = ((float) Color.alpha(i)) / 255.0f;
        this.f5359b = ((float) Color.red(i)) / 255.0f;
        this.f5360c = ((float) Color.green(i)) / 255.0f;
        this.f5361d = ((float) Color.blue(i)) / 255.0f;
        this.f5370m.setRunLowFrame(false);
    }

    public int getStrokeColor() throws RemoteException {
        return this.f5366i;
    }

    public void destroy() {
        try {
            this.f5362e = null;
            this.f5363f = null;
            this.f5364g = null;
        } catch (Throwable th) {
            ee.m4243a(th, "ArcDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "ArcDelegateImp destroy");
        }
    }

    public boolean isDrawFinish() {
        return this.f5373p;
    }

    public void setStart(LatLng latLng) {
        this.f5362e = latLng;
    }

    public void setPassed(LatLng latLng) {
        this.f5363f = latLng;
    }

    public void setEnd(LatLng latLng) {
        this.f5364g = latLng;
    }
}
