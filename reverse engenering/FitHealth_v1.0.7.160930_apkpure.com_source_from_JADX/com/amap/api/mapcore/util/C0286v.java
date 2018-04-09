package com.amap.api.mapcore.util;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.amap.api.maps.model.LatLng;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IGroundOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IPolylineDelegate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: GLOverlayLayer */
class C0286v {
    private static int f742c = 0;
    IAMapDelegate f743a;
    C0285a f744b = new C0285a();
    private CopyOnWriteArrayList<IOverlayDelegate> f745d = new CopyOnWriteArrayList(new ArrayList(500));
    private CopyOnWriteArrayList<Integer> f746e = new CopyOnWriteArrayList();
    private Handler f747f = new Handler();
    private Runnable f748g = new C0287w(this);

    /* compiled from: GLOverlayLayer */
    static class C0285a implements Comparator<Object>, Serializable {
        C0285a() {
        }

        public int compare(Object obj, Object obj2) {
            IOverlayDelegate iOverlayDelegate = (IOverlayDelegate) obj;
            IOverlayDelegate iOverlayDelegate2 = (IOverlayDelegate) obj2;
            if (!(iOverlayDelegate == null || iOverlayDelegate2 == null)) {
                try {
                    if (iOverlayDelegate.getZIndex() > iOverlayDelegate2.getZIndex()) {
                        return 1;
                    }
                    if (iOverlayDelegate.getZIndex() < iOverlayDelegate2.getZIndex()) {
                        return -1;
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "GLOverlayLayer", "compare");
                    th.printStackTrace();
                }
            }
            return 0;
        }
    }

    static String m1022a(String str) {
        f742c++;
        return str + f742c;
    }

    public C0286v(IAMapDelegate iAMapDelegate) {
        this.f743a = iAMapDelegate;
    }

    public synchronized void m1032b(String str) {
        if (str != null) {
            try {
                if (str.trim().length() != 0) {
                    Iterator it = this.f745d.iterator();
                    while (it.hasNext()) {
                        IOverlayDelegate iOverlayDelegate = (IOverlayDelegate) it.next();
                        if (!str.equals(iOverlayDelegate.getId())) {
                            this.f745d.remove(iOverlayDelegate);
                        }
                    }
                }
            } catch (Throwable th) {
                ee.m4243a(th, "GLOverlayLayer", "clear");
                th.printStackTrace();
                Log.d("amapApi", "GLOverlayLayer clear erro" + th.getMessage());
            }
        }
        this.f745d.clear();
        f742c = 0;
    }

    public synchronized void m1027a() {
        try {
            Iterator it = this.f745d.iterator();
            while (it.hasNext()) {
                ((IOverlayDelegate) it.next()).destroy();
            }
            m1032b(null);
        } catch (Throwable th) {
            ee.m4243a(th, "GLOverlayLayer", "destory");
            th.printStackTrace();
            Log.d("amapApi", "GLOverlayLayer destory erro" + th.getMessage());
        }
        return;
    }

    private synchronized IOverlayDelegate m1025d(String str) throws RemoteException {
        IOverlayDelegate iOverlayDelegate;
        Iterator it = this.f745d.iterator();
        while (it.hasNext()) {
            iOverlayDelegate = (IOverlayDelegate) it.next();
            if (iOverlayDelegate != null && iOverlayDelegate.getId().equals(str)) {
                break;
            }
        }
        iOverlayDelegate = null;
        return iOverlayDelegate;
    }

    public synchronized void m1028a(IOverlayDelegate iOverlayDelegate) throws RemoteException {
        this.f745d.add(iOverlayDelegate);
        m1031b();
    }

    public synchronized boolean m1034c(String str) throws RemoteException {
        boolean remove;
        IOverlayDelegate d = m1025d(str);
        if (d != null) {
            remove = this.f745d.remove(d);
        } else {
            remove = false;
        }
        return remove;
    }

    protected synchronized void m1031b() {
        this.f747f.removeCallbacks(this.f748g);
        this.f747f.postDelayed(this.f748g, 10);
    }

    public void m1030a(GL10 gl10, boolean z, int i) {
        Iterator it = this.f746e.iterator();
        while (it.hasNext()) {
            gl10.glDeleteTextures(1, new int[]{((Integer) it.next()).intValue()}, 0);
            this.f743a.deleteTexsureId(r0.intValue());
        }
        this.f746e.clear();
        int size = this.f745d.size();
        Iterator it2 = this.f745d.iterator();
        while (it2.hasNext()) {
            IOverlayDelegate iOverlayDelegate = (IOverlayDelegate) it2.next();
            try {
                if (iOverlayDelegate.isVisible()) {
                    if (size > 20) {
                        if (iOverlayDelegate.checkInBounds()) {
                            if (z) {
                                if (iOverlayDelegate.getZIndex() <= ((float) i)) {
                                    iOverlayDelegate.draw(gl10);
                                }
                            } else if (iOverlayDelegate.getZIndex() > ((float) i)) {
                                iOverlayDelegate.draw(gl10);
                            }
                        }
                    } else if (z) {
                        if (iOverlayDelegate.getZIndex() <= ((float) i)) {
                            iOverlayDelegate.draw(gl10);
                        }
                    } else if (iOverlayDelegate.getZIndex() > ((float) i)) {
                        iOverlayDelegate.draw(gl10);
                    }
                }
            } catch (Throwable e) {
                ee.m4243a(e, "GLOverlayLayer", "draw");
                e.printStackTrace();
            }
        }
    }

    public void m1029a(Integer num) {
        if (num.intValue() != 0) {
            this.f746e.add(num);
        }
    }

    public synchronized void m1033c() {
        Iterator it = this.f745d.iterator();
        while (it.hasNext()) {
            IOverlayDelegate iOverlayDelegate = (IOverlayDelegate) it.next();
            if (iOverlayDelegate != null) {
                try {
                    iOverlayDelegate.calMapFPoint();
                } catch (Throwable e) {
                    ee.m4243a(e, "GLOverlayLayer", "calMapFPoint");
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean m1035d() {
        Iterator it = this.f745d.iterator();
        while (it.hasNext()) {
            IOverlayDelegate iOverlayDelegate = (IOverlayDelegate) it.next();
            if (iOverlayDelegate != null && !iOverlayDelegate.isDrawFinish()) {
                return false;
            }
        }
        return true;
    }

    public synchronized IOverlayDelegate m1026a(LatLng latLng) {
        IOverlayDelegate iOverlayDelegate;
        Iterator it = this.f745d.iterator();
        while (it.hasNext()) {
            iOverlayDelegate = (IOverlayDelegate) it.next();
            if (iOverlayDelegate != null && iOverlayDelegate.isDrawFinish() && (iOverlayDelegate instanceof IPolylineDelegate) && ((IPolylineDelegate) iOverlayDelegate).contains(latLng)) {
                break;
            }
        }
        iOverlayDelegate = null;
        return iOverlayDelegate;
    }

    public void m1036e() {
        Iterator it = this.f745d.iterator();
        while (it.hasNext()) {
            IOverlayDelegate iOverlayDelegate = (IOverlayDelegate) it.next();
            if (iOverlayDelegate != null) {
                if (iOverlayDelegate instanceof IPolylineDelegate) {
                    ((IPolylineDelegate) iOverlayDelegate).reLoadTexture();
                } else if (iOverlayDelegate instanceof IGroundOverlayDelegate) {
                    ((IGroundOverlayDelegate) iOverlayDelegate).reLoadTexture();
                }
            }
        }
    }
}
