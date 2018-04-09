package com.aps;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

final class C0472v implements Serializable {
    protected short f1946a = (short) 0;
    protected int f1947b = 0;
    protected byte f1948c = (byte) 0;
    protected byte f1949d = (byte) 0;
    protected ArrayList f1950e = new ArrayList();
    private byte f1951f = (byte) 2;

    C0472v() {
    }

    protected final Boolean m2020a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.f1951f);
            dataOutputStream.writeShort(this.f1946a);
            dataOutputStream.writeInt(this.f1947b);
            dataOutputStream.writeByte(this.f1948c);
            dataOutputStream.writeByte(this.f1949d);
            for (byte b = (byte) 0; b < this.f1949d; b++) {
                dataOutputStream.writeShort(((ah) this.f1950e.get(b)).f1721a);
                dataOutputStream.writeInt(((ah) this.f1950e.get(b)).f1722b);
                dataOutputStream.writeByte(((ah) this.f1950e.get(b)).f1723c);
            }
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }
}
