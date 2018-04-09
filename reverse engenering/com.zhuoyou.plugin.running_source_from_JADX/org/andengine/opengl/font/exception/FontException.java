package org.andengine.opengl.font.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class FontException extends AndEngineRuntimeException {
    private static final long serialVersionUID = 2766566088383545102L;

    public FontException(String pMessage) {
        super(pMessage);
    }

    public FontException(Throwable pThrowable) {
        super(pThrowable);
    }

    public FontException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
