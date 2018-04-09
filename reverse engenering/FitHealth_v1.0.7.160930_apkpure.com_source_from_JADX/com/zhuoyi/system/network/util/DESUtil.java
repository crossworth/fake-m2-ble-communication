package com.zhuoyi.system.network.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        src = padding(src, (byte) 0);
        SecretKey securekey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key));
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(1, securekey);
        return cipher.doFinal(src);
    }

    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecretKey securekey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key));
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(2, securekey);
        return cipher.doFinal(src);
    }

    private static byte[] padding(byte[] sourceBytes, byte b) {
        byte[] paddingBytes = new byte[(8 - (sourceBytes.length % 8))];
        for (int i = 0; i < paddingBytes.length; i++) {
            paddingBytes[i] = b;
        }
        return ArrayUtils.addAll(sourceBytes, paddingBytes);
    }
}
