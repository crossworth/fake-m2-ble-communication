package org.andengine.engine.options;

public class AudioOptions {
    private MusicOptions mMusicOptions = new MusicOptions();
    private SoundOptions mSoundOptions = new SoundOptions();

    public SoundOptions getSoundOptions() {
        return this.mSoundOptions;
    }

    public MusicOptions getMusicOptions() {
        return this.mMusicOptions;
    }

    public boolean needsSound() {
        return this.mSoundOptions.needsSound();
    }

    public AudioOptions setNeedsSound(boolean pNeedsSound) {
        this.mSoundOptions.setNeedsSound(pNeedsSound);
        return this;
    }

    public boolean needsMusic() {
        return this.mMusicOptions.needsMusic();
    }

    public AudioOptions setNeedsMusic(boolean pNeedsMusic) {
        this.mMusicOptions.setNeedsMusic(pNeedsMusic);
        return this;
    }
}
