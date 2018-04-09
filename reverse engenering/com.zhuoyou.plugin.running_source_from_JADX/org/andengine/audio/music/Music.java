package org.andengine.audio.music;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import org.andengine.audio.BaseAudioEntity;
import org.andengine.audio.music.exception.MusicReleasedException;

public class Music extends BaseAudioEntity {
    private MediaPlayer mMediaPlayer;

    Music(MusicManager pMusicManager, MediaPlayer pMediaPlayer) {
        super(pMusicManager);
        this.mMediaPlayer = pMediaPlayer;
    }

    public boolean isPlaying() throws MusicReleasedException {
        assertNotReleased();
        return this.mMediaPlayer.isPlaying();
    }

    public MediaPlayer getMediaPlayer() throws MusicReleasedException {
        assertNotReleased();
        return this.mMediaPlayer;
    }

    protected MusicManager getAudioManager() throws MusicReleasedException {
        return (MusicManager) super.getAudioManager();
    }

    protected void throwOnReleased() throws MusicReleasedException {
        throw new MusicReleasedException();
    }

    public void play() throws MusicReleasedException {
        super.play();
        this.mMediaPlayer.start();
    }

    public void stop() throws MusicReleasedException {
        super.stop();
        this.mMediaPlayer.stop();
    }

    public void resume() throws MusicReleasedException {
        super.resume();
        this.mMediaPlayer.start();
    }

    public void pause() throws MusicReleasedException {
        super.pause();
        this.mMediaPlayer.pause();
    }

    public void setLooping(boolean pLooping) throws MusicReleasedException {
        super.setLooping(pLooping);
        this.mMediaPlayer.setLooping(pLooping);
    }

    public void setVolume(float pLeftVolume, float pRightVolume) throws MusicReleasedException {
        super.setVolume(pLeftVolume, pRightVolume);
        float masterVolume = getAudioManager().getMasterVolume();
        this.mMediaPlayer.setVolume(pLeftVolume * masterVolume, pRightVolume * masterVolume);
    }

    public void onMasterVolumeChanged(float pMasterVolume) throws MusicReleasedException {
        setVolume(this.mLeftVolume, this.mRightVolume);
    }

    public void release() throws MusicReleasedException {
        assertNotReleased();
        this.mMediaPlayer.release();
        this.mMediaPlayer = null;
        getAudioManager().remove(this);
        super.release();
    }

    public void seekTo(int pMilliseconds) throws MusicReleasedException {
        assertNotReleased();
        this.mMediaPlayer.seekTo(pMilliseconds);
    }

    public void setOnCompletionListener(OnCompletionListener pOnCompletionListener) throws MusicReleasedException {
        assertNotReleased();
        this.mMediaPlayer.setOnCompletionListener(pOnCompletionListener);
    }
}
