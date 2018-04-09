package org.andengine.audio.music.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class MusicException extends AndEngineRuntimeException {
    private static final long serialVersionUID = -3314204068618256639L;

    public MusicException(String pMessage) {
        super(pMessage);
    }

    public MusicException(Throwable pThrowable) {
        super(pThrowable);
    }

    public MusicException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
