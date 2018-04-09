package org.andengine.util.adt.io.out;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteBufferOutputStream extends OutputStream {
    protected int mCount;
    protected byte[] mData;
    protected final int mMaximumGrow;

    public ByteBufferOutputStream(int pInitialCapacity, int pMaximumGrow) {
        this.mMaximumGrow = pMaximumGrow;
        this.mData = new byte[pInitialCapacity];
    }

    public void write(int pByte) {
        ensureCapacity(this.mCount + 1);
        this.mData[this.mCount] = (byte) pByte;
        this.mCount++;
    }

    public void write(byte[] pData, int pOffset, int pLength) {
        ensureCapacity(this.mCount + pLength);
        System.arraycopy(pData, pOffset, this.mData, this.mCount, pLength);
        this.mCount += pLength;
    }

    public void close() throws IOException {
    }

    private void ensureCapacity(int pDesiredCapacity) {
        if (pDesiredCapacity - this.mData.length > 0) {
            grow(pDesiredCapacity);
        }
    }

    private void grow(int pDesiredCapacity) {
        int oldCapacity = this.mData.length;
        int newCapacity = oldCapacity + Math.min(this.mMaximumGrow, oldCapacity);
        if (newCapacity - pDesiredCapacity < 0) {
            newCapacity = pDesiredCapacity;
        }
        if (newCapacity < 0) {
            if (pDesiredCapacity < 0) {
                throw new OutOfMemoryError();
            }
            newCapacity = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        byte[] data = new byte[newCapacity];
        System.arraycopy(this.mData, 0, data, 0, this.mCount);
        this.mData = data;
    }

    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(this.mData, 0, this.mCount).slice();
    }
}
