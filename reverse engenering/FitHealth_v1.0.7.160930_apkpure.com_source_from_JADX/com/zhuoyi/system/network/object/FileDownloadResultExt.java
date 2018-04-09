package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;

public class FileDownloadResultExt extends FileDownloadResult {
    private static final long serialVersionUID = -2209996252235793710L;
    @ByteField(index = 7)
    public String reserved1;
    @ByteField(index = 8)
    public String reserved2;
    @ByteField(index = 5)
    private short source1;
    @ByteField(index = 6)
    public short source2;

    public short getSource1() {
        return this.source1;
    }

    public void setSource1(short source1) {
        this.source1 = source1;
    }

    public short getSource2() {
        return this.source2;
    }

    public void setSource2(short source2) {
        this.source2 = source2;
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
        return "FileDownloadResultExt [source1=" + this.source1 + ", source2=" + this.source2 + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
