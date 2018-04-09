package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111009)
public class GetCommonConfigReq {
    @ByteField(index = 2)
    private String isFirstReq;
    @ByteField(index = 1)
    private String packageName;
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

    public String getIsFirstReq() {
        return this.isFirstReq;
    }

    public void setIsFirstReq(String isFirstReq) {
        this.isFirstReq = isFirstReq;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }
}
