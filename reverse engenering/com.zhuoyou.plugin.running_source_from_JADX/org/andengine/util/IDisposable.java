package org.andengine.util;

import org.andengine.util.exception.AndEngineRuntimeException;

public interface IDisposable {

    public static class AlreadyDisposedException extends AndEngineRuntimeException {
        private static final long serialVersionUID = 5796912098160771249L;

        public AlreadyDisposedException(String pMessage) {
            super(pMessage);
        }

        public AlreadyDisposedException(Throwable pThrowable) {
            super(pThrowable);
        }

        public AlreadyDisposedException(String pMessage, Throwable pThrowable) {
            super(pMessage, pThrowable);
        }
    }

    void dispose() throws AlreadyDisposedException;

    boolean isDisposed();
}
