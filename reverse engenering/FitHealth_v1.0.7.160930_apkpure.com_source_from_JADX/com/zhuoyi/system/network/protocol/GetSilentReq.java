package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111016)
public class GetSilentReq {
    @ByteField(index = 3)
    private int commandType;
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 5)
    private String reserved1;
    @ByteField(index = 6)
    private String reserved2;
    @ByteField(index = 7)
    private String reserved3;
    @ByteField(index = 8)
    private String reserved4;
    @ByteField(index = 9)
    private String reserved5;
    @ByteField(index = 10)
    private String reserved6;
    @ByteField(index = 4)
    private String root;
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

    public int getCommandType() {
        return this.commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public String getRoot() {
        return this.root;
    }

    public void setRoot(String root) {
        this.root = root;
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

    public String getReserved5() {
        return this.reserved5;
    }

    public void setReserved5(String reserved5) {
        this.reserved5 = reserved5;
    }

    public String getReserved6() {
        return this.reserved6;
    }

    public void setReserved6(String reserved6) {
        this.reserved6 = reserved6;
    }

    public String toString() {
        return "GetSilentReq [terminalInfo=" + this.terminalInfo + ", packageName=" + this.packageName + ", version=" + this.version + ", commandType=" + this.commandType + ", root=" + this.root + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + ", reserved4=" + this.reserved4 + ", reserved5=" + this.reserved5 + ", reserved6=" + this.reserved6 + "]";
    }
}
