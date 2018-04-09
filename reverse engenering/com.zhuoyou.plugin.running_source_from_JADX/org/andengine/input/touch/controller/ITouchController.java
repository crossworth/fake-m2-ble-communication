package org.andengine.input.touch.controller;

import android.view.MotionEvent;
import org.andengine.engine.handler.IUpdateHandler;

public interface ITouchController extends IUpdateHandler {
    void onHandleMotionEvent(MotionEvent motionEvent);

    void setTouchEventCallback(ITouchEventCallback iTouchEventCallback);
}
