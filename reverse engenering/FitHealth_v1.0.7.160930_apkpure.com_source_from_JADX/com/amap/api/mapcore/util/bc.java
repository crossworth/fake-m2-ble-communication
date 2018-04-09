package com.amap.api.mapcore.util;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/* compiled from: BaseGestureDetector */
public abstract class bc {
    protected final Context f236a;
    protected boolean f237b;
    protected MotionEvent f238c;
    protected MotionEvent f239d;
    protected float f240e;
    protected float f241f;
    protected long f242g;

    protected abstract void mo2984a(int i, MotionEvent motionEvent);

    protected abstract void mo2985b(int i, MotionEvent motionEvent);

    public bc(Context context) {
        this.f236a = context;
    }

    public boolean m257a(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.f237b) {
            mo2985b(action, motionEvent);
        } else {
            mo2984a(action, motionEvent);
        }
        return true;
    }

    protected void mo1468b(MotionEvent motionEvent) {
        if (this.f238c != null) {
            MotionEvent motionEvent2 = this.f238c;
            if (this.f239d != null) {
                this.f239d.recycle();
                this.f239d = null;
            }
            this.f239d = MotionEvent.obtain(motionEvent);
            this.f242g = motionEvent.getEventTime() - motionEvent2.getEventTime();
            this.f240e = motionEvent.getPressure(m260c(motionEvent));
            this.f241f = motionEvent2.getPressure(m260c(motionEvent2));
        }
    }

    public final int m260c(MotionEvent motionEvent) {
        return (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    protected void mo2983a() {
        if (this.f238c != null) {
            this.f238c.recycle();
            this.f238c = null;
        }
        if (this.f239d != null) {
            this.f239d.recycle();
            this.f239d = null;
        }
        this.f237b = false;
    }
}
