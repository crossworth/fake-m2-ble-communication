package com.amap.api.mapcore.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.opengl.GLES10;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TextOptions;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IMarkerDelegate;
import com.autonavi.amap.mapcore.interfaces.ITextDelegate;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TextDelegateImp */
class at implements ITextDelegate {
    private static int f5306a = 0;
    private Paint f5307A = new Paint();
    private Handler f5308B = new Handler();
    private Runnable f5309C = new au(this);
    private boolean f5310D = false;
    private boolean f5311E = false;
    private float f5312b = 0.0f;
    private float f5313c = 0.0f;
    private int f5314d = 4;
    private int f5315e = 32;
    private FPoint f5316f = new FPoint();
    private int f5317g;
    private Bitmap f5318h;
    private int f5319i;
    private int f5320j;
    private FloatBuffer f5321k = null;
    private String f5322l;
    private LatLng f5323m;
    private float f5324n = 0.5f;
    private float f5325o = 1.0f;
    private boolean f5326p = true;
    private ae f5327q;
    private FloatBuffer f5328r;
    private Object f5329s;
    private String f5330t;
    private int f5331u;
    private int f5332v;
    private int f5333w;
    private Typeface f5334x;
    private float f5335y;
    private Rect f5336z = new Rect();

    private static String m5676a(String str) {
        f5306a++;
        return str + f5306a;
    }

    public void setRotateAngle(float f) {
        this.f5313c = f;
        this.f5312b = (((-f) % 360.0f) + 360.0f) % 360.0f;
        m5685d();
    }

    public boolean isDestory() {
        return this.f5310D;
    }

    public synchronized void realDestroy() {
        if (this.f5310D) {
            try {
                remove();
                if (this.f5318h != null) {
                    this.f5318h.recycle();
                    this.f5318h = null;
                }
                if (this.f5328r != null) {
                    this.f5328r.clear();
                    this.f5328r = null;
                }
                if (this.f5321k != null) {
                    this.f5321k.clear();
                    this.f5321k = null;
                }
                this.f5323m = null;
                this.f5329s = null;
            } catch (Throwable th) {
                ee.m4243a(th, "TextDelegateImp", "realdestroy");
                th.printStackTrace();
                Log.d("destroy erro", "TextDelegateImp destroy");
            }
        }
    }

    public void destroy() {
        try {
            this.f5310D = true;
            if (!(this.f5327q == null || this.f5327q.f139a == null)) {
                this.f5327q.f139a.callRunDestroy();
            }
            this.f5317g = 0;
        } catch (Throwable th) {
            ee.m4243a(th, "TextDelegateImp", "destroy");
            th.printStackTrace();
            Log.d("destroy erro", "TextDelegateImp destroy");
        }
    }

    public at(TextOptions textOptions, ae aeVar) throws RemoteException {
        this.f5327q = aeVar;
        if (textOptions.getPosition() != null) {
            this.f5323m = textOptions.getPosition();
        }
        setAlign(textOptions.getAlignX(), textOptions.getAlignY());
        this.f5326p = textOptions.isVisible();
        this.f5330t = textOptions.getText();
        this.f5331u = textOptions.getBackgroundColor();
        this.f5332v = textOptions.getFontColor();
        this.f5333w = textOptions.getFontSize();
        this.f5329s = textOptions.getObject();
        this.f5335y = textOptions.getZIndex();
        this.f5334x = textOptions.getTypeface();
        this.f5322l = getId();
        setRotateAngle(textOptions.getRotate());
        m5677a();
        calFPoint();
    }

    private void m5677a() {
        if (this.f5330t != null && this.f5330t.trim().length() > 0) {
            try {
                this.f5307A.setTypeface(this.f5334x);
                this.f5307A.setSubpixelText(true);
                this.f5307A.setAntiAlias(true);
                this.f5307A.setStrokeWidth(5.0f);
                this.f5307A.setStrokeCap(Cap.ROUND);
                this.f5307A.setTextSize((float) this.f5333w);
                this.f5307A.setTextAlign(Align.CENTER);
                this.f5307A.setColor(this.f5332v);
                FontMetrics fontMetrics = this.f5307A.getFontMetrics();
                int i = (int) (fontMetrics.descent - fontMetrics.ascent);
                int i2 = (int) (((((float) i) - fontMetrics.bottom) - fontMetrics.top) / 2.0f);
                this.f5307A.getTextBounds(this.f5330t, 0, this.f5330t.length(), this.f5336z);
                Bitmap createBitmap = Bitmap.createBitmap(this.f5336z.width() + 6, i, Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawColor(this.f5331u);
                canvas.drawText(this.f5330t, (float) (this.f5336z.centerX() + 3), (float) i2, this.f5307A);
                this.f5318h = createBitmap;
                this.f5319i = this.f5318h.getWidth();
                this.f5320j = this.f5318h.getHeight();
                this.f5328r = dj.m575a(new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f});
            } catch (Throwable th) {
                ee.m4243a(th, "TextDelegateImp", "initBitmap");
            }
        }
    }

    private int m5682b() {
        return this.f5319i;
    }

    private int m5684c() {
        return this.f5320j;
    }

    public FPoint anchorUVoff() {
        return null;
    }

    public boolean isContains() {
        return this.f5327q.m150a((IMarkerDelegate) this);
    }

    public IPoint getAnchor() {
        return null;
    }

    public synchronized boolean remove() {
        m5685d();
        this.f5326p = false;
        return this.f5327q.m156c(this);
    }

    private void m5685d() {
        if (this.f5327q.f139a != null) {
            this.f5327q.f139a.setRunLowFrame(false);
        }
    }

    public LatLng getPosition() {
        return this.f5323m;
    }

    public String getId() {
        if (this.f5322l == null) {
            this.f5322l = m5676a("Text");
        }
        return this.f5322l;
    }

    public void setPosition(LatLng latLng) {
        this.f5323m = latLng;
        calFPoint();
        m5685d();
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

    public synchronized void setIcons(ArrayList<BitmapDescriptor> arrayList) {
    }

    public synchronized ArrayList<BitmapDescriptor> getIcons() {
        return null;
    }

    public synchronized void setIcon(BitmapDescriptor bitmapDescriptor) {
    }

    public synchronized BitmapDescriptor getBitmapDescriptor() {
        return null;
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
        if (this.f5326p != z) {
            this.f5326p = z;
            m5685d();
        }
    }

    public boolean isVisible() {
        return this.f5326p;
    }

    public void setZIndex(float f) {
        this.f5335y = f;
        this.f5327q.m165i();
    }

    public float getZIndex() {
        return this.f5335y;
    }

    public void setAnchor(float f, float f2) {
    }

    public float getAnchorU() {
        return this.f5324n;
    }

    public float getAnchorV() {
        return this.f5325o;
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
        if (this.f5323m == null) {
            return false;
        }
        this.f5327q.f139a.getLatLng2Map(this.f5323m.latitude, this.f5323m.longitude, this.f5316f);
        return true;
    }

    private void m5680a(IAMapDelegate iAMapDelegate) throws RemoteException {
        float[] a = dj.m585a(iAMapDelegate, 0, this.f5316f, this.f5312b, m5682b(), m5684c(), this.f5324n, this.f5325o);
        if (this.f5321k == null) {
            this.f5321k = dj.m575a(a);
        } else {
            this.f5321k = dj.m576a(a, this.f5321k);
        }
        if (this.f5317g != 0) {
            m5678a(this.f5317g, this.f5321k, this.f5328r);
        }
    }

    private void m5678a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
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
        if (this.f5326p && !this.f5310D && this.f5323m != null && this.f5318h != null) {
            if (!this.f5311E) {
                try {
                    if (!(this.f5318h == null || this.f5318h.isRecycled())) {
                        if (this.f5317g == 0) {
                            this.f5317g = m5675a(gl10);
                        }
                        dj.m587b(gl10, this.f5317g, this.f5318h, false);
                        this.f5311E = true;
                        this.f5318h.recycle();
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "TextDelegateImp", "loadtexture");
                    th.printStackTrace();
                    return;
                }
            }
            try {
                m5680a(iAMapDelegate);
            } catch (Throwable th2) {
                ee.m4243a(th2, "TextDelegateImp", "drawMarker");
            }
        }
    }

    private int m5675a(GL10 gl10) {
        int texsureId = this.f5327q.f139a.getTexsureId();
        if (texsureId != 0) {
            return texsureId;
        }
        int[] iArr = new int[]{0};
        gl10.glGenTextures(1, iArr, 0);
        return iArr[0];
    }

    public boolean isAllowLow() {
        return true;
    }

    public void setPeriod(int i) {
    }

    public void setObject(Object obj) {
        this.f5329s = obj;
    }

    public Object getObject() {
        return this.f5329s;
    }

    public void setPerspective(boolean z) {
    }

    public boolean isPerspective() {
        return false;
    }

    public int getTextureId() {
        try {
            return this.f5317g;
        } catch (Throwable th) {
            return 0;
        }
    }

    public int getPeriod() {
        return 0;
    }

    public LatLng getRealPosition() {
        return this.f5323m;
    }

    public void set2Top() {
        this.f5327q.m157d(this);
    }

    public void setFlat(boolean z) throws RemoteException {
    }

    public boolean isFlat() {
        return false;
    }

    public float getRotateAngle() {
        return this.f5313c;
    }

    public void setInfoWindowOffset(int i, int i2) throws RemoteException {
    }

    public int getInfoWindowOffsetX() {
        return 0;
    }

    public int getInfoWindowOffsetY() {
        return 0;
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
        return this.f5316f;
    }

    public boolean isViewMode() {
        return false;
    }

    public Rect getRect() {
        return null;
    }

    public void setText(String str) throws RemoteException {
        this.f5330t = str;
        m5686e();
    }

    public String getText() throws RemoteException {
        return this.f5330t;
    }

    public void setBackgroundColor(int i) throws RemoteException {
        this.f5331u = i;
        m5686e();
    }

    public int getBackgroundColor() throws RemoteException {
        return this.f5331u;
    }

    public void setFontColor(int i) throws RemoteException {
        this.f5332v = i;
        m5686e();
    }

    public int getFontColor() throws RemoteException {
        return this.f5332v;
    }

    public void setFontSize(int i) throws RemoteException {
        this.f5333w = i;
        m5686e();
    }

    public int getFontSize() throws RemoteException {
        return this.f5333w;
    }

    public void setTypeface(Typeface typeface) throws RemoteException {
        this.f5334x = typeface;
        m5686e();
    }

    public Typeface getTypeface() throws RemoteException {
        return this.f5334x;
    }

    public void setAlign(int i, int i2) throws RemoteException {
        this.f5314d = i;
        switch (i) {
            case 1:
                this.f5324n = 0.0f;
                break;
            case 2:
                this.f5324n = 1.0f;
                break;
            case 4:
                this.f5324n = 0.5f;
                break;
            default:
                this.f5324n = 0.5f;
                break;
        }
        this.f5315e = i2;
        switch (i2) {
            case 8:
                this.f5325o = 0.0f;
                break;
            case 16:
                this.f5325o = 1.0f;
                break;
            case 32:
                this.f5325o = 0.5f;
                break;
            default:
                this.f5325o = 0.5f;
                break;
        }
        m5685d();
    }

    public int getAlignX() throws RemoteException {
        return this.f5314d;
    }

    public int getAlignY() {
        return this.f5315e;
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    private void m5686e() {
        this.f5308B.removeCallbacks(this.f5309C);
        this.f5308B.post(this.f5309C);
    }

    public boolean checkInBounds() {
        Rect rect = this.f5327q.f139a.getRect();
        if (rect == null) {
            return true;
        }
        IPoint iPoint = new IPoint();
        if (this.f5323m != null) {
            this.f5327q.f139a.getLatLng2Pixel(this.f5323m.latitude, this.f5323m.longitude, iPoint);
        }
        return rect.contains(iPoint.f2030x, iPoint.f2031y);
    }

    public void setInfoWindowShown(boolean z) {
    }

    public void setGeoPoint(IPoint iPoint) {
    }

    public IPoint getGeoPoint() {
        return null;
    }

    public void reLoadTexture() {
        this.f5311E = false;
        this.f5317g = 0;
        m5677a();
    }
}
