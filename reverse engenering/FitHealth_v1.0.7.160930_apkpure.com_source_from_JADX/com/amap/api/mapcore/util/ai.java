package com.amap.api.mapcore.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES10;
import android.os.Build.VERSION;
import android.os.RemoteException;
import android.util.Log;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IMarkerDelegate;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: MarkerDelegateImp */
class ai implements IMarkerDelegate {
    private static int f3945a = 0;
    private boolean f3946A = false;
    private boolean f3947B = true;
    private ae f3948C;
    private FloatBuffer f3949D;
    private Object f3950E;
    private boolean f3951F = false;
    private CopyOnWriteArrayList<BitmapDescriptor> f3952G = null;
    private boolean f3953H = false;
    private boolean f3954I = false;
    private boolean f3955J = true;
    private int f3956K = 0;
    private int f3957L = 20;
    private boolean f3958M = false;
    private int f3959N;
    private int f3960O;
    private long f3961P = 0;
    private boolean f3962b = false;
    private boolean f3963c = false;
    private boolean f3964d = false;
    private float f3965e = 0.0f;
    private float f3966f = 0.0f;
    private boolean f3967g = false;
    private int f3968h = 0;
    private int f3969i = 0;
    private int f3970j = 0;
    private int f3971k = 0;
    private int f3972l;
    private int f3973m;
    private FPoint f3974n = new FPoint();
    private float[] f3975o;
    private int[] f3976p = null;
    private float f3977q = 0.0f;
    private boolean f3978r = false;
    private FloatBuffer f3979s = null;
    private String f3980t;
    private LatLng f3981u;
    private LatLng f3982v;
    private String f3983w;
    private String f3984x;
    private float f3985y = 0.5f;
    private float f3986z = 1.0f;

    private static String m3971a(String str) {
        f3945a++;
        return str + f3945a;
    }

    public void setRotateAngle(float f) {
        this.f3966f = f;
        this.f3965e = (((-f) % 360.0f) + 360.0f) % 360.0f;
        if (isInfoWindowShown()) {
            this.f3948C.m162f(this);
            this.f3948C.m160e(this);
        }
        m3975c();
    }

    public boolean isDestory() {
        return this.f3978r;
    }

    public void realDestroy() {
        if (this.f3978r) {
            try {
                if (this.f3952G != null) {
                    Iterator it = this.f3952G.iterator();
                    while (it.hasNext()) {
                        ((BitmapDescriptor) it.next()).recycle();
                    }
                    this.f3952G = null;
                }
                if (this.f3949D != null) {
                    this.f3949D.clear();
                    this.f3949D = null;
                }
                if (this.f3979s != null) {
                    this.f3979s.clear();
                    this.f3979s = null;
                }
                this.f3981u = null;
                this.f3950E = null;
                this.f3976p = null;
            } catch (Throwable th) {
                ee.m4243a(th, "MarkerDelegateImp", "realdestroy");
                th.printStackTrace();
                Log.d("destroy erro", "MarkerDelegateImp destroy");
            }
        }
    }

    public void destroy() {
        try {
            int i;
            this.f3978r = true;
            remove();
            if (this.f3948C != null) {
                this.f3948C.f139a.callRunDestroy();
                i = 0;
                while (this.f3976p != null && i < this.f3976p.length) {
                    this.f3948C.m147a(Integer.valueOf(this.f3976p[i]));
                    this.f3948C.m145a(this.f3976p[i]);
                    i++;
                }
            }
            i = 0;
            while (this.f3952G != null && i < this.f3952G.size()) {
                ((BitmapDescriptor) this.f3952G.get(i)).recycle();
                i++;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "MarkerDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "MarkerDelegateImp destroy");
        }
    }

    synchronized void m3976a() {
        if (this.f3952G == null) {
            this.f3952G = new CopyOnWriteArrayList();
        } else {
            this.f3952G.clear();
        }
    }

    public synchronized void m3977a(ArrayList<BitmapDescriptor> arrayList) {
        m3976a();
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                BitmapDescriptor bitmapDescriptor = (BitmapDescriptor) it.next();
                if (bitmapDescriptor != null) {
                    this.f3952G.add(bitmapDescriptor);
                }
            }
        }
    }

    public ai(MarkerOptions markerOptions, ae aeVar) {
        this.f3948C = aeVar;
        this.f3981u = markerOptions.getPosition();
        IPoint iPoint = new IPoint();
        this.f3953H = markerOptions.isGps();
        if (markerOptions.getPosition() != null) {
            if (this.f3953H) {
                try {
                    double[] a = gd.m996a(markerOptions.getPosition().longitude, markerOptions.getPosition().latitude);
                    this.f3982v = new LatLng(a[1], a[0]);
                    MapProjection.lonlat2Geo(a[0], a[1], iPoint);
                } catch (Throwable th) {
                    ee.m4243a(th, "MarkerDelegateImp", "create");
                    this.f3982v = markerOptions.getPosition();
                }
            } else {
                MapProjection.lonlat2Geo(this.f3981u.longitude, this.f3981u.latitude, iPoint);
            }
        }
        this.f3972l = iPoint.f2030x;
        this.f3973m = iPoint.f2031y;
        this.f3985y = markerOptions.getAnchorU();
        this.f3986z = markerOptions.getAnchorV();
        this.f3968h = markerOptions.getInfoWindowOffsetX();
        this.f3969i = markerOptions.getInfoWindowOffsetY();
        this.f3957L = markerOptions.getPeriod();
        this.f3977q = markerOptions.getZIndex();
        calFPoint();
        m3977a(markerOptions.getIcons());
        this.f3947B = markerOptions.isVisible();
        this.f3984x = markerOptions.getSnippet();
        this.f3983w = markerOptions.getTitle();
        this.f3946A = markerOptions.isDraggable();
        this.f3980t = getId();
        this.f3951F = markerOptions.isPerspective();
        this.f3967g = markerOptions.isFlat();
    }

    public IPoint m3978b() {
        if (this.f3981u == null && !this.f3958M) {
            return null;
        }
        IPoint iPoint = new IPoint();
        this.f3948C.m142a().getMapProjection().map2Win(this.f3974n.f2028x, this.f3974n.f2029y, iPoint);
        return iPoint;
    }

    public int getWidth() {
        try {
            return getBitmapDescriptor().getWidth();
        } catch (Throwable th) {
            return 0;
        }
    }

    public int getHeight() {
        try {
            return getBitmapDescriptor().getHeight();
        } catch (Throwable th) {
            return 0;
        }
    }

    public FPoint anchorUVoff() {
        FPoint fPoint = new FPoint();
        if (!(this.f3952G == null || this.f3952G.size() == 0)) {
            fPoint.f2028x = ((float) getWidth()) * this.f3985y;
            fPoint.f2029y = ((float) getHeight()) * this.f3986z;
        }
        return fPoint;
    }

    public boolean isContains() {
        return this.f3948C.m150a((IMarkerDelegate) this);
    }

    public IPoint getAnchor() {
        IPoint b = m3978b();
        if (b == null) {
            return null;
        }
        return b;
    }

    public Rect getRect() {
        if (this.f3975o == null) {
            return new Rect(0, 0, 0, 0);
        }
        try {
            Rect rect;
            MapProjection mapProjection = this.f3948C.f139a.getMapProjection();
            int width = getWidth();
            int height = getHeight();
            IPoint iPoint = new IPoint();
            IPoint iPoint2 = new IPoint();
            mapProjection.map2Win(this.f3974n.f2028x, this.f3974n.f2029y, iPoint);
            if (this.f3967g) {
                mapProjection.map2Win(this.f3975o[0], this.f3975o[1], iPoint2);
                rect = new Rect(iPoint2.f2030x, iPoint2.f2031y, iPoint2.f2030x, iPoint2.f2031y);
                mapProjection.map2Win(this.f3975o[3], this.f3975o[4], iPoint2);
                rect.union(iPoint2.f2030x, iPoint2.f2031y);
                mapProjection.map2Win(this.f3975o[6], this.f3975o[7], iPoint2);
                rect.union(iPoint2.f2030x, iPoint2.f2031y);
                mapProjection.map2Win(this.f3975o[9], this.f3975o[10], iPoint2);
                rect.union(iPoint2.f2030x, iPoint2.f2031y);
            } else {
                m3972a((-this.f3985y) * ((float) width), (this.f3986z - 1.0f) * ((float) height), iPoint2);
                rect = new Rect(iPoint.f2030x + iPoint2.f2030x, iPoint.f2031y - iPoint2.f2031y, iPoint.f2030x + iPoint2.f2030x, iPoint.f2031y - iPoint2.f2031y);
                m3972a((-this.f3985y) * ((float) width), this.f3986z * ((float) height), iPoint2);
                rect.union(iPoint.f2030x + iPoint2.f2030x, iPoint.f2031y - iPoint2.f2031y);
                m3972a((1.0f - this.f3985y) * ((float) width), this.f3986z * ((float) height), iPoint2);
                rect.union(iPoint.f2030x + iPoint2.f2030x, iPoint.f2031y - iPoint2.f2031y);
                m3972a((1.0f - this.f3985y) * ((float) width), (this.f3986z - 1.0f) * ((float) height), iPoint2);
                rect.union(iPoint.f2030x + iPoint2.f2030x, iPoint.f2031y - iPoint2.f2031y);
            }
            this.f3970j = rect.centerX() - iPoint.f2030x;
            this.f3971k = rect.top - iPoint.f2031y;
            return rect;
        } catch (Throwable th) {
            ee.m4243a(th, "MarkerDelegateImp", "getRect");
            th.printStackTrace();
            return new Rect(0, 0, 0, 0);
        }
    }

    public boolean remove() {
        m3975c();
        this.f3947B = false;
        if (this.f3948C != null) {
            return this.f3948C.m156c(this);
        }
        return false;
    }

    private void m3975c() {
        if (this.f3948C.f139a != null) {
            this.f3948C.f139a.setRunLowFrame(false);
        }
    }

    public LatLng getPosition() {
        if (!this.f3958M || this.f3974n == null) {
            return this.f3981u;
        }
        DPoint dPoint = new DPoint();
        IPoint iPoint = new IPoint();
        calFPoint();
        this.f3948C.f139a.map2Geo(this.f3974n.f2028x, this.f3974n.f2029y, iPoint);
        MapProjection.geo2LonLat(iPoint.f2030x, iPoint.f2031y, dPoint);
        return new LatLng(dPoint.f2027y, dPoint.f2026x);
    }

    public String getId() {
        if (this.f3980t == null) {
            this.f3980t = m3971a("Marker");
        }
        return this.f3980t;
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            ee.m4243a(new AMapException("非法坐标值 latlng is null"), "setPosition", "Marker");
            return;
        }
        this.f3981u = latLng;
        IPoint iPoint = new IPoint();
        if (this.f3953H) {
            try {
                double[] a = gd.m996a(latLng.longitude, latLng.latitude);
                this.f3982v = new LatLng(a[1], a[0]);
                MapProjection.lonlat2Geo(a[0], a[1], iPoint);
            } catch (Throwable th) {
                this.f3982v = latLng;
            }
        } else {
            MapProjection.lonlat2Geo(latLng.longitude, latLng.latitude, iPoint);
        }
        this.f3972l = iPoint.f2030x;
        this.f3973m = iPoint.f2031y;
        this.f3958M = false;
        calFPoint();
        m3975c();
    }

    public void setTitle(String str) {
        this.f3983w = str;
        m3975c();
    }

    public String getTitle() {
        return this.f3983w;
    }

    public void setSnippet(String str) {
        this.f3984x = str;
        m3975c();
    }

    public String getSnippet() {
        return this.f3984x;
    }

    public void setDraggable(boolean z) {
        this.f3946A = z;
        m3975c();
    }

    public synchronized void setIcons(ArrayList<BitmapDescriptor> arrayList) {
        if (arrayList != null) {
            try {
                if (this.f3952G != null) {
                    m3977a((ArrayList) arrayList);
                    this.f3954I = false;
                    this.f3962b = false;
                    if (this.f3949D != null) {
                        this.f3949D.clear();
                        this.f3949D = null;
                    }
                    this.f3976p = null;
                    if (isInfoWindowShown()) {
                        this.f3948C.m162f(this);
                        this.f3948C.m160e(this);
                    }
                    m3975c();
                }
            } catch (Throwable th) {
                ee.m4243a(th, "MarkerDelegateImp", "setIcons");
                th.printStackTrace();
            }
        }
    }

    public synchronized ArrayList<BitmapDescriptor> getIcons() {
        ArrayList<BitmapDescriptor> arrayList;
        if (this.f3952G == null || this.f3952G.size() <= 0) {
            arrayList = null;
        } else {
            ArrayList<BitmapDescriptor> arrayList2 = new ArrayList();
            Iterator it = this.f3952G.iterator();
            while (it.hasNext()) {
                arrayList2.add((BitmapDescriptor) it.next());
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public synchronized void setIcon(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor != null) {
            try {
                if (this.f3952G != null) {
                    this.f3952G.clear();
                    this.f3952G.add(bitmapDescriptor);
                    this.f3954I = false;
                    this.f3962b = false;
                    this.f3976p = null;
                    if (this.f3949D != null) {
                        this.f3949D.clear();
                        this.f3949D = null;
                    }
                    if (isInfoWindowShown()) {
                        this.f3948C.m162f(this);
                        this.f3948C.m160e(this);
                    }
                    m3975c();
                }
            } catch (Throwable th) {
                ee.m4243a(th, "MarkerDelegateImp", "setIcon");
                th.printStackTrace();
            }
        }
    }

    public synchronized BitmapDescriptor getBitmapDescriptor() {
        BitmapDescriptor bitmapDescriptor;
        try {
            if (this.f3952G == null || this.f3952G.size() == 0) {
                m3976a();
                this.f3952G.add(BitmapDescriptorFactory.defaultMarker());
            } else if (this.f3952G.get(0) == null) {
                this.f3952G.clear();
                bitmapDescriptor = getBitmapDescriptor();
            }
            bitmapDescriptor = (BitmapDescriptor) this.f3952G.get(0);
        } catch (Throwable th) {
            ee.m4243a(th, "MarkerDelegateImp", "getBitmapDescriptor");
            th.printStackTrace();
            bitmapDescriptor = null;
        }
        return bitmapDescriptor;
    }

    public boolean isDraggable() {
        return this.f3946A;
    }

    public void showInfoWindow() {
        if (this.f3947B) {
            this.f3948C.m160e(this);
            m3975c();
        }
    }

    public void hideInfoWindow() {
        if (isInfoWindowShown()) {
            this.f3948C.m162f(this);
            m3975c();
            this.f3963c = false;
        }
        this.f3964d = false;
    }

    public void setInfoWindowShown(boolean z) {
        this.f3963c = z;
        if (this.f3963c && this.f3958M) {
            this.f3964d = true;
        }
    }

    public boolean isInfoWindowShown() {
        return this.f3963c;
    }

    public void setVisible(boolean z) {
        if (this.f3947B != z) {
            this.f3947B = z;
            if (!z && isInfoWindowShown()) {
                this.f3948C.m162f(this);
            }
            m3975c();
        }
    }

    public boolean isVisible() {
        return this.f3947B;
    }

    public void setAnchor(float f, float f2) {
        if (this.f3985y != f || this.f3986z != f2) {
            this.f3985y = f;
            this.f3986z = f2;
            if (isInfoWindowShown()) {
                this.f3948C.m162f(this);
                this.f3948C.m160e(this);
            }
            m3975c();
        }
    }

    public float getAnchorU() {
        return this.f3985y;
    }

    public float getAnchorV() {
        return this.f3986z;
    }

    public boolean equalsRemote(IMarkerDelegate iMarkerDelegate) throws RemoteException {
        if (equals(iMarkerDelegate) || iMarkerDelegate.getId().equals(getId())) {
            return true;
        }
        return false;
    }

    public int hashCodeRemote() {
        return super.hashCode();
    }

    public boolean calFPoint() {
        if (this.f3958M) {
            this.f3948C.f139a.getMapProjection().win2Map(this.f3959N, this.f3960O, this.f3974n);
        } else {
            this.f3948C.f139a.getMapProjection().geo2Map(this.f3972l, this.f3973m, this.f3974n);
        }
        return true;
    }

    private void m3974a(IAMapDelegate iAMapDelegate) throws RemoteException {
        float[] a = dj.m585a(iAMapDelegate, this.f3967g ? 1 : 0, this.f3974n, this.f3965e, getWidth(), getHeight(), this.f3985y, this.f3986z);
        this.f3975o = (float[]) a.clone();
        if (this.f3979s == null) {
            this.f3979s = dj.m575a(a);
        } else {
            this.f3979s = dj.m576a(a, this.f3979s);
        }
        if (this.f3952G != null && this.f3952G.size() > 0) {
            this.f3956K++;
            if (this.f3956K >= this.f3957L * this.f3952G.size()) {
                this.f3956K = 0;
            }
            int i = this.f3956K / this.f3957L;
            if (!this.f3955J) {
                m3975c();
            }
            if (this.f3976p != null && this.f3976p.length > 0) {
                m3973a(this.f3976p[i % this.f3952G.size()], this.f3979s, this.f3949D);
            }
        }
    }

    private void m3972a(float f, float f2, IPoint iPoint) {
        float f3 = (float) ((3.141592653589793d * ((double) this.f3965e)) / 180.0d);
        iPoint.f2030x = (int) ((((double) f) * Math.cos((double) f3)) + (((double) f2) * Math.sin((double) f3)));
        iPoint.f2031y = (int) ((((double) f2) * Math.cos((double) f3)) - (Math.sin((double) f3) * ((double) f)));
    }

    private void m3973a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (i != 0 && floatBuffer != null && floatBuffer2 != null) {
            GLES10.glEnable(3042);
            GLES10.glBlendFunc(1, 771);
            GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GLES10.glEnable(3553);
            GLES10.glEnableClientState(32884);
            GLES10.glEnableClientState(32888);
            GLES10.glBindTexture(3553, i);
            GLES10.glVertexPointer(3, 5126, 0, floatBuffer);
            GLES10.glTexCoordPointer(2, 5126, 0, floatBuffer2);
            GLES10.glDrawArrays(6, 0, 4);
            GLES10.glDisableClientState(32884);
            GLES10.glDisableClientState(32888);
            GLES10.glDisable(3553);
            GLES10.glDisable(3042);
        }
    }

    public void drawMarker(GL10 gl10, IAMapDelegate iAMapDelegate) {
        int i = 0;
        if (this.f3947B && !this.f3978r) {
            if (this.f3981u == null && !this.f3958M) {
                return;
            }
            if (getBitmapDescriptor() != null || this.f3952G != null) {
                int i2;
                int i3;
                BitmapDescriptor bitmapDescriptor;
                if (!this.f3954I) {
                    try {
                        if (this.f3952G != null) {
                            this.f3976p = new int[this.f3952G.size()];
                            i2 = VERSION.SDK_INT >= 12 ? 1 : 0;
                            Iterator it = this.f3952G.iterator();
                            i3 = 0;
                            while (it.hasNext()) {
                                bitmapDescriptor = (BitmapDescriptor) it.next();
                                if (i2 != 0) {
                                    i = this.f3948C.m141a(bitmapDescriptor);
                                }
                                if (i == 0) {
                                    Bitmap bitmap = bitmapDescriptor.getBitmap();
                                    if (!(bitmap == null || bitmap.isRecycled())) {
                                        i = m3970a(gl10);
                                        if (i2 != 0) {
                                            this.f3948C.m146a(new am(bitmapDescriptor, i));
                                        }
                                        dj.m587b(gl10, i, bitmap, false);
                                    }
                                }
                                int i4 = i;
                                this.f3976p[i3] = i4;
                                i3++;
                                i = i4;
                            }
                            if (this.f3952G.size() == 1) {
                                this.f3955J = true;
                            } else {
                                this.f3955J = false;
                            }
                            this.f3954I = true;
                        }
                    } catch (Throwable th) {
                        ee.m4243a(th, "MarkerDelegateImp", "loadtexture");
                        return;
                    }
                }
                try {
                    if (!this.f3962b) {
                        if (this.f3949D == null) {
                            bitmapDescriptor = getBitmapDescriptor();
                            if (bitmapDescriptor != null) {
                                i = bitmapDescriptor.getWidth();
                                i3 = bitmapDescriptor.getHeight();
                                i2 = bitmapDescriptor.getBitmap().getHeight();
                                float width = ((float) i) / ((float) bitmapDescriptor.getBitmap().getWidth());
                                float f = ((float) i3) / ((float) i2);
                                this.f3949D = dj.m575a(new float[]{0.0f, f, width, f, width, 0.0f, 0.0f, 0.0f});
                            } else {
                                return;
                            }
                        }
                        calFPoint();
                        this.f3961P = System.currentTimeMillis();
                        this.f3962b = true;
                    }
                    if (this.f3958M) {
                        iAMapDelegate.pixel2Map(this.f3959N, this.f3960O, this.f3974n);
                    }
                    m3974a(iAMapDelegate);
                    if (this.f3964d && isInfoWindowShown()) {
                        this.f3948C.m167k();
                        if (System.currentTimeMillis() - this.f3961P > 1000) {
                            this.f3964d = false;
                        }
                    }
                } catch (Throwable th2) {
                    ee.m4243a(th2, "MarkerDelegateImp", "drawMarker");
                }
            }
        }
    }

    private int m3970a(GL10 gl10) {
        int texsureId = this.f3948C.f139a.getTexsureId();
        if (texsureId != 0) {
            return texsureId;
        }
        int[] iArr = new int[]{0};
        gl10.glGenTextures(1, iArr, 0);
        return iArr[0];
    }

    public boolean isAllowLow() {
        return this.f3955J;
    }

    public void setPeriod(int i) {
        if (i <= 1) {
            this.f3957L = 1;
        } else {
            this.f3957L = i;
        }
    }

    public void setObject(Object obj) {
        this.f3950E = obj;
    }

    public Object getObject() {
        return this.f3950E;
    }

    public void setPerspective(boolean z) {
        this.f3951F = z;
    }

    public boolean isPerspective() {
        return this.f3951F;
    }

    public int getTextureId() {
        int i = 0;
        try {
            if (this.f3952G != null && this.f3952G.size() > 0) {
                i = this.f3976p[0];
            }
        } catch (Throwable th) {
        }
        return i;
    }

    public int getPeriod() {
        return this.f3957L;
    }

    public LatLng getRealPosition() {
        if (!this.f3958M) {
            return this.f3953H ? this.f3982v : this.f3981u;
        } else {
            this.f3948C.f139a.getMapProjection().win2Map(this.f3959N, this.f3960O, this.f3974n);
            DPoint dPoint = new DPoint();
            this.f3948C.f139a.getPixel2LatLng(this.f3959N, this.f3960O, dPoint);
            return new LatLng(dPoint.f2027y, dPoint.f2027y);
        }
    }

    public void set2Top() {
        this.f3948C.m157d(this);
    }

    public void setFlat(boolean z) throws RemoteException {
        this.f3967g = z;
        m3975c();
    }

    public boolean isFlat() {
        return this.f3967g;
    }

    public float getRotateAngle() {
        return this.f3966f;
    }

    public void setInfoWindowOffset(int i, int i2) throws RemoteException {
        this.f3968h = i;
        this.f3969i = i2;
    }

    public int getInfoWindowOffsetX() {
        return this.f3968h;
    }

    public int getInfoWindowOffsetY() {
        return this.f3969i;
    }

    public void setPositionByPixels(int i, int i2) {
        int i3 = 1;
        this.f3959N = i;
        this.f3960O = i2;
        this.f3958M = true;
        calFPoint();
        try {
            IAMapDelegate iAMapDelegate = this.f3948C.f139a;
            if (!this.f3967g) {
                i3 = 0;
            }
            this.f3975o = dj.m585a(iAMapDelegate, i3, this.f3974n, this.f3965e, getWidth(), getHeight(), this.f3985y, this.f3986z);
        } catch (Throwable th) {
            ee.m4243a(th, "MarkerDelegateImp", "setPositionByPixels");
        }
        m3975c();
        if (isInfoWindowShown()) {
            showInfoWindow();
        }
    }

    public int getRealInfoWindowOffsetX() {
        return this.f3970j;
    }

    public int getRealInfoWindowOffsetY() {
        return this.f3971k;
    }

    public FPoint getMapPosition() {
        return this.f3974n;
    }

    public boolean isViewMode() {
        return this.f3958M;
    }

    public void setZIndex(float f) {
        this.f3977q = f;
        this.f3948C.m165i();
    }

    public float getZIndex() {
        return this.f3977q;
    }

    public boolean checkInBounds() {
        Rect rect = this.f3948C.f139a.getRect();
        if (this.f3958M || rect == null) {
            return true;
        }
        IPoint iPoint = new IPoint();
        if (this.f3974n != null) {
            this.f3948C.f139a.getMapProjection().map2Win(this.f3974n.f2028x, this.f3974n.f2029y, iPoint);
        }
        return rect.contains(iPoint.f2030x, iPoint.f2031y);
    }

    public void setGeoPoint(IPoint iPoint) {
        this.f3958M = false;
        this.f3972l = iPoint.f2030x;
        this.f3973m = iPoint.f2031y;
        DPoint dPoint = new DPoint();
        MapProjection.geo2LonLat(this.f3972l, this.f3973m, dPoint);
        this.f3981u = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
        this.f3948C.f139a.getMapProjection().geo2Map(this.f3972l, this.f3973m, this.f3974n);
    }

    public IPoint getGeoPoint() {
        IPoint iPoint = new IPoint();
        if (!this.f3958M) {
            return new IPoint(this.f3972l, this.f3973m);
        }
        this.f3948C.f139a.getPixel2Geo(this.f3959N, this.f3960O, iPoint);
        return iPoint;
    }

    public void reLoadTexture() {
        this.f3954I = false;
        if (this.f3976p != null) {
            Arrays.fill(this.f3976p, 0);
        }
    }
}
