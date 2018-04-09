package org.andengine.input.touch;

import android.view.MotionEvent;
import org.andengine.util.adt.pool.GenericPool;

public class TouchEvent {
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_OUTSIDE = 4;
    public static final int ACTION_UP = 1;
    public static final int INVALID_POINTER_ID = -1;
    private static final TouchEventPool TOUCHEVENT_POOL = new TouchEventPool();
    protected int mAction;
    protected MotionEvent mMotionEvent;
    protected int mPointerID;
    protected float mX;
    protected float mY;

    private static final class TouchEventPool extends GenericPool<TouchEvent> {
        private TouchEventPool() {
        }

        protected TouchEvent onAllocatePoolItem() {
            return new TouchEvent();
        }
    }

    public static TouchEvent obtain(float pX, float pY, int pAction, int pPointerID, MotionEvent pMotionEvent) {
        TouchEvent touchEvent = (TouchEvent) TOUCHEVENT_POOL.obtainPoolItem();
        touchEvent.set(pX, pY, pAction, pPointerID, pMotionEvent);
        return touchEvent;
    }

    private void set(float pX, float pY, int pAction, int pPointerID, MotionEvent pMotionEvent) {
        this.mX = pX;
        this.mY = pY;
        this.mAction = pAction;
        this.mPointerID = pPointerID;
        this.mMotionEvent = pMotionEvent;
    }

    public void recycle() {
        TOUCHEVENT_POOL.recyclePoolItem(this);
    }

    public static void recycle(TouchEvent pTouchEvent) {
        TOUCHEVENT_POOL.recyclePoolItem(pTouchEvent);
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public void set(float pX, float pY) {
        this.mX = pX;
        this.mY = pY;
    }

    public void offset(float pDeltaX, float pDeltaY) {
        this.mX += pDeltaX;
        this.mY += pDeltaY;
    }

    public int getPointerID() {
        return this.mPointerID;
    }

    public int getAction() {
        return this.mAction;
    }

    public boolean isActionDown() {
        return this.mAction == 0;
    }

    public boolean isActionUp() {
        return this.mAction == 1;
    }

    public boolean isActionMove() {
        return this.mAction == 2;
    }

    public boolean isActionCancel() {
        return this.mAction == 3;
    }

    public boolean isActionOutside() {
        return this.mAction == 4;
    }

    public MotionEvent getMotionEvent() {
        return this.mMotionEvent;
    }
}
