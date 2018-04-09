package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.Marker;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IMarkerDelegate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: MapOverlayImageView */
class ae extends View {
    IAMapDelegate f139a;
    C0197a f140b = new C0197a();
    private CopyOnWriteArrayList<IMarkerDelegate> f141c = new CopyOnWriteArrayList(new ArrayList(500));
    private CopyOnWriteArrayList<am> f142d = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<Integer> f143e = new CopyOnWriteArrayList();
    private IPoint f144f;
    private IMarkerDelegate f145g;
    private Handler f146h = new Handler();
    private Runnable f147i = new af(this);
    private final Handler f148j = new Handler();
    private final Runnable f149k = new ag(this);

    /* compiled from: MapOverlayImageView */
    static class C0197a implements Comparator<Object>, Serializable {
        C0197a() {
        }

        public int compare(Object obj, Object obj2) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) obj;
            IMarkerDelegate iMarkerDelegate2 = (IMarkerDelegate) obj2;
            if (!(iMarkerDelegate == null || iMarkerDelegate2 == null)) {
                try {
                    if (iMarkerDelegate.getZIndex() > iMarkerDelegate2.getZIndex()) {
                        return 1;
                    }
                    if (iMarkerDelegate.getZIndex() < iMarkerDelegate2.getZIndex()) {
                        return -1;
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "MapOverlayImageView", "compare");
                    th.printStackTrace();
                }
            }
            return 0;
        }
    }

    public IAMapDelegate m142a() {
        return this.f139a;
    }

    public ae(Context context) {
        super(context);
    }

    public ae(Context context, AttributeSet attributeSet, IAMapDelegate iAMapDelegate) {
        super(context, attributeSet);
        this.f139a = iAMapDelegate;
    }

    public synchronized IMarkerDelegate m144a(String str) throws RemoteException {
        IMarkerDelegate iMarkerDelegate;
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            iMarkerDelegate = (IMarkerDelegate) it.next();
            if (iMarkerDelegate != null && iMarkerDelegate.getId().equals(str)) {
                break;
            }
        }
        iMarkerDelegate = null;
        return iMarkerDelegate;
    }

    public synchronized boolean m150a(IMarkerDelegate iMarkerDelegate) {
        return this.f141c.contains(iMarkerDelegate);
    }

    protected synchronized int m151b() {
        return this.f141c.size();
    }

    public synchronized void m153b(String str) {
        Object obj;
        Iterator it;
        IMarkerDelegate iMarkerDelegate;
        if (str != null) {
            try {
                if (str.trim().length() != 0) {
                    obj = null;
                    this.f145g = null;
                    this.f144f = null;
                    if (obj == null) {
                        it = this.f141c.iterator();
                        while (it.hasNext()) {
                            ((IMarkerDelegate) it.next()).remove();
                        }
                        this.f141c.clear();
                    } else {
                        it = this.f141c.iterator();
                        while (it.hasNext()) {
                            iMarkerDelegate = (IMarkerDelegate) it.next();
                            if (!str.equals(iMarkerDelegate.getId())) {
                                iMarkerDelegate.remove();
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                ee.m4243a(e, "MapOverlayImageView", "clear");
                e.printStackTrace();
            }
        }
        obj = 1;
        this.f145g = null;
        this.f144f = null;
        if (obj == null) {
            it = this.f141c.iterator();
            while (it.hasNext()) {
                iMarkerDelegate = (IMarkerDelegate) it.next();
                if (!str.equals(iMarkerDelegate.getId())) {
                    iMarkerDelegate.remove();
                }
            }
        } else {
            it = this.f141c.iterator();
            while (it.hasNext()) {
                ((IMarkerDelegate) it.next()).remove();
            }
            this.f141c.clear();
        }
        return;
    }

    public synchronized void m152b(IMarkerDelegate iMarkerDelegate) {
        this.f141c.add(iMarkerDelegate);
        m165i();
    }

    public synchronized boolean m156c(IMarkerDelegate iMarkerDelegate) {
        m162f(iMarkerDelegate);
        return this.f141c.remove(iMarkerDelegate);
    }

    public synchronized void m157d(IMarkerDelegate iMarkerDelegate) {
        try {
            if (this.f141c.remove(iMarkerDelegate)) {
                m140l();
                this.f141c.add(iMarkerDelegate);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "set2Top");
        }
    }

    public void m160e(IMarkerDelegate iMarkerDelegate) {
        if (this.f144f == null) {
            this.f144f = new IPoint();
        }
        Rect rect = iMarkerDelegate.getRect();
        this.f144f = new IPoint(rect.left + (rect.width() / 2), rect.top);
        this.f145g = iMarkerDelegate;
        try {
            this.f139a.showInfoWindow(this.f145g);
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "showInfoWindow");
            th.printStackTrace();
        }
    }

    public void m162f(IMarkerDelegate iMarkerDelegate) {
        try {
            if (iMarkerDelegate.isInfoWindowShown()) {
                this.f139a.hiddenInfoWindowShown();
                this.f145g = null;
            } else if (this.f145g != null && this.f145g.getId() == iMarkerDelegate.getId()) {
                this.f145g = null;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public synchronized void m155c() {
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
            try {
                if (iMarkerDelegate.isVisible()) {
                    iMarkerDelegate.calFPoint();
                }
            } catch (Throwable th) {
                ee.m4243a(th, "MapOverlayImageView", "calFPoint");
                th.printStackTrace();
            }
        }
    }

    private void m140l() {
        try {
            Collection arrayList = new ArrayList(this.f141c);
            Collections.sort(arrayList, this.f140b);
            this.f141c = new CopyOnWriteArrayList(arrayList);
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "changeOverlayIndex");
        }
    }

    public void m148a(GL10 gl10) {
        Iterator it = this.f143e.iterator();
        while (it.hasNext()) {
            gl10.glDeleteTextures(1, new int[]{((Integer) it.next()).intValue()}, 0);
            this.f139a.deleteTexsureId(r0.intValue());
        }
        this.f143e.clear();
        if (!(this.f145g == null || this.f145g.isViewMode())) {
            m167k();
        }
        it = this.f141c.iterator();
        while (it.hasNext()) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
            if (iMarkerDelegate.checkInBounds() || iMarkerDelegate.isInfoWindowShown()) {
                iMarkerDelegate.drawMarker(gl10, this.f139a);
            }
        }
    }

    public synchronized boolean m158d() {
        boolean z;
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            if (!((IMarkerDelegate) it.next()).isAllowLow()) {
                z = false;
                break;
            }
        }
        z = true;
        return z;
    }

    public IMarkerDelegate m159e() {
        return this.f145g;
    }

    public IMarkerDelegate m143a(MotionEvent motionEvent) {
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
            if ((iMarkerDelegate instanceof ai) && m149a(iMarkerDelegate.getRect(), (int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.f145g = iMarkerDelegate;
                return this.f145g;
            }
        }
        return null;
    }

    public synchronized void m146a(am amVar) {
        if (amVar != null) {
            if (amVar.m202b() != 0) {
                this.f142d.add(amVar);
            }
        }
    }

    public synchronized void m145a(int i) {
        Iterator it = this.f142d.iterator();
        while (it.hasNext()) {
            am amVar = (am) it.next();
            if (amVar.m202b() == i) {
                this.f142d.remove(amVar);
            }
        }
    }

    public void m147a(Integer num) {
        if (num.intValue() != 0) {
            this.f143e.add(num);
        }
    }

    public synchronized int m141a(BitmapDescriptor bitmapDescriptor) {
        int b;
        if (bitmapDescriptor != null) {
            if (!(bitmapDescriptor.getBitmap() == null || bitmapDescriptor.getBitmap().isRecycled())) {
                for (int i = 0; i < this.f142d.size(); i++) {
                    am amVar = (am) this.f142d.get(i);
                    if (amVar.m201a().equals(bitmapDescriptor)) {
                        b = amVar.m202b();
                        break;
                    }
                }
                b = 0;
            }
        }
        b = 0;
        return b;
    }

    public synchronized void m161f() {
        try {
            Iterator it = this.f141c.iterator();
            while (it.hasNext()) {
                IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
                if (iMarkerDelegate != null) {
                    iMarkerDelegate.destroy();
                }
            }
            m153b(null);
            it = this.f142d.iterator();
            while (it.hasNext()) {
                ((am) it.next()).m201a().recycle();
            }
            this.f142d.clear();
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "destroy");
            th.printStackTrace();
            Log.d("amapApi", "MapOverlayImageView clear erro" + th.getMessage());
        }
        return;
    }

    public boolean m154b(MotionEvent motionEvent) throws RemoteException {
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
            if ((iMarkerDelegate instanceof ai) && iMarkerDelegate.isVisible()) {
                Rect rect = iMarkerDelegate.getRect();
                boolean a = m149a(rect, (int) motionEvent.getX(), (int) motionEvent.getY());
                if (a) {
                    this.f144f = new IPoint(rect.left + (rect.width() / 2), rect.top);
                    this.f145g = iMarkerDelegate;
                    return a;
                }
            }
        }
        return false;
    }

    public boolean m149a(Rect rect, int i, int i2) {
        return rect.contains(i, i2);
    }

    public synchronized List<Marker> m163g() {
        List<Marker> arrayList;
        arrayList = new ArrayList();
        try {
            Rect rect = new Rect(0, 0, this.f139a.getMapWidth(), this.f139a.getMapHeight());
            IPoint iPoint = new IPoint();
            Iterator it = this.f141c.iterator();
            while (it.hasNext()) {
                IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
                if (!(iMarkerDelegate instanceof at)) {
                    FPoint mapPosition = iMarkerDelegate.getMapPosition();
                    if (mapPosition != null) {
                        this.f139a.getMapProjection().map2Win(mapPosition.f2028x, mapPosition.f2029y, iPoint);
                        if (m149a(rect, iPoint.f2030x, iPoint.f2031y)) {
                            arrayList.add(new Marker(iMarkerDelegate));
                        }
                    }
                }
            }
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "getMapScreenMarkers");
            th.printStackTrace();
        }
        return arrayList;
    }

    public synchronized void m164h() {
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
            if (iMarkerDelegate.isDestory()) {
                iMarkerDelegate.realDestroy();
            }
        }
    }

    protected synchronized void m165i() {
        this.f146h.removeCallbacks(this.f147i);
        this.f146h.postDelayed(this.f147i, 10);
    }

    public void m166j() {
        Iterator it = this.f141c.iterator();
        while (it.hasNext()) {
            IMarkerDelegate iMarkerDelegate = (IMarkerDelegate) it.next();
            if (iMarkerDelegate != null) {
                iMarkerDelegate.reLoadTexture();
            }
        }
        if (this.f142d != null) {
            this.f142d.clear();
        }
    }

    public void m167k() {
        this.f148j.post(this.f149k);
    }
}
