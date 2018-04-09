package org.andengine.util.adt.data;

public final class DataUtils {
    public static final int unsignedByteToInt(byte pByte) {
        return pByte & 255;
    }
}
