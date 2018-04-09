package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.opengl.GLUtils;
import java.lang.Thread.State;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

@SuppressLint({"NewApi"})
public class C0640l extends Thread {
    private AtomicBoolean f2098a;
    private SurfaceTexture f2099b;
    private C0617a f2100c;
    private EGL10 f2101d;
    private EGLDisplay f2102e = EGL10.EGL_NO_DISPLAY;
    private EGLContext f2103f = EGL10.EGL_NO_CONTEXT;
    private EGLSurface f2104g = EGL10.EGL_NO_SURFACE;
    private GL10 f2105h;
    private int f2106i = 1;
    private boolean f2107j = false;
    private final C0618E f2108k;

    public interface C0617a {
        int mo1831a();
    }

    public C0640l(SurfaceTexture surfaceTexture, C0617a c0617a, AtomicBoolean atomicBoolean, C0618E c0618e) {
        this.f2099b = surfaceTexture;
        this.f2100c = c0617a;
        this.f2098a = atomicBoolean;
        this.f2108k = c0618e;
    }

    private boolean m2053a(int i, int i2, int i3, int i4, int i5, int i6) {
        this.f2101d = (EGL10) EGLContext.getEGL();
        this.f2102e = this.f2101d.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.f2102e == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetdisplay failed : " + GLUtils.getEGLErrorString(this.f2101d.eglGetError()));
        }
        if (this.f2101d.eglInitialize(this.f2102e, new int[2])) {
            EGLConfig[] eGLConfigArr = new EGLConfig[100];
            int[] iArr = new int[1];
            if (!this.f2101d.eglChooseConfig(this.f2102e, new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344}, eGLConfigArr, 100, iArr) || iArr[0] <= 0) {
                return false;
            }
            this.f2103f = this.f2101d.eglCreateContext(this.f2102e, eGLConfigArr[0], EGL10.EGL_NO_CONTEXT, new int[]{12440, 1, 12344});
            this.f2104g = this.f2101d.eglCreateWindowSurface(this.f2102e, eGLConfigArr[0], this.f2099b, null);
            if (this.f2104g == EGL10.EGL_NO_SURFACE || this.f2103f == EGL10.EGL_NO_CONTEXT) {
                if (this.f2101d.eglGetError() == 12299) {
                    throw new RuntimeException("eglCreateWindowSurface returned  EGL_BAD_NATIVE_WINDOW. ");
                }
                GLUtils.getEGLErrorString(this.f2101d.eglGetError());
            }
            if (this.f2101d.eglMakeCurrent(this.f2102e, this.f2104g, this.f2104g, this.f2103f)) {
                this.f2105h = (GL10) this.f2103f.getGL();
                return true;
            }
            GLUtils.getEGLErrorString(this.f2101d.eglGetError());
            throw new RuntimeException("eglMakeCurrent failed : " + GLUtils.getEGLErrorString(this.f2101d.eglGetError()));
        }
        throw new RuntimeException("eglInitialize failed : " + GLUtils.getEGLErrorString(this.f2101d.eglGetError()));
    }

    private static boolean m2054b(int i, int i2, int i3, int i4, int i5, int i6) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        return egl10.eglChooseConfig(eglGetDisplay, new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344}, new EGLConfig[100], 100, iArr) && iArr[0] > 0;
    }

    private void m2055d() {
        try {
            if (C0640l.m2054b(5, 6, 5, 0, 24, 0)) {
                m2053a(5, 6, 5, 0, 24, 0);
            } else {
                m2053a(8, 8, 8, 0, 16, 0);
            }
        } catch (IllegalArgumentException e) {
            m2053a(8, 8, 8, 0, 16, 0);
        }
        MapRenderer.nativeInit(this.f2108k.m1915b().f2054h);
        MapRenderer.nativeResize(this.f2108k.m1915b().f2054h, C0618E.f1982a, C0618E.f1983b);
    }

    private void m2056e() {
        this.f2101d.eglDestroyContext(this.f2102e, this.f2103f);
        this.f2101d.eglDestroySurface(this.f2102e, this.f2104g);
        this.f2101d.eglTerminate(this.f2102e);
        this.f2103f = EGL10.EGL_NO_CONTEXT;
        this.f2104g = EGL10.EGL_NO_SURFACE;
    }

    public void m2057a() {
        this.f2106i = 1;
        synchronized (this) {
            if (getState() == State.WAITING) {
                notify();
            }
        }
    }

    public void m2058b() {
        this.f2106i = 0;
    }

    public void m2059c() {
        this.f2107j = true;
        synchronized (this) {
            if (getState() == State.WAITING) {
                notify();
            }
        }
    }

    public void run() {
        m2055d();
        while (this.f2100c != null) {
            if (this.f2106i != 1) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (this.f2108k.m1915b() == null) {
                break;
            } else {
                synchronized (this.f2108k.m1915b()) {
                    this.f2106i = this.f2100c.mo1831a();
                    for (C0482k c0482k : this.f2108k.m1915b().f2052f) {
                        C0616D H = this.f2108k.m1915b().m1957H();
                        if (this.f2105h == null) {
                            return;
                        }
                        this.f2105h.glPushMatrix();
                        this.f2105h.glRotatef((float) H.f1965c, 1.0f, 0.0f, 0.0f);
                        this.f2105h.glRotatef((float) H.f1964b, 0.0f, 0.0f, 1.0f);
                        c0482k.mo1776a(this.f2105h, H);
                        this.f2105h.glPopMatrix();
                        this.f2105h.glColor4f(0.96f, 0.95f, 0.94f, 1.0f);
                    }
                    this.f2101d.eglSwapBuffers(this.f2102e, this.f2104g);
                }
            }
            if (this.f2107j) {
                break;
            }
        }
        m2056e();
    }
}
