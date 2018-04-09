package org.andengine.entity.util;

import org.andengine.engine.handler.IUpdateHandler;

public class FrameCounter implements IUpdateHandler {
    private int mFrames;

    public int getFrames() {
        return this.mFrames;
    }

    public void onUpdate(float pSecondsElapsed) {
        this.mFrames++;
    }

    public void reset() {
        this.mFrames = 0;
    }
}
