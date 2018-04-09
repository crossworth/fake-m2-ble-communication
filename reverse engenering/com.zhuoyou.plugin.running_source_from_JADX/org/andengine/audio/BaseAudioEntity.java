package org.andengine.audio;

import org.andengine.audio.exception.AudioException;

public abstract class BaseAudioEntity implements IAudioEntity {
    private final IAudioManager<? extends IAudioEntity> mAudioManager;
    protected float mLeftVolume = 1.0f;
    private boolean mReleased;
    protected float mRightVolume = 1.0f;

    protected abstract void throwOnReleased() throws AudioException;

    public BaseAudioEntity(IAudioManager<? extends IAudioEntity> pAudioManager) {
        this.mAudioManager = pAudioManager;
    }

    public boolean isReleased() {
        return this.mReleased;
    }

    protected IAudioManager<? extends IAudioEntity> getAudioManager() throws AudioException {
        assertNotReleased();
        return this.mAudioManager;
    }

    public float getActualLeftVolume() throws AudioException {
        assertNotReleased();
        return this.mLeftVolume * getMasterVolume();
    }

    public float getActualRightVolume() throws AudioException {
        assertNotReleased();
        return this.mRightVolume * getMasterVolume();
    }

    protected float getMasterVolume() throws AudioException {
        assertNotReleased();
        return this.mAudioManager.getMasterVolume();
    }

    public float getVolume() throws AudioException {
        assertNotReleased();
        return (this.mLeftVolume + this.mRightVolume) * 0.5f;
    }

    public float getLeftVolume() throws AudioException {
        assertNotReleased();
        return this.mLeftVolume;
    }

    public float getRightVolume() throws AudioException {
        assertNotReleased();
        return this.mRightVolume;
    }

    public final void setVolume(float pVolume) throws AudioException {
        assertNotReleased();
        setVolume(pVolume, pVolume);
    }

    public void setVolume(float pLeftVolume, float pRightVolume) throws AudioException {
        assertNotReleased();
        this.mLeftVolume = pLeftVolume;
        this.mRightVolume = pRightVolume;
    }

    public void onMasterVolumeChanged(float pMasterVolume) throws AudioException {
        assertNotReleased();
    }

    public void play() throws AudioException {
        assertNotReleased();
    }

    public void pause() throws AudioException {
        assertNotReleased();
    }

    public void resume() throws AudioException {
        assertNotReleased();
    }

    public void stop() throws AudioException {
        assertNotReleased();
    }

    public void setLooping(boolean pLooping) throws AudioException {
        assertNotReleased();
    }

    public void release() throws AudioException {
        assertNotReleased();
        this.mReleased = true;
    }

    protected void assertNotReleased() throws AudioException {
        if (this.mReleased) {
            throwOnReleased();
        }
    }
}
