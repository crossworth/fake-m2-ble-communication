package com.tencent.utils;

import com.zhuoyou.plugin.bluetooth.data.BMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Properties;
import java.util.zip.ZipException;

/* compiled from: ProGuard */
public final class ApkExternalInfoTool {
    public static final String CHANNELID = "channelNo";
    private static final ZipLong f2960a = new ZipLong(101010256);
    private static final ZipShort f2961b = new ZipShort(38651);

    /* compiled from: ProGuard */
    private static class ApkExternalInfo {
        Properties f2958a;
        byte[] f2959b;

        private ApkExternalInfo() {
            this.f2958a = new Properties();
        }

        void m2806a(byte[] bArr) throws IOException {
            if (bArr != null) {
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                int length = ApkExternalInfoTool.f2961b.getBytes().length;
                byte[] bArr2 = new byte[length];
                wrap.get(bArr2);
                if (!ApkExternalInfoTool.f2961b.equals(new ZipShort(bArr2))) {
                    throw new ProtocolException("unknow protocl [" + Arrays.toString(bArr) + "]");
                } else if (bArr.length - length > 2) {
                    bArr2 = new byte[2];
                    wrap.get(bArr2);
                    int value = new ZipShort(bArr2).getValue();
                    if ((bArr.length - length) - 2 >= value) {
                        byte[] bArr3 = new byte[value];
                        wrap.get(bArr3);
                        this.f2958a.load(new ByteArrayInputStream(bArr3));
                        length = ((bArr.length - length) - value) - 2;
                        if (length > 0) {
                            this.f2959b = new byte[length];
                            wrap.get(this.f2959b);
                        }
                    }
                }
            }
        }

        byte[] m2807a() throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(ApkExternalInfoTool.f2961b.getBytes());
            String str = "";
            for (Object next : this.f2958a.keySet()) {
                str = str + next + "=" + this.f2958a.getProperty((String) next) + BMessage.CRLF;
            }
            byte[] bytes = str.getBytes();
            byteArrayOutputStream.write(new ZipShort(bytes.length).getBytes());
            byteArrayOutputStream.write(bytes);
            if (this.f2959b != null) {
                byteArrayOutputStream.write(this.f2959b);
            }
            return byteArrayOutputStream.toByteArray();
        }

        public String toString() {
            return "ApkExternalInfo [p=" + this.f2958a + ", otherData=" + Arrays.toString(this.f2959b) + "]";
        }
    }

    public static String read(File file, String str) throws IOException {
        Throwable th;
        String str2 = null;
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            try {
                byte[] a = m2809a(randomAccessFile);
                if (a != null) {
                    ApkExternalInfo apkExternalInfo = new ApkExternalInfo();
                    apkExternalInfo.m2806a(a);
                    str2 = apkExternalInfo.f2958a.getProperty(str);
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } else if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                return str2;
            } catch (Throwable th2) {
                th = th2;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            randomAccessFile = null;
            th = th4;
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            throw th;
        }
    }

    public static String readChannelId(File file) throws IOException {
        return read(file, CHANNELID);
    }

    private static byte[] m2809a(RandomAccessFile randomAccessFile) throws IOException {
        int i = 1;
        long length = randomAccessFile.length() - 22;
        randomAccessFile.seek(length);
        byte[] bytes = f2960a.getBytes();
        byte read = randomAccessFile.read();
        while (read != (byte) -1) {
            if (read == bytes[0] && randomAccessFile.read() == bytes[1] && randomAccessFile.read() == bytes[2] && randomAccessFile.read() == bytes[3]) {
                break;
            }
            length--;
            randomAccessFile.seek(length);
            read = randomAccessFile.read();
        }
        i = 0;
        if (i == 0) {
            throw new ZipException("archive is not a ZIP archive");
        }
        randomAccessFile.seek((16 + length) + 4);
        byte[] bArr = new byte[2];
        randomAccessFile.readFully(bArr);
        i = new ZipShort(bArr).getValue();
        if (i == 0) {
            return null;
        }
        bArr = new byte[i];
        randomAccessFile.read(bArr);
        return bArr;
    }

    public static void updateExternalInfo(File file, String str, String str2) throws IOException {
        Throwable th;
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                byte[] a = m2809a(randomAccessFile);
                ApkExternalInfo apkExternalInfo = new ApkExternalInfo();
                apkExternalInfo.m2806a(a);
                apkExternalInfo.f2958a.setProperty(str, str2);
                randomAccessFile.seek((randomAccessFile.length() - ((long) (a == null ? 0 : a.length))) - 2);
                a = apkExternalInfo.m2807a();
                randomAccessFile.write(new ZipShort(a.length).getBytes());
                randomAccessFile.write(a);
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile = null;
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            throw th;
        }
    }
}
