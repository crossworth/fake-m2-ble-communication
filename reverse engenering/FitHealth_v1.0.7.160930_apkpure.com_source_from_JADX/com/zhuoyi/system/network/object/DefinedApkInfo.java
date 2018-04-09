package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class DefinedApkInfo implements Serializable {
    private static final long serialVersionUID = 1;
    @ByteField(index = 1)
    private String fileName;
    @ByteField(index = 2)
    private String filePath;
    @ByteField(index = 0)
    private int id;
    @ByteField(index = 3)
    private byte needPush;
    @ByteField(index = 4)
    private String packageName;
    @ByteField(index = 5)
    private PromAppInfo pushInfo;
    @ByteField(index = 6)
    private String reserved1;
    @ByteField(index = 7)
    private String reserved2;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean getNeedPush() {
        return this.needPush != (byte) 0;
    }

    public void setNeedPush(byte needPush) {
        this.needPush = needPush;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public PromAppInfo getPushInfo() {
        return this.pushInfo;
    }

    public void setPushInfo(PromAppInfo pushInfo) {
        this.pushInfo = pushInfo;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String toString() {
        return "DefinedApkInfo [id=" + this.id + ", fileName=" + this.fileName + ", filePath=" + this.filePath + ", needPush=" + this.needPush + ", packageName=" + this.packageName + ", pushInfo=" + this.pushInfo + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
