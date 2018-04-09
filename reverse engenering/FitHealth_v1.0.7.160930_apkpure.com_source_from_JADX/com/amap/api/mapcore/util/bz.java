package com.amap.api.mapcore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: FileCopy */
public class bz {

    /* compiled from: FileCopy */
    public interface C0210a {
        void mo1469a(String str, String str2);

        void mo1470a(String str, String str2, float f);

        void mo1471a(String str, String str2, int i);

        void mo1472b(String str, String str2);
    }

    public long m389a(File file, File file2, long j, long j2, C0210a c0210a) {
        Exception e;
        if (j == 0) {
            System.err.println("sizeOfDirectory is the total Size,  must be a positive number");
            if (c0210a != null) {
                c0210a.mo1471a("", "", -1);
            }
            return 0;
        }
        long j3;
        String absolutePath = file.getAbsolutePath();
        String absolutePath2 = file2.getAbsolutePath();
        try {
            if (!file.isDirectory()) {
                File parentFile = file2.getParentFile();
                if (parentFile == null || parentFile.exists() || parentFile.mkdirs()) {
                    if (c0210a != null && j <= 0) {
                        c0210a.mo1469a(absolutePath, absolutePath2);
                    }
                    InputStream fileInputStream = new FileInputStream(file);
                    OutputStream fileOutputStream = new FileOutputStream(file2);
                    byte[] bArr = new byte[1024];
                    j3 = j;
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                        j = j3 + ((long) read);
                        if (c0210a != null) {
                            c0210a.mo1470a(absolutePath, absolutePath2, m388a(j, j2));
                            j3 = j;
                        } else {
                            j3 = j;
                        }
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    if (c0210a == null || j3 < j2 - 1) {
                        return j3;
                    }
                    c0210a.mo1472b(absolutePath, absolutePath2);
                    return j3;
                }
                throw new IOException("Cannot create dir " + parentFile.getAbsolutePath());
            } else if (file2.exists() || file2.mkdirs()) {
                String[] list = file.list();
                if (list == null) {
                    return j;
                }
                int i = 0;
                j3 = j;
                while (i < list.length) {
                    try {
                        j3 = m389a(new File(file, list[i]), new File(file2, list[i]), j3, j2, c0210a);
                        i++;
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                return j3;
            } else {
                throw new IOException("Cannot create dir " + file2.getAbsolutePath());
            }
        } catch (Exception e3) {
            e = e3;
            j3 = j;
        }
        e.printStackTrace();
        if (c0210a == null) {
            return j3;
        }
        c0210a.mo1471a(absolutePath, absolutePath2, -1);
        return j3;
    }

    private float m388a(long j, long j2) {
        return (((float) j) / ((float) j2)) * 100.0f;
    }
}
