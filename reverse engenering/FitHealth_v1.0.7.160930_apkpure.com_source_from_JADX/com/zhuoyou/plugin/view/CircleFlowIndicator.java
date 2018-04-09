package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.tencent.open.yyb.TitleBar;

public class CircleFlowIndicator extends View implements FlowIndicator, AnimationListener {
    private static final int STYLE_FILL = 1;
    private static final int STYLE_STROKE = 0;
    private float activeRadius = 0.5f;
    private Animation animation;
    public AnimationListener animationListener = this;
    private float circleSeparation = (((2.0f * this.radius) + this.radius) + TitleBar.BACKBTN_LEFT_MARGIN);
    private int currentScroll = 0;
    private int fadeOutTime = 0;
    private int flowWidth = 0;
    private final Paint mPaintActive = new Paint(1);
    private final Paint mPaintInactive = new Paint(1);
    private float radius = 12.0f;
    private FadeTimer timer;
    private ViewFlow viewFlow;

    private class FadeTimer extends AsyncTask<Void, Void, Void> {
        private boolean _run;
        private int timer;

        private FadeTimer() {
            this.timer = 0;
            this._run = true;
        }

        public void resetTimer() {
            this.timer = 0;
        }

        protected Void doInBackground(Void... arg0) {
            while (this._run) {
                try {
                    Thread.sleep(1);
                    this.timer++;
                    if (this.timer == CircleFlowIndicator.this.fadeOutTime) {
                        this._run = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            CircleFlowIndicator.this.animation = AnimationUtils.loadAnimation(CircleFlowIndicator.this.getContext(), 17432577);
            CircleFlowIndicator.this.animation.setAnimationListener(CircleFlowIndicator.this.animationListener);
            CircleFlowIndicator.this.startAnimation(CircleFlowIndicator.this.animation);
        }
    }

    public CircleFlowIndicator(Context context) {
        super(context);
        initColors(0, -1, 1, 0);
    }

    public CircleFlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initColors(int activeColor, int inactiveColor, int activeType, int inactiveType) {
        switch (inactiveType) {
            case 1:
                this.mPaintInactive.setStyle(Style.FILL);
                break;
            default:
                this.mPaintInactive.setStyle(Style.STROKE);
                break;
        }
        this.mPaintInactive.setColor(inactiveColor);
        switch (activeType) {
            case 0:
                this.mPaintActive.setStyle(Style.STROKE);
                break;
            default:
                this.mPaintActive.setStyle(Style.FILL);
                break;
        }
        this.mPaintActive.setColor(activeColor);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = 3;
        if (this.viewFlow != null) {
            count = this.viewFlow.getViewsCount();
        }
        int leftPadding = getPaddingLeft();
        for (int iLoop = 0; iLoop < count; iLoop++) {
            canvas.drawCircle(((((float) leftPadding) + this.radius) + (((float) iLoop) * this.circleSeparation)) + 0.0f, ((float) getPaddingTop()) + this.radius, this.radius, this.mPaintInactive);
        }
        float cx = 0.0f;
        if (this.flowWidth != 0) {
            cx = (((float) this.currentScroll) * this.circleSeparation) / ((float) this.flowWidth);
        }
        canvas.drawCircle(((((float) leftPadding) + this.radius) + cx) + 0.0f, ((float) getPaddingTop()) + this.radius, this.radius + this.activeRadius, this.mPaintActive);
    }

    public void onSwitched(View view, int position) {
    }

    public void setViewFlow(ViewFlow view) {
        resetTimer();
        this.viewFlow = view;
        this.flowWidth = this.viewFlow.getWidth();
        invalidate();
    }

    public void onScrolled(int h, int v, int oldh, int oldv) {
        setVisibility(0);
        resetTimer();
        this.flowWidth = this.viewFlow.getWidth();
        if (this.viewFlow.getViewsCount() * this.flowWidth != 0) {
            this.currentScroll = h % (this.viewFlow.getViewsCount() * this.flowWidth);
        } else {
            this.currentScroll = h;
        }
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        int count = 3;
        if (this.viewFlow != null) {
            count = this.viewFlow.getViewsCount();
        }
        int result = (int) (((((float) (getPaddingLeft() + getPaddingRight())) + (((float) (count * 2)) * this.radius)) + (((float) (count - 1)) * (this.circleSeparation - (2.0f * this.radius)))) + 1.0f);
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        int result = (int) ((((2.0f * this.radius) + ((float) getPaddingTop())) + ((float) getPaddingBottom())) + 1.0f);
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    public void setFillColor(int color) {
        this.mPaintActive.setColor(color);
        invalidate();
    }

    public void setStrokeColor(int color) {
        this.mPaintInactive.setColor(color);
        invalidate();
    }

    private void resetTimer() {
        if (this.fadeOutTime <= 0) {
            return;
        }
        if (this.timer == null || !this.timer._run) {
            this.timer = new FadeTimer();
            this.timer.execute(new Void[0]);
            return;
        }
        this.timer.resetTimer();
    }

    public void onAnimationEnd(Animation animation) {
        setVisibility(8);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
