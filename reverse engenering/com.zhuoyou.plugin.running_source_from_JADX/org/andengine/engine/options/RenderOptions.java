package org.andengine.engine.options;

public class RenderOptions {
    private boolean mDithering = false;
    private boolean mMultiSampling = false;

    public boolean isMultiSampling() {
        return this.mMultiSampling;
    }

    public void setMultiSampling(boolean pMultiSampling) {
        this.mMultiSampling = pMultiSampling;
    }

    public boolean isDithering() {
        return this.mDithering;
    }

    public void setDithering(boolean pDithering) {
        this.mDithering = pDithering;
    }
}
