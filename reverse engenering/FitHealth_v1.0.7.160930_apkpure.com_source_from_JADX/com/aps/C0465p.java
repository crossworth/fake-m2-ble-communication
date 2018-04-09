package com.aps;

import com.aps.C0451g.C0448a;
import com.aps.C0451g.C0450c;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: SimpleDiskCache */
public class C0465p {
    private static final List<File> f1933a = new ArrayList();
    private C0451g f1934b;
    private int f1935c;

    /* compiled from: SimpleDiskCache */
    private static class C0464a extends FilterOutputStream {
        private final C0448a f1931a;
        private boolean f1932b;

        private C0464a(OutputStream outputStream, C0448a c0448a) {
            super(outputStream);
            this.f1932b = false;
            this.f1931a = c0448a;
        }

        public void close() throws IOException {
            IOException iOException = null;
            try {
                super.close();
            } catch (IOException e) {
                iOException = e;
            }
            if (this.f1932b) {
                this.f1931a.m1910b();
            } else {
                this.f1931a.m1909a();
            }
            if (iOException != null) {
                throw iOException;
            }
        }

        public void flush() throws IOException {
            try {
                super.flush();
            } catch (IOException e) {
                this.f1932b = true;
                throw e;
            }
        }

        public void write(int i) throws IOException {
            try {
                super.write(i);
            } catch (IOException e) {
                this.f1932b = true;
                throw e;
            }
        }

        public void write(byte[] bArr) throws IOException {
            try {
                super.write(bArr);
            } catch (IOException e) {
                this.f1932b = true;
                throw e;
            }
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                super.write(bArr, i, i2);
            } catch (IOException e) {
                this.f1932b = true;
                throw e;
            }
        }
    }

    private C0465p(File file, int i, long j) throws IOException {
        this.f1935c = i;
        this.f1934b = C0451g.m1928a(file, i, 1, j);
    }

    public static synchronized C0465p m1985a(File file, int i, long j) throws IOException {
        C0465p c0465p;
        synchronized (C0465p.class) {
            if (f1933a.contains(file)) {
                throw new IllegalStateException("Cache dir " + file.getAbsolutePath() + " was used before.");
            }
            f1933a.add(file);
            c0465p = new C0465p(file, i, j);
        }
        return c0465p;
    }

    public Map<String, Serializable> m1990a(String str) throws IOException {
        C0450c a = this.f1934b.m1948a(m1987b(str));
        if (a == null) {
            return null;
        }
        try {
            Map<String, Serializable> a2 = m1986a(a);
            return a2;
        } finally {
            a.close();
        }
    }

    public OutputStream m1989a(String str, Map<String, ? extends Serializable> map) throws IOException {
        C0448a b = this.f1934b.m1950b(m1987b(str));
        try {
            OutputStream objectOutputStream = new ObjectOutputStream(b.m1908a(0));
            objectOutputStream.writeObject(map);
            return new C0464a(objectOutputStream, b);
        } catch (IOException e) {
            b.m1910b();
            throw e;
        }
    }

    public void m1991b(String str, Map<String, ? extends Serializable> map) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = m1989a(str, map);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private Map<String, Serializable> m1986a(C0450c c0450c) throws IOException {
        ObjectInputStream objectInputStream;
        Throwable e;
        try {
            objectInputStream = new ObjectInputStream(new BufferedInputStream(c0450c.m1925a(0)));
            try {
                Map<String, Serializable> map = (Map) objectInputStream.readObject();
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                return map;
            } catch (ClassNotFoundException e2) {
                e = e2;
                try {
                    throw new RuntimeException(e);
                } catch (Throwable th) {
                    e = th;
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    throw e;
                }
            }
        } catch (ClassNotFoundException e3) {
            e = e3;
            objectInputStream = null;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            e = th2;
            objectInputStream = null;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            throw e;
        }
    }

    private String m1987b(String str) {
        return m1988c(str);
    }

    private String m1988c(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("UTF-8"));
            return new BigInteger(1, instance.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError();
        }
    }
}
