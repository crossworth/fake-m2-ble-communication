package com.amap.api.mapcore.util;

import android.content.Context;
import android.view.View;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.UrlTileProvider;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.ITileOverlayDelegate;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TileOverlayView */
public class aw extends View {
    CopyOnWriteArrayList<ITileOverlayDelegate> f200a = new CopyOnWriteArrayList();
    C0200a f201b = new C0200a();
    CopyOnWriteArrayList<Integer> f202c = new CopyOnWriteArrayList();
    av f203d = null;
    private IAMapDelegate f204e;

    /* compiled from: TileOverlayView */
    static class C0200a implements Comparator<Object>, Serializable {
        C0200a() {
        }

        public int compare(Object obj, Object obj2) {
            ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) obj;
            ITileOverlayDelegate iTileOverlayDelegate2 = (ITileOverlayDelegate) obj2;
            if (!(iTileOverlayDelegate == null || iTileOverlayDelegate2 == null)) {
                try {
                    if (iTileOverlayDelegate.getZIndex() > iTileOverlayDelegate2.getZIndex()) {
                        return 1;
                    }
                    if (iTileOverlayDelegate.getZIndex() < iTileOverlayDelegate2.getZIndex()) {
                        return -1;
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "TileOverlayView", "compare");
                    th.printStackTrace();
                }
            }
            return 0;
        }
    }

    public aw(Context context) {
        super(context);
    }

    public aw(Context context, IAMapDelegate iAMapDelegate) {
        super(context);
        this.f204e = iAMapDelegate;
        this.f203d = new av(new TileOverlayOptions().tileProvider(new UrlTileProvider(this, 256, 256) {
            final /* synthetic */ aw f5337a;

            public URL getTileUrl(int i, int i2, int i3) {
                try {
                    return new URL(String.format("http://grid.amap.com/grid/%d/%d/%d?dpiType=webrd&lang=zh_cn&pack=%s&version=3.3.2", new Object[]{Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i2), C0273r.f696c}));
                } catch (Throwable th) {
                    return null;
                }
            }
        }), this, true);
    }

    IAMapDelegate m215a() {
        return this.f204e;
    }

    public void m217a(GL10 gl10) {
        try {
            Iterator it = this.f202c.iterator();
            while (it.hasNext()) {
                dj.m580a(gl10, ((Integer) it.next()).intValue());
            }
            this.f202c.clear();
            this.f203d.drawTiles(gl10);
            it = this.f200a.iterator();
            while (it.hasNext()) {
                ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) it.next();
                if (iTileOverlayDelegate.isVisible()) {
                    iTileOverlayDelegate.drawTiles(gl10);
                }
            }
        } catch (Throwable th) {
        }
    }

    public void m219b() {
        Iterator it = this.f200a.iterator();
        while (it.hasNext()) {
            ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) it.next();
            if (iTileOverlayDelegate != null) {
                iTileOverlayDelegate.remove();
            }
        }
        this.f200a.clear();
    }

    void m222c() {
        Object[] toArray = this.f200a.toArray();
        Arrays.sort(toArray, this.f201b);
        this.f200a.clear();
        for (Object obj : toArray) {
            this.f200a.add((ITileOverlayDelegate) obj);
        }
    }

    public void m216a(ITileOverlayDelegate iTileOverlayDelegate) {
        m221b(iTileOverlayDelegate);
        this.f200a.add(iTileOverlayDelegate);
        m222c();
    }

    public boolean m221b(ITileOverlayDelegate iTileOverlayDelegate) {
        return this.f200a.remove(iTileOverlayDelegate);
    }

    public void m218a(boolean z) {
        this.f203d.refresh(z);
        Iterator it = this.f200a.iterator();
        while (it.hasNext()) {
            ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) it.next();
            if (iTileOverlayDelegate != null && iTileOverlayDelegate.isVisible()) {
                iTileOverlayDelegate.refresh(z);
            }
        }
    }

    public void m223d() {
        this.f203d.onResume();
        Iterator it = this.f200a.iterator();
        while (it.hasNext()) {
            ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) it.next();
            if (iTileOverlayDelegate != null) {
                iTileOverlayDelegate.onResume();
            }
        }
    }

    public void m220b(boolean z) {
        this.f203d.onFling(z);
        Iterator it = this.f200a.iterator();
        while (it.hasNext()) {
            ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) it.next();
            if (iTileOverlayDelegate != null) {
                iTileOverlayDelegate.onFling(z);
            }
        }
    }

    public void m224e() {
        this.f203d.remove();
        this.f203d = null;
    }

    public void m225f() {
        this.f203d.reLoadTexture();
        Iterator it = this.f200a.iterator();
        while (it.hasNext()) {
            ITileOverlayDelegate iTileOverlayDelegate = (ITileOverlayDelegate) it.next();
            if (iTileOverlayDelegate != null) {
                iTileOverlayDelegate.reLoadTexture();
            }
        }
    }
}
