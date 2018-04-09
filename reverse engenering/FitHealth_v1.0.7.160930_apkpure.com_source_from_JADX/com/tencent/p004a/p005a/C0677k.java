package com.tencent.p004a.p005a;

import java.io.File;
import java.util.Comparator;

/* compiled from: ProGuard */
class C0677k implements Comparator<File> {
    final /* synthetic */ C0674h f2347a;

    C0677k(C0674h c0674h) {
        this.f2347a = c0674h;
    }

    public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        return m2289a((File) obj, (File) obj2);
    }

    public int m2289a(File file, File file2) {
        return C0674h.m2265f(file) - C0674h.m2265f(file2);
    }
}
