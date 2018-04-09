package org.andengine.opengl.exception;

import android.opengl.GLU;

public class GLException extends RuntimeException {
    private static final long serialVersionUID = -7494923307858371890L;
    private final int mError;

    public GLException(int pError) {
        this(pError, getErrorString(pError));
    }

    public GLException(int pError, String pString) {
        super(pString);
        this.mError = pError;
    }

    public int getError() {
        return this.mError;
    }

    private static String getErrorString(int pError) {
        String errorString = GLU.gluErrorString(pError);
        if (errorString == null) {
            return "Unknown error '0x" + Integer.toHexString(pError) + "'.";
        }
        return errorString;
    }
}
