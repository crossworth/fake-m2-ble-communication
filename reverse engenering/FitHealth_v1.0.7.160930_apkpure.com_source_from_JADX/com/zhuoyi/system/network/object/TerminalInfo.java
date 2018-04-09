package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;

public class TerminalInfo implements Cloneable {
    @ByteField(index = 9)
    private short Lac;
    @ByteField(description = "应用程序id", index = 13)
    private String appId;
    @ByteField(description = "通道标识符", index = 12)
    private String channelId;
    @ByteField(index = 0)
    private String hsman;
    @ByteField(index = 1)
    private String hstype;
    @ByteField(index = 7)
    private String imei;
    @ByteField(index = 6)
    private String imsi;
    @ByteField(index = 10)
    private String ip;
    @ByteField(description = "1:2G, 2:3G, 3:wifi", index = 11)
    private byte networkType;
    @ByteField(index = 2)
    private String osVer;
    @ByteField(description = "运营商 1:中国移动 2:中国联通 3:中国电信 4:其它", index = 15)
    private String providersName;
    @ByteField(index = 5)
    private short ramSize;
    @ByteField(index = 4)
    private short screenHeight;
    @ByteField(index = 3)
    private short screenWidth;
    @ByteField(index = 8)
    private String smsCenter;
    @ByteField(description = "版本号", index = 14)
    private String versionCode;

    public String getHsman() {
        return this.hsman;
    }

    public void setHsman(String hsman) {
        this.hsman = hsman;
    }

    public String getHstype() {
        return this.hstype;
    }

    public void setHstype(String hstype) {
        this.hstype = hstype;
    }

    public short getRamSize() {
        return this.ramSize;
    }

    public void setRamSize(short ramSize) {
        this.ramSize = ramSize;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public short getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenWidth(short screenWidth) {
        this.screenWidth = screenWidth;
    }

    public short getScreenHeight() {
        return this.screenHeight;
    }

    public void setScreenHeight(short screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getOsVer() {
        return this.osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getSmsCenter() {
        return this.smsCenter;
    }

    public void setSmsCenter(String smsCenter) {
        this.smsCenter = smsCenter;
    }

    public short getLac() {
        return this.Lac;
    }

    public void setLac(short lac) {
        this.Lac = lac;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public byte getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(byte networkType) {
        this.networkType = networkType;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getProvidersName() {
        return this.providersName;
    }

    public void setProvidersName(String providersName) {
        this.providersName = providersName;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "TerminalInfo [hsman=" + this.hsman + ", hstype=" + this.hstype + ", osVer=" + this.osVer + ", screenWidth=" + this.screenWidth + ", screenHeight=" + this.screenHeight + ", ramSize=" + this.ramSize + ", imsi=" + this.imsi + ", imei=" + this.imei + ", smsCenter=" + this.smsCenter + ", Lac=" + this.Lac + ", ip=" + this.ip + ", networkType=" + this.networkType + ", channelId=" + this.channelId + ", appId=" + this.appId + ", versionCode=" + this.versionCode + ", providersName=" + this.providersName + "]";
    }
}
