package com.zhuoyou.plugin.bluetooth.data;

import android.content.Context;
import android.util.Log;
import com.zhuoyou.plugin.running.RunningApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AppList {
    public static final CharSequence BETTRYLOW_APPID = "com.mtk.btnotification.batterylow";
    public static final int CREATE_LENTH = 3;
    private static final AppList INSTANCE = new AppList();
    private static final String LOG_TAG = "AppList";
    public static final String MAX_APP = "MaxApp";
    private static final String SAVE_FILE_NAME = "AppList";
    public static final CharSequence SMSRESULT_APPID = "com.mtk.btnotification.smsresult";
    private Map<Object, Object> mAppList = null;
    private Context mContext = null;

    private AppList() {
        Log.i("AppList", "AppList(), AppList created!");
        this.mContext = RunningApp.getInstance().getApplicationContext();
    }

    public static AppList getInstance() {
        return INSTANCE;
    }

    public Map<Object, Object> getAppList() {
        if (this.mAppList == null) {
            loadAppListFromFile();
        }
        Log.i("AppList", "getAppList(), mAppList = " + this.mAppList.toString());
        return this.mAppList;
    }

    private void loadAppListFromFile() {
        Log.i("AppList", "loadIgnoreListFromFile(),  file_name= AppList");
        if (this.mAppList == null) {
            try {
                this.mAppList = (Map) new ObjectInputStream(this.mContext.openFileInput("AppList")).readObject();
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException exception2) {
                exception2.printStackTrace();
            }
        }
        if (this.mAppList == null) {
            this.mAppList = new HashMap();
        }
    }

    public void saveAppList(Map<Object, Object> appList) {
        Log.i("AppList", "setIgnoreList(),  file_name= AppList");
        try {
            FileOutputStream fileoutputstream = this.mContext.openFileOutput("AppList", 0);
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(this.mAppList);
            objectoutputstream.close();
            fileoutputstream.close();
            this.mAppList = appList;
            Log.i("AppList", "setIgnoreList(),  mIgnoreList= " + this.mAppList);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
