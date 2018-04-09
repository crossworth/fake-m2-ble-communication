package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Looper;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: SDKLogHandler */
public class bh extends be implements UncaughtExceptionHandler {
    private static ExecutorService f4350e;
    private Context f4351d;

    /* compiled from: SDKLogHandler */
    private static class C1609a implements cu {
        private Context f4349a;

        C1609a(Context context) {
            this.f4349a = context;
        }

        public void mo1760a() {
            try {
                bf.m1349b(this.f4349a);
            } catch (Throwable th) {
                be.m1340a(th, "LogNetListener", "onNetCompleted");
            }
        }
    }

    public static synchronized bh m4436a(Context context, ba baVar) throws ar {
        bh bhVar;
        synchronized (bh.class) {
            if (baVar == null) {
                throw new ar("sdk info is null");
            } else if (baVar.m1308a() == null || "".equals(baVar.m1308a())) {
                throw new ar("sdk name is invalid");
            } else {
                try {
                    if (be.f1397a == null) {
                        be.f1397a = new bh(context, baVar);
                    } else {
                        be.f1397a.f1399c = false;
                    }
                    be.f1397a.mo1761a(context, baVar, be.f1397a.f1399c);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                bhVar = (bh) be.f1397a;
            }
        }
        return bhVar;
    }

    public static synchronized bh m4435a() {
        bh bhVar;
        synchronized (bh.class) {
            bhVar = (bh) be.f1397a;
        }
        return bhVar;
    }

    public static void m4438b(Throwable th, String str, String str2) {
        if (be.f1397a != null) {
            be.f1397a.mo1762a(th, 1, str, str2);
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (th != null) {
            mo1762a(th, 0, null, null);
            if (this.b != null) {
                try {
                    Thread.setDefaultUncaughtExceptionHandler(this.b);
                } catch (Throwable th2) {
                }
                this.b.uncaughtException(thread, th);
            }
        }
    }

    protected void mo1762a(Throwable th, int i, String str, String str2) {
        bf.m1347a(this.f4351d, th, i, str, str2);
    }

    protected void mo1761a(final Context context, final ba baVar, final boolean z) {
        try {
            ExecutorService b = m4437b();
            if (b != null && !b.isShutdown()) {
                b.submit(new Runnable(this) {
                    final /* synthetic */ bh f1414d;

                    public void run() {
                        try {
                            synchronized (Looper.getMainLooper()) {
                                new bx(context, true).m1427a(baVar);
                            }
                            if (z) {
                                synchronized (Looper.getMainLooper()) {
                                    by byVar = new by(context);
                                    bz bzVar = new bz();
                                    bzVar.m1435c(true);
                                    bzVar.m1431a(true);
                                    bzVar.m1433b(true);
                                    byVar.m1430a(bzVar);
                                }
                                bf.m1346a(this.f1414d.f4351d);
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
            }
        } catch (RejectedExecutionException e) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private bh(Context context, ba baVar) {
        this.f4351d = context;
        ct.m1557a(new C1609a(context));
        m4439c();
    }

    private void m4439c() {
        try {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            if (this.b == null) {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
            } else if (this.b.toString().indexOf("com.amap.api") != -1) {
                this.c = false;
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void m4442c(Throwable th, String str, String str2) {
        if (th != null) {
            try {
                mo1762a(th, 1, str, str2);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    static synchronized ExecutorService m4437b() {
        ExecutorService executorService;
        synchronized (bh.class) {
            try {
                if (f4350e == null || f4350e.isShutdown()) {
                    f4350e = Executors.newSingleThreadExecutor();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            executorService = f4350e;
        }
        return executorService;
    }
}
