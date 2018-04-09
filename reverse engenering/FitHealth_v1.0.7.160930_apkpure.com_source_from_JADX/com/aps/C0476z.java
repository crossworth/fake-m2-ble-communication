package com.aps;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

final class C0476z implements Serializable {
    protected int f2015a = 0;
    protected int f2016b = 0;
    protected int f2017c = 0;
    protected int f2018d = 0;
    protected int f2019e = 0;
    protected short f2020f = (short) 0;
    protected byte f2021g = (byte) 0;
    protected byte f2022h = (byte) 0;
    protected long f2023i = 0;
    protected long f2024j = 0;
    private byte f2025k = (byte) 1;

    C0476z() {
    }

    protected final Boolean m2077a(DataOutputStream dataOutputStream) {
        Boolean valueOf = Boolean.valueOf(false);
        if (dataOutputStream != null) {
            try {
                dataOutputStream.writeByte(this.f2025k);
                dataOutputStream.writeInt(this.f2015a);
                dataOutputStream.writeInt(this.f2016b);
                dataOutputStream.writeInt(this.f2017c);
                dataOutputStream.writeInt(this.f2018d);
                dataOutputStream.writeInt(this.f2019e);
                dataOutputStream.writeShort(this.f2020f);
                dataOutputStream.writeByte(this.f2021g);
                dataOutputStream.writeByte(this.f2022h);
                dataOutputStream.writeLong(this.f2023i);
                dataOutputStream.writeLong(this.f2024j);
                valueOf = Boolean.valueOf(true);
            } catch (IOException e) {
            }
        }
        return valueOf;
    }
}
