package com.aps;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import p031u.aly.cv;

/* compiled from: StrictLineReader */
class C0468r implements Closeable {
    private final InputStream f1938a;
    private final Charset f1939b;
    private byte[] f1940c;
    private int f1941d;
    private int f1942e;

    public C0468r(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    public C0468r(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(C0469s.f1943a)) {
            this.f1938a = inputStream;
            this.f1939b = charset;
            this.f1940c = new byte[i];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public void close() throws IOException {
        synchronized (this.f1938a) {
            if (this.f1940c != null) {
                this.f1940c = null;
                this.f1938a.close();
            }
        }
    }

    public String m2000a() throws IOException {
        String str;
        synchronized (this.f1938a) {
            if (this.f1940c == null) {
                throw new IOException("LineReader is closed");
            }
            int i;
            if (this.f1941d >= this.f1942e) {
                m1999b();
            }
            int i2 = this.f1941d;
            while (i2 != this.f1942e) {
                if (this.f1940c[i2] == (byte) 10) {
                    int i3 = (i2 == this.f1941d || this.f1940c[i2 - 1] != cv.f3781k) ? i2 : i2 - 1;
                    str = new String(this.f1940c, this.f1941d, i3 - this.f1941d, this.f1939b.name());
                    this.f1941d = i2 + 1;
                } else {
                    i2++;
                }
            }
            ByteArrayOutputStream c04671 = new ByteArrayOutputStream(this, (this.f1942e - this.f1941d) + 80) {
                final /* synthetic */ C0468r f1937a;

                public String toString() {
                    int i = (this.count <= 0 || this.buf[this.count - 1] != cv.f3781k) ? this.count : this.count - 1;
                    try {
                        return new String(this.buf, 0, i, this.f1937a.f1939b.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                }
            };
            loop1:
            while (true) {
                c04671.write(this.f1940c, this.f1941d, this.f1942e - this.f1941d);
                this.f1942e = -1;
                m1999b();
                i = this.f1941d;
                while (i != this.f1942e) {
                    if (this.f1940c[i] == (byte) 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.f1941d) {
                c04671.write(this.f1940c, this.f1941d, i - this.f1941d);
            }
            this.f1941d = i + 1;
            str = c04671.toString();
        }
        return str;
    }

    private void m1999b() throws IOException {
        int read = this.f1938a.read(this.f1940c, 0, this.f1940c.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.f1941d = 0;
        this.f1942e = read;
    }
}
