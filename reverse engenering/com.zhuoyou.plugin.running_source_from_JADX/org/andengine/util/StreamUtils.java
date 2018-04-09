package org.andengine.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;

public final class StreamUtils {
    private static final int END_OF_STREAM = -1;
    public static final int IO_BUFFER_SIZE = 8192;

    public static final String readFully(InputStream pInputStream) throws IOException {
        StringWriter writer = new StringWriter();
        char[] buf = new char[8192];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(pInputStream, "UTF-8"));
            while (true) {
                int read = reader.read(buf);
                if (read == -1) {
                    break;
                }
                writer.write(buf, 0, read);
            }
            return writer.toString();
        } finally {
            close(pInputStream);
        }
    }

    public static final byte[] streamToBytes(InputStream pInputStream) throws IOException {
        return streamToBytes(pInputStream, -1);
    }

    public static final byte[] streamToBytes(InputStream pInputStream, int pReadLimit) throws IOException {
        int i;
        if (pReadLimit == -1) {
            i = 8192;
        } else {
            i = pReadLimit;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream(i);
        copy(pInputStream, os, pReadLimit);
        return os.toByteArray();
    }

    public static final void streamToBytes(InputStream pInputStream, int pByteLimit, byte[] pData) throws IOException {
        streamToBytes(pInputStream, pByteLimit, pData, 0);
    }

    public static final void streamToBytes(InputStream pInputStream, int pByteLimit, byte[] pData, int pOffset) throws IOException {
        if (pByteLimit > pData.length - pOffset) {
            throw new IOException("pData is not big enough.");
        }
        int pBytesLeftToRead = pByteLimit;
        int readTotal = 0;
        while (true) {
            int read = pInputStream.read(pData, pOffset + readTotal, pBytesLeftToRead);
            if (read == -1) {
                break;
            }
            readTotal += read;
            if (pBytesLeftToRead <= read) {
                break;
            }
            pBytesLeftToRead -= read;
        }
        if (readTotal != pByteLimit) {
            throw new IOException("ReadLimit: '" + pByteLimit + "', Read: '" + readTotal + "'.");
        }
    }

    public static final void copy(InputStream pInputStream, OutputStream pOutputStream) throws IOException {
        copy(pInputStream, pOutputStream, -1);
    }

    public static final void copy(InputStream pInputStream, byte[] pData) throws IOException {
        int dataOffset = 0;
        byte[] buf = new byte[8192];
        while (true) {
            int read = pInputStream.read(buf);
            if (read != -1) {
                System.arraycopy(buf, 0, pData, dataOffset, read);
                dataOffset += read;
            } else {
                return;
            }
        }
    }

    public static final void copy(InputStream pInputStream, ByteBuffer pByteBuffer) throws IOException {
        byte[] buf = new byte[8192];
        while (true) {
            int read = pInputStream.read(buf);
            if (read != -1) {
                pByteBuffer.put(buf, 0, read);
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void copy(java.io.InputStream r10, java.io.OutputStream r11, int r12) throws java.io.IOException {
        /*
        r5 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r9 = -1;
        r8 = 0;
        if (r12 != r9) goto L_0x0012;
    L_0x0006:
        r0 = new byte[r5];
    L_0x0008:
        r4 = r10.read(r0);
        if (r4 == r9) goto L_0x002e;
    L_0x000e:
        r11.write(r0, r8, r4);
        goto L_0x0008;
    L_0x0012:
        r0 = new byte[r5];
        r1 = java.lang.Math.min(r12, r5);
        r2 = (long) r12;
    L_0x0019:
        r4 = r10.read(r0, r8, r1);
        if (r4 == r9) goto L_0x002e;
    L_0x001f:
        r6 = (long) r4;
        r5 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r5 <= 0) goto L_0x002a;
    L_0x0024:
        r11.write(r0, r8, r4);
        r6 = (long) r4;
        r2 = r2 - r6;
        goto L_0x0019;
    L_0x002a:
        r5 = (int) r2;
        r11.write(r0, r8, r5);
    L_0x002e:
        r11.flush();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.andengine.util.StreamUtils.copy(java.io.InputStream, java.io.OutputStream, int):void");
    }

    public static final boolean copyAndClose(InputStream pInputStream, OutputStream pOutputStream) {
        boolean z;
        try {
            copy(pInputStream, pOutputStream, -1);
            z = true;
        } catch (IOException e) {
            z = false;
        } finally {
            close(pInputStream);
            close(pOutputStream);
        }
        return z;
    }

    public static final void close(Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final void flushCloseStream(OutputStream pOutputStream) {
        if (pOutputStream != null) {
            try {
                pOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(pOutputStream);
            }
        }
    }

    public static final void flushCloseWriter(Writer pWriter) {
        if (pWriter != null) {
            try {
                pWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(pWriter);
            }
        }
    }
}
