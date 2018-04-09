package com.amap.api.location.core;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Encrypt */
public class C0190e {
    private static final char[] f113a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static byte[] m116a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = null;
        try {
            Key secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(1, secretKeySpec);
            bArr3 = instance.doFinal(bArr2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return bArr3;
    }

    static byte[] m115a(byte[] bArr, Key key) throws Exception {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    static PublicKey m113a(Context context) throws Exception {
        try {
            InputStream open = context.getAssets().open("location_public_key.der");
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            KeyFactory instance2 = KeyFactory.getInstance("RSA");
            Certificate generateCertificate = instance.generateCertificate(open);
            open.close();
            return instance2.generatePublic(new X509EncodedKeySpec(generateCertificate.getPublicKey().getEncoded()));
        } catch (IOException e) {
            return null;
        } catch (CertificateException e2) {
            return null;
        } catch (NoSuchAlgorithmException e3) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e4) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e5) {
            throw new Exception("公钥数据为空");
        }
    }

    static String m110a(String str) {
        String str2 = null;
        if (str != null) {
            try {
                if (str.length() != 0) {
                    str2 = C0190e.m117b("MD5", C0190e.m117b("SHA1", str) + str);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return str2;
    }

    private static String m117b(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(str2.getBytes("utf-8"));
            return C0190e.m112a(instance.digest());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static String m112a(byte[] bArr) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(f113a[(bArr[i] >> 4) & 15]);
            stringBuilder.append(f113a[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    static String m111a(String str, String str2) {
        byte[] b;
        try {
            b = C0190e.m118b(str);
        } catch (Exception e) {
            e.printStackTrace();
            b = null;
        }
        byte[] a = C0190e.m114a(b, str2);
        if (a == null) {
            return null;
        }
        try {
            return new String(a, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static byte[] m118b(String str) {
        int i = 0;
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        String toLowerCase = str.toLowerCase();
        int length = toLowerCase.length() / 2;
        byte[] bArr = new byte[length];
        while (i < length) {
            bArr[i] = (byte) (Integer.parseInt(toLowerCase.substring(i * 2, (i * 2) + 2), 16) & 255);
            i++;
        }
        return bArr;
    }

    public static byte[] m114a(byte[] bArr, String str) {
        try {
            Key c = C0190e.m119c(str);
            Cipher instance = Cipher.getInstance("AES/ECB/ZeroBytePadding");
            instance.init(2, c);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SecretKeySpec m119c(String str) {
        byte[] bArr = null;
        if (str == null) {
            str = "";
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(16);
        }
        try {
            bArr = stringBuffer.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(bArr, "AES");
    }
}
