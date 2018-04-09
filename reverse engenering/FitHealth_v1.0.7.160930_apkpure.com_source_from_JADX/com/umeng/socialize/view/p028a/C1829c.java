package com.umeng.socialize.view.p028a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.umeng.socialize.view.p028a.p030b.C0989a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

/* compiled from: ACProgressCustom */
public final class C1829c extends C0987a {
    private static final int f4847a = -1;
    private static final int f4848b = 0;
    private static final int f4849c = 1;
    private C0995a f4850d;
    private C0989a f4851e;
    private Timer f4852f;
    private int f4853g;
    private int f4854h;
    private List<Bitmap> f4855i;

    /* compiled from: ACProgressCustom */
    public static class C0995a {
        private Context f3425a;
        private float f3426b = 0.2f;
        private List<Integer> f3427c = new ArrayList();
        private List<String> f3428d = new ArrayList();
        private int f3429e = -1;
        private float f3430f = 6.67f;

        public C0995a(Context context) {
            this.f3425a = context;
        }

        public C0995a m3296a(float f) {
            this.f3426b = f;
            return this;
        }

        public C0995a m3300b(float f) {
            this.f3430f = f;
            return this;
        }

        public C0995a m3297a(Integer... numArr) {
            if (!(numArr == null || numArr.length == 0)) {
                this.f3427c.clear();
                Collections.addAll(this.f3427c, numArr);
                this.f3429e = 0;
            }
            return this;
        }

        public C0995a m3298a(String... strArr) {
            if (!(strArr == null || strArr.length == 0)) {
                this.f3428d.clear();
                Collections.addAll(this.f3428d, strArr);
                this.f3429e = 1;
            }
            return this;
        }

        public C1829c m3299a() {
            return new C1829c();
        }
    }

    private C1829c(C0995a c0995a) {
        super(c0995a.f3425a);
        this.f4853g = 0;
        this.f4850d = c0995a;
        setOnDismissListener(new C0996d(this));
    }

    public void show() {
        if (this.f4850d.f3429e != -1) {
            if (this.f4851e == null) {
                this.f4855i = new ArrayList();
                int a = (int) (((float) m3285a(this.f4850d.f3425a)) * this.f4850d.f3426b);
                int i;
                if (this.f4850d.f3429e == 0) {
                    this.f4854h = this.f4850d.f3427c.size();
                    for (i = 0; i < this.f4854h; i++) {
                        this.f4855i.add(BitmapFactory.decodeResource(this.f4850d.f3425a.getResources(), ((Integer) this.f4850d.f3427c.get(i)).intValue()));
                    }
                } else {
                    this.f4854h = this.f4850d.f3428d.size();
                    for (i = 0; i < this.f4854h; i++) {
                        this.f4855i.add(BitmapFactory.decodeFile((String) this.f4850d.f3428d.get(i)));
                    }
                }
                this.f4851e = new C0989a(this.f4850d.f3425a, a, this.f4855i);
            }
            super.setContentView(this.f4851e);
            super.show();
            long f = (long) (1000.0f / this.f4850d.f3430f);
            this.f4852f = new Timer();
            this.f4852f.scheduleAtFixedRate(new C0997e(this), f, f);
            return;
        }
        Log.d(C1829c.class.toString(), "you must assign the image source in Builder");
    }
}
