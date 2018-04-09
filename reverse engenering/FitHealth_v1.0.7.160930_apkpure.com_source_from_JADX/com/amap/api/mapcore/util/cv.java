package com.amap.api.mapcore.util;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: AsyncTask */
public abstract class cv<Params, Progress, Result> {
    private static final ThreadFactory f392a = new cw();
    public static final Executor f393b = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, f396e, f392a, new DiscardOldestPolicy());
    public static final Executor f394c;
    public static final Executor f395d = Executors.newFixedThreadPool(2, f392a);
    private static final BlockingQueue<Runnable> f396e = new LinkedBlockingQueue(10);
    private static final C0234b f397f = new C0234b();
    private static volatile Executor f398g = f394c;
    private final C0238e<Params, Result> f399h = new C15941(this);
    private final FutureTask<Result> f400i = new FutureTask<Result>(this, this.f399h) {
        final /* synthetic */ cv f380a;

        protected void done() {
            try {
                this.f380a.m459c(this.f380a.f400i.get());
            } catch (Throwable e) {
                Log.w("AsyncTask", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                this.f380a.m459c(null);
            }
        }
    };
    private volatile C0237d f401j = C0237d.PENDING;
    private final AtomicBoolean f402k = new AtomicBoolean();
    private final AtomicBoolean f403l = new AtomicBoolean();

    /* compiled from: AsyncTask */
    private static class C0233a<Data> {
        final cv f381a;
        final Data[] f382b;

        C0233a(cv cvVar, Data... dataArr) {
            this.f381a = cvVar;
            this.f382b = dataArr;
        }
    }

    /* compiled from: AsyncTask */
    private static class C0234b extends Handler {
        private C0234b() {
        }

        public void handleMessage(Message message) {
            C0233a c0233a = (C0233a) message.obj;
            switch (message.what) {
                case 1:
                    c0233a.f381a.m461e(c0233a.f382b[0]);
                    return;
                case 2:
                    c0233a.f381a.m469b(c0233a.f382b);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: AsyncTask */
    private static class C0236c implements Executor {
        final ArrayDeque<Runnable> f385a;
        Runnable f386b;

        private C0236c() {
            this.f385a = new ArrayDeque();
        }

        public synchronized void execute(final Runnable runnable) {
            this.f385a.offer(new Runnable(this) {
                final /* synthetic */ C0236c f384b;

                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        this.f384b.m453a();
                    }
                }
            });
            if (this.f386b == null) {
                m453a();
            }
        }

        protected synchronized void m453a() {
            Runnable runnable = (Runnable) this.f385a.poll();
            this.f386b = runnable;
            if (runnable != null) {
                cv.f393b.execute(this.f386b);
            }
        }
    }

    /* compiled from: AsyncTask */
    public enum C0237d {
        PENDING,
        RUNNING,
        FINISHED
    }

    /* compiled from: AsyncTask */
    private static abstract class C0238e<Params, Result> implements Callable<Result> {
        Params[] f391b;

        private C0238e() {
        }
    }

    /* compiled from: AsyncTask */
    class C15941 extends C0238e<Params, Result> {
        final /* synthetic */ cv f4161a;

        C15941(cv cvVar) {
            this.f4161a = cvVar;
            super();
        }

        public Result call() throws Exception {
            this.f4161a.f403l.set(true);
            Process.setThreadPriority(10);
            return this.f4161a.m460d(this.f4161a.mo1428a(this.b));
        }
    }

    protected abstract Result mo1428a(Params... paramsArr);

    static {
        Executor c0236c;
        if (dj.m594c()) {
            c0236c = new C0236c();
        } else {
            c0236c = Executors.newSingleThreadExecutor(f392a);
        }
        f394c = c0236c;
    }

    private void m459c(Result result) {
        if (!this.f403l.get()) {
            m460d(result);
        }
    }

    private Result m460d(Result result) {
        f397f.obtainMessage(1, new C0233a(this, result)).sendToTarget();
        return result;
    }

    public final C0237d m462a() {
        return this.f401j;
    }

    protected void m467b() {
    }

    protected void mo1429a(Result result) {
    }

    protected void m469b(Progress... progressArr) {
    }

    protected void mo1641b(Result result) {
        m471c();
    }

    protected void m471c() {
    }

    public final boolean m472d() {
        return this.f402k.get();
    }

    public final boolean m466a(boolean z) {
        this.f402k.set(true);
        return this.f400i.cancel(z);
    }

    public final cv<Params, Progress, Result> m470c(Params... paramsArr) {
        return m463a(f398g, (Object[]) paramsArr);
    }

    public final cv<Params, Progress, Result> m463a(Executor executor, Params... paramsArr) {
        if (this.f401j != C0237d.PENDING) {
            switch (this.f401j) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.f401j = C0237d.RUNNING;
        m467b();
        this.f399h.f391b = paramsArr;
        executor.execute(this.f400i);
        return this;
    }

    private void m461e(Result result) {
        if (m472d()) {
            mo1641b((Object) result);
        } else {
            mo1429a((Object) result);
        }
        this.f401j = C0237d.FINISHED;
    }
}
