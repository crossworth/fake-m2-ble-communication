package com.aps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;

public final class bd {
    private RandomAccessFile f1804a;
    private af f1805b;
    private String f1806c = "";
    private File f1807d = null;

    protected bd(af afVar) {
        this.f1805b = afVar;
    }

    protected final synchronized void m1849a(long j, byte[] bArr) {
        int i = 0;
        synchronized (this) {
            this.f1807d = this.f1805b.m1748a(j);
            if (this.f1807d != null) {
                try {
                    this.f1804a = new RandomAccessFile(this.f1807d, "rw");
                    byte[] bArr2 = new byte[this.f1805b.m1747a()];
                    int readInt = this.f1804a.read(bArr2) == -1 ? 0 : this.f1804a.readInt();
                    BitSet b = af.m1740b(bArr2);
                    int a = (this.f1805b.m1747a() + 4) + (readInt * 1500);
                    if (readInt < 0 || readInt > (this.f1805b.m1747a() << 3)) {
                        this.f1804a.close();
                        this.f1807d.delete();
                        if (this.f1804a != null) {
                            try {
                                this.f1804a.close();
                            } catch (IOException e) {
                            }
                        }
                    } else {
                        this.f1804a.seek((long) a);
                        byte[] a2 = af.m1738a(bArr);
                        this.f1804a.writeInt(a2.length);
                        this.f1804a.writeLong(j);
                        this.f1804a.write(a2);
                        b.set(readInt, true);
                        this.f1804a.seek(0);
                        this.f1804a.write(af.m1737a(b));
                        readInt++;
                        if (readInt != (this.f1805b.m1747a() << 3)) {
                            i = readInt;
                        }
                        this.f1804a.writeInt(i);
                        if (!this.f1806c.equalsIgnoreCase(this.f1807d.getName())) {
                            this.f1806c = this.f1807d.getName();
                        }
                        this.f1807d.length();
                        if (this.f1804a != null) {
                            try {
                                this.f1804a.close();
                            } catch (IOException e2) {
                            }
                        }
                        this.f1807d = null;
                    }
                } catch (FileNotFoundException e3) {
                    if (this.f1804a != null) {
                        try {
                            this.f1804a.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (IOException e5) {
                    if (this.f1804a != null) {
                        try {
                            this.f1804a.close();
                        } catch (IOException e6) {
                        }
                    }
                } catch (Throwable th) {
                    if (this.f1804a != null) {
                        try {
                            this.f1804a.close();
                        } catch (IOException e7) {
                        }
                    }
                }
            }
        }
        return;
    }
}
