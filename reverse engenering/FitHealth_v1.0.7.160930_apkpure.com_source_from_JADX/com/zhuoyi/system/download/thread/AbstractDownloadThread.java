package com.zhuoyi.system.download.thread;

import android.content.Context;
import android.os.Handler;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.download.util.DownloadConstants;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractDownloadThread extends Thread implements IDownloadThread {
    protected static final int RECONNECT_COUNT = 3;
    protected String MD5;
    protected int closeConnectCount = 0;
    protected float disProgress;
    protected String downloadFilePath;
    protected int downloadResult = 1;
    protected int downloadSize;
    protected boolean isDownloading = true;
    protected Context mContext;
    protected Handler mHandler;
    protected String mSavePath;
    protected String mURL;
    protected int offset;
    protected String tmpFilePath;
    protected int totalSize;

    public void reciveData(HttpURLConnection httpConnection, String downloadFilePath, String apkFilePath) throws IOException {
        this.downloadSize = 0;
        this.totalSize = (int) NetworkUtils.getLengthByURLConnection(httpConnection);
        if (this.totalSize <= 0) {
            throw new IOException();
        }
        this.totalSize += this.offset;
        RandomAccessFile oSavedFile = new RandomAccessFile(downloadFilePath, "rw");
        oSavedFile.seek((long) this.offset);
        InputStream input = httpConnection.getInputStream();
        byte[] buffer = new byte[2048];
        int nRead = input.read(buffer, 0, 2048);
        this.disProgress = (((float) this.offset) * 100.0f) / ((float) this.totalSize);
        while (this.isDownloading && nRead > 0 && this.offset < this.totalSize) {
            oSavedFile.write(buffer, 0, nRead);
            this.offset += nRead;
            this.downloadSize += nRead;
            this.disProgress = (((float) this.offset) * 100.0f) / ((float) this.totalSize);
            sendProgressMsg();
            nRead = input.read(buffer, 0, 2048);
        }
        httpConnection.disconnect();
        try {
            oSavedFile.close();
        } catch (Exception e) {
        }
        if (this.offset == this.totalSize) {
            File downloadFile = new File(downloadFilePath);
            File apkFile = new File(apkFilePath);
            downloadFile.renameTo(apkFile);
            String md5 = "";
            try {
                md5 = AppInfoUtils.getMd5FromFile(apkFilePath);
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            }
            if (md5.equalsIgnoreCase(this.MD5)) {
                sendStopMsg(3);
                this.downloadResult = 3;
                return;
            }
            apkFile.delete();
            sendStopMsg(2);
            return;
        }
        sendStopMsg(2);
    }

    public void connect() {
        try {
            HttpURLConnection httpConnection = (HttpURLConnection) new URL(this.mURL).openConnection();
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(5000);
            httpConnection.setRequestProperty("User-Agent", DownloadConstants.UPDATE_SEVICE_USERAGENT);
            httpConnection.setRequestProperty("RANGE", "bytes=" + this.offset + SocializeConstants.OP_DIVIDER_MINUS);
            int responseCode = httpConnection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                reconnect();
            } else {
                reciveData(httpConnection, this.tmpFilePath, this.downloadFilePath);
            }
        } catch (IOException e) {
            reconnect();
        }
    }

    public void reconnect() {
        this.closeConnectCount++;
        if (this.closeConnectCount >= 3 || !this.isDownloading) {
            sendStopMsg(2);
        } else {
            connect();
        }
    }

    public void onPause() {
        this.isDownloading = false;
    }

    public boolean isDownloading() {
        return this.isDownloading;
    }
}
