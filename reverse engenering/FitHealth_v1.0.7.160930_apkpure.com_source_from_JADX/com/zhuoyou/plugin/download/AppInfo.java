package com.zhuoyou.plugin.download;

import android.graphics.Bitmap;

public class AppInfo {
    private String appName;
    private String appPackageName;
    private byte[] bitmap;
    private boolean downloading;
    private int flag;
    private String localFile;
    private String logo;
    private String mainActivity;
    private Bitmap mbitmap;
    private String size;
    private String url;
    private String version;

    public AppInfo(String appName, String url, String appPackageName, String mainActivity, byte[] bitmap, String localFile, int flag, String version, String size) {
        this.appName = appName;
        this.url = url;
        this.appPackageName = appPackageName;
        this.mainActivity = mainActivity;
        this.bitmap = bitmap;
        this.size = size;
        this.localFile = localFile;
        this.version = version;
        this.flag = flag;
    }

    public boolean getDownloading() {
        return this.downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }

    public String getLocalFile() {
        return this.localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Bitmap getMbitmap() {
        return this.mbitmap;
    }

    public void setMbitmap(Bitmap mbitmap) {
        this.mbitmap = mbitmap;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppPackageName() {
        return this.appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getMainActivity() {
        return this.mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public byte[] getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
