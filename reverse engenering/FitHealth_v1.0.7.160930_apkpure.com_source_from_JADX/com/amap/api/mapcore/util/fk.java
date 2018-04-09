package com.amap.api.mapcore.util;

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
public final class fk implements Closeable {
    static final Pattern f626a = Pattern.compile("[a-z0-9_-]{1,120}");
    private static final OutputStream f627q = new fm();
    final ThreadPoolExecutor f628b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final File f629c;
    private final File f630d;
    private final File f631e;
    private final File f632f;
    private final int f633g;
    private long f634h;
    private final int f635i;
    private long f636j = 0;
    private Writer f637k;
    private final LinkedHashMap<String, C0261c> f638l = new LinkedHashMap(0, 0.75f, true);
    private int f639m;
    private fn f640n;
    private long f641o = 0;
    private final Callable<Void> f642p = new fl(this);

    /* compiled from: DiskLruCache */
    public final class C0259a {
        final /* synthetic */ fk f610a;
        private final C0261c f611b;
        private final boolean[] f612c;
        private boolean f613d;
        private boolean f614e;

        /* compiled from: DiskLruCache */
        private class C0258a extends FilterOutputStream {
            final /* synthetic */ C0259a f609a;

            private C0258a(C0259a c0259a, OutputStream outputStream) {
                this.f609a = c0259a;
                super(outputStream);
            }

            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException e) {
                    this.f609a.f613d = true;
                }
            }

            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException e) {
                    this.f609a.f613d = true;
                }
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    this.f609a.f613d = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    this.f609a.f613d = true;
                }
            }
        }

        private C0259a(fk fkVar, C0261c c0261c) {
            this.f610a = fkVar;
            this.f611b = c0261c;
            this.f612c = c0261c.f623d ? null : new boolean[fkVar.f635i];
        }

        public OutputStream m894a(int i) throws IOException {
            if (i < 0 || i >= this.f610a.f635i) {
                throw new IllegalArgumentException("Expected index " + i + " to " + "be greater than 0 and less than the maximum value count " + "of " + this.f610a.f635i);
            }
            OutputStream d;
            synchronized (this.f610a) {
                File b;
                OutputStream fileOutputStream;
                if (this.f611b.f624e != this) {
                    throw new IllegalStateException();
                }
                if (!this.f611b.f623d) {
                    this.f612c[i] = true;
                }
                b = this.f611b.m911b(i);
                try {
                    fileOutputStream = new FileOutputStream(b);
                } catch (FileNotFoundException e) {
                    this.f610a.f629c.mkdirs();
                    try {
                        fileOutputStream = new FileOutputStream(b);
                    } catch (FileNotFoundException e2) {
                        d = fk.f627q;
                    }
                }
                d = new C0258a(fileOutputStream);
            }
            return d;
        }

        public void m895a() throws IOException {
            if (this.f613d) {
                this.f610a.m916a(this, false);
                this.f610a.m940c(this.f611b.f621b);
            } else {
                this.f610a.m916a(this, true);
            }
            this.f614e = true;
        }

        public void m896b() throws IOException {
            this.f610a.m916a(this, false);
        }
    }

    /* compiled from: DiskLruCache */
    public final class C0260b implements Closeable {
        final /* synthetic */ fk f615a;
        private final String f616b;
        private final long f617c;
        private final InputStream[] f618d;
        private final long[] f619e;

        private C0260b(fk fkVar, String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.f615a = fkVar;
            this.f616b = str;
            this.f617c = j;
            this.f618d = inputStreamArr;
            this.f619e = jArr;
        }

        public InputStream m897a(int i) {
            return this.f618d[i];
        }

        public void close() {
            for (Closeable a : this.f618d) {
                fp.m946a(a);
            }
        }
    }

    /* compiled from: DiskLruCache */
    private final class C0261c {
        final /* synthetic */ fk f620a;
        private final String f621b;
        private final long[] f622c;
        private boolean f623d;
        private C0259a f624e;
        private long f625f;

        private C0261c(fk fkVar, String str) {
            this.f620a = fkVar;
            this.f621b = str;
            this.f622c = new long[fkVar.f635i];
        }

        public String m910a() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long append : this.f622c) {
                stringBuilder.append(' ').append(append);
            }
            return stringBuilder.toString();
        }

        private void m902a(String[] strArr) throws IOException {
            if (strArr.length != this.f620a.f635i) {
                throw m904b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.f622c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw m904b(strArr);
                }
            }
        }

        private IOException m904b(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        public File m909a(int i) {
            return new File(this.f620a.f629c, this.f621b + "." + i);
        }

        public File m911b(int i) {
            return new File(this.f620a.f629c, this.f621b + "." + i + ".tmp");
        }
    }

    public void m935a(fn fnVar) {
        this.f640n = fnVar;
    }

    private fk(File file, int i, int i2, long j) {
        this.f629c = file;
        this.f633g = i;
        this.f630d = new File(file, "journal");
        this.f631e = new File(file, "journal.tmp");
        this.f632f = new File(file, "journal.bkp");
        this.f635i = i2;
        this.f634h = j;
    }

    public static fk m914a(File file, int i, int i2, long j) throws IOException {
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
                    m919a(file2, file3, false);
                }
            }
            fk fkVar = new fk(file, i, i2, j);
            if (fkVar.f630d.exists()) {
                try {
                    fkVar.m926e();
                    fkVar.m929f();
                    fkVar.f637k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fkVar.f630d, true), fp.f650a));
                    return fkVar;
                } catch (IOException e) {
                    fkVar.m939c();
                }
            }
            file.mkdirs();
            fkVar = new fk(file, i, i2, j);
            fkVar.m930g();
            return fkVar;
        }
    }

    private void m926e() throws IOException {
        Closeable foVar = new fo(new FileInputStream(this.f630d), fp.f650a);
        int i;
        try {
            String a = foVar.m945a();
            String a2 = foVar.m945a();
            String a3 = foVar.m945a();
            String a4 = foVar.m945a();
            String a5 = foVar.m945a();
            if ("libcore.io.DiskLruCache".equals(a) && "1".equals(a2) && Integer.toString(this.f633g).equals(a3) && Integer.toString(this.f635i).equals(a4) && "".equals(a5)) {
                i = 0;
                while (true) {
                    m924d(foVar.m945a());
                    i++;
                }
            } else {
                throw new IOException("unexpected journal header: [" + a + ", " + a2 + ", " + a4 + ", " + a5 + "]");
            }
        } catch (EOFException e) {
            this.f639m = i - this.f638l.size();
            fp.m946a(foVar);
        } catch (Throwable th) {
            fp.m946a(foVar);
        }
    }

    private void m924d(String str) throws IOException {
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
                this.f638l.remove(substring);
                return;
            }
            str2 = substring;
        } else {
            str2 = str.substring(i, indexOf2);
        }
        C0261c c0261c = (C0261c) this.f638l.get(str2);
        if (c0261c == null) {
            c0261c = new C0261c(str2);
            this.f638l.put(str2, c0261c);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(" ");
            c0261c.f623d = true;
            c0261c.f624e = null;
            c0261c.m902a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            c0261c.f624e = new C0259a(c0261c);
        } else if (indexOf2 != -1 || indexOf != "READ".length() || !str.startsWith("READ")) {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void m929f() throws IOException {
        m918a(this.f631e);
        Iterator it = this.f638l.values().iterator();
        while (it.hasNext()) {
            C0261c c0261c = (C0261c) it.next();
            int i;
            if (c0261c.f624e == null) {
                for (i = 0; i < this.f635i; i++) {
                    this.f636j += c0261c.f622c[i];
                }
            } else {
                c0261c.f624e = null;
                for (i = 0; i < this.f635i; i++) {
                    m918a(c0261c.m909a(i));
                    m918a(c0261c.m911b(i));
                }
                it.remove();
            }
        }
    }

    private synchronized void m930g() throws IOException {
        if (this.f637k != null) {
            this.f637k.close();
        }
        Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f631e), fp.f650a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f633g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f635i));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (C0261c c0261c : this.f638l.values()) {
                if (c0261c.f624e != null) {
                    bufferedWriter.write("DIRTY " + c0261c.f621b + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + c0261c.f621b + c0261c.m910a() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.f630d.exists()) {
                m919a(this.f630d, this.f632f, true);
            }
            m919a(this.f631e, this.f630d, false);
            this.f632f.delete();
            this.f637k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f630d, true), fp.f650a));
        } catch (Throwable th) {
            bufferedWriter.close();
        }
    }

    private static void m918a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void m919a(File file, File file2, boolean z) throws IOException {
        if (z) {
            m918a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    public synchronized C0260b m934a(String str) throws IOException {
        int i;
        C0260b c0260b = null;
        synchronized (this) {
            m932i();
            m927e(str);
            C0261c c0261c = (C0261c) this.f638l.get(str);
            if (c0261c != null) {
                if (c0261c.f623d) {
                    InputStream[] inputStreamArr = new InputStream[this.f635i];
                    int i2 = 0;
                    while (i2 < this.f635i) {
                        try {
                            inputStreamArr[i2] = new FileInputStream(c0261c.m909a(i2));
                            i2++;
                        } catch (FileNotFoundException e) {
                            i = 0;
                            while (i < this.f635i && inputStreamArr[i] != null) {
                                fp.m946a(inputStreamArr[i]);
                                i++;
                            }
                        }
                    }
                    this.f639m++;
                    this.f637k.append("READ " + str + '\n');
                    if (m931h()) {
                        this.f628b.submit(this.f642p);
                    }
                    c0260b = new C0260b(str, c0261c.f625f, inputStreamArr, c0261c.f622c);
                }
            }
        }
        return c0260b;
    }

    public C0259a m937b(String str) throws IOException {
        return m913a(str, -1);
    }

    private synchronized C0259a m913a(String str, long j) throws IOException {
        C0259a c0259a;
        m932i();
        m927e(str);
        C0261c c0261c = (C0261c) this.f638l.get(str);
        if (j == -1 || (c0261c != null && c0261c.f625f == j)) {
            C0261c c0261c2;
            if (c0261c == null) {
                c0261c = new C0261c(str);
                this.f638l.put(str, c0261c);
                c0261c2 = c0261c;
            } else if (c0261c.f624e != null) {
                c0259a = null;
            } else {
                c0261c2 = c0261c;
            }
            c0259a = new C0259a(c0261c2);
            c0261c2.f624e = c0259a;
            this.f637k.write("DIRTY " + str + '\n');
            this.f637k.flush();
        } else {
            c0259a = null;
        }
        return c0259a;
    }

    private synchronized void m916a(C0259a c0259a, boolean z) throws IOException {
        int i = 0;
        synchronized (this) {
            C0261c a = c0259a.f611b;
            if (a.f624e != c0259a) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.f623d) {
                    int i2 = 0;
                    while (i2 < this.f635i) {
                        if (!c0259a.f612c[i2]) {
                            c0259a.m896b();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!a.m911b(i2).exists()) {
                            c0259a.m896b();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.f635i) {
                File b = a.m911b(i);
                if (!z) {
                    m918a(b);
                } else if (b.exists()) {
                    File a2 = a.m909a(i);
                    b.renameTo(a2);
                    long j = a.f622c[i];
                    long length = a2.length();
                    a.f622c[i] = length;
                    this.f636j = (this.f636j - j) + length;
                }
                i++;
            }
            this.f639m++;
            a.f624e = null;
            if ((a.f623d | z) != 0) {
                a.f623d = true;
                this.f637k.write("CLEAN " + a.f621b + a.m910a() + '\n');
                if (z) {
                    long j2 = this.f641o;
                    this.f641o = 1 + j2;
                    a.f625f = j2;
                }
            } else {
                this.f638l.remove(a.f621b);
                this.f637k.write("REMOVE " + a.f621b + '\n');
            }
            this.f637k.flush();
            if (this.f636j > this.f634h || m931h()) {
                this.f628b.submit(this.f642p);
            }
        }
    }

    private boolean m931h() {
        return this.f639m >= 2000 && this.f639m >= this.f638l.size();
    }

    public synchronized boolean m940c(String str) throws IOException {
        boolean z;
        int i = 0;
        synchronized (this) {
            m932i();
            m927e(str);
            C0261c c0261c = (C0261c) this.f638l.get(str);
            if (c0261c == null || c0261c.f624e != null) {
                z = false;
            } else {
                while (i < this.f635i) {
                    File a = c0261c.m909a(i);
                    if (!a.exists() || a.delete()) {
                        this.f636j -= c0261c.f622c[i];
                        c0261c.f622c[i] = 0;
                        i++;
                    } else {
                        throw new IOException("failed to delete " + a);
                    }
                }
                this.f639m++;
                this.f637k.append("REMOVE " + str + '\n');
                this.f638l.remove(str);
                if (m931h()) {
                    this.f628b.submit(this.f642p);
                }
                z = true;
            }
        }
        return z;
    }

    public synchronized boolean m936a() {
        return this.f637k == null;
    }

    private void m932i() {
        if (this.f637k == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void m938b() throws IOException {
        m932i();
        m933j();
        this.f637k.flush();
    }

    public synchronized void close() throws IOException {
        if (this.f637k != null) {
            Iterator it = new ArrayList(this.f638l.values()).iterator();
            while (it.hasNext()) {
                C0261c c0261c = (C0261c) it.next();
                if (c0261c.f624e != null) {
                    c0261c.f624e.m896b();
                }
            }
            m933j();
            this.f637k.close();
            this.f637k = null;
        }
    }

    private void m933j() throws IOException {
        while (this.f636j > this.f634h) {
            String str = (String) ((Entry) this.f638l.entrySet().iterator().next()).getKey();
            m940c(str);
            if (this.f640n != null) {
                this.f640n.mo1649a(str);
            }
        }
    }

    public void m939c() throws IOException {
        close();
        fp.m947a(this.f629c);
    }

    private void m927e(String str) {
        if (!f626a.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
    }
}
