package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111014)
public class GetPushInAppReq {
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 2)
    private String reserved1;
    @ByteField(index = 3)
    private String reserved2;
    @ByteField(index = 0)
    private TerminalInfo terminalInfo = new TerminalInfo();

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
        return "GetPushInAppReq [terminalInfo=" + this.terminalInfo + ", packageName=" + this.packageName + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
