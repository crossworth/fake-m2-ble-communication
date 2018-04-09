package com.amap.api.mapcore.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: FileAccessI */
class by {
    RandomAccessFile f305a;

    public by() throws IOException {
        this("", 0);
    }

    public by(String str, long j) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Throwable e) {
                ee.m4243a(e, "FileAccessI", "create");
                e.printStackTrace();
            }
        }
        this.f305a = new RandomAccessFile(str, "rw");
        this.f305a.seek(j);
    }

    public synchronized int m382a(byte[] bArr) throws IOException {
        this.f305a.write(bArr);
        return bArr.length;
    }

    public void m383a() {
        if (this.f305a != null) {
            try {
                this.f305a.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.f305a = null;
        }
    }
}
