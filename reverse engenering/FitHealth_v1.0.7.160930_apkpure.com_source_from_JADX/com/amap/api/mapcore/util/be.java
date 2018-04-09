package com.amap.api.mapcore.util;

import android.content.Context;
import android.view.MotionEvent;

/* compiled from: RotateGestureDetector */
public class be extends bf {
    private final C0205a f5338l;
    private boolean f5339m;

    /* compiled from: RotateGestureDetector */
    public interface C0205a {
        boolean mo1488a(be beVar);

        boolean mo1489b(be beVar);

        void mo1490c(be beVar);
    }

    public be(Context context, C0205a c0205a) {
        super(context);
        this.f5338l = c0205a;
    }

    protected void mo2984a(int i, MotionEvent motionEvent) {
        switch (i) {
            case 2:
                if (this.f5339m) {
                    this.f5339m = m4011d(motionEvent);
                    if (!this.f5339m) {
                        this.b = this.f5338l.mo1489b(this);
                        return;
                    }
                    return;
                }
                return;
            case 5:
                mo2983a();
                this.c = MotionEvent.obtain(motionEvent);
                this.g = 0;
                mo1468b(motionEvent);
                this.f5339m = m4011d(motionEvent);
                if (!this.f5339m) {
                    this.b = this.f5338l.mo1489b(this);
                    return;
                }
                return;
            case 6:
                if (!this.f5339m) {
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void mo2985b(int i, MotionEvent motionEvent) {
        switch (i) {
            case 2:
                mo1468b(motionEvent);
                if (this.e / this.f > 0.67f && this.f5338l.mo1488a(this) && this.c != null) {
                    this.c.recycle();
                    this.c = MotionEvent.obtain(motionEvent);
                    return;
                }
                return;
            case 3:
                if (!this.f5339m) {
                    this.f5338l.mo1490c(this);
                }
                mo2983a();
                return;
            case 6:
                mo1468b(motionEvent);
                if (!this.f5339m) {
                    this.f5338l.mo1490c(this);
                }
                mo2983a();
                return;
            default:
                return;
        }
    }

    protected void mo2983a() {
        super.mo2983a();
        this.f5339m = false;
    }

    public float m5689b() {
        return (float) (((Math.atan2((double) this.i, (double) this.h) - Math.atan2((double) this.k, (double) this.j)) * 180.0d) / 3.141592653589793d);
    }
}
