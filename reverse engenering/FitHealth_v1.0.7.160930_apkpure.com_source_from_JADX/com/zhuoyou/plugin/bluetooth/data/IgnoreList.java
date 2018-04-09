package com.zhuoyou.plugin.bluetooth.data;

import android.content.Context;
import com.zhuoyou.plugin.running.RunningApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class IgnoreList {
    private static final String[] EXCLUSION_LIST = new String[]{"android", "com.android.mms", "com.android.phone", "com.android.providers.downloads", "com.android.bluetooth", "com.mediatek.bluetooth", "com.htc.music", "com.lge.music", "com.sec.android.app.music", "com.sonyericsson.music", "com.ijinshan.mguard", "com.android.music", "com.android.dialer", "com.google.android.music"};
    private static final IgnoreList INSTANCE = new IgnoreList();
    private static final String SAVE_FILE_NAME = "IgnoreList";
    private Context mContext;
    private HashSet<String> mIgnoreList;

    private IgnoreList() {
        this.mIgnoreList = null;
        this.mContext = null;
        this.mContext = RunningApp.getInstance().getApplicationContext();
    }

    public static IgnoreList getInstance() {
        return INSTANCE;
    }

    public HashSet<String> getIgnoreList() {
        if (this.mIgnoreList == null) {
            loadIgnoreListFromFile();
        }
        return this.mIgnoreList;
    }

    private void loadIgnoreListFromFile() {
        if (this.mIgnoreList == null) {
            try {
                this.mIgnoreList = (HashSet) new ObjectInputStream(this.mContext.openFileInput(SAVE_FILE_NAME)).readObject();
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException exception2) {
                exception2.printStackTrace();
            }
        }
        if (this.mIgnoreList == null) {
            this.mIgnoreList = new HashSet();
        }
    }

    public void addIgnoreItem(String name) {
        if (this.mIgnoreList == null) {
            loadIgnoreListFromFile();
        }
        if (!this.mIgnoreList.contains(name)) {
            this.mIgnoreList.add(name);
        }
    }

    public void removeIgnoreItem(String name) {
        if (this.mIgnoreList == null) {
            loadIgnoreListFromFile();
        }
        if (this.mIgnoreList.contains(name)) {
            this.mIgnoreList.remove(name);
        }
    }

    public void saveIgnoreList() {
        try {
            FileOutputStream fileoutputstream = this.mContext.openFileOutput(SAVE_FILE_NAME, 0);
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(this.mIgnoreList);
            objectoutputstream.close();
            fileoutputstream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void saveIgnoreList(HashSet<String> ignoreList) {
        try {
            FileOutputStream fileoutputstream = this.mContext.openFileOutput(SAVE_FILE_NAME, 0);
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(ignoreList);
            objectoutputstream.close();
            fileoutputstream.close();
            this.mIgnoreList = ignoreList;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public HashSet<String> getExclusionList() {
        HashSet<String> exclusionList = new HashSet();
        for (String exclusionPackage : EXCLUSION_LIST) {
            exclusionList.add(exclusionPackage);
        }
        return exclusionList;
    }
}
