package com.aps;

import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.zip.CRC32;
import p031u.aly.au;

/* compiled from: Req */
public class C0462o {
    public byte[] f1904A = null;
    public String f1905a = "1";
    public short f1906b = (short) 0;
    public String f1907c = null;
    public String f1908d = null;
    public String f1909e = null;
    public String f1910f = null;
    public String f1911g = null;
    public String f1912h = null;
    public String f1913i = null;
    public String f1914j = null;
    public String f1915k = null;
    public String f1916l = null;
    public String f1917m = null;
    public String f1918n = null;
    public String f1919o = null;
    public String f1920p = null;
    public String f1921q = null;
    public String f1922r = null;
    public String f1923s = null;
    public String f1924t = null;
    public String f1925u = null;
    public String f1926v = null;
    public String f1927w = null;
    public String f1928x = null;
    public String f1929y = null;
    public String f1930z = null;

    public byte[] m1984a() {
        Object a;
        Object b;
        m1983b();
        int i = 1024;
        if (this.f1904A != null) {
            i = 1024 + (this.f1904A.length + 1);
        }
        Object obj = new byte[i];
        obj[0] = Byte.parseByte(this.f1905a);
        Object b2 = C0466q.m1996b(this.f1906b);
        System.arraycopy(b2, 0, obj, 1, b2.length);
        int length = b2.length + 1;
        try {
            b2 = this.f1907c.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th) {
            th.printStackTrace();
            C0470t.m2008a(th);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1908d.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th2) {
            th2.printStackTrace();
            C0470t.m2008a(th2);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1919o.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th22) {
            th22.printStackTrace();
            C0470t.m2008a(th22);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1909e.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th222) {
            th222.printStackTrace();
            C0470t.m2008a(th222);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1910f.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th2222) {
            th2222.printStackTrace();
            C0470t.m2008a(th2222);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1911g.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th22222) {
            th22222.printStackTrace();
            C0470t.m2008a(th22222);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1923s.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th222222) {
            th222222.printStackTrace();
            C0470t.m2008a(th222222);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1912h.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th2222222) {
            th2222222.printStackTrace();
            C0470t.m2008a(th2222222);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1920p.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            length += b2.length;
        } catch (Throwable th22222222) {
            th22222222.printStackTrace();
            C0470t.m2008a(th22222222);
            obj[length] = null;
            length++;
        }
        try {
            b2 = this.f1921q.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            i = b2.length + length;
        } catch (Throwable th222222222) {
            th222222222.printStackTrace();
            C0470t.m2008a(th222222222);
            obj[length] = null;
            i = length + 1;
        }
        if (TextUtils.isEmpty(this.f1922r)) {
            obj[i] = null;
            length = i + 1;
        } else {
            a = m1981a(this.f1922r);
            obj[i] = (byte) a.length;
            i++;
            System.arraycopy(a, 0, obj, i, a.length);
            length = a.length + i;
        }
        try {
            b2 = this.f1924t.getBytes("GBK");
            obj[length] = (byte) b2.length;
            length++;
            System.arraycopy(b2, 0, obj, length, b2.length);
            i = b2.length + length;
        } catch (Throwable th2222222222) {
            th2222222222.printStackTrace();
            C0470t.m2008a(th2222222222);
            obj[length] = null;
            i = length + 1;
        }
        obj[i] = Byte.parseByte(this.f1925u);
        i++;
        obj[i] = Byte.parseByte(this.f1914j);
        i++;
        if (this.f1914j.equals("1") || this.f1914j.equals("2")) {
            a = C0466q.m1993a(Integer.parseInt(this.f1916l));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
        }
        if (this.f1914j.equals("1") || this.f1914j.equals("2")) {
            a = C0466q.m1993a(Integer.parseInt(this.f1917m));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
        }
        if (this.f1914j.equals("1") || this.f1914j.equals("2")) {
            a = C0466q.m1997b(this.f1918n);
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
        }
        obj[i] = Byte.parseByte(this.f1926v);
        i++;
        if (this.f1926v.equals("1")) {
            a = C0466q.m1997b(m1982b("mcc"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1997b(m1982b("mnc"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1997b(m1982b("lac"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1995a(m1982b("cellid"));
            System.arraycopy(a, 0, obj, i, a.length);
            length = a.length + i;
            i = Integer.parseInt(m1982b("signal"));
            if (i > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                i = 0;
            } else if (i < -128) {
                i = 0;
            }
            obj[length] = (byte) i;
            i = length + 1;
            if (this.f1928x.length() == 0) {
                obj[i] = null;
                i++;
            } else {
                int length2 = this.f1928x.split("\\*").length;
                obj[i] = (byte) length2;
                i++;
                length = 0;
                while (length < length2) {
                    b = C0466q.m1997b(m1980a("lac", length));
                    System.arraycopy(b, 0, obj, i, b.length);
                    i += b.length;
                    b = C0466q.m1995a(m1980a("cellid", length));
                    System.arraycopy(b, 0, obj, i, b.length);
                    int length3 = b.length + i;
                    i = Integer.parseInt(m1980a("signal", length));
                    if (i > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                        i = 0;
                    } else if (i < -128) {
                        i = 0;
                    }
                    obj[length3] = (byte) i;
                    length++;
                    i = length3 + 1;
                }
            }
        } else if (this.f1926v.equals("2")) {
            a = C0466q.m1997b(m1982b("mcc"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1997b(m1982b(SocializeProtocolConstants.PROTOCOL_KEY_SID));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1997b(m1982b("nid"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1997b(m1982b("bid"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1995a(m1982b("lon"));
            System.arraycopy(a, 0, obj, i, a.length);
            i += a.length;
            a = C0466q.m1995a(m1982b(au.f3570Y));
            System.arraycopy(a, 0, obj, i, a.length);
            length = a.length + i;
            i = Integer.parseInt(m1982b("signal"));
            if (i > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                i = 0;
            } else if (i < -128) {
                i = 0;
            }
            obj[length] = (byte) i;
            i = length + 1;
            obj[i] = null;
            i++;
        }
        if (this.f1930z.length() == 0) {
            obj[i] = null;
            i++;
        } else {
            String[] split;
            obj[i] = 1;
            length = i + 1;
            try {
                split = this.f1930z.split(SeparatorConstants.SEPARATOR_ADS_ID);
                b2 = m1981a(split[0]);
                System.arraycopy(b2, 0, obj, length, b2.length);
                length += b2.length;
                b2 = split[2].getBytes("GBK");
                obj[length] = (byte) b2.length;
                length++;
                System.arraycopy(b2, 0, obj, length, b2.length);
                length += b2.length;
            } catch (Throwable th22222222222) {
                th22222222222.printStackTrace();
                C0470t.m2008a(th22222222222);
                obj[length] = null;
                i = length + 1;
            }
            i = Integer.parseInt(split[1]);
            if (i > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                i = 0;
            } else if (i < -128) {
                i = 0;
            }
            obj[length] = Byte.parseByte(String.valueOf(i));
            i = length + 1;
        }
        String[] split2 = this.f1929y.split("\\*");
        if (TextUtils.isEmpty(this.f1929y) || split2.length == 0) {
            obj[i] = null;
            i++;
        } else {
            obj[i] = (byte) split2.length;
            i++;
            length3 = 0;
            while (length3 < split2.length) {
                String[] split3 = split2[length3].split(SeparatorConstants.SEPARATOR_ADS_ID);
                a = m1981a(split3[0]);
                System.arraycopy(a, 0, obj, i, a.length);
                length = a.length + i;
                try {
                    b2 = split3[2].getBytes("GBK");
                    obj[length] = (byte) b2.length;
                    length++;
                    System.arraycopy(b2, 0, obj, length, b2.length);
                    i = b2.length + length;
                } catch (Throwable th222222222222) {
                    th222222222222.printStackTrace();
                    C0470t.m2008a(th222222222222);
                    obj[length] = null;
                    i = length + 1;
                }
                length = Integer.parseInt(split3[1]);
                if (length > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                    length = 0;
                } else if (length < -128) {
                    length = 0;
                }
                obj[i] = Byte.parseByte(String.valueOf(length));
                length3++;
                i++;
            }
        }
        if (this.f1904A != null) {
            length = this.f1904A.length;
        } else {
            length = 0;
        }
        b = C0466q.m1996b(length);
        System.arraycopy(b, 0, obj, i, b.length);
        i += b.length;
        if (length > 0) {
            System.arraycopy(this.f1904A, 0, obj, i, this.f1904A.length);
            i += this.f1904A.length;
        }
        a = new byte[i];
        System.arraycopy(obj, 0, a, 0, i);
        CRC32 crc32 = new CRC32();
        crc32.update(a);
        b = C0466q.m1994a(crc32.getValue());
        obj = new byte[(b.length + i)];
        System.arraycopy(a, 0, obj, 0, i);
        System.arraycopy(b, 0, obj, i, b.length);
        i += b.length;
        return obj;
    }

    private byte[] m1981a(String str) {
        String[] split = str.split(":");
        if (split == null || split.length != 6) {
            String[] strArr = new String[6];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = "0";
            }
            split = strArr;
        }
        byte[] bArr = new byte[6];
        for (int i2 = 0; i2 < split.length; i2++) {
            if (split[i2].length() > 2) {
                split[i2] = split[i2].substring(0, 2);
            }
            bArr[i2] = (byte) Integer.parseInt(split[i2], 16);
        }
        return bArr;
    }

    private String m1982b(String str) {
        if (!this.f1927w.contains(str + ">")) {
            return "0";
        }
        int indexOf = this.f1927w.indexOf(str + ">");
        return this.f1927w.substring((indexOf + str.length()) + 1, this.f1927w.indexOf("</" + str));
    }

    private String m1980a(String str, int i) {
        String[] split = this.f1928x.split("\\*")[i].split(SeparatorConstants.SEPARATOR_ADS_ID);
        if (str.equals("lac")) {
            return split[0];
        }
        if (str.equals("cellid")) {
            return split[1];
        }
        if (str.equals("signal")) {
            return split[2];
        }
        return null;
    }

    private void m1983b() {
        if (this.f1905a == null) {
            this.f1905a = "";
        }
        if (this.f1907c == null) {
            this.f1907c = "";
        }
        if (this.f1908d == null) {
            this.f1908d = "";
        }
        if (this.f1909e == null) {
            this.f1909e = "";
        }
        if (this.f1910f == null) {
            this.f1910f = "";
        }
        if (this.f1911g == null) {
            this.f1911g = "";
        }
        if (this.f1912h == null) {
            this.f1912h = "";
        }
        if (this.f1913i == null) {
            this.f1913i = "";
        }
        if (this.f1914j == null) {
            this.f1914j = "0";
        } else if (!(this.f1914j.equals("1") || this.f1914j.equals("2"))) {
            this.f1914j = "0";
        }
        if (this.f1915k == null) {
            this.f1915k = "0";
        } else if (!(this.f1915k.equals("0") || this.f1915k.equals("1"))) {
            this.f1915k = "0";
        }
        if (this.f1916l == null) {
            this.f1916l = "";
        } else {
            this.f1916l = String.valueOf(Double.valueOf(Double.parseDouble(this.f1916l) * 1200000.0d).intValue());
        }
        if (this.f1917m == null) {
            this.f1917m = "";
        } else {
            this.f1917m = String.valueOf(Double.valueOf(Double.parseDouble(this.f1917m) * 1000000.0d).intValue());
        }
        if (this.f1918n == null) {
            this.f1918n = "";
        }
        if (this.f1919o == null) {
            this.f1919o = "";
        }
        if (this.f1920p == null) {
            this.f1920p = "";
        }
        if (this.f1921q == null) {
            this.f1921q = "";
        }
        if (this.f1922r == null) {
            this.f1922r = "";
        }
        if (this.f1923s == null) {
            this.f1923s = "";
        }
        if (this.f1924t == null) {
            this.f1924t = "";
        }
        if (this.f1925u == null) {
            this.f1925u = "0";
        } else if (!(this.f1925u.equals("1") || this.f1925u.equals("2"))) {
            this.f1925u = "0";
        }
        if (this.f1926v == null) {
            this.f1926v = "0";
        } else if (!(this.f1926v.equals("1") || this.f1926v.equals("2"))) {
            this.f1926v = "0";
        }
        if (this.f1927w == null) {
            this.f1927w = "";
        }
        if (this.f1928x == null) {
            this.f1928x = "";
        }
        if (this.f1929y == null) {
            this.f1929y = "";
        }
        if (this.f1930z == null) {
            this.f1930z = "";
        }
        if (this.f1904A == null) {
            this.f1904A = new byte[0];
        }
    }
}
