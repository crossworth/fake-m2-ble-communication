package com.droi.sdk.analytics;

import android.os.Process;
import com.droi.sdk.analytics.C0760e.C0757a;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

class C0771g implements UncaughtExceptionHandler {
    private Thread f2329a;
    private Throwable f2330b;
    private UncaughtExceptionHandler f2331c = Thread.getDefaultUncaughtExceptionHandler();

    C0771g() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private String m2372a(Throwable th) {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        while (th != null) {
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        String stringWriter2 = stringWriter.toString();
        printWriter.close();
        return stringWriter2;
    }

    private void m2373a() {
        C0753a.m2312a("DroiCrashHandler", "endApplication");
        if (this.f2331c != null) {
            C0753a.m2312a("DroiCrashHandler", "mDefaultHandler != null");
            this.f2331c.uncaughtException(this.f2329a, this.f2330b);
            return;
        }
        C0753a.m2312a("DroiCrashHandler", "mDefaultHandler == null");
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private void m2374b(Throwable th) {
        C0753a.m2312a("DroiCrashHandler", "handleException");
        if (C0760e.m2348b() >= 20) {
            C0753a.m2312a("DroiCrashHandler", "exceed maximum");
            return;
        }
        if (th == null) {
            th = new Exception("Report requested by developer");
        }
        C0757a c0757a = new C0757a();
        c0757a.f2277b = C0755c.m2329b();
        String a = m2372a(th);
        c0757a.f2281f = C0755c.m2323a(C0755c.m2332b(a));
        c0757a.f2278c = a;
        c0757a.f2279d = C0754b.m2319c();
        c0757a.f2280e = C0754b.m2320d();
        new C0760e(C0770f.f2324a).m2351a(c0757a);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        C0780p.m2414b(C0770f.f2324a, C0755c.m2329b());
        try {
            if (C0756d.f2274b) {
                C0753a.m2312a("DroiCrashHandler", "enableCrashHandler");
                this.f2329a = thread;
                this.f2330b = th;
                m2374b(this.f2330b);
                m2373a();
            } else if (this.f2331c != null) {
                this.f2331c.uncaughtException(thread, th);
                C0753a.m2313b("DroiCrashHandler", "DroiCrashHandler is disabled and fallback to default handler.");
            } else {
                C0753a.m2313b("DroiCrashHandler", "DroiCrashHandler is disabled and there is no default handler");
            }
        } catch (Exception e) {
            C0753a.m2311a("DroiCrashHandler", e);
            m2373a();
        }
    }
}
