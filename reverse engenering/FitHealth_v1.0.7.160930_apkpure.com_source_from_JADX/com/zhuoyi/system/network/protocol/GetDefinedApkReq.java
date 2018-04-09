package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111015)
public class GetDefinedApkReq {
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 0)
    private TerminalInfo terminalInfo;

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

    public String toString() {
        return "GetDefinedApkReq [terminalInfo=" + this.terminalInfo + ", packageName=" + this.packageName + "]";
    }
}
