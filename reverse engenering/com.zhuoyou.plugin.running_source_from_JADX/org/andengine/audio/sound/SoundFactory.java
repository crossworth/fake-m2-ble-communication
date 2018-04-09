package org.andengine.audio.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class SoundFactory {
    private static String sAssetBasePath = "";

    public static void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    public static String getAssetBasePath() {
        return sAssetBasePath;
    }

    public static void onCreate() {
        setAssetBasePath("");
    }

    public static Sound createSoundFromPath(SoundManager pSoundManager, String pPath) {
        Sound sound;
        synchronized (pSoundManager) {
            sound = new Sound(pSoundManager, pSoundManager.getSoundPool().load(pPath, 1));
            pSoundManager.add(sound);
        }
        return sound;
    }

    public static Sound createSoundFromAsset(SoundManager pSoundManager, Context pContext, String pAssetPath) throws IOException {
        Sound sound;
        synchronized (pSoundManager) {
            sound = new Sound(pSoundManager, pSoundManager.getSoundPool().load(pContext.getAssets().openFd(sAssetBasePath + pAssetPath), 1));
            pSoundManager.add(sound);
        }
        return sound;
    }

    public static Sound createSoundFromResource(SoundManager pSoundManager, Context pContext, int pSoundResID) {
        Sound sound;
        synchronized (pSoundManager) {
            sound = new Sound(pSoundManager, pSoundManager.getSoundPool().load(pContext, pSoundResID, 1));
            pSoundManager.add(sound);
        }
        return sound;
    }

    public static Sound createSoundFromFile(SoundManager pSoundManager, File pFile) {
        return createSoundFromPath(pSoundManager, pFile.getAbsolutePath());
    }

    public static Sound createSoundFromAssetFileDescriptor(SoundManager pSoundManager, AssetFileDescriptor pAssetFileDescriptor) {
        Sound sound;
        synchronized (pSoundManager) {
            sound = new Sound(pSoundManager, pSoundManager.getSoundPool().load(pAssetFileDescriptor, 1));
            pSoundManager.add(sound);
        }
        return sound;
    }

    public static Sound createSoundFromFileDescriptor(SoundManager pSoundManager, FileDescriptor pFileDescriptor, long pOffset, long pLength) {
        Sound sound;
        synchronized (pSoundManager) {
            sound = new Sound(pSoundManager, pSoundManager.getSoundPool().load(pFileDescriptor, pOffset, pLength, 1));
            pSoundManager.add(sound);
        }
        return sound;
    }
}
