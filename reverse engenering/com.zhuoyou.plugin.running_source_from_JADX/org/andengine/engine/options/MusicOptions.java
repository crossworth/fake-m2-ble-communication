package org.andengine.engine.options;

public class MusicOptions {
    private boolean mNeedsMusic;

    public boolean needsMusic() {
        return this.mNeedsMusic;
    }

    public MusicOptions setNeedsMusic(boolean pNeedsMusic) {
        this.mNeedsMusic = pNeedsMusic;
        return this;
    }
}
