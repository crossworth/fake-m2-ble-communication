package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211006)
public class GetPushResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 590189917368869687L;
    @ByteField(index = 2)
    private byte adSwitch;
    @ByteField(index = 4)
    private long nextReqTime;
    @ByteField(index = 3)
    private ArrayList<PromAppInfo> promAppInfos = new ArrayList();
    @ByteField(index = 5)
    private long relativeTime;
    @ByteField(index = 6)
    private String reserved1;
    @ByteField(index = 7)
    private String reserved2;

    public ArrayList<PromAppInfo> getPromAppInfos() {
        return this.promAppInfos;
    }

    public void setPromAppInfos(ArrayList<PromAppInfo> promAppInfos) {
        this.promAppInfos = promAppInfos;
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

    public byte getAdSwitch() {
        return this.adSwitch;
    }

    public void setAdSwitch(byte adSwitch) {
        this.adSwitch = adSwitch;
    }

    public String toString() {
        return "GetPushResp [adSwitch=" + this.adSwitch + ", promAppInfos=" + this.promAppInfos + ", nextReqTime=" + this.nextReqTime + ", relativeTime=" + this.relativeTime + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
