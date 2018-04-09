package org.andengine.util.exception;

public class NullBitmapException extends AndEngineRuntimeException {
    private static final long serialVersionUID = -2183655622078988389L;

    public NullBitmapException(String pMessage) {
        super(pMessage);
    }

    public NullBitmapException(Throwable pThrowable) {
        super(pThrowable);
    }

    public NullBitmapException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
