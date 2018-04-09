package org.andengine.opengl;

import android.opengl.GLES20;
import org.andengine.util.exception.AndEngineRuntimeException;
import org.andengine.util.system.SystemUtils;

public class GLES20Fix {
    private static boolean NATIVE_LIB_LOADED;
    private static final boolean WORKAROUND_MISSING_GLES20_METHODS;

    public static native void glDrawElements(int i, int i2, int i3, int i4);

    public static native void glVertexAttribPointer(int i, int i2, int i3, boolean z, int i4, int i5);

    static {
        boolean loadLibrarySuccess;
        try {
            System.loadLibrary("andengine");
            loadLibrarySuccess = true;
        } catch (UnsatisfiedLinkError e) {
            loadLibrarySuccess = false;
        }
        NATIVE_LIB_LOADED = loadLibrarySuccess;
        if (!SystemUtils.isAndroidVersionOrLower(8)) {
            WORKAROUND_MISSING_GLES20_METHODS = false;
        } else if (loadLibrarySuccess) {
            WORKAROUND_MISSING_GLES20_METHODS = true;
        } else {
            throw new AndEngineRuntimeException("Inherently incompatible device detected.");
        }
    }

    private GLES20Fix() {
    }

    public static void glVertexAttribPointerFix(int pIndex, int pSize, int pType, boolean pNormalized, int pStride, int pOffset) {
        if (WORKAROUND_MISSING_GLES20_METHODS) {
            glVertexAttribPointerFix(pIndex, pSize, pType, pNormalized, pStride, pOffset);
        } else {
            GLES20.glVertexAttribPointer(pIndex, pSize, pType, pNormalized, pStride, pOffset);
        }
    }

    public static void glDrawElementsFix(int pMode, int pCount, int pType, int pOffset) {
        if (WORKAROUND_MISSING_GLES20_METHODS) {
            glDrawElements(pMode, pCount, pType, pOffset);
        } else {
            GLES20.glDrawElements(pMode, pCount, pType, pOffset);
        }
    }
}
