package com.zhuoyou.plugin.running.net;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
    private static final String algorithm = "DES/ECB/NoPadding";

    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        src = padding(src, (byte) 0);
        SecretKey securekey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key));
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(1, securekey);
        return cipher.doFinal(src);
    }

    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecretKey securekey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key));
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(2, securekey);
        return cipher.doFinal(src);
    }

    private static byte[] padding(byte[] sourceBytes, byte b) {
        byte[] paddingBytes = new byte[(8 - (sourceBytes.length % 8))];
        for (int i = 0; i < paddingBytes.length; i++) {
            paddingBytes[i] = b;
        }
        return addAll(sourceBytes, paddingBytes);
    }

    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }
}
