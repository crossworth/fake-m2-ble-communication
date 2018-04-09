package com.umeng.socialize.view.p028a;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import com.tencent.open.yyb.TitleBar;
import com.umeng.socialize.view.p028a.p030b.C0991b;
import java.util.Timer;

/* compiled from: ACProgressFlower */
public class C1830f extends C0987a {
    private C0998a f4856a;
    private C0991b f4857b;
    private int f4858c;
    private Timer f4859d;

    /* compiled from: ACProgressFlower */
    public static class C0998a {
        private Context f3433a;
        private float f3434b = 0.25f;
        private float f3435c = 0.55f;
        private float f3436d = 0.27f;
        private int f3437e = ViewCompat.MEASURED_STATE_MASK;
        private int f3438f = -1;
        private int f3439g = -12303292;
        private int f3440h = 12;
        private int f3441i = 9;
        private float f3442j = 0.5f;
        private float f3443k = TitleBar.BACKBTN_LEFT_MARGIN;
        private float f3444l = 0.5f;
        private int f3445m = 100;
        private float f3446n = 9.0f;
        private String f3447o = null;
        private int f3448p = -1;
        private float f3449q = 0.5f;
        private float f3450r = 40.0f;
        private int f3451s = 40;
        private boolean f3452t = true;

        public C0998a(Context context) {
            this.f3433a = context;
        }

        public C0998a m3321a(float f) {
            this.f3434b = f;
            return this;
        }

        public C0998a m3326b(float f) {
            this.f3435c = f;
            return this;
        }

        public C0998a m3328c(float f) {
            this.f3436d = f;
            return this;
        }

        public C0998a m3322a(int i) {
            this.f3437e = i;
            return this;
        }

        public C0998a m3327b(int i) {
            this.f3438f = i;
            return this;
        }

        public C0998a m3329c(int i) {
            this.f3439g = i;
            return this;
        }

        public C0998a m3331d(int i) {
            this.f3440h = i;
            return this;
        }

        public C0998a m3333e(int i) {
            this.f3441i = i;
            return this;
        }

        public C0998a m3330d(float f) {
            this.f3442j = f;
            return this;
        }

        public C0998a m3332e(float f) {
            this.f3443k = f;
            return this;
        }

        public C0998a m3334f(float f) {
            this.f3444l = f;
            return this;
        }

        public C0998a m3335f(int i) {
            this.f3445m = i;
            return this;
        }

        public C0998a m3336g(float f) {
            this.f3446n = f;
            return this;
        }

        public C0998a m3323a(String str) {
            this.f3447o = str;
            return this;
        }

        public C0998a m3337g(int i) {
            this.f3450r = (float) i;
            return this;
        }

        public C0998a m3339h(int i) {
            this.f3448p = i;
            return this;
        }

        public C0998a m3338h(float f) {
            this.f3449q = f;
            return this;
        }

        public C0998a m3340i(int i) {
            this.f3451s = i;
            return this;
        }

        public C0998a m3324a(boolean z) {
            this.f3452t = z;
            return this;
        }

        public C1830f m3325a() {
            return new C1830f();
        }
    }

    private C1830f(C0998a c0998a) {
        super(c0998a.f3433a);
        this.f4858c = 0;
        this.f4856a = c0998a;
        setOnDismissListener(new C0999g(this));
    }

    public void show() {
        if (this.f4857b == null) {
            this.f4857b = new C0991b(this.f4856a.f3433a, (int) (((float) m3285a(this.f4856a.f3433a)) * this.f4856a.f3434b), this.f4856a.f3437e, this.f4856a.f3444l, this.f4856a.f3443k, this.f4856a.f3441i, this.f4856a.f3440h, this.f4856a.f3442j, this.f4856a.f3435c, this.f4856a.f3436d, this.f4856a.f3438f, this.f4856a.f3439g, this.f4856a.f3447o, this.f4856a.f3450r, this.f4856a.f3448p, this.f4856a.f3449q, this.f4856a.f3451s, this.f4856a.f3452t);
        }
        super.setContentView(this.f4857b);
        super.show();
        long s = (long) (1000.0f / this.f4856a.f3446n);
        this.f4859d = new Timer();
        this.f4859d.scheduleAtFixedRate(new C1000h(this), s, s);
    }
}
