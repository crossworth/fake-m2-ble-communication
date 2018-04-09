package com.umeng.socialize.view.p028a;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import com.tencent.open.yyb.TitleBar;
import com.umeng.socialize.view.p028a.p030b.C0993c;
import java.util.Timer;

/* compiled from: ACProgressPie */
public class C1831i extends C0987a {
    private C1001a f4860a;
    private C0993c f4861b;
    private Timer f4862c;
    private int f4863d;

    /* compiled from: ACProgressPie */
    public static class C1001a {
        private Context f3455a;
        private float f3456b = 0.25f;
        private int f3457c = ViewCompat.MEASURED_STATE_MASK;
        private float f3458d = TitleBar.BACKBTN_LEFT_MARGIN;
        private float f3459e = 0.5f;
        private int f3460f = -1;
        private float f3461g = 0.9f;
        private float f3462h = 0.2f;
        private int f3463i = 3;
        private int f3464j = -1;
        private float f3465k = 0.9f;
        private float f3466l = 0.08f;
        private float f3467m = 6.67f;
        private int f3468n = 100;
        private int f3469o = 200;

        public C1001a(Context context) {
            this.f3455a = context;
        }

        public C1001a m3356a(float f) {
            this.f3456b = f;
            return this;
        }

        public C1001a m3357a(int i) {
            this.f3457c = i;
            return this;
        }

        public C1001a m3359b(float f) {
            this.f3459e = f;
            return this;
        }

        public C1001a m3361c(float f) {
            this.f3458d = f;
            return this;
        }

        public C1001a m3360b(int i) {
            this.f3460f = i;
            return this;
        }

        public C1001a m3363d(float f) {
            this.f3461g = f;
            return this;
        }

        public C1001a m3365e(float f) {
            this.f3462h = f;
            return this;
        }

        public C1001a m3362c(int i) {
            this.f3463i = i;
            return this;
        }

        public C1001a m3364d(int i) {
            this.f3464j = i;
            return this;
        }

        public C1001a m3367f(float f) {
            this.f3465k = f;
            return this;
        }

        public C1001a m3369g(float f) {
            this.f3466l = f;
            return this;
        }

        public C1001a m3370h(float f) {
            this.f3467m = f;
            return this;
        }

        public C1001a m3366e(int i) {
            this.f3468n = i;
            return this;
        }

        public C1001a m3368f(int i) {
            this.f3469o = i;
            return this;
        }

        public C1831i m3358a() {
            return new C1831i();
        }
    }

    private C1831i(C1001a c1001a) {
        super(c1001a.f3455a);
        this.f4863d = 0;
        this.f4860a = c1001a;
        setOnDismissListener(new C1002j(this));
    }

    public void show() {
        if (this.f4861b == null) {
            this.f4861b = new C0993c(this.f4860a.f3455a, (int) (((float) m3285a(this.f4860a.f3455a)) * this.f4860a.f3456b), this.f4860a.f3457c, this.f4860a.f3459e, this.f4860a.f3458d, this.f4860a.f3462h, this.f4860a.f3466l, this.f4860a.f3463i, this.f4860a.f3460f, this.f4860a.f3461g, this.f4860a.f3464j, this.f4860a.f3465k);
        }
        super.setContentView(this.f4861b);
        super.show();
        if (this.f4860a.f3469o == 200) {
            long n = (long) (1000.0f / this.f4860a.f3467m);
            this.f4862c = new Timer();
            this.f4862c.scheduleAtFixedRate(new C1003k(this), n, n);
        }
    }

    public void m5035a(float f) {
        if (this.f4860a.f3469o == 201 && this.f4861b != null) {
            this.f4861b.m3289a(360.0f * f);
        }
    }
}
