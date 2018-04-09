package com.zhuoyi.system.download.thread.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.download.model.DownloadInfo;
import com.zhuoyi.system.download.thread.AbstractDownloadThread;
import com.zhuoyi.system.download.util.DownloadConstants;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.statistics.listener.ZyStatisticsSDK;
import com.zhuoyi.system.statistics.prom.data.StatsPromDBUtils;
import com.zhuoyi.system.statistics.prom.model.MyDownloadResult;
import com.zhuoyi.system.statistics.prom.util.StatsPromConstants;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class DownloadApkThread extends AbstractDownloadThread {
    public static final String TAG = "DownloadApkThread";
    private boolean isDownloadNext;
    private String mPackageName;
    private int mPosition;
    private int mSource;
    private int mVersionCode;

    public DownloadApkThread(Context context, DownloadInfo downloadInfo) {
        this.mContext = context;
        this.mHandler = downloadInfo.getHandler();
        this.mPackageName = downloadInfo.getPackageName();
        this.MD5 = downloadInfo.getMd5();
        this.mURL = downloadInfo.getUrl();
        this.mSource = downloadInfo.getSource();
        this.mPosition = downloadInfo.getPosition();
        this.mVersionCode = downloadInfo.getVersionCode();
        this.isDownloadNext = downloadInfo.isDownloadNext();
        Logger.m3373e(TAG, "mURL:" + this.mURL);
        this.mSavePath = DownloadUtils.getInstance(this.mContext).getApkDownloadPath(downloadInfo.getPackageName());
        this.tmpFilePath = this.mSavePath + File.separator + this.mPackageName + "_r" + this.mVersionCode + ".tmp";
        this.downloadFilePath = this.mSavePath + File.separator + this.mPackageName + "_r" + this.mVersionCode + ".apk";
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
            e2.printStackTrace();
        }
        this.isDownloading = false;
        Map map = new HashMap();
        map.put("package_name", this.mPackageName);
        map.put("version_code", this.mVersionCode);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_SOURCE1, this.mPosition);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_SOURCE2, new StringBuilder(String.valueOf(this.mSource)).toString());
        map.put("sdk_version", ZySDKConfig.SDK_VERSION_NAME);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_TOTAL_SIZE, this.totalSize);
        map.put("offset", this.offset);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_DOWNLOAD_SIZE, new StringBuilder(String.valueOf(this.downloadSize)).toString());
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_DOWNLOAD_RESULT, new StringBuilder(String.valueOf(this.downloadResult)).toString());
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_DOWNLOAD_RESULT, map);
        MyDownloadResult result = new MyDownloadResult();
        result.setDownloadResult(this.downloadResult);
        result.setDownloadSize(this.downloadSize);
        result.setOffset(this.offset);
        result.setPackageName(this.mPackageName);
        result.setSource1(this.mPosition);
        result.setSource2(this.mSource);
        result.setTotalSize(this.totalSize);
        result.setVersionCode(this.mVersionCode);
        StatsPromDBUtils.getInstance(this.mContext).insertDownloadResult(result);
        if (this.isDownloadNext) {
            DownloadUtils.getInstance(this.mContext).downloadNextApk();
        }
    }

    public void sendStopMsg(int status) {
        Logger.m3373e(TAG, "send status=" + status);
        MyPackageInfo info = new MyPackageInfo(this.mPackageName, this.mVersionCode);
        DownloadUtils.getInstance(this.mContext).removeDownloadApkThread(info);
        DownloadUtils.getInstance(this.mContext).removeSelfUpdateThread(info);
        Message msg = new Message();
        msg.what = status;
        Bundle b = new Bundle();
        b.putString(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PACKAGE_NAME, this.mPackageName);
        b.putInt(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_VERSION_CODE, this.mVersionCode);
        b.putInt(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_POSITION, this.mPosition);
        b.putInt(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_SOURCE, this.mSource);
        msg.obj = b;
        this.mHandler.sendMessage(msg);
    }

    public void sendProgressMsg() {
        Message msg = new Message();
        msg.what = 1;
        Bundle b = new Bundle();
        b.putString(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PACKAGE_NAME, this.mPackageName);
        b.putInt(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_VERSION_CODE, this.mVersionCode);
        b.putFloat(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PROGRESS, this.disProgress);
        msg.obj = b;
        this.mHandler.sendMessage(msg);
    }
}
