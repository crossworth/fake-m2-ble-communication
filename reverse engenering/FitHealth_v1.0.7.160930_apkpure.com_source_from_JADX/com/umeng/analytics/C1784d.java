package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;
import com.umeng.analytics.social.C0942e;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import p031u.aly.C1513m;
import p031u.aly.C1950f;
import p031u.aly.ae;
import p031u.aly.af;
import p031u.aly.ag;
import p031u.aly.ai;
import p031u.aly.an;
import p031u.aly.ap;
import p031u.aly.ar;
import p031u.aly.at;
import p031u.aly.av.C1921i;
import p031u.aly.bj;
import p031u.aly.bl;

/* compiled from: InternalAgent */
public class C1784d implements an {
    private Context f4767a = null;
    private C0921c f4768b;
    private af f4769c = new af();
    private at f4770d = new at();
    private ar f4771e = new ar();
    private ag f4772f;
    private ae f4773g;
    private C1513m f4774h = null;
    private boolean f4775i = false;
    private boolean f4776j = false;

    /* compiled from: InternalAgent */
    class C17791 extends C0924g {
        final /* synthetic */ C1784d f4758a;

        /* compiled from: InternalAgent */
        class C20081 extends C1950f {
            final /* synthetic */ C17791 f5436a;

            C20081(C17791 c17791) {
                this.f5436a = c17791;
            }

            public void mo2823a(Object obj, boolean z) {
                this.f5436a.f4758a.f4776j = true;
            }
        }

        C17791(C1784d c1784d) {
            this.f4758a = c1784d;
        }

        public void mo2152a() {
            this.f4758a.f4774h.m3837a(new C20081(this));
        }
    }

    /* compiled from: InternalAgent */
    class C17835 extends C0924g {
        final /* synthetic */ C1784d f4766a;

        C17835(C1784d c1784d) {
            this.f4766a = c1784d;
        }

        public void mo2152a() {
            String[] a = C0922e.m3073a(this.f4766a.f4767a);
            if (a != null && !TextUtils.isEmpty(a[0]) && !TextUtils.isEmpty(a[1])) {
                boolean e = this.f4766a.m4932a().m3474e(this.f4766a.f4767a);
                ae.m5075a(this.f4766a.f4767a).mo2752c();
                if (e) {
                    this.f4766a.m4932a().m3475f(this.f4766a.f4767a);
                }
                C0922e.m3074b(this.f4766a.f4767a);
            }
        }
    }

    C1784d() {
        this.f4769c.m3413a((an) this);
    }

    private void m4929e(Context context) {
        if (!this.f4775i) {
            this.f4767a = context.getApplicationContext();
            this.f4772f = new ag(this.f4767a);
            this.f4773g = ae.m5075a(this.f4767a);
            this.f4775i = true;
            if (this.f4774h == null) {
                this.f4774h = C1513m.m3810a(this.f4767a);
            }
            if (!this.f4776j) {
                C0923f.m3078b(new C17791(this));
            }
        }
    }

    void m4946a(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                this.f4770d.m3479a(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void m4955b(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                this.f4770d.m3480b(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void m4945a(C0921c c0921c) {
        this.f4768b = c0921c;
    }

    public void m4936a(Context context, int i) {
        AnalyticsConfig.m3052a(context, i);
    }

    public void m4947a(String str, String str2) {
        AnalyticsConfig.mWrapperType = str;
        AnalyticsConfig.mWrapperVersion = str2;
    }

    void m4935a(final Context context) {
        if (context == null) {
            bl.m3594e("unexpected null context in onResume");
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            this.f4770d.m3479a(context.getClass().getName());
        }
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            C0923f.m3076a(new C0924g(this) {
                final /* synthetic */ C1784d f4760b;

                public void mo2152a() {
                    this.f4760b.m4930f(context.getApplicationContext());
                }
            });
        } catch (Throwable e) {
            bl.m3596e("Exception occurred in Mobclick.onResume(). ", e);
        }
    }

    void m4953b(final Context context) {
        if (context == null) {
            bl.m3594e("unexpected null context in onPause");
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            this.f4770d.m3480b(context.getClass().getName());
        }
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            C0923f.m3076a(new C0924g(this) {
                final /* synthetic */ C1784d f4762b;

                public void mo2152a() {
                    this.f4762b.m4931g(context.getApplicationContext());
                    this.f4762b.f4774h.m3844d();
                }
            });
        } catch (Throwable e) {
            bl.m3596e("Exception occurred in Mobclick.onRause(). ", e);
        }
    }

    public ar m4932a() {
        return this.f4771e;
    }

    public void m4940a(Context context, String str, HashMap<String, Object> hashMap) {
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            this.f4772f.m3419a(str, hashMap);
        } catch (Throwable e) {
            bl.m3598e(e);
        }
    }

    void m4938a(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            if (context == null) {
                bl.m3594e("unexpected null context in reportError");
                return;
            }
            try {
                if (!this.f4775i) {
                    m4929e(context);
                }
                ai c1921i = new C1921i();
                c1921i.f4915a = System.currentTimeMillis();
                c1921i.f4916b = 2;
                c1921i.f4917c = str;
                this.f4773g.mo2748a(c1921i);
            } catch (Throwable e) {
                bl.m3598e(e);
            }
        }
    }

    void m4942a(Context context, Throwable th) {
        if (context != null && th != null) {
            try {
                m4938a(context, C0920b.m3062a(th));
            } catch (Throwable e) {
                bl.m3598e(e);
            }
        }
    }

    private void m4930f(Context context) {
        this.f4771e.m3472c(context);
        if (this.f4768b != null) {
            this.f4768b.mo2154a();
        }
    }

    private void m4931g(Context context) {
        this.f4771e.m3473d(context);
        this.f4770d.m3478a(context);
        if (this.f4768b != null) {
            this.f4768b.mo2155b();
        }
        this.f4773g.mo2750b();
    }

    void m4958c(Context context) {
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            this.f4773g.mo2747a();
        } catch (Throwable e) {
            bl.m3598e(e);
        }
    }

    public void m4943a(Context context, List<String> list, int i, String str) {
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            this.f4772f.m3421a((List) list, i, str);
        } catch (Throwable e) {
            bl.m3598e(e);
        }
    }

    public void m4939a(Context context, String str, String str2, long j, int i) {
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            this.f4772f.m3418a(str, str2, j, i);
        } catch (Throwable e) {
            bl.m3598e(e);
        }
    }

    void m4941a(Context context, String str, Map<String, Object> map, long j) {
        try {
            if (!this.f4775i) {
                m4929e(context);
            }
            this.f4772f.m3420a(str, (Map) map, j);
        } catch (Throwable e) {
            bl.m3598e(e);
        }
    }

    void m4960d(Context context) {
        try {
            this.f4770d.m3477a();
            m4931g(context);
            ap.m3451a(context).edit().commit();
            this.f4774h.m3841b();
            C0923f.m3075a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mo2153a(Throwable th) {
        try {
            this.f4770d.m3477a();
            if (this.f4767a != null) {
                if (!(th == null || this.f4773g == null)) {
                    ai c1921i = new C1921i();
                    c1921i.f4915a = System.currentTimeMillis();
                    c1921i.f4916b = 1;
                    c1921i.f4917c = C0920b.m3062a(th);
                    this.f4773g.mo2748a(c1921i);
                }
                this.f4774h.m3843c();
                m4931g(this.f4767a);
                ap.m3451a(this.f4767a).edit().commit();
            }
            C0923f.m3075a();
        } catch (Throwable e) {
            bl.m3596e("Exception in onAppCrash", e);
        }
    }

    void m4956b(final String str, final String str2) {
        try {
            C0923f.m3076a(new C0924g(this) {
                final /* synthetic */ C1784d f4765c;

                public void mo2152a() {
                    String[] a = C0922e.m3073a(this.f4765c.f4767a);
                    if (a == null || !str.equals(a[0]) || !str2.equals(a[1])) {
                        boolean e = this.f4765c.m4932a().m3474e(this.f4765c.f4767a);
                        ae.m5075a(this.f4765c.f4767a).mo2752c();
                        if (e) {
                            this.f4765c.m4932a().m3475f(this.f4765c.f4767a);
                        }
                        C0922e.m3072a(this.f4765c.f4767a, str, str2);
                    }
                }
            });
        } catch (Throwable e) {
            bl.m3596e(" Excepthon  in  onProfileSignIn", e);
        }
    }

    void m4951b() {
        try {
            C0923f.m3076a(new C17835(this));
        } catch (Throwable e) {
            bl.m3596e(" Excepthon  in  onProfileSignOff", e);
        }
    }

    void m4950a(boolean z) {
        AnalyticsConfig.CATCH_EXCEPTION = z;
    }

    void m4957b(boolean z) {
        AnalyticsConfig.ENABLE_MEMORY_BUFFER = z;
    }

    void m4949a(GL10 gl10) {
        String[] a = bj.m3520a(gl10);
        if (a.length == 2) {
            AnalyticsConfig.GPU_VENDER = a[0];
            AnalyticsConfig.GPU_RENDERER = a[1];
        }
    }

    void m4959c(boolean z) {
        AnalyticsConfig.ACTIVITY_DURATION_OPEN = z;
    }

    void m4961d(boolean z) {
        C0919a.f3108e = z;
    }

    void m4962e(boolean z) {
        bl.f3714a = z;
        C0942e.f3211v = z;
    }

    void m4963f(boolean z) {
        AnalyticsConfig.m3055a(z);
    }

    void m4933a(double d, double d2) {
        if (AnalyticsConfig.f3085a == null) {
            AnalyticsConfig.f3085a = new double[2];
        }
        AnalyticsConfig.f3085a[0] = d;
        AnalyticsConfig.f3085a[1] = d2;
    }

    void m4934a(long j) {
        AnalyticsConfig.sLatentWindow = ((int) j) * 1000;
    }

    void m4937a(Context context, EScenarioType eScenarioType) {
        if (context != null) {
            this.f4767a = context.getApplicationContext();
        }
        if (eScenarioType != null) {
            m4936a(context, eScenarioType.toValue());
        }
    }

    void m4954b(Context context, String str) {
        if (context != null) {
            this.f4767a = context.getApplicationContext();
        }
        AnalyticsConfig.m3056b(context, str);
    }

    void m4944a(UMAnalyticsConfig uMAnalyticsConfig) {
        if (uMAnalyticsConfig.mContext != null) {
            this.f4767a = uMAnalyticsConfig.mContext.getApplicationContext();
        }
        if (TextUtils.isEmpty(uMAnalyticsConfig.mAppkey)) {
            bl.m3594e("the appkey is null!");
            return;
        }
        AnalyticsConfig.m3053a(uMAnalyticsConfig.mContext, uMAnalyticsConfig.mAppkey);
        if (!TextUtils.isEmpty(uMAnalyticsConfig.mChannelId)) {
            AnalyticsConfig.m3054a(uMAnalyticsConfig.mChannelId);
        }
        AnalyticsConfig.CATCH_EXCEPTION = uMAnalyticsConfig.mIsCrashEnable;
        m4937a(this.f4767a, uMAnalyticsConfig.mType);
    }

    void m4952b(long j) {
        AnalyticsConfig.kContinueSessionMillis = j;
    }
}
