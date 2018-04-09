package org.andengine.audio.sound;

import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.SparseArray;
import org.andengine.audio.BaseAudioManager;
import org.andengine.audio.sound.exception.SoundException;

public class SoundManager extends BaseAudioManager<Sound> implements OnLoadCompleteListener {
    public static final int MAX_SIMULTANEOUS_STREAMS_DEFAULT = 5;
    private static final int SOUND_STATUS_OK = 0;
    private final SparseArray<Sound> mSoundMap;
    private final SoundPool mSoundPool;

    public SoundManager() {
        this(5);
    }

    public SoundManager(int pMaxSimultaneousStreams) {
        this.mSoundMap = new SparseArray();
        this.mSoundPool = new SoundPool(pMaxSimultaneousStreams, 3, 0);
        this.mSoundPool.setOnLoadCompleteListener(this);
    }

    SoundPool getSoundPool() {
        return this.mSoundPool;
    }

    public void add(Sound pSound) {
        super.add(pSound);
        this.mSoundMap.put(pSound.getSoundID(), pSound);
    }

    public boolean remove(Sound pSound) {
        boolean removed = super.remove(pSound);
        if (removed) {
            this.mSoundMap.remove(pSound.getSoundID());
        }
        return removed;
    }

    public void releaseAll() {
        super.releaseAll();
        this.mSoundPool.release();
    }

    public synchronized void onLoadComplete(SoundPool pSoundPool, int pSoundID, int pStatus) {
        if (pStatus == 0) {
            Sound sound = (Sound) this.mSoundMap.get(pSoundID);
            if (sound == null) {
                throw new SoundException("Unexpected soundID: '" + pSoundID + "'.");
            }
            sound.setLoaded(true);
        }
    }
}
