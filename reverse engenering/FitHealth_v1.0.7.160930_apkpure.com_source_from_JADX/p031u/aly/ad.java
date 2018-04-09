package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0920b;
import com.umeng.analytics.C0923f;
import com.umeng.analytics.C0924g;
import com.umeng.analytics.C0934h;
import com.umeng.analytics.ReportPolicy.C0918i;
import com.umeng.analytics.ReportPolicy.C1769a;
import com.umeng.analytics.ReportPolicy.C1770b;
import com.umeng.analytics.ReportPolicy.C1771c;
import com.umeng.analytics.ReportPolicy.C1772d;
import com.umeng.analytics.ReportPolicy.C1773e;
import com.umeng.analytics.ReportPolicy.C1775g;
import com.umeng.analytics.ReportPolicy.C1776h;
import com.umeng.analytics.ReportPolicy.C1777j;
import com.umeng.analytics.ReportPolicy.C1778k;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.util.List;
import p031u.aly.C1527x.C1526a;
import p031u.aly.av.C1468h;
import p031u.aly.av.C1923o;

/* compiled from: CacheImpl */
public final class ad implements ah, ao {
    private static Context f4874o;
    private final long f4875a = PromConstants.PROM_SHOW_NOTIFY_INTERVAL;
    private final int f4876b = 5000;
    private ak f4877c = null;
    private C0934h f4878d = null;
    private as f4879e = null;
    private ax f4880f = null;
    private aw f4881g = null;
    private ay f4882h = null;
    private C1460a f4883i = null;
    private C1526a f4884j = null;
    private int f4885k = 10;
    private long f4886l = 0;
    private int f4887m = 0;
    private int f4888n = 0;

    /* compiled from: CacheImpl */
    public class C1460a {
        final /* synthetic */ ad f3500a;
        private C0918i f3501b;
        private int f3502c = -1;
        private int f3503d = -1;
        private int f3504e = -1;
        private int f3505f = -1;

        public C1460a(ad adVar) {
            this.f3500a = adVar;
            int[] a = adVar.f4884j.m3932a(-1, -1);
            this.f3502c = a[0];
            this.f3503d = a[1];
        }

        protected void m3410a(boolean z) {
            int i = 1;
            int i2 = 0;
            if (this.f3500a.f4880f.m5126c()) {
                C0918i c0918i;
                if (!((this.f3501b instanceof C1770b) && this.f3501b.mo2151a())) {
                    i = 0;
                }
                if (i != 0) {
                    c0918i = this.f3501b;
                } else {
                    c0918i = new C1770b(this.f3500a.f4879e, this.f3500a.f4880f);
                }
                this.f3501b = c0918i;
            } else {
                if (!((this.f3501b instanceof C1771c) && this.f3501b.mo2151a())) {
                    i = 0;
                }
                if (i == 0) {
                    if (z && this.f3500a.f4882h.m5129a()) {
                        this.f3501b = new C1771c((int) this.f3500a.f4882h.m5130b());
                        this.f3500a.m5053b((int) this.f3500a.f4882h.m5130b());
                    } else if (bl.f3714a && this.f3500a.f4884j.m3935b()) {
                        bl.m3576b("Debug: send log every 15 seconds");
                        this.f3501b = new C1769a(this.f3500a.f4879e);
                    } else if (this.f3500a.f4881g.m5115a()) {
                        bl.m3576b("Start A/B Test");
                        if (this.f3500a.f4881g.m5116b() == 6) {
                            if (this.f3500a.f4884j.m3931a()) {
                                i2 = this.f3500a.f4884j.m3938d(90000);
                            } else if (this.f3503d > 0) {
                                i2 = this.f3503d;
                            } else {
                                i2 = this.f3505f;
                            }
                        }
                        this.f3501b = m3407b(this.f3500a.f4881g.m5116b(), i2);
                    } else {
                        i = this.f3504e;
                        i2 = this.f3505f;
                        if (this.f3502c != -1) {
                            i = this.f3502c;
                            i2 = this.f3503d;
                        }
                        this.f3501b = m3407b(i, i2);
                    }
                }
            }
            bl.m3576b("Report policy : " + this.f3501b.getClass().getSimpleName());
        }

        public C0918i m3411b(boolean z) {
            m3410a(z);
            return this.f3501b;
        }

        private C0918i m3407b(int i, int i2) {
            switch (i) {
                case 0:
                    return this.f3501b instanceof C1776h ? this.f3501b : new C1776h();
                case 1:
                    return this.f3501b instanceof C1772d ? this.f3501b : new C1772d();
                case 4:
                    if (this.f3501b instanceof C1775g) {
                        return this.f3501b;
                    }
                    return new C1775g(this.f3500a.f4879e);
                case 5:
                    if (this.f3501b instanceof C1777j) {
                        return this.f3501b;
                    }
                    return new C1777j(ad.f4874o);
                case 6:
                    if (!(this.f3501b instanceof C1773e)) {
                        return new C1773e(this.f3500a.f4879e, (long) i2);
                    }
                    C0918i c0918i = this.f3501b;
                    ((C1773e) c0918i).m4911a((long) i2);
                    return c0918i;
                case 8:
                    if (this.f3501b instanceof C1778k) {
                        return this.f3501b;
                    }
                    return new C1778k(this.f3500a.f4879e);
                default:
                    if (this.f3501b instanceof C1772d) {
                        return this.f3501b;
                    }
                    return new C1772d();
            }
        }

        public void m3408a(int i, int i2) {
            this.f3504e = i;
            this.f3505f = i2;
        }

        public void m3409a(C1526a c1526a) {
            int[] a = c1526a.m3932a(-1, -1);
            this.f3502c = a[0];
            this.f3503d = a[1];
        }
    }

    /* compiled from: CacheImpl */
    class C19141 extends C0924g {
        final /* synthetic */ ad f4873a;

        C19141(ad adVar) {
            this.f4873a = adVar;
        }

        public void mo2152a() {
            this.f4873a.mo2747a();
        }
    }

    public ad(Context context) {
        f4874o = context;
        this.f4877c = new ak(context);
        this.f4879e = new as(context);
        this.f4878d = C0934h.m3100a(context);
        this.f4884j = C1527x.m3942a(context).m3950b();
        this.f4883i = new C1460a(this);
        this.f4881g = aw.m5107a(f4874o);
        this.f4880f = ax.m5119a(f4874o);
        this.f4882h = ay.m5127a(f4874o, this.f4879e);
        SharedPreferences a = ap.m3451a(f4874o);
        this.f4886l = a.getLong("thtstart", 0);
        this.f4887m = a.getInt("gkvc", 0);
        this.f4888n = a.getInt("ekvc", 0);
    }

    public void mo2747a() {
        if (bj.m3541o(f4874o)) {
            m5062f();
        } else {
            bl.m3576b("network is unavailable");
        }
    }

    public void mo2748a(ai aiVar) {
        if (aiVar != null) {
            this.f4877c.m3434a(aiVar);
        }
        m5051a(aiVar instanceof C1923o);
    }

    public void mo2751b(ai aiVar) {
        this.f4877c.m3434a(aiVar);
    }

    public void mo2750b() {
        if (this.f4877c.m3437b() > 0) {
            try {
                this.f4878d.m3113a(m5064a(new int[0]));
            } catch (Throwable th) {
                bl.m3598e(th);
                if (th instanceof OutOfMemoryError) {
                    this.f4878d.m3125h();
                }
                if (th != null) {
                    th.printStackTrace();
                }
            }
        }
        ap.m3451a(f4874o).edit().putLong("thtstart", this.f4886l).putInt("gkvc", this.f4887m).putInt("ekvc", this.f4888n).commit();
    }

    public void mo2752c() {
        m5050a(m5064a(new int[0]));
    }

    private void m5051a(boolean z) {
        boolean f = this.f4879e.m5094f();
        if (f) {
            av.f3691c = this.f4879e.m5102n();
        }
        if (m5055b(z)) {
            m5062f();
        } else if (f || m5061e()) {
            mo2750b();
        }
    }

    private void m5047a(int i) {
        int currentTimeMillis = (int) (System.currentTimeMillis() - this.f4879e.m5103o());
        m5050a(m5064a(i, currentTimeMillis));
        C0923f.m3077a(new C19141(this), (long) i);
    }

    private void m5050a(av avVar) {
        if (avVar != null) {
            try {
                byte[] a;
                C1525v a2 = C1525v.m3913a(f4874o);
                a2.m3917a();
                try {
                    a = new by().m3669a(a2.m3920b());
                    avVar.f3693a.f3661O = Base64.encodeToString(a, 0);
                } catch (Exception e) {
                }
                a = C0934h.m3100a(f4874o).m3118b(m5057c(avVar));
                if (a != null && !C0920b.m3064a(f4874o, a)) {
                    C1523t b;
                    if (m5063g()) {
                        b = C1523t.m3898b(f4874o, AnalyticsConfig.getAppkey(f4874o), a);
                    } else {
                        b = C1523t.m3896a(f4874o, AnalyticsConfig.getAppkey(f4874o), a);
                    }
                    a = b.m3907c();
                    C0934h a3 = C0934h.m3100a(f4874o);
                    a3.m3125h();
                    a3.m3114a(a);
                    a2.m3922d();
                    av.f3691c = 0;
                }
            } catch (Exception e2) {
            }
        }
    }

    protected av m5064a(int... iArr) {
        Object obj = null;
        try {
            if (TextUtils.isEmpty(AnalyticsConfig.getAppkey(f4874o))) {
                bl.m3594e("Appkey is missing ,Please check AndroidManifest.xml");
                return null;
            }
            av g = C0934h.m3100a(f4874o).m3124g();
            if (g == null && this.f4877c.m3437b() == 0) {
                return null;
            }
            if (g == null) {
                g = new av();
            }
            this.f4877c.m3435a(g);
            if (g.f3694b.f3638c != null && bl.f3714a && g.f3694b.f3638c.size() > 0) {
                for (C1923o c1923o : g.f3694b.f3638c) {
                    Object obj2;
                    if (c1923o.f4933h.size() > 0) {
                        obj2 = 1;
                    } else {
                        obj2 = obj;
                    }
                    obj = obj2;
                }
                if (obj == null) {
                    bl.m3588d("missing Activities or PageViews");
                }
            }
            this.f4880f.m5122a(g, f4874o);
            if (iArr != null && iArr.length == 2) {
                g.f3694b.f3640e.f3620a = Integer.valueOf(iArr[0] / 1000);
                g.f3694b.f3640e.f3621b = (long) iArr[1];
                g.f3694b.f3640e.f3622c = true;
            }
            return g;
        } catch (Throwable e) {
            bl.m3596e("Fail to construct message ...", e);
            C0934h.m3100a(f4874o).m3125h();
            bl.m3598e(e);
            return null;
        }
    }

    private boolean m5055b(boolean z) {
        if (!bj.m3541o(f4874o)) {
            bl.m3576b("network is unavailable");
            return false;
        } else if (this.f4879e.m5094f()) {
            return true;
        } else {
            return this.f4883i.m3411b(z).mo2150a(z);
        }
    }

    private boolean m5061e() {
        return this.f4877c.m3437b() > this.f4885k;
    }

    private void m5062f() {
        try {
            if (this.f4878d.m3126i()) {
                aq aqVar = new aq(f4874o, this.f4879e);
                aqVar.m3461a((ao) this);
                if (this.f4880f.m5126c()) {
                    aqVar.m3464b(true);
                }
                aqVar.m3460a();
                return;
            }
            av a = m5064a(new int[0]);
            if (m5054b(a)) {
                aq aqVar2 = new aq(f4874o, this.f4879e);
                aqVar2.m3461a((ao) this);
                if (this.f4880f.m5126c()) {
                    aqVar2.m3464b(true);
                }
                aqVar2.m3462a(m5057c(a));
                aqVar2.m3463a(m5063g());
                aqVar2.m3460a();
            }
        } catch (Throwable th) {
            if (th instanceof OutOfMemoryError) {
                if (th != null) {
                    th.printStackTrace();
                }
            } else if (th != null) {
                th.printStackTrace();
            }
        }
    }

    private boolean m5054b(av avVar) {
        if (avVar != null && avVar.m3481a()) {
            return true;
        }
        return false;
    }

    private av m5057c(av avVar) {
        int i;
        int i2;
        int i3 = 5000;
        if (avVar.f3694b.f3636a != null) {
            i = 0;
            for (i2 = 0; i2 < avVar.f3694b.f3636a.size(); i2++) {
                i += ((C1468h) avVar.f3694b.f3636a.get(i2)).f3625b.size();
            }
        } else {
            i = 0;
        }
        if (avVar.f3694b.f3637b != null) {
            for (i2 = 0; i2 < avVar.f3694b.f3637b.size(); i2++) {
                i += ((C1468h) avVar.f3694b.f3637b.get(i2)).f3625b.size();
            }
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f4886l > PromConstants.PROM_SHOW_NOTIFY_INTERVAL) {
            int i4 = i - 5000;
            if (i4 > 0) {
                m5048a(-5000, i4, avVar);
            }
            this.f4887m = 0;
            if (i4 > 0) {
                i = 5000;
            }
            this.f4888n = i;
            this.f4886l = currentTimeMillis;
        } else {
            int i5 = this.f4887m > 5000 ? 0 : (this.f4887m + 0) - 5000;
            i2 = this.f4888n > 5000 ? i : (this.f4888n + i) - 5000;
            if (i5 > 0 || i2 > 0) {
                m5048a(i5, i2, avVar);
            }
            this.f4887m = i5 > 0 ? 5000 : this.f4887m + 0;
            if (i2 <= 0) {
                i3 = this.f4888n + i;
            }
            this.f4888n = i3;
        }
        return avVar;
    }

    private void m5048a(int i, int i2, av avVar) {
        List list;
        int size;
        int size2;
        if (i > 0) {
            list = avVar.f3694b.f3637b;
            if (list.size() >= i) {
                size = list.size() - i;
                for (size2 = list.size() - 1; size2 >= size; size2--) {
                    list.remove(size2);
                }
            } else {
                size2 = i - list.size();
                list.clear();
            }
        }
        if (i2 > 0) {
            list = avVar.f3694b.f3636a;
            if (list.size() >= i2) {
                size = list.size() - i2;
                for (size2 = list.size() - 1; size2 >= size; size2--) {
                    list.remove(size2);
                }
                return;
            }
            size2 = i2 - list.size();
            list.clear();
        }
    }

    private boolean m5063g() {
        switch (this.f4884j.m3936c(-1)) {
            case -1:
                return AnalyticsConfig.sEncrypt;
            case 1:
                return true;
            default:
                return false;
        }
    }

    private void m5053b(int i) {
        m5047a(i);
    }

    public void mo2749a(C1526a c1526a) {
        this.f4881g.mo2749a(c1526a);
        this.f4880f.mo2749a(c1526a);
        this.f4882h.mo2749a(c1526a);
        this.f4883i.m3409a(c1526a);
    }
}
