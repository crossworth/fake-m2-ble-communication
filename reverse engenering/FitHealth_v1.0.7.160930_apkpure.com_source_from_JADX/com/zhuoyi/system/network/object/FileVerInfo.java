package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class FileVerInfo implements Serializable {
    private static final long serialVersionUID = 3430827059186183259L;
    @ByteField(index = 0)
    private String name;
    @ByteField(index = 1)
    private int ver;

    public FileVerInfo(String name, int ver) {
        this.name = name;
        this.ver = ver;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "FileVerInfo [name=" + this.name + ", ver=" + this.ver + "]";
    }
}
