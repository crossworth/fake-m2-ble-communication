package org.andengine.input.touch.detector;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import org.andengine.input.touch.TouchEvent;

public abstract class SurfaceGestureDetector extends BaseDetector {
    private static final float SWIPE_MIN_DISTANCE_DEFAULT = 120.0f;
    private final GestureDetector mGestureDetector;

    private class InnerOnGestureDetectorListener extends SimpleOnGestureListener {
        private final float mSwipeMinDistance;

        public InnerOnGestureDetectorListener(float pSwipeMinDistance) {
            this.mSwipeMinDistance = pSwipeMinDistance;
        }

        public boolean onSingleTapConfirmed(MotionEvent pMotionEvent) {
            return SurfaceGestureDetector.this.onSingleTap();
        }

        public boolean onDoubleTap(MotionEvent pMotionEvent) {
            return SurfaceGestureDetector.this.onDoubleTap();
        }

        public boolean onFling(MotionEvent pMotionEventStart, MotionEvent pMotionEventEnd, float pVelocityX, float pVelocityY) {
            boolean isHorizontalFling;
            float swipeMinDistance = this.mSwipeMinDistance;
            if (Math.abs(pVelocityX) > Math.abs(pVelocityY)) {
                isHorizontalFling = true;
            } else {
                isHorizontalFling = false;
            }
            if (isHorizontalFling) {
                if (pMotionEventStart.getX() - pMotionEventEnd.getX() > swipeMinDistance) {
                    return SurfaceGestureDetector.this.onSwipeLeft();
                }
                if (pMotionEventEnd.getX() - pMotionEventStart.getX() > swipeMinDistance) {
                    return SurfaceGestureDetector.this.onSwipeRight();
                }
                return false;
            } else if (pMotionEventStart.getY() - pMotionEventEnd.getY() > swipeMinDistance) {
                return SurfaceGestureDetector.this.onSwipeUp();
            } else {
                if (pMotionEventEnd.getY() - pMotionEventStart.getY() > swipeMinDistance) {
                    return SurfaceGestureDetector.this.onSwipeDown();
                }
                return false;
            }
        }
    }

    protected abstract boolean onDoubleTap();

    protected abstract boolean onSingleTap();

    protected abstract boolean onSwipeDown();

    protected abstract boolean onSwipeLeft();

    protected abstract boolean onSwipeRight();

    protected abstract boolean onSwipeUp();

    public SurfaceGestureDetector(Context pContext) {
        this(pContext, SWIPE_MIN_DISTANCE_DEFAULT);
    }

    public SurfaceGestureDetector(Context pContext, float pSwipeMinDistance) {
        this.mGestureDetector = new GestureDetector(pContext, new InnerOnGestureDetectorListener(pSwipeMinDistance));
    }

    public void reset() {
    }

    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        return this.mGestureDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());
    }
}
