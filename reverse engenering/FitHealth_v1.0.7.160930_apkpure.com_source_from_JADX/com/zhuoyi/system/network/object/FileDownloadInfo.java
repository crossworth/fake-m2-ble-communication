package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class FileDownloadInfo implements Serializable {
    private static final long serialVersionUID = 9053230197538092748L;
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

    public String toString() {
        return "FileDownloadInfo [fileVerInfo=" + this.fileVerInfo + ", offset=" + this.offset + "]";
    }
}
