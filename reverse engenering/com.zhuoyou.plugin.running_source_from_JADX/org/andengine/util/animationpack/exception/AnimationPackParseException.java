package org.andengine.util.animationpack.exception;

import org.xml.sax.SAXException;

public class AnimationPackParseException extends SAXException {
    private static final long serialVersionUID = 1136010869754861664L;

    public AnimationPackParseException(String pDetailMessage) {
        super(pDetailMessage);
    }

    public AnimationPackParseException(Exception pException) {
        super(pException);
    }

    public AnimationPackParseException(String pMessage, Exception pException) {
        super(pMessage, pException);
    }
}
