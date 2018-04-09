package com.amap.api.services.proguard;

import com.amap.api.services.proguard.db.C0388a;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* compiled from: ThreadPool */
public final class da {
    private static da f1534a = null;
    private ExecutorService f1535b;
    private ConcurrentHashMap<db, Future<?>> f1536c = new ConcurrentHashMap();
    private C0388a f1537d = new C16121(this);

    /* compiled from: ThreadPool */
    class C16121 implements C0388a {
        final /* synthetic */ da f4384a;

        C16121(da daVar) {
            this.f4384a = daVar;
        }

        public void mo1778a(db dbVar) {
        }

        public void mo1779b(db dbVar) {
            this.f4384a.m1580a(dbVar, false);
        }
    }

    public static synchronized da m1578a(int i) {
        da daVar;
        synchronized (da.class) {
            if (f1534a == null) {
                f1534a = new da(i);
            }
            daVar = f1534a;
        }
        return daVar;
    }

    private da(int i) {
        try {
            this.f1535b = Executors.newFixedThreadPool(i);
        } catch (Throwable th) {
            bh.m4438b(th, "TPool", "ThreadPool");
            th.printStackTrace();
        }
    }

    private synchronized void m1580a(db dbVar, boolean z) {
        try {
            Future future = (Future) this.f1536c.remove(dbVar);
            if (z && future != null) {
                future.cancel(true);
            }
        } catch (Throwable th) {
            bh.m4438b(th, "TPool", "removeQueue");
            th.printStackTrace();
        }
    }
}
