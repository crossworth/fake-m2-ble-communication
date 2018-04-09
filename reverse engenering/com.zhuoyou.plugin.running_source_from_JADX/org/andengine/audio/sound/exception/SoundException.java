package org.andengine.audio.sound.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class SoundException extends AndEngineRuntimeException {
    private static final long serialVersionUID = 2647561236520151571L;

    public SoundException(String pMessage) {
        super(pMessage);
    }

    public SoundException(Throwable pThrowable) {
        super(pThrowable);
    }

    public SoundException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
