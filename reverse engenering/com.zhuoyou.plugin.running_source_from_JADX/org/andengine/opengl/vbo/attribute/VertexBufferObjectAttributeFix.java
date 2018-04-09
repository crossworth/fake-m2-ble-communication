package org.andengine.opengl.vbo.attribute;

import org.andengine.opengl.GLES20Fix;

public class VertexBufferObjectAttributeFix extends VertexBufferObjectAttribute {
    public VertexBufferObjectAttributeFix(int pLocation, String pName, int pSize, int pType, boolean pNormalized, int pOffset) {
        super(pLocation, pName, pSize, pType, pNormalized, pOffset);
    }

    public void glVertexAttribPointer(int pStride) {
        GLES20Fix.glVertexAttribPointer(this.mLocation, this.mSize, this.mType, this.mNormalized, pStride, this.mOffset);
    }
}
