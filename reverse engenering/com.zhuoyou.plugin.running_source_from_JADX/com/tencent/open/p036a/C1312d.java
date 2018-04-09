package com.tencent.open.p036a;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import java.text.SimpleDateFormat;

/* compiled from: ProGuard */
public class C1312d {

    /* compiled from: ProGuard */
    public static final class C1308a {
        public static final boolean m3851a(int i, int i2) {
            return i2 == (i & i2);
        }
    }

    /* compiled from: ProGuard */
    public static final class C1309b {
        public static boolean m3852a() {
            String externalStorageState = Environment.getExternalStorageState();
            return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
        }

        public static C1310c m3853b() {
            if (C1309b.m3852a()) {
                return C1310c.m3854b(Environment.getExternalStorageDirectory());
            }
            return null;
        }
    }

    /* compiled from: ProGuard */
    public static class C1310c {
        private File f4120a;
        private long f4121b;
        private long f4122c;

        public File m3855a() {
            return this.f4120a;
        }

        public void m3857a(File file) {
            this.f4120a = file;
        }

        public long m3858b() {
            return this.f4121b;
        }

        public void m3856a(long j) {
            this.f4121b = j;
        }

        public long m3860c() {
            return this.f4122c;
        }

        public void m3859b(long j) {
            this.f4122c = j;
        }

        public static C1310c m3854b(File file) {
            C1310c c1310c = new C1310c();
            c1310c.m3857a(file);
            StatFs statFs = new StatFs(file.getAbsolutePath());
            long blockSize = (long) statFs.getBlockSize();
            long availableBlocks = (long) statFs.getAvailableBlocks();
            c1310c.m3856a(((long) statFs.getBlockCount()) * blockSize);
            c1310c.m3859b(blockSize * availableBlocks);
            return c1310c;
        }

        public String toString() {
            return String.format("[%s : %d / %d]", new Object[]{m3855a().getAbsolutePath(), Long.valueOf(m3860c()), Long.valueOf(m3858b())});
        }
    }

    /* compiled from: ProGuard */
    public static final class C1311d {
        @SuppressLint({"SimpleDateFormat"})
        public static SimpleDateFormat m3861a(String str) {
            return new SimpleDateFormat(str);
        }
    }
}
