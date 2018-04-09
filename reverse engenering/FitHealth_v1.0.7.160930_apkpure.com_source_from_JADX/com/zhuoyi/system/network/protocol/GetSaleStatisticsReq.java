package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(encrypt = true, messageCode = 114001)
public class GetSaleStatisticsReq {
    @ByteField(index = 2)
    private String activeTime;
    @ByteField(index = 1)
    private String mac;
    @ByteField(index = 4)
    private String reserved2;
    @ByteField(index = 5)
    private String reserved3;
    @ByteField(index = 6)
    private String reserved4;
    @ByteField(index = 0)
    private TerminalInfo termInfo = new TerminalInfo();
    @ByteField(index = 3)
    private String token;

    public TerminalInfo getTermInfo() {
        return this.termInfo;
    }

    public void setTermInfo(TerminalInfo termInfo) {
        this.termInfo = termInfo;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return this.reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getReserved4() {
        return this.reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return "GetSaleStatisticsReq [termInfo=" + this.termInfo + ", mac=" + this.mac + ", activeTime=" + this.activeTime + ", token=" + this.token + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + ", reserved4=" + this.reserved4 + "]";
    }
}
