package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;
import java.util.Arrays;

public class FileDownloadData implements Serializable {
    private static final long serialVersionUID = 4743968910487347236L;
    @ByteField(index = 3)
    private byte[] data = new byte[0];
    @ByteField(index = 1)
    private int errorCode;
    @ByteField(index = 0)
    private FileVerInfo fileVerInfo = new FileVerInfo();
    @ByteField(index = 2)
    private int offset;

    public FileVerInfo getFileVerInfo() {
        return this.fileVerInfo;
    }

    public void setFileVerInfo(FileVerInfo fileVerInfo) {
        this.fileVerInfo = fileVerInfo;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String toString() {
        return "FileDownloadData [fileVerInfo=" + this.fileVerInfo + ", errorCode=" + this.errorCode + ", offset=" + this.offset + ", data=" + Arrays.toString(this.data) + "]";
    }
}
