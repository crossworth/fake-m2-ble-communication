package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Looper;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: SDKLogHandler */
public class ee extends eb implements UncaughtExceptionHandler {
    private static ExecutorService f4187e;
    private Context f4188d;

    /* compiled from: SDKLogHandler */
    private static class C1600a implements fu {
        private Context f4186a;

        C1600a(Context context) {
            this.f4186a = context;
        }

        public void mo1643a() {
            try {
                ec.m751b(this.f4186a);
            } catch (Throwable th) {
                eb.m742a(th, "LogNetListener", "onNetCompleted");
            }
        }
    }

    public static synchronized ee m4242a(Context context, dv dvVar) throws dk {
        ee eeVar;
        synchronized (ee.class) {
            if (dvVar == null) {
                throw new dk("sdk info is null");
            } else if (dvVar.m706a() == null || "".equals(dvVar.m706a())) {
                throw new dk("sdk name is invalid");
            } else {
                try {
                    if (eb.f535a == null) {
                        eb.f535a = new ee(context, dvVar);
                    } else {
                        eb.f535a.f537c = false;
                    }
                    eb.f535a.mo1644a(context, dvVar, eb.f535a.f537c);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                eeVar = (ee) eb.f535a;
            }
        }
        return eeVar;
    }

    public static synchronized ee m4241a() {
        ee eeVar;
        synchronized (ee.class) {
            eeVar = (ee) eb.f535a;
        }
        return eeVar;
    }

    public static void m4243a(Throwable th, String str, String str2) {
        if (eb.f535a != null) {
            eb.f535a.mo1645a(th, 1, str, str2);
        }
    }

    public static synchronized void m4244b() {
        synchronized (ee.class) {
            try {
                if (f4187e != null) {
                    f4187e.shutdown();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            try {
                if (!(eb.f535a == null || Thread.getDefaultUncaughtExceptionHandler() != eb.f535a || eb.f535a.f536b == null)) {
                    Thread.setDefaultUncaughtExceptionHandler(eb.f535a.f536b);
                }
                eb.f535a = null;
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (th != null) {
            mo1645a(th, 0, null, null);
            if (this.b != null) {
                try {
                    Thread.setDefaultUncaughtExceptionHandler(this.b);
                } catch (Throwable th2) {
                }
                this.b.uncaughtException(thread, th);
            }
        }
    }

    protected void mo1645a(Throwable th, int i, String str, String str2) {
        ec.m749a(this.f4188d, th, i, str, str2);
    }

    protected void mo1644a(final Context context, final dv dvVar, final boolean z) {
        try {
            ExecutorService c = m4245c();
            if (c != null && !c.isShutdown()) {
                c.submit(new Runnable(this) {
                    final /* synthetic */ ee f552d;

                    public void run() {
                        try {
                            synchronized (Looper.getMainLooper()) {
                                new eu(context, true).m830a(dvVar);
                            }
                            if (z) {
                                synchronized (Looper.getMainLooper()) {
                                    ev evVar = new ev(context);
                                    ew ewVar = new ew();
                                    ewVar.m838c(true);
                                    ewVar.m834a(true);
                                    ewVar.m836b(true);
                                    evVar.m833a(ewVar);
                                }
                                ec.m748a(this.f552d.f4188d);
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

    private ee(Context context, dv dvVar) {
        this.f4188d = context;
        fs.m961a(new C1600a(context));
        m4246d();
    }

    private void m4246d() {
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

    public void m4249b(Throwable th, String str, String str2) {
        if (th != null) {
            try {
                mo1645a(th, 1, str, str2);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    static synchronized ExecutorService m4245c() {
        ExecutorService executorService;
        synchronized (ee.class) {
            try {
                if (f4187e == null || f4187e.isShutdown()) {
                    f4187e = Executors.newSingleThreadExecutor();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            executorService = f4187e;
        }
        return executorService;
    }
}
