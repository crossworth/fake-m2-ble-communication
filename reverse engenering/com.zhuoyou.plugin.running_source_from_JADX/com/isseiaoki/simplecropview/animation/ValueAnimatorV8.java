package com.isseiaoki.simplecropview.animation;

import android.os.SystemClock;
import android.view.animation.Interpolator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ValueAnimatorV8 implements SimpleValueAnimator {
    private static final int DEFAULT_ANIMATION_DURATION = 150;
    private static final int FRAME_RATE = 30;
    private static final int UPDATE_SPAN = Math.round(33.333332f);
    private SimpleValueAnimatorListener animatorListener = new C10801();
    long duration;
    boolean isAnimationStarted = false;
    private Interpolator mInterpolator;
    private final Runnable runnable = new C10812();
    ScheduledExecutorService service;
    long start;

    class C10801 implements SimpleValueAnimatorListener {
        C10801() {
        }

        public void onAnimationStarted() {
        }

        public void onAnimationUpdated(float scale) {
        }

        public void onAnimationFinished() {
        }
    }

    class C10812 implements Runnable {
        C10812() {
        }

        public void run() {
            long elapsed = SystemClock.uptimeMillis() - ValueAnimatorV8.this.start;
            if (elapsed > ValueAnimatorV8.this.duration) {
                ValueAnimatorV8.this.isAnimationStarted = false;
                ValueAnimatorV8.this.animatorListener.onAnimationFinished();
                ValueAnimatorV8.this.service.shutdown();
                return;
            }
            ValueAnimatorV8.this.animatorListener.onAnimationUpdated(Math.min(ValueAnimatorV8.this.mInterpolator.getInterpolation(((float) elapsed) / ((float) ValueAnimatorV8.this.duration)), 1.0f));
        }
    }

    public ValueAnimatorV8(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void startAnimation(long duration) {
        if (duration >= 0) {
            this.duration = duration;
        } else {
            this.duration = 150;
        }
        this.isAnimationStarted = true;
        this.animatorListener.onAnimationStarted();
        this.start = SystemClock.uptimeMillis();
        this.service = Executors.newSingleThreadScheduledExecutor();
        this.service.scheduleAtFixedRate(this.runnable, 0, (long) UPDATE_SPAN, TimeUnit.MILLISECONDS);
    }

    public void cancelAnimation() {
        this.isAnimationStarted = false;
        this.service.shutdown();
        this.animatorListener.onAnimationFinished();
    }

    public boolean isAnimationStarted() {
        return this.isAnimationStarted;
    }

    public void addAnimatorListener(SimpleValueAnimatorListener animatorListener) {
        if (animatorListener != null) {
            this.animatorListener = animatorListener;
        }
    }
}
