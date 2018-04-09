package com.amap.api.mapcore.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IMarkerDelegate;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: PopupOverlay */
class ap implements IMarkerDelegate {
    private boolean f3987a = false;
    private int f3988b = 0;
    private int f3989c = 0;
    private FloatBuffer f3990d = null;
    private String f3991e;
    private FPoint f3992f;
    private BitmapDescriptor f3993g;
    private boolean f3994h = true;
    private FloatBuffer f3995i;
    private Object f3996j;
    private int f3997k;
    private IAMapDelegate f3998l = null;
    private MapProjection f3999m = null;
    private float f4000n = 0.5f;
    private float f4001o = 1.0f;
    private boolean f4002p;
    private boolean f4003q = false;
    private boolean f4004r = true;
    private int f4005s = 20;

    public boolean isDestory() {
        return this.f3987a;
    }

    public void realDestroy() {
        if (this.f3987a) {
            try {
                remove();
                if (this.f3993g != null) {
                    Bitmap bitmap = this.f3993g.getBitmap();
                    if (bitmap != null) {
                        bitmap.recycle();
                        this.f3993g = null;
                    }
                }
                if (this.f3995i != null) {
                    this.f3995i.clear();
                    this.f3995i = null;
                }
                if (this.f3990d != null) {
                    this.f3990d.clear();
                    this.f3990d = null;
                }
                this.f3992f = null;
                this.f3996j = null;
                this.f3997k = 0;
            } catch (Throwable th) {
                ee.m4243a(th, "PopupOverlay", "realDestroy");
                th.printStackTrace();
                Log.d("destroy erro", "MarkerDelegateImp destroy");
            }
        }
    }

    private void m3979a(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor != null) {
            this.f3997k = 0;
            this.f3993g = bitmapDescriptor;
        }
    }

    public ap(MarkerOptions markerOptions, IAMapDelegate iAMapDelegate) {
        this.f3998l = iAMapDelegate;
        this.f3999m = iAMapDelegate.getMapProjection();
        m3979a(markerOptions.getIcon());
        this.f3988b = markerOptions.getInfoWindowOffsetX();
        this.f3989c = markerOptions.getInfoWindowOffsetY();
        this.f3994h = markerOptions.isVisible();
        this.f3991e = getId();
        calFPoint();
    }

    public IPoint m3986b() {
        if (this.f3992f == null) {
            return null;
        }
        IPoint iPoint = new IPoint();
        this.f3998l.getMapProjection().map2Win(this.f3992f.f2028x, this.f3992f.f2029y, iPoint);
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
        if (this.f3993g != null) {
            fPoint.f2028x = ((float) getWidth()) * this.f4000n;
            fPoint.f2029y = ((float) getHeight()) * this.f4001o;
        }
        return fPoint;
    }

    public boolean isContains() {
        return false;
    }

    public IPoint getAnchor() {
        IPoint b = m3986b();
        if (b == null) {
            return null;
        }
        return b;
    }

    public Rect getRect() {
        return null;
    }

    public boolean remove() {
        m3982c();
        if (this.f3997k != 0) {
            this.f3998l.deleteTexsureId(this.f3997k);
        }
        return true;
    }

    private void m3982c() {
        if (this.f3998l != null) {
            this.f3998l.setRunLowFrame(false);
        }
    }

    public LatLng getPosition() {
        return null;
    }

    public String getId() {
        if (this.f3991e == null) {
            this.f3991e = "PopupOverlay";
        }
        return this.f3991e;
    }

    public void m3984a(FPoint fPoint) {
        if (fPoint == null || !fPoint.equals(this.f3992f)) {
            this.f3992f = fPoint;
        }
    }

    public void setPosition(LatLng latLng) {
    }

    public void setTitle(String str) {
    }

    public String getTitle() {
        return null;
    }

    public void setSnippet(String str) {
    }

    public String getSnippet() {
        return null;
    }

    public void setDraggable(boolean z) {
    }

    public void setIcons(ArrayList<BitmapDescriptor> arrayList) {
    }

    public ArrayList<BitmapDescriptor> getIcons() {
        return null;
    }

    public void setIcon(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor != null) {
            this.f3993g = bitmapDescriptor;
            this.f4003q = false;
            if (this.f3995i != null) {
                this.f3995i.clear();
                this.f3995i = null;
            }
            m3982c();
        }
    }

    public BitmapDescriptor getBitmapDescriptor() {
        return this.f3993g;
    }

    public boolean isDraggable() {
        return false;
    }

    public void showInfoWindow() {
    }

    public void hideInfoWindow() {
    }

    public boolean isInfoWindowShown() {
        return false;
    }

    public void setVisible(boolean z) {
        if (!this.f3994h && z) {
            this.f4002p = true;
        }
        this.f3994h = z;
    }

    public boolean isVisible() {
        return this.f3994h;
    }

    public void setAnchor(float f, float f2) {
        if (this.f4000n != f || this.f4001o != f2) {
            this.f4000n = f;
            this.f4001o = f2;
        }
    }

    public float getAnchorU() {
        return this.f4000n;
    }

    public float getAnchorV() {
        return this.f4001o;
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
        if (this.f3992f == null) {
            return false;
        }
        IPoint iPoint = new IPoint();
        this.f3998l.getMapProjection().map2Win(this.f3992f.f2028x, this.f3992f.f2029y, iPoint);
        int width = getWidth();
        int height = getHeight();
        int i = (int) (((float) (iPoint.f2030x + this.f3988b)) - (((float) width) * this.f4000n));
        int i2 = (int) (((float) (iPoint.f2031y + this.f3989c)) + (((float) height) * (1.0f - this.f4001o)));
        if (i - width > this.f3998l.getMapWidth() || i < (-width) * 2 || i2 < (-height) * 2 || i2 - height > this.f3998l.getMapHeight() || this.f3993g == null) {
            return false;
        }
        width = this.f3993g.getWidth();
        float width2 = ((float) width) / ((float) this.f3993g.getBitmap().getWidth());
        float height2 = ((float) this.f3993g.getHeight()) / ((float) this.f3993g.getBitmap().getHeight());
        if (this.f3995i == null) {
            this.f3995i = dj.m575a(new float[]{0.0f, height2, width2, height2, width2, 0.0f, 0.0f, 0.0f});
        }
        float[] fArr = new float[]{(float) i, (float) (this.f3998l.getMapHeight() - i2), 0.0f, (float) (i + width), (float) (this.f3998l.getMapHeight() - i2), 0.0f, (float) (width + i), (float) ((this.f3998l.getMapHeight() - i2) + height), 0.0f, (float) i, (float) ((this.f3998l.getMapHeight() - i2) + height), 0.0f};
        if (this.f3990d == null) {
            this.f3990d = dj.m575a(fArr);
        } else {
            this.f3990d = dj.m576a(fArr, this.f3990d);
        }
        return true;
    }

    private void m3980a(GL10 gl10, int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (floatBuffer != null && floatBuffer2 != null) {
            gl10.glEnable(3042);
            gl10.glBlendFunc(1, 771);
            gl10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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

    public void m3985a(GL10 gl10) {
        if (this.f3994h && this.f3992f != null && getBitmapDescriptor() != null) {
            if (!this.f4003q) {
                try {
                    if (this.f3997k != 0) {
                        gl10.glDeleteTextures(1, new int[]{this.f3997k}, 0);
                        this.f3998l.deleteTexsureId(this.f3997k);
                    }
                    this.f3997k = m3981b(gl10);
                    if (this.f3993g != null) {
                        Bitmap bitmap = this.f3993g.getBitmap();
                        if (!(bitmap == null || bitmap.isRecycled())) {
                            dj.m587b(gl10, this.f3997k, bitmap, false);
                        }
                        this.f4003q = true;
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "PopupOverlay", "drawMarker");
                    th.printStackTrace();
                    return;
                }
            }
            if (calFPoint()) {
                gl10.glLoadIdentity();
                gl10.glViewport(0, 0, this.f3998l.getMapWidth(), this.f3998l.getMapHeight());
                gl10.glMatrixMode(5889);
                gl10.glLoadIdentity();
                gl10.glOrthof(0.0f, (float) this.f3998l.getMapWidth(), 0.0f, (float) this.f3998l.getMapHeight(), 1.0f, GroundOverlayOptions.NO_DIMENSION);
                m3980a(gl10, this.f3997k, this.f3990d, this.f3995i);
                if (this.f4002p) {
                    mo3005a();
                    this.f4002p = false;
                }
            }
        }
    }

    public void mo3005a() {
    }

    private int m3981b(GL10 gl10) {
        int texsureId = this.f3998l.getTexsureId();
        if (texsureId != 0) {
            return texsureId;
        }
        int[] iArr = new int[]{0};
        gl10.glGenTextures(1, iArr, 0);
        return iArr[0];
    }

    public boolean isAllowLow() {
        return this.f4004r;
    }

    public void setPeriod(int i) {
        if (i <= 1) {
            this.f4005s = 1;
        } else {
            this.f4005s = i;
        }
    }

    public void setObject(Object obj) {
        this.f3996j = obj;
    }

    public Object getObject() {
        return this.f3996j;
    }

    public void setPerspective(boolean z) {
    }

    public boolean isPerspective() {
        return false;
    }

    public int getTextureId() {
        return this.f3997k;
    }

    public int getPeriod() {
        return this.f4005s;
    }

    public LatLng getRealPosition() {
        return null;
    }

    public void set2Top() {
    }

    public void setFlat(boolean z) throws RemoteException {
        m3982c();
    }

    public boolean isFlat() {
        return false;
    }

    public void setRotateAngle(float f) throws RemoteException {
    }

    public void destroy() {
    }

    public void drawMarker(GL10 gl10, IAMapDelegate iAMapDelegate) {
    }

    public float getRotateAngle() {
        return 0.0f;
    }

    public void setInfoWindowOffset(int i, int i2) throws RemoteException {
        this.f3988b = i;
        this.f3989c = i2;
    }

    public int getInfoWindowOffsetX() {
        return this.f3988b;
    }

    public int getInfoWindowOffsetY() {
        return this.f3989c;
    }

    public void setPositionByPixels(int i, int i2) {
    }

    public int getRealInfoWindowOffsetX() {
        return 0;
    }

    public int getRealInfoWindowOffsetY() {
        return 0;
    }

    public FPoint getMapPosition() {
        return this.f3992f;
    }

    public boolean isViewMode() {
        return false;
    }

    public void setZIndex(float f) {
    }

    public float getZIndex() {
        return 0.0f;
    }

    public boolean checkInBounds() {
        return false;
    }

    public void setInfoWindowShown(boolean z) {
    }

    public void setGeoPoint(IPoint iPoint) {
    }

    public IPoint getGeoPoint() {
        return null;
    }

    public void reLoadTexture() {
        this.f4003q = false;
        this.f3997k = 0;
    }
}
