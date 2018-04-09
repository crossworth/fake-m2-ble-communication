package org.andengine.input.touch.detector;

import org.andengine.input.touch.TouchEvent;

public class ClickDetector extends BaseDetector {
    private static final long TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT = 200;
    private final IClickDetectorListener mClickDetectorListener;
    private long mDownTimeMilliseconds;
    private int mPointerID;
    private long mTriggerClickMaximumMilliseconds;

    public interface IClickDetectorListener {
        void onClick(ClickDetector clickDetector, int i, float f, float f2);
    }

    public ClickDetector(IClickDetectorListener pClickDetectorListener) {
        this(TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT, pClickDetectorListener);
    }

    public ClickDetector(long pTriggerClickMaximumMilliseconds, IClickDetectorListener pClickDetectorListener) {
        this.mPointerID = -1;
        this.mDownTimeMilliseconds = Long.MIN_VALUE;
        this.mTriggerClickMaximumMilliseconds = pTriggerClickMaximumMilliseconds;
        this.mClickDetectorListener = pClickDetectorListener;
    }

    public long getTriggerClickMaximumMilliseconds() {
        return this.mTriggerClickMaximumMilliseconds;
    }

    public void setTriggerClickMaximumMilliseconds(long pClickMaximumMilliseconds) {
        this.mTriggerClickMaximumMilliseconds = pClickMaximumMilliseconds;
    }

    public void reset() {
        this.mDownTimeMilliseconds = Long.MIN_VALUE;
        this.mPointerID = -1;
    }

    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                prepareClick(pSceneTouchEvent);
                return true;
            case 1:
            case 3:
                if (this.mPointerID != pSceneTouchEvent.getPointerID()) {
                    return false;
                }
                boolean handled = false;
                if (pSceneTouchEvent.getMotionEvent().getEventTime() - this.mDownTimeMilliseconds <= this.mTriggerClickMaximumMilliseconds) {
                    this.mDownTimeMilliseconds = Long.MIN_VALUE;
                    this.mClickDetectorListener.onClick(this, pSceneTouchEvent.getPointerID(), pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                    handled = true;
                }
                this.mPointerID = -1;
                return handled;
            default:
                return false;
        }
    }

    private void prepareClick(TouchEvent pSceneTouchEvent) {
        this.mDownTimeMilliseconds = pSceneTouchEvent.getMotionEvent().getDownTime();
        this.mPointerID = pSceneTouchEvent.getPointerID();
    }
}
