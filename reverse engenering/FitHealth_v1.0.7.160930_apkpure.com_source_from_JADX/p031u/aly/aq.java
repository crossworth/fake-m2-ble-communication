package p031u.aly;

import android.content.Context;
import android.util.Base64;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0920b;
import com.umeng.analytics.C0934h;
import com.umeng.analytics.C0934h.C0933b;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import p031u.aly.ch.C1947a;

/* compiled from: Sender */
public class aq {
    private static final int f3521a = 1;
    private static final int f3522b = 2;
    private static final int f3523c = 3;
    private static Context f3524g;
    private C1525v f3525d;
    private C1527x f3526e;
    private final int f3527f = 1;
    private as f3528h;
    private al f3529i;
    private av f3530j;
    private boolean f3531k = false;
    private boolean f3532l;

    /* compiled from: Sender */
    class C19201 implements C0933b {
        final /* synthetic */ aq f4899a;

        C19201(aq aqVar) {
            this.f4899a = aqVar;
        }

        public void mo2753a(File file) {
        }

        public boolean mo2754b(File file) {
            InputStream fileInputStream;
            Throwable th;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] b = bk.m3565b(fileInputStream);
                    try {
                        int i;
                        bk.m3567c(fileInputStream);
                        byte[] a = this.f4899a.f3529i.m3444a(b);
                        if (a == null) {
                            i = 1;
                        } else {
                            i = this.f4899a.m3454a(a);
                        }
                        if (i == 2 && this.f4899a.f3528h.m5101m()) {
                            this.f4899a.f3528h.m5100l();
                        }
                        if (!this.f4899a.f3532l && i == 1) {
                            return false;
                        }
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bk.m3567c(fileInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                bk.m3567c(fileInputStream);
                throw th;
            }
        }

        public void mo2755c(File file) {
            this.f4899a.f3528h.m5099k();
        }
    }

    public aq(Context context, as asVar) {
        this.f3525d = C1525v.m3913a(context);
        this.f3526e = C1527x.m3942a(context);
        f3524g = context;
        this.f3528h = asVar;
        this.f3529i = new al(context);
        this.f3529i.m3443a(this.f3528h);
    }

    public void m3462a(av avVar) {
        this.f3530j = avVar;
    }

    public void m3463a(boolean z) {
        this.f3531k = z;
    }

    public void m3464b(boolean z) {
        this.f3532l = z;
    }

    public void m3461a(ao aoVar) {
        this.f3526e.m3948a(aoVar);
    }

    public void m3460a() {
        if (this.f3530j != null) {
            m3458c();
        } else {
            m3457b();
        }
    }

    private void m3457b() {
        C0934h.m3100a(f3524g).m3127j().m3092a(new C19201(this));
    }

    private void m3458c() {
        byte[] a;
        this.f3525d.m3917a();
        av avVar = this.f3530j;
        try {
            a = new by().m3669a(this.f3525d.m3920b());
            avVar.f3693a.f3661O = Base64.encodeToString(a, 0);
        } catch (Throwable e) {
            bl.m3598e(e);
        }
        a = C0934h.m3100a(f3524g).m3118b(avVar);
        if (!C0920b.m3064a(f3524g, a)) {
            if (a == null) {
                bl.m3594e("message is null");
                return;
            }
            C1523t b;
            int i;
            if (this.f3531k) {
                b = C1523t.m3898b(f3524g, AnalyticsConfig.getAppkey(f3524g), a);
            } else {
                b = C1523t.m3896a(f3524g, AnalyticsConfig.getAppkey(f3524g), a);
            }
            byte[] c = b.m3907c();
            C0934h.m3100a(f3524g).m3125h();
            a = this.f3529i.m3444a(c);
            if (a == null) {
                i = 1;
            } else {
                i = m3454a(a);
            }
            switch (i) {
                case 1:
                    if (!this.f3532l) {
                        C0934h.m3100a(f3524g).m3114a(c);
                        return;
                    }
                    return;
                case 2:
                    if (this.f3528h.m5101m()) {
                        this.f3528h.m5100l();
                    }
                    this.f3525d.m3922d();
                    this.f3528h.m5099k();
                    av.f3691c = 0;
                    return;
                case 3:
                    this.f3528h.m5099k();
                    return;
                default:
                    return;
            }
        }
    }

    private int m3454a(byte[] bArr) {
        bp bfVar = new bf();
        try {
            new bs(new C1947a()).m3654a(bfVar, bArr);
            if (bfVar.f5080a == 1) {
                this.f3526e.m3951b(bfVar.m5370i());
                this.f3526e.m3953d();
            }
            bl.m3582c("send log:" + bfVar.m5367f());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bfVar.f5080a == 1) {
            return 2;
        }
        return 3;
    }
}
