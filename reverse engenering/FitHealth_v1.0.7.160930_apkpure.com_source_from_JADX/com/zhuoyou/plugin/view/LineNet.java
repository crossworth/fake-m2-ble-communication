package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.Tools;

public class LineNet extends View {
    private final int initPosition = ((this.mWidth / 2) - (this.offsetPx / 2));
    private final int mHeight;
    private final Paint mPaint;
    private final Path mPathLine;
    private final int mWidth;
    private final int mWindowWidth;
    private final int offsetPx;
    private final int paintWidth;

    public LineNet(Context paramContext, int paramInt1, int paramInt2) {
        super(paramContext);
        this.mHeight = paramInt1;
        this.mWindowWidth = paramInt2;
        this.offsetPx = Tools.dip2px(paramContext, 22.0f);
        this.mWidth = this.mWindowWidth - Tools.dip2px(paramContext, 24.0f);
        this.paintWidth = Tools.dip2px(paramContext, 1.0f);
        this.mPaint = new Paint(1);
        this.mPathLine = new Path();
    }

    public int getCanvasWidth() {
        return this.mWidth;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        paramCanvas.drawColor(getResources().getColor(R.color.transparent));
        this.mPathLine.reset();
        this.mPaint.setPathEffect(null);
        this.mPaint.setStrokeWidth((float) this.paintWidth);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setColor(-1);
        this.mPathLine.moveTo(0.0f, 0.0f);
        this.mPathLine.lineTo((float) this.mWidth, 0.0f);
        paramCanvas.drawPath(this.mPathLine, this.mPaint);
        float f1 = (float) this.initPosition;
        float f2 = (float) this.mHeight;
        float f3 = (float) (this.initPosition + this.offsetPx);
        while (f1 >= 0.0f) {
            this.mPathLine.moveTo(f1, 0.0f);
            this.mPathLine.lineTo(f1, (float) this.mHeight);
            f1 -= (float) this.offsetPx;
            paramCanvas.drawPath(this.mPathLine, this.mPaint);
            this.mPathLine.reset();
        }
        while (f3 <= ((float) this.mWidth)) {
            this.mPathLine.moveTo(f3, 0.0f);
            this.mPathLine.lineTo(f3, (float) this.mHeight);
            f3 += (float) this.offsetPx;
            paramCanvas.drawPath(this.mPathLine, this.mPaint);
            this.mPathLine.reset();
        }
        while (f2 >= 0.0f) {
            this.mPathLine.moveTo(0.0f, f2);
            this.mPathLine.lineTo((float) this.mWidth, f2);
            f2 -= (float) this.offsetPx;
            paramCanvas.drawPath(this.mPathLine, this.mPaint);
            this.mPathLine.reset();
        }
    }
}
