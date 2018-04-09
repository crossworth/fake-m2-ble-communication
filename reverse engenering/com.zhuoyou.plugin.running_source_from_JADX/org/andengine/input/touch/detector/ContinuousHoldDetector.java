package org.andengine.input.touch.detector;

import android.view.MotionEvent;
import com.tencent.open.yyb.TitleBar;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.HoldDetector.IHoldDetectorListener;

public class ContinuousHoldDetector extends HoldDetector implements IUpdateHandler {
    private static final float TIME_BETWEEN_UPDATES_DEFAULT = 0.1f;
    private final TimerHandler mTimerHandler;

    class C20661 implements ITimerCallback {
        C20661() {
        }

        public void onTimePassed(TimerHandler pTimerHandler) {
            ContinuousHoldDetector.this.fireListener();
        }
    }

    public ContinuousHoldDetector(IHoldDetectorListener pHoldDetectorListener) {
        this(200, TitleBar.SHAREBTN_RIGHT_MARGIN, TIME_BETWEEN_UPDATES_DEFAULT, pHoldDetectorListener);
    }

    public ContinuousHoldDetector(long pTriggerHoldMinimumMilliseconds, float pTriggerHoldMaximumDistance, float pTimeBetweenUpdates, IHoldDetectorListener pHoldDetectorListener) {
        super(pTriggerHoldMinimumMilliseconds, pTriggerHoldMaximumDistance, pHoldDetectorListener);
        this.mTimerHandler = new TimerHandler(pTimeBetweenUpdates, true, new C20661());
    }

    public void onUpdate(float pSecondsElapsed) {
        this.mTimerHandler.onUpdate(pSecondsElapsed);
    }

    public void reset() {
        super.reset();
        this.mTimerHandler.reset();
    }

    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        boolean z = false;
        MotionEvent motionEvent = pSceneTouchEvent.getMotionEvent();
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
                if (this.mTriggering) {
                    triggerOnHoldFinished(motionEvent.getEventTime() - this.mDownTimeMilliseconds);
                }
                this.mPointerID = -1;
                return true;
            case 2:
                if (this.mPointerID != pSceneTouchEvent.getPointerID()) {
                    return false;
                }
                this.mHoldX = pSceneTouchEvent.getX();
                this.mHoldY = pSceneTouchEvent.getY();
                if (this.mMaximumDistanceExceeded || Math.abs(this.mDownX - motionEvent.getX()) > this.mTriggerHoldMaximumDistance || Math.abs(this.mDownY - motionEvent.getY()) > this.mTriggerHoldMaximumDistance) {
                    z = true;
                }
                this.mMaximumDistanceExceeded = z;
                return true;
            default:
                return false;
        }
    }

    protected void prepareHold(TouchEvent pSceneTouchEvent) {
        super.prepareHold(pSceneTouchEvent);
        this.mTimerHandler.reset();
    }

    void fireListener() {
        if (this.mPointerID != -1) {
            long holdTimeMilliseconds = System.currentTimeMillis() - this.mDownTimeMilliseconds;
            if (holdTimeMilliseconds < this.mTriggerHoldMinimumMilliseconds) {
                return;
            }
            if (this.mTriggering) {
                triggerOnHold(holdTimeMilliseconds);
            } else if (!this.mMaximumDistanceExceeded) {
                triggerOnHoldStarted();
            }
        }
    }
}
