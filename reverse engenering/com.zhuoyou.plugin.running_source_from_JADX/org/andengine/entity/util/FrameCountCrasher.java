package org.andengine.entity.util;

import org.andengine.engine.handler.IUpdateHandler;

public class FrameCountCrasher implements IUpdateHandler {
    private final float[] mFrameLengths;
    private int mFramesLeft;

    public FrameCountCrasher(int pFrameCount) {
        this.mFramesLeft = pFrameCount;
        this.mFrameLengths = new float[pFrameCount];
    }

    public void onUpdate(float pSecondsElapsed) {
        this.mFramesLeft--;
        float[] frameLengths = this.mFrameLengths;
        if (this.mFramesLeft >= 0) {
            frameLengths[this.mFramesLeft] = pSecondsElapsed;
            return;
        }
        throw new RuntimeException();
    }

    public void reset() {
    }
}
