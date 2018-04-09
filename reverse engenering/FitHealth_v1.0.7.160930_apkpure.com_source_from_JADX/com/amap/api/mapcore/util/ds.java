package com.amap.api.mapcore.util;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5 */
public class ds {
    public static String m678a(String str) {
        FileInputStream fileInputStream;
        Throwable th;
        Throwable th2;
        String str2 = null;
        FileInputStream fileInputStream2 = null;
        try {
            if (!TextUtils.isEmpty(str)) {
                File file = new File(str);
                if (file.isFile() && file.exists()) {
                    byte[] bArr = new byte[2048];
                    MessageDigest instance = MessageDigest.getInstance("MD5");
                    fileInputStream = new FileInputStream(file);
                    while (true) {
                        try {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            instance.update(bArr, 0, read);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                    str2 = dx.m728d(instance.digest());
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th4) {
                            eb.m742a(th4, "MD5", "getMd5FromFile");
                        }
                    }
                } else if (str2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Throwable th42) {
                        eb.m742a(th42, "MD5", "getMd5FromFile");
                    }
                }
            } else if (str2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Throwable th422) {
                    eb.m742a(th422, "MD5", "getMd5FromFile");
                }
            }
        } catch (Throwable th4222) {
            fileInputStream = str2;
            th2 = th4222;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th2;
        }
        return str2;
    }

    public static String m681b(String str) {
        if (str == null) {
            return null;
        }
        return dx.m728d(m684d(str));
    }

    public static String m679a(byte[] bArr) {
        return dx.m728d(m682b(bArr));
    }

    public static String m683c(String str) {
        return dx.m729e(m685e(str));
    }

    public static byte[] m680a(byte[] bArr, String str) {
        byte[] bArr2 = null;
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            bArr2 = instance.digest();
        } catch (Throwable th) {
            eb.m742a(th, "MD5", "getMd5Bytes1");
        }
        return bArr2;
    }

    private static byte[] m682b(byte[] bArr) {
        return m680a(bArr, "MD5");
    }

    public static byte[] m684d(String str) {
        try {
            return m686f(str);
        } catch (Throwable th) {
            eb.m742a(th, "MD5", "getMd5Bytes");
            return new byte[0];
        }
    }

    private static byte[] m685e(String str) {
        try {
            return m686f(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return new byte[0];
        }
    }

    private static byte[] m686f(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(dx.m721a(str));
        return instance.digest();
    }
}
