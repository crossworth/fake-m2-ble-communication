package com.zhuoyi.system.statistics.prom.model;

public class MyDownloadResult {
    private int downloadResult;
    private int downloadSize;
    private int offset;
    private String packageName;
    private int source1;
    private int source2;
    private int totalSize;
    private int versionCode;

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

    public int getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDownloadSize() {
        return this.downloadSize;
    }

    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }

    public int getDownloadResult() {
        return this.downloadResult;
    }

    public void setDownloadResult(int downloadResult) {
        this.downloadResult = downloadResult;
    }

    public int getSource1() {
        return this.source1;
    }

    public void setSource1(int source1) {
        this.source1 = source1;
    }

    public int getSource2() {
        return this.source2;
    }

    public void setSource2(int source2) {
        this.source2 = source2;
    }
}
