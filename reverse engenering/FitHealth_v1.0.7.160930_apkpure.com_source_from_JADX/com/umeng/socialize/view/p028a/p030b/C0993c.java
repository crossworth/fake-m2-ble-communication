package com.umeng.socialize.view.p028a.p030b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;

/* compiled from: PieView */
public final class C0993c extends View {
    private RectF f3411a;
    private RectF f3412b;
    private Paint f3413c;
    private Paint f3414d;
    private Paint f3415e;
    private int f3416f;
    private float f3417g;
    private float f3418h;
    private float f3419i;
    private Handler f3420j = new C0992a(this);

    /* compiled from: PieView */
    private static class C0992a extends Handler {
        WeakReference<C0993c> f3410a;

        public C0992a(C0993c c0993c) {
            this.f3410a = new WeakReference(c0993c);
        }

        public void handleMessage(Message message) {
            C0993c c0993c = (C0993c) this.f3410a.get();
            if (c0993c != null) {
                c0993c.invalidate();
            }
        }
    }

    public C0993c(Context context, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, float f5, int i5, float f6) {
        super(context);
        this.f3416f = i;
        this.f3417g = f2;
        this.f3418h = f3;
        this.f3411a = new RectF(0.0f, 0.0f, (float) i, (float) i);
        float f7 = (f3 + f4) * ((float) i);
        this.f3412b = new RectF(0.0f + (f7 / 2.0f), 0.0f + (f7 / 2.0f), ((float) i) - (f7 / 2.0f), ((float) i) - (f7 / 2.0f));
        this.f3413c = new Paint();
        this.f3413c.setAntiAlias(true);
        this.f3413c.setColor(i2);
        this.f3413c.setAlpha((int) (255.0f * f));
        this.f3414d = new Paint();
        this.f3414d.setAntiAlias(true);
        this.f3414d.setStrokeWidth((float) i3);
        this.f3414d.setColor(i4);
        this.f3414d.setAlpha((int) (255.0f * f5));
        this.f3414d.setStyle(Style.STROKE);
        this.f3415e = new Paint();
        this.f3415e.setAntiAlias(true);
        this.f3415e.setColor(i5);
        this.f3415e.setAlpha((int) (255.0f * f6));
    }

    public void m3289a(float f) {
        this.f3419i = f;
        this.f3420j.sendEmptyMessage(0);
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(this.f3416f, this.f3416f);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(this.f3411a, this.f3417g, this.f3417g, this.f3413c);
        canvas.drawCircle((float) (this.f3416f / 2), (float) (this.f3416f / 2), (((float) this.f3416f) * (1.0f - this.f3418h)) / 2.0f, this.f3414d);
        Log.d("23232", "--" + this.f3419i);
        canvas.drawArc(this.f3412b, -90.0f, this.f3419i, true, this.f3415e);
    }
}
