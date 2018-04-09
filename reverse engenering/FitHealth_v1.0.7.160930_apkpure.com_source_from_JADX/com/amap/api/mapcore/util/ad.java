package com.amap.api.mapcore.util;

import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: MapMessageQueue */
class ad {
    C1592c f136a;
    private CopyOnWriteArrayList<CameraUpdateFactoryDelegate> f137b = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<ac> f138c = new CopyOnWriteArrayList();

    public ad(C1592c c1592c) {
        this.f136a = c1592c;
    }

    public synchronized void m134a(ac acVar) {
        this.f136a.setRunLowFrame(false);
        this.f138c.add(acVar);
        this.f136a.setRunLowFrame(false);
    }

    public ac m133a() {
        if (m136b() == 0) {
            return null;
        }
        ac acVar = (ac) this.f138c.get(0);
        this.f138c.remove(acVar);
        return acVar;
    }

    public synchronized int m136b() {
        return this.f138c.size();
    }

    public void m135a(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) {
        this.f136a.setRunLowFrame(false);
        this.f137b.add(cameraUpdateFactoryDelegate);
        this.f136a.setRunLowFrame(false);
    }

    public CameraUpdateFactoryDelegate m137c() {
        if (m138d() == 0) {
            return null;
        }
        CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate = (CameraUpdateFactoryDelegate) this.f137b.get(0);
        this.f137b.remove(cameraUpdateFactoryDelegate);
        this.f136a.setRunLowFrame(false);
        return cameraUpdateFactoryDelegate;
    }

    public int m138d() {
        return this.f137b.size();
    }
}
