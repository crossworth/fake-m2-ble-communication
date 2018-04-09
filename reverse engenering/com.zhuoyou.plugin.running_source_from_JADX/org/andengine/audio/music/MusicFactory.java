package org.andengine.audio.music;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MusicFactory {
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

    public static Music createMusicFromFile(MusicManager pMusicManager, File pFile) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(new FileInputStream(pFile).getFD());
        mediaPlayer.prepare();
        Music music = new Music(pMusicManager, mediaPlayer);
        pMusicManager.add(music);
        return music;
    }

    public static Music createMusicFromAsset(MusicManager pMusicManager, Context pContext, String pAssetPath) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        AssetFileDescriptor assetFileDescritor = pContext.getAssets().openFd(sAssetBasePath + pAssetPath);
        mediaPlayer.setDataSource(assetFileDescritor.getFileDescriptor(), assetFileDescritor.getStartOffset(), assetFileDescritor.getLength());
        mediaPlayer.prepare();
        Music music = new Music(pMusicManager, mediaPlayer);
        pMusicManager.add(music);
        return music;
    }

    public static Music createMusicFromResource(MusicManager pMusicManager, Context pContext, int pMusicResID) throws IOException {
        MediaPlayer mediaPlayer = MediaPlayer.create(pContext, pMusicResID);
        mediaPlayer.prepare();
        Music music = new Music(pMusicManager, mediaPlayer);
        pMusicManager.add(music);
        return music;
    }

    public static Music createMusicFromAssetFileDescriptor(MusicManager pMusicManager, AssetFileDescriptor pAssetFileDescriptor) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(pAssetFileDescriptor.getFileDescriptor(), pAssetFileDescriptor.getStartOffset(), pAssetFileDescriptor.getLength());
        mediaPlayer.prepare();
        Music music = new Music(pMusicManager, mediaPlayer);
        pMusicManager.add(music);
        return music;
    }
}
