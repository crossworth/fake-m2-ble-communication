package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(messageCode = 111007)
public class GetApkCommandReq {
    @ByteField(index = 3)
    private int commandType;
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 5)
    private String reserved2;
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

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRoot() {
        return this.root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getCommandType() {
        return this.commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public String toString() {
        return "GetApkCommandReq [terminalInfo=" + this.terminalInfo + ", packageName=" + this.packageName + ", version=" + this.version + ", commandType=" + this.commandType + ", root=" + this.root + ", reserved2=" + this.reserved2 + "]";
    }
}
