package org.andengine.engine;

import org.andengine.engine.options.EngineOptions;
import org.andengine.util.time.TimeConstants;

public class LimitedFPSEngine extends Engine {
    private final long mPreferredFrameLengthNanoseconds;

    public LimitedFPSEngine(EngineOptions pEngineOptions, int pFramesPerSecond) {
        super(pEngineOptions);
        this.mPreferredFrameLengthNanoseconds = TimeConstants.NANOSECONDS_PER_SECOND / ((long) pFramesPerSecond);
    }

    public void onUpdate(long pNanosecondsElapsed) throws InterruptedException {
        long deltaFrameLengthNanoseconds = this.mPreferredFrameLengthNanoseconds - pNanosecondsElapsed;
        if (deltaFrameLengthNanoseconds <= 0) {
            super.onUpdate(pNanosecondsElapsed);
            return;
        }
        Thread.sleep((long) ((int) (deltaFrameLengthNanoseconds / TimeConstants.NANOSECONDS_PER_MILLISECOND)));
        super.onUpdate(pNanosecondsElapsed + deltaFrameLengthNanoseconds);
    }
}
