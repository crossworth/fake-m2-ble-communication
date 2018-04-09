package com.zhuoyi.system.util.model;

import java.io.Serializable;

public class MyPackageInfo implements Serializable {
    private static final long serialVersionUID = -4644479123519038658L;
    private String activityName;
    private String apkPath;
    private boolean extra = false;
    private boolean imeInstall = false;
    private boolean imeOpen = true;
    private String packageName = "";
    private int position;
    private int source;
    private int versionCode;

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public MyPackageInfo(String packageName) {
        this.packageName = packageName;
    }

    public MyPackageInfo(String packageName, int versionCode) {
        this.packageName = packageName;
        this.versionCode = versionCode;
    }

    public MyPackageInfo(String packageName, int versionCode, int position, int source) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.position = position;
        this.source = source;
    }

    public MyPackageInfo(String packageName, int versionCode, int position, int source, String activityName) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.position = position;
        this.source = source;
        this.activityName = activityName;
    }

    public MyPackageInfo(String packageName, int versionCode, int position, int source, boolean imeOpen) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.position = position;
        this.source = source;
        this.imeOpen = imeOpen;
    }

    public MyPackageInfo(boolean extra, String packageName, int versionCode, int position, int source) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.position = position;
        this.source = source;
        this.extra = extra;
    }

    public MyPackageInfo(String packageName, int versionCode, int position, int source, boolean imeOpen, boolean extra) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.position = position;
        this.source = source;
        this.imeOpen = imeOpen;
        this.extra = extra;
    }

    public MyPackageInfo(String packageName, int versionCode, int position, int source, boolean imeInstall, boolean imeOpen, boolean extra) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.position = position;
        this.source = source;
        this.imeInstall = imeInstall;
        this.imeOpen = imeOpen;
        this.extra = extra;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public boolean isImeOpen() {
        return this.imeOpen;
    }

    public void setImeOpen(boolean imeOpen) {
        this.imeOpen = imeOpen;
    }

    public boolean isImeInstall() {
        return this.imeInstall;
    }

    public void setImeInstall(boolean imeInstall) {
        this.imeInstall = imeInstall;
    }

    public boolean isExtra() {
        return this.extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public String getApkPath() {
        return this.apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public boolean equals(Object o) {
        if (!(o instanceof MyPackageInfo)) {
            return false;
        }
        MyPackageInfo info = (MyPackageInfo) o;
        if (info.getPackageName().equals(this.packageName) && info.getVersionCode() == this.versionCode) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.packageName.hashCode() + this.versionCode;
    }

    public String toString() {
        return "MyPackageInfo [packageName=" + this.packageName + ", versionCode=" + this.versionCode + ", apkPath=" + this.apkPath + ", position=" + this.position + ", source=" + this.source + ", activityName=" + this.activityName + ", imeInstall=" + this.imeInstall + ", imeOpen=" + this.imeOpen + ", extra=" + this.extra + "]";
    }
}
