package org.andengine.engine.options;

public class TouchOptions {
    private static final long TOUCHEVENT_INTERVAL_MILLISECONDS_DEFAULT = 20;
    private boolean mNeedsMultiTouch;
    private long mTouchEventIntervalMilliseconds = TOUCHEVENT_INTERVAL_MILLISECONDS_DEFAULT;

    public boolean needsMultiTouch() {
        return this.mNeedsMultiTouch;
    }

    public TouchOptions setNeedsMultiTouch(boolean pNeedsMultiTouch) {
        this.mNeedsMultiTouch = pNeedsMultiTouch;
        return this;
    }

    public long getTouchEventIntervalMilliseconds() {
        return this.mTouchEventIntervalMilliseconds;
    }

    public void setTouchEventIntervalMilliseconds(long pTouchEventIntervalMilliseconds) {
        this.mTouchEventIntervalMilliseconds = pTouchEventIntervalMilliseconds;
    }
}
