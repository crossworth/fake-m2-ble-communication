package com.tencent.p004a.p008b;

import android.os.StatFs;
import java.io.File;

/* compiled from: ProGuard */
public class C0682d {
    private File f2350a;
    private long f2351b;
    private long f2352c;

    public File m2297a() {
        return this.f2350a;
    }

    public void m2299a(File file) {
        this.f2350a = file;
    }

    public long m2300b() {
        return this.f2351b;
    }

    public void m2298a(long j) {
        this.f2351b = j;
    }

    public long m2302c() {
        return this.f2352c;
    }

    public void m2301b(long j) {
        this.f2352c = j;
    }

    public static C0682d m2296b(File file) {
        C0682d c0682d = new C0682d();
        c0682d.m2299a(file);
        StatFs statFs = new StatFs(file.getAbsolutePath());
        long blockSize = (long) statFs.getBlockSize();
        long availableBlocks = (long) statFs.getAvailableBlocks();
        c0682d.m2298a(((long) statFs.getBlockCount()) * blockSize);
        c0682d.m2301b(blockSize * availableBlocks);
        return c0682d;
    }

    public String toString() {
        return String.format("[%s : %d / %d]", new Object[]{m2297a().getAbsolutePath(), Long.valueOf(m2302c()), Long.valueOf(m2300b())});
    }
}
