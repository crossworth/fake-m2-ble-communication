package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111001)
public class GetAdsReq {
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 3)
    private byte position;
    @ByteField(index = 4)
    private String reserved1;
    @ByteField(index = 5)
    private String reserved2;
    @ByteField(index = 0)
    private TerminalInfo terminalInfo = new TerminalInfo();
    @ByteField(index = 2)
    private int version;

    public TerminalInfo getTerminalInfo() {
        return this.terminalInfo;
    }

    public void setTerminalInfo(TerminalInfo terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public byte getPosition() {
        return this.position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public String toString() {
        return "GetAdsReq [terminalInfo=" + this.terminalInfo + ", packageName=" + this.packageName + ", version=" + this.version + ", position=" + this.position + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
