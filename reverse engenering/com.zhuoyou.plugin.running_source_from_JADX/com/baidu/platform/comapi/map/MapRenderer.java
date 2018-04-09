package com.baidu.platform.comapi.map;

import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MapRenderer implements Renderer {
    private static final String f1998d = MapRenderer.class.getSimpleName();
    public int f1999a;
    public int f2000b;
    public int f2001c;
    private long f2002e;
    private C0627a f2003f;
    private final C0638i f2004g;

    public interface C0627a {
        void mo1834e();
    }

    public MapRenderer(C0638i c0638i, C0627a c0627a) {
        this.f2003f = c0627a;
        this.f2004g = c0638i;
    }

    private void m1928a(GL10 gl10) {
        gl10.glClear(16640);
        gl10.glClearColor(0.85f, 0.8f, 0.8f, 0.0f);
    }

    private boolean m1929a() {
        return this.f2002e != 0;
    }

    public static native void nativeInit(long j);

    public static native int nativeRender(long j);

    public static native void nativeResize(long j, int i, int i2);

    public void m1930a(long j) {
        this.f2002e = j;
    }

    public void onDrawFrame(GL10 gl10) {
        if (m1929a()) {
            if (this.f2001c <= 1) {
                nativeResize(this.f2002e, this.f1999a, this.f2000b);
                this.f2001c++;
            }
            this.f2003f.mo1834e();
            int nativeRender = nativeRender(this.f2002e);
            for (C0482k c0482k : this.f2004g.m2046a().f2052f) {
                C0616D H = this.f2004g.m2046a().m1957H();
                gl10.glPushMatrix();
                gl10.glRotatef((float) H.f1965c, 1.0f, 0.0f, 0.0f);
                gl10.glRotatef((float) H.f1964b, 0.0f, 0.0f, 1.0f);
                c0482k.mo1776a(gl10, H);
                gl10.glPopMatrix();
                gl10.glColor4f(0.96f, 0.95f, 0.94f, 1.0f);
            }
            C0638i c0638i = this.f2004g;
            if (nativeRender == 1) {
                c0638i.requestRender();
                return;
            } else if (this.f2004g.m2046a().m1997c()) {
                if (c0638i.getRenderMode() != 1) {
                    c0638i.setRenderMode(1);
                    return;
                }
                return;
            } else if (c0638i.getRenderMode() != 0) {
                c0638i.setRenderMode(0);
                return;
            } else {
                return;
            }
        }
        m1928a(gl10);
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        if (this.f2002e != 0) {
            nativeResize(this.f2002e, i, i2);
        }
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        nativeInit(this.f2002e);
        if (m1929a()) {
            this.f2003f.mo1834e();
        }
    }
}
