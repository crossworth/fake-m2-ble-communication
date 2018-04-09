package org.andengine.opengl.vbo.attribute;

import android.opengl.GLES20;

public class VertexBufferObjectAttribute {
    final int mLocation;
    final String mName;
    final boolean mNormalized;
    final int mOffset;
    final int mSize;
    final int mType;

    public VertexBufferObjectAttribute(int pLocation, String pName, int pSize, int pType, boolean pNormalized, int pOffset) {
        this.mLocation = pLocation;
        this.mName = pName;
        this.mSize = pSize;
        this.mType = pType;
        this.mNormalized = pNormalized;
        this.mOffset = pOffset;
    }

    public int getLocation() {
        return this.mLocation;
    }

    public String getName() {
        return this.mName;
    }

    public int getSize() {
        return this.mSize;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isNormalized() {
        return this.mNormalized;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public void glVertexAttribPointer(int pStride) {
        GLES20.glVertexAttribPointer(this.mLocation, this.mSize, this.mType, this.mNormalized, pStride, this.mOffset);
    }
}
