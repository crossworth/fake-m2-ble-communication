package com.tyd.aidlservice.internal;

import android.util.Base64;
import android.util.Log;
import com.droi.btlib.connection.MapConstants;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public class Tutil {
    private static final String LOG_TAG = "Tutil";

    static {
        try {
            System.loadLibrary("TydEngine_mbed_jni");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Load TUtil.so fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "Load TUtil.so fail." + e2);
        }
    }

    public static int Adler32(byte[] bArr, byte[] bArr2) {
        try {
            return nAdler32(bArr, bArr2);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int AesDecrypt(byte[] bArr, byte[] bArr2, int i) {
        try {
            return nAesDecrypt(bArr, bArr2, i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int AesDecryptByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i) {
        try {
            return nAesDecryptByteBuffer(byteBuffer, byteBuffer2, i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int AesEncrypt(byte[] bArr, byte[] bArr2, int i) {
        try {
            return nAesEncrypt(bArr, bArr2, i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int AesEncryptByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i) {
        try {
            return nAesEncryptByteBuffer(byteBuffer, byteBuffer2, i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int AesEncryptMemSize(int i) {
        try {
            return nAesEncryptMemSize(i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static byte[] Base64Decode(String str) {
        return Base64.decode(str, 0);
    }

    public static String Base64Encode(byte[] bArr) {
        return Base64.encodeToString(bArr, 0);
    }

    public static int ChkKeyValidationCorrect(int i, int i2, byte[] bArr) {
        try {
            return nChkKeyValidationCorrect(i, i2, bArr);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int ChkKeyValidationFailed(int i, int i2, int i3, byte[] bArr) {
        try {
            return nChkKeyValidationFailed(i, i2, i3, bArr);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] DecapsulateSecUDP(byte[] r8, int r9, java.util.concurrent.atomic.AtomicInteger r10) {
        /*
        r7 = 2;
        r6 = -99;
        r0 = 0;
        r1 = 16;
        r2 = 6;
        r2 = le16toh(r8, r2);	 Catch:{ IOException -> 0x0052 }
        r3 = 8;
        r3 = le32toh(r8, r3);	 Catch:{ IOException -> 0x0052 }
        r4 = 10;
        r4 = le16toh(r8, r4);	 Catch:{ IOException -> 0x0052 }
        r10.set(r4);	 Catch:{ IOException -> 0x0052 }
        r4 = 2;
        r4 = r8[r4];	 Catch:{ IOException -> 0x0052 }
        r5 = -95;
        if (r4 != r5) goto L_0x003a;
    L_0x0021:
        r4 = 3;
        r4 = r8[r4];	 Catch:{ IOException -> 0x0052 }
        r5 = 1;
        if (r4 != r5) goto L_0x003a;
    L_0x0027:
        r4 = 4;
        r4 = r8[r4];	 Catch:{ IOException -> 0x0052 }
        r5 = GetKlKeyType();	 Catch:{ IOException -> 0x0052 }
        if (r4 != r5) goto L_0x003a;
    L_0x0030:
        r4 = r9 - r1;
        if (r4 != r2) goto L_0x003a;
    L_0x0034:
        r2 = r10.get();	 Catch:{ IOException -> 0x0052 }
        if (r2 == 0) goto L_0x0040;
    L_0x003a:
        r1 = -99;
        r10.set(r1);	 Catch:{ IOException -> 0x0052 }
    L_0x003f:
        return r0;
    L_0x0040:
        r1 = java.util.Arrays.copyOfRange(r8, r1, r9);	 Catch:{ IOException -> 0x0052 }
        r2 = 5;
        r2 = r8[r2];	 Catch:{ IOException -> 0x0052 }
        r2 = r2 & 3;
        switch(r2) {
            case 1: goto L_0x0077;
            case 2: goto L_0x005a;
            case 3: goto L_0x005a;
            default: goto L_0x004c;
        };	 Catch:{ IOException -> 0x0052 }
    L_0x004c:
        r1 = -99;
        r10.set(r1);	 Catch:{ IOException -> 0x0052 }
        goto L_0x003f;
    L_0x0052:
        r1 = move-exception;
        r1.printStackTrace();
        r10.set(r6);
        goto L_0x003f;
    L_0x005a:
        r4 = r1.length;	 Catch:{ IOException -> 0x0052 }
        r4 = new byte[r4];	 Catch:{ IOException -> 0x0052 }
        r5 = GetKlKeyType();	 Catch:{ IOException -> 0x0052 }
        r1 = AesDecrypt(r1, r4, r5);	 Catch:{ IOException -> 0x0052 }
        if (r1 < 0) goto L_0x003f;
    L_0x0067:
        r5 = 0;
        r1 = java.util.Arrays.copyOfRange(r4, r5, r1);	 Catch:{ IOException -> 0x0052 }
        if (r2 != r7) goto L_0x0077;
    L_0x006e:
        r2 = r1.length;	 Catch:{ IOException -> 0x0052 }
        if (r2 == r3) goto L_0x007c;
    L_0x0071:
        r1 = -99;
        r10.set(r1);	 Catch:{ IOException -> 0x0052 }
        goto L_0x003f;
    L_0x0077:
        r1 = decompress(r1);	 Catch:{ IOException -> 0x0052 }
        goto L_0x006e;
    L_0x007c:
        r0 = r1;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tyd.aidlservice.internal.Tutil.DecapsulateSecUDP(byte[], int, java.util.concurrent.atomic.AtomicInteger):byte[]");
    }

    public static void DumpLongtermKey() {
        try {
            nDumpLongtermKey();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static byte[] EncapsulateSecUDP(byte[] bArr, boolean z, boolean z2) {
        try {
            Object compressDeflater;
            Object obj;
            Object obj2 = new byte[16];
            Arrays.fill(obj2, (byte) 0);
            if (z2) {
                compressDeflater = compressDeflater(bArr);
                obj2[5] = (byte) (obj2[5] | 1);
            } else {
                obj2[5] = (byte) (obj2[5] & MapConstants.MAX_SUBJECT_LEN);
                compressDeflater = bArr;
            }
            if (z) {
                byte[] bArr2 = new byte[AesEncryptMemSize(compressDeflater.length)];
                int AesEncrypt = AesEncrypt(compressDeflater, bArr2, GetKlKeyType());
                if (AesEncrypt < 0) {
                    return null;
                }
                compressDeflater = Arrays.copyOfRange(bArr2, 0, AesEncrypt);
                Log.i(LOG_TAG, "GetKlKeyType():" + GetKlKeyType());
                obj2[4] = (byte) GetKlKeyType();
                Log.i(LOG_TAG, "udpH[4]:" + obj2[4]);
                obj2[5] = (byte) (obj2[5] | 2);
                obj = compressDeflater;
            } else {
                obj2[5] = (byte) (obj2[5] & 253);
                obj = compressDeflater;
            }
            if (obj.length <= 0) {
                return null;
            }
            obj2[2] = Constants.MAGIC_DROIVC;
            obj2[3] = (byte) 1;
            htole16((short) obj.length, obj2, 6);
            htole16((short) bArr.length, obj2, 8);
            htole16((short) GetKlKeyVersion(), obj2, 10);
            htole32((int) GetKlKeyID(), obj2, 12);
            compressDeflater = new byte[(obj2.length + obj.length)];
            System.arraycopy(obj2, 0, compressDeflater, 0, obj2.length);
            System.arraycopy(obj, 0, compressDeflater, obj2.length, obj.length);
            return compressDeflater;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] GenKeyValidation(int i) {
        try {
            return nGenKeyValidation(i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return null;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return null;
        }
    }

    public static long GetKlKeyID() {
        try {
            return nGetKlKeyID();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static boolean GetKlKeyIsValid() {
        try {
            return nGetKlKeyIsValid();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return false;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return false;
        }
    }

    public static int GetKlKeyType() {
        try {
            int nGetKlKeyType = nGetKlKeyType();
            return nGetKlKeyType == 0 ? 1 : nGetKlKeyType;
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 1;
        }
    }

    public static long GetKlKeyUID_l() {
        try {
            return nGetKlKeyUID_l();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static long GetKlKeyUID_u() {
        try {
            return nGetKlKeyUID_u();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int GetKlKeyVersion() {
        try {
            return nGetKlKeyVersion();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int GetLibEngineVersion() {
        try {
            return nGetLibEngineVersion();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int GetLibJNIVersion() {
        try {
            return nGetLibJNIVersion();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int HmacMd5(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return nHmacMd5(bArr, bArr2, bArr3);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int KlKeyAlloc(byte[] bArr) {
        try {
            return nKlKeyAlloc(bArr);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static void KlKeyFree() {
        try {
            nKlKeyFree();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static byte[] KlKeyGet() {
        try {
            return nKlKeyGet();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return null;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return null;
        }
    }

    public static int RsaCiphertextSize() {
        try {
            return nRsaCiphertextSize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int RsaDecrypt(byte[] bArr, byte[] bArr2) {
        try {
            return nRsaDecrypt(bArr, bArr2);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int RsaEncrypt(byte[] bArr, byte[] bArr2) {
        try {
            return nRsaEncrypt(bArr, bArr2);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return -1;
        }
    }

    public static int RsaKeyVersion() {
        try {
            return nRsaKeyVersion();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static int RsaPlaintextMaxSize() {
        try {
            return nRsaPlaintextMaxSize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
            return 0;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
            return 0;
        }
    }

    public static void SetDebug(boolean z) {
        try {
            nSetDebug(z);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static void SetEncType(int i) {
        try {
            nSetEncType(i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static void SetFakeKlKey(long j, long j2) {
        try {
            nSetFakeKlKey(j, j2);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static void SetKlKeyInvalid() {
        try {
            nSetKlKeyInvalid();
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static String bytesToHexString(byte[] bArr) {
        char[] toCharArray = "0123456789ABCDEF".toCharArray();
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            cArr[i * 2] = toCharArray[i2 >>> 4];
            cArr[(i * 2) + 1] = toCharArray[i2 & 15];
        }
        return new String(cArr);
    }

    public static byte[] compress(byte[] bArr) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        GZIPOutputStream c14721 = new GZIPOutputStream(byteArrayOutputStream) {
        };
        c14721.write(bArr);
        c14721.close();
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return toByteArray;
    }

    public static byte[] compressDeflater(byte[] bArr) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        new Deflater().setLevel(1);
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
        deflaterOutputStream.write(bArr);
        deflaterOutputStream.close();
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return toByteArray;
    }

    public static byte[] compressString(String str) throws IOException {
        return compress(str.getBytes());
    }

    public static byte[] decompress(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[256];
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read >= 0) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                gZIPInputStream.close();
                byteArrayInputStream.close();
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return toByteArray;
            }
        }
    }

    public static byte[] decompressInflater(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[256];
        while (true) {
            int read = inflaterInputStream.read(bArr2);
            if (read >= 0) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                inflaterInputStream.close();
                byteArrayInputStream.close();
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return toByteArray;
            }
        }
    }

    public static String decompressString(byte[] bArr) throws IOException {
        return new String(decompress(bArr));
    }

    public static void dumpKlKeyInfo() {
        Log.i(LOG_TAG, "Tutil.GetKlKeyIsValid=" + GetKlKeyIsValid());
        Log.i(LOG_TAG, "Tutil.GetKlKeyID=" + GetKlKeyID());
        Log.i(LOG_TAG, "Tutil.GetKlKeyType=" + GetKlKeyType());
        Log.i(LOG_TAG, "Tutil.GetKlKeyVersion=" + GetKlKeyVersion());
        Log.i(LOG_TAG, "Tutil.GetKlKeyUID_u=" + GetKlKeyUID_u());
        Log.i(LOG_TAG, "Tutil.GetKlKeyUID_l=" + GetKlKeyUID_l());
    }

    public static byte[] hexStringToBytes(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static void htole16(short s, byte[] bArr, int i) {
        bArr[i] = (byte) (s & 255);
        bArr[i + 1] = (byte) ((s >> 8) & 255);
    }

    public static void htole32(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i & 255);
        bArr[i2 + 1] = (byte) ((i >> 8) & 255);
        bArr[i2 + 2] = (byte) ((i >> 16) & 255);
        bArr[i2 + 3] = (byte) ((i >> 24) & 255);
    }

    public static short le16toh(byte[] bArr, int i) {
        return (short) (((short) (bArr[i] & 255)) + ((short) ((bArr[i + 1] & 255) << 8)));
    }

    public static int le32toh(byte[] bArr, int i) {
        return (((bArr[i] & 255) + ((bArr[i + 1] & 255) << 8)) + ((bArr[i + 2] & 255) << 16)) + ((bArr[i + 3] & 255) << 24);
    }

    private static native int nAdler32(byte[] bArr, byte[] bArr2);

    private static native int nAesDecrypt(byte[] bArr, byte[] bArr2, int i);

    private static native int nAesDecryptByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i);

    private static native int nAesEncrypt(byte[] bArr, byte[] bArr2, int i);

    private static native int nAesEncryptByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i);

    private static native int nAesEncryptMemSize(int i);

    private static native int nChkKeyValidationCorrect(int i, int i2, byte[] bArr);

    private static native int nChkKeyValidationFailed(int i, int i2, int i3, byte[] bArr);

    private static native void nDumpLongtermKey();

    private static native byte[] nGenKeyValidation(int i);

    private static native long nGetKlKeyID();

    private static native boolean nGetKlKeyIsValid();

    private static native int nGetKlKeyType();

    private static native long nGetKlKeyUID_l();

    private static native long nGetKlKeyUID_u();

    private static native int nGetKlKeyVersion();

    private static native int nGetLibEngineVersion();

    private static native int nGetLibJNIVersion();

    private static native int nHmacMd5(byte[] bArr, byte[] bArr2, byte[] bArr3);

    private static native int nKlKeyAlloc(byte[] bArr);

    private static native void nKlKeyFree();

    private static native byte[] nKlKeyGet();

    private static native int nRsaCiphertextSize();

    private static native int nRsaDecrypt(byte[] bArr, byte[] bArr2);

    private static native int nRsaEncrypt(byte[] bArr, byte[] bArr2);

    private static native int nRsaKeyVersion();

    private static native int nRsaPlaintextMaxSize();

    private static native void nSetDebug(boolean z);

    private static native void nSetEncType(int i);

    private static native void nSetFakeKlKey(long j, long j2);

    private static native void nSetKlKeyInvalid();

    private static native void nSetKlKeyUID_l(long j);

    private static native void nSetKlKeyUID_u(long j);

    public static void selfTesting() throws Exception {
        String str = "Hello World!!!";
        byte[] stringToBytesWithTermination = stringToBytesWithTermination(str);
        Log.i(LOG_TAG, "plainText=" + bytesToHexString(stringToBytesWithTermination));
        SetEncType(1);
        Log.i(LOG_TAG, "AesEncryptMemSize=" + AesEncryptMemSize(stringToBytesWithTermination.length));
        byte[] bArr = new byte[AesEncryptMemSize(stringToBytesWithTermination.length)];
        int AesEncrypt = AesEncrypt(stringToBytesWithTermination, bArr, 1);
        Log.e(LOG_TAG, "AesEncrypt return code=" + AesEncrypt);
        if (AesEncrypt >= 0) {
            Log.i(LOG_TAG, "AES encryption block=" + bytesToHexString(bArr) + ", length=" + AesEncrypt);
            bArr = Arrays.copyOf(bArr, AesEncrypt);
            byte[] bArr2 = new byte[AesEncrypt];
            Log.i(LOG_TAG, "cipherText=" + bytesToHexString(bArr));
            int AesDecrypt = AesDecrypt(bArr, bArr2, 1);
            Log.e(LOG_TAG, "AesDecrypt return code=" + AesDecrypt);
            if (AesDecrypt < 0 || AesDecrypt != str.length() + 1) {
                Log.e(LOG_TAG, "AES decryption failed");
                throw new Exception("AES decryption failed");
            }
            Log.i(LOG_TAG, "AES decryption block=" + bytesToHexString(bArr2) + ", length=" + AesDecrypt);
            Log.i(LOG_TAG, "RsaPlaintextMaxSize=" + RsaPlaintextMaxSize());
            Log.i(LOG_TAG, "RsaCiphertextSize=" + RsaCiphertextSize());
            byte[] bArr3 = new byte[RsaCiphertextSize()];
            AesDecrypt = RsaEncrypt(stringToBytesWithTermination, bArr3);
            Log.i(LOG_TAG, "RsaEncryptn return code=" + AesDecrypt);
            if (AesDecrypt >= 0) {
                Log.i(LOG_TAG, "RSA encryption block=" + bytesToHexString(bArr3) + ", length=" + AesDecrypt);
                Log.i(LOG_TAG, "cipherText=" + bytesToHexString(Arrays.copyOf(bArr3, AesDecrypt)));
                str = "md5key";
                bArr = new byte[16];
                int HmacMd5 = HmacMd5(str.getBytes(), stringToBytesWithTermination, bArr);
                if (HmacMd5 == 0) {
                    Log.i(LOG_TAG, "key=" + bytesToHexString(str.getBytes()));
                    Log.i(LOG_TAG, "digest=" + bytesToHexString(bArr));
                    str = "More text test vectors to stuff up EBCDIC machines :-)";
                    stringToBytesWithTermination = new byte[4];
                    AesDecrypt = Adler32(str.getBytes(), stringToBytesWithTermination);
                    if (AesDecrypt == 0) {
                        Log.i(LOG_TAG, "adlerData=" + str);
                        Log.i(LOG_TAG, "adler=" + bytesToHexString(stringToBytesWithTermination));
                        try {
                            str = "{\"body\":\"{\"tInfo\":{\"sWidth\":800,\"sHeight\":480,\"ramSize\":0,\"lac\":0,\"netType\":0,\"appId\":\"tyd000\",\"apkVer\":0,\"romSize\":0,\"extraSize\":0,\"root\":0,\"networkSystem\":0},\"key\":\"卓易市场\",\"start\":0,\"pSize\":10,\"topicId\":0,\"fixed\":0,\"silentVersion\":0,\"resId\":0,\"version\":0,\"sWidth\":0,\"sHeight\":0,\"assId\":0,\"apkVer\":0,\"userId\":0,\"gameId\":0,\"source\":0,\"recPId\":0,\"parentId\":0,\"reqModel\":0,\"stars\":0}\",\"head\":\"{\"mcd\":101009,\"ver\":1,\"lsb\":-7844788937974452830,\"type\":1,\"msb\":3199154739660997084}\"}";
                            stringToBytesWithTermination = compressString(str);
                            Log.i(LOG_TAG, "compressed String=" + bytesToHexString(stringToBytesWithTermination));
                            Log.i(LOG_TAG, "decompressed String=" + decompressString(stringToBytesWithTermination));
                            bArr3 = compress(str.getBytes());
                            Log.i(LOG_TAG, "compressed=" + bytesToHexString(bArr3));
                            Log.i(LOG_TAG, "decompressed=" + bytesToHexString(decompress(bArr3)));
                            Log.i(LOG_TAG, "String=" + new String(decompress(bArr3), "UTF-8"));
                            Log.i(LOG_TAG, "binary:");
                            bArr3 = compress(bArr3);
                            Log.i(LOG_TAG, "compressed=" + bytesToHexString(bArr3));
                            Log.i(LOG_TAG, "decompressed=" + bytesToHexString(decompress(bArr3)));
                            Log.i(LOG_TAG, "Deflater:");
                            bArr3 = compressDeflater("{\"head\":\"{\"ver\":1,\"lsb\":-6606404711030641436,\"type\":1,\"mcd\":101010,\"msb\":-6449446549219160387}\",\"body\":\"{\"tInfo\":{\"ramSize\":979088,\"hman\":\"alps\",\"appId\":\"online\",\"osVer\":\"4.4.2\",\"chId\":\"onlweb002\",\"cpu\":\"mt6582\",\"mac\":\"c8:ae:9c:a7:49:65\",\"sWidth\":720,\"reserved\":\"{\\\"marketSignal\\\":2}\",\"lac\":0,\"romSize\":908,\"pName\":\"com.zhuoyi.market\",\"netType\":3,\"sdkApiVer\":19,\"htype\":\"k502\",\"uuid\":\"\",\"apkVer\":119,\"sHeight\":1184,\"apkVerName\":\"10.6.5\"}}\"}".getBytes());
                            Log.i(LOG_TAG, "compressed=" + bytesToHexString(bArr3));
                            Log.i(LOG_TAG, "decompressed=" + bytesToHexString(decompressInflater(bArr3)));
                            Log.i(LOG_TAG, "String=" + new String(decompressInflater(bArr3), "UTF-8"));
                            String str2 = new String("78014550bb72c32010fc97ab1d0d580809ba747193c699a4a1c11289180989d1c319dba37fcf011a67688ebdddbddb7b406b7403121e0aae665220e941413f5fb07ae19c7046584929c9b1a22ce7d85c6edeec3c5737a122e161c7ed32c60463bc60e24805e524afca0d0e70199b5b1ab49c86ef11853873d2ee6cefc14f94825415bab44e0ff857a07b3f2b40447b7f0a83148c436f07642338ce9f715f052c63d9316275fbcffb35174276d8af51ed165e540972ba8e505d496da4a8a52e25139217d167feb2cdd222a13c865c9399cd74356985875298544f9d59cef667d07df8cbe316857db48d9af1198c8454fe5dbb1053413dbaecdeaee3cd66c9262a07b37ca4c3e6c89e9beed5db14900a04dafdea0aba624fb5ae366d14f5da773b3df2e737637fda0507525a313448fde71294643cc3b0db06db1f3ade98b3");
                            Log.i(LOG_TAG, "str4=\t" + str2);
                            stringToBytesWithTermination = hexStringToBytes(str2);
                            Log.i(LOG_TAG, "b2  =\t" + bytesToHexString(stringToBytesWithTermination));
                            Log.i(LOG_TAG, "decompressed=" + bytesToHexString(decompressInflater(stringToBytesWithTermination)));
                            Log.i(LOG_TAG, "String=" + new String(decompressInflater(bArr3), "UTF-8"));
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Exception("GZIP/GUNZIP failed");
                        }
                    }
                    Log.e(LOG_TAG, "adler32 failed, len = " + AesDecrypt);
                    throw new Exception("adler32 failed, len = " + AesDecrypt);
                }
                Log.e(LOG_TAG, "HMAC failed, len = " + HmacMd5);
                throw new Exception("HMAC failed, len" + HmacMd5);
            }
            Log.e(LOG_TAG, "RSA encryption failed");
            throw new Exception("RSA encryption failed");
        }
        Log.e(LOG_TAG, "AES encryption failed");
        throw new Exception("AES encryption failed");
    }

    public static void selftAesKeyStorageTest() {
        dumpKlKeyInfo();
        Object KlKeyGet = KlKeyGet();
        if (KlKeyGet == null) {
            Log.e(LOG_TAG, "storageData==null");
            return;
        }
        KlKeyFree();
        int KlKeyAlloc = KlKeyAlloc(KlKeyGet);
        if (KlKeyAlloc != 0) {
            Log.e(LOG_TAG, "Tutil.KlKeyAlloc(storageData) != 0, ret = " + KlKeyAlloc);
            return;
        }
        Object KlKeyGet2 = KlKeyGet();
        if (KlKeyGet2 == null) {
            Log.e(LOG_TAG, "storageData2==null");
        } else if (!KlKeyGet.equals(KlKeyGet2)) {
            Log.i(LOG_TAG, "storageData.equals(storageData2)=" + KlKeyGet.equals(KlKeyGet2));
        }
    }

    public static void setKlKeyUID_l(long j) {
        try {
            nSetKlKeyUID_l(j);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static void setKlKeyUID_u(long j) {
        try {
            nSetKlKeyUID_u(j);
        } catch (Exception e) {
            Log.e(LOG_TAG, "call native function fail." + e);
        } catch (UnsatisfiedLinkError e2) {
            Log.e(LOG_TAG, "call native function fail." + e2);
        }
    }

    public static byte[] stringToBytesWithTermination(String str) {
        CharsetEncoder newEncoder = Charset.forName("ISO-8859-1").newEncoder();
        int length = str.length();
        byte[] bArr = new byte[(str.length() + 1)];
        newEncoder.encode(CharBuffer.wrap(str), ByteBuffer.wrap(bArr), true);
        bArr[length] = (byte) 0;
        return bArr;
    }

    public static void writeToFile(String str, byte[] bArr) {
        BufferedOutputStream bufferedOutputStream;
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        File file = new File(str);
        Log.i(LOG_TAG, "tmp.getAbsolutePath()=" + file.getAbsolutePath());
        if (file.exists()) {
            Log.i(LOG_TAG, "file exits, overwrite");
        }
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            try {
                bufferedOutputStream.write(bArr);
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e4) {
                e2 = e4;
                try {
                    e2.printStackTrace();
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.flush();
                            bufferedOutputStream.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream2 = bufferedOutputStream;
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.flush();
                            bufferedOutputStream2.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e32 = e6;
                bufferedOutputStream2 = bufferedOutputStream;
                try {
                    e32.printStackTrace();
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.flush();
                            bufferedOutputStream2.close();
                        } catch (IOException e322) {
                            e322.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedOutputStream2 != null) {
                        bufferedOutputStream2.flush();
                        bufferedOutputStream2.close();
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException e7) {
            e2 = e7;
            bufferedOutputStream = null;
            e2.printStackTrace();
            if (bufferedOutputStream != null) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        } catch (IOException e8) {
            e322 = e8;
            e322.printStackTrace();
            if (bufferedOutputStream2 != null) {
                bufferedOutputStream2.flush();
                bufferedOutputStream2.close();
            }
        }
    }
}
