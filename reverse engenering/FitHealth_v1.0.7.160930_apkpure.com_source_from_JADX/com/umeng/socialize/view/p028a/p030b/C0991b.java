package com.umeng.socialize.view.p028a.p030b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.umeng.socialize.view.p028a.p029a.C0985a;
import com.umeng.socialize.view.p028a.p029a.C0986b;
import java.lang.ref.WeakReference;
import java.util.List;

/* compiled from: FlowerView */
public final class C0991b extends View {
    private int f3393a;
    private int f3394b;
    private int f3395c;
    private float f3396d;
    private RectF f3397e;
    private Paint f3398f;
    private Paint f3399g;
    private Paint f3400h;
    private List<C0986b> f3401i;
    private int[] f3402j;
    private Handler f3403k = new C0990a(this);
    private int f3404l;
    private String f3405m;
    private int f3406n;
    private int f3407o;
    private int f3408p;
    private boolean f3409q;

    /* compiled from: FlowerView */
    private static class C0990a extends Handler {
        WeakReference<C0991b> f3392a;

        public C0990a(C0991b c0991b) {
            this.f3392a = new WeakReference(c0991b);
        }

        public void handleMessage(Message message) {
            C0991b c0991b = (C0991b) this.f3392a.get();
            if (c0991b != null) {
                c0991b.invalidate();
            }
        }
    }

    public C0991b(Context context, int i, int i2, float f, float f2, int i3, int i4, float f3, float f4, float f5, int i5, int i6, String str, float f6, int i7, float f7, int i8, boolean z) {
        super(context);
        this.f3408p = i8;
        m3287a(i, i2, f, f2, i3, i4, f3, f4, f5, i5, i6, str, f6, i7, f7, z);
    }

    private void m3287a(int i, int i2, float f, float f2, int i3, int i4, float f3, float f4, float f5, int i5, int i6, String str, float f6, int i7, float f7, boolean z) {
        boolean z2 = (str == null || str.length() == 0 || !z) ? false : true;
        this.f3409q = z2;
        this.f3393a = i;
        this.f3395c = i4;
        this.f3396d = f2;
        this.f3398f = new Paint();
        this.f3398f.setAntiAlias(true);
        this.f3398f.setColor(i2);
        this.f3398f.setAlpha((int) (255.0f * f));
        this.f3399g = new Paint();
        this.f3399g.setAntiAlias(true);
        this.f3399g.setStrokeWidth((float) i3);
        this.f3399g.setStrokeCap(Cap.ROUND);
        if (str == null || str.length() == 0) {
            this.f3408p = 0;
        } else {
            this.f3405m = str;
            this.f3400h = new Paint();
            this.f3400h.setAntiAlias(true);
            this.f3400h.setColor(i7);
            this.f3400h.setAlpha((int) (255.0f * f7));
            this.f3400h.setTextSize(f6);
            Rect rect = new Rect();
            this.f3400h.getTextBounds(str, 0, str.length(), rect);
            this.f3406n = rect.bottom - rect.top;
            this.f3407o = rect.right - rect.left;
        }
        if (this.f3409q) {
            this.f3397e = new RectF(0.0f, 0.0f, (float) ((this.f3393a + this.f3406n) + this.f3408p), (float) ((this.f3393a + this.f3406n) + this.f3408p));
            this.f3394b = (this.f3393a + this.f3406n) + this.f3408p;
        } else {
            this.f3397e = new RectF(0.0f, 0.0f, (float) this.f3393a, (float) ((this.f3393a + this.f3406n) + this.f3408p));
            this.f3394b = this.f3393a;
        }
        C0985a c0985a = new C0985a(i4);
        this.f3401i = c0985a.m3279a(this.f3393a, (int) (((float) this.f3393a) * f4), (int) (((float) this.f3393a) * f5), i4, this.f3394b);
        this.f3402j = c0985a.m3280a(i5, i6, i4, (int) (255.0f * f3));
    }

    public void m3288a(int i) {
        this.f3404l = i;
        this.f3403k.sendEmptyMessage(0);
    }

    protected void onMeasure(int i, int i2) {
        if (this.f3409q) {
            setMeasuredDimension((this.f3393a + this.f3406n) + this.f3408p, (this.f3393a + this.f3406n) + this.f3408p);
        } else {
            setMeasuredDimension(this.f3393a, (this.f3393a + this.f3406n) + this.f3408p);
        }
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(this.f3397e, this.f3396d, this.f3396d, this.f3398f);
        for (int i = 0; i < this.f3395c; i++) {
            C0986b c0986b = (C0986b) this.f3401i.get(i);
            this.f3399g.setColor(this.f3402j[(this.f3404l + i) % this.f3395c]);
            canvas.drawLine((float) c0986b.m3281a(), (float) c0986b.m3282b(), (float) c0986b.m3283c(), (float) c0986b.m3284d(), this.f3399g);
        }
        if (this.f3405m != null) {
            canvas.drawText(this.f3405m, (float) ((this.f3394b / 2) - (this.f3407o / 2)), (float) this.f3393a, this.f3400h);
        }
    }
}
