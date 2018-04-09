package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111008)
public class GetShortcutNewReq {
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 4)
    private String reserved1;
    @ByteField(index = 5)
    private String reserved2;
    @ByteField(description = "1:桌面 2：优化大师", index = 3)
    private int source;
    @ByteField(index = 0)
    private TerminalInfo terminalInfo;
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

    public int getSource() {
        return this.source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String toString() {
        return "GetShortcutNewReq [terminalInfo=" + this.terminalInfo + ", packageName=" + this.packageName + ", version=" + this.version + ", source=" + this.source + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
