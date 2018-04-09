package com.amap.api.services.proguard;

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
public final class cm implements Closeable {
    static final Pattern f1488a = Pattern.compile("[a-z0-9_-]{1,120}");
    private static final OutputStream f1489q = new C03792();
    final ThreadPoolExecutor f1490b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final File f1491c;
    private final File f1492d;
    private final File f1493e;
    private final File f1494f;
    private final int f1495g;
    private long f1496h;
    private final int f1497i;
    private long f1498j = 0;
    private Writer f1499k;
    private final LinkedHashMap<String, C0383c> f1500l = new LinkedHashMap(0, 0.75f, true);
    private int f1501m;
    private cn f1502n;
    private long f1503o = 0;
    private final Callable<Void> f1504p = new C03781(this);

    /* compiled from: DiskLruCache */
    class C03781 implements Callable<Void> {
        final /* synthetic */ cm f1470a;

        C03781(cm cmVar) {
            this.f1470a = cmVar;
        }

        public /* synthetic */ Object call() throws Exception {
            return m1488a();
        }

        public Void m1488a() throws Exception {
            synchronized (this.f1470a) {
                if (this.f1470a.f1499k == null) {
                } else {
                    this.f1470a.m1531j();
                    if (this.f1470a.m1529h()) {
                        this.f1470a.m1528g();
                        this.f1470a.f1501m = 0;
                    }
                }
            }
            return null;
        }
    }

    /* compiled from: DiskLruCache */
    static class C03792 extends OutputStream {
        C03792() {
        }

        public void write(int i) throws IOException {
        }
    }

    /* compiled from: DiskLruCache */
    public final class C0381a {
        final /* synthetic */ cm f1472a;
        private final C0383c f1473b;
        private final boolean[] f1474c;
        private boolean f1475d;
        private boolean f1476e;

        /* compiled from: DiskLruCache */
        private class C0380a extends FilterOutputStream {
            final /* synthetic */ C0381a f1471a;

            private C0380a(C0381a c0381a, OutputStream outputStream) {
                this.f1471a = c0381a;
                super(outputStream);
            }

            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException e) {
                    this.f1471a.f1475d = true;
                }
            }

            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException e) {
                    this.f1471a.f1475d = true;
                }
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    this.f1471a.f1475d = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    this.f1471a.f1475d = true;
                }
            }
        }

        private C0381a(cm cmVar, C0383c c0383c) {
            this.f1472a = cmVar;
            this.f1473b = c0383c;
            this.f1474c = c0383c.f1485d ? null : new boolean[cmVar.f1497i];
        }

        public OutputStream m1492a(int i) throws IOException {
            if (i < 0 || i >= this.f1472a.f1497i) {
                throw new IllegalArgumentException("Expected index " + i + " to " + "be greater than 0 and less than the maximum value count " + "of " + this.f1472a.f1497i);
            }
            OutputStream d;
            synchronized (this.f1472a) {
                File b;
                OutputStream fileOutputStream;
                if (this.f1473b.f1486e != this) {
                    throw new IllegalStateException();
                }
                if (!this.f1473b.f1485d) {
                    this.f1474c[i] = true;
                }
                b = this.f1473b.m1509b(i);
                try {
                    fileOutputStream = new FileOutputStream(b);
                } catch (FileNotFoundException e) {
                    this.f1472a.f1491c.mkdirs();
                    try {
                        fileOutputStream = new FileOutputStream(b);
                    } catch (FileNotFoundException e2) {
                        d = cm.f1489q;
                    }
                }
                d = new C0380a(fileOutputStream);
            }
            return d;
        }

        public void m1493a() throws IOException {
            if (this.f1475d) {
                this.f1472a.m1514a(this, false);
                this.f1472a.m1538c(this.f1473b.f1483b);
            } else {
                this.f1472a.m1514a(this, true);
            }
            this.f1476e = true;
        }

        public void m1494b() throws IOException {
            this.f1472a.m1514a(this, false);
        }
    }

    /* compiled from: DiskLruCache */
    public final class C0382b implements Closeable {
        final /* synthetic */ cm f1477a;
        private final String f1478b;
        private final long f1479c;
        private final InputStream[] f1480d;
        private final long[] f1481e;

        private C0382b(cm cmVar, String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.f1477a = cmVar;
            this.f1478b = str;
            this.f1479c = j;
            this.f1480d = inputStreamArr;
            this.f1481e = jArr;
        }

        public InputStream m1495a(int i) {
            return this.f1480d[i];
        }

        public void close() {
            for (Closeable a : this.f1480d) {
                cp.m1543a(a);
            }
        }
    }

    /* compiled from: DiskLruCache */
    private final class C0383c {
        final /* synthetic */ cm f1482a;
        private final String f1483b;
        private final long[] f1484c;
        private boolean f1485d;
        private C0381a f1486e;
        private long f1487f;

        private C0383c(cm cmVar, String str) {
            this.f1482a = cmVar;
            this.f1483b = str;
            this.f1484c = new long[cmVar.f1497i];
        }

        public String m1508a() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long append : this.f1484c) {
                stringBuilder.append(' ').append(append);
            }
            return stringBuilder.toString();
        }

        private void m1500a(String[] strArr) throws IOException {
            if (strArr.length != this.f1482a.f1497i) {
                throw m1502b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.f1484c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw m1502b(strArr);
                }
            }
        }

        private IOException m1502b(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        public File m1507a(int i) {
            return new File(this.f1482a.f1491c, this.f1483b + "." + i);
        }

        public File m1509b(int i) {
            return new File(this.f1482a.f1491c, this.f1483b + "." + i + ".tmp");
        }
    }

    public void m1533a(cn cnVar) {
        this.f1502n = cnVar;
    }

    private cm(File file, int i, int i2, long j) {
        this.f1491c = file;
        this.f1495g = i;
        this.f1492d = new File(file, "journal");
        this.f1493e = new File(file, "journal.tmp");
        this.f1494f = new File(file, "journal.bkp");
        this.f1497i = i2;
        this.f1496h = j;
    }

    public static cm m1512a(File file, int i, int i2, long j) throws IOException {
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
                    m1517a(file2, file3, false);
                }
            }
            cm cmVar = new cm(file, i, i2, j);
            if (cmVar.f1492d.exists()) {
                try {
                    cmVar.m1524e();
                    cmVar.m1527f();
                    cmVar.f1499k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cmVar.f1492d, true), cp.f1511a));
                    return cmVar;
                } catch (IOException e) {
                    cmVar.m1537c();
                }
            }
            file.mkdirs();
            cmVar = new cm(file, i, i2, j);
            cmVar.m1528g();
            return cmVar;
        }
    }

    private void m1524e() throws IOException {
        Closeable coVar = new co(new FileInputStream(this.f1492d), cp.f1511a);
        int i;
        try {
            String a = coVar.m1542a();
            String a2 = coVar.m1542a();
            String a3 = coVar.m1542a();
            String a4 = coVar.m1542a();
            String a5 = coVar.m1542a();
            if ("libcore.io.DiskLruCache".equals(a) && "1".equals(a2) && Integer.toString(this.f1495g).equals(a3) && Integer.toString(this.f1497i).equals(a4) && "".equals(a5)) {
                i = 0;
                while (true) {
                    m1522d(coVar.m1542a());
                    i++;
                }
            } else {
                throw new IOException("unexpected journal header: [" + a + ", " + a2 + ", " + a4 + ", " + a5 + "]");
            }
        } catch (EOFException e) {
            this.f1501m = i - this.f1500l.size();
            cp.m1543a(coVar);
        } catch (Throwable th) {
            cp.m1543a(coVar);
        }
    }

    private void m1522d(String str) throws IOException {
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
                this.f1500l.remove(substring);
                return;
            }
            str2 = substring;
        } else {
            str2 = str.substring(i, indexOf2);
        }
        C0383c c0383c = (C0383c) this.f1500l.get(str2);
        if (c0383c == null) {
            c0383c = new C0383c(str2);
            this.f1500l.put(str2, c0383c);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(" ");
            c0383c.f1485d = true;
            c0383c.f1486e = null;
            c0383c.m1500a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            c0383c.f1486e = new C0381a(c0383c);
        } else if (indexOf2 != -1 || indexOf != "READ".length() || !str.startsWith("READ")) {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void m1527f() throws IOException {
        m1516a(this.f1493e);
        Iterator it = this.f1500l.values().iterator();
        while (it.hasNext()) {
            C0383c c0383c = (C0383c) it.next();
            int i;
            if (c0383c.f1486e == null) {
                for (i = 0; i < this.f1497i; i++) {
                    this.f1498j += c0383c.f1484c[i];
                }
            } else {
                c0383c.f1486e = null;
                for (i = 0; i < this.f1497i; i++) {
                    m1516a(c0383c.m1507a(i));
                    m1516a(c0383c.m1509b(i));
                }
                it.remove();
            }
        }
    }

    private synchronized void m1528g() throws IOException {
        if (this.f1499k != null) {
            this.f1499k.close();
        }
        Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f1493e), cp.f1511a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f1495g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f1497i));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (C0383c c0383c : this.f1500l.values()) {
                if (c0383c.f1486e != null) {
                    bufferedWriter.write("DIRTY " + c0383c.f1483b + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + c0383c.f1483b + c0383c.m1508a() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.f1492d.exists()) {
                m1517a(this.f1492d, this.f1494f, true);
            }
            m1517a(this.f1493e, this.f1492d, false);
            this.f1494f.delete();
            this.f1499k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f1492d, true), cp.f1511a));
        } catch (Throwable th) {
            bufferedWriter.close();
        }
    }

    private static void m1516a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void m1517a(File file, File file2, boolean z) throws IOException {
        if (z) {
            m1516a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    public synchronized C0382b m1532a(String str) throws IOException {
        int i;
        C0382b c0382b = null;
        synchronized (this) {
            m1530i();
            m1525e(str);
            C0383c c0383c = (C0383c) this.f1500l.get(str);
            if (c0383c != null) {
                if (c0383c.f1485d) {
                    r6 = new InputStream[this.f1497i];
                    int i2 = 0;
                    while (i2 < this.f1497i) {
                        try {
                            r6[i2] = new FileInputStream(c0383c.m1507a(i2));
                            i2++;
                        } catch (FileNotFoundException e) {
                            i = 0;
                            while (i < this.f1497i && r6[i] != null) {
                                InputStream[] inputStreamArr;
                                cp.m1543a(inputStreamArr[i]);
                                i++;
                            }
                        }
                    }
                    this.f1501m++;
                    this.f1499k.append("READ " + str + '\n');
                    if (m1529h()) {
                        this.f1490b.submit(this.f1504p);
                    }
                    c0382b = new C0382b(str, c0383c.f1487f, inputStreamArr, c0383c.f1484c);
                }
            }
        }
        return c0382b;
    }

    public C0381a m1535b(String str) throws IOException {
        return m1511a(str, -1);
    }

    private synchronized C0381a m1511a(String str, long j) throws IOException {
        C0381a c0381a;
        m1530i();
        m1525e(str);
        C0383c c0383c = (C0383c) this.f1500l.get(str);
        if (j == -1 || (c0383c != null && c0383c.f1487f == j)) {
            C0383c c0383c2;
            if (c0383c == null) {
                c0383c = new C0383c(str);
                this.f1500l.put(str, c0383c);
                c0383c2 = c0383c;
            } else if (c0383c.f1486e != null) {
                c0381a = null;
            } else {
                c0383c2 = c0383c;
            }
            c0381a = new C0381a(c0383c2);
            c0383c2.f1486e = c0381a;
            this.f1499k.write("DIRTY " + str + '\n');
            this.f1499k.flush();
        } else {
            c0381a = null;
        }
        return c0381a;
    }

    private synchronized void m1514a(C0381a c0381a, boolean z) throws IOException {
        int i = 0;
        synchronized (this) {
            C0383c a = c0381a.f1473b;
            if (a.f1486e != c0381a) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.f1485d) {
                    int i2 = 0;
                    while (i2 < this.f1497i) {
                        if (!c0381a.f1474c[i2]) {
                            c0381a.m1494b();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!a.m1509b(i2).exists()) {
                            c0381a.m1494b();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.f1497i) {
                File b = a.m1509b(i);
                if (!z) {
                    m1516a(b);
                } else if (b.exists()) {
                    File a2 = a.m1507a(i);
                    b.renameTo(a2);
                    long j = a.f1484c[i];
                    long length = a2.length();
                    a.f1484c[i] = length;
                    this.f1498j = (this.f1498j - j) + length;
                }
                i++;
            }
            this.f1501m++;
            a.f1486e = null;
            if ((a.f1485d | z) != 0) {
                a.f1485d = true;
                this.f1499k.write("CLEAN " + a.f1483b + a.m1508a() + '\n');
                if (z) {
                    long j2 = this.f1503o;
                    this.f1503o = 1 + j2;
                    a.f1487f = j2;
                }
            } else {
                this.f1500l.remove(a.f1483b);
                this.f1499k.write("REMOVE " + a.f1483b + '\n');
            }
            this.f1499k.flush();
            if (this.f1498j > this.f1496h || m1529h()) {
                this.f1490b.submit(this.f1504p);
            }
        }
    }

    private boolean m1529h() {
        return this.f1501m >= 2000 && this.f1501m >= this.f1500l.size();
    }

    public synchronized boolean m1538c(String str) throws IOException {
        boolean z;
        int i = 0;
        synchronized (this) {
            m1530i();
            m1525e(str);
            C0383c c0383c = (C0383c) this.f1500l.get(str);
            if (c0383c == null || c0383c.f1486e != null) {
                z = false;
            } else {
                while (i < this.f1497i) {
                    File a = c0383c.m1507a(i);
                    if (!a.exists() || a.delete()) {
                        this.f1498j -= c0383c.f1484c[i];
                        c0383c.f1484c[i] = 0;
                        i++;
                    } else {
                        throw new IOException("failed to delete " + a);
                    }
                }
                this.f1501m++;
                this.f1499k.append("REMOVE " + str + '\n');
                this.f1500l.remove(str);
                if (m1529h()) {
                    this.f1490b.submit(this.f1504p);
                }
                z = true;
            }
        }
        return z;
    }

    public synchronized boolean m1534a() {
        return this.f1499k == null;
    }

    private void m1530i() {
        if (this.f1499k == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void m1536b() throws IOException {
        m1530i();
        m1531j();
        this.f1499k.flush();
    }

    public synchronized void close() throws IOException {
        if (this.f1499k != null) {
            Iterator it = new ArrayList(this.f1500l.values()).iterator();
            while (it.hasNext()) {
                C0383c c0383c = (C0383c) it.next();
                if (c0383c.f1486e != null) {
                    c0383c.f1486e.m1494b();
                }
            }
            m1531j();
            this.f1499k.close();
            this.f1499k = null;
        }
    }

    private void m1531j() throws IOException {
        while (this.f1498j > this.f1496h) {
            String str = (String) ((Entry) this.f1500l.entrySet().iterator().next()).getKey();
            m1538c(str);
            if (this.f1502n != null) {
                this.f1502n.mo1766a(str);
            }
        }
    }

    public void m1537c() throws IOException {
        close();
        cp.m1544a(this.f1491c);
    }

    private void m1525e(String str) {
        if (!f1488a.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
    }
}
