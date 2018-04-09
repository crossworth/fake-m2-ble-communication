package com.baidu.location.p008c.p009a;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class C0388f {
    private byte[] f436a;
    private int f437b;

    public C0388f() {
        this(512);
    }

    private C0388f(int i) {
        this.f437b = i;
        this.f436a = new byte[this.f437b];
    }

    public void m506a(String str, String str2) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    String str3 = str2 + nextEntry.getName();
                    File file = new File(str3);
                    if (nextEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parentFile = file.getParentFile();
                        if (!(parentFile == null || parentFile.exists())) {
                            parentFile.mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(str3);
                        while (true) {
                            int read = zipInputStream.read(this.f436a);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(this.f436a, 0, read);
                        }
                        fileOutputStream.close();
                    }
                    zipInputStream.closeEntry();
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
