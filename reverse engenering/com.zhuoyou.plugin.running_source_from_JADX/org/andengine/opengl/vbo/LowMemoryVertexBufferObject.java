package org.andengine.opengl.vbo;

import android.opengl.GLES20;
import java.nio.FloatBuffer;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemoryVertexBufferObject extends VertexBufferObject {
    protected final FloatBuffer mFloatBuffer = this.mByteBuffer.asFloatBuffer();

    public LowMemoryVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public FloatBuffer getFloatBuffer() {
        return this.mFloatBuffer;
    }

    public int getHeapMemoryByteSize() {
        return 0;
    }

    public int getNativeHeapMemoryByteSize() {
        return getByteCapacity();
    }

    protected void onBufferData() {
        GLES20.glBufferData(34962, this.mByteBuffer.limit(), this.mByteBuffer, this.mUsage);
    }
}
