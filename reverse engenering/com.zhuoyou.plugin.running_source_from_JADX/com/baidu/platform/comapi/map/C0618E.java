package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0640l.C0617a;
import java.util.concurrent.atomic.AtomicBoolean;
import lecho.lib.hellocharts.model.PieChartData;
import org.andengine.util.level.constants.LevelConstants;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class C0618E extends TextureView implements OnDoubleTapListener, OnGestureListener, SurfaceTextureListener, C0617a {
    public static int f1982a;
    public static int f1983b;
    private GestureDetector f1984c;
    private Handler f1985d;
    private C0640l f1986e = null;
    private C0633e f1987f;

    public C0618E(Context context, C0612B c0612b, String str) {
        super(context);
        m1909a(context, c0612b, str);
    }

    private void m1909a(Context context, C0612B c0612b, String str) {
        setSurfaceTextureListener(this);
        if (context == null) {
            throw new RuntimeException("when you create an mapview, the context can not be null");
        }
        this.f1984c = new GestureDetector(context, this);
        EnvironmentUtilities.initAppDirectory(context);
        if (this.f1987f == null) {
            this.f1987f = new C0633e(context, str);
        }
        this.f1987f.m1967a();
        this.f1987f.m1989b();
        this.f1987f.m1973a(c0612b);
        m1912f();
        this.f1987f.m1971a(this.f1985d);
        this.f1987f.m2004e();
    }

    public static void m1910a(boolean z) {
        C0633e.m1949j(z);
    }

    private void m1912f() {
        this.f1985d = new C0619F(this);
    }

    public int mo1831a() {
        return this.f1987f == null ? 0 : MapRenderer.nativeRender(this.f1987f.f2054h);
    }

    public void m1914a(String str, Rect rect) {
        if (this.f1987f != null && this.f1987f.f2053g != null) {
            if (rect != null) {
                int i = rect.left;
                int i2 = f1983b < rect.bottom ? 0 : f1983b - rect.bottom;
                int width = rect.width();
                int height = rect.height();
                if (i >= 0 && i2 >= 0 && width > 0 && height > 0) {
                    if (width > f1982a) {
                        width = Math.abs(rect.width()) - (rect.right - f1982a);
                    }
                    if (height > f1983b) {
                        height = Math.abs(rect.height()) - (rect.bottom - f1983b);
                    }
                    if (i > SysOSUtil.getScreenSizeX() || i2 > SysOSUtil.getScreenSizeY()) {
                        this.f1987f.f2053g.m2202a(str, null);
                        if (this.f1986e != null) {
                            this.f1986e.m2057a();
                            return;
                        }
                        return;
                    }
                    f1982a = width;
                    f1983b = height;
                    Bundle bundle = new Bundle();
                    bundle.putInt("x", i);
                    bundle.putInt("y", i2);
                    bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH, width);
                    bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT, height);
                    this.f1987f.f2053g.m2202a(str, bundle);
                    if (this.f1986e != null) {
                        this.f1986e.m2057a();
                        return;
                    }
                    return;
                }
                return;
            }
            this.f1987f.f2053g.m2202a(str, null);
            if (this.f1986e != null) {
                this.f1986e.m2057a();
            }
        }
    }

    public C0633e m1915b() {
        return this.f1987f;
    }

    public void m1916c() {
        if (this.f1987f != null && this.f1987f.f2053g != null) {
            for (C0482k d : this.f1987f.f2052f) {
                d.mo1785d();
            }
            this.f1987f.f2053g.m2239i();
            this.f1987f.f2053g.m2233f();
            this.f1987f.f2053g.m2246p();
            if (this.f1986e != null) {
                this.f1986e.m2057a();
            }
        }
    }

    public void m1917d() {
        if (this.f1987f != null && this.f1987f.f2053g != null) {
            this.f1987f.f2053g.m2230e();
            synchronized (this.f1987f) {
                this.f1987f.f2053g.m2230e();
                if (this.f1986e != null) {
                    this.f1986e.m2058b();
                }
            }
        }
    }

    public void m1918e() {
        synchronized (this.f1987f) {
            for (C0482k f : this.f1987f.f2052f) {
                f.mo1789f();
            }
            if (this.f1987f != null) {
                this.f1987f.m1991b(this.f1985d);
                this.f1987f.m1962M();
                this.f1987f = null;
            }
            this.f1985d.removeCallbacksAndMessages(null);
        }
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.f1987f == null || this.f1987f.f2053g == null || !this.f1987f.f2055i) {
            return true;
        }
        GeoPoint b = this.f1987f.m1988b((int) motionEvent.getX(), (int) motionEvent.getY());
        if (b == null) {
            return false;
        }
        for (C0482k b2 : this.f1987f.f2052f) {
            b2.mo1779b(b);
        }
        if (!this.f1987f.f2051e) {
            return false;
        }
        C0616D D = this.f1987f.m1953D();
        D.f1963a += 1.0f;
        D.f1966d = b.getLongitudeE6();
        D.f1967e = b.getLatitudeE6();
        this.f1987f.m1975a(D, 300);
        C0633e c0633e = this.f1987f;
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
        if (this.f1987f == null || this.f1987f.f2053g == null || !this.f1987f.f2055i) {
            return true;
        }
        if (!this.f1987f.f2050d) {
            return false;
        }
        float sqrt = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        if (sqrt <= 500.0f) {
            return false;
        }
        this.f1987f.m2038z();
        this.f1987f.m1964a(34, (int) (sqrt * PieChartData.DEFAULT_CENTER_CIRCLE_SCALE), (((int) motionEvent2.getY()) << 16) | ((int) motionEvent2.getX()));
        this.f1987f.m1961L();
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.f1987f != null && this.f1987f.f2053g != null && this.f1987f.f2055i) {
            String a = this.f1987f.f2053g.m2198a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.f1987f.f2056j);
            if (a == null || a.equals("")) {
                for (C0482k c : this.f1987f.f2052f) {
                    c.mo1783c(this.f1987f.m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return;
            }
            for (C0482k c2 : this.f1987f.f2052f) {
                if (c2.mo1781b(a)) {
                    this.f1987f.f2059n = true;
                } else {
                    c2.mo1783c(this.f1987f.m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
            }
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
        if (!(this.f1987f == null || this.f1987f.f2053g == null || !this.f1987f.f2055i)) {
            String a = this.f1987f.f2053g.m2198a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.f1987f.f2056j);
            if (a == null || a.equals("")) {
                for (C0482k a2 : this.f1987f.f2052f) {
                    a2.mo1773a(this.f1987f.m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
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
                        for (C0482k a22 : this.f1987f.f2052f) {
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
                    for (C0482k a222 : this.f1987f.f2052f) {
                        if (jSONObject2 == null) {
                            a222.mo1775a(jSONObject2.toString());
                        }
                    }
                    return true;
                }
                for (C0482k a2222 : this.f1987f.f2052f) {
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

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.f1987f != null) {
            this.f1986e = new C0640l(surfaceTexture, this, new AtomicBoolean(true), this);
            this.f1986e.start();
            f1982a = i;
            f1983b = i2;
            C0616D D = this.f1987f.m1953D();
            if (D != null) {
                if (D.f1968f == 0 || D.f1968f == -1 || D.f1968f == (D.f1972j.f1957a - D.f1972j.f1958b) / 2) {
                    D.f1968f = -1;
                }
                if (D.f1969g == 0 || D.f1969g == -1 || D.f1969g == (D.f1972j.f1960d - D.f1972j.f1959c) / 2) {
                    D.f1969g = -1;
                }
                D.f1972j.f1957a = 0;
                D.f1972j.f1959c = 0;
                D.f1972j.f1960d = i2;
                D.f1972j.f1958b = i;
                this.f1987f.m1974a(D);
                this.f1987f.m1969a(f1982a, f1983b);
            }
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.f1986e != null) {
            this.f1986e.m2059c();
            this.f1986e = null;
        }
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.f1987f != null) {
            f1982a = i;
            f1983b = i2;
            this.f1987f.m1969a(f1982a, f1983b);
            MapRenderer.nativeResize(this.f1987f.f2054h, i, i2);
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.f1987f == null || this.f1987f.f2053g == null) {
            return true;
        }
        super.onTouchEvent(motionEvent);
        for (C0482k a : this.f1987f.f2052f) {
            a.mo1772a(motionEvent);
        }
        return this.f1984c.onTouchEvent(motionEvent) ? true : this.f1987f.m1986a(motionEvent);
    }
}
