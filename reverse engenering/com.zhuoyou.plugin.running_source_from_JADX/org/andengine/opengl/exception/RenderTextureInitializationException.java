package org.andengine.opengl.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class RenderTextureInitializationException extends AndEngineRuntimeException {
    private static final long serialVersionUID = -7219303294648252076L;

    public RenderTextureInitializationException(String pMessage) {
        super(pMessage);
    }

    public RenderTextureInitializationException(Throwable pThrowable) {
        super(pThrowable);
    }

    public RenderTextureInitializationException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
