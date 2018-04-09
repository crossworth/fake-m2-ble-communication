package p031u.aly;

import android.content.Context;
import com.umeng.analytics.C0923f;
import com.umeng.analytics.C0924g;

/* compiled from: CacheService */
public final class ae implements ah {
    private static ae f4894c;
    private ah f4895a = new ad(this.f4896b);
    private Context f4896b;

    /* compiled from: CacheService */
    class C19162 extends C0924g {
        final /* synthetic */ ae f4891a;

        C19162(ae aeVar) {
            this.f4891a = aeVar;
        }

        public void mo2152a() {
            this.f4891a.f4895a.mo2747a();
        }
    }

    /* compiled from: CacheService */
    class C19173 extends C0924g {
        final /* synthetic */ ae f4892a;

        C19173(ae aeVar) {
            this.f4892a = aeVar;
        }

        public void mo2152a() {
            this.f4892a.f4895a.mo2750b();
        }
    }

    /* compiled from: CacheService */
    class C19184 extends C0924g {
        final /* synthetic */ ae f4893a;

        C19184(ae aeVar) {
            this.f4893a = aeVar;
        }

        public void mo2152a() {
            this.f4893a.f4895a.mo2752c();
        }
    }

    private ae(Context context) {
        this.f4896b = context.getApplicationContext();
    }

    public static synchronized ae m5075a(Context context) {
        ae aeVar;
        synchronized (ae.class) {
            if (f4894c == null && context != null) {
                f4894c = new ae(context);
            }
            aeVar = f4894c;
        }
        return aeVar;
    }

    public void m5078a(ah ahVar) {
        this.f4895a = ahVar;
    }

    public void mo2748a(final ai aiVar) {
        C0923f.m3078b(new C0924g(this) {
            final /* synthetic */ ae f4890b;

            public void mo2152a() {
                this.f4890b.f4895a.mo2748a(aiVar);
            }
        });
    }

    public void mo2751b(ai aiVar) {
        this.f4895a.mo2751b(aiVar);
    }

    public void mo2747a() {
        C0923f.m3078b(new C19162(this));
    }

    public void mo2750b() {
        C0923f.m3078b(new C19173(this));
    }

    public void mo2752c() {
        C0923f.m3079c(new C19184(this));
    }
}
