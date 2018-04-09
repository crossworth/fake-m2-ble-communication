package org.andengine.opengl.vbo.attribute;

public class VertexBufferObjectAttributes {
    private final int mStride;
    private final VertexBufferObjectAttribute[] mVertexBufferObjectAttributes;

    public VertexBufferObjectAttributes(int pStride, VertexBufferObjectAttribute... pVertexBufferObjectAttributes) {
        this.mVertexBufferObjectAttributes = pVertexBufferObjectAttributes;
        this.mStride = pStride;
    }

    public void glVertexAttribPointers() {
        VertexBufferObjectAttribute[] vertexBufferObjectAttributes = this.mVertexBufferObjectAttributes;
        int stride = this.mStride;
        for (VertexBufferObjectAttribute glVertexAttribPointer : vertexBufferObjectAttributes) {
            glVertexAttribPointer.glVertexAttribPointer(stride);
        }
    }
}
