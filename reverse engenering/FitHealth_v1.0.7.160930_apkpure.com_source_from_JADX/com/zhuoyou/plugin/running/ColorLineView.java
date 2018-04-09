package com.zhuoyou.plugin.running;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.fithealth.running.R;

public class ColorLineView extends View {
    private Paint mBackPaint = new Paint();
    private int mCount;
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();
    private Paint mPaint3 = new Paint();
    private Paint mPaint4 = new Paint();
    private Paint mPaint5 = new Paint();
    private Paint mPaint6 = new Paint();
    private Paint mPaint7 = new Paint();
    private Paint mPaint8 = new Paint();
    private float mSpace = getResources().getDimension(R.dimen.color_line_view_space);
    private float mStartx = getResources().getDimension(R.dimen.color_line_view_startx);
    private float mStarty = getResources().getDimension(R.dimen.color_line_view_starty);
    private float mStopx = getResources().getDimension(R.dimen.color_line_view_stopx);

    public ColorLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBackPaint.setColor(getResources().getColor(R.color.line_background));
        this.mBackPaint.setStrokeWidth(7.0f);
        this.mPaint1.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint1.setStrokeWidth(7.0f);
        this.mPaint2.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint2.setStrokeWidth(7.0f);
        this.mPaint3.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint3.setStrokeWidth(7.0f);
        this.mPaint4.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint4.setStrokeWidth(7.0f);
        this.mPaint5.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint5.setStrokeWidth(7.0f);
        this.mPaint6.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint6.setStrokeWidth(7.0f);
        this.mPaint7.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint7.setStrokeWidth(7.0f);
        this.mPaint8.setColor(getResources().getColor(R.color.line_color1));
        this.mPaint8.setStrokeWidth(7.0f);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(this.mStartx, this.mStarty, this.mStopx, this.mStarty, this.mBackPaint);
        if (this.mCount != 0) {
            if (this.mCount == 1) {
                drawColorLine1(canvas);
            } else if (this.mCount == 2) {
                drawColorLine2(canvas);
            } else if (this.mCount == 3) {
                drawColorLine3(canvas);
            } else if (this.mCount == 4) {
                drawColorLine4(canvas);
            } else if (this.mCount == 5) {
                drawColorLine5(canvas);
            } else if (this.mCount == 6) {
                drawColorLine6(canvas);
            } else if (this.mCount == 7) {
                drawColorLine7(canvas);
            } else if (this.mCount == 8) {
                drawColorLine8(canvas);
            } else {
                drawColorLine8(canvas);
            }
        }
    }

    public void drawColorLine1(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        invalidate();
    }

    public void drawColorLine2(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (2.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint2);
        invalidate();
    }

    public void drawColorLine3(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (this.mSpace * 2.0f) + this.mStartx, this.mStarty, this.mPaint2);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 2.0f) + this.mStartx, this.mStarty, (3.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint3);
        invalidate();
    }

    public void drawColorLine4(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (this.mSpace * 2.0f) + this.mStartx, this.mStarty, this.mPaint2);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 2.0f) + this.mStartx, this.mStarty, (this.mSpace * 3.0f) + this.mStartx, this.mStarty, this.mPaint3);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 3.0f) + this.mStartx, this.mStarty, (4.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint4);
        invalidate();
    }

    public void drawColorLine5(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (this.mSpace * 2.0f) + this.mStartx, this.mStarty, this.mPaint2);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 2.0f) + this.mStartx, this.mStarty, (this.mSpace * 3.0f) + this.mStartx, this.mStarty, this.mPaint3);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 3.0f) + this.mStartx, this.mStarty, (this.mSpace * 4.0f) + this.mStartx, this.mStarty, this.mPaint4);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 4.0f) + this.mStartx, this.mStarty, (5.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint5);
        invalidate();
    }

    public void drawColorLine6(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (this.mSpace * 2.0f) + this.mStartx, this.mStarty, this.mPaint2);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 2.0f) + this.mStartx, this.mStarty, (this.mSpace * 3.0f) + this.mStartx, this.mStarty, this.mPaint3);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 3.0f) + this.mStartx, this.mStarty, (this.mSpace * 4.0f) + this.mStartx, this.mStarty, this.mPaint4);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 4.0f) + this.mStartx, this.mStarty, (this.mSpace * 5.0f) + this.mStartx, this.mStarty, this.mPaint5);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 5.0f) + this.mStartx, this.mStarty, (6.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint6);
        invalidate();
    }

    public void drawColorLine7(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (this.mSpace * 2.0f) + this.mStartx, this.mStarty, this.mPaint2);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 2.0f) + this.mStartx, this.mStarty, (this.mSpace * 3.0f) + this.mStartx, this.mStarty, this.mPaint3);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 3.0f) + this.mStartx, this.mStarty, (this.mSpace * 4.0f) + this.mStartx, this.mStarty, this.mPaint4);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 4.0f) + this.mStartx, this.mStarty, (this.mSpace * 5.0f) + this.mStartx, this.mStarty, this.mPaint5);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 5.0f) + this.mStartx, this.mStarty, (this.mSpace * 6.0f) + this.mStartx, this.mStarty, this.mPaint6);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 6.0f) + this.mStartx, this.mStarty, (7.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint7);
        invalidate();
    }

    public void drawColorLine8(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.mStartx, this.mStarty, this.mSpace + this.mStartx, this.mStarty, this.mPaint1);
        canvas2 = canvas;
        canvas2.drawLine(this.mSpace + this.mStartx, this.mStarty, (this.mSpace * 2.0f) + this.mStartx, this.mStarty, this.mPaint2);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 2.0f) + this.mStartx, this.mStarty, (this.mSpace * 3.0f) + this.mStartx, this.mStarty, this.mPaint3);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 3.0f) + this.mStartx, this.mStarty, (this.mSpace * 4.0f) + this.mStartx, this.mStarty, this.mPaint4);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 4.0f) + this.mStartx, this.mStarty, (this.mSpace * 5.0f) + this.mStartx, this.mStarty, this.mPaint5);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 5.0f) + this.mStartx, this.mStarty, (this.mSpace * 6.0f) + this.mStartx, this.mStarty, this.mPaint6);
        canvas2 = canvas;
        canvas2.drawLine((this.mSpace * 6.0f) + this.mStartx, this.mStarty, (7.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint7);
        canvas2 = canvas;
        canvas2.drawLine((7.0f * this.mSpace) + this.mStartx, this.mStarty, (8.0f * this.mSpace) + this.mStartx, this.mStarty, this.mPaint8);
        invalidate();
    }

    public void setNumber(int i) {
        this.mCount = i;
        invalidate();
    }
}
