package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

/* compiled from: ProGuard */
public class C1168b extends View {
    private Rect f3627a;
    private Paint f3628b;

    public C1168b(Context context) {
        super(context);
        m3442b();
    }

    private void m3442b() {
        this.f3628b = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect a = m3443a();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.f3628b.setStyle(Style.FILL);
        this.f3628b.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawRect(0.0f, 0.0f, (float) measuredWidth, (float) a.top, this.f3628b);
        canvas.drawRect(0.0f, (float) a.bottom, (float) measuredWidth, (float) measuredHeight, this.f3628b);
        canvas.drawRect(0.0f, (float) a.top, (float) a.left, (float) a.bottom, this.f3628b);
        canvas.drawRect((float) a.right, (float) a.top, (float) measuredWidth, (float) a.bottom, this.f3628b);
        canvas.drawColor(Color.argb(100, 0, 0, 0));
        this.f3628b.setStyle(Style.STROKE);
        this.f3628b.setColor(-1);
        canvas.drawRect((float) a.left, (float) a.top, (float) (a.right - 1), (float) a.bottom, this.f3628b);
    }

    public Rect m3443a() {
        if (this.f3627a == null) {
            this.f3627a = new Rect();
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int min = Math.min(Math.min((measuredHeight - 60) - 80, measuredWidth), 640);
            measuredWidth = (measuredWidth - min) / 2;
            measuredHeight = (measuredHeight - min) / 2;
            this.f3627a.set(measuredWidth, measuredHeight, measuredWidth + min, min + measuredHeight);
        }
        return this.f3627a;
    }
}
