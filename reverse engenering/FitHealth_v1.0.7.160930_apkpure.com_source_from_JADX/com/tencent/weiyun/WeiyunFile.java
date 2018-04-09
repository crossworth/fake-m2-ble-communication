package com.tencent.weiyun;

/* compiled from: ProGuard */
public class WeiyunFile {
    private String f3001a;
    private String f3002b;
    private String f3003c;
    private long f3004d;

    public WeiyunFile(String str, String str2, String str3, long j) {
        this.f3001a = str;
        this.f3002b = str2;
        this.f3003c = str3;
        this.f3004d = j;
    }

    public String getFileId() {
        return this.f3001a;
    }

    public String getFileName() {
        return this.f3002b;
    }

    public String getCreateTime() {
        return this.f3003c;
    }

    public long getFileSize() {
        return this.f3004d;
    }

    public void setFileId(String str) {
        this.f3001a = str;
    }

    public void setFileName(String str) {
        this.f3002b = str;
    }

    public void setCreateTime(String str) {
        this.f3003c = str;
    }

    public void setFileSize(long j) {
        this.f3004d = j;
    }
}
