package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.AdLogInfo;
import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import java.util.ArrayList;

@SignalCode(encrypt = true, messageCode = 114002)
public class GetAdsLogReq {
    @ByteField(index = 2)
    private ArrayList<AdLogInfo> adLogInfoList = new ArrayList();
    @ByteField(index = 1)
    private String mac;
    @ByteField(index = 5)
    private String reserved2;
    @ByteField(index = 6)
    private String reserved3;
    @ByteField(index = 7)
    private String reserved4;
    @ByteField(index = 0)
    private TerminalInfo termInfo = new TerminalInfo();
    @ByteField(index = 4)
    private String token;
    @ByteField(index = 3)
    private String uploadTime;

    public TerminalInfo getTerminalInfo() {
        return this.termInfo;
    }

    public void setTerminalInfo(TerminalInfo termInfo) {
        this.termInfo = termInfo;
    }

    public ArrayList<AdLogInfo> getAdLogInfoList() {
        return this.adLogInfoList;
    }

    public void setAdLogInfoList(ArrayList<AdLogInfo> adLogInfoList) {
        this.adLogInfoList = adLogInfoList;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

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

    public String getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return "GetAdsLogReq [termInfo=" + this.termInfo + ", mac=" + this.mac + ", adLogInfoList=" + this.adLogInfoList + ", uploadTime=" + this.uploadTime + ", token=" + this.token + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + ", reserved4=" + this.reserved4 + "]";
    }
}
