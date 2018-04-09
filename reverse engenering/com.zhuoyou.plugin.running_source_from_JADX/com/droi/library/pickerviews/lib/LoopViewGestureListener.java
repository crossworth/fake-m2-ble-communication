package com.droi.library.pickerviews.lib;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

final class LoopViewGestureListener extends SimpleOnGestureListener {
    final WheelView loopView;

    LoopViewGestureListener(WheelView loopview) {
        this.loopView = loopview;
    }

    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.loopView.scrollBy(velocityY);
        return true;
    }
}
