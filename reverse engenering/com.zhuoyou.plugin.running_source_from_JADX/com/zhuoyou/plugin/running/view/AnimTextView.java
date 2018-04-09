package com.zhuoyou.plugin.running.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import java.text.DecimalFormat;

public class AnimTextView extends TextView {
    private static final int RUNNING = 1;
    private static final int STOPPED = 0;
    private long duration = 800;
    private DecimalFormat fnum = new DecimalFormat("#0.00");
    private EndListener mEndListener = null;
    private int mPlayingState = 0;
    private float number;
    private int numberType = 1;

    class C19301 implements AnimatorUpdateListener {
        C19301() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AnimTextView.this.setText(AnimTextView.this.fnum.format((double) Float.parseFloat(valueAnimator.getAnimatedValue().toString())));
            if (valueAnimator.getAnimatedFraction() >= 1.0f) {
                AnimTextView.this.mPlayingState = 0;
                if (AnimTextView.this.mEndListener != null) {
                    AnimTextView.this.mEndListener.onEndFinish();
                }
            }
        }
    }

    class C19312 implements AnimatorUpdateListener {
        C19312() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AnimTextView.this.setText(valueAnimator.getAnimatedValue().toString());
            if (valueAnimator.getAnimatedFraction() >= 1.0f) {
                AnimTextView.this.mPlayingState = 0;
                if (AnimTextView.this.mEndListener != null) {
                    AnimTextView.this.mEndListener.onEndFinish();
                }
            }
        }
    }

    class C19323 implements AnimatorUpdateListener {
        C19323() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AnimTextView.this.setText(valueAnimator.getAnimatedValue().toString() + "%");
            if (valueAnimator.getAnimatedFraction() >= 1.0f) {
                AnimTextView.this.mPlayingState = 0;
                if (AnimTextView.this.mEndListener != null) {
                    AnimTextView.this.mEndListener.onEndFinish();
                }
            }
        }
    }

    public interface EndListener {
        void onEndFinish();
    }

    public AnimTextView(Context context) {
        super(context);
    }

    public AnimTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public AnimTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    public boolean isRunning() {
        return this.mPlayingState == 1;
    }

    private void runFloat() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, this.number});
        valueAnimator.setDuration(this.duration);
        valueAnimator.addUpdateListener(new C19301());
        valueAnimator.start();
    }

    private void runInt() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{0, (int) this.number});
        valueAnimator.setDuration(this.duration);
        valueAnimator.addUpdateListener(new C19312());
        valueAnimator.start();
    }

    private void runPercent() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{0, (int) this.number});
        valueAnimator.setDuration(this.duration);
        valueAnimator.addUpdateListener(new C19323());
        valueAnimator.start();
    }

    public void start() {
        if (!isRunning()) {
            this.mPlayingState = 1;
            switch (this.numberType) {
                case 1:
                    runInt();
                    return;
                case 2:
                    runFloat();
                    return;
                case 3:
                    runPercent();
                    return;
                default:
                    return;
            }
        }
    }

    public void withNumber(float number) {
        if (this.number != number) {
            this.number = number;
            this.numberType = 2;
            start();
        }
    }

    public void withNumber(int number) {
        if (this.number != ((float) number)) {
            this.number = (float) number;
            this.numberType = 1;
            start();
        }
    }

    public void withPercent(int number) {
        if (this.number != ((float) number)) {
            this.number = (float) number;
            this.numberType = 3;
            start();
        }
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setOnEndListener(EndListener callback) {
        this.mEndListener = callback;
    }
}
