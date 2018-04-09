package com.aps;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

final class ac implements Serializable {
    protected byte f1700a = (byte) 0;
    protected ArrayList f1701b = new ArrayList();
    private byte f1702c = (byte) 8;

    ac() {
    }

    protected final Boolean m1728a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.f1702c);
            dataOutputStream.writeByte(this.f1700a);
            for (byte b = (byte) 0; b < this.f1700a; b++) {
                ad adVar = (ad) this.f1701b.get(b);
                dataOutputStream.write(adVar.f1703a);
                dataOutputStream.writeShort(adVar.f1704b);
                dataOutputStream.write(af.m1739a(adVar.f1705c, adVar.f1705c.length));
            }
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }
}
