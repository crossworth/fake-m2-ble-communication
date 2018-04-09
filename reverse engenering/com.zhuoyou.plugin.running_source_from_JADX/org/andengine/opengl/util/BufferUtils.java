package org.andengine.opengl.util;

import java.nio.ByteBuffer;
import org.andengine.util.adt.DataConstants;
import org.andengine.util.debug.Debug;
import org.andengine.util.system.SystemUtils;

public class BufferUtils {
    private static final boolean NATIVE_LIB_LOADED;
    private static final boolean WORKAROUND_BYTEBUFFER_ALLOCATE_DIRECT;
    private static final boolean WORKAROUND_BYTEBUFFER_PUT_FLOATARRAY;

    private static native ByteBuffer jniAllocateDirect(int i);

    private static native void jniFreeDirect(ByteBuffer byteBuffer);

    private static native void jniPut(ByteBuffer byteBuffer, float[] fArr, int i, int i2);

    static {
        boolean loadLibrarySuccess;
        try {
            System.loadLibrary("andengine");
            loadLibrarySuccess = true;
        } catch (UnsatisfiedLinkError e) {
            loadLibrarySuccess = false;
        }
        NATIVE_LIB_LOADED = loadLibrarySuccess;
        if (NATIVE_LIB_LOADED) {
            if (SystemUtils.isAndroidVersion(11, 13)) {
                WORKAROUND_BYTEBUFFER_ALLOCATE_DIRECT = true;
            } else {
                WORKAROUND_BYTEBUFFER_ALLOCATE_DIRECT = false;
            }
            if (SystemUtils.isAndroidVersionOrLower(8)) {
                WORKAROUND_BYTEBUFFER_PUT_FLOATARRAY = true;
                return;
            } else {
                WORKAROUND_BYTEBUFFER_PUT_FLOATARRAY = false;
                return;
            }
        }
        WORKAROUND_BYTEBUFFER_ALLOCATE_DIRECT = false;
        if (SystemUtils.isAndroidVersion(11, 13)) {
            Debug.m4601w("Creating a " + ByteBuffer.class.getSimpleName() + " will actually allocate 4x the memory than requested!");
        }
        WORKAROUND_BYTEBUFFER_PUT_FLOATARRAY = false;
    }

    public static ByteBuffer allocateDirectByteBuffer(int pCapacity) {
        if (WORKAROUND_BYTEBUFFER_ALLOCATE_DIRECT) {
            return jniAllocateDirect(pCapacity);
        }
        return ByteBuffer.allocateDirect(pCapacity);
    }

    public static void freeDirectByteBuffer(ByteBuffer pByteBuffer) {
        if (WORKAROUND_BYTEBUFFER_ALLOCATE_DIRECT) {
            jniFreeDirect(pByteBuffer);
        }
    }

    public static void put(ByteBuffer pByteBuffer, float[] pSource, int pLength, int pOffset) {
        if (WORKAROUND_BYTEBUFFER_PUT_FLOATARRAY) {
            jniPut(pByteBuffer, pSource, pLength, pOffset);
        } else {
            for (int i = pOffset; i < pOffset + pLength; i++) {
                pByteBuffer.putFloat(pSource[i]);
            }
        }
        pByteBuffer.position(0);
        pByteBuffer.limit(pLength << 2);
    }

    public static short getUnsignedByte(ByteBuffer pByteBuffer) {
        return (short) (pByteBuffer.get() & 255);
    }

    public static void putUnsignedByte(ByteBuffer pByteBuffer, int pValue) {
        pByteBuffer.put((byte) (pValue & 255));
    }

    public static short getUnsignedByte(ByteBuffer pByteBuffer, int pPosition) {
        return (short) (pByteBuffer.get(pPosition) & 255);
    }

    public static void putUnsignedByte(ByteBuffer pByteBuffer, int pPosition, int pValue) {
        pByteBuffer.put(pPosition, (byte) (pValue & 255));
    }

    public static int getUnsignedShort(ByteBuffer pByteBuffer) {
        return pByteBuffer.getShort() & 65535;
    }

    public static void putUnsignedShort(ByteBuffer pByteBuffer, int pValue) {
        pByteBuffer.putShort((short) (65535 & pValue));
    }

    public static int getUnsignedShort(ByteBuffer pByteBuffer, int pPosition) {
        return pByteBuffer.getShort(pPosition) & 65535;
    }

    public static void putUnsignedShort(ByteBuffer pByteBuffer, int pPosition, int pValue) {
        pByteBuffer.putShort(pPosition, (short) (65535 & pValue));
    }

    public static long getUnsignedInt(ByteBuffer pByteBuffer) {
        return ((long) pByteBuffer.getInt()) & DataConstants.UNSIGNED_INT_MAX_VALUE;
    }

    public static void putUnsignedInt(ByteBuffer pByteBuffer, long pValue) {
        pByteBuffer.putInt((int) (DataConstants.UNSIGNED_INT_MAX_VALUE & pValue));
    }

    public static long getUnsignedInt(ByteBuffer pByteBuffer, int pPosition) {
        return ((long) pByteBuffer.getInt(pPosition)) & DataConstants.UNSIGNED_INT_MAX_VALUE;
    }

    public static void putUnsignedInt(ByteBuffer pByteBuffer, int pPosition, long pValue) {
        pByteBuffer.putInt(pPosition, (short) ((int) (DataConstants.UNSIGNED_INT_MAX_VALUE & pValue)));
    }
}
