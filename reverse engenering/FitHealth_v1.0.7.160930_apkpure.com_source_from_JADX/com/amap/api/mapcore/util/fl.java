package com.amap.api.mapcore.util;

import java.util.concurrent.Callable;

/* compiled from: DiskLruCache */
class fl implements Callable<Void> {
    final /* synthetic */ fk f643a;

    fl(fk fkVar) {
        this.f643a = fkVar;
    }

    public /* synthetic */ Object call() throws Exception {
        return m941a();
    }

    public Void m941a() throws Exception {
        synchronized (this.f643a) {
            if (this.f643a.f637k == null) {
            } else {
                this.f643a.m933j();
                if (this.f643a.m931h()) {
                    this.f643a.m930g();
                    this.f643a.f639m = 0;
                }
            }
        }
        return null;
    }
}
