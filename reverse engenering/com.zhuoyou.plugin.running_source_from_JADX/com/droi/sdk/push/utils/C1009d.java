package com.droi.sdk.push.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class C1009d {
    public static byte[] m3128a(byte[] bArr) {
        return bArr == null ? null : (byte[]) bArr.clone();
    }

    private static byte[] m3129a(byte[] bArr, byte b) {
        byte[] bArr2 = new byte[(8 - (bArr.length % 8))];
        for (int i = 0; i < bArr2.length; i++) {
            bArr2[i] = b;
        }
        return C1009d.m3132c(bArr, bArr2);
    }

    public static byte[] m3130a(byte[] bArr, byte[] bArr2) {
        byte[] a = C1009d.m3129a(bArr, (byte) 0);
        Key generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr2));
        Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
        instance.init(1, generateSecret);
        return instance.doFinal(a);
    }

    public static byte[] m3131b(byte[] bArr, byte[] bArr2) {
        Key generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr2));
        Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
        instance.init(2, generateSecret);
        return instance.doFinal(bArr);
    }

    public static byte[] m3132c(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return C1009d.m3128a(bArr2);
        }
        if (bArr2 == null) {
            return C1009d.m3128a(bArr);
        }
        Object obj = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        System.arraycopy(bArr2, 0, obj, bArr.length, bArr2.length);
        return obj;
    }
}
