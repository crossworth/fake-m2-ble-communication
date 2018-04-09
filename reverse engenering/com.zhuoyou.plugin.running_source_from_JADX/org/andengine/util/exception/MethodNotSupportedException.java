package org.andengine.util.exception;

public class MethodNotSupportedException extends AndEngineRuntimeException {
    private static final long serialVersionUID = 1248621152476879759L;

    public MethodNotSupportedException(String pMessage) {
        super(pMessage);
    }

    public MethodNotSupportedException(Throwable pThrowable) {
        super(pThrowable);
    }

    public MethodNotSupportedException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
