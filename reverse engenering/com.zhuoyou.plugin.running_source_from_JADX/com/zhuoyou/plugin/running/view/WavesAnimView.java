package com.zhuoyou.plugin.running.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.Tools;

public class WavesAnimView extends View {
    private int[] colors;
    private float controlHeight;
    private boolean inited;
    private ValueAnimator levelAnim;
    private float mControl1_x;
    private float mControl1_y;
    private float mControl2_x;
    private float mControl2_y;
    private Paint mPaint;
    private Path mPath;
    private float mScrollPosition;
    private float mStartPoint_y;
    private float translate;
    private int viewHeight;
    private int waterLevel;
    private float width;

    class C19641 implements AnimatorUpdateListener {
        C19641() {
        }

        public void onAnimationUpdate(ValueAnimator paramValueAnimator) {
            if (WavesAnimView.this.mScrollPosition <= (-WavesAnimView.this.width) * 2.0f) {
                WavesAnimView.this.mScrollPosition = 0.0f;
            }
            WavesAnimView.this.mScrollPosition = ((Float) paramValueAnimator.getAnimatedValue()).floatValue();
            WavesAnimView.this.postInvalidate();
        }
    }

    class C19652 implements AnimatorUpdateListener {
        C19652() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            WavesAnimView.this.mStartPoint_y = ((Float) animation.getAnimatedValue()).floatValue();
            WavesAnimView.this.mControl1_y = WavesAnimView.this.mStartPoint_y - WavesAnimView.this.controlHeight;
            WavesAnimView.this.mControl2_y = WavesAnimView.this.mStartPoint_y + WavesAnimView.this.controlHeight;
            WavesAnimView.this.postInvalidate();
        }
    }

    public WavesAnimView(Context context) {
        super(context);
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.colors = new int[3];
        this.translate = (float) Tools.dip2px(40.0f);
        this.mScrollPosition = 0.0f;
        this.mStartPoint_y = 0.0f;
    }

    public WavesAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.colors = new int[3];
        this.translate = (float) Tools.dip2px(40.0f);
        this.mScrollPosition = 0.0f;
        this.mStartPoint_y = 0.0f;
    }

    public WavesAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.colors = new int[3];
        this.translate = (float) Tools.dip2px(40.0f);
        this.mScrollPosition = 0.0f;
        this.mStartPoint_y = 0.0f;
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.inited) {
            canvas.save();
            this.mPath.moveTo(0.0f, this.mStartPoint_y);
            this.mPath.quadTo(this.mControl1_x, this.mControl1_y, this.width, this.mStartPoint_y);
            this.mPath.quadTo(this.mControl2_x, this.mControl2_y, this.width * 2.0f, this.mStartPoint_y);
            this.mPath.quadTo(this.mControl1_x + (this.width * 2.0f), this.mControl1_y, this.width * 3.0f, this.mStartPoint_y);
            this.mPath.lineTo(this.width * 3.0f, (float) this.viewHeight);
            this.mPath.lineTo(0.0f, (float) this.viewHeight);
            this.mPath.close();
            this.mPaint.setColor(this.colors[0]);
            canvas.translate(this.mScrollPosition, 0.0f);
            canvas.drawPath(this.mPath, this.mPaint);
            this.mPaint.setColor(this.colors[1]);
            canvas.translate(-this.translate, 0.0f);
            canvas.drawPath(this.mPath, this.mPaint);
            this.mPaint.setColor(this.colors[2]);
            canvas.translate(-this.translate, 0.0f);
            canvas.drawPath(this.mPath, this.mPaint);
            canvas.restore();
            this.mPath.reset();
        }
    }

    private void setupPaints() {
        this.colors[0] = getResources().getColor(C1680R.color.health_center_water_color3);
        this.colors[1] = getResources().getColor(C1680R.color.health_center_water_color2);
        this.colors[2] = getResources().getColor(C1680R.color.health_center_water_color1);
        this.mPaint.setColor(this.colors[0]);
        this.mPaint.setAntiAlias(true);
    }

    private void startAnim() {
        ValueAnimator mScrollAnimator = ValueAnimator.ofFloat(new float[]{0.0f, (-this.width) * 2.0f});
        mScrollAnimator.setDuration(6000);
        mScrollAnimator.setInterpolator(new LinearInterpolator());
        mScrollAnimator.addUpdateListener(new C19641());
        mScrollAnimator.setRepeatCount(-1);
        mScrollAnimator.setRepeatMode(1);
        mScrollAnimator.start();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.viewHeight = h;
        this.controlHeight = ((float) this.viewHeight) / 12.0f;
        this.width = ((float) w) + (this.translate * 2.0f);
        this.mControl1_x = this.width / 2.0f;
        this.mControl2_x = this.width + (this.width / 2.0f);
        this.mStartPoint_y = ((float) this.viewHeight) - (this.controlHeight / 2.0f);
        setupPaints();
        startAnim();
        setWaterLevel(this.waterLevel, false);
        this.inited = true;
    }

    public void setInitWaterLevel(int i) {
        this.waterLevel = i;
    }

    public void setWaterLevel(int i, boolean anim) {
        float target;
        if (i > 8) {
            i = 8;
        } else if (i < 0) {
            i = 0;
        }
        if (i == 0) {
            target = ((float) this.viewHeight) - (this.controlHeight / 2.0f);
        } else {
            target = ((float) this.viewHeight) - ((((float) this.viewHeight) / 9.0f) * ((float) i));
        }
        if (!anim) {
            this.mStartPoint_y = target;
        }
        startLevelAnim(target);
    }

    private void startLevelAnim(float target) {
        if (this.levelAnim != null && this.levelAnim.isRunning()) {
            this.levelAnim.cancel();
        }
        this.levelAnim = ValueAnimator.ofFloat(new float[]{this.mStartPoint_y, target});
        this.levelAnim.setInterpolator(new LinearInterpolator());
        this.levelAnim.setDuration(500);
        this.levelAnim.addUpdateListener(new C19652());
        this.levelAnim.start();
    }
}
