package org.andengine.audio.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class AudioException extends AndEngineRuntimeException {
    private static final long serialVersionUID = 2647561236520151571L;

    public AudioException(String pMessage) {
        super(pMessage);
    }

    public AudioException(Throwable pThrowable) {
        super(pThrowable);
    }

    public AudioException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
