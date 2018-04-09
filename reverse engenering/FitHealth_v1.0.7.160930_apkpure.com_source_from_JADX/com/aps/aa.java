package com.aps;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

final class aa implements Serializable {
    protected byte[] f1688a = new byte[16];
    protected byte[] f1689b = new byte[16];
    protected byte[] f1690c = new byte[16];
    protected short f1691d = (short) 0;
    protected short f1692e = (short) 0;
    protected byte f1693f = (byte) 0;
    protected byte[] f1694g = new byte[16];
    protected byte[] f1695h = new byte[32];
    protected short f1696i = (short) 0;
    protected ArrayList f1697j = new ArrayList();
    private byte f1698k = (byte) 4;
    private short f1699l = (short) 0;

    aa() {
    }

    private Boolean m1725a(DataOutputStream dataOutputStream) {
        Boolean.valueOf(true);
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream2.flush();
            dataOutputStream2.write(this.f1688a);
            dataOutputStream2.write(this.f1689b);
            dataOutputStream2.write(this.f1690c);
            dataOutputStream2.writeShort(this.f1691d);
            dataOutputStream2.writeShort(this.f1692e);
            dataOutputStream2.writeByte(this.f1693f);
            this.f1694g[15] = (byte) 0;
            dataOutputStream2.write(af.m1739a(this.f1694g, this.f1694g.length));
            this.f1695h[31] = (byte) 0;
            dataOutputStream2.write(af.m1739a(this.f1695h, this.f1695h.length));
            dataOutputStream2.writeShort(this.f1696i);
            for (short s = (short) 0; s < this.f1696i; s = (short) (s + 1)) {
                OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream2);
                dataOutputStream3.flush();
                C0474x c0474x = (C0474x) this.f1697j.get(s);
                if (!(c0474x.f1959c == null || c0474x.f1959c.m2077a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(c0474x.f1960d == null || c0474x.f1960d.m2020a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(c0474x.f1961e == null || c0474x.f1961e.m1752a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(c0474x.f1962f == null || c0474x.f1962f.m1728a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                c0474x.f1957a = Integer.valueOf(byteArrayOutputStream2.size() + 4).shortValue();
                dataOutputStream2.writeShort(c0474x.f1957a);
                dataOutputStream2.writeInt(c0474x.f1958b);
                dataOutputStream2.write(byteArrayOutputStream2.toByteArray());
            }
            this.f1699l = Integer.valueOf(byteArrayOutputStream.size()).shortValue();
            dataOutputStream.writeByte(this.f1698k);
            dataOutputStream.writeShort(this.f1699l);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }

    protected final byte[] m1726a() {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        m1725a(new DataOutputStream(byteArrayOutputStream));
        return byteArrayOutputStream.toByteArray();
    }
}
