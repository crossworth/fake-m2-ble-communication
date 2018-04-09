package com.zhuoyou.plugin.running;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.fithealth.running.R;

public class WavesAnimView extends View {
    private float height;
    private float mControlE_x;
    private float mControlE_y;
    private float mControlF_x;
    private float mControlF_y;
    private float mEndPoint_x;
    private float mEndPoint_y;
    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private Path mPath;
    private ValueAnimator mScrollAnimator;
    private int mScrollPosition;
    private float mStartPoint_x;
    private float mStartPoint_y;
    private int mWavePerimeter;
    private int width;

    class C14241 implements AnimatorUpdateListener {
        C14241() {
        }

        public void onAnimationUpdate(ValueAnimator paramValueAnimator) {
            if (WavesAnimView.this.mScrollPosition <= (-WavesAnimView.this.mWavePerimeter)) {
                WavesAnimView.this.mScrollPosition = 0;
            }
            WavesAnimView.this.mScrollPosition = ((Integer) paramValueAnimator.getAnimatedValue()).intValue();
            WavesAnimView.this.invalidate();
        }
    }

    public WavesAnimView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.width = 720;
        this.mScrollPosition = 0;
        this.mStartPoint_x = 0.0f;
        this.mStartPoint_y = 0.0f;
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.mPaint1 = new Paint();
        this.mPaint2 = new Paint();
        this.height = getResources().getDimension(R.dimen.waves_anim_view_height);
        this.mWavePerimeter = (int) getResources().getDimension(R.dimen.wave_perimeter);
        this.mStartPoint_x = 0.0f;
        this.mEndPoint_x = (float) (this.mWavePerimeter * 2);
        this.mEndPoint_y = this.mStartPoint_y;
        this.mScrollAnimator = new ValueAnimator();
        this.mScrollAnimator.setDuration(12000);
        this.mScrollAnimator.setInterpolator(new LinearInterpolator());
        this.mScrollAnimator.addUpdateListener(new C14241());
        this.mScrollAnimator.setIntValues(new int[]{this.mScrollPosition, -this.mWavePerimeter});
        this.mScrollAnimator.setRepeatCount(-1);
        this.mScrollAnimator.setRepeatMode(1);
        this.mScrollAnimator.start();
    }

    public WavesAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WavesAnimView(Context context) {
        this(context, null);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        this.mPath.moveTo(this.mStartPoint_x, this.mStartPoint_y);
        this.mPath.cubicTo(this.mControlF_x, this.mControlF_y, this.mControlE_x, this.mControlE_y, (float) this.mWavePerimeter, this.mStartPoint_y);
        this.mPath.cubicTo(this.mControlF_x + ((float) this.mWavePerimeter), this.mControlF_y, this.mControlE_x + ((float) this.mWavePerimeter), this.mControlE_y, (float) (this.mWavePerimeter * 2), this.mStartPoint_y);
        this.mPath.lineTo((float) (this.mWavePerimeter * 2), this.height);
        this.mPath.lineTo(this.mStartPoint_x, this.height);
        this.mPath.close();
        Matrix matrix = canvas.getMatrix();
        matrix.setTranslate((float) this.mScrollPosition, 0.0f);
        canvas.setMatrix(matrix);
        this.mPaint.setColor(getResources().getColor(R.color.water_color1));
        this.mPaint.setStrokeWidth(15.0f);
        this.mPaint.setAntiAlias(true);
        this.mPaint1.setColor(getResources().getColor(R.color.water_color2));
        this.mPaint1.setStrokeWidth(15.0f);
        this.mPaint1.setAlpha(70);
        this.mPaint1.setAntiAlias(true);
        this.mPaint2.setColor(getResources().getColor(R.color.water_color3));
        this.mPaint2.setStrokeWidth(15.0f);
        this.mPaint2.setAlpha(70);
        this.mPaint2.setAntiAlias(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        canvas.drawPath(this.mPath, this.mPaint2);
        matrix.setTranslate((float) (this.mScrollPosition - 100), 0.0f);
        canvas.setMatrix(matrix);
        canvas.drawPath(this.mPath, this.mPaint1);
        matrix.setTranslate((float) (this.mScrollPosition - 200), 0.0f);
        canvas.setMatrix(matrix);
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
        this.mPath.reset();
    }

    public void setStartPointY(int i) {
        Log.v("renjing", "i" + i);
        if (i >= 8) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y8);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x8);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y8);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x8);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y8);
            invalidate();
        }
        if (i == 0) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y0);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x0);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y0);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x0);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y0);
            invalidate();
        }
        if (i == 1) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y1);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x1);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y1);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x1);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y1);
            invalidate();
        }
        if (i == 2) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y2);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x2);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y2);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x2);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y2);
            invalidate();
        }
        if (i == 3) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y3);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x3);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y3);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x3);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y3);
            invalidate();
        }
        if (i == 4) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y4);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x4);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y4);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x4);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y4);
            invalidate();
        }
        if (i == 5) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y5);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x5);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y5);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x5);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y5);
            invalidate();
        }
        if (i == 6) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y6);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x6);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y6);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x6);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y6);
            invalidate();
        }
        if (i == 7) {
            this.mStartPoint_y = getResources().getDimension(R.dimen.waves_anim_view_mStartPoint_y7);
            this.mControlF_x = getResources().getDimension(R.dimen.waves_anim_view_mControlF_x7);
            this.mControlF_y = getResources().getDimension(R.dimen.waves_anim_view_mControlF_y7);
            this.mControlE_x = getResources().getDimension(R.dimen.waves_anim_view_mControlE_x7);
            this.mControlE_y = getResources().getDimension(R.dimen.waves_anim_view_mControlE_y7);
            invalidate();
        }
    }
}
