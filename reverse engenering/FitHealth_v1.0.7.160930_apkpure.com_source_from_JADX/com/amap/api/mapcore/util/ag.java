package com.amap.api.mapcore.util;

/* compiled from: MapOverlayImageView */
class ag implements Runnable {
    final /* synthetic */ ae f151a;

    ag(ae aeVar) {
        this.f151a = aeVar;
    }

    public void run() {
        try {
            this.f151a.f139a.redrawInfoWindow();
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "redrawInfoWindow post");
            th.printStackTrace();
        }
    }
}
