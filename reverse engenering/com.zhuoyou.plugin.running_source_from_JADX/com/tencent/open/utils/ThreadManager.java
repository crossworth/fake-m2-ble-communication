package com.tencent.open.utils;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: ProGuard */
public final class ThreadManager {
    public static final Executor NETWORK_EXECUTOR = m3959a();
    private static Handler f4220a;
    private static Object f4221b = new Object();
    private static Handler f4222c;
    private static HandlerThread f4223d;
    private static Handler f4224e;
    private static HandlerThread f4225f;

    /* compiled from: ProGuard */
    private static class SerialExecutor implements Executor {
        final Queue<Runnable> f4218a;
        Runnable f4219b;

        private SerialExecutor() {
            this.f4218a = new LinkedList();
        }

        public synchronized void execute(final Runnable runnable) {
            this.f4218a.offer(new Runnable(this) {
                final /* synthetic */ SerialExecutor f4217b;

                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        this.f4217b.m3958a();
                    }
                }
            });
            if (this.f4219b == null) {
                m3958a();
            }
        }

        protected synchronized void m3958a() {
            Runnable runnable = (Runnable) this.f4218a.poll();
            this.f4219b = runnable;
            if (runnable != null) {
                ThreadManager.NETWORK_EXECUTOR.execute(this.f4219b);
            }
        }
    }

    private static Executor m3959a() {
        Executor threadPoolExecutor;
        if (VERSION.SDK_INT >= 11) {
            threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue());
        } else {
            Executor executor;
            try {
                Field declaredField = AsyncTask.class.getDeclaredField("sExecutor");
                declaredField.setAccessible(true);
                executor = (Executor) declaredField.get(null);
            } catch (Exception e) {
                Object threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue());
            }
            threadPoolExecutor = executor;
        }
        if (threadPoolExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) threadPoolExecutor).setCorePoolSize(3);
        }
        return threadPoolExecutor;
    }

    public static void init() {
    }

    public static void executeOnNetWorkThread(Runnable runnable) {
        try {
            NETWORK_EXECUTOR.execute(runnable);
        } catch (RejectedExecutionException e) {
        }
    }

    public static Handler getMainHandler() {
        if (f4220a == null) {
            synchronized (f4221b) {
                if (f4220a == null) {
                    f4220a = new Handler(Looper.getMainLooper());
                }
            }
        }
        return f4220a;
    }

    public static Handler getFileThreadHandler() {
        if (f4224e == null) {
            synchronized (ThreadManager.class) {
                f4225f = new HandlerThread("SDK_FILE_RW");
                f4225f.start();
                f4224e = new Handler(f4225f.getLooper());
            }
        }
        return f4224e;
    }

    public static Looper getFileThreadLooper() {
        return getFileThreadHandler().getLooper();
    }

    public static Thread getSubThread() {
        if (f4223d == null) {
            getSubThreadHandler();
        }
        return f4223d;
    }

    public static Handler getSubThreadHandler() {
        if (f4222c == null) {
            synchronized (ThreadManager.class) {
                f4223d = new HandlerThread("SDK_SUB");
                f4223d.start();
                f4222c = new Handler(f4223d.getLooper());
            }
        }
        return f4222c;
    }

    public static Looper getSubThreadLooper() {
        return getSubThreadHandler().getLooper();
    }

    public static void executeOnSubThread(Runnable runnable) {
        getSubThreadHandler().post(runnable);
    }

    public static void executeOnFileThread(Runnable runnable) {
        getFileThreadHandler().post(runnable);
    }

    public static Executor newSerialExecutor() {
        return new SerialExecutor();
    }
}
