package com.droi.sdk.push.utils;

public class C1014i {
    private static char m3147a(int i) {
        int i2 = i & 15;
        return i2 >= 10 ? (char) ((i2 - 10) + 97) : (char) (i2 + 48);
    }

    public static String m3148a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(C1014i.m3147a(bArr[i] >> 4));
            stringBuffer.append(C1014i.m3147a(bArr[i] & 15));
        }
        return stringBuffer.toString();
    }

    public static String m3149a(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length < i + i2) {
            return null;
        }
        String str = "";
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (Exception e) {
            C1012g.m3139b(e);
            return str;
        }
    }
}
