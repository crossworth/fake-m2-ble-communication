package org.andengine.util.adt.io.in;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStreamOpener implements IInputStreamOpener {
    private final byte[] mBytes;
    private final int mLength;
    private final int mOffset;

    public ByteArrayInputStreamOpener(byte[] pBytes) {
        this(pBytes, 0, pBytes.length);
    }

    public ByteArrayInputStreamOpener(byte[] pBytes, int pOffset, int pLength) {
        this.mBytes = pBytes;
        this.mOffset = pOffset;
        this.mLength = pLength;
    }

    public InputStream open() throws IOException {
        return new ByteArrayInputStream(this.mBytes, this.mOffset, this.mLength);
    }
}
