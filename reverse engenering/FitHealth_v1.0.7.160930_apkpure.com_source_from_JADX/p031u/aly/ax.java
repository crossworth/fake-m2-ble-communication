package p031u.aly;

import android.content.Context;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import p031u.aly.C1527x.C1526a;
import p031u.aly.av.C1923o;

/* compiled from: Defcon */
public class ax implements ao {
    private static final int f4944a = 0;
    private static final int f4945b = 1;
    private static final int f4946c = 2;
    private static final int f4947d = 3;
    private static final long f4948e = 14400000;
    private static final long f4949f = 28800000;
    private static final long f4950g = 86400000;
    private static ax f4951j = null;
    private int f4952h = 0;
    private final long f4953i = TimeManager.UNIT_MINUTE;

    public static synchronized ax m5119a(Context context) {
        ax axVar;
        synchronized (ax.class) {
            if (f4951j == null) {
                f4951j = new ax();
                f4951j.m5121a(C1527x.m3942a(context).m3950b().m3927a(0));
            }
            axVar = f4951j;
        }
        return axVar;
    }

    private ax() {
    }

    public void m5122a(av avVar, Context context) {
        if (this.f4952h == 1) {
            avVar.f3694b.f3644i = null;
            avVar.f3694b.f3636a = null;
            avVar.f3694b.f3637b = null;
            avVar.f3694b.f3643h = null;
        } else if (this.f4952h == 2) {
            avVar.f3694b.f3638c.clear();
            avVar.f3694b.f3638c.add(m5125b(context));
            avVar.f3694b.f3644i = null;
            avVar.f3694b.f3636a = null;
            avVar.f3694b.f3637b = null;
            avVar.f3694b.f3643h = null;
        } else if (this.f4952h == 3) {
            avVar.f3694b.f3638c = null;
            avVar.f3694b.f3644i = null;
            avVar.f3694b.f3636a = null;
            avVar.f3694b.f3637b = null;
            avVar.f3694b.f3643h = null;
        }
    }

    public C1923o m5125b(Context context) {
        C1923o c1923o = new C1923o();
        c1923o.f4928b = ar.m3469g(context);
        long currentTimeMillis = System.currentTimeMillis();
        c1923o.f4929c = currentTimeMillis;
        c1923o.f4930d = currentTimeMillis + TimeManager.UNIT_MINUTE;
        c1923o.f4931e = TimeManager.UNIT_MINUTE;
        return c1923o;
    }

    public long m5120a() {
        switch (this.f4952h) {
            case 1:
                return f4948e;
            case 2:
                return 28800000;
            case 3:
                return 86400000;
            default:
                return 0;
        }
    }

    public long m5124b() {
        return this.f4952h == 0 ? 0 : 300000;
    }

    public void m5121a(int i) {
        if (i >= 0 && i <= 3) {
            this.f4952h = i;
        }
    }

    public boolean m5126c() {
        return this.f4952h != 0;
    }

    public void mo2749a(C1526a c1526a) {
        m5121a(c1526a.m3927a(0));
    }
}
