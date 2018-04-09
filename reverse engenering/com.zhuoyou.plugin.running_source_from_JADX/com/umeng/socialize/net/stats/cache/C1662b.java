package com.umeng.socialize.net.stats.cache;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.umeng.socialize.net.stats.cache.C1665c.C1664a;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;
import java.io.File;

/* compiled from: StatsCacheApis */
public class C1662b {
    static String f4990a = C1662b.class.getSimpleName();
    private HandlerThread f4991b;
    private Handler f4992c;
    private C1665c f4993d;

    /* compiled from: StatsCacheApis */
    private static class C1661a {
        private static final C1662b f4989a = new C1662b();

        private C1661a() {
        }
    }

    public static final C1662b m4517a() {
        return C1661a.f4989a;
    }

    private C1662b() {
        this.f4991b = new HandlerThread(Log.TAG, 10);
        this.f4991b.start();
        this.f4992c = new Handler(this.f4991b.getLooper());
        Object b = m4519b();
        if (!TextUtils.isEmpty(b)) {
            this.f4993d = new C1665c(b);
        }
    }

    public void m4521a(final String str, final UMCacheListener uMCacheListener) {
        if (this.f4993d != null) {
            this.f4992c.post(new Runnable(this) {
                final /* synthetic */ C1662b f4983c;

                public void run() {
                    Log.m4546d(C1662b.f4990a, "save:" + Thread.currentThread().getId());
                    boolean a = this.f4983c.f4993d.m4543a(str);
                    if (uMCacheListener != null) {
                        uMCacheListener.onResult(a, null);
                    }
                }
            });
        }
    }

    public void m4520a(final UMCacheListener uMCacheListener) {
        if (this.f4993d != null) {
            this.f4992c.post(new Runnable(this) {
                final /* synthetic */ C1662b f4985b;

                public void run() {
                    Log.m4546d(C1662b.f4990a, "read:" + Thread.currentThread().getId());
                    C1664a a = this.f4985b.f4993d.m4542a();
                    if (uMCacheListener != null) {
                        uMCacheListener.onResult(a != null, a);
                    }
                }
            });
        }
    }

    public void m4522b(final String str, final UMCacheListener uMCacheListener) {
        if (this.f4993d != null) {
            this.f4992c.post(new Runnable(this) {
                final /* synthetic */ C1662b f4988c;

                public void run() {
                    Log.m4546d(C1662b.f4990a, "delete:" + Thread.currentThread().getId());
                    boolean b = this.f4988c.f4993d.m4544b(str);
                    if (uMCacheListener != null) {
                        uMCacheListener.onResult(b, null);
                    }
                }
            });
        }
    }

    private String m4519b() {
        if (ContextUtil.getContext() == null) {
            return null;
        }
        Object packageName = ContextUtil.getContext().getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        return File.separator + "data" + File.separator + "data" + File.separator + packageName + File.separator + "files" + File.separator + "umSocialStateLog";
    }
}
