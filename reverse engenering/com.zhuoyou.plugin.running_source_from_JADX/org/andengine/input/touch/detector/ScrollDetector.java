package org.andengine.input.touch.detector;

import org.andengine.input.touch.TouchEvent;

public class ScrollDetector extends BaseDetector {
    private static final float TRIGGER_SCROLL_MINIMUM_DISTANCE_DEFAULT = 10.0f;
    private float mLastX;
    private float mLastY;
    private int mPointerID;
    private final IScrollDetectorListener mScrollDetectorListener;
    private float mTriggerScrollMinimumDistance;
    private boolean mTriggering;

    public interface IScrollDetectorListener {
        void onScroll(ScrollDetector scrollDetector, int i, float f, float f2);

        void onScrollFinished(ScrollDetector scrollDetector, int i, float f, float f2);

        void onScrollStarted(ScrollDetector scrollDetector, int i, float f, float f2);
    }

    public ScrollDetector(IScrollDetectorListener pScrollDetectorListener) {
        this(10.0f, pScrollDetectorListener);
    }

    public ScrollDetector(float pTriggerScrollMinimumDistance, IScrollDetectorListener pScrollDetectorListener) {
        this.mPointerID = -1;
        this.mTriggerScrollMinimumDistance = pTriggerScrollMinimumDistance;
        this.mScrollDetectorListener = pScrollDetectorListener;
    }

    public float getTriggerScrollMinimumDistance() {
        return this.mTriggerScrollMinimumDistance;
    }

    public void setTriggerScrollMinimumDistance(float pTriggerScrollMinimumDistance) {
        this.mTriggerScrollMinimumDistance = pTriggerScrollMinimumDistance;
    }

    public void reset() {
        if (this.mTriggering) {
            this.mScrollDetectorListener.onScrollFinished(this, this.mPointerID, 0.0f, 0.0f);
        }
        this.mLastX = 0.0f;
        this.mLastY = 0.0f;
        this.mTriggering = false;
        this.mPointerID = -1;
    }

    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        float touchX = getX(pSceneTouchEvent);
        float touchY = getY(pSceneTouchEvent);
        float distanceX;
        float distanceY;
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                prepareScroll(pSceneTouchEvent.getPointerID(), touchX, touchY);
                return true;
            case 1:
            case 3:
                if (this.mPointerID != pSceneTouchEvent.getPointerID()) {
                    return true;
                }
                distanceX = touchX - this.mLastX;
                distanceY = touchY - this.mLastY;
                if (this.mTriggering) {
                    triggerOnScrollFinished(distanceX, distanceY);
                }
                this.mPointerID = -1;
                return true;
            case 2:
                if (this.mPointerID == -1) {
                    prepareScroll(pSceneTouchEvent.getPointerID(), touchX, touchY);
                    return true;
                } else if (this.mPointerID != pSceneTouchEvent.getPointerID()) {
                    return false;
                } else {
                    distanceX = touchX - this.mLastX;
                    distanceY = touchY - this.mLastY;
                    float triggerScrollMinimumDistance = this.mTriggerScrollMinimumDistance;
                    if (!this.mTriggering && Math.abs(distanceX) <= triggerScrollMinimumDistance && Math.abs(distanceY) <= triggerScrollMinimumDistance) {
                        return true;
                    }
                    if (this.mTriggering) {
                        triggerOnScroll(distanceX, distanceY);
                    } else {
                        triggerOnScrollStarted(distanceX, distanceY);
                    }
                    this.mLastX = touchX;
                    this.mLastY = touchY;
                    this.mTriggering = true;
                    return true;
                }
            default:
                return false;
        }
    }

    private void prepareScroll(int pPointerID, float pTouchX, float pTouchY) {
        this.mLastX = pTouchX;
        this.mLastY = pTouchY;
        this.mTriggering = false;
        this.mPointerID = pPointerID;
    }

    private void triggerOnScrollStarted(float pDistanceX, float pDistanceY) {
        if (this.mPointerID != -1) {
            this.mScrollDetectorListener.onScrollStarted(this, this.mPointerID, pDistanceX, pDistanceY);
        }
    }

    private void triggerOnScroll(float pDistanceX, float pDistanceY) {
        if (this.mPointerID != -1) {
            this.mScrollDetectorListener.onScroll(this, this.mPointerID, pDistanceX, pDistanceY);
        }
    }

    private void triggerOnScrollFinished(float pDistanceX, float pDistanceY) {
        this.mTriggering = false;
        if (this.mPointerID != -1) {
            this.mScrollDetectorListener.onScrollFinished(this, this.mPointerID, pDistanceX, pDistanceY);
        }
    }

    protected float getX(TouchEvent pTouchEvent) {
        return pTouchEvent.getX();
    }

    protected float getY(TouchEvent pTouchEvent) {
        return pTouchEvent.getY();
    }
}
