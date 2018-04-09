package com.tencent.p004a.p009c;

import java.io.File;

/* compiled from: ProGuard */
public class C0683a {
    public static boolean m2303a(File file) {
        int i = 0;
        if (file == null) {
            return false;
        }
        if (file.isFile()) {
            if (file.delete()) {
                return true;
            }
            file.deleteOnExit();
            return false;
        } else if (!file.isDirectory()) {
            return false;
        } else {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            while (i < length) {
                C0683a.m2303a(listFiles[i]);
                i++;
            }
            return file.delete();
        }
    }
}
