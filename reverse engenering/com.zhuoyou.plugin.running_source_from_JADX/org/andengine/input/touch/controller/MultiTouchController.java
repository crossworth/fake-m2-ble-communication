package org.andengine.input.touch.controller;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class MultiTouchController extends BaseTouchController {
    public void onHandleMotionEvent(MotionEvent pMotionEvent) {
        int action = pMotionEvent.getAction() & 255;
        switch (action) {
            case 0:
            case 5:
                onHandleTouchAction(0, pMotionEvent);
                return;
            case 1:
            case 6:
                onHandleTouchAction(1, pMotionEvent);
                return;
            case 2:
                onHandleTouchMove(pMotionEvent);
                return;
            case 3:
            case 4:
                onHandleTouchAction(action, pMotionEvent);
                return;
            default:
                throw new IllegalArgumentException("Invalid Action detected: " + action);
        }
    }

    private void onHandleTouchMove(MotionEvent pMotionEvent) {
        for (int i = pMotionEvent.getPointerCount() - 1; i >= 0; i--) {
            int pointerIndex = i;
            fireTouchEvent(pMotionEvent.getX(pointerIndex), pMotionEvent.getY(pointerIndex), 2, pMotionEvent.getPointerId(pointerIndex), pMotionEvent);
        }
    }

    private void onHandleTouchAction(int pAction, MotionEvent pMotionEvent) {
        int pointerIndex = getPointerIndex(pMotionEvent);
        fireTouchEvent(pMotionEvent.getX(pointerIndex), pMotionEvent.getY(pointerIndex), pAction, pMotionEvent.getPointerId(pointerIndex), pMotionEvent);
    }

    private static int getPointerIndex(MotionEvent pMotionEvent) {
        return (pMotionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }
}
