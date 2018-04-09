package com.baidu.location.p006h;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class C0460c {
    static C0460c f844c;
    String f845a = "firll.dat";
    int f846b = 3164;
    int f847d = 0;
    int f848e = 20;
    int f849f = 40;
    int f850g = 60;
    int f851h = 80;
    int f852i = 100;

    private long m988a(int i) {
        Throwable th;
        String g = C0468j.m1029g();
        if (g == null) {
            return -1;
        }
        String str = g + File.separator + this.f845a;
        RandomAccessFile randomAccessFile = null;
        RandomAccessFile randomAccessFile2;
        try {
            randomAccessFile2 = new RandomAccessFile(str, "rw");
            try {
                randomAccessFile2.seek((long) i);
                int readInt = randomAccessFile2.readInt();
                long readLong = randomAccessFile2.readLong();
                if (readInt == randomAccessFile2.readInt()) {
                    if (randomAccessFile2 != null) {
                        try {
                            randomAccessFile2.close();
                        } catch (IOException e) {
                        }
                    }
                    return readLong;
                } else if (randomAccessFile2 == null) {
                    return -1;
                } else {
                    try {
                        randomAccessFile2.close();
                        return -1;
                    } catch (IOException e2) {
                        return -1;
                    }
                }
            } catch (Exception e3) {
                randomAccessFile = randomAccessFile2;
                if (randomAccessFile != null) {
                    return -1;
                }
                try {
                    randomAccessFile.close();
                    return -1;
                } catch (IOException e4) {
                    return -1;
                }
            } catch (Throwable th2) {
                th = th2;
                if (randomAccessFile2 != null) {
                    try {
                        randomAccessFile2.close();
                    } catch (IOException e5) {
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            if (randomAccessFile != null) {
                return -1;
            }
            randomAccessFile.close();
            return -1;
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile2 = null;
            if (randomAccessFile2 != null) {
                randomAccessFile2.close();
            }
            throw th;
        }
    }

    public static C0460c m989a() {
        if (f844c == null) {
            f844c = new C0460c();
        }
        return f844c;
    }

    private void m990a(int i, long j) {
        String g = C0468j.m1029g();
        if (g != null) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(g + File.separator + this.f845a, "rw");
                randomAccessFile.seek((long) i);
                randomAccessFile.writeInt(this.f846b);
                randomAccessFile.writeLong(j);
                randomAccessFile.writeInt(this.f846b);
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }

    public void m991a(long j) {
        m990a(this.f847d, j);
    }

    public long m992b() {
        return m988a(this.f847d);
    }

    public void m993b(long j) {
        m990a(this.f848e, j);
    }

    public long m994c() {
        return m988a(this.f848e);
    }

    public void m995c(long j) {
        m990a(this.f850g, j);
    }

    public long m996d() {
        return m988a(this.f850g);
    }
}
