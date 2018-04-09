package com.amap.api.mapcore.util;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.RemoteException;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.WeightedLatLng;
import com.autonavi.amap.mapcore.AMapNativeRenderer;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IPolylineDelegate;
import com.tencent.open.yyb.TitleBar;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: PolylineDelegateImp */
class ao implements IPolylineDelegate {
    private float f5268A = 0.0f;
    private float f5269B = 0.0f;
    private float f5270C;
    private float f5271D;
    private float f5272E;
    private float f5273F;
    private float f5274G = 0.0f;
    private float f5275H = 0.0f;
    private float[] f5276I;
    private int[] f5277J;
    private int[] f5278K;
    private double f5279L = 5.0d;
    private C0286v f5280a;
    private String f5281b;
    private List<IPoint> f5282c = new ArrayList();
    private List<FPoint> f5283d = new ArrayList();
    private List<LatLng> f5284e = new ArrayList();
    private List<BitmapDescriptor> f5285f = new ArrayList();
    private List<Integer> f5286g = new ArrayList();
    private List<Integer> f5287h = new ArrayList();
    private FloatBuffer f5288i;
    private BitmapDescriptor f5289j = null;
    private LatLngBounds f5290k = null;
    private Object f5291l = new Object();
    private boolean f5292m = true;
    private boolean f5293n = true;
    private boolean f5294o = false;
    private boolean f5295p = false;
    private boolean f5296q = false;
    private boolean f5297r = true;
    private boolean f5298s = false;
    private boolean f5299t = false;
    private boolean f5300u = true;
    private int f5301v = 0;
    private int f5302w = 0;
    private int f5303x = ViewCompat.MEASURED_STATE_MASK;
    private int f5304y = 0;
    private float f5305z = TitleBar.SHAREBTN_RIGHT_MARGIN;

    public void m5674a(boolean z) {
        this.f5300u = z;
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public void setGeodesic(boolean z) throws RemoteException {
        this.f5294o = z;
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public boolean isGeodesic() {
        return this.f5294o;
    }

    public void setDottedLine(boolean z) {
        if (this.f5301v == 2 || this.f5301v == 0) {
            this.f5295p = z;
            if (z && this.f5293n) {
                this.f5301v = 2;
            }
            this.f5280a.f743a.setRunLowFrame(false);
        }
    }

    public boolean isDottedLine() {
        return this.f5295p;
    }

    public ao(C0286v c0286v) {
        this.f5280a = c0286v;
        try {
            this.f5281b = getId();
        } catch (Throwable e) {
            ee.m4243a(e, "PolylineDelegateImp", "create");
            e.printStackTrace();
        }
    }

    void m5672a(List<LatLng> list) throws RemoteException {
        List arrayList = new ArrayList();
        Builder builder = LatLngBounds.builder();
        if (list != null) {
            LatLng latLng = null;
            for (LatLng latLng2 : list) {
                if (!(latLng2 == null || latLng2.equals(latLng))) {
                    IPoint iPoint;
                    if (!this.f5294o) {
                        iPoint = new IPoint();
                        this.f5280a.f743a.latlon2Geo(latLng2.latitude, latLng2.longitude, iPoint);
                        arrayList.add(iPoint);
                        builder.include(latLng2);
                    } else if (latLng != null) {
                        if (Math.abs(latLng2.longitude - latLng.longitude) < 0.01d) {
                            iPoint = new IPoint();
                            this.f5280a.f743a.latlon2Geo(latLng.latitude, latLng.longitude, iPoint);
                            arrayList.add(iPoint);
                            builder.include(latLng);
                            iPoint = new IPoint();
                            this.f5280a.f743a.latlon2Geo(latLng2.latitude, latLng2.longitude, iPoint);
                            arrayList.add(iPoint);
                            builder.include(latLng2);
                        } else {
                            m5671a(latLng, latLng2, arrayList, builder);
                        }
                    }
                    latLng = latLng2;
                }
            }
        }
        this.f5282c = arrayList;
        this.f5304y = 0;
        if (this.f5282c.size() > 0) {
            this.f5290k = builder.build();
        }
        this.f5280a.f743a.setRunLowFrame(false);
    }

    IPoint m5669a(IPoint iPoint, IPoint iPoint2, IPoint iPoint3, double d, int i) {
        IPoint iPoint4 = new IPoint();
        double d2 = (double) (iPoint2.f2030x - iPoint.f2030x);
        double d3 = (double) (iPoint2.f2031y - iPoint.f2031y);
        iPoint4.f2031y = (int) (((((double) i) * d) / Math.sqrt(((d3 * d3) / (d2 * d2)) + WeightedLatLng.DEFAULT_INTENSITY)) + ((double) iPoint3.f2031y));
        iPoint4.f2030x = (int) (((d3 * ((double) (iPoint3.f2031y - iPoint4.f2031y))) / d2) + ((double) iPoint3.f2030x));
        return iPoint4;
    }

    void m5673a(List<IPoint> list, List<IPoint> list2, double d) {
        if (list.size() == 3) {
            for (int i = 0; i <= 10; i = (int) (((float) i) + 1.0f)) {
                float f = ((float) i) / TitleBar.SHAREBTN_RIGHT_MARGIN;
                IPoint iPoint = new IPoint();
                double d2 = ((((WeightedLatLng.DEFAULT_INTENSITY - ((double) f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) * ((double) ((IPoint) list.get(0)).f2030x)) + (((((double) (2.0f * f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) * ((double) ((IPoint) list.get(1)).f2030x)) * d)) + ((double) (((float) ((IPoint) list.get(2)).f2030x) * (f * f)));
                double d3 = ((((WeightedLatLng.DEFAULT_INTENSITY - ((double) f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) * ((double) ((IPoint) list.get(0)).f2031y)) + (((((double) (2.0f * f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) * ((double) ((IPoint) list.get(1)).f2031y)) * d)) + ((double) (((float) ((IPoint) list.get(2)).f2031y) * (f * f)));
                double d4 = (((WeightedLatLng.DEFAULT_INTENSITY - ((double) f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) + ((((double) (2.0f * f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) * d)) + ((double) (f * f));
                iPoint.f2030x = (int) (d2 / ((((WeightedLatLng.DEFAULT_INTENSITY - ((double) f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) + ((((double) (2.0f * f)) * (WeightedLatLng.DEFAULT_INTENSITY - ((double) f))) * d)) + ((double) (f * f))));
                iPoint.f2031y = (int) (d3 / d4);
                list2.add(iPoint);
            }
        }
    }

    void m5671a(LatLng latLng, LatLng latLng2, List<IPoint> list, Builder builder) {
        double abs = (Math.abs(latLng.longitude - latLng2.longitude) * 3.141592653589793d) / 180.0d;
        LatLng latLng3 = new LatLng((latLng2.latitude + latLng.latitude) / 2.0d, (latLng2.longitude + latLng.longitude) / 2.0d, false);
        builder.include(latLng).include(latLng3).include(latLng2);
        int i = latLng3.latitude > 0.0d ? -1 : 1;
        IPoint iPoint = new IPoint();
        this.f5280a.f743a.latlon2Geo(latLng.latitude, latLng.longitude, iPoint);
        IPoint iPoint2 = new IPoint();
        this.f5280a.f743a.latlon2Geo(latLng2.latitude, latLng2.longitude, iPoint2);
        IPoint iPoint3 = new IPoint();
        this.f5280a.f743a.latlon2Geo(latLng3.latitude, latLng3.longitude, iPoint3);
        double cos = Math.cos(0.5d * abs);
        IPoint a = m5669a(iPoint, iPoint2, iPoint3, (Math.hypot((double) (iPoint.f2030x - iPoint2.f2030x), (double) (iPoint.f2031y - iPoint2.f2031y)) * 0.5d) * Math.tan(0.5d * abs), i);
        List arrayList = new ArrayList();
        arrayList.add(iPoint);
        arrayList.add(a);
        arrayList.add(iPoint2);
        m5673a(arrayList, (List) list, cos);
    }

    public void remove() throws RemoteException {
        this.f5280a.m1034c(getId());
        if (this.f5278K != null && this.f5278K.length > 0) {
            for (int valueOf : this.f5278K) {
                this.f5280a.m1029a(Integer.valueOf(valueOf));
            }
        }
        if (this.f5302w > 0) {
            this.f5280a.m1029a(Integer.valueOf(this.f5302w));
        }
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public String getId() throws RemoteException {
        if (this.f5281b == null) {
            this.f5281b = C0286v.m1022a("Polyline");
        }
        return this.f5281b;
    }

    public void setPoints(List<LatLng> list) throws RemoteException {
        try {
            this.f5284e = list;
            synchronized (this.f5291l) {
                m5672a((List) list);
            }
            this.f5297r = true;
            this.f5280a.f743a.setRunLowFrame(false);
        } catch (Throwable th) {
            ee.m4243a(th, "PolylineDelegateImp", "setPoints");
            this.f5282c.clear();
            th.printStackTrace();
        }
    }

    public List<LatLng> getPoints() throws RemoteException {
        return this.f5284e;
    }

    public void setWidth(float f) throws RemoteException {
        this.f5305z = f;
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public float getWidth() throws RemoteException {
        return this.f5305z;
    }

    public void setColor(int i) {
        if (this.f5301v == 0 || this.f5301v == 2) {
            this.f5303x = i;
            this.f5270C = ((float) Color.alpha(i)) / 255.0f;
            this.f5271D = ((float) Color.red(i)) / 255.0f;
            this.f5272E = ((float) Color.green(i)) / 255.0f;
            this.f5273F = ((float) Color.blue(i)) / 255.0f;
            if (this.f5293n) {
                this.f5301v = 0;
            }
            this.f5280a.f743a.setRunLowFrame(false);
        }
    }

    public int getColor() throws RemoteException {
        return this.f5303x;
    }

    public void setZIndex(float f) throws RemoteException {
        this.f5268A = f;
        this.f5280a.m1031b();
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public float getZIndex() throws RemoteException {
        return this.f5268A;
    }

    public void setVisible(boolean z) throws RemoteException {
        this.f5292m = z;
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public boolean isVisible() throws RemoteException {
        return this.f5292m;
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
        return true;
    }

    public void calMapFPoint() throws RemoteException {
        synchronized (this.f5291l) {
            this.f5283d.clear();
            this.f5299t = false;
            this.f5276I = new float[(this.f5282c.size() * 3)];
            int i = 0;
            for (IPoint iPoint : this.f5282c) {
                FPoint fPoint = new FPoint();
                this.f5280a.f743a.geo2Map(iPoint.f2031y, iPoint.f2030x, fPoint);
                this.f5276I[i * 3] = fPoint.f2028x;
                this.f5276I[(i * 3) + 1] = fPoint.f2029y;
                this.f5276I[(i * 3) + 2] = 0.0f;
                this.f5283d.add(fPoint);
                i++;
            }
        }
        if (!this.f5300u) {
            this.f5288i = dj.m575a(this.f5276I);
        }
        this.f5304y = this.f5282c.size();
        m5656a();
    }

    private void m5656a() {
        if (this.f5304y <= 5000 || this.f5269B > 12.0f) {
            this.f5275H = this.f5280a.f743a.getMapProjection().getMapLenWithWin(10);
            return;
        }
        float f = (this.f5305z / 2.0f) + (this.f5269B / 2.0f);
        if (f > 200.0f) {
            f = 200.0f;
        }
        this.f5275H = this.f5280a.f743a.getMapProjection().getMapLenWithWin((int) f);
    }

    private void m5660b(List<FPoint> list) throws RemoteException {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        if (size >= 2) {
            FPoint fPoint = (FPoint) list.get(0);
            arrayList.add(fPoint);
            int i = 1;
            FPoint fPoint2 = fPoint;
            while (i < size - 1) {
                fPoint = (FPoint) list.get(i);
                if (m5659a(fPoint2, fPoint)) {
                    arrayList.add(fPoint);
                } else {
                    fPoint = fPoint2;
                }
                i++;
                fPoint2 = fPoint;
            }
            arrayList.add(list.get(size - 1));
            this.f5276I = new float[(arrayList.size() * 3)];
            this.f5277J = null;
            this.f5277J = new int[arrayList.size()];
            Iterator it = arrayList.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                fPoint = (FPoint) it.next();
                this.f5277J[i2] = i2;
                this.f5276I[i2 * 3] = fPoint.f2028x;
                this.f5276I[(i2 * 3) + 1] = fPoint.f2029y;
                this.f5276I[(i2 * 3) + 2] = 0.0f;
                i2++;
            }
        }
    }

    private boolean m5659a(FPoint fPoint, FPoint fPoint2) {
        return Math.abs(fPoint2.f2028x - fPoint.f2028x) >= this.f5275H || Math.abs(fPoint2.f2029y - fPoint.f2029y) >= this.f5275H;
    }

    public void m5670a(BitmapDescriptor bitmapDescriptor) {
        this.f5293n = false;
        this.f5301v = 1;
        this.f5289j = bitmapDescriptor;
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public void draw(GL10 gl10) throws RemoteException {
        if (this.f5282c != null && this.f5282c.size() != 0 && this.f5305z > 0.0f) {
            if (this.f5297r) {
                calMapFPoint();
                this.f5297r = false;
            }
            if (this.f5276I != null && this.f5304y > 0) {
                if (this.f5300u) {
                    m5657a(gl10);
                } else {
                    if (this.f5288i == null) {
                        this.f5288i = dj.m575a(this.f5276I);
                    }
                    C0276t.m1006a(gl10, 3, this.f5303x, this.f5288i, this.f5305z, this.f5304y);
                }
            }
            this.f5299t = true;
        }
    }

    private void m5657a(GL10 gl10) {
        float mapLenWithWin = this.f5280a.f743a.getMapProjection().getMapLenWithWin((int) this.f5305z);
        switch (this.f5301v) {
            case 0:
                m5668f(gl10, mapLenWithWin);
                return;
            case 1:
                m5666d(gl10, mapLenWithWin);
                return;
            case 2:
                m5667e(gl10, mapLenWithWin);
                return;
            case 3:
                m5665c(gl10, mapLenWithWin);
                return;
            case 4:
                m5661b(gl10, mapLenWithWin);
                return;
            case 5:
                m5658a(gl10, mapLenWithWin);
                return;
            default:
                return;
        }
    }

    private void m5658a(GL10 gl10, float f) {
        int i = 0;
        if (!this.f5296q) {
            this.f5278K = new int[this.f5285f.size()];
            for (int i2 = 0; i2 < this.f5278K.length; i2++) {
                int i3;
                int texsureId = this.f5280a.f743a.getTexsureId();
                if (texsureId == 0) {
                    int[] iArr = new int[]{0};
                    gl10.glGenTextures(1, iArr, 0);
                    i3 = iArr[0];
                } else {
                    i3 = texsureId;
                }
                dj.m587b(gl10, i3, ((BitmapDescriptor) this.f5285f.get(i2)).getBitmap(), true);
                this.f5278K[i2] = i3;
            }
            this.f5296q = true;
        }
        int[] iArr2 = new int[this.f5286g.size()];
        while (i < iArr2.length) {
            iArr2[i] = this.f5278K[((Integer) this.f5286g.get(i)).intValue()];
            i++;
        }
        AMapNativeRenderer.nativeDrawLineByMultiTextureID(this.f5276I, this.f5276I.length, f, iArr2, iArr2.length, this.f5277J, this.f5277J.length, this.f5274G);
    }

    private void m5661b(GL10 gl10, float f) {
        int[] iArr = new int[this.f5287h.size()];
        for (int i = 0; i < this.f5287h.size(); i++) {
            iArr[i] = ((Integer) this.f5287h.get(i)).intValue();
        }
        AMapNativeRenderer.nativeDrawGradientColorLine(this.f5276I, this.f5276I.length, f, iArr, this.f5287h.size(), this.f5277J, this.f5277J.length, this.f5280a.f743a.getLineTextureID());
    }

    private void m5665c(GL10 gl10, float f) {
        int[] iArr = new int[this.f5287h.size()];
        for (int i = 0; i < this.f5287h.size(); i++) {
            iArr[i] = ((Integer) this.f5287h.get(i)).intValue();
        }
        AMapNativeRenderer.nativeDrawLineByMultiColor(this.f5276I, this.f5276I.length, f, this.f5280a.f743a.getLineTextureID(), iArr, this.f5287h.size(), this.f5277J, this.f5277J.length);
    }

    private void m5666d(GL10 gl10, float f) {
        if (!this.f5296q) {
            this.f5302w = this.f5280a.f743a.getTexsureId();
            if (this.f5302w == 0) {
                int[] iArr = new int[]{0};
                gl10.glGenTextures(1, iArr, 0);
                this.f5302w = iArr[0];
            }
            if (this.f5289j != null) {
                dj.m587b(gl10, this.f5302w, this.f5289j.getBitmap(), true);
            }
            this.f5296q = true;
        }
        try {
            List list = this.f5283d;
            if (m5662b()) {
                synchronized (this.f5291l) {
                    list = dj.m577a(this.f5280a.f743a, this.f5283d, false);
                }
            }
            m5660b(list);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        AMapNativeRenderer.nativeDrawLineByTextureID(this.f5276I, this.f5276I.length, f, this.f5302w, this.f5271D, this.f5272E, this.f5273F, this.f5270C, this.f5274G, false, false, false);
    }

    private void m5667e(GL10 gl10, float f) {
        AMapNativeRenderer.nativeDrawLineByTextureID(this.f5276I, this.f5276I.length, f, this.f5280a.f743a.getImaginaryLineTextureID(), this.f5271D, this.f5272E, this.f5273F, this.f5270C, 0.0f, true, true, false);
    }

    private void m5668f(GL10 gl10, float f) {
        try {
            List list = this.f5283d;
            if (m5662b()) {
                synchronized (this.f5291l) {
                    list = dj.m577a(this.f5280a.f743a, this.f5283d, false);
                }
            }
            m5660b(list);
            AMapNativeRenderer.nativeDrawLineByTextureID(this.f5276I, this.f5276I.length, f, this.f5280a.f743a.getLineTextureID(), this.f5271D, this.f5272E, this.f5273F, this.f5270C, 0.0f, false, true, false);
        } catch (Throwable th) {
        }
    }

    private boolean m5662b() {
        try {
            this.f5269B = this.f5280a.f743a.getCameraPosition().zoom;
            m5656a();
            if (this.f5269B <= TitleBar.SHAREBTN_RIGHT_MARGIN || this.f5301v > 2) {
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            if (this.f5280a.f743a == null) {
                return false;
            }
            Rect rect = new Rect(-100, -100, this.f5280a.f743a.getMapWidth() + 100, this.f5280a.f743a.getMapHeight() + 100);
            LatLng latLng = this.f5290k.northeast;
            LatLng latLng2 = this.f5290k.southwest;
            IPoint iPoint = new IPoint();
            this.f5280a.f743a.getLatLng2Pixel(latLng.latitude, latLng2.longitude, iPoint);
            IPoint iPoint2 = new IPoint();
            this.f5280a.f743a.getLatLng2Pixel(latLng.latitude, latLng.longitude, iPoint2);
            IPoint iPoint3 = new IPoint();
            this.f5280a.f743a.getLatLng2Pixel(latLng2.latitude, latLng.longitude, iPoint3);
            IPoint iPoint4 = new IPoint();
            this.f5280a.f743a.getLatLng2Pixel(latLng2.latitude, latLng2.longitude, iPoint4);
            if (rect.contains(iPoint.f2030x, iPoint.f2031y) && rect.contains(iPoint2.f2030x, iPoint2.f2031y) && rect.contains(iPoint3.f2030x, iPoint3.f2031y) && rect.contains(iPoint4.f2030x, iPoint4.f2031y)) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public void destroy() {
        try {
            remove();
            if (this.f5276I != null) {
                this.f5276I = null;
            }
            if (this.f5288i != null) {
                this.f5288i.clear();
                this.f5288i = null;
            }
            if (this.f5285f != null && this.f5285f.size() > 0) {
                for (BitmapDescriptor recycle : this.f5285f) {
                    recycle.recycle();
                }
            }
            if (this.f5289j != null) {
                this.f5289j.recycle();
            }
            if (this.f5287h != null) {
                this.f5287h.clear();
                this.f5287h = null;
            }
            if (this.f5286g != null) {
                this.f5286g.clear();
                this.f5286g = null;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "PolylineDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "PolylineDelegateImp destroy");
        }
    }

    public boolean isDrawFinish() {
        return this.f5299t;
    }

    public LatLng getNearestLatLng(LatLng latLng) {
        int i = 0;
        if (latLng == null) {
            return null;
        }
        if (this.f5284e == null || this.f5284e.size() == 0) {
            return null;
        }
        float f = 0.0f;
        int i2 = 0;
        while (i < this.f5284e.size()) {
            try {
                float calculateLineDistance;
                int i3;
                if (i == 0) {
                    calculateLineDistance = AMapUtils.calculateLineDistance(latLng, (LatLng) this.f5284e.get(i));
                    i3 = i2;
                } else {
                    calculateLineDistance = AMapUtils.calculateLineDistance(latLng, (LatLng) this.f5284e.get(i));
                    if (f > calculateLineDistance) {
                        i3 = i;
                    } else {
                        calculateLineDistance = f;
                        i3 = i2;
                    }
                }
                i++;
                i2 = i3;
                f = calculateLineDistance;
            } catch (Throwable th) {
                ee.m4243a(th, "PolylineDelegateImp", "getNearestLatLng");
                th.printStackTrace();
                return null;
            }
        }
        return (LatLng) this.f5284e.get(i2);
    }

    public boolean contains(LatLng latLng) {
        Object obj = new float[this.f5276I.length];
        System.arraycopy(this.f5276I, 0, obj, 0, this.f5276I.length);
        if (obj.length / 3 < 2) {
            return false;
        }
        try {
            ArrayList c = m5663c();
            if (c == null || c.size() < 1) {
                return false;
            }
            double mapLenWithWin = (double) this.f5280a.f743a.getMapProjection().getMapLenWithWin(((int) this.f5305z) / 4);
            double mapLenWithWin2 = (double) this.f5280a.f743a.getMapProjection().getMapLenWithWin((int) this.f5279L);
            FPoint a = m5655a(latLng);
            FPoint fPoint = null;
            for (int i = 0; i < c.size() - 1; i++) {
                FPoint fPoint2;
                if (i == 0) {
                    fPoint2 = (FPoint) c.get(i);
                } else {
                    fPoint2 = fPoint;
                }
                fPoint = (FPoint) c.get(i + 1);
                if ((mapLenWithWin2 + mapLenWithWin) - m5654a(a, fPoint2, fPoint) >= 0.0d) {
                    c.clear();
                    return true;
                }
            }
            c.clear();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private ArrayList<FPoint> m5663c() {
        ArrayList<FPoint> arrayList = new ArrayList();
        int i = 0;
        while (i < this.f5276I.length) {
            float f = this.f5276I[i];
            i++;
            float f2 = this.f5276I[i];
            i++;
            arrayList.add(new FPoint(f, f2));
            i++;
        }
        return arrayList;
    }

    private double m5654a(FPoint fPoint, FPoint fPoint2, FPoint fPoint3) {
        return m5653a((double) fPoint.f2028x, (double) fPoint.f2029y, (double) fPoint2.f2028x, (double) fPoint2.f2029y, (double) fPoint3.f2028x, (double) fPoint3.f2029y);
    }

    private FPoint m5655a(LatLng latLng) {
        IPoint iPoint = new IPoint();
        this.f5280a.f743a.latlon2Geo(latLng.latitude, latLng.longitude, iPoint);
        FPoint fPoint = new FPoint();
        this.f5280a.f743a.geo2Map(iPoint.f2031y, iPoint.f2030x, fPoint);
        return fPoint;
    }

    private double m5653a(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = ((d5 - d3) * (d - d3)) + ((d6 - d4) * (d2 - d4));
        if (d7 <= 0.0d) {
            return Math.sqrt(((d - d3) * (d - d3)) + ((d2 - d4) * (d2 - d4)));
        }
        double d8 = ((d5 - d3) * (d5 - d3)) + ((d6 - d4) * (d6 - d4));
        if (d7 >= d8) {
            return Math.sqrt(((d - d5) * (d - d5)) + ((d2 - d6) * (d2 - d6)));
        }
        d7 /= d8;
        d8 = ((d5 - d3) * d7) + d3;
        d7 = (d7 * (d6 - d4)) + d4;
        return Math.sqrt(((d7 - d2) * (d7 - d2)) + ((d - d8) * (d - d8)));
    }

    public void setTransparency(float f) {
        this.f5274G = f;
        this.f5280a.f743a.setRunLowFrame(false);
    }

    public void setCustomTextureList(List<BitmapDescriptor> list) {
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                this.f5293n = false;
                this.f5301v = 5;
                this.f5285f = list;
                this.f5280a.f743a.setRunLowFrame(false);
                return;
            }
            m5670a((BitmapDescriptor) list.get(0));
        }
    }

    public void setCustemTextureIndex(List<Integer> list) {
        if (list != null && list.size() != 0) {
            this.f5286g = m5664c(list);
        }
    }

    public void setColorValues(List<Integer> list) {
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                this.f5293n = false;
                this.f5287h = m5664c(list);
                this.f5301v = 3;
                this.f5280a.f743a.setRunLowFrame(false);
                return;
            }
            setColor(((Integer) list.get(0)).intValue());
        }
    }

    public void useGradient(boolean z) {
        if (z && this.f5287h != null && this.f5287h.size() > 1) {
            this.f5298s = z;
            this.f5301v = 4;
            this.f5280a.f743a.setRunLowFrame(false);
        }
    }

    private List<Integer> m5664c(List<Integer> list) {
        Object obj = new int[list.size()];
        List<Integer> arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            int intValue = ((Integer) list.get(i3)).intValue();
            if (i3 == 0) {
                arrayList.add(Integer.valueOf(intValue));
            } else if (intValue != i2) {
                arrayList.add(Integer.valueOf(intValue));
            } else {
            }
            obj[i] = i3;
            i++;
            i2 = intValue;
        }
        this.f5277J = new int[arrayList.size()];
        System.arraycopy(obj, 0, this.f5277J, 0, this.f5277J.length);
        return arrayList;
    }

    public void reLoadTexture() {
        this.f5296q = false;
        this.f5302w = 0;
        if (this.f5278K != null) {
            Arrays.fill(this.f5278K, 0);
        }
    }
}
