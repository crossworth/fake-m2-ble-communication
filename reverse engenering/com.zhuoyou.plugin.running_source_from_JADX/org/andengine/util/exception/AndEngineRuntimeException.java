package org.andengine.util.exception;

public class AndEngineRuntimeException extends RuntimeException {
    public static final long serialVersionUID = -4325207483842883006L;

    public AndEngineRuntimeException(String pMessage) {
        super(pMessage);
    }

    public AndEngineRuntimeException(Throwable pThrowable) {
        super(pThrowable);
    }

    public AndEngineRuntimeException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
