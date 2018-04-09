package com.umeng.analytics;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: QueuedWork */
public class C0923f {
    private static List<WeakReference<ScheduledFuture<?>>> f3132a = new ArrayList();
    private static ExecutorService f3133b = Executors.newSingleThreadExecutor();
    private static long f3134c = 5;
    private static ScheduledExecutorService f3135d = Executors.newSingleThreadScheduledExecutor();

    public static void m3076a(Runnable runnable) {
        if (f3133b.isShutdown()) {
            f3133b = Executors.newSingleThreadExecutor();
        }
        f3133b.execute(runnable);
    }

    public static void m3075a() {
        try {
            for (WeakReference weakReference : f3132a) {
                ScheduledFuture scheduledFuture = (ScheduledFuture) weakReference.get();
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
            }
            f3132a.clear();
            if (!f3133b.isShutdown()) {
                f3133b.shutdown();
            }
            if (!f3135d.isShutdown()) {
                f3135d.shutdown();
            }
            f3133b.awaitTermination(f3134c, TimeUnit.SECONDS);
            f3135d.awaitTermination(f3134c, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public static synchronized void m3078b(Runnable runnable) {
        synchronized (C0923f.class) {
            if (f3135d.isShutdown()) {
                f3135d = Executors.newSingleThreadScheduledExecutor();
            }
            f3135d.execute(runnable);
        }
    }

    public static synchronized void m3077a(Runnable runnable, long j) {
        synchronized (C0923f.class) {
            if (f3135d.isShutdown()) {
                f3135d = Executors.newSingleThreadScheduledExecutor();
            }
            f3132a.add(new WeakReference(f3135d.schedule(runnable, j, TimeUnit.MILLISECONDS)));
        }
    }

    public static synchronized void m3079c(Runnable runnable) {
        synchronized (C0923f.class) {
            if (f3135d.isShutdown()) {
                f3135d = Executors.newSingleThreadScheduledExecutor();
            }
            try {
                f3135d.submit(runnable).get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
            }
        }
    }
}
