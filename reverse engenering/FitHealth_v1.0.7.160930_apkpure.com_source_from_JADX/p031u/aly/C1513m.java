package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.facebook.GraphResponse;
import com.umeng.analytics.C0919a;
import com.umeng.analytics.C0923f;
import com.umeng.analytics.C0924g;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import p031u.aly.C1513m;
import p031u.aly.C1520p;
import p031u.aly.av.C1465e;
import p031u.aly.av.C1466f;

/* compiled from: UMCCAggregatedManager */
public class C1513m {
    private static final int f3851i = 48;
    private static final int f3852j = 49;
    private static Context f3853k;
    private C1505h f3854a;
    private C1519o f3855b;
    private C1520p f3856c;
    private boolean f3857d;
    private boolean f3858e;
    private long f3859f;
    private final String f3860g;
    private final String f3861h;
    private List<String> f3862l;
    private C1511a f3863m;
    private final Thread f3864n;

    /* compiled from: UMCCAggregatedManager */
    class C15101 implements Runnable {
        final /* synthetic */ C1513m f3848a;

        C15101(C1513m c1513m) {
            this.f3848a = c1513m;
        }

        public void run() {
            Looper.prepare();
            if (this.f3848a.f3863m == null) {
                this.f3848a.f3863m = new C1511a(this.f3848a);
            }
            this.f3848a.m3820f();
        }
    }

    /* compiled from: UMCCAggregatedManager */
    private static class C1511a extends Handler {
        private final WeakReference<C1513m> f3849a;

        public C1511a(C1513m c1513m) {
            this.f3849a = new WeakReference(c1513m);
        }

        public void handleMessage(Message message) {
            if (this.f3849a != null) {
                switch (message.what) {
                    case 48:
                        sendEmptyMessageDelayed(48, C1521q.m3880b(System.currentTimeMillis()));
                        C1513m.m3810a(C1513m.f3853k).m3833n();
                        return;
                    case 49:
                        sendEmptyMessageDelayed(49, C1521q.m3881c(System.currentTimeMillis()));
                        C1513m.m3810a(C1513m.f3853k).m3832m();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: UMCCAggregatedManager */
    private static class C1512b {
        private static final C1513m f3850a = new C1513m();

        private C1512b() {
        }
    }

    /* compiled from: UMCCAggregatedManager */
    class C20372 extends C1950f {
        final /* synthetic */ C1513m f5552a;

        C20372(C1513m c1513m) {
            this.f5552a = c1513m;
        }

        public void mo2823a(Object obj, boolean z) {
            if (obj instanceof String) {
                this.f5552a.f3854a.m3774d();
            }
        }
    }

    /* compiled from: UMCCAggregatedManager */
    class C20383 extends C1950f {
        final /* synthetic */ C1513m f5553a;

        C20383(C1513m c1513m) {
            this.f5553a = c1513m;
        }

        public void mo2823a(Object obj, boolean z) {
            if (obj instanceof String) {
                this.f5553a.f3856c.m3876b();
            }
        }
    }

    /* compiled from: UMCCAggregatedManager */
    class C20394 extends C1950f {
        final /* synthetic */ C1513m f5554a;

        C20394(C1513m c1513m) {
            this.f5554a = c1513m;
        }

        public void mo2823a(Object obj, boolean z) {
        }
    }

    /* compiled from: UMCCAggregatedManager */
    class C20405 extends C1950f {
        final /* synthetic */ C1513m f5555a;

        C20405(C1513m c1513m) {
            this.f5555a = c1513m;
        }

        public void mo2823a(Object obj, boolean z) {
            if (obj instanceof String) {
                this.f5555a.f3856c.m3876b();
            }
        }
    }

    /* compiled from: UMCCAggregatedManager */
    class C20438 extends C1950f {
        final /* synthetic */ C1513m f5559a;

        C20438(C1513m c1513m) {
            this.f5559a = c1513m;
        }

        public void mo2823a(Object obj, boolean z) {
            this.f5559a.f3854a = (C1505h) obj;
        }
    }

    /* compiled from: UMCCAggregatedManager */
    class C20449 extends C1950f {
        final /* synthetic */ C1513m f5560a;

        C20449(C1513m c1513m) {
            this.f5560a = c1513m;
        }

        public void mo2823a(Object obj, boolean z) {
            if (obj instanceof C1505h) {
                this.f5560a.f3854a = (C1505h) obj;
            } else if (obj instanceof Boolean) {
                this.f5560a.m3831l();
            }
        }
    }

    public boolean m3839a() {
        return this.f3857d;
    }

    private C1513m() {
        this.f3854a = null;
        this.f3855b = null;
        this.f3856c = null;
        this.f3857d = false;
        this.f3858e = false;
        this.f3859f = 0;
        this.f3860g = "main_fest_mode";
        this.f3861h = "main_fest_timestamp";
        this.f3862l = new ArrayList();
        this.f3863m = null;
        this.f3864n = new Thread(new C15101(this));
        if (f3853k != null) {
            if (this.f3854a == null) {
                this.f3854a = new C1505h();
            }
            if (this.f3855b == null) {
                this.f3855b = C1519o.m3857a(f3853k);
            }
            if (this.f3856c == null) {
                this.f3856c = new C1520p();
            }
        }
        this.f3864n.start();
    }

    private void m3820f() {
        long currentTimeMillis = System.currentTimeMillis();
        this.f3863m.sendEmptyMessageDelayed(48, C1521q.m3880b(currentTimeMillis));
        this.f3863m.sendEmptyMessageDelayed(49, C1521q.m3881c(currentTimeMillis));
    }

    public static final C1513m m3810a(Context context) {
        f3853k = context;
        return C1512b.f3850a;
    }

    public void m3837a(final C1950f c1950f) {
        if (!this.f3857d) {
            C0923f.m3078b(new C0924g(this) {
                final /* synthetic */ C1513m f5208b;

                /* compiled from: UMCCAggregatedManager */
                class C20411 extends C1950f {
                    final /* synthetic */ C19516 f5556a;

                    C20411(C19516 c19516) {
                        this.f5556a = c19516;
                    }

                    public void mo2823a(Object obj, boolean z) {
                        if (obj instanceof Map) {
                            this.f5556a.f5208b.f3854a.m3765a((Map) obj);
                        } else if (!(obj instanceof String) && (obj instanceof Boolean)) {
                        }
                        this.f5556a.f5208b.f3857d = true;
                    }
                }

                public void mo2152a() {
                    try {
                        this.f5208b.f3855b.m3859a(new C20411(this));
                        this.f5208b.m3828j();
                        this.f5208b.m3834o();
                        c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void m3838a(final C1950f c1950f, Map<List<String>, C1509l> map) {
        C1509l c1509l = (C1509l) map.values().toArray()[0];
        List a = c1509l.m3801a();
        if (this.f3862l.size() > 0 && this.f3862l.contains(C1502d.m3741a(a))) {
            this.f3854a.m3768a(new C1950f(this) {
                final /* synthetic */ C1513m f5558b;

                public void mo2823a(Object obj, boolean z) {
                    if (obj instanceof C1505h) {
                        this.f5558b.f3854a = (C1505h) obj;
                    }
                    c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
                }
            }, c1509l);
        } else if (this.f3858e) {
            m3812a(c1509l, a);
        } else if (m3822g()) {
            String a2 = C1502d.m3741a(a);
            if (!this.f3862l.contains(a2)) {
                this.f3862l.add(a2);
            }
            this.f3854a.m3767a(new C20438(this), a, c1509l);
        } else {
            m3812a(c1509l, a);
            m3823h();
        }
    }

    private void m3812a(C1509l c1509l, List<String> list) {
        this.f3854a.m3769a(new C20449(this), c1509l, list, this.f3862l);
    }

    private boolean m3822g() {
        if (this.f3862l.size() < C1516n.m3847a().m3855d()) {
            return true;
        }
        return false;
    }

    private void m3823h() {
        SharedPreferences a = ap.m3451a(f3853k);
        if (!a.getBoolean("main_fest_mode", false)) {
            this.f3858e = true;
            Editor edit = a.edit();
            edit.putBoolean("main_fest_mode", true);
            edit.putLong("main_fest_timestamp", System.currentTimeMillis());
            edit.commit();
        }
    }

    private void m3825i() {
        Editor edit = ap.m3451a(f3853k).edit();
        edit.putBoolean("main_fest_mode", false);
        edit.putLong("main_fest_timestamp", 0);
        edit.commit();
        this.f3858e = false;
    }

    private void m3828j() {
        SharedPreferences a = ap.m3451a(f3853k);
        this.f3858e = a.getBoolean("main_fest_mode", false);
        this.f3859f = a.getLong("main_fest_timestamp", 0);
    }

    public void m3836a(av avVar) {
        if (avVar.f3694b.f3643h != null) {
            avVar.f3694b.f3643h.f3607a = m3840b(new C1950f());
            avVar.f3694b.f3643h.f3608b = m3842c(new C1950f());
        }
    }

    public Map<String, List<C1465e>> m3840b(C1950f c1950f) {
        Map a = this.f3855b.m3858a();
        Map<String, List<C1465e>> hashMap = new HashMap();
        if (a == null || a.size() <= 0) {
            return null;
        }
        for (String str : this.f3862l) {
            if (a.containsKey(str)) {
                hashMap.put(str, a.get(str));
            }
        }
        return hashMap;
    }

    public Map<String, List<C1466f>> m3842c(C1950f c1950f) {
        if (this.f3856c.m3871a().size() > 0) {
            this.f3855b.m3866b(new C1950f(this) {
                final /* synthetic */ C1513m f5548a;

                {
                    this.f5548a = r1;
                }

                public void mo2823a(Object obj, boolean z) {
                    if (obj instanceof String) {
                        this.f5548a.f3856c.m3876b();
                    }
                }
            }, this.f3856c.m3871a());
        }
        return this.f3855b.m3865b(new C1950f());
    }

    public void m3845d(C1950f c1950f) {
        boolean z = false;
        if (this.f3858e) {
            if (this.f3859f == 0) {
                m3828j();
            }
            z = C1521q.m3879a(System.currentTimeMillis(), this.f3859f);
        }
        if (!z) {
            m3825i();
            this.f3862l.clear();
        }
        this.f3856c.m3876b();
        this.f3855b.m3863a(new C1950f(this) {
            final /* synthetic */ C1513m f5549a;

            {
                this.f5549a = r1;
            }

            public void mo2823a(Object obj, boolean z) {
                if (obj.equals(GraphResponse.SUCCESS_KEY)) {
                    this.f5549a.m3829k();
                }
            }
        }, z);
    }

    private void m3829k() {
        for (Entry key : this.f3854a.m3763a().entrySet()) {
            List list = (List) key.getKey();
            if (!this.f3862l.contains(list)) {
                this.f3862l.add(C1502d.m3741a(list));
            }
        }
        if (this.f3862l.size() > 0) {
            this.f3855b.m3861a(new C1950f(), this.f3862l);
        }
    }

    private void m3831l() {
        this.f3856c.m3873a(new C1950f(this) {
            final /* synthetic */ C1513m f5550a;

            {
                this.f5550a = r1;
            }

            public void mo2823a(Object obj, boolean z) {
                this.f5550a.f3856c = (C1520p) obj;
            }
        }, C0919a.f3121r);
    }

    public void m3835a(long j, long j2, String str) {
        this.f3855b.m3860a(new C1950f(this) {
            final /* synthetic */ C1513m f5551a;

            {
                this.f5551a = r1;
            }

            public void mo2823a(Object obj, boolean z) {
                if (!obj.equals(GraphResponse.SUCCESS_KEY)) {
                }
            }
        }, str, j, j2);
    }

    private void m3832m() {
        try {
            if (this.f3854a.m3763a().size() > 0) {
                this.f3855b.m3867c(new C20372(this), this.f3854a.m3763a());
            }
            if (this.f3856c.m3871a().size() > 0) {
                this.f3855b.m3866b(new C20383(this), this.f3856c.m3871a());
            }
            if (this.f3862l.size() > 0) {
                this.f3855b.m3861a(new C1950f(), this.f3862l);
            }
        } catch (Throwable th) {
            bl.m3576b("converyMemoryToDataTable happen error: " + th.toString());
        }
    }

    private void m3833n() {
        try {
            if (this.f3854a.m3763a().size() > 0) {
                this.f3855b.m3862a(new C20394(this), this.f3854a.m3763a());
            }
            if (this.f3856c.m3871a().size() > 0) {
                this.f3855b.m3866b(new C20405(this), this.f3856c.m3871a());
            }
            if (this.f3862l.size() > 0) {
                this.f3855b.m3861a(new C1950f(), this.f3862l);
            }
        } catch (Throwable th) {
            bl.m3576b("convertMemoryToCacheTable happen error: " + th.toString());
        }
    }

    private void m3834o() {
        List b = this.f3855b.m3864b();
        if (b != null) {
            this.f3862l = b;
        }
    }

    public void m3841b() {
        m3833n();
    }

    public void m3843c() {
        m3833n();
    }

    public void m3844d() {
        m3833n();
    }
}
