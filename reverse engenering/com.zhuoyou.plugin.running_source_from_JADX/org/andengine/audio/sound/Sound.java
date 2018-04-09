package org.andengine.audio.sound;

import android.media.SoundPool;
import org.andengine.audio.BaseAudioEntity;
import org.andengine.audio.sound.exception.SoundReleasedException;

public class Sound extends BaseAudioEntity {
    private boolean mLoaded;
    private int mLoopCount;
    private float mRate = 1.0f;
    private int mSoundID;
    private int mStreamID;

    Sound(SoundManager pSoundManager, int pSoundID) {
        super(pSoundManager);
        this.mSoundID = pSoundID;
    }

    public int getSoundID() {
        return this.mSoundID;
    }

    public int getStreamID() {
        return this.mStreamID;
    }

    public boolean isLoaded() {
        return this.mLoaded;
    }

    public void setLoaded(boolean pLoaded) {
        this.mLoaded = pLoaded;
    }

    public void setLoopCount(int pLoopCount) throws SoundReleasedException {
        assertNotReleased();
        this.mLoopCount = pLoopCount;
        if (this.mStreamID != 0) {
            getSoundPool().setLoop(this.mStreamID, pLoopCount);
        }
    }

    public float getRate() {
        return this.mRate;
    }

    public void setRate(float pRate) throws SoundReleasedException {
        assertNotReleased();
        this.mRate = pRate;
        if (this.mStreamID != 0) {
            getSoundPool().setRate(this.mStreamID, pRate);
        }
    }

    private SoundPool getSoundPool() throws SoundReleasedException {
        return getAudioManager().getSoundPool();
    }

    protected SoundManager getAudioManager() throws SoundReleasedException {
        return (SoundManager) super.getAudioManager();
    }

    protected void throwOnReleased() throws SoundReleasedException {
        throw new SoundReleasedException();
    }

    public void play() throws SoundReleasedException {
        super.play();
        float masterVolume = getMasterVolume();
        this.mStreamID = getSoundPool().play(this.mSoundID, this.mLeftVolume * masterVolume, this.mRightVolume * masterVolume, 1, this.mLoopCount, this.mRate);
    }

    public void stop() throws SoundReleasedException {
        super.stop();
        if (this.mStreamID != 0) {
            getSoundPool().stop(this.mStreamID);
        }
    }

    public void resume() throws SoundReleasedException {
        super.resume();
        if (this.mStreamID != 0) {
            getSoundPool().resume(this.mStreamID);
        }
    }

    public void pause() throws SoundReleasedException {
        super.pause();
        if (this.mStreamID != 0) {
            getSoundPool().pause(this.mStreamID);
        }
    }

    public void release() throws SoundReleasedException {
        assertNotReleased();
        getSoundPool().unload(this.mSoundID);
        this.mSoundID = 0;
        this.mLoaded = false;
        getAudioManager().remove(this);
        super.release();
    }

    public void setLooping(boolean pLooping) throws SoundReleasedException {
        super.setLooping(pLooping);
        setLoopCount(pLooping ? -1 : 0);
    }

    public void setVolume(float pLeftVolume, float pRightVolume) throws SoundReleasedException {
        super.setVolume(pLeftVolume, pRightVolume);
        if (this.mStreamID != 0) {
            float masterVolume = getMasterVolume();
            getSoundPool().setVolume(this.mStreamID, this.mLeftVolume * masterVolume, this.mRightVolume * masterVolume);
        }
    }

    public void onMasterVolumeChanged(float pMasterVolume) throws SoundReleasedException {
        setVolume(this.mLeftVolume, this.mRightVolume);
    }
}
