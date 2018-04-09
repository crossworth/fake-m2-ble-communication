package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0919a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MemoCache */
public class ak {
    private List<ai> f3513a = new ArrayList();
    private Context f3514b = null;

    public ak(Context context) {
        this.f3514b = context;
    }

    public Context m3433a() {
        return this.f3514b;
    }

    protected boolean m3436a(int i) {
        return true;
    }

    public synchronized int m3437b() {
        int size;
        size = this.f3513a.size();
        if (av.f3691c != 0) {
            size++;
        }
        return size;
    }

    public synchronized void m3434a(ai aiVar) {
        this.f3513a.add(aiVar);
    }

    public void m3435a(av avVar) {
        if (ar.m3469g(this.f3514b) != null) {
            m3438b(avVar);
            m3432c(avVar);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3432c(p031u.aly.av r7) {
        /*
        r6 = this;
        r5 = 1;
        r4 = 0;
        monitor-enter(r6);
        r0 = r6.f3513a;	 Catch:{ all -> 0x0019 }
        r1 = r0.iterator();	 Catch:{ all -> 0x0019 }
    L_0x0009:
        r0 = r1.hasNext();	 Catch:{ all -> 0x0019 }
        if (r0 == 0) goto L_0x001c;
    L_0x000f:
        r0 = r1.next();	 Catch:{ all -> 0x0019 }
        r0 = (p031u.aly.ai) r0;	 Catch:{ all -> 0x0019 }
        r0.mo2760a(r7);	 Catch:{ all -> 0x0019 }
        goto L_0x0009;
    L_0x0019:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        throw r0;
    L_0x001c:
        r0 = r6.f3514b;	 Catch:{ all -> 0x0019 }
        r0 = p031u.aly.ap.m3451a(r0);	 Catch:{ all -> 0x0019 }
        if (r0 != 0) goto L_0x0026;
    L_0x0024:
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
    L_0x0025:
        return;
    L_0x0026:
        r1 = "userlevel";
        r2 = "";
        r0 = r0.getString(r1, r2);	 Catch:{ all -> 0x0019 }
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0019 }
        if (r1 != 0) goto L_0x0038;
    L_0x0034:
        r1 = r7.f3694b;	 Catch:{ all -> 0x0019 }
        r1.f3645j = r0;	 Catch:{ all -> 0x0019 }
    L_0x0038:
        r0 = r6.f3513a;	 Catch:{ all -> 0x0019 }
        r0.clear();	 Catch:{ all -> 0x0019 }
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        r0 = p031u.aly.av.f3691c;
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 == 0) goto L_0x004e;
    L_0x0046:
        r0 = r7.f3694b;
        r0 = r0.f3639d;
        r2 = p031u.aly.av.f3691c;
        r0.f3602a = r2;
    L_0x004e:
        r0 = r6.f3514b;
        r0 = p031u.aly.C1513m.m3810a(r0);
        r0.m3836a(r7);
        r0 = r6.f3514b;
        r0 = com.umeng.analytics.C0922e.m3073a(r0);
        if (r0 == 0) goto L_0x007f;
    L_0x005f:
        r1 = r0[r4];
        r1 = android.text.TextUtils.isEmpty(r1);
        if (r1 != 0) goto L_0x007f;
    L_0x0067:
        r1 = r0[r5];
        r1 = android.text.TextUtils.isEmpty(r1);
        if (r1 != 0) goto L_0x007f;
    L_0x006f:
        r1 = r7.f3694b;
        r1 = r1.f3642g;
        r2 = r0[r4];
        r1.f3604a = r2;
        r1 = r7.f3694b;
        r1 = r1.f3642g;
        r0 = r0[r5];
        r1.f3605b = r0;
    L_0x007f:
        r0 = r6.f3514b;
        r0 = p031u.aly.aw.m5107a(r0);
        r0.m5113a(r7);
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: u.aly.ak.c(u.aly.av):void");
    }

    void m3438b(av avVar) {
        String[] m;
        avVar.f3693a.f3662a = AnalyticsConfig.getAppkey(this.f3514b);
        avVar.f3693a.f3663b = AnalyticsConfig.getChannel(this.f3514b);
        avVar.f3693a.f3664c = bk.m3557a(AnalyticsConfig.getSecretKey(this.f3514b));
        avVar.f3693a.f3674m = AnalyticsConfig.getVerticalType(this.f3514b);
        avVar.f3693a.f3673l = AnalyticsConfig.getSDKVersion(this.f3514b);
        avVar.f3693a.f3666e = bj.m3552z(this.f3514b);
        int parseInt = Integer.parseInt(bj.m3526c(this.f3514b));
        String d = bj.m3528d(this.f3514b);
        SharedPreferences a = ap.m3451a(this.f3514b);
        if (a == null) {
            avVar.f3693a.f3669h = parseInt;
            avVar.f3693a.f3665d = d;
        } else {
            avVar.f3693a.f3669h = a.getInt(C0919a.f3128y, 0);
            avVar.f3693a.f3665d = a.getString(C0919a.f3129z, "");
        }
        avVar.f3693a.f3667f = bj.m3500A(this.f3514b);
        avVar.f3693a.f3668g = bj.m3503D(this.f3514b);
        if (!(AnalyticsConfig.mWrapperType == null || AnalyticsConfig.mWrapperVersion == null)) {
            avVar.f3693a.f3670i = AnalyticsConfig.mWrapperType;
            avVar.f3693a.f3671j = AnalyticsConfig.mWrapperVersion;
        }
        avVar.f3693a.f3681t = bj.m3531f(this.f3514b);
        avVar.f3693a.f3675n = bj.m3533g(this.f3514b);
        avVar.f3693a.f3680s = bj.m3547u(this.f3514b);
        avVar.f3693a.f3648B = bj.m3504E(this.f3514b);
        avVar.f3693a.f3649C = bj.m3505F(this.f3514b);
        int[] w = bj.m3549w(this.f3514b);
        if (w != null) {
            avVar.f3693a.f3679r = w[1] + "*" + w[0];
        }
        if (AnalyticsConfig.GPU_RENDERER == null || AnalyticsConfig.GPU_VENDER != null) {
            m = bj.m3539m(this.f3514b);
        } else {
            m = bj.m3539m(this.f3514b);
        }
        if (bj.f3709d.equals(m[0])) {
            avVar.f3693a.f3654H = "wifi";
        } else if (bj.f3708c.equals(m[0])) {
            avVar.f3693a.f3654H = bj.f3708c;
        } else {
            avVar.f3693a.f3654H = "unknow";
        }
        if (!"".equals(m[1])) {
            avVar.f3693a.f3655I = m[1];
        }
        Object h = bj.m3534h(this.f3514b);
        if (!TextUtils.isEmpty(h)) {
            avVar.f3693a.f3656J = h;
        }
        avVar.f3693a.f3653G = bj.m3537k(this.f3514b);
        m = bj.m3544r(this.f3514b);
        avVar.f3693a.f3652F = m[0];
        avVar.f3693a.f3651E = m[1];
        avVar.f3693a.f3650D = (long) bj.m3542p(this.f3514b);
        as.m5088a(this.f3514b, avVar);
        try {
            bp b = C1525v.m3913a(this.f3514b).m3920b();
            if (b != null) {
                byte[] a2 = new by().m3669a(b);
                avVar.f3693a.f3661O = Base64.encodeToString(a2, 0);
                try {
                    b = C1527x.m3942a(this.f3514b).m3947a();
                    if (b == null) {
                        bl.m3594e("trans the imprint is null");
                        return;
                    }
                    a2 = new by().m3669a(b);
                    avVar.f3693a.f3660N = Base64.encodeToString(a2, 0);
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        }
    }
}
