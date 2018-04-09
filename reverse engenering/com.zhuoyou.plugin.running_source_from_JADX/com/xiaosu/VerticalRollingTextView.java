package com.xiaosu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class VerticalRollingTextView extends View {
    Rect bounds = new Rect();
    private boolean isRunning;
    private int mAnimInterval = 2000;
    private InternalAnimation mAnimation = new InternalAnimation();
    private boolean mAnimationEnded;
    private int mCurrentIndex;
    private float mCurrentOffsetY;
    DataSetAdapter mDataSetAdapter;
    private int mDuration = 1000;
    private int mNextIndex;
    private float mOffset;
    private float mOrgOffsetY = -1.0f;
    private final Paint mPaint = new Paint(1);
    Runnable mRollingTask = new C16781();
    private final float mTextTopToAscentOffset;

    class C16781 implements Runnable {
        C16781() {
        }

        public void run() {
            VerticalRollingTextView.this.mAnimationEnded = false;
            VerticalRollingTextView.this.startAnimation(VerticalRollingTextView.this.mAnimation);
            VerticalRollingTextView.this.postDelayed(this, (long) VerticalRollingTextView.this.mAnimInterval);
        }
    }

    class InternalAnimation extends Animation {
        float endValue;
        float startValue;

        InternalAnimation() {
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (!VerticalRollingTextView.this.mAnimationEnded) {
                VerticalRollingTextView.this.mCurrentOffsetY = VerticalRollingTextView.this.evaluate(interpolatedTime, this.startValue, this.endValue);
                if (VerticalRollingTextView.this.mCurrentOffsetY == this.endValue) {
                    VerticalRollingTextView.this.animationEnd();
                }
                VerticalRollingTextView.this.postInvalidate();
            }
        }

        public void updateValue(float startValue, float endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(VerticalRollingTextView verticalRollingTextView, int i);
    }

    public VerticalRollingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaint.setColor(-16777216);
        this.mPaint.setTypeface(Typeface.DEFAULT);
        parseAttrs(context, attrs);
        FontMetricsInt metricsInt = this.mPaint.getFontMetricsInt();
        this.mTextTopToAscentOffset = (float) (metricsInt.ascent - metricsInt.top);
        this.mAnimation.setDuration((long) this.mDuration);
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        float density = getResources().getDisplayMetrics().density;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, C1677R.styleable.VerticalRollingTextView);
        this.mPaint.setColor(typedArray.getColor(C1677R.styleable.VerticalRollingTextView_android_textColor, -16777216));
        this.mPaint.setTextSize((float) typedArray.getDimensionPixelOffset(C1677R.styleable.VerticalRollingTextView_android_textSize, (int) (14.0f * density)));
        this.mDuration = typedArray.getInt(C1677R.styleable.VerticalRollingTextView_android_duration, this.mDuration);
        this.mAnimInterval = typedArray.getInt(C1677R.styleable.VerticalRollingTextView_animInterval, this.mAnimInterval);
        typedArray.recycle();
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDataSetAdapter != null && !this.mDataSetAdapter.isEmpty()) {
            String text1 = this.mDataSetAdapter.getText(this.mCurrentIndex);
            String text2 = this.mDataSetAdapter.getText(this.mNextIndex);
            if (this.mOrgOffsetY == -1.0f) {
                this.mPaint.getTextBounds(text1, 0, text1.length(), this.bounds);
                this.mOffset = ((float) (getHeight() + this.bounds.height())) * 0.5f;
                float f = this.mOffset - this.mTextTopToAscentOffset;
                this.mCurrentOffsetY = f;
                this.mOrgOffsetY = f;
                this.mAnimation.updateValue(this.mOrgOffsetY, -2.0f * this.mTextTopToAscentOffset);
            }
            canvas.drawText(text1, 0.0f, this.mCurrentOffsetY, this.mPaint);
            canvas.drawText(text2, 0.0f, (this.mCurrentOffsetY + this.mOffset) + this.mTextTopToAscentOffset, this.mPaint);
        }
    }

    public void setDataSetAdapter(DataSetAdapter dataSetAdapter) {
        this.mDataSetAdapter = dataSetAdapter;
        confirmNextIndex();
        invalidate();
    }

    public void run() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.mAnimation.updateValue(this.mCurrentOffsetY, -2.0f * this.mTextTopToAscentOffset);
            post(this.mRollingTask);
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void stop() {
        this.isRunning = false;
        removeCallbacks(this.mRollingTask);
    }

    public void animationEnd() {
        this.mCurrentIndex++;
        this.mCurrentIndex = this.mCurrentIndex < this.mDataSetAdapter.getItemCount() ? this.mCurrentIndex : this.mCurrentIndex % this.mDataSetAdapter.getItemCount();
        confirmNextIndex();
        this.mCurrentOffsetY = this.mOrgOffsetY;
        this.mAnimationEnded = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mRollingTask);
        if (isRunning()) {
            this.mAnimation.cancel();
        }
    }

    private void confirmNextIndex() {
        this.mNextIndex = this.mCurrentIndex + 1;
        this.mNextIndex = this.mNextIndex < this.mDataSetAdapter.getItemCount() ? this.mNextIndex : 0;
    }

    float evaluate(float fraction, float startValue, float endValue) {
        return ((endValue - startValue) * fraction) + startValue;
    }

    public void setOnClickListener(OnClickListener l) {
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        super.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                onItemClickListener.onItemClick(VerticalRollingTextView.this, VerticalRollingTextView.this.mCurrentIndex);
            }
        });
    }
}
