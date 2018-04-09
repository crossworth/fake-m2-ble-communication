package org.andengine.entity.text.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class TextException extends AndEngineRuntimeException {
    private static final long serialVersionUID = -412281825916020126L;

    public TextException(String pMessage) {
        super(pMessage);
    }

    public TextException(Throwable pThrowable) {
        super(pThrowable);
    }

    public TextException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
