package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: ProGuard */
public class C0702g extends View {
    private Rect f2435a;
    private Paint f2436b;

    public C0702g(Context context) {
        super(context);
        m2374b();
    }

    public C0702g(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m2374b();
    }

    private void m2374b() {
        this.f2436b = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect a = m2375a();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.f2436b.setStyle(Style.FILL);
        this.f2436b.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawRect(0.0f, 0.0f, (float) measuredWidth, (float) a.top, this.f2436b);
        canvas.drawRect(0.0f, (float) a.bottom, (float) measuredWidth, (float) measuredHeight, this.f2436b);
        canvas.drawRect(0.0f, (float) a.top, (float) a.left, (float) a.bottom, this.f2436b);
        canvas.drawRect((float) a.right, (float) a.top, (float) measuredWidth, (float) a.bottom, this.f2436b);
        canvas.drawColor(Color.argb(100, 0, 0, 0));
        this.f2436b.setStyle(Style.STROKE);
        this.f2436b.setColor(-1);
        canvas.drawRect((float) a.left, (float) a.top, (float) (a.right - 1), (float) a.bottom, this.f2436b);
    }

    public Rect m2375a() {
        if (this.f2435a == null) {
            this.f2435a = new Rect();
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int min = Math.min(Math.min((measuredHeight - 60) - 80, measuredWidth), 640);
            measuredWidth = (measuredWidth - min) / 2;
            measuredHeight = (measuredHeight - min) / 2;
            this.f2435a.set(measuredWidth, measuredHeight, measuredWidth + min, min + measuredHeight);
        }
        return this.f2435a;
    }
}
