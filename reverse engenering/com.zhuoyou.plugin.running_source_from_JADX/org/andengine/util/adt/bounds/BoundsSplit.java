package org.andengine.util.adt.bounds;

import org.andengine.util.exception.AndEngineRuntimeException;

public enum BoundsSplit {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public static class BoundsSplitException extends AndEngineRuntimeException {
        private static final long serialVersionUID = 7970869239897412727L;
    }
}
