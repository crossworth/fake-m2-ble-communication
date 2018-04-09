package com.baidu.location.p001b.p003b;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class C0516a {
    private static final String f2252a = "AES";
    private static final String f2253if = "AES/CBC/PKCS5Padding";

    private C0516a() {
    }

    public static byte[] m2180a(String str, String str2, byte[] bArr) throws Exception {
        Key secretKeySpec = new SecretKeySpec(str2.getBytes(), f2252a);
        Cipher instance = Cipher.getInstance(f2253if);
        instance.init(2, secretKeySpec, new IvParameterSpec(str.getBytes()));
        return instance.doFinal(bArr);
    }

    public static byte[] m2181if(String str, String str2, byte[] bArr) throws Exception {
        Key secretKeySpec = new SecretKeySpec(str2.getBytes(), f2252a);
        Cipher instance = Cipher.getInstance(f2253if);
        instance.init(1, secretKeySpec, new IvParameterSpec(str.getBytes()));
        return instance.doFinal(bArr);
    }
}
