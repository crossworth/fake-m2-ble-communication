package p031u.aly;

import android.content.Context;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0920b;
import com.umeng.analytics.C0934h;
import p031u.aly.C1527x.C1526a;

/* compiled from: ImLatent */
public class ay implements ao {
    private static ay f4954l = null;
    private final long f4955a = 1296000000;
    private final long f4956b = 129600000;
    private final int f4957c = 1800000;
    private final int f4958d = 10000;
    private C0934h f4959e;
    private as f4960f;
    private long f4961g = 1296000000;
    private int f4962h = 10000;
    private long f4963i = 0;
    private long f4964j = 0;
    private Context f4965k;

    public static synchronized ay m5127a(Context context, as asVar) {
        ay ayVar;
        synchronized (ay.class) {
            if (f4954l == null) {
                f4954l = new ay(context, asVar);
                f4954l.mo2749a(C1527x.m3942a(context).m3950b());
            }
            ayVar = f4954l;
        }
        return ayVar;
    }

    private ay(Context context, as asVar) {
        this.f4965k = context;
        this.f4959e = C0934h.m3100a(context);
        this.f4960f = asVar;
    }

    public boolean m5129a() {
        if (this.f4959e.m3126i() || this.f4960f.m5094f()) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis() - this.f4960f.m5103o();
        if (currentTimeMillis > this.f4961g) {
            this.f4963i = (long) C0920b.m3061a(this.f4962h, C1523t.m3895a(this.f4965k));
            this.f4964j = currentTimeMillis;
            return true;
        } else if (currentTimeMillis <= 129600000) {
            return false;
        } else {
            this.f4963i = 0;
            this.f4964j = currentTimeMillis;
            return true;
        }
    }

    public long m5130b() {
        return this.f4963i;
    }

    public long m5131c() {
        return this.f4964j;
    }

    public void mo2749a(C1526a c1526a) {
        this.f4961g = c1526a.m3928a(1296000000);
        int b = c1526a.m3933b(0);
        if (b != 0) {
            this.f4962h = b;
        } else if (AnalyticsConfig.sLatentWindow <= 0 || AnalyticsConfig.sLatentWindow > 1800000) {
            this.f4962h = 10000;
        } else {
            this.f4962h = AnalyticsConfig.sLatentWindow;
        }
    }
}
