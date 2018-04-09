package com.amap.api.services.proguard;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import p031u.aly.cv;

/* compiled from: StrictLineReader */
public class co implements Closeable {
    private final InputStream f1506a;
    private final Charset f1507b;
    private byte[] f1508c;
    private int f1509d;
    private int f1510e;

    public co(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    public co(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(cp.f1511a)) {
            this.f1506a = inputStream;
            this.f1507b = charset;
            this.f1508c = new byte[i];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public void close() throws IOException {
        synchronized (this.f1506a) {
            if (this.f1508c != null) {
                this.f1508c = null;
                this.f1506a.close();
            }
        }
    }

    public String m1542a() throws IOException {
        String str;
        synchronized (this.f1506a) {
            if (this.f1508c == null) {
                throw new IOException("LineReader is closed");
            }
            int i;
            if (this.f1509d >= this.f1510e) {
                m1541b();
            }
            int i2 = this.f1509d;
            while (i2 != this.f1510e) {
                if (this.f1508c[i2] == (byte) 10) {
                    int i3 = (i2 == this.f1509d || this.f1508c[i2 - 1] != cv.f3781k) ? i2 : i2 - 1;
                    str = new String(this.f1508c, this.f1509d, i3 - this.f1509d, this.f1507b.name());
                    this.f1509d = i2 + 1;
                } else {
                    i2++;
                }
            }
            ByteArrayOutputStream c03841 = new ByteArrayOutputStream(this, (this.f1510e - this.f1509d) + 80) {
                final /* synthetic */ co f1505a;

                public String toString() {
                    int i = (this.count <= 0 || this.buf[this.count - 1] != cv.f3781k) ? this.count : this.count - 1;
                    try {
                        return new String(this.buf, 0, i, this.f1505a.f1507b.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                }
            };
            loop1:
            while (true) {
                c03841.write(this.f1508c, this.f1509d, this.f1510e - this.f1509d);
                this.f1510e = -1;
                m1541b();
                i = this.f1509d;
                while (i != this.f1510e) {
                    if (this.f1508c[i] == (byte) 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.f1509d) {
                c03841.write(this.f1508c, this.f1509d, i - this.f1509d);
            }
            this.f1509d = i + 1;
            str = c03841.toString();
        }
        return str;
    }

    private void m1541b() throws IOException {
        int read = this.f1506a.read(this.f1508c, 0, this.f1508c.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.f1509d = 0;
        this.f1510e = read;
    }
}
