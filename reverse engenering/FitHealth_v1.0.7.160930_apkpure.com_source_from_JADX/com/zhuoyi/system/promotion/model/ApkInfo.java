package com.zhuoyi.system.promotion.model;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import com.zhuoyi.system.network.object.SerApkInfo;

public class ApkInfo {
    private String appName;
    private Drawable drawable;
    private String extraIconUrl;
    private String fileName;
    private long fileSize;
    private int iconId;
    private String iconUrl;
    private String label;
    private String md5;
    private PackageInfo packageInfo;
    private String path;
    private String url;

    public PackageInfo getPackageInfo() {
        return this.packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtraIconUrl() {
        return this.extraIconUrl;
    }

    public void setExtraIconUrl(String extraIconUrl) {
        this.extraIconUrl = extraIconUrl;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public Drawable getDrawable() {
        return this.drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static ApkInfo switchSerApkInfoToThis(SerApkInfo serApkInfo) {
        ApkInfo apkInfo = new ApkInfo();
        PackageInfo pInfo = new PackageInfo();
        pInfo.packageName = serApkInfo.getPackageName();
        pInfo.versionCode = serApkInfo.getVer();
        pInfo.versionName = serApkInfo.getVerName();
        apkInfo.setPackageInfo(pInfo);
        apkInfo.setFileSize((long) serApkInfo.getFileSize());
        apkInfo.setAppName(serApkInfo.getAppName());
        apkInfo.setIconId(serApkInfo.getIconId());
        apkInfo.setIconUrl(serApkInfo.getIconUrl());
        apkInfo.setUrl(serApkInfo.getDownloadUrl());
        apkInfo.setMd5(serApkInfo.getFileVerifyCode());
        apkInfo.setFileName(serApkInfo.getFileName());
        return apkInfo;
    }
}
