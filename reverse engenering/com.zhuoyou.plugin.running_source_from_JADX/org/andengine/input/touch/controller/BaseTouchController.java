package org.andengine.input.touch.controller;

import android.view.MotionEvent;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.pool.RunnablePoolItem;
import org.andengine.util.adt.pool.RunnablePoolUpdateHandler;

public abstract class BaseTouchController implements ITouchController {
    private ITouchEventCallback mTouchEventCallback;
    private final RunnablePoolUpdateHandler<TouchEventRunnablePoolItem> mTouchEventRunnablePoolUpdateHandler = new C20651();

    class C20651 extends RunnablePoolUpdateHandler<TouchEventRunnablePoolItem> {
        C20651() {
        }

        protected TouchEventRunnablePoolItem onAllocatePoolItem() {
            return new TouchEventRunnablePoolItem();
        }
    }

    class TouchEventRunnablePoolItem extends RunnablePoolItem {
        private TouchEvent mTouchEvent;

        TouchEventRunnablePoolItem() {
        }

        public void set(TouchEvent pTouchEvent) {
            this.mTouchEvent = pTouchEvent;
        }

        public void run() {
            BaseTouchController.this.mTouchEventCallback.onTouchEvent(this.mTouchEvent);
        }

        protected void onRecycle() {
            super.onRecycle();
            TouchEvent touchEvent = this.mTouchEvent;
            touchEvent.getMotionEvent().recycle();
            touchEvent.recycle();
        }
    }

    public void setTouchEventCallback(ITouchEventCallback pTouchEventCallback) {
        this.mTouchEventCallback = pTouchEventCallback;
    }

    public void reset() {
        this.mTouchEventRunnablePoolUpdateHandler.reset();
    }

    public void onUpdate(float pSecondsElapsed) {
        this.mTouchEventRunnablePoolUpdateHandler.onUpdate(pSecondsElapsed);
    }

    protected void fireTouchEvent(float pX, float pY, int pAction, int pPointerID, MotionEvent pMotionEvent) {
        TouchEventRunnablePoolItem touchEventRunnablePoolItem = (TouchEventRunnablePoolItem) this.mTouchEventRunnablePoolUpdateHandler.obtainPoolItem();
        touchEventRunnablePoolItem.set(TouchEvent.obtain(pX, pY, pAction, pPointerID, MotionEvent.obtain(pMotionEvent)));
        this.mTouchEventRunnablePoolUpdateHandler.postPoolItem(touchEventRunnablePoolItem);
    }
}
