package com.droi.sdk.selfupdate.util;

import com.droi.sdk.internal.DroiLog;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class C1051e {
    private static String m3298a(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[(bArr.length * 2)];
        char[] cArr2 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i + 1;
            cArr[i] = cArr2[(bArr[i2] >>> 4) & 15];
            i = i3 + 1;
            cArr[i3] = cArr2[bArr[i2] & 15];
        }
        return new String(cArr);
    }

    public static String m3296a(File file) {
        String a;
        Exception e;
        Throwable th;
        FileInputStream fileInputStream = null;
        FileInputStream fileInputStream2;
        try {
            fileInputStream2 = new FileInputStream(file);
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = fileInputStream2.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    instance.update(bArr, 0, read);
                }
                a = C1051e.m3298a(instance.digest());
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e2) {
                        DroiLog.m2869e("SignUtils", e2);
                    }
                }
            } catch (Exception e3) {
                fileInputStream = fileInputStream2;
                e2 = e3;
                try {
                    a = "";
                    DroiLog.m2869e("SignUtils", e2);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e22) {
                            DroiLog.m2869e("SignUtils", e22);
                        }
                    }
                    return a;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Exception e222) {
                            DroiLog.m2869e("SignUtils", e222);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                throw th;
            }
        } catch (Exception e32) {
            e222 = e32;
            a = "";
            DroiLog.m2869e("SignUtils", e222);
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return a;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream2 = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
        return a;
    }

    public static String m3297a(String str) {
        return C1051e.m3296a(new File(str));
    }
}
