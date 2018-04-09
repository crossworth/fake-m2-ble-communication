package org.andengine.input.touch.detector;

import android.view.MotionEvent;
import org.andengine.input.touch.TouchEvent;

public class HoldDetector extends BaseDetector {
    protected static final float TRIGGER_HOLD_MAXIMUM_DISTANCE_DEFAULT = 10.0f;
    protected static final long TRIGGER_HOLD_MINIMUM_MILLISECONDS_DEFAULT = 200;
    protected long mDownTimeMilliseconds;
    protected float mDownX;
    protected float mDownY;
    protected final IHoldDetectorListener mHoldDetectorListener;
    protected float mHoldX;
    protected float mHoldY;
    protected boolean mMaximumDistanceExceeded;
    protected int mPointerID;
    protected float mTriggerHoldMaximumDistance;
    protected long mTriggerHoldMinimumMilliseconds;
    protected boolean mTriggering;

    public interface IHoldDetectorListener {
        void onHold(HoldDetector holdDetector, long j, int i, float f, float f2);

        void onHoldFinished(HoldDetector holdDetector, long j, int i, float f, float f2);

        void onHoldStarted(HoldDetector holdDetector, int i, float f, float f2);
    }

    public HoldDetector(IHoldDetectorListener pHoldDetectorListener) {
        this(TRIGGER_HOLD_MINIMUM_MILLISECONDS_DEFAULT, 10.0f, pHoldDetectorListener);
    }

    public HoldDetector(long pTriggerHoldMinimumMilliseconds, float pTriggerHoldMaximumDistance, IHoldDetectorListener pHoldDetectorListener) {
        this.mPointerID = -1;
        this.mDownTimeMilliseconds = Long.MIN_VALUE;
        setTriggerHoldMinimumMilliseconds(pTriggerHoldMinimumMilliseconds);
        setTriggerHoldMaximumDistance(pTriggerHoldMaximumDistance);
        this.mHoldDetectorListener = pHoldDetectorListener;
    }

    public long getTriggerHoldMinimumMilliseconds() {
        return this.mTriggerHoldMinimumMilliseconds;
    }

    public void setTriggerHoldMinimumMilliseconds(long pTriggerHoldMinimumMilliseconds) {
        if (pTriggerHoldMinimumMilliseconds < 0) {
            throw new IllegalArgumentException("pTriggerHoldMinimumMilliseconds must not be < 0.");
        }
        this.mTriggerHoldMinimumMilliseconds = pTriggerHoldMinimumMilliseconds;
    }

    public float getTriggerHoldMaximumDistance() {
        return this.mTriggerHoldMaximumDistance;
    }

    public void setTriggerHoldMaximumDistance(float pTriggerHoldMaximumDistance) {
        if (pTriggerHoldMaximumDistance < 0.0f) {
            throw new IllegalArgumentException("pTriggerHoldMaximumDistance must not be < 0.");
        }
        this.mTriggerHoldMaximumDistance = pTriggerHoldMaximumDistance;
    }

    public boolean isHolding() {
        return this.mTriggering;
    }

    public void reset() {
        if (this.mTriggering) {
            triggerOnHoldFinished(System.currentTimeMillis() - this.mDownTimeMilliseconds);
        }
        this.mTriggering = false;
        this.mMaximumDistanceExceeded = false;
        this.mDownTimeMilliseconds = Long.MIN_VALUE;
        this.mPointerID = -1;
    }

    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        boolean z = false;
        MotionEvent motionEvent = pSceneTouchEvent.getMotionEvent();
        long holdTimeMilliseconds;
        float triggerHoldMaximumDistance;
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                if (this.mPointerID != -1) {
                    return false;
                }
                prepareHold(pSceneTouchEvent);
                return true;
            case 1:
            case 3:
                if (this.mPointerID != pSceneTouchEvent.getPointerID()) {
                    return false;
                }
                this.mHoldX = pSceneTouchEvent.getX();
                this.mHoldY = pSceneTouchEvent.getY();
                holdTimeMilliseconds = System.currentTimeMillis() - this.mDownTimeMilliseconds;
                if (holdTimeMilliseconds >= this.mTriggerHoldMinimumMilliseconds) {
                    if (this.mTriggering) {
                        triggerOnHoldFinished(holdTimeMilliseconds);
                    } else {
                        triggerHoldMaximumDistance = this.mTriggerHoldMaximumDistance;
                        if (this.mMaximumDistanceExceeded || Math.abs(this.mDownX - motionEvent.getX()) > triggerHoldMaximumDistance || Math.abs(this.mDownY - motionEvent.getY()) > triggerHoldMaximumDistance) {
                            z = true;
                        }
                        this.mMaximumDistanceExceeded = z;
                        if (!this.mMaximumDistanceExceeded) {
                            triggerOnHoldFinished(holdTimeMilliseconds);
                        }
                    }
                }
                this.mPointerID = -1;
                return true;
            case 2:
                if (this.mPointerID != pSceneTouchEvent.getPointerID()) {
                    return false;
                }
                this.mHoldX = pSceneTouchEvent.getX();
                this.mHoldY = pSceneTouchEvent.getY();
                holdTimeMilliseconds = System.currentTimeMillis() - this.mDownTimeMilliseconds;
                if (holdTimeMilliseconds >= this.mTriggerHoldMinimumMilliseconds) {
                    if (this.mTriggering) {
                        triggerOnHold(holdTimeMilliseconds);
                    } else {
                        triggerHoldMaximumDistance = this.mTriggerHoldMaximumDistance;
                        if (this.mMaximumDistanceExceeded || Math.abs(this.mDownX - motionEvent.getX()) > triggerHoldMaximumDistance || Math.abs(this.mDownY - motionEvent.getY()) > triggerHoldMaximumDistance) {
                            z = true;
                        }
                        this.mMaximumDistanceExceeded = z;
                        if (!this.mMaximumDistanceExceeded) {
                            if (this.mTriggering) {
                                triggerOnHold(holdTimeMilliseconds);
                            } else {
                                triggerOnHoldStarted();
                            }
                        }
                    }
                }
                return true;
            default:
                return false;
        }
    }

    protected void prepareHold(TouchEvent pSceneTouchEvent) {
        MotionEvent motionEvent = pSceneTouchEvent.getMotionEvent();
        this.mDownTimeMilliseconds = System.currentTimeMillis();
        this.mDownX = motionEvent.getX();
        this.mDownY = motionEvent.getY();
        this.mMaximumDistanceExceeded = false;
        this.mPointerID = pSceneTouchEvent.getPointerID();
        this.mHoldX = pSceneTouchEvent.getX();
        this.mHoldY = pSceneTouchEvent.getY();
        if (this.mTriggerHoldMinimumMilliseconds == 0) {
            triggerOnHoldStarted();
        }
    }

    protected void triggerOnHoldStarted() {
        this.mTriggering = true;
        if (this.mPointerID != -1) {
            this.mHoldDetectorListener.onHoldStarted(this, this.mPointerID, this.mHoldX, this.mHoldY);
        }
    }

    protected void triggerOnHold(long pHoldTimeMilliseconds) {
        if (this.mPointerID != -1) {
            this.mHoldDetectorListener.onHold(this, pHoldTimeMilliseconds, this.mPointerID, this.mHoldX, this.mHoldY);
        }
    }

    protected void triggerOnHoldFinished(long pHoldTimeMilliseconds) {
        this.mTriggering = false;
        if (this.mPointerID != -1) {
            this.mHoldDetectorListener.onHoldFinished(this, pHoldTimeMilliseconds, this.mPointerID, this.mHoldX, this.mHoldY);
        }
    }
}
