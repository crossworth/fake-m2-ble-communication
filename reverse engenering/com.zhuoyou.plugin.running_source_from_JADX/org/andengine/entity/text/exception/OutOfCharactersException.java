package org.andengine.entity.text.exception;

public class OutOfCharactersException extends TextException {
    private static final long serialVersionUID = 3076821980884912905L;

    public OutOfCharactersException(String pMessage) {
        super(pMessage);
    }

    public OutOfCharactersException(Throwable pThrowable) {
        super(pThrowable);
    }

    public OutOfCharactersException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
