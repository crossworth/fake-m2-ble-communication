package com.amap.api.mapcore.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import p031u.aly.cv;

/* compiled from: StrictLineReader */
public class fo implements Closeable {
    private final InputStream f645a;
    private final Charset f646b;
    private byte[] f647c;
    private int f648d;
    private int f649e;

    public fo(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    public fo(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(fp.f650a)) {
            this.f645a = inputStream;
            this.f646b = charset;
            this.f647c = new byte[i];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public void close() throws IOException {
        synchronized (this.f645a) {
            if (this.f647c != null) {
                this.f647c = null;
                this.f645a.close();
            }
        }
    }

    public String m945a() throws IOException {
        String str;
        synchronized (this.f645a) {
            if (this.f647c == null) {
                throw new IOException("LineReader is closed");
            }
            int i;
            if (this.f648d >= this.f649e) {
                m944b();
            }
            int i2 = this.f648d;
            while (i2 != this.f649e) {
                if (this.f647c[i2] == (byte) 10) {
                    int i3 = (i2 == this.f648d || this.f647c[i2 - 1] != cv.f3781k) ? i2 : i2 - 1;
                    str = new String(this.f647c, this.f648d, i3 - this.f648d, this.f646b.name());
                    this.f648d = i2 + 1;
                } else {
                    i2++;
                }
            }
            ByteArrayOutputStream c02621 = new ByteArrayOutputStream(this, (this.f649e - this.f648d) + 80) {
                final /* synthetic */ fo f644a;

                public String toString() {
                    int i = (this.count <= 0 || this.buf[this.count - 1] != cv.f3781k) ? this.count : this.count - 1;
                    try {
                        return new String(this.buf, 0, i, this.f644a.f646b.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                }
            };
            loop1:
            while (true) {
                c02621.write(this.f647c, this.f648d, this.f649e - this.f648d);
                this.f649e = -1;
                m944b();
                i = this.f648d;
                while (i != this.f649e) {
                    if (this.f647c[i] == (byte) 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.f648d) {
                c02621.write(this.f647c, this.f648d, i - this.f648d);
            }
            this.f648d = i + 1;
            str = c02621.toString();
        }
        return str;
    }

    private void m944b() throws IOException {
        int read = this.f645a.read(this.f647c, 0, this.f647c.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.f648d = 0;
        this.f649e = read;
    }
}
