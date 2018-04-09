package com.aps;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.zip.GZIPInputStream;

public final class bc {
    private RandomAccessFile f1801a;
    private af f1802b;
    private File f1803c = null;

    protected bc(af afVar) {
        this.f1802b = afVar;
    }

    private static byte m1841a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] bArr3 = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = gZIPInputStream.read(bArr3, 0, bArr3.length);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr3, 0, read);
            }
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            gZIPInputStream.close();
            byteArrayInputStream.close();
        } catch (Exception e) {
        }
        return bArr2[0];
    }

    private static int m1842a(int i, int i2, int i3) {
        int i4 = ((i3 - 1) * 1500) + i;
        while (i4 >= i2) {
            i4 -= 1500;
        }
        return i4;
    }

    private int m1843a(BitSet bitSet) {
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                return this.f1802b.m1747a() + ((i * 1500) + 4);
            }
        }
        return 0;
    }

    private ArrayList m1844a(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        while (i <= i2) {
            try {
                this.f1801a.seek((long) i);
                int readInt = this.f1801a.readInt();
                this.f1801a.readLong();
                if (readInt <= 0) {
                    readInt = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                }
                byte[] bArr = new byte[readInt];
                this.f1801a.read(bArr);
                byte a = m1841a(bArr);
                if (a == (byte) 3 || a == (byte) 4) {
                    arrayList.add(bArr);
                    i += 1500;
                } else {
                    throw new OutOfMemoryError();
                }
            } catch (IOException e) {
            }
        }
        return arrayList;
    }

    private BitSet m1845b() {
        BitSet bitSet = null;
        byte[] bArr = new byte[this.f1802b.m1747a()];
        try {
            this.f1801a.read(bArr);
            bitSet = af.m1740b(bArr);
        } catch (IOException e) {
        }
        return bitSet;
    }

    protected final int m1846a() {
        int i = 0;
        synchronized (this) {
            this.f1803c = this.f1802b.m1750b();
            try {
                if (this.f1803c != null) {
                    this.f1801a = new RandomAccessFile(this.f1802b.m1750b(), "rw");
                    byte[] bArr = new byte[this.f1802b.m1747a()];
                    this.f1801a.read(bArr);
                    BitSet b = af.m1740b(bArr);
                    for (int i2 = 0; i2 < b.size(); i2++) {
                        if (b.get(i2)) {
                            i++;
                        }
                    }
                }
                if (this.f1801a != null) {
                    try {
                        this.f1801a.close();
                    } catch (IOException e) {
                    }
                }
            } catch (FileNotFoundException e2) {
                if (this.f1801a != null) {
                    try {
                        this.f1801a.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (IOException e4) {
                if (this.f1801a != null) {
                    try {
                        this.f1801a.close();
                    } catch (IOException e5) {
                    }
                }
            } catch (NullPointerException e6) {
                if (this.f1801a != null) {
                    try {
                        this.f1801a.close();
                    } catch (IOException e7) {
                    }
                }
            } catch (Throwable th) {
                if (this.f1801a != null) {
                    try {
                        this.f1801a.close();
                    } catch (IOException e8) {
                    }
                }
            }
            this.f1803c = null;
        }
        return i;
    }

    protected final synchronized ae m1847a(int i) {
        ae aeVar = null;
        synchronized (this) {
            if (this.f1802b != null) {
                synchronized (this) {
                    this.f1803c = this.f1802b.m1750b();
                    if (this.f1803c == null) {
                    } else {
                        ae aeVar2;
                        try {
                            this.f1801a = new RandomAccessFile(this.f1803c, "rw");
                            BitSet b = m1845b();
                            if (b == null) {
                                this.f1803c.delete();
                                if (this.f1801a != null) {
                                    try {
                                        this.f1801a.close();
                                    } catch (IOException e) {
                                    }
                                }
                            } else {
                                int a = m1843a(b);
                                aeVar2 = new ae(this.f1803c, m1844a(a, m1842a(a, (int) this.f1803c.length(), i)), new int[]{((a - this.f1802b.m1747a()) - 4) / 1500, ((r2 - this.f1802b.m1747a()) - 4) / 1500});
                                if (this.f1801a != null) {
                                    try {
                                        this.f1801a.close();
                                    } catch (IOException e2) {
                                    }
                                }
                                if (aeVar2.m1732c() > 100 || aeVar2.m1732c() >= 5242880) {
                                    this.f1803c.delete();
                                    this.f1803c = null;
                                } else {
                                    aeVar = aeVar2;
                                }
                            }
                        } catch (FileNotFoundException e3) {
                            if (this.f1801a != null) {
                                try {
                                    this.f1801a.close();
                                    aeVar2 = null;
                                } catch (IOException e4) {
                                    aeVar2 = null;
                                    if (aeVar2.m1732c() > 100) {
                                    }
                                    this.f1803c.delete();
                                    this.f1803c = null;
                                    return aeVar;
                                }
                            }
                            aeVar2 = null;
                        } catch (OutOfMemoryError e5) {
                            if (this.f1801a != null) {
                                try {
                                    this.f1801a.close();
                                } catch (IOException e6) {
                                }
                            }
                            this.f1803c.delete();
                            this.f1803c = null;
                            aeVar2 = null;
                        } catch (Throwable th) {
                            if (this.f1801a != null) {
                                try {
                                    this.f1801a.close();
                                } catch (IOException e7) {
                                }
                            }
                        }
                    }
                }
            }
        }
        return aeVar;
    }

    protected final synchronized void m1848a(ae aeVar) {
        BitSet bitSet = null;
        synchronized (this) {
            synchronized (this) {
                this.f1803c = aeVar.f1706a;
                if (this.f1803c == null) {
                } else {
                    try {
                        this.f1801a = new RandomAccessFile(this.f1803c, "rw");
                        byte[] bArr = new byte[this.f1802b.m1747a()];
                        this.f1801a.read(bArr);
                        bitSet = af.m1740b(bArr);
                        if (aeVar.m1731b()) {
                            for (int i = aeVar.f1707b[0]; i <= aeVar.f1707b[1]; i++) {
                                bitSet.set(i, false);
                            }
                            this.f1801a.seek(0);
                            this.f1801a.write(af.m1737a(bitSet));
                        }
                        if (this.f1801a != null) {
                            try {
                                this.f1801a.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (FileNotFoundException e2) {
                        if (this.f1801a != null) {
                            try {
                                this.f1801a.close();
                            } catch (IOException e3) {
                            }
                        }
                    } catch (IOException e4) {
                        if (this.f1801a != null) {
                            try {
                                this.f1801a.close();
                            } catch (IOException e5) {
                            }
                        }
                    } catch (Throwable th) {
                        if (this.f1801a != null) {
                            try {
                                this.f1801a.close();
                            } catch (IOException e6) {
                            }
                        }
                    }
                    if (bitSet.isEmpty()) {
                        this.f1803c.delete();
                    }
                    this.f1803c = null;
                }
            }
        }
        return;
    }
}
