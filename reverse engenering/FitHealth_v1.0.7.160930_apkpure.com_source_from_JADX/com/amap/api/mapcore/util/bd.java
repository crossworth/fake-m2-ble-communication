package com.amap.api.mapcore.util;

import android.content.Context;
import android.view.MotionEvent;
import java.lang.reflect.Method;

/* compiled from: MultiTouchSupport */
public class bd {
    protected Method f243a;
    protected Method f244b;
    protected Method f245c;
    private boolean f246d = false;
    private final C0204a f247e;
    private long f248f = 0;
    private boolean f249g = false;

    /* compiled from: MultiTouchSupport */
    public interface C0204a {
        void mo1484a();

        void mo1485a(float f, float f2, float f3, float f4, float f5);

        boolean mo1486a(MotionEvent motionEvent, float f, float f2, float f3, float f4);
    }

    public bd(Context context, C0204a c0204a) {
        this.f247e = c0204a;
        m264c();
    }

    public boolean m265a() {
        return this.f249g;
    }

    public long m267b() {
        return this.f248f;
    }

    private void m264c() {
        try {
            this.f243a = MotionEvent.class.getMethod("getPointerCount", new Class[0]);
            this.f244b = MotionEvent.class.getMethod("getX", new Class[]{Integer.TYPE});
            this.f245c = MotionEvent.class.getMethod("getY", new Class[]{Integer.TYPE});
            this.f246d = true;
        } catch (Throwable th) {
            this.f246d = false;
            ee.m4243a(th, "MultiTouchSupport", "initMethods");
            th.printStackTrace();
        }
    }

    public boolean m266a(MotionEvent motionEvent) {
        if (!this.f246d) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        try {
            if (((Integer) this.f243a.invoke(motionEvent, new Object[0])).intValue() < 2) {
                this.f248f = 0;
                this.f249g = false;
                return false;
            }
            Float f = (Float) this.f244b.invoke(motionEvent, new Object[]{Integer.valueOf(0)});
            Float f2 = (Float) this.f244b.invoke(motionEvent, new Object[]{Integer.valueOf(1)});
            Float f3 = (Float) this.f245c.invoke(motionEvent, new Object[]{Integer.valueOf(0)});
            Float f4 = (Float) this.f245c.invoke(motionEvent, new Object[]{Integer.valueOf(1)});
            float sqrt = (float) Math.sqrt((double) (((f2.floatValue() - f.floatValue()) * (f2.floatValue() - f.floatValue())) + ((f4.floatValue() - f3.floatValue()) * (f4.floatValue() - f3.floatValue()))));
            if (action == 5) {
                this.f247e.mo1485a(sqrt, f.floatValue(), f3.floatValue(), f2.floatValue(), f4.floatValue());
                this.f249g = true;
                return true;
            } else if (action == 6) {
                this.f248f = motionEvent.getEventTime();
                if (motionEvent.getPointerCount() == 2 && this.f248f - motionEvent.getDownTime() < 100) {
                    this.f247e.mo1484a();
                }
                if (this.f249g) {
                    this.f249g = false;
                }
                return false;
            } else {
                if (this.f249g && action == 2) {
                    return this.f247e.mo1486a(motionEvent, f.floatValue(), f3.floatValue(), f2.floatValue(), f4.floatValue());
                }
                return false;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "MultiTouchSupport", "onTouchEvent");
            th.printStackTrace();
        }
    }
}
