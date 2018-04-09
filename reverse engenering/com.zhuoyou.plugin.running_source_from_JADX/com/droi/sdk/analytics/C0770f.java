package com.droi.sdk.analytics;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import com.droi.sdk.analytics.priv.AnalyticsModule;
import com.droi.sdk.core.Core;
import java.util.Map;

class C0770f {
    protected static Context f2324a;
    private static C0771g f2325c;
    private static C0770f f2326d;
    private static AnalyticsModule f2327e;
    private Handler f2328b;

    class C07611 implements ActivityLifecycleCallbacks {
        final /* synthetic */ C0770f f2295a;

        C07611(C0770f c0770f) {
            this.f2295a = c0770f;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
            this.f2295a.m2369c(activity);
        }

        public void onActivityResumed(Activity activity) {
            this.f2295a.m2366b(activity);
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }
    }

    class C07644 implements Runnable {
        final /* synthetic */ C0770f f2308a;

        C07644(C0770f c0770f) {
            this.f2308a = c0770f;
        }

        public void run() {
            C0753a.m2312a("DroiAnalyticsImpl", "Start postCrashLog thread");
            try {
                new C0760e(C0770f.f2324a).m2350a();
            } catch (Exception e) {
                C0753a.m2311a("DroiAnalyticsImpl", e);
            }
        }
    }

    class C07655 implements Runnable {
        final /* synthetic */ C0770f f2309a;

        C07655(C0770f c0770f) {
            this.f2309a = c0770f;
        }

        public void run() {
            C0753a.m2312a("DroiAnalyticsImpl", "Start postOtherInfo thread");
            C0777m.m2388a();
        }
    }

    C0770f(Context context) {
        HandlerThread handlerThread = new HandlerThread("DroiAnalyticsImpl");
        handlerThread.start();
        this.f2328b = new Handler(handlerThread.getLooper());
        m2358d(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.droi.sdk.analytics.C0770f m2352a(android.content.Context r3) {
        /*
        r1 = com.droi.sdk.analytics.C0770f.class;
        monitor-enter(r1);
        r0 = f2326d;	 Catch:{ all -> 0x001e }
        if (r0 != 0) goto L_0x001a;
    L_0x0007:
        if (r3 != 0) goto L_0x0013;
    L_0x0009:
        r0 = "DroiAnalyticsImpl";
        r2 = "context is null!";
        com.droi.sdk.analytics.C0753a.m2315d(r0, r2);	 Catch:{ all -> 0x001e }
        r0 = 0;
        monitor-exit(r1);	 Catch:{ all -> 0x001e }
    L_0x0012:
        return r0;
    L_0x0013:
        r0 = new com.droi.sdk.analytics.f;	 Catch:{ all -> 0x001e }
        r0.<init>(r3);	 Catch:{ all -> 0x001e }
        f2326d = r0;	 Catch:{ all -> 0x001e }
    L_0x001a:
        monitor-exit(r1);	 Catch:{ all -> 0x001e }
        r0 = f2326d;
        goto L_0x0012;
    L_0x001e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.analytics.f.a(android.content.Context):com.droi.sdk.analytics.f");
    }

    private void m2353a() {
        C0753a.m2312a("DroiAnalyticsImpl", "postCrashLog");
        new Thread(new C07644(this)).start();
    }

    static void m2354a(String str, String str2) {
        if (f2327e != null) {
            C0753a.m2312a("DroiAnalyticsImpl", "analyticsModule send");
            f2327e.send(C0756d.f2273a, str, str2);
            return;
        }
        C0753a.m2312a("DroiAnalyticsImpl", "analyticsModule == null");
    }

    private void m2355b() {
        C0753a.m2312a("DroiAnalyticsImpl", "postOtherInfo");
        new Thread(new C07655(this)).start();
    }

    static void m2356b(String str, String str2) {
        if (f2327e != null) {
            f2327e.send(1, str, str2);
        } else {
            C0753a.m2312a("DroiAnalyticsImpl", "analyticsModule == null");
        }
    }

    static void m2357c(String str, String str2) {
        C0753a.m2312a("DroiAnalyticsImpl", str2);
        if (f2327e != null) {
            f2327e.send(2, str, str2);
        } else {
            C0753a.m2312a("DroiAnalyticsImpl", "analyticsModule == null");
        }
    }

    private void m2358d(Context context) {
        C0753a.m2316e("DroiAnalyticsImpl", "DroiAnalytics initializing:1.0.015");
        f2324a = context.getApplicationContext();
        Core.initialize(f2324a);
        f2327e = new AnalyticsModule(f2324a);
        f2325c = new C0771g();
        C0778n.m2399a();
        C0754b.m2317a();
        String packageName = f2324a.getPackageName();
        if (packageName == null || packageName.equals(C0755c.m2337e(f2324a))) {
            m2353a();
            m2355b();
        }
    }

    void m2359a(final Context context, final Exception exception) {
        if (context != null) {
            this.f2328b.post(new Runnable(this) {
                long f2291a = C0755c.m2329b();
                final /* synthetic */ C0770f f2294d;

                public void run() {
                    C0753a.m2312a("DroiAnalyticsImpl", "Call onError(context,exception)");
                    new C0772h(context).m2376a(exception.toString(), this.f2291a);
                }
            });
        }
    }

    void m2360a(final Context context, final String str) {
        this.f2328b.post(new Runnable(this) {
            long f2316a = C0755c.m2329b();
            final /* synthetic */ C0770f f2319d;

            public void run() {
                C0780p.m2412a(context, str, this.f2316a);
            }
        });
    }

    void m2361a(Context context, String str, Map<String, String> map, int i) {
        if (context != null) {
            final Context context2 = context;
            final String str2 = str;
            final Map<String, String> map2 = map;
            final int i2 = i;
            this.f2328b.post(new Runnable(this) {
                long f2296a = C0755c.m2329b();
                final /* synthetic */ C0770f f2301f;

                public void run() {
                    C0753a.m2312a("DroiAnalyticsImpl", "Call onEvent(eventId,kv,count)");
                    new C0773i(context2, str2, 1, map2, i2).m2379a(this.f2296a);
                }
            });
        }
    }

    void m2362a(SendPolicy sendPolicy) {
        if (sendPolicy == SendPolicy.REALTIME) {
            C0756d.f2273a = 0;
        } else if (sendPolicy == SendPolicy.SCHEDULE) {
            C0756d.f2273a = 1;
        }
    }

    void m2363a(boolean z) {
        C0753a.m2312a("DroiAnalyticsImpl", "setCrashReport:" + z);
        synchronized (C0770f.class) {
            if (f2325c != null) {
                C0756d.f2274b = z;
            }
        }
    }

    void m2364a(boolean z, int i) {
        f2327e.setScheduleConfig(i, z);
    }

    boolean m2365a(Application application) {
        C0753a.m2312a("DroiAnalyticsImpl", "enableActivityLifecycleCallbacks");
        if (application == null) {
            C0753a.m2314c("DroiAnalyticsImpl", "Application is null!");
            return false;
        } else if (VERSION.SDK_INT >= 14) {
            C0753a.m2312a("DroiAnalyticsImpl", "enableActivityLifecycleCallbacks successfully");
            C0756d.f2275c = true;
            application.registerActivityLifecycleCallbacks(new C07611(this));
            return true;
        } else {
            C0753a.m2312a("DroiAnalyticsImpl", "enableActivityLifecycleCallbacks failed");
            return false;
        }
    }

    void m2366b(final Context context) {
        if (f2326d != null) {
            this.f2328b.post(new Runnable(this) {
                long f2310a = C0755c.m2329b();
                final /* synthetic */ C0770f f2312c;

                public void run() {
                    C0780p.m2411a(context, this.f2310a);
                }
            });
        }
    }

    void m2367b(final Context context, final String str) {
        this.f2328b.post(new Runnable(this) {
            long f2320a = C0755c.m2329b();
            final /* synthetic */ C0770f f2323d;

            public void run() {
                C0780p.m2415b(context, str, this.f2320a);
            }
        });
    }

    void m2368b(Context context, String str, Map<String, String> map, int i) {
        if (context != null) {
            final Context context2 = context;
            final String str2 = str;
            final Map<String, String> map2 = map;
            final int i2 = i;
            this.f2328b.post(new Runnable(this) {
                long f2302a = C0755c.m2329b();
                final /* synthetic */ C0770f f2307f;

                public void run() {
                    C0753a.m2312a("DroiAnalyticsImpl", "Call onCalculateEvent(eventId,kv,du)");
                    new C0773i(context2, str2, 2, map2, i2).m2379a(this.f2302a);
                }
            });
        }
    }

    void m2369c(final Context context) {
        if (f2326d != null) {
            this.f2328b.post(new Runnable(this) {
                long f2313a = C0755c.m2329b();
                final /* synthetic */ C0770f f2315c;

                public void run() {
                    C0780p.m2414b(context, this.f2313a);
                }
            });
        }
    }

    void m2370c(final Context context, final String str) {
        if (context != null) {
            this.f2328b.post(new Runnable(this) {
                long f2287a = C0755c.m2329b();
                final /* synthetic */ C0770f f2290d;

                public void run() {
                    C0753a.m2312a("DroiAnalyticsImpl", "Call onError(context,errorinfo)");
                    new C0772h(context).m2376a(str, this.f2287a);
                }
            });
        }
    }

    void m2371d(Context context, String str) {
        m2361a(context, str, null, 1);
    }
}
