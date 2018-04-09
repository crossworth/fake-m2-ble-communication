package com.autonavi.amap.mapcore.interfaces;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

@SuppressLint({"NewApi"})
public class GLTextureView extends TextureView implements SurfaceTextureListener {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceView";
    private static final C0485g sGLThreadManager = new C0485g();
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private C0484f mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private Renderer mRenderer;
    private final WeakReference<GLTextureView> mThisWeakRef = new WeakReference(this);

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    private static class C0483e {
        EGL10 f2050a;
        EGLDisplay f2051b;
        EGLSurface f2052c;
        EGLConfig f2053d;
        EGLContext f2054e;
        private WeakReference<GLTextureView> f2055f;

        public C0483e(WeakReference<GLTextureView> weakReference) {
            this.f2055f = weakReference;
        }

        public void m2089a() {
            this.f2050a = (EGL10) EGLContext.getEGL();
            this.f2051b = this.f2050a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.f2051b == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (this.f2050a.eglInitialize(this.f2051b, new int[2])) {
                GLTextureView gLTextureView = (GLTextureView) this.f2055f.get();
                if (gLTextureView == null) {
                    this.f2053d = null;
                    this.f2054e = null;
                } else {
                    this.f2053d = gLTextureView.mEGLConfigChooser.chooseConfig(this.f2050a, this.f2051b);
                    this.f2054e = gLTextureView.mEGLContextFactory.createContext(this.f2050a, this.f2051b, this.f2053d);
                }
                if (this.f2054e == null || this.f2054e == EGL10.EGL_NO_CONTEXT) {
                    this.f2054e = null;
                    m2084a("createContext");
                }
                this.f2052c = null;
                return;
            }
            throw new RuntimeException("eglInitialize failed");
        }

        public boolean m2090b() {
            if (this.f2050a == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.f2051b == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.f2053d == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                m2088g();
                GLTextureView gLTextureView = (GLTextureView) this.f2055f.get();
                if (gLTextureView != null) {
                    this.f2052c = gLTextureView.mEGLWindowSurfaceFactory.createWindowSurface(this.f2050a, this.f2051b, this.f2053d, gLTextureView.getSurfaceTexture());
                } else {
                    this.f2052c = null;
                }
                if (this.f2052c == null || this.f2052c == EGL10.EGL_NO_SURFACE) {
                    if (this.f2050a.eglGetError() == 12299) {
                        Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    }
                    return false;
                } else if (this.f2050a.eglMakeCurrent(this.f2051b, this.f2052c, this.f2052c, this.f2054e)) {
                    return true;
                } else {
                    C0483e.m2086a("EGLHelper", "eglMakeCurrent", this.f2050a.eglGetError());
                    return false;
                }
            }
        }

        GL m2091c() {
            GL gl = this.f2054e.getGL();
            GLTextureView gLTextureView = (GLTextureView) this.f2055f.get();
            if (gLTextureView == null) {
                return gl;
            }
            if (gLTextureView.mGLWrapper != null) {
                gl = gLTextureView.mGLWrapper.wrap(gl);
            }
            if ((gLTextureView.mDebugFlags & 3) == 0) {
                return gl;
            }
            Writer c0486h;
            int i = 0;
            if ((gLTextureView.mDebugFlags & 1) != 0) {
                i = 1;
            }
            if ((gLTextureView.mDebugFlags & 2) != 0) {
                c0486h = new C0486h();
            } else {
                c0486h = null;
            }
            return GLDebugHelper.wrap(gl, i, c0486h);
        }

        public int m2092d() {
            if (this.f2050a.eglSwapBuffers(this.f2051b, this.f2052c)) {
                return 12288;
            }
            return this.f2050a.eglGetError();
        }

        public void m2093e() {
            m2088g();
        }

        private void m2088g() {
            if (this.f2052c != null && this.f2052c != EGL10.EGL_NO_SURFACE) {
                this.f2050a.eglMakeCurrent(this.f2051b, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLTextureView gLTextureView = (GLTextureView) this.f2055f.get();
                if (gLTextureView != null) {
                    gLTextureView.mEGLWindowSurfaceFactory.destroySurface(this.f2050a, this.f2051b, this.f2052c);
                }
                this.f2052c = null;
            }
        }

        public void m2094f() {
            if (this.f2054e != null) {
                GLTextureView gLTextureView = (GLTextureView) this.f2055f.get();
                if (gLTextureView != null) {
                    gLTextureView.mEGLContextFactory.destroyContext(this.f2050a, this.f2051b, this.f2054e);
                }
                this.f2054e = null;
            }
            if (this.f2051b != null) {
                this.f2050a.eglTerminate(this.f2051b);
                this.f2051b = null;
            }
        }

        private void m2084a(String str) {
            C0483e.m2085a(str, this.f2050a.eglGetError());
        }

        public static void m2085a(String str, int i) {
            throw new RuntimeException(C0483e.m2087b(str, i));
        }

        public static void m2086a(String str, String str2, int i) {
            Log.w(str, C0483e.m2087b(str2, i));
        }

        public static String m2087b(String str, int i) {
            return str + " failed: " + i;
        }
    }

    static class C0484f extends Thread {
        private boolean f2056a;
        private boolean f2057b;
        private boolean f2058c;
        private boolean f2059d;
        private boolean f2060e;
        private boolean f2061f;
        private boolean f2062g;
        private boolean f2063h;
        private boolean f2064i;
        private boolean f2065j;
        private boolean f2066k;
        private int f2067l = 0;
        private int f2068m = 0;
        private int f2069n = 1;
        private boolean f2070o = true;
        private boolean f2071p;
        private ArrayList<Runnable> f2072q = new ArrayList();
        private boolean f2073r = true;
        private C0483e f2074s;
        private WeakReference<GLTextureView> f2075t;

        C0484f(WeakReference<GLTextureView> weakReference) {
            this.f2075t = weakReference;
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                m2098l();
            } catch (InterruptedException e) {
            } finally {
                GLTextureView.sGLThreadManager.m2113a(this);
            }
        }

        private void m2096j() {
            if (this.f2064i) {
                this.f2064i = false;
                this.f2074s.m2093e();
            }
        }

        private void m2097k() {
            if (this.f2063h) {
                this.f2074s.m2094f();
                this.f2063h = false;
                GLTextureView.sGLThreadManager.m2118c(this);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void m2098l() throws java.lang.InterruptedException {
            /*
            r18 = this;
            r1 = new com.autonavi.amap.mapcore.interfaces.GLTextureView$e;
            r0 = r18;
            r2 = r0.f2075t;
            r1.<init>(r2);
            r0 = r18;
            r0.f2074s = r1;
            r1 = 0;
            r0 = r18;
            r0.f2063h = r1;
            r1 = 0;
            r0 = r18;
            r0.f2064i = r1;
            r3 = 0;
            r12 = 0;
            r1 = 0;
            r11 = 0;
            r10 = 0;
            r9 = 0;
            r8 = 0;
            r2 = 0;
            r7 = 0;
            r6 = 0;
            r5 = 0;
            r4 = 0;
            r14 = r3;
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r17;
        L_0x0031:
            r15 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r15);	 Catch:{ all -> 0x01d5 }
        L_0x0036:
            r0 = r18;
            r1 = r0.f2056a;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x004d;
        L_0x003c:
            monitor-exit(r15);	 Catch:{ all -> 0x01d2 }
            r2 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;
            monitor-enter(r2);
            r18.m2096j();	 Catch:{ all -> 0x004a }
            r18.m2097k();	 Catch:{ all -> 0x004a }
            monitor-exit(r2);	 Catch:{ all -> 0x004a }
            return;
        L_0x004a:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x004a }
            throw r1;
        L_0x004d:
            r0 = r18;
            r1 = r0.f2072q;	 Catch:{ all -> 0x01d2 }
            r1 = r1.isEmpty();	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x0081;
        L_0x0057:
            r0 = r18;
            r1 = r0.f2072q;	 Catch:{ all -> 0x01d2 }
            r2 = 0;
            r1 = r1.remove(r2);	 Catch:{ all -> 0x01d2 }
            r1 = (java.lang.Runnable) r1;	 Catch:{ all -> 0x01d2 }
            r2 = r6;
            r6 = r4;
            r4 = r1;
            r1 = r11;
            r11 = r10;
            r10 = r9;
            r9 = r8;
            r8 = r7;
            r7 = r5;
            r5 = r3;
        L_0x006c:
            monitor-exit(r15);	 Catch:{ all -> 0x01d2 }
            if (r4 == 0) goto L_0x01f9;
        L_0x006f:
            r4.run();	 Catch:{ all -> 0x01d5 }
            r4 = 0;
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r17;
            goto L_0x0031;
        L_0x0081:
            r1 = 0;
            r0 = r18;
            r13 = r0.f2059d;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r0 = r0.f2058c;	 Catch:{ all -> 0x01d2 }
            r16 = r0;
            r0 = r16;
            if (r13 == r0) goto L_0x02f2;
        L_0x0090:
            r0 = r18;
            r1 = r0.f2058c;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r13 = r0.f2058c;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r0.f2059d = r13;	 Catch:{ all -> 0x01d2 }
            r13 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r13.notifyAll();	 Catch:{ all -> 0x01d2 }
            r13 = r1;
        L_0x00a4:
            r0 = r18;
            r1 = r0.f2066k;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00b6;
        L_0x00aa:
            r18.m2096j();	 Catch:{ all -> 0x01d2 }
            r18.m2097k();	 Catch:{ all -> 0x01d2 }
            r1 = 0;
            r0 = r18;
            r0.f2066k = r1;	 Catch:{ all -> 0x01d2 }
            r5 = 1;
        L_0x00b6:
            if (r9 == 0) goto L_0x00bf;
        L_0x00b8:
            r18.m2096j();	 Catch:{ all -> 0x01d2 }
            r18.m2097k();	 Catch:{ all -> 0x01d2 }
            r9 = 0;
        L_0x00bf:
            if (r13 == 0) goto L_0x00ca;
        L_0x00c1:
            r0 = r18;
            r1 = r0.f2064i;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00ca;
        L_0x00c7:
            r18.m2096j();	 Catch:{ all -> 0x01d2 }
        L_0x00ca:
            if (r13 == 0) goto L_0x00ee;
        L_0x00cc:
            r0 = r18;
            r1 = r0.f2063h;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00ee;
        L_0x00d2:
            r0 = r18;
            r1 = r0.f2075t;	 Catch:{ all -> 0x01d2 }
            r1 = r1.get();	 Catch:{ all -> 0x01d2 }
            r1 = (com.autonavi.amap.mapcore.interfaces.GLTextureView) r1;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x01ab;
        L_0x00de:
            r1 = 0;
        L_0x00df:
            if (r1 == 0) goto L_0x00eb;
        L_0x00e1:
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1 = r1.m2115a();	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00ee;
        L_0x00eb:
            r18.m2097k();	 Catch:{ all -> 0x01d2 }
        L_0x00ee:
            if (r13 == 0) goto L_0x0101;
        L_0x00f0:
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1 = r1.m2116b();	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x0101;
        L_0x00fa:
            r0 = r18;
            r1 = r0.f2074s;	 Catch:{ all -> 0x01d2 }
            r1.m2094f();	 Catch:{ all -> 0x01d2 }
        L_0x0101:
            r0 = r18;
            r1 = r0.f2060e;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x0127;
        L_0x0107:
            r0 = r18;
            r1 = r0.f2062g;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x0127;
        L_0x010d:
            r0 = r18;
            r1 = r0.f2064i;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x0116;
        L_0x0113:
            r18.m2096j();	 Catch:{ all -> 0x01d2 }
        L_0x0116:
            r1 = 1;
            r0 = r18;
            r0.f2062g = r1;	 Catch:{ all -> 0x01d2 }
            r1 = 0;
            r0 = r18;
            r0.f2061f = r1;	 Catch:{ all -> 0x01d2 }
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
        L_0x0127:
            r0 = r18;
            r1 = r0.f2060e;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x013f;
        L_0x012d:
            r0 = r18;
            r1 = r0.f2062g;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x013f;
        L_0x0133:
            r1 = 0;
            r0 = r18;
            r0.f2062g = r1;	 Catch:{ all -> 0x01d2 }
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
        L_0x013f:
            if (r6 == 0) goto L_0x014f;
        L_0x0141:
            r7 = 0;
            r6 = 0;
            r1 = 1;
            r0 = r18;
            r0.f2071p = r1;	 Catch:{ all -> 0x01d2 }
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
        L_0x014f:
            r1 = r18.m2099m();	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x01f0;
        L_0x0155:
            r0 = r18;
            r1 = r0.f2063h;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x015e;
        L_0x015b:
            if (r5 == 0) goto L_0x01b1;
        L_0x015d:
            r5 = 0;
        L_0x015e:
            r0 = r18;
            r1 = r0.f2063h;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x02ee;
        L_0x0164:
            r0 = r18;
            r1 = r0.f2064i;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x02ee;
        L_0x016a:
            r1 = 1;
            r0 = r18;
            r0.f2064i = r1;	 Catch:{ all -> 0x01d2 }
            r11 = 1;
            r10 = 1;
            r8 = 1;
            r1 = r8;
            r8 = r10;
        L_0x0174:
            r0 = r18;
            r10 = r0.f2064i;	 Catch:{ all -> 0x01d2 }
            if (r10 == 0) goto L_0x01ee;
        L_0x017a:
            r0 = r18;
            r10 = r0.f2073r;	 Catch:{ all -> 0x01d2 }
            if (r10 == 0) goto L_0x02e4;
        L_0x0180:
            r7 = 1;
            r0 = r18;
            r3 = r0.f2067l;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r1 = r0.f2068m;	 Catch:{ all -> 0x01d2 }
            r4 = 1;
            r10 = 1;
            r11 = 0;
            r0 = r18;
            r0.f2073r = r11;	 Catch:{ all -> 0x01d2 }
        L_0x0190:
            r11 = 0;
            r0 = r18;
            r0.f2070o = r11;	 Catch:{ all -> 0x01d2 }
            r11 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r11.notifyAll();	 Catch:{ all -> 0x01d2 }
            r11 = r8;
            r8 = r4;
            r4 = r2;
            r2 = r6;
            r6 = r3;
            r17 = r1;
            r1 = r10;
            r10 = r9;
            r9 = r7;
            r7 = r5;
            r5 = r17;
            goto L_0x006c;
        L_0x01ab:
            r1 = r1.mPreserveEGLContextOnPause;	 Catch:{ all -> 0x01d2 }
            goto L_0x00df;
        L_0x01b1:
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r1 = r1.m2117b(r0);	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x015e;
        L_0x01bd:
            r0 = r18;
            r1 = r0.f2074s;	 Catch:{ RuntimeException -> 0x01e3 }
            r1.m2089a();	 Catch:{ RuntimeException -> 0x01e3 }
            r1 = 1;
            r0 = r18;
            r0.f2063h = r1;	 Catch:{ all -> 0x01d2 }
            r12 = 1;
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
            goto L_0x015e;
        L_0x01d2:
            r1 = move-exception;
            monitor-exit(r15);	 Catch:{ all -> 0x01d2 }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x01d5:
            r1 = move-exception;
            r2 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;
            monitor-enter(r2);
            r18.m2096j();	 Catch:{ all -> 0x02db }
            r18.m2097k();	 Catch:{ all -> 0x02db }
            monitor-exit(r2);	 Catch:{ all -> 0x02db }
            throw r1;
        L_0x01e3:
            r1 = move-exception;
            r2 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r2.m2118c(r0);	 Catch:{ all -> 0x01d2 }
            throw r1;	 Catch:{ all -> 0x01d2 }
        L_0x01ee:
            r10 = r8;
            r8 = r1;
        L_0x01f0:
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d2 }
            r1.wait();	 Catch:{ all -> 0x01d2 }
            goto L_0x0036;
        L_0x01f9:
            if (r1 == 0) goto L_0x02e1;
        L_0x01fb:
            r0 = r18;
            r3 = r0.f2074s;	 Catch:{ all -> 0x01d5 }
            r3 = r3.m2090b();	 Catch:{ all -> 0x01d5 }
            if (r3 == 0) goto L_0x02ad;
        L_0x0205:
            r3 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r3);	 Catch:{ all -> 0x01d5 }
            r1 = 1;
            r0 = r18;
            r0.f2065j = r1;	 Catch:{ all -> 0x02aa }
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x02aa }
            r1.notifyAll();	 Catch:{ all -> 0x02aa }
            monitor-exit(r3);	 Catch:{ all -> 0x02aa }
            r1 = 0;
            r3 = r1;
        L_0x0219:
            if (r11 == 0) goto L_0x02de;
        L_0x021b:
            r0 = r18;
            r1 = r0.f2074s;	 Catch:{ all -> 0x01d5 }
            r1 = r1.m2091c();	 Catch:{ all -> 0x01d5 }
            r1 = (javax.microedition.khronos.opengles.GL10) r1;	 Catch:{ all -> 0x01d5 }
            r11 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d5 }
            r11.m2114a(r1);	 Catch:{ all -> 0x01d5 }
            r11 = 0;
            r13 = r1;
        L_0x022e:
            if (r12 == 0) goto L_0x024a;
        L_0x0230:
            r0 = r18;
            r1 = r0.f2075t;	 Catch:{ all -> 0x01d5 }
            r1 = r1.get();	 Catch:{ all -> 0x01d5 }
            r1 = (com.autonavi.amap.mapcore.interfaces.GLTextureView) r1;	 Catch:{ all -> 0x01d5 }
            if (r1 == 0) goto L_0x0249;
        L_0x023c:
            r1 = r1.mRenderer;	 Catch:{ all -> 0x01d5 }
            r0 = r18;
            r12 = r0.f2074s;	 Catch:{ all -> 0x01d5 }
            r12 = r12.f2053d;	 Catch:{ all -> 0x01d5 }
            r1.onSurfaceCreated(r13, r12);	 Catch:{ all -> 0x01d5 }
        L_0x0249:
            r12 = 0;
        L_0x024a:
            if (r9 == 0) goto L_0x0260;
        L_0x024c:
            r0 = r18;
            r1 = r0.f2075t;	 Catch:{ all -> 0x01d5 }
            r1 = r1.get();	 Catch:{ all -> 0x01d5 }
            r1 = (com.autonavi.amap.mapcore.interfaces.GLTextureView) r1;	 Catch:{ all -> 0x01d5 }
            if (r1 == 0) goto L_0x025f;
        L_0x0258:
            r1 = r1.mRenderer;	 Catch:{ all -> 0x01d5 }
            r1.onSurfaceChanged(r13, r6, r5);	 Catch:{ all -> 0x01d5 }
        L_0x025f:
            r9 = 0;
        L_0x0260:
            r0 = r18;
            r1 = r0.f2075t;	 Catch:{ all -> 0x01d5 }
            r1 = r1.get();	 Catch:{ all -> 0x01d5 }
            r1 = (com.autonavi.amap.mapcore.interfaces.GLTextureView) r1;	 Catch:{ all -> 0x01d5 }
            if (r1 == 0) goto L_0x0273;
        L_0x026c:
            r1 = r1.mRenderer;	 Catch:{ all -> 0x01d5 }
            r1.onDrawFrame(r13);	 Catch:{ all -> 0x01d5 }
        L_0x0273:
            r0 = r18;
            r1 = r0.f2074s;	 Catch:{ all -> 0x01d5 }
            r1 = r1.m2092d();	 Catch:{ all -> 0x01d5 }
            switch(r1) {
                case 12288: goto L_0x0297;
                case 12302: goto L_0x02d6;
                default: goto L_0x027e;
            };	 Catch:{ all -> 0x01d5 }
        L_0x027e:
            r14 = "GLThread";
            r15 = "eglSwapBuffers";
            com.autonavi.amap.mapcore.interfaces.GLTextureView.C0483e.m2086a(r14, r15, r1);	 Catch:{ all -> 0x01d5 }
            r14 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r14);	 Catch:{ all -> 0x01d5 }
            r1 = 1;
            r0 = r18;
            r0.f2061f = r1;	 Catch:{ all -> 0x02d8 }
            r1 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x02d8 }
            r1.notifyAll();	 Catch:{ all -> 0x02d8 }
            monitor-exit(r14);	 Catch:{ all -> 0x02d8 }
        L_0x0297:
            if (r8 == 0) goto L_0x02f5;
        L_0x0299:
            r1 = 1;
        L_0x029a:
            r2 = r4;
            r14 = r13;
            r4 = r6;
            r6 = r1;
            r17 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r3;
            r3 = r5;
            r5 = r17;
            goto L_0x0031;
        L_0x02aa:
            r1 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x02aa }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x02ad:
            r3 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r3);	 Catch:{ all -> 0x01d5 }
            r13 = 1;
            r0 = r18;
            r0.f2065j = r13;	 Catch:{ all -> 0x02d3 }
            r13 = 1;
            r0 = r18;
            r0.f2061f = r13;	 Catch:{ all -> 0x02d3 }
            r13 = com.autonavi.amap.mapcore.interfaces.GLTextureView.sGLThreadManager;	 Catch:{ all -> 0x02d3 }
            r13.notifyAll();	 Catch:{ all -> 0x02d3 }
            monitor-exit(r3);	 Catch:{ all -> 0x02d3 }
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r17;
            goto L_0x0031;
        L_0x02d3:
            r1 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x02d3 }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x02d6:
            r10 = 1;
            goto L_0x0297;
        L_0x02d8:
            r1 = move-exception;
            monitor-exit(r14);	 Catch:{ all -> 0x02d8 }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x02db:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x02db }
            throw r1;
        L_0x02de:
            r13 = r14;
            goto L_0x022e;
        L_0x02e1:
            r3 = r1;
            goto L_0x0219;
        L_0x02e4:
            r10 = r11;
            r17 = r4;
            r4 = r7;
            r7 = r1;
            r1 = r3;
            r3 = r17;
            goto L_0x0190;
        L_0x02ee:
            r1 = r8;
            r8 = r10;
            goto L_0x0174;
        L_0x02f2:
            r13 = r1;
            goto L_0x00a4;
        L_0x02f5:
            r1 = r2;
            goto L_0x029a;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.amap.mapcore.interfaces.GLTextureView.f.l():void");
        }

        public boolean m2103a() {
            return this.f2063h && this.f2064i && m2099m();
        }

        private boolean m2099m() {
            return !this.f2059d && this.f2060e && !this.f2061f && this.f2067l > 0 && this.f2068m > 0 && (this.f2070o || this.f2069n == 1);
        }

        public void m2100a(int i) {
            if (i < 0 || i > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2069n = i;
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }

        public int m2104b() {
            int i;
            synchronized (GLTextureView.sGLThreadManager) {
                i = this.f2069n;
            }
            return i;
        }

        public void m2105c() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2070o = true;
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }

        public void m2106d() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2060e = true;
                this.f2065j = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (this.f2062g && !this.f2065j && !this.f2057b) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void m2107e() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2060e = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.f2062g && !this.f2057b) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void m2108f() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2058c = true;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.f2057b && !this.f2059d) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void m2109g() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2058c = false;
                this.f2070o = true;
                this.f2071p = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.f2057b && this.f2059d && !this.f2071p) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void m2101a(int i, int i2) {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2067l = i;
                this.f2068m = i2;
                this.f2073r = true;
                this.f2070o = true;
                this.f2071p = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.f2057b && !this.f2059d && !this.f2071p && m2103a()) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void m2110h() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2056a = true;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.f2057b) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void m2111i() {
            this.f2066k = true;
            GLTextureView.sGLThreadManager.notifyAll();
        }

        public void m2102a(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLTextureView.sGLThreadManager) {
                this.f2072q.add(runnable);
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }
    }

    private static class C0485g {
        private static String f2076a = "GLThreadManager";
        private boolean f2077b;
        private int f2078c;
        private boolean f2079d;
        private boolean f2080e;
        private boolean f2081f;
        private C0484f f2082g;

        private C0485g() {
        }

        public synchronized void m2113a(C0484f c0484f) {
            c0484f.f2057b = true;
            if (this.f2082g == c0484f) {
                this.f2082g = null;
            }
            notifyAll();
        }

        public boolean m2117b(C0484f c0484f) {
            if (this.f2082g == c0484f || this.f2082g == null) {
                this.f2082g = c0484f;
                notifyAll();
                return true;
            }
            m2112c();
            if (this.f2080e) {
                return true;
            }
            if (this.f2082g != null) {
                this.f2082g.m2111i();
            }
            return false;
        }

        public void m2118c(C0484f c0484f) {
            if (this.f2082g == c0484f) {
                this.f2082g = null;
            }
            notifyAll();
        }

        public synchronized boolean m2115a() {
            return this.f2081f;
        }

        public synchronized boolean m2116b() {
            m2112c();
            return !this.f2080e;
        }

        public synchronized void m2114a(GL10 gl10) {
            boolean z = true;
            synchronized (this) {
                if (!this.f2079d) {
                    m2112c();
                    String glGetString = gl10.glGetString(7937);
                    if (this.f2078c < 131072) {
                        this.f2080e = !glGetString.startsWith("Q3Dimension MSM7500 ");
                        notifyAll();
                    }
                    if (this.f2080e) {
                        z = false;
                    }
                    this.f2081f = z;
                    this.f2079d = true;
                }
            }
        }

        private void m2112c() {
            if (!this.f2077b) {
                this.f2078c = 131072;
                if (this.f2078c >= 131072) {
                    this.f2080e = true;
                }
                this.f2077b = true;
            }
        }
    }

    static class C0486h extends Writer {
        private StringBuilder f2083a = new StringBuilder();

        C0486h() {
        }

        public void close() {
            m2119a();
        }

        public void flush() {
            m2119a();
        }

        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == '\n') {
                    m2119a();
                } else {
                    this.f2083a.append(c);
                }
            }
        }

        private void m2119a() {
            if (this.f2083a.length() > 0) {
                Log.v(GLTextureView.TAG, this.f2083a.toString());
                this.f2083a.delete(0, this.f2083a.length());
            }
        }
    }

    private abstract class C1614a implements EGLConfigChooser {
        protected int[] f4455a;
        final /* synthetic */ GLTextureView f4456b;

        abstract EGLConfig mo3049a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public C1614a(GLTextureView gLTextureView, int[] iArr) {
            this.f4456b = gLTextureView;
            this.f4455a = m4570a(iArr);
        }

        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (egl10.eglChooseConfig(eGLDisplay, this.f4455a, null, 0, iArr)) {
                int i = iArr[0];
                if (i <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] eGLConfigArr = new EGLConfig[i];
                if (egl10.eglChooseConfig(eGLDisplay, this.f4455a, eGLConfigArr, i, iArr)) {
                    EGLConfig a = mo3049a(egl10, eGLDisplay, eGLConfigArr);
                    if (a != null) {
                        return a;
                    }
                    throw new IllegalArgumentException("No config chosen");
                }
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        private int[] m4570a(int[] iArr) {
            if (this.f4456b.mEGLContextClientVersion != 2 && this.f4456b.mEGLContextClientVersion != 3) {
                return iArr;
            }
            int length = iArr.length;
            Object obj = new int[(length + 2)];
            System.arraycopy(iArr, 0, obj, 0, length - 1);
            obj[length - 1] = 12352;
            if (this.f4456b.mEGLContextClientVersion == 2) {
                obj[length] = 4;
            } else {
                obj[length] = 64;
            }
            obj[length + 1] = 12344;
            return obj;
        }
    }

    private class C1615c implements EGLContextFactory {
        final /* synthetic */ GLTextureView f4457a;
        private int f4458b;

        private C1615c(GLTextureView gLTextureView) {
            this.f4457a = gLTextureView;
            this.f4458b = 12440;
        }

        public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = new int[]{this.f4458b, this.f4457a.mEGLContextClientVersion, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (this.f4457a.mEGLContextClientVersion == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (!egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                Log.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
                C0483e.m2085a("eglDestroyContex", egl10.eglGetError());
            }
        }
    }

    private static class C1616d implements EGLWindowSurfaceFactory {
        private C1616d() {
        }

        public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
            EGLSurface eGLSurface = null;
            try {
                eGLSurface = egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
            } catch (Throwable e) {
                Log.e(GLTextureView.TAG, "eglCreateWindowSurface", e);
            }
            return eGLSurface;
        }

        public void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    private class C1973b extends C1614a {
        protected int f5415c;
        protected int f5416d;
        protected int f5417e;
        protected int f5418f;
        protected int f5419g;
        protected int f5420h;
        final /* synthetic */ GLTextureView f5421i;
        private int[] f5422j = new int[1];

        public C1973b(GLTextureView gLTextureView, int i, int i2, int i3, int i4, int i5, int i6) {
            this.f5421i = gLTextureView;
            super(gLTextureView, new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
            this.f5415c = i;
            this.f5416d = i2;
            this.f5417e = i3;
            this.f5418f = i4;
            this.f5419g = i5;
            this.f5420h = i6;
        }

        public EGLConfig mo3049a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            for (EGLConfig eGLConfig : eGLConfigArr) {
                int a = m5796a(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int a2 = m5796a(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (a >= this.f5419g && a2 >= this.f5420h) {
                    a = m5796a(egl10, eGLDisplay, eGLConfig, 12324, 0);
                    int a3 = m5796a(egl10, eGLDisplay, eGLConfig, 12323, 0);
                    int a4 = m5796a(egl10, eGLDisplay, eGLConfig, 12322, 0);
                    a2 = m5796a(egl10, eGLDisplay, eGLConfig, 12321, 0);
                    if (a == this.f5415c && a3 == this.f5416d && a4 == this.f5417e && a2 == this.f5418f) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }

        private int m5796a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            if (egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.f5422j)) {
                return this.f5422j[0];
            }
            return i2;
        }
    }

    private class C2056i extends C1973b {
        final /* synthetic */ GLTextureView f5570j;

        public C2056i(GLTextureView gLTextureView, boolean z) {
            int i;
            this.f5570j = gLTextureView;
            if (z) {
                i = 16;
            } else {
                i = 0;
            }
            super(gLTextureView, 8, 8, 8, 0, i, 0);
        }
    }

    public GLTextureView(Context context) {
        super(context);
        init();
    }

    public GLTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.m2110h();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    private void init() {
        setSurfaceTextureListener(this);
    }

    public void setGLWrapper(GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setDebugFlags(int i) {
        this.mDebugFlags = i;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.mPreserveEGLContextOnPause = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new C2056i(this, true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new C1615c();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new C1616d();
        }
        this.mRenderer = renderer;
        this.mGLThread = new C0484f(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory eGLContextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setEGLConfigChooser(EGLConfigChooser eGLConfigChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new C2056i(this, z));
    }

    public void setEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new C1973b(this, i, i2, i3, i4, i5, i6));
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    public void setRenderMode(int i) {
        this.mGLThread.m2100a(i);
    }

    public void requestRender() {
        this.mGLThread.m2105c();
    }

    public int getRenderMode() {
        return this.mGLThread.m2104b();
    }

    public void onPause() {
        this.mGLThread.m2108f();
    }

    public void onResume() {
        this.mGLThread.m2109g();
    }

    public void queueEvent(Runnable runnable) {
        this.mGLThread.m2102a(runnable);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int b;
            if (this.mGLThread != null) {
                b = this.mGLThread.m2104b();
            } else {
                b = 1;
            }
            this.mGLThread = new C0484f(this.mThisWeakRef);
            if (b != 1) {
                this.mGLThread.m2100a(b);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    protected void onDetachedFromWindow() {
        if (this.mGLThread != null) {
            this.mGLThread.m2110h();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mGLThread.m2106d();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mGLThread.m2107e();
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mGLThread.m2101a(i, i2);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        onSurfaceTextureSizeChanged(getSurfaceTexture(), i3 - i, i4 - i2);
        super.onLayout(z, i, i2, i3, i4);
    }
}
