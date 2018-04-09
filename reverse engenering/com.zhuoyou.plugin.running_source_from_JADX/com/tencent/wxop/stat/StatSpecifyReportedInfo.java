package com.tencent.wxop.stat;

public class StatSpecifyReportedInfo {
    private String f4585a = null;
    private String f4586b = null;
    private String f4587c = null;
    private boolean f4588d = false;
    private boolean f4589e = false;

    public String getAppKey() {
        return this.f4585a;
    }

    public String getInstallChannel() {
        return this.f4586b;
    }

    public String getVersion() {
        return this.f4587c;
    }

    public boolean isImportant() {
        return this.f4589e;
    }

    public boolean isSendImmediately() {
        return this.f4588d;
    }

    public void setAppKey(String str) {
        this.f4585a = str;
    }

    public void setImportant(boolean z) {
        this.f4589e = z;
    }

    public void setInstallChannel(String str) {
        this.f4586b = str;
    }

    public void setSendImmediately(boolean z) {
        this.f4588d = z;
    }

    public void setVersion(String str) {
        this.f4587c = str;
    }

    public String toString() {
        return "StatSpecifyReportedInfo [appKey=" + this.f4585a + ", installChannel=" + this.f4586b + ", version=" + this.f4587c + ", sendImmediately=" + this.f4588d + ", isImportant=" + this.f4589e + "]";
    }
}
