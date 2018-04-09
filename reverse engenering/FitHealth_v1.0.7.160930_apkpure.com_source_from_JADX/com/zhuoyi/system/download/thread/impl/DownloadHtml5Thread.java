package com.zhuoyi.system.download.thread.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.zhuoyi.system.download.thread.AbstractDownloadThread;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.util.Logger;
import java.io.File;
import java.io.FileInputStream;

public class DownloadHtml5Thread extends AbstractDownloadThread {
    public static final String TAG = "DownloadZipThread";
    private Html5Info html5Info;

    public DownloadHtml5Thread(Context context, Handler handler, Html5Info html5Info, String filePath) {
        this.mContext = context;
        this.mHandler = handler;
        this.mSavePath = filePath;
        this.mURL = html5Info.getUrl();
        this.MD5 = html5Info.getZipMd5();
        this.html5Info = html5Info;
        this.tmpFilePath = this.mSavePath + File.separator + html5Info.getId() + ".tmp";
        this.downloadFilePath = this.mSavePath + File.separator + html5Info.getId() + ".zip";
    }

    public void run() {
        try {
            File downloadPath = new File(this.mSavePath);
            if (!downloadPath.exists()) {
                downloadPath.mkdirs();
            }
            File downloadFile = new File(this.tmpFilePath);
            if (!downloadFile.exists()) {
                downloadFile.createNewFile();
            }
            FileInputStream fis = new FileInputStream(downloadFile);
            this.offset = fis.available();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connect();
        } catch (Exception e2) {
            Logger.m3373e(TAG, "fail1");
            e2.printStackTrace();
        }
        this.isDownloading = false;
    }

    public void sendStopMsg(int status) {
        Logger.m3373e(TAG, "send status=" + status);
        DownloadUtils.getInstance(this.mContext).removeDownloadZipThread(this.html5Info.getId());
        Message msg = new Message();
        msg.what = status;
        Bundle b = new Bundle();
        b.putSerializable(BundleConstants.BUNDLE_DESKTOP_AD_INFO, this.html5Info);
        msg.obj = b;
        this.mHandler.sendMessage(msg);
    }

    public void sendProgressMsg() {
    }
}
