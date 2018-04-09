package org.andengine.engine.options;

public class SoundOptions {
    private int mMaxSimultaneousStreams = 5;
    private boolean mNeedsSound;

    public boolean needsSound() {
        return this.mNeedsSound;
    }

    public SoundOptions setNeedsSound(boolean pNeedsSound) {
        this.mNeedsSound = pNeedsSound;
        return this;
    }

    public int getMaxSimultaneousStreams() {
        return this.mMaxSimultaneousStreams;
    }

    public SoundOptions setMaxSimultaneousStreams(int pMaxSimultaneousStreams) {
        this.mMaxSimultaneousStreams = pMaxSimultaneousStreams;
        return this;
    }
}
