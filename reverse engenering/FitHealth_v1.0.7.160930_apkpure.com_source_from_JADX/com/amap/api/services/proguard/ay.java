package com.amap.api.services.proguard;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5 */
public class ay {
    public static String m1279a(String str) {
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
                    str2 = bb.m1326d(instance.digest());
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th4) {
                            be.m1340a(th4, "MD5", "getMd5FromFile");
                        }
                    }
                } else if (str2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Throwable th42) {
                        be.m1340a(th42, "MD5", "getMd5FromFile");
                    }
                }
            } else if (str2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Throwable th422) {
                    be.m1340a(th422, "MD5", "getMd5FromFile");
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

    public static String m1282b(String str) {
        if (str == null) {
            return null;
        }
        return bb.m1326d(m1285d(str));
    }

    public static String m1280a(byte[] bArr) {
        return bb.m1326d(m1283b(bArr));
    }

    public static String m1284c(String str) {
        return bb.m1327e(m1286e(str));
    }

    public static byte[] m1281a(byte[] bArr, String str) {
        byte[] bArr2 = null;
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            bArr2 = instance.digest();
        } catch (Throwable th) {
            be.m1340a(th, "MD5", "getMd5Bytes1");
        }
        return bArr2;
    }

    private static byte[] m1283b(byte[] bArr) {
        return m1281a(bArr, "MD5");
    }

    public static byte[] m1285d(String str) {
        try {
            return m1287f(str);
        } catch (Throwable th) {
            be.m1340a(th, "MD5", "getMd5Bytes");
            return new byte[0];
        }
    }

    private static byte[] m1286e(String str) {
        try {
            return m1287f(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return new byte[0];
        }
    }

    private static byte[] m1287f(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(bb.m1321a(str));
        return instance.digest();
    }
}
