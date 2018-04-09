package com.baidu.mapapi.map;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;

public class SwipeDismissTouchListener implements OnTouchListener {
    private int f1283a;
    private int f1284b;
    private int f1285c;
    private long f1286d;
    private View f1287e;
    private DismissCallbacks f1288f;
    private int f1289g = 1;
    private float f1290h;
    private float f1291i;
    private boolean f1292j;
    private int f1293k;
    private Object f1294l;
    private VelocityTracker f1295m;
    private float f1296n;
    private boolean f1297o;
    private boolean f1298p;

    public interface DismissCallbacks {
        boolean canDismiss(Object obj);

        void onDismiss(View view, Object obj);

        void onNotify();
    }

    public SwipeDismissTouchListener(View view, Object obj, DismissCallbacks dismissCallbacks) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(view.getContext());
        this.f1283a = viewConfiguration.getScaledTouchSlop();
        this.f1284b = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f1285c = viewConfiguration.getScaledMaximumFlingVelocity();
        this.f1286d = (long) view.getContext().getResources().getInteger(17694720);
        this.f1287e = view;
        this.f1287e.getContext();
        this.f1294l = obj;
        this.f1288f = dismissCallbacks;
    }

    @TargetApi(11)
    private void m1182a() {
        LayoutParams layoutParams = this.f1287e.getLayoutParams();
        ValueAnimator duration = ValueAnimator.ofInt(new int[]{this.f1287e.getHeight(), 1}).setDuration(this.f1286d);
        duration.addListener(new C0497n(this, layoutParams, r1));
        duration.addUpdateListener(new C0498o(this, layoutParams));
        duration.start();
    }

    @TargetApi(12)
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z = true;
        motionEvent.offsetLocation(this.f1296n, 0.0f);
        if (this.f1289g < 2) {
            this.f1289g = this.f1287e.getWidth();
        }
        float rawX;
        float xVelocity;
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.f1290h = motionEvent.getRawX();
                this.f1291i = motionEvent.getRawY();
                if (!this.f1288f.canDismiss(this.f1294l)) {
                    return true;
                }
                this.f1297o = false;
                this.f1295m = VelocityTracker.obtain();
                this.f1295m.addMovement(motionEvent);
                return true;
            case 1:
                if (this.f1295m != null) {
                    boolean z2;
                    rawX = motionEvent.getRawX() - this.f1290h;
                    this.f1295m.addMovement(motionEvent);
                    this.f1295m.computeCurrentVelocity(1000);
                    xVelocity = this.f1295m.getXVelocity();
                    float abs = Math.abs(xVelocity);
                    float abs2 = Math.abs(this.f1295m.getYVelocity());
                    if (Math.abs(rawX) > ((float) (this.f1289g / 3)) && this.f1292j) {
                        z2 = rawX > 0.0f;
                    } else if (((float) this.f1284b) > abs || abs > ((float) this.f1285c) || abs2 >= abs || abs2 >= abs || !this.f1292j) {
                        z2 = false;
                        z = false;
                    } else {
                        z2 = ((xVelocity > 0.0f ? 1 : (xVelocity == 0.0f ? 0 : -1)) < 0) == ((rawX > 0.0f ? 1 : (rawX == 0.0f ? 0 : -1)) < 0);
                        if (this.f1295m.getXVelocity() <= 0.0f) {
                            z = false;
                        }
                        boolean z3 = z;
                        z = z2;
                        z2 = z3;
                    }
                    if (z) {
                        this.f1287e.animate().translationX(z2 ? (float) this.f1289g : (float) (-this.f1289g)).setDuration(this.f1286d).setListener(new C0496m(this));
                    } else if (this.f1292j) {
                        this.f1287e.animate().translationX(0.0f).setDuration(this.f1286d).setListener(null);
                    }
                    this.f1295m.recycle();
                    this.f1295m = null;
                    this.f1296n = 0.0f;
                    this.f1290h = 0.0f;
                    this.f1291i = 0.0f;
                    this.f1292j = false;
                    break;
                }
                break;
            case 2:
                if (this.f1295m != null) {
                    this.f1295m.addMovement(motionEvent);
                    xVelocity = motionEvent.getRawX() - this.f1290h;
                    rawX = motionEvent.getRawY() - this.f1291i;
                    if (Math.abs(xVelocity) > ((float) this.f1283a) && Math.abs(rawX) < Math.abs(xVelocity) / 2.0f) {
                        this.f1292j = true;
                        this.f1293k = xVelocity > 0.0f ? this.f1283a : -this.f1283a;
                        this.f1287e.getParent().requestDisallowInterceptTouchEvent(true);
                        if (!this.f1297o) {
                            this.f1297o = true;
                            this.f1288f.onNotify();
                        }
                        if (Math.abs(xVelocity) <= ((float) (this.f1289g / 3))) {
                            this.f1298p = false;
                        } else if (!this.f1298p) {
                            this.f1298p = true;
                            this.f1288f.onNotify();
                        }
                        MotionEvent obtain = MotionEvent.obtain(motionEvent);
                        obtain.setAction((motionEvent.getActionIndex() << 8) | 3);
                        this.f1287e.onTouchEvent(obtain);
                        obtain.recycle();
                    }
                    if (this.f1292j) {
                        this.f1296n = xVelocity;
                        this.f1287e.setTranslationX(xVelocity - ((float) this.f1293k));
                        return true;
                    }
                }
                break;
            case 3:
                if (this.f1295m != null) {
                    this.f1287e.animate().translationX(0.0f).setDuration(this.f1286d).setListener(null);
                    this.f1295m.recycle();
                    this.f1295m = null;
                    this.f1296n = 0.0f;
                    this.f1290h = 0.0f;
                    this.f1291i = 0.0f;
                    this.f1292j = false;
                    break;
                }
                break;
        }
        return false;
    }
}
