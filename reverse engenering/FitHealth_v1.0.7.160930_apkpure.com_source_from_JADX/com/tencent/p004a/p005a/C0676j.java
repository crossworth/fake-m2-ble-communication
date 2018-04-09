package com.tencent.p004a.p005a;

import java.io.File;
import java.io.FileFilter;

/* compiled from: ProGuard */
class C0676j implements FileFilter {
    final /* synthetic */ C0674h f2346a;

    C0676j(C0674h c0674h) {
        this.f2346a = c0674h;
    }

    public boolean accept(File file) {
        if (file.getName().endsWith(this.f2346a.m2287j()) && C0674h.m2265f(file) != -1) {
            return true;
        }
        return false;
    }
}
