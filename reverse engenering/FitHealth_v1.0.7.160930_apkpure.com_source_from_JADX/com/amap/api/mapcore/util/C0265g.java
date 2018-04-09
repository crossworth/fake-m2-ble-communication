package com.amap.api.mapcore.util;

/* compiled from: AMapDelegateImp */
class C0265g implements Runnable {
    final /* synthetic */ C1592c f673a;

    C0265g(C1592c c1592c) {
        this.f673a = c1592c;
    }

    public void run() {
        if (this.f673a.aj != null) {
            this.f673a.aS = true;
            if (this.f673a.al != null) {
                this.f673a.al.setVisible(false);
            }
        }
    }
}
