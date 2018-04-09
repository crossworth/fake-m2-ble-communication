package org.andengine.util.exception;

public class MethodNotYetImplementedException extends AndEngineRuntimeException {
    private static final long serialVersionUID = -4308430823868086531L;

    public MethodNotYetImplementedException(String pMessage) {
        super(pMessage);
    }

    public MethodNotYetImplementedException(Throwable pThrowable) {
        super(pThrowable);
    }

    public MethodNotYetImplementedException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
