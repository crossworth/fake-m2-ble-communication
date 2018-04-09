package com.tencent.p004a.p005a;

import java.io.File;
import java.io.FileFilter;

/* compiled from: ProGuard */
class C0675i implements FileFilter {
    C0675i() {
    }

    public boolean accept(File file) {
        if (file.isDirectory() && C0674h.m2261a(file) > 0) {
            return true;
        }
        return false;
    }
}
