package org.andengine.entity.util;

public abstract class AverageFPSCounter extends FPSCounter {
    private static final float AVERAGE_DURATION_DEFAULT = 5.0f;
    protected final float mAverageDuration;

    protected abstract void onHandleAverageDurationElapsed(float f);

    public AverageFPSCounter() {
        this(AVERAGE_DURATION_DEFAULT);
    }

    public AverageFPSCounter(float pAverageDuration) {
        this.mAverageDuration = pAverageDuration;
    }

    public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        if (this.mSecondsElapsed > this.mAverageDuration) {
            onHandleAverageDurationElapsed(getFPS());
            this.mSecondsElapsed -= this.mAverageDuration;
            this.mFrames = 0;
        }
    }
}
