package com.droi.sdk.core.priv;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import com.droi.sdk.DroiProgressCallback;
import com.droi.sdk.internal.DroiLog;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;

public class FileDescriptorHelper {
    public static final long f2835a = 10485760;
    public static final Method f2836b = m2649a(MemoryFile.class, "getFileDescriptor", new Class[0]);
    private static final String f2837c = "FILE_DESCRIPTOR_HELPER";

    private static Method m2649a(Class cls, String str, Class<?>... clsArr) {
        try {
            return cls.getDeclaredMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static byte[] calculateHash(File file) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[8192];
            while (true) {
                int read = fileInputStream.read(bArr, 0, 8192);
                if (read <= 0) {
                    return instance.digest();
                }
                instance.update(bArr, 0, read);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] calculateHash(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                DroiLog.m2873w(f2837c, e);
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[8192];
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                outputStream.write(bArr, 0, read);
                j += (long) read;
            } catch (IOException e) {
            }
        }
        return j;
    }

    public static void copy(File file, File file2, DroiProgressCallback droiProgressCallback) throws IOException {
        Closeable channel;
        Throwable th;
        Closeable closeable = null;
        try {
            long length = file.length();
            channel = new FileInputStream(file).getChannel();
            try {
                Closeable channel2 = new FileOutputStream(file2).getChannel();
                long j = 0;
                while (j < length) {
                    try {
                        long transferFrom = channel2.transferFrom(channel, j, f2835a);
                        if (transferFrom == 0) {
                            break;
                        }
                        transferFrom += j;
                        if (droiProgressCallback != null) {
                            droiProgressCallback.progress(file, transferFrom, length);
                        }
                        j = transferFrom;
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        closeable = channel;
                        channel = channel2;
                        th = th3;
                    }
                }
                closeQuietly(channel2);
                closeQuietly(channel);
            } catch (Throwable th4) {
                th = th4;
                closeable = channel;
                channel = null;
                closeQuietly(channel);
                closeQuietly(closeable);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            channel = null;
            closeQuietly(channel);
            closeQuietly(closeable);
            throw th;
        }
    }

    public static void copy(byte[] bArr, File file, DroiProgressCallback droiProgressCallback) throws IOException {
        Throwable th;
        Closeable closeable = null;
        try {
            long length = (long) bArr.length;
            Closeable channel = new FileOutputStream(file).getChannel();
            try {
                channel.write(ByteBuffer.wrap(bArr));
                if (droiProgressCallback != null) {
                    droiProgressCallback.progress(bArr, length, length);
                }
                closeQuietly(channel);
            } catch (Throwable th2) {
                th = th2;
                closeable = channel;
                closeQuietly(closeable);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            closeQuietly(closeable);
            throw th;
        }
    }

    public static long copyLarge(Reader reader, Writer writer) {
        char[] cArr = new char[8192];
        long j = 0;
        while (true) {
            try {
                int read = reader.read(cArr);
                if (-1 == read) {
                    break;
                }
                writer.write(cArr, 0, read);
                j += (long) read;
            } catch (IOException e) {
            }
        }
        return j;
    }

    public static void deleteRecursively(File file) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(file);
        while (!linkedList.isEmpty()) {
            File file2 = (File) linkedList.removeFirst();
            File[] listFiles = file2.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file2.delete();
            } else {
                linkedList.addAll(Arrays.asList(listFiles));
                linkedList.addLast(file2);
            }
        }
    }

    public static ParcelFileDescriptor fromByteArray(byte[] bArr) {
        return fromByteArray(bArr, 0, bArr.length);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.ParcelFileDescriptor fromByteArray(byte[] r5, int r6, int r7) {
        /*
        r2 = 0;
        r3 = new android.os.MemoryFile;	 Catch:{ IOException -> 0x0030, InvocationTargetException -> 0x0037, IllegalAccessException -> 0x003e, all -> 0x0045 }
        r0 = java.util.UUID.randomUUID();	 Catch:{ IOException -> 0x0030, InvocationTargetException -> 0x0037, IllegalAccessException -> 0x003e, all -> 0x0045 }
        r0 = r0.toString();	 Catch:{ IOException -> 0x0030, InvocationTargetException -> 0x0037, IllegalAccessException -> 0x003e, all -> 0x0045 }
        r3.<init>(r0, r7);	 Catch:{ IOException -> 0x0030, InvocationTargetException -> 0x0037, IllegalAccessException -> 0x003e, all -> 0x0045 }
        r1 = new java.io.ByteArrayInputStream;	 Catch:{ IOException -> 0x0030, InvocationTargetException -> 0x0037, IllegalAccessException -> 0x003e, all -> 0x0045 }
        r1.<init>(r5, r6, r7);	 Catch:{ IOException -> 0x0030, InvocationTargetException -> 0x0037, IllegalAccessException -> 0x003e, all -> 0x0045 }
        r0 = r3.getOutputStream();	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        copy(r1, r0);	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        r0 = f2836b;	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        r0 = r0.invoke(r3, r4);	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        r0 = (java.io.FileDescriptor) r0;	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        r0 = android.os.ParcelFileDescriptor.dup(r0);	 Catch:{ IOException -> 0x0057, InvocationTargetException -> 0x0052, IllegalAccessException -> 0x004d, all -> 0x004b }
        r3.close();	 Catch:{ IOException -> 0x005a, InvocationTargetException -> 0x0055, IllegalAccessException -> 0x0050, all -> 0x004b }
        closeQuietly(r1);
    L_0x002f:
        return r0;
    L_0x0030:
        r0 = move-exception;
        r1 = r2;
        r0 = r2;
    L_0x0033:
        closeQuietly(r1);
        goto L_0x002f;
    L_0x0037:
        r0 = move-exception;
        r1 = r2;
        r0 = r2;
    L_0x003a:
        closeQuietly(r1);
        goto L_0x002f;
    L_0x003e:
        r0 = move-exception;
        r1 = r2;
        r0 = r2;
    L_0x0041:
        closeQuietly(r1);
        goto L_0x002f;
    L_0x0045:
        r0 = move-exception;
        r1 = r2;
    L_0x0047:
        closeQuietly(r1);
        throw r0;
    L_0x004b:
        r0 = move-exception;
        goto L_0x0047;
    L_0x004d:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0041;
    L_0x0050:
        r2 = move-exception;
        goto L_0x0041;
    L_0x0052:
        r0 = move-exception;
        r0 = r2;
        goto L_0x003a;
    L_0x0055:
        r2 = move-exception;
        goto L_0x003a;
    L_0x0057:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0033;
    L_0x005a:
        r2 = move-exception;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.priv.FileDescriptorHelper.fromByteArray(byte[], int, int):android.os.ParcelFileDescriptor");
    }

    public static ParcelFileDescriptor fromString(String str) {
        return str == null ? null : fromByteArray(str.getBytes());
    }

    public static byte[] readFile(File file) {
        Closeable channel;
        Exception e;
        Throwable th;
        byte[] bArr = null;
        int length = (int) file.length();
        try {
            channel = new FileInputStream(file).getChannel();
            try {
                ByteBuffer allocate = ByteBuffer.allocate(length);
                channel.read(allocate);
                allocate.flip();
                bArr = allocate.array();
                if (channel != null) {
                    closeQuietly(channel);
                }
            } catch (IOException e2) {
                e = e2;
                try {
                    DroiLog.m2869e(f2837c, e);
                    if (channel != null) {
                        closeQuietly(channel);
                    }
                    return bArr;
                } catch (Throwable th2) {
                    th = th2;
                    if (channel != null) {
                        closeQuietly(channel);
                    }
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            channel = bArr;
            DroiLog.m2869e(f2837c, e);
            if (channel != null) {
                closeQuietly(channel);
            }
            return bArr;
        } catch (Throwable th3) {
            channel = bArr;
            th = th3;
            if (channel != null) {
                closeQuietly(channel);
            }
            throw th;
        }
        return bArr;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int toBytes(android.os.ParcelFileDescriptor r7, byte[] r8, com.droi.sdk.DroiError r9) {
        /*
        r0 = 0;
        r1 = r7.getFileDescriptor();	 Catch:{ Exception -> 0x0060 }
        r2 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0060 }
        r2.<init>(r1);	 Catch:{ Exception -> 0x0060 }
        r3 = r8.length;	 Catch:{ Exception -> 0x0060 }
        r1 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r4 = new byte[r1];	 Catch:{ all -> 0x0056 }
        r1 = r0;
    L_0x0010:
        r5 = 0;
        r6 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r5 = r2.read(r4, r5, r6);	 Catch:{ all -> 0x0056 }
        if (r5 <= 0) goto L_0x0034;
    L_0x0019:
        r6 = r5 + r1;
        if (r6 <= r3) goto L_0x002e;
    L_0x001d:
        if (r9 == 0) goto L_0x0025;
    L_0x001f:
        r1 = 1070015; // 0x1053bf float:1.49941E-39 double:5.286577E-318;
        r9.setCode(r1);	 Catch:{ all -> 0x0056 }
    L_0x0025:
        closeQuietly(r2);	 Catch:{ Exception -> 0x0060 }
        if (r7 == 0) goto L_0x002d;
    L_0x002a:
        r7.close();	 Catch:{ Exception -> 0x0060 }
    L_0x002d:
        return r0;
    L_0x002e:
        r6 = 0;
        java.lang.System.arraycopy(r4, r6, r8, r1, r5);	 Catch:{ all -> 0x0056 }
        r1 = r1 + r5;
        goto L_0x0010;
    L_0x0034:
        r3 = "FILE_DESCRIPTOR_HELPER";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0056 }
        r4.<init>();	 Catch:{ all -> 0x0056 }
        r5 = "down recv: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0056 }
        r4 = r4.append(r1);	 Catch:{ all -> 0x0056 }
        r4 = r4.toString();	 Catch:{ all -> 0x0056 }
        com.droi.sdk.internal.DroiLog.m2868d(r3, r4);	 Catch:{ all -> 0x0056 }
        closeQuietly(r2);	 Catch:{ Exception -> 0x0060 }
        if (r7 == 0) goto L_0x0054;
    L_0x0051:
        r7.close();	 Catch:{ Exception -> 0x0060 }
    L_0x0054:
        r0 = r1;
        goto L_0x002d;
    L_0x0056:
        r1 = move-exception;
        closeQuietly(r2);	 Catch:{ Exception -> 0x0060 }
        if (r7 == 0) goto L_0x005f;
    L_0x005c:
        r7.close();	 Catch:{ Exception -> 0x0060 }
    L_0x005f:
        throw r1;	 Catch:{ Exception -> 0x0060 }
    L_0x0060:
        r1 = move-exception;
        r2 = "FILE_DESCRIPTOR_HELPER";
        com.droi.sdk.internal.DroiLog.m2873w(r2, r1);
        if (r9 == 0) goto L_0x002d;
    L_0x0068:
        r2 = 1070001; // 0x1053b1 float:1.499391E-39 double:5.286507E-318;
        r9.setCode(r2);
        r1 = r1.toString();
        r9.setAppendedMessage(r1);
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.priv.FileDescriptorHelper.toBytes(android.os.ParcelFileDescriptor, byte[], com.droi.sdk.DroiError):int");
    }

    public static byte[] toBytes(ParcelFileDescriptor parcelFileDescriptor) {
        Closeable fileInputStream;
        Closeable byteArrayOutputStream;
        try {
            fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            byteArrayOutputStream = new ByteArrayOutputStream();
            copy(fileInputStream, byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            closeQuietly(fileInputStream);
            closeQuietly(byteArrayOutputStream);
            if (parcelFileDescriptor == null) {
                return toByteArray;
            }
            parcelFileDescriptor.close();
            return toByteArray;
        } catch (Exception e) {
            DroiLog.m2873w(f2837c, e);
            return null;
        } catch (Throwable th) {
            closeQuietly(fileInputStream);
            closeQuietly(byteArrayOutputStream);
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        }
    }

    public static String toString(ParcelFileDescriptor parcelFileDescriptor) {
        Closeable fileInputStream;
        Closeable stringWriter;
        Closeable inputStreamReader;
        try {
            fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            stringWriter = new StringWriter();
            inputStreamReader = new InputStreamReader(fileInputStream);
            copyLarge(inputStreamReader, stringWriter);
            String stringWriter2 = stringWriter.toString();
            closeQuietly(inputStreamReader);
            closeQuietly(stringWriter);
            closeQuietly(fileInputStream);
            if (parcelFileDescriptor == null) {
                return stringWriter2;
            }
            parcelFileDescriptor.close();
            return stringWriter2;
        } catch (Exception e) {
            return null;
        } catch (Throwable th) {
            closeQuietly(inputStreamReader);
            closeQuietly(stringWriter);
            closeQuietly(fileInputStream);
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        }
    }
}
