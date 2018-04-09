package com.amap.api.mapcore.util;

import com.amap.api.mapcore.util.gc.C0266a;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: ThreadPool */
public final class ga {
    private static ga f674a = null;
    private ExecutorService f675b;
    private ConcurrentHashMap<gc, Future<?>> f676c = new ConcurrentHashMap();
    private C0266a f677d = new gb(this);

    public static synchronized ga m982a(int i) {
        ga gaVar;
        synchronized (ga.class) {
            if (f674a == null) {
                f674a = new ga(i);
            }
            gaVar = f674a;
        }
        return gaVar;
    }

    private ga(int i) {
        try {
            this.f675b = Executors.newFixedThreadPool(i);
        } catch (Throwable th) {
            ee.m4243a(th, "TPool", "ThreadPool");
            th.printStackTrace();
        }
    }

    public void m989a(gc gcVar) throws dk {
        try {
            if (!m988b(gcVar) && this.f675b != null && !this.f675b.isShutdown()) {
                gcVar.f678d = this.f677d;
                Future submit = this.f675b.submit(gcVar);
                if (submit != null) {
                    m985a(gcVar, submit);
                }
            }
        } catch (RejectedExecutionException e) {
        } catch (Throwable th) {
            th.printStackTrace();
            ee.m4243a(th, "TPool", "addTask");
            dk dkVar = new dk("thread pool has exception");
        }
    }

    public static synchronized void m983a() {
        synchronized (ga.class) {
            try {
                if (f674a != null) {
                    f674a.m987b();
                    f674a = null;
                }
            } catch (Throwable th) {
                ee.m4243a(th, "TPool", "onDestroy");
                th.printStackTrace();
            }
        }
    }

    private void m987b() {
        try {
            for (Entry key : this.f676c.entrySet()) {
                Future future = (Future) this.f676c.get((gc) key.getKey());
                if (future != null) {
                    future.cancel(true);
                }
            }
            this.f676c.clear();
            this.f675b.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            ee.m4243a(th, "TPool", "destroy");
            th.printStackTrace();
        }
    }

    private synchronized boolean m988b(gc gcVar) {
        boolean z;
        z = false;
        try {
            z = this.f676c.containsKey(gcVar);
        } catch (Throwable th) {
            ee.m4243a(th, "TPool", "contain");
            th.printStackTrace();
        }
        return z;
    }

    private synchronized void m985a(gc gcVar, Future<?> future) {
        try {
            this.f676c.put(gcVar, future);
        } catch (Throwable th) {
            ee.m4243a(th, "TPool", "addQueue");
            th.printStackTrace();
        }
    }

    private synchronized void m986a(gc gcVar, boolean z) {
        try {
            Future future = (Future) this.f676c.remove(gcVar);
            if (z && future != null) {
                future.cancel(true);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "TPool", "removeQueue");
            th.printStackTrace();
        }
    }
}
