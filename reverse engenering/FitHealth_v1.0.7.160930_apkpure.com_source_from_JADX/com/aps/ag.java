package com.aps;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

final class ag implements Serializable {
    protected int f1714a = 0;
    protected int f1715b = 0;
    protected short f1716c = (short) 0;
    protected short f1717d = (short) 0;
    protected int f1718e = 0;
    protected byte f1719f = (byte) 0;
    private byte f1720g = (byte) 4;

    ag() {
    }

    protected final Boolean m1752a(DataOutputStream dataOutputStream) {
        Boolean valueOf = Boolean.valueOf(false);
        try {
            dataOutputStream.writeByte(this.f1720g);
            dataOutputStream.writeInt(this.f1714a);
            dataOutputStream.writeInt(this.f1715b);
            dataOutputStream.writeShort(this.f1716c);
            dataOutputStream.writeShort(this.f1717d);
            dataOutputStream.writeInt(this.f1718e);
            dataOutputStream.writeByte(this.f1719f);
            valueOf = Boolean.valueOf(true);
        } catch (IOException e) {
        }
        return valueOf;
    }
}
