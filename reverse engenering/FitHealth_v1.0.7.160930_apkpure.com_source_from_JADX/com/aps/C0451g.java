package com.aps;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* compiled from: DiskLruCache */
public final class C0451g implements Closeable {
    static final Pattern f1877a = Pattern.compile("[a-z0-9_-]{1,120}");
    private static final OutputStream f1878p = new C0453i();
    final ThreadPoolExecutor f1879b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final File f1880c;
    private final File f1881d;
    private final File f1882e;
    private final File f1883f;
    private final int f1884g;
    private long f1885h;
    private final int f1886i;
    private long f1887j = 0;
    private Writer f1888k;
    private final LinkedHashMap<String, C0449b> f1889l = new LinkedHashMap(0, 0.75f, true);
    private int f1890m;
    private long f1891n = 0;
    private final Callable<Void> f1892o = new C0452h(this);

    /* compiled from: DiskLruCache */
    public final class C0448a {
        final /* synthetic */ C0451g f1861a;
        private final C0449b f1862b;
        private final boolean[] f1863c;
        private boolean f1864d;
        private boolean f1865e;

        /* compiled from: DiskLruCache */
        private class C0447a extends FilterOutputStream {
            final /* synthetic */ C0448a f1860a;

            private C0447a(C0448a c0448a, OutputStream outputStream) {
                this.f1860a = c0448a;
                super(outputStream);
            }

            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException e) {
                    this.f1860a.f1864d = true;
                }
            }

            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException e) {
                    this.f1860a.f1864d = true;
                }
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    this.f1860a.f1864d = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    this.f1860a.f1864d = true;
                }
            }
        }

        private C0448a(C0451g c0451g, C0449b c0449b) {
            this.f1861a = c0451g;
            this.f1862b = c0449b;
            this.f1863c = c0449b.f1869d ? null : new boolean[c0451g.f1886i];
        }

        public OutputStream m1908a(int i) throws IOException {
            if (i < 0 || i >= this.f1861a.f1886i) {
                throw new IllegalArgumentException("Expected index " + i + " to " + "be greater than 0 and less than the maximum value count " + "of " + this.f1861a.f1886i);
            }
            OutputStream b;
            synchronized (this.f1861a) {
                OutputStream fileOutputStream;
                if (this.f1862b.f1870e != this) {
                    throw new IllegalStateException();
                }
                if (!this.f1862b.f1869d) {
                    this.f1863c[i] = true;
                }
                r1 = this.f1862b.m1924b(i);
                try {
                    fileOutputStream = new FileOutputStream(r1);
                } catch (FileNotFoundException e) {
                    this.f1861a.f1880c.mkdirs();
                    try {
                        File b2;
                        fileOutputStream = new FileOutputStream(b2);
                    } catch (FileNotFoundException e2) {
                        b = C0451g.f1878p;
                    }
                }
                b = new C0447a(fileOutputStream);
            }
            return b;
        }

        public void m1909a() throws IOException {
            if (this.f1864d) {
                this.f1861a.m1930a(this, false);
                this.f1861a.m1951c(this.f1862b.f1867b);
            } else {
                this.f1861a.m1930a(this, true);
            }
            this.f1865e = true;
        }

        public void m1910b() throws IOException {
            this.f1861a.m1930a(this, false);
        }
    }

    /* compiled from: DiskLruCache */
    private final class C0449b {
        final /* synthetic */ C0451g f1866a;
        private final String f1867b;
        private final long[] f1868c;
        private boolean f1869d;
        private C0448a f1870e;
        private long f1871f;

        private C0449b(C0451g c0451g, String str) {
            this.f1866a = c0451g;
            this.f1867b = str;
            this.f1868c = new long[c0451g.f1886i];
        }

        public String m1923a() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long append : this.f1868c) {
                stringBuilder.append(' ').append(append);
            }
            return stringBuilder.toString();
        }

        private void m1915a(String[] strArr) throws IOException {
            if (strArr.length != this.f1866a.f1886i) {
                throw m1917b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.f1868c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw m1917b(strArr);
                }
            }
        }

        private IOException m1917b(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        public File m1922a(int i) {
            return new File(this.f1866a.f1880c, this.f1867b + "." + i);
        }

        public File m1924b(int i) {
            return new File(this.f1866a.f1880c, this.f1867b + "." + i + ".tmp");
        }
    }

    /* compiled from: DiskLruCache */
    public final class C0450c implements Closeable {
        final /* synthetic */ C0451g f1872a;
        private final String f1873b;
        private final long f1874c;
        private final InputStream[] f1875d;
        private final long[] f1876e;

        private C0450c(C0451g c0451g, String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.f1872a = c0451g;
            this.f1873b = str;
            this.f1874c = j;
            this.f1875d = inputStreamArr;
            this.f1876e = jArr;
        }

        public InputStream m1925a(int i) {
            return this.f1875d[i];
        }

        public void close() {
            for (Closeable a : this.f1875d) {
                C0469s.m2001a(a);
            }
        }
    }

    private C0451g(File file, int i, int i2, long j) {
        this.f1880c = file;
        this.f1884g = i;
        this.f1881d = new File(file, "journal");
        this.f1882e = new File(file, "journal.tmp");
        this.f1883f = new File(file, "journal.bkp");
        this.f1886i = i2;
        this.f1885h = j;
    }

    public static C0451g m1928a(File file, int i, int i2, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            File file2 = new File(file, "journal.bkp");
            if (file2.exists()) {
                File file3 = new File(file, "journal");
                if (file3.exists()) {
                    file2.delete();
                } else {
                    C0451g.m1933a(file2, file3, false);
                }
            }
            C0451g c0451g = new C0451g(file, i, i2, j);
            if (c0451g.f1881d.exists()) {
                try {
                    c0451g.m1936c();
                    c0451g.m1938d();
                    c0451g.f1888k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(c0451g.f1881d, true), C0469s.f1943a));
                    return c0451g;
                } catch (IOException e) {
                    System.out.println("DiskLruCache " + file + " is corrupt: " + e.getMessage() + ", removing");
                    c0451g.m1949a();
                }
            }
            file.mkdirs();
            c0451g = new C0451g(file, i, i2, j);
            c0451g.m1942e();
            return c0451g;
        }
    }

    private void m1936c() throws IOException {
        int i;
        Closeable c0468r = new C0468r(new FileInputStream(this.f1881d), C0469s.f1943a);
        try {
            String a = c0468r.m2000a();
            String a2 = c0468r.m2000a();
            String a3 = c0468r.m2000a();
            String a4 = c0468r.m2000a();
            String a5 = c0468r.m2000a();
            if ("libcore.io.DiskLruCache".equals(a) && "1".equals(a2) && Integer.toString(this.f1884g).equals(a3) && Integer.toString(this.f1886i).equals(a4) && "".equals(a5)) {
                i = 0;
                while (true) {
                    m1940d(c0468r.m2000a());
                    i++;
                }
            } else {
                throw new IOException("unexpected journal header: [" + a + ", " + a2 + ", " + a4 + ", " + a5 + "]");
            }
        } catch (EOFException e) {
            this.f1890m = i - this.f1889l.size();
            C0469s.m2001a(c0468r);
        } catch (Throwable th) {
            C0469s.m2001a(c0468r);
        }
    }

    private void m1940d(String str) throws IOException {
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            throw new IOException("unexpected journal line: " + str);
        }
        String str2;
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(32, i);
        if (indexOf2 == -1) {
            String substring = str.substring(i);
            if (indexOf == "REMOVE".length() && str.startsWith("REMOVE")) {
                this.f1889l.remove(substring);
                return;
            }
            str2 = substring;
        } else {
            str2 = str.substring(i, indexOf2);
        }
        C0449b c0449b = (C0449b) this.f1889l.get(str2);
        if (c0449b == null) {
            c0449b = new C0449b(str2);
            this.f1889l.put(str2, c0449b);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(" ");
            c0449b.f1869d = true;
            c0449b.f1870e = null;
            c0449b.m1915a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            c0449b.f1870e = new C0448a(c0449b);
        } else if (indexOf2 != -1 || indexOf != "READ".length() || !str.startsWith("READ")) {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void m1938d() throws IOException {
        C0451g.m1932a(this.f1882e);
        Iterator it = this.f1889l.values().iterator();
        while (it.hasNext()) {
            C0449b c0449b = (C0449b) it.next();
            int i;
            if (c0449b.f1870e == null) {
                for (i = 0; i < this.f1886i; i++) {
                    this.f1887j += c0449b.f1868c[i];
                }
            } else {
                c0449b.f1870e = null;
                for (i = 0; i < this.f1886i; i++) {
                    C0451g.m1932a(c0449b.m1922a(i));
                    C0451g.m1932a(c0449b.m1924b(i));
                }
                it.remove();
            }
        }
    }

    private synchronized void m1942e() throws IOException {
        if (this.f1888k != null) {
            this.f1888k.close();
        }
        Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f1882e), C0469s.f1943a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f1884g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f1886i));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (C0449b c0449b : this.f1889l.values()) {
                if (c0449b.f1870e != null) {
                    bufferedWriter.write("DIRTY " + c0449b.f1867b + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + c0449b.f1867b + c0449b.m1923a() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.f1881d.exists()) {
                C0451g.m1933a(this.f1881d, this.f1883f, true);
            }
            C0451g.m1933a(this.f1882e, this.f1881d, false);
            this.f1883f.delete();
            this.f1888k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f1881d, true), C0469s.f1943a));
        } catch (Throwable th) {
            bufferedWriter.close();
        }
    }

    private static void m1932a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void m1933a(File file, File file2, boolean z) throws IOException {
        if (z) {
            C0451g.m1932a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    public synchronized C0450c m1948a(String str) throws IOException {
        int i;
        C0450c c0450c = null;
        synchronized (this) {
            m1946g();
            m1943e(str);
            C0449b c0449b = (C0449b) this.f1889l.get(str);
            if (c0449b != null) {
                if (c0449b.f1869d) {
                    r6 = new InputStream[this.f1886i];
                    int i2 = 0;
                    while (i2 < this.f1886i) {
                        try {
                            r6[i2] = new FileInputStream(c0449b.m1922a(i2));
                            i2++;
                        } catch (FileNotFoundException e) {
                            i = 0;
                            while (i < this.f1886i && r6[i] != null) {
                                InputStream[] inputStreamArr;
                                C0469s.m2001a(inputStreamArr[i]);
                                i++;
                            }
                        }
                    }
                    this.f1890m++;
                    this.f1888k.append("READ " + str + '\n');
                    if (m1945f()) {
                        this.f1879b.submit(this.f1892o);
                    }
                    c0450c = new C0450c(str, c0449b.f1871f, inputStreamArr, c0449b.f1868c);
                }
            }
        }
        return c0450c;
    }

    public C0448a m1950b(String str) throws IOException {
        return m1927a(str, -1);
    }

    private synchronized C0448a m1927a(String str, long j) throws IOException {
        C0448a c0448a;
        m1946g();
        m1943e(str);
        C0449b c0449b = (C0449b) this.f1889l.get(str);
        if (j == -1 || (c0449b != null && c0449b.f1871f == j)) {
            C0449b c0449b2;
            if (c0449b == null) {
                c0449b = new C0449b(str);
                this.f1889l.put(str, c0449b);
                c0449b2 = c0449b;
            } else if (c0449b.f1870e != null) {
                c0448a = null;
            } else {
                c0449b2 = c0449b;
            }
            c0448a = new C0448a(c0449b2);
            c0449b2.f1870e = c0448a;
            this.f1888k.write("DIRTY " + str + '\n');
            this.f1888k.flush();
        } else {
            c0448a = null;
        }
        return c0448a;
    }

    private synchronized void m1930a(C0448a c0448a, boolean z) throws IOException {
        int i = 0;
        synchronized (this) {
            C0449b a = c0448a.f1862b;
            if (a.f1870e != c0448a) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.f1869d) {
                    int i2 = 0;
                    while (i2 < this.f1886i) {
                        if (!c0448a.f1863c[i2]) {
                            c0448a.m1910b();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!a.m1924b(i2).exists()) {
                            c0448a.m1910b();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.f1886i) {
                File b = a.m1924b(i);
                if (!z) {
                    C0451g.m1932a(b);
                } else if (b.exists()) {
                    File a2 = a.m1922a(i);
                    b.renameTo(a2);
                    long j = a.f1868c[i];
                    long length = a2.length();
                    a.f1868c[i] = length;
                    this.f1887j = (this.f1887j - j) + length;
                }
                i++;
            }
            this.f1890m++;
            a.f1870e = null;
            if ((a.f1869d | z) != 0) {
                a.f1869d = true;
                this.f1888k.write("CLEAN " + a.f1867b + a.m1923a() + '\n');
                if (z) {
                    long j2 = this.f1891n;
                    this.f1891n = 1 + j2;
                    a.f1871f = j2;
                }
            } else {
                this.f1889l.remove(a.f1867b);
                this.f1888k.write("REMOVE " + a.f1867b + '\n');
            }
            this.f1888k.flush();
            if (this.f1887j > this.f1885h || m1945f()) {
                this.f1879b.submit(this.f1892o);
            }
        }
    }

    private boolean m1945f() {
        return this.f1890m >= 2000 && this.f1890m >= this.f1889l.size();
    }

    public synchronized boolean m1951c(String str) throws IOException {
        boolean z;
        int i = 0;
        synchronized (this) {
            m1946g();
            m1943e(str);
            C0449b c0449b = (C0449b) this.f1889l.get(str);
            if (c0449b == null || c0449b.f1870e != null) {
                z = false;
            } else {
                while (i < this.f1886i) {
                    File a = c0449b.m1922a(i);
                    if (!a.exists() || a.delete()) {
                        this.f1887j -= c0449b.f1868c[i];
                        c0449b.f1868c[i] = 0;
                        i++;
                    } else {
                        throw new IOException("failed to delete " + a);
                    }
                }
                this.f1890m++;
                this.f1888k.append("REMOVE " + str + '\n');
                this.f1889l.remove(str);
                if (m1945f()) {
                    this.f1879b.submit(this.f1892o);
                }
                z = true;
            }
        }
        return z;
    }

    private void m1946g() {
        if (this.f1888k == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void close() throws IOException {
        if (this.f1888k != null) {
            Iterator it = new ArrayList(this.f1889l.values()).iterator();
            while (it.hasNext()) {
                C0449b c0449b = (C0449b) it.next();
                if (c0449b.f1870e != null) {
                    c0449b.f1870e.m1910b();
                }
            }
            m1947h();
            this.f1888k.close();
            this.f1888k = null;
        }
    }

    private void m1947h() throws IOException {
        while (this.f1887j > this.f1885h) {
            m1951c((String) ((Entry) this.f1889l.entrySet().iterator().next()).getKey());
        }
    }

    public void m1949a() throws IOException {
        close();
        C0469s.m2002a(this.f1880c);
    }

    private void m1943e(String str) {
        if (!f1877a.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
    }
}
