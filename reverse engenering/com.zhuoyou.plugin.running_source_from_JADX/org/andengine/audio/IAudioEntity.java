package org.andengine.audio;

public interface IAudioEntity {
    float getLeftVolume();

    float getRightVolume();

    float getVolume();

    void onMasterVolumeChanged(float f);

    void pause();

    void play();

    void release();

    void resume();

    void setLooping(boolean z);

    void setVolume(float f);

    void setVolume(float f, float f2);

    void stop();
}
