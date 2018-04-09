package org.andengine.opengl.exception;

public class GLFrameBufferException extends GLException {
    private static final long serialVersionUID = -8910272713633644676L;

    public GLFrameBufferException(int pError) {
        super(pError);
    }

    public GLFrameBufferException(int pError, String pString) {
        super(pError, pString);
    }
}
