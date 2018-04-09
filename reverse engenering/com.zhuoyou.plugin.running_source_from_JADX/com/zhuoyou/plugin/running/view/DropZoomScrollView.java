package com.zhuoyou.plugin.running.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import com.baidu.mapapi.map.HeatMap;

public class DropZoomScrollView extends ScrollView implements OnTouchListener {
    private View dropZoomView;
    private int dropZoomViewHeight;
    private int dropZoomViewWidth;
    private float mFirstPosition = 0.0f;
    private Boolean mScaling = Boolean.valueOf(false);

    public DropZoomScrollView(Context context) {
        super(context);
    }

    public DropZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        setOverScrollMode(2);
        if (getChildAt(0) != null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                this.dropZoomView = vg.getChildAt(0);
                setOnTouchListener(this);
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (this.dropZoomViewWidth <= 0 || this.dropZoomViewHeight <= 0) {
            this.dropZoomViewWidth = this.dropZoomView.getMeasuredWidth();
            this.dropZoomViewHeight = this.dropZoomView.getMeasuredHeight();
        }
        switch (event.getAction()) {
            case 1:
                this.mScaling = Boolean.valueOf(false);
                replyImage();
                break;
            case 2:
                if (!this.mScaling.booleanValue()) {
                    if (getScrollY() == 0) {
                        this.mFirstPosition = event.getY();
                    }
                }
                int distance = (int) (((double) (event.getY() - this.mFirstPosition)) * HeatMap.DEFAULT_OPACITY);
                if (distance >= 0) {
                    this.mScaling = Boolean.valueOf(true);
                    setZoom((float) (distance + 1));
                    return true;
                }
                break;
        }
        return false;
    }

    public void replyImage() {
        final float distance = (float) (this.dropZoomView.getMeasuredWidth() - this.dropZoomViewWidth);
        ValueAnimator anim = ObjectAnimator.ofFloat(new float[]{0.0f, 1.0f}).setDuration((long) (((double) distance) * 0.7d));
        anim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                DropZoomScrollView.this.setZoom(distance - (distance * ((Float) animation.getAnimatedValue()).floatValue()));
            }
        });
        anim.start();
    }

    public void setZoom(float s) {
        if (this.dropZoomViewHeight > 0 && this.dropZoomViewWidth > 0) {
            LayoutParams lp = this.dropZoomView.getLayoutParams();
            lp.width = (int) (((float) this.dropZoomViewWidth) + s);
            lp.height = (int) (((float) this.dropZoomViewHeight) * ((((float) this.dropZoomViewWidth) + s) / ((float) this.dropZoomViewWidth)));
            this.dropZoomView.setLayoutParams(lp);
        }
    }
}
