package com.zhuoyi.system.download.model;

import android.os.Handler;
import com.zhuoyi.system.util.model.MyPackageInfo;

public class DownloadInfo extends MyPackageInfo {
    private Handler handler;
    private boolean isDownloadNext;
    private String md5;
    private String url;

    public DownloadInfo(String packageName, int versionCode) {
        super(packageName, versionCode);
    }

    public DownloadInfo(String packageName, int versionCode, int position, int source, Handler handler, String url, String md5) {
        super(packageName, versionCode, position, source);
        this.handler = handler;
        this.url = url;
        this.md5 = md5;
    }

    public DownloadInfo(Handler handler, String packageName, int versionCode, int position, int source, String url, String md5, boolean isDownloadNext) {
        super(packageName, versionCode, position, source);
        this.handler = handler;
        this.url = url;
        this.md5 = md5;
        this.isDownloadNext = isDownloadNext;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isDownloadNext() {
        return this.isDownloadNext;
    }

    public void setDownloadNext(boolean isDownloadNext) {
        this.isDownloadNext = isDownloadNext;
    }
}
