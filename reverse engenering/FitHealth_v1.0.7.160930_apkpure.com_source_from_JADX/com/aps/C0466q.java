package com.aps;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/* compiled from: Storage */
public class C0466q {
    private static C0466q f1936a = null;

    private C0466q() {
    }

    static String m1992a(Object obj, String str) {
        DecimalFormat decimalFormat = new DecimalFormat("#", new DecimalFormatSymbols(Locale.US));
        decimalFormat.applyPattern(str);
        return decimalFormat.format(obj);
    }

    static byte[] m1995a(String str) {
        return C0466q.m1993a(Integer.parseInt(str));
    }

    static byte[] m1993a(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    static byte[] m1997b(String str) {
        return C0466q.m1996b(Integer.parseInt(str));
    }

    static byte[] m1996b(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)};
    }

    public static byte[] m1994a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((int) ((j >> (i * 8)) & 255));
        }
        return bArr;
    }
}
