package org.andengine.input.touch.detector;

import android.content.Context;

public class SurfaceGestureDetectorAdapter extends SurfaceGestureDetector {
    public SurfaceGestureDetectorAdapter(Context pContext) {
        super(pContext);
    }

    public SurfaceGestureDetectorAdapter(Context pContext, float pSwipeMinDistance) {
        super(pContext, pSwipeMinDistance);
    }

    protected boolean onDoubleTap() {
        return false;
    }

    protected boolean onSingleTap() {
        return false;
    }

    protected boolean onSwipeDown() {
        return false;
    }

    protected boolean onSwipeLeft() {
        return false;
    }

    protected boolean onSwipeRight() {
        return false;
    }

    protected boolean onSwipeUp() {
        return false;
    }
}
