package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;

@SignalCode(messageCode = 211009)
public class GetCommonConfigResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 1866993317701568L;
    @ByteField(index = 2)
    private byte adSwitch;
    @ByteField(index = 5)
    private String curTime;
    @ByteField(index = 7)
    private String installLocalApkSwitch;
    @ByteField(index = 3)
    private long nextReqTime;
    @ByteField(index = 6)
    private String relativeActiveTime;
    @ByteField(index = 4)
    private long relativeTime;
    @ByteField(index = 8)
    private String reserved4;

    public byte getAdSwitch() {
        return this.adSwitch;
    }

    public void setAdSwitch(byte adSwitch) {
        this.adSwitch = adSwitch;
    }

    public long getNextReqTime() {
        return this.nextReqTime;
    }

    public void setNextReqTime(long nextReqTime) {
        this.nextReqTime = nextReqTime;
    }

    public long getRelativeTime() {
        return this.relativeTime;
    }

    public void setRelativeTime(long relativeTime) {
        this.relativeTime = relativeTime;
    }

    public String getCurTime() {
        return this.curTime;
    }

    public void setCurTime(String curTime) {
        this.curTime = curTime;
    }

    public String getRelativeActiveTime() {
        return this.relativeActiveTime;
    }

    public void setRelativeActiveTime(String relativeActiveTime) {
        this.relativeActiveTime = relativeActiveTime;
    }

    public String getInstallLocalApkSwitch() {
        return this.installLocalApkSwitch;
    }

    public void setInstallLocalApkSwitch(String installLocalApkSwitch) {
        this.installLocalApkSwitch = installLocalApkSwitch;
    }

    public String getReserved4() {
        return this.reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String toString() {
        return "GetCommonConfigResp [adSwitch=" + this.adSwitch + ", nextReqTime=" + this.nextReqTime + ", relativeTime=" + this.relativeTime + ", curTime=" + this.curTime + ", relativeActiveTime=" + this.relativeActiveTime + ", installLocalApkSwitch=" + this.installLocalApkSwitch + ", reserved4=" + this.reserved4 + "]";
    }
}
