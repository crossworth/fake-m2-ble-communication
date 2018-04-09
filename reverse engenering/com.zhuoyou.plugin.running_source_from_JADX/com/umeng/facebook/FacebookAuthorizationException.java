package com.umeng.facebook;

public class FacebookAuthorizationException extends FacebookException {
    static final long serialVersionUID = 1;

    public FacebookAuthorizationException(String message) {
        super(message);
    }

    public FacebookAuthorizationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FacebookAuthorizationException(Throwable throwable) {
        super(throwable);
    }
}
