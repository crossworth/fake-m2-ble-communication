package com.droi.sdk.push.p019a;

import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1014i;
import java.nio.ByteBuffer;

public final class C0974a {
    protected byte[] f3200a;

    public C0974a(byte[] bArr) {
        this.f3200a = bArr;
    }

    public int m2921a() {
        return ByteBuffer.wrap(this.f3200a, 52, 2).getChar();
    }

    public byte[] m2922b() {
        int d = m2924d();
        int a = m2921a();
        if (a < 1 || d != 32) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(this.f3200a, 54, a);
        byte[] bArr = new byte[wrap.remaining()];
        wrap.get(bArr);
        return bArr;
    }

    public String m2923c() {
        return m2924d() == 32 ? C1014i.m3149a(this.f3200a, 4, 40) : null;
    }

    public int m2924d() {
        return this.f3200a[3] & 255;
    }

    public int m2925e() {
        return this.f3200a[2] & 255;
    }

    public boolean m2926f() {
        if (this.f3200a == null || this.f3200a.length == 0) {
            C1012g.m3142d("FormatError: data length error");
            return false;
        } else if (1 != m2925e()) {
            C1012g.m3142d("FormatError: protocol version error - " + m2925e());
            return false;
        } else {
            int d = m2924d();
            int a = m2921a();
            if (d != 32) {
                return true;
            }
            if (a < 1) {
                C1012g.m3142d("FormatError: content length incorrent - " + a);
                return false;
            } else if (this.f3200a.length >= a + 54) {
                return true;
            } else {
                C1012g.m3142d("FormatError: data length incorrent - " + d);
                return false;
            }
        }
    }

    public byte[] m2927g() {
        return this.f3200a;
    }

    public long m2928h() {
        return ByteBuffer.wrap(this.f3200a, 44, 8).getLong();
    }
}
