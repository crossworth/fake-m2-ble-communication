package org.andengine.opengl.font.exception;

public class LetterNotFoundException extends FontException {
    private static final long serialVersionUID = 5260601170771253529L;

    public LetterNotFoundException(String pMessage) {
        super(pMessage);
    }

    public LetterNotFoundException(Throwable pThrowable) {
        super(pThrowable);
    }

    public LetterNotFoundException(String pMessage, Throwable pThrowable) {
        super(pMessage, pThrowable);
    }
}
