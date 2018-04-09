package com.autonavi.amap.mapcore;

/* compiled from: ConnectionManager */
class C0477a implements Runnable {
    public BaseMapLoader f2032a = null;

    public C0477a(BaseMapLoader baseMapLoader) {
        this.f2032a = baseMapLoader;
    }

    public void run() {
        try {
            this.f2032a.doRequest();
        } catch (Throwable th) {
        }
    }
}
