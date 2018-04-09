package com.amap.api.mapcore.util;

import android.util.Log;
import com.autonavi.amap.mapcore.interfaces.GLOverlay;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: CustomGLOverlayLayer */
class C0275s {
    C0274a f702a = new C0274a();
    private CopyOnWriteArrayList<GLOverlay> f703b = new CopyOnWriteArrayList();

    /* compiled from: CustomGLOverlayLayer */
    static class C0274a implements Comparator<Object>, Serializable {
        C0274a() {
        }

        public int compare(Object obj, Object obj2) {
            GLOverlay gLOverlay = (GLOverlay) obj;
            GLOverlay gLOverlay2 = (GLOverlay) obj2;
            if (!(gLOverlay == null || gLOverlay2 == null)) {
                try {
                    if (gLOverlay.getZIndex() > gLOverlay2.getZIndex()) {
                        return 1;
                    }
                    if (gLOverlay.getZIndex() < gLOverlay2.getZIndex()) {
                        return -1;
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "CustomGLOverlayLayer", "compare");
                    th.printStackTrace();
                }
            }
            return 0;
        }
    }

    C0275s() {
    }

    public void m1002a() {
        try {
            this.f703b.clear();
        } catch (Throwable th) {
            ee.m4243a(th, "CustomGLOverlayLayer", "clear");
            th.printStackTrace();
            Log.d("amapApi", "GLOverlayLayer clear erro" + th.getMessage());
        }
    }

    public void m1003a(GLOverlay gLOverlay) {
        m1005b(gLOverlay);
        this.f703b.add(gLOverlay);
        m1001b();
    }

    public boolean m1005b(GLOverlay gLOverlay) {
        if (this.f703b.contains(gLOverlay)) {
            return this.f703b.remove(gLOverlay);
        }
        return false;
    }

    private void m1001b() {
        Object[] toArray = this.f703b.toArray();
        Arrays.sort(toArray, this.f702a);
        this.f703b.clear();
        for (Object obj : toArray) {
            this.f703b.add((GLOverlay) obj);
        }
    }

    public void m1004a(GL10 gl10) {
        Iterator it = this.f703b.iterator();
        while (it.hasNext()) {
            ((GLOverlay) it.next()).onDrawFrame(gl10);
        }
    }
}
