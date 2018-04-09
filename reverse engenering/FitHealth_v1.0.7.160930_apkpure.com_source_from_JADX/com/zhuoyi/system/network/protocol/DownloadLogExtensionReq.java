package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.FileDownloadResultExt;
import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import java.util.ArrayList;

@SignalCode(encrypt = true, messageCode = 114007)
public class DownloadLogExtensionReq {
    @ByteField(index = 1)
    private ArrayList<FileDownloadResultExt> notifyInfos = new ArrayList();
    @ByteField(index = 3)
    private String reserved1;
    @ByteField(index = 4)
    private String reserved2;
    @ByteField(index = 5)
    private String reserved3;
    @ByteField(index = 0)
    private TerminalInfo termInfo = new TerminalInfo();
    @ByteField(index = 2)
    private String token;

    public TerminalInfo getTermInfo() {
        return this.termInfo;
    }

    public void setTermInfo(TerminalInfo termInfo) {
        this.termInfo = termInfo;
    }

    public ArrayList<FileDownloadResultExt> getNotifyInfos() {
        return this.notifyInfos;
    }

    public void setNotifyInfos(ArrayList<FileDownloadResultExt> notifyInfos) {
        this.notifyInfos = notifyInfos;
    }

    public void addNotifyInfo(FileDownloadResultExt notifyInfo) {
        this.notifyInfos.add(notifyInfo);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String toString() {
        return "DownloadLogExtensionReq [termInfo=" + this.termInfo + ", notifyInfos=" + this.notifyInfos + ", token=" + this.token + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + "]";
    }
}
