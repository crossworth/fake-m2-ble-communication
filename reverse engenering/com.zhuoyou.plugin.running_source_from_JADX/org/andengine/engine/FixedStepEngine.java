package org.andengine.engine;

import org.andengine.engine.options.EngineOptions;
import org.andengine.util.time.TimeConstants;

public class FixedStepEngine extends Engine {
    private long mSecondsElapsedAccumulator;
    private final long mStepLength;

    public FixedStepEngine(EngineOptions pEngineOptions, int pStepsPerSecond) {
        super(pEngineOptions);
        this.mStepLength = TimeConstants.NANOSECONDS_PER_SECOND / ((long) pStepsPerSecond);
    }

    public void onUpdate(long pNanosecondsElapsed) throws InterruptedException {
        this.mSecondsElapsedAccumulator += pNanosecondsElapsed;
        long stepLength = this.mStepLength;
        while (this.mSecondsElapsedAccumulator >= stepLength) {
            super.onUpdate(stepLength);
            this.mSecondsElapsedAccumulator -= stepLength;
        }
    }
}
