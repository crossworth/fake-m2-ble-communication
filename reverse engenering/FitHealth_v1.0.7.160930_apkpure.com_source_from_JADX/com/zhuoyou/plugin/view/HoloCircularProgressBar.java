package com.zhuoyou.plugin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.C1400R;

public class HoloCircularProgressBar extends View {
    private Paint mBackgroundColorPaint;
    private final RectF mCircleBounds;
    private int mCircleStrokeWidth;
    private int mGravity;
    private int mHorizontalInset;
    private boolean mIsInitializing;
    private boolean mOverrdraw;
    private float mProgress;
    private int mProgressBackgroundColor;
    private int mProgressColor;
    private Paint mProgressColorPaint;
    private float mRadius;
    private float mTranslationOffsetX;
    private float mTranslationOffsetY;
    private int mVerticalInset;

    public HoloCircularProgressBar(Context context) {
        this(context, null);
    }

    public HoloCircularProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.circularProgressBarStyle);
    }

    public HoloCircularProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCircleBounds = new RectF();
        this.mBackgroundColorPaint = new Paint();
        this.mCircleStrokeWidth = 10;
        this.mGravity = 17;
        this.mHorizontalInset = 0;
        this.mIsInitializing = true;
        this.mOverrdraw = false;
        this.mProgress = 0.0f;
        this.mVerticalInset = 0;
        TypedArray attributes = context.obtainStyledAttributes(attrs, C1400R.styleable.HoloCircularProgressBar, defStyle, 0);
        if (attributes != null) {
            try {
                setProgressColor(Color.argb(255, 255, 255, 255));
                setProgressBackgroundColor(Color.argb(100, 255, 255, 255));
                setProgress(0.0f);
                setWheelSize(6);
                this.mGravity = 17;
            } finally {
                attributes.recycle();
            }
        }
        updateBackgroundColor();
        updateProgressColor();
        this.mIsInitializing = false;
    }

    protected void onDraw(Canvas canvas) {
        float f;
        canvas.translate(this.mTranslationOffsetX, this.mTranslationOffsetY);
        float progressRotation = getCurrentRotation();
        if (!this.mOverrdraw) {
            canvas.drawArc(this.mCircleBounds, BitmapDescriptorFactory.HUE_VIOLET, -(360.0f - progressRotation), false, this.mBackgroundColorPaint);
        }
        RectF rectF = this.mCircleBounds;
        if (this.mOverrdraw) {
            f = 360.0f;
        } else {
            f = progressRotation;
        }
        canvas.drawArc(rectF, BitmapDescriptorFactory.HUE_VIOLET, f, false, this.mProgressColorPaint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int diameter;
        int height = getDefaultSize((getSuggestedMinimumHeight() + getPaddingTop()) + getPaddingBottom(), heightMeasureSpec);
        int width = getDefaultSize((getSuggestedMinimumWidth() + getPaddingLeft()) + getPaddingRight(), widthMeasureSpec);
        if (heightMeasureSpec == 0) {
            diameter = width;
            computeInsets(0, 0);
        } else if (widthMeasureSpec == 0) {
            diameter = height;
            computeInsets(0, 0);
        } else {
            diameter = Math.min(width, height);
            computeInsets(width - diameter, height - diameter);
        }
        setMeasuredDimension(diameter, diameter);
        float halfWidth = ((float) diameter) * 0.5f;
        this.mRadius = (halfWidth - (((float) this.mCircleStrokeWidth) / 2.0f)) - 0.5f;
        this.mCircleBounds.set(-this.mRadius, -this.mRadius, this.mRadius, this.mRadius);
        this.mTranslationOffsetX = ((float) this.mHorizontalInset) + halfWidth;
        this.mTranslationOffsetY = ((float) this.mVerticalInset) + halfWidth;
    }

    public void setProgress(float progress) {
        if (progress != this.mProgress) {
            if (progress == 1.0f) {
                this.mOverrdraw = false;
                this.mProgress = 1.0f;
            } else {
                if (progress >= 1.0f) {
                    this.mOverrdraw = true;
                } else {
                    this.mOverrdraw = false;
                }
                this.mProgress = progress % 1.0f;
            }
            if (!this.mIsInitializing) {
                invalidate();
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void computeInsets(int dx, int dy) {
        int absoluteGravity = this.mGravity;
        if (VERSION.SDK_INT >= 16) {
            absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection());
        }
        switch (absoluteGravity & 7) {
            case 3:
                this.mHorizontalInset = 0;
                break;
            case 5:
                this.mHorizontalInset = dx;
                break;
            default:
                this.mHorizontalInset = dx / 2;
                break;
        }
        switch (absoluteGravity & 112) {
            case 48:
                this.mVerticalInset = 0;
                return;
            case 80:
                this.mVerticalInset = dy;
                return;
            default:
                this.mVerticalInset = dy / 2;
                return;
        }
    }

    private float getCurrentRotation() {
        return 360.0f * this.mProgress;
    }

    private void setProgressBackgroundColor(int color) {
        this.mProgressBackgroundColor = color;
    }

    private void setProgressColor(int color) {
        this.mProgressColor = color;
    }

    private void setWheelSize(int dimension) {
        this.mCircleStrokeWidth = dimension;
    }

    private void updateBackgroundColor() {
        this.mBackgroundColorPaint = new Paint(1);
        this.mBackgroundColorPaint.setColor(this.mProgressBackgroundColor);
        this.mBackgroundColorPaint.setStyle(Style.STROKE);
        this.mBackgroundColorPaint.setStrokeWidth((float) this.mCircleStrokeWidth);
        invalidate();
    }

    private void updateProgressColor() {
        this.mProgressColorPaint = new Paint(1);
        this.mProgressColorPaint.setColor(this.mProgressColor);
        this.mProgressColorPaint.setStyle(Style.STROKE);
        this.mProgressColorPaint.setStrokeWidth((float) this.mCircleStrokeWidth);
        invalidate();
    }
}
