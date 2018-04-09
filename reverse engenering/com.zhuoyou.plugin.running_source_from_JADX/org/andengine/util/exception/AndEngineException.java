package org.andengine.util.exception;

public class AndEngineException extends Exception {
    private static final long serialVersionUID = 6577340337732194722L;

    public AndEngineException(String pMessage) {
        super(pMessage);
    }

    public AndEngineException(Throwable pThrowable) {
        super(pThrowable);
    }

    public AndEngineException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
