package org.andengine.entity.util;

import android.support.v4.widget.AutoScrollHelper;

public class FPSLogger extends AverageFPSCounter {
    protected float mLongestFrame = Float.MIN_VALUE;
    protected float mShortestFrame = AutoScrollHelper.NO_MAX;

    public FPSLogger(float pAverageDuration) {
        super(pAverageDuration);
    }

    protected void onHandleAverageDurationElapsed(float pFPS) {
        onLogFPS();
        this.mLongestFrame = Float.MIN_VALUE;
        this.mShortestFrame = AutoScrollHelper.NO_MAX;
    }

    public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        this.mShortestFrame = Math.min(this.mShortestFrame, pSecondsElapsed);
        this.mLongestFrame = Math.max(this.mLongestFrame, pSecondsElapsed);
    }

    public void reset() {
        super.reset();
        this.mShortestFrame = AutoScrollHelper.NO_MAX;
        this.mLongestFrame = Float.MIN_VALUE;
    }

    protected void onLogFPS() {
    }
}
