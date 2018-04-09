package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.MapRenderer.C0627a;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import lecho.lib.hellocharts.model.PieChartData;
import org.andengine.util.level.constants.LevelConstants;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class C0638i extends GLSurfaceView implements OnDoubleTapListener, OnGestureListener, C0627a {
    private static final String f2090a = C0638i.class.getSimpleName();
    private Handler f2091b;
    private MapRenderer f2092c;
    private int f2093d;
    private int f2094e;
    private GestureDetector f2095f;
    private C0633e f2096g;

    static class C0637a {
        float f2082a;
        float f2083b;
        float f2084c;
        float f2085d;
        boolean f2086e;
        float f2087f;
        float f2088g;
        double f2089h;

        C0637a() {
        }
    }

    public C0638i(Context context, C0612B c0612b, String str) {
        super(context);
        if (context == null) {
            throw new RuntimeException("when you create an mapview, the context can not be null");
        }
        this.f2095f = new GestureDetector(context, this);
        EnvironmentUtilities.initAppDirectory(context);
        if (this.f2096g == null) {
            this.f2096g = new C0633e(context, str);
        }
        this.f2096g.m1967a();
        m2044f();
        this.f2096g.m1989b();
        this.f2096g.m1973a(c0612b);
        m2045g();
        this.f2096g.m1971a(this.f2091b);
        this.f2096g.m2004e();
        setBackgroundColor(0);
    }

    public static void m2040a(boolean z) {
        C0633e.m1949j(z);
    }

    private static boolean m2041a(int i, int i2, int i3, int i4, int i5, int i6) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        return egl10.eglChooseConfig(eglGetDisplay, new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344}, new EGLConfig[100], 100, iArr) && iArr[0] > 0;
    }

    private void m2044f() {
        try {
            if (C0638i.m2041a(5, 6, 5, 0, 24, 0)) {
                setEGLConfigChooser(5, 6, 5, 0, 24, 0);
            } else {
                setEGLConfigChooser(true);
            }
        } catch (IllegalArgumentException e) {
            setEGLConfigChooser(true);
        }
        this.f2092c = new MapRenderer(this, this);
        this.f2092c.m1930a(this.f2096g.f2054h);
        setRenderer(this.f2092c);
        setRenderMode(1);
    }

    private void m2045g() {
        this.f2091b = new C0639j(this);
    }

    public C0633e m2046a() {
        return this.f2096g;
    }

    public void m2047a(int i) {
        if (this.f2096g != null) {
            Message message = new Message();
            message.what = 50;
            message.obj = Long.valueOf(this.f2096g.f2054h);
            boolean p = this.f2096g.m2026p();
            if (i == 3) {
                message.arg1 = 0;
            } else if (p) {
                message.arg1 = 1;
            }
            this.f2091b.sendMessage(message);
        }
    }

    public void m2048a(String str, Rect rect) {
        if (this.f2096g != null && this.f2096g.f2053g != null) {
            if (rect != null) {
                int i = rect.left;
                int i2 = this.f2094e < rect.bottom ? 0 : this.f2094e - rect.bottom;
                int width = rect.width();
                int height = rect.height();
                if (i >= 0 && i2 >= 0 && width > 0 && height > 0) {
                    if (width > this.f2093d) {
                        width = Math.abs(rect.width()) - (rect.right - this.f2093d);
                    }
                    if (height > this.f2094e) {
                        height = Math.abs(rect.height()) - (rect.bottom - this.f2094e);
                    }
                    if (i > SysOSUtil.getScreenSizeX() || i2 > SysOSUtil.getScreenSizeY()) {
                        this.f2096g.f2053g.m2202a(str, null);
                        requestRender();
                        return;
                    }
                    this.f2093d = width;
                    this.f2094e = height;
                    Bundle bundle = new Bundle();
                    bundle.putInt("x", i);
                    bundle.putInt("y", i2);
                    bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH, width);
                    bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT, height);
                    this.f2096g.f2053g.m2202a(str, bundle);
                    requestRender();
                    return;
                }
                return;
            }
            this.f2096g.f2053g.m2202a(str, null);
            requestRender();
        }
    }

    public void m2049b() {
        if (this.f2096g != null) {
            for (C0482k f : this.f2096g.f2052f) {
                f.mo1789f();
            }
            this.f2096g.m1991b(this.f2091b);
            this.f2096g.m1962M();
            this.f2096g = null;
        }
    }

    public void m2050c() {
        if (this.f2096g != null) {
            this.f2096g.m2032t();
        }
    }

    public void m2051d() {
        if (this.f2096g != null) {
            this.f2096g.m2033u();
        }
    }

    public void mo1834e() {
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.f2096g == null || this.f2096g.f2053g == null || !this.f2096g.f2055i) {
            return true;
        }
        GeoPoint b = this.f2096g.m1988b((int) motionEvent.getX(), (int) motionEvent.getY());
        if (b == null) {
            return false;
        }
        for (C0482k b2 : this.f2096g.f2052f) {
            b2.mo1779b(b);
        }
        if (!this.f2096g.f2051e) {
            return false;
        }
        C0616D D = this.f2096g.m1953D();
        D.f1963a += 1.0f;
        D.f1966d = b.getLongitudeE6();
        D.f1967e = b.getLatitudeE6();
        this.f2096g.m1975a(D, 300);
        C0633e c0633e = this.f2096g;
        C0633e.f2021k = System.currentTimeMillis();
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.f2096g == null || this.f2096g.f2053g == null || !this.f2096g.f2055i) {
            return true;
        }
        if (!this.f2096g.f2050d) {
            return false;
        }
        float sqrt = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        if (sqrt <= 500.0f) {
            return false;
        }
        this.f2096g.m2038z();
        this.f2096g.m1964a(34, (int) (sqrt * PieChartData.DEFAULT_CENTER_CIRCLE_SCALE), (((int) motionEvent2.getY()) << 16) | ((int) motionEvent2.getX()));
        this.f2096g.m1961L();
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.f2096g != null && this.f2096g.f2053g != null && this.f2096g.f2055i) {
            String a = this.f2096g.f2053g.m2198a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.f2096g.f2056j);
            if (a == null || a.equals("")) {
                for (C0482k c : this.f2096g.f2052f) {
                    c.mo1783c(this.f2096g.m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return;
            }
            for (C0482k c2 : this.f2096g.f2052f) {
                if (c2.mo1781b(a)) {
                    this.f2096g.f2059n = true;
                } else {
                    c2.mo1783c(this.f2096g.m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (this.f2096g != null && this.f2096g.f2053g != null) {
            this.f2096g.f2053g.m2230e();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.f2096g != null && this.f2096g.f2053g != null) {
            for (C0482k d : this.f2096g.f2052f) {
                d.mo1785d();
            }
            this.f2096g.f2053g.m2239i();
            this.f2096g.f2053g.m2233f();
            this.f2096g.f2053g.m2246p();
            setRenderMode(1);
        }
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        JSONObject jSONObject;
        JSONException e;
        if (!(this.f2096g == null || this.f2096g.f2053g == null || !this.f2096g.f2055i)) {
            String a = this.f2096g.f2053g.m2198a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.f2096g.f2056j);
            if (a == null || a.equals("")) {
                for (C0482k a2 : this.f2096g.f2052f) {
                    a2.mo1773a(this.f2096g.m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
            } else {
                JSONObject jSONObject2;
                try {
                    jSONObject = new JSONObject(a);
                    try {
                        jSONObject.put("px", (int) motionEvent.getX());
                        jSONObject.put("py", (int) motionEvent.getY());
                        jSONObject2 = jSONObject;
                    } catch (JSONException e2) {
                        e = e2;
                        e.printStackTrace();
                        jSONObject2 = jSONObject;
                        for (C0482k a22 : this.f2096g.f2052f) {
                            if (jSONObject2 == null) {
                                a22.mo1775a(jSONObject2.toString());
                            }
                        }
                        return true;
                    }
                } catch (JSONException e3) {
                    JSONException jSONException = e3;
                    jSONObject = null;
                    e = jSONException;
                    e.printStackTrace();
                    jSONObject2 = jSONObject;
                    for (C0482k a222 : this.f2096g.f2052f) {
                        if (jSONObject2 == null) {
                            a222.mo1775a(jSONObject2.toString());
                        }
                    }
                    return true;
                }
                for (C0482k a2222 : this.f2096g.f2052f) {
                    if (jSONObject2 == null) {
                        a2222.mo1775a(jSONObject2.toString());
                    }
                }
            }
        }
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.f2096g == null || this.f2096g.f2053g == null) {
            return true;
        }
        super.onTouchEvent(motionEvent);
        for (C0482k a : this.f2096g.f2052f) {
            a.mo1772a(motionEvent);
        }
        return this.f2095f.onTouchEvent(motionEvent) ? true : this.f2096g.m1986a(motionEvent);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        super.surfaceChanged(surfaceHolder, i, i2, i3);
        if (this.f2096g != null && this.f2096g.f2053g != null) {
            this.f2092c.f1999a = i2;
            this.f2092c.f2000b = i3;
            this.f2093d = i2;
            this.f2094e = i3;
            this.f2092c.f2001c = 0;
            C0616D D = this.f2096g.m1953D();
            if (D.f1968f == 0 || D.f1968f == -1 || D.f1968f == (D.f1972j.f1957a - D.f1972j.f1958b) / 2) {
                D.f1968f = -1;
            }
            if (D.f1969g == 0 || D.f1969g == -1 || D.f1969g == (D.f1972j.f1960d - D.f1972j.f1959c) / 2) {
                D.f1969g = -1;
            }
            D.f1972j.f1957a = 0;
            D.f1972j.f1959c = 0;
            D.f1972j.f1960d = i3;
            D.f1972j.f1958b = i2;
            this.f2096g.m1974a(D);
            this.f2096g.m1969a(this.f2093d, this.f2094e);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);
        if (surfaceHolder != null && !surfaceHolder.getSurface().isValid()) {
            surfaceDestroyed(surfaceHolder);
        }
    }
}
