package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class FileDownloadResult implements Serializable {
    private static final long serialVersionUID = 1;
    @ByteField(index = 3)
    private int fileSize;
    @ByteField(index = 1)
    private FileVerInfo fileVerInfo = new FileVerInfo();
    @ByteField(index = 2)
    private int offset;
    @ByteField(index = 0)
    private int result;
    @ByteField(index = 4)
    private int transportSize;

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

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

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getTransportSize() {
        return this.transportSize;
    }

    public void setTransportSize(int transportSize) {
        this.transportSize = transportSize;
    }

    public String toString() {
        return "FileDownloadResult [result=" + this.result + ", fileVerInfo=" + this.fileVerInfo + ", offset=" + this.offset + ", fileSize=" + this.fileSize + ", transportSize=" + this.transportSize + "]";
    }
}
