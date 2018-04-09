package com.amap.api.mapcore.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IGroundOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: GroundOverlayDelegateImp */
public class C1970x implements IGroundOverlayDelegate {
    private IAMapDelegate f5392a;
    private BitmapDescriptor f5393b;
    private LatLng f5394c;
    private float f5395d;
    private float f5396e;
    private LatLngBounds f5397f;
    private float f5398g;
    private float f5399h;
    private boolean f5400i = true;
    private float f5401j = 0.0f;
    private float f5402k = 0.5f;
    private float f5403l = 0.5f;
    private String f5404m;
    private FloatBuffer f5405n = null;
    private FloatBuffer f5406o;
    private int f5407p;
    private boolean f5408q = false;
    private boolean f5409r = false;

    public C1970x(IAMapDelegate iAMapDelegate) {
        this.f5392a = iAMapDelegate;
        try {
            this.f5404m = getId();
        } catch (Throwable e) {
            ee.m4243a(e, "GroundOverlayDelegateImp", "create");
            e.printStackTrace();
        }
    }

    public void remove() throws RemoteException {
        this.f5392a.deleteTexsureId(this.f5407p);
        this.f5392a.removeGLOverlay(getId());
        this.f5392a.setRunLowFrame(false);
    }

    public String getId() throws RemoteException {
        if (this.f5404m == null) {
            this.f5404m = C0286v.m1022a("GroundOverlay");
        }
        return this.f5404m;
    }

    public void setZIndex(float f) throws RemoteException {
        this.f5399h = f;
        this.f5392a.changeGLOverlayIndex();
        this.f5392a.setRunLowFrame(false);
    }

    public float getZIndex() throws RemoteException {
        return this.f5399h;
    }

    public void setVisible(boolean z) throws RemoteException {
        this.f5400i = z;
        this.f5392a.setRunLowFrame(false);
    }

    public boolean isVisible() throws RemoteException {
        return this.f5400i;
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

    public void calMapFPoint() throws RemoteException {
        this.f5409r = false;
        if (this.f5394c == null) {
            m5774b();
        } else if (this.f5397f == null) {
            m5771a();
        } else {
            m5775c();
        }
    }

    private void m5771a() {
        if (this.f5394c != null) {
            double cos = ((double) this.f5395d) / ((6371000.79d * Math.cos(this.f5394c.latitude * 0.01745329251994329d)) * 0.01745329251994329d);
            double d = ((double) this.f5396e) / 111194.94043265979d;
            this.f5397f = new LatLngBounds(new LatLng(this.f5394c.latitude - (((double) (1.0f - this.f5403l)) * d), this.f5394c.longitude - (((double) this.f5402k) * cos)), new LatLng((d * ((double) this.f5403l)) + this.f5394c.latitude, (cos * ((double) (1.0f - this.f5402k))) + this.f5394c.longitude));
            m5775c();
        }
    }

    private void m5774b() {
        if (this.f5397f != null) {
            LatLng latLng = this.f5397f.southwest;
            LatLng latLng2 = this.f5397f.northeast;
            this.f5394c = new LatLng(latLng.latitude + (((double) (1.0f - this.f5403l)) * (latLng2.latitude - latLng.latitude)), latLng.longitude + (((double) this.f5402k) * (latLng2.longitude - latLng.longitude)));
            this.f5395d = (float) (((6371000.79d * Math.cos(this.f5394c.latitude * 0.01745329251994329d)) * (latLng2.longitude - latLng.longitude)) * 0.01745329251994329d);
            this.f5396e = (float) (((latLng2.latitude - latLng.latitude) * 6371000.79d) * 0.01745329251994329d);
            m5775c();
        }
    }

    private void m5775c() {
        if (this.f5397f != null) {
            float[] fArr = new float[12];
            FPoint fPoint = new FPoint();
            FPoint fPoint2 = new FPoint();
            FPoint fPoint3 = new FPoint();
            FPoint fPoint4 = new FPoint();
            this.f5392a.getLatLng2Map(this.f5397f.southwest.latitude, this.f5397f.southwest.longitude, fPoint);
            this.f5392a.getLatLng2Map(this.f5397f.southwest.latitude, this.f5397f.northeast.longitude, fPoint2);
            this.f5392a.getLatLng2Map(this.f5397f.northeast.latitude, this.f5397f.northeast.longitude, fPoint3);
            this.f5392a.getLatLng2Map(this.f5397f.northeast.latitude, this.f5397f.southwest.longitude, fPoint4);
            if (this.f5398g != 0.0f) {
                double d = (double) (fPoint2.f2028x - fPoint.f2028x);
                double d2 = (double) (fPoint2.f2029y - fPoint3.f2029y);
                DPoint dPoint = new DPoint();
                dPoint.f2026x = ((double) fPoint.f2028x) + (((double) this.f5402k) * d);
                dPoint.f2027y = ((double) fPoint.f2029y) - (((double) (1.0f - this.f5403l)) * d2);
                m5772a(dPoint, 0.0d, 0.0d, d, d2, fPoint);
                m5772a(dPoint, d, 0.0d, d, d2, fPoint2);
                m5772a(dPoint, d, d2, d, d2, fPoint3);
                m5772a(dPoint, 0.0d, d2, d, d2, fPoint4);
            }
            fArr[0] = fPoint.f2028x;
            fArr[1] = fPoint.f2029y;
            fArr[2] = 0.0f;
            fArr[3] = fPoint2.f2028x;
            fArr[4] = fPoint2.f2029y;
            fArr[5] = 0.0f;
            fArr[6] = fPoint3.f2028x;
            fArr[7] = fPoint3.f2029y;
            fArr[8] = 0.0f;
            fArr[9] = fPoint4.f2028x;
            fArr[10] = fPoint4.f2029y;
            fArr[11] = 0.0f;
            if (this.f5405n == null) {
                this.f5405n = dj.m575a(fArr);
            } else {
                this.f5405n = dj.m576a(fArr, this.f5405n);
            }
        }
    }

    private void m5772a(DPoint dPoint, double d, double d2, double d3, double d4, FPoint fPoint) {
        double d5 = d - (((double) this.f5402k) * d3);
        double d6 = (((double) (1.0f - this.f5403l)) * d4) - d2;
        double d7 = ((double) (-this.f5398g)) * 0.01745329251994329d;
        fPoint.f2028x = (float) (dPoint.f2026x + ((Math.cos(d7) * d5) + (Math.sin(d7) * d6)));
        fPoint.f2029y = (float) (((d6 * Math.cos(d7)) - (d5 * Math.sin(d7))) + dPoint.f2027y);
    }

    private void m5776d() {
        if (this.f5393b != null) {
            int width = this.f5393b.getWidth();
            float width2 = ((float) width) / ((float) this.f5393b.getBitmap().getWidth());
            float height = ((float) this.f5393b.getHeight()) / ((float) this.f5393b.getBitmap().getHeight());
            this.f5406o = dj.m575a(new float[]{0.0f, height, width2, height, width2, 0.0f, 0.0f, 0.0f});
        }
    }

    public void draw(GL10 gl10) throws RemoteException {
        if (!this.f5400i) {
            return;
        }
        if ((this.f5394c != null || this.f5397f != null) && this.f5393b != null) {
            if (!this.f5408q) {
                Bitmap bitmap = this.f5393b.getBitmap();
                if (!(bitmap == null || bitmap.isRecycled())) {
                    if (this.f5407p == 0) {
                        this.f5407p = this.f5392a.getTexsureId();
                        if (this.f5407p == 0) {
                            int[] iArr = new int[]{0};
                            gl10.glGenTextures(1, iArr, 0);
                            this.f5407p = iArr[0];
                        }
                    } else {
                        gl10.glDeleteTextures(1, new int[]{this.f5407p}, 0);
                    }
                    dj.m587b(gl10, this.f5407p, bitmap, true);
                }
                this.f5408q = true;
            }
            if (this.f5395d != 0.0f || this.f5396e != 0.0f) {
                m5773a(gl10, this.f5407p, this.f5405n, this.f5406o);
                this.f5409r = true;
            }
        }
    }

    private void m5773a(GL10 gl10, int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (floatBuffer != null && floatBuffer2 != null) {
            gl10.glEnable(3042);
            gl10.glTexEnvf(8960, 8704, 8448.0f);
            gl10.glBlendFunc(1, 771);
            gl10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - this.f5401j);
            gl10.glEnable(3553);
            gl10.glEnableClientState(32884);
            gl10.glEnableClientState(32888);
            gl10.glBindTexture(3553, i);
            gl10.glVertexPointer(3, 5126, 0, floatBuffer);
            gl10.glTexCoordPointer(2, 5126, 0, floatBuffer2);
            gl10.glDrawArrays(6, 0, 4);
            gl10.glDisableClientState(32884);
            gl10.glDisableClientState(32888);
            gl10.glDisable(3553);
            gl10.glDisable(3042);
        }
    }

    public void destroy() {
        try {
            remove();
            if (this.f5393b != null) {
                Bitmap bitmap = this.f5393b.getBitmap();
                if (bitmap != null) {
                    bitmap.recycle();
                    this.f5393b = null;
                }
            }
            if (this.f5406o != null) {
                this.f5406o.clear();
                this.f5406o = null;
            }
            if (this.f5405n != null) {
                this.f5405n.clear();
                this.f5405n = null;
            }
            this.f5394c = null;
            this.f5397f = null;
        } catch (Throwable th) {
            ee.m4243a(th, "GroundOverlayDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "GroundOverlayDelegateImp destroy");
        }
    }

    public boolean checkInBounds() {
        Rect rect = this.f5392a.getRect();
        if (rect == null) {
            return true;
        }
        IPoint iPoint = new IPoint();
        if (this.f5394c != null) {
            this.f5392a.getLatLng2Pixel(this.f5394c.latitude, this.f5394c.longitude, iPoint);
        }
        return rect.contains(iPoint.f2030x, iPoint.f2031y);
    }

    public void setPosition(LatLng latLng) throws RemoteException {
        this.f5394c = latLng;
        m5771a();
        this.f5392a.setRunLowFrame(false);
    }

    public LatLng getPosition() throws RemoteException {
        return this.f5394c;
    }

    public void setDimensions(float f) throws RemoteException {
        cu.m452b(f >= 0.0f, "Width must be non-negative");
        if (!this.f5408q || this.f5395d == f) {
            this.f5395d = f;
            this.f5396e = f;
        } else {
            this.f5395d = f;
            this.f5396e = f;
            m5771a();
        }
        this.f5392a.setRunLowFrame(false);
    }

    public void setDimensions(float f, float f2) throws RemoteException {
        boolean z = true;
        cu.m452b(f >= 0.0f, "Width must be non-negative");
        if (f2 < 0.0f) {
            z = false;
        }
        cu.m452b(z, "Height must be non-negative");
        if (!this.f5408q || this.f5395d == f || this.f5396e == f2) {
            this.f5395d = f;
            this.f5396e = f2;
        } else {
            this.f5395d = f;
            this.f5396e = f2;
            m5771a();
        }
        this.f5392a.setRunLowFrame(false);
    }

    public float getWidth() throws RemoteException {
        return this.f5395d;
    }

    public float getHeight() throws RemoteException {
        return this.f5396e;
    }

    public void setPositionFromBounds(LatLngBounds latLngBounds) throws RemoteException {
        this.f5397f = latLngBounds;
        m5774b();
        this.f5392a.setRunLowFrame(false);
    }

    public LatLngBounds getBounds() throws RemoteException {
        return this.f5397f;
    }

    public void setBearing(float f) throws RemoteException {
        float f2 = ((f % 360.0f) + 360.0f) % 360.0f;
        if (!this.f5408q || ((double) Math.abs(this.f5398g - f2)) <= 1.0E-7d) {
            this.f5398g = f2;
        } else {
            this.f5398g = f2;
            m5775c();
        }
        this.f5392a.setRunLowFrame(false);
    }

    public float getBearing() throws RemoteException {
        return this.f5398g;
    }

    public void setTransparency(float f) throws RemoteException {
        boolean z = f >= 0.0f && f <= 1.0f;
        cu.m452b(z, "Transparency must be in the range [0..1]");
        this.f5401j = f;
        this.f5392a.setRunLowFrame(false);
    }

    public float getTransparency() throws RemoteException {
        return this.f5401j;
    }

    public void setImage(BitmapDescriptor bitmapDescriptor) throws RemoteException {
        this.f5393b = bitmapDescriptor;
        m5776d();
        if (this.f5408q) {
            this.f5408q = false;
        }
        this.f5392a.setRunLowFrame(false);
    }

    public void setAnchor(float f, float f2) throws RemoteException {
        this.f5402k = f;
        this.f5403l = f2;
        this.f5392a.setRunLowFrame(false);
    }

    public void reLoadTexture() {
        this.f5408q = false;
        this.f5407p = 0;
    }

    public boolean isDrawFinish() {
        return this.f5409r;
    }
}
