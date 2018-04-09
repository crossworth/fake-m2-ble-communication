package com.amap.api.mapcore.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.amap.api.maps.model.GroundOverlayOptions;

/* compiled from: TwoFingerGestureDetector */
public abstract class bf extends bc {
    protected float f4039h;
    protected float f4040i;
    protected float f4041j;
    protected float f4042k;
    private final float f4043l;
    private float f4044m;
    private float f4045n;
    private float f4046o;
    private float f4047p;

    public bf(Context context) {
        super(context);
        this.f4043l = (float) ViewConfiguration.get(context).getScaledEdgeSlop();
    }

    protected void mo1468b(MotionEvent motionEvent) {
        super.mo1468b(motionEvent);
        if (this.c != null) {
            MotionEvent motionEvent2 = this.c;
            int pointerCount = this.c.getPointerCount();
            int pointerCount2 = motionEvent.getPointerCount();
            if (pointerCount2 == 2 && pointerCount2 == pointerCount) {
                this.f4046o = GroundOverlayOptions.NO_DIMENSION;
                this.f4047p = GroundOverlayOptions.NO_DIMENSION;
                float x = motionEvent2.getX(0);
                float y = motionEvent2.getY(0);
                float x2 = motionEvent2.getX(1);
                float y2 = motionEvent2.getY(1) - y;
                this.f4039h = x2 - x;
                this.f4040i = y2;
                y2 = motionEvent.getX(0);
                x = motionEvent.getY(0);
                x = motionEvent.getY(1) - x;
                this.f4041j = motionEvent.getX(1) - y2;
                this.f4042k = x;
            }
        }
    }

    public float m4010c() {
        if (this.f4046o == GroundOverlayOptions.NO_DIMENSION) {
            float f = this.f4041j;
            float f2 = this.f4042k;
            this.f4046o = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        }
        return this.f4046o;
    }

    protected static float m4007a(MotionEvent motionEvent, int i) {
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        if (i < motionEvent.getPointerCount()) {
            return rawX + motionEvent.getX(i);
        }
        return 0.0f;
    }

    protected static float m4008b(MotionEvent motionEvent, int i) {
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        if (i < motionEvent.getPointerCount()) {
            return rawY + motionEvent.getY(i);
        }
        return 0.0f;
    }

    protected boolean m4011d(MotionEvent motionEvent) {
        DisplayMetrics displayMetrics = this.a.getResources().getDisplayMetrics();
        this.f4044m = ((float) displayMetrics.widthPixels) - this.f4043l;
        this.f4045n = ((float) displayMetrics.heightPixels) - this.f4043l;
        float f = this.f4043l;
        float f2 = this.f4044m;
        float f3 = this.f4045n;
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        float a = m4007a(motionEvent, 1);
        float b = m4008b(motionEvent, 1);
        boolean z = rawX < f || rawY < f || rawX > f2 || rawY > f3;
        boolean z2;
        if (a < f || b < f || a > f2 || b > f3) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z || r2) {
            return true;
        }
        return false;
    }
}
