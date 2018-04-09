package com.zhuoyi.system.network.serializer;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;

class ByteUtil {
    ByteUtil() {
    }

    protected static void putShort(byte[] b, short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    protected static short getShort(byte[] b, int index) {
        return (short) ((b[index + 1] << 8) | (b[index + 0] & 255));
    }

    protected static void putInt(byte[] bb, int x, int index) {
        bb[index] = (byte) (x & 255);
        bb[index + 1] = (byte) ((x >> 8) & 255);
        bb[index + 2] = (byte) ((x >> 16) & 255);
        bb[index + 3] = (byte) ((x >> 24) & 255);
    }

    protected static int getInt(byte[] bb, int index) {
        return ((((bb[index + 3] & 255) << 24) | ((bb[index + 2] & 255) << 16)) | ((bb[index + 1] & 255) << 8)) | ((bb[index + 0] & 255) << 0);
    }

    protected static void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) ((int) (x >> 56));
        bb[index + 6] = (byte) ((int) (x >> 48));
        bb[index + 5] = (byte) ((int) (x >> 40));
        bb[index + 4] = (byte) ((int) (x >> 32));
        bb[index + 3] = (byte) ((int) (x >> 24));
        bb[index + 2] = (byte) ((int) (x >> 16));
        bb[index + 1] = (byte) ((int) (x >> 8));
        bb[index + 0] = (byte) ((int) (x >> null));
    }

    protected static long getLong(byte[] bb, int index) {
        return ((((((((((long) bb[index + 7]) & 255) << 56) | ((((long) bb[index + 6]) & 255) << 48)) | ((((long) bb[index + 5]) & 255) << 40)) | ((((long) bb[index + 4]) & 255) << 32)) | ((((long) bb[index + 3]) & 255) << 24)) | ((((long) bb[index + 2]) & 255) << 16)) | ((((long) bb[index + 1]) & 255) << 8)) | ((((long) bb[index + 0]) & 255) << null);
    }

    protected static void putChar(byte[] bb, char ch, int index) {
        int temp = ch;
        for (int i = 0; i < 2; i++) {
            bb[index + i] = new Integer(temp & 255).byteValue();
            temp >>= 8;
        }
    }

    protected static char getChar(byte[] b, int index) {
        int s;
        if (b[index + 1] > (byte) 0) {
            s = 0 + b[index + 1];
        } else {
            s = 0 + (b[index + 0] + 256);
        }
        s *= 256;
        if (b[index + 0] > (byte) 0) {
            s += b[index + 1];
        } else {
            s += b[index + 0] + 256;
        }
        return (char) s;
    }

    protected static void putFloat(byte[] bb, float x, int index) {
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l >>= 8;
        }
    }

    protected static float getFloat(byte[] b, int index) {
        return Float.intBitsToFloat((int) (((long) (((int) (((long) (((int) (((long) (b[index + 0] & 255)) | (((long) b[index + 1]) << 8))) & SupportMenu.USER_MASK)) | (((long) b[index + 2]) << 16))) & ViewCompat.MEASURED_SIZE_MASK)) | (((long) b[index + 3]) << 24)));
    }

    protected static void putDouble(byte[] bb, double x, int index) {
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 8; i++) {
            bb[index + i] = new Long(l).byteValue();
            l >>= 8;
        }
    }

    protected static double getDouble(byte[] b, int index) {
        return Double.longBitsToDouble((((((((((((((((long) b[index + 0]) & 255) | (((long) b[index + 1]) << 8)) & 65535) | (((long) b[index + 2]) << 16)) & 16777215) | (((long) b[index + 3]) << 24)) & 4294967295L) | (((long) b[index + 4]) << 32)) & 1099511627775L) | (((long) b[index + 5]) << 40)) & 281474976710655L) | (((long) b[index + 6]) << 48)) & 72057594037927935L) | (((long) b[index + 7]) << 56));
    }
}
