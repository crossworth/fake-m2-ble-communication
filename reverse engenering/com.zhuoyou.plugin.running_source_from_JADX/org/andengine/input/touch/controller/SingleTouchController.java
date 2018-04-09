package org.andengine.input.touch.controller;

import android.view.MotionEvent;

public class SingleTouchController extends BaseTouchController {
    public void onHandleMotionEvent(MotionEvent pMotionEvent) {
        fireTouchEvent(pMotionEvent.getX(), pMotionEvent.getY(), pMotionEvent.getAction(), 0, pMotionEvent);
    }
}
