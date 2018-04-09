package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class ShortcutInfo implements Serializable {
    private static final long serialVersionUID = -6887310416310194394L;
    @ByteField(index = 0)
    private String packageName;
    @ByteField(index = 1)
    private String reserved1;
    @ByteField(index = 2)
    private String reserved2;

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
        return "ShortcutInfo [packageName=" + this.packageName + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
