package com.zhuoyou.plugin.selfupdate;

import android.content.Context;

public class SelfUpdateMain {
    public static boolean isDownloading = false;

    public static void execApkSelfUpdateRequest(Context context, String appid, String chnid) {
        new RequestAsyncTask(context, new MyHandler(context), 1001, appid, chnid).startRun();
    }
}
