package org.andengine.opengl.vbo;

import android.opengl.GLES20;
import java.nio.FloatBuffer;
import org.andengine.opengl.util.BufferUtils;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.util.system.SystemUtils;

public class HighPerformanceVertexBufferObject extends VertexBufferObject {
    protected final float[] mBufferData;
    protected final FloatBuffer mFloatBuffer;

    public HighPerformanceVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
        this.mBufferData = new float[pCapacity];
        if (SystemUtils.SDK_VERSION_HONEYCOMB_OR_LATER) {
            this.mFloatBuffer = this.mByteBuffer.asFloatBuffer();
        } else {
            this.mFloatBuffer = null;
        }
    }

    public HighPerformanceVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, float[] pBufferData, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pBufferData.length, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
        this.mBufferData = pBufferData;
        if (SystemUtils.SDK_VERSION_HONEYCOMB_OR_LATER) {
            this.mFloatBuffer = this.mByteBuffer.asFloatBuffer();
        } else {
            this.mFloatBuffer = null;
        }
    }

    public float[] getBufferData() {
        return this.mBufferData;
    }

    public int getHeapMemoryByteSize() {
        return getByteCapacity();
    }

    public int getNativeHeapMemoryByteSize() {
        return getByteCapacity();
    }

    protected void onBufferData() {
        if (SystemUtils.SDK_VERSION_HONEYCOMB_OR_LATER) {
            this.mFloatBuffer.position(0);
            this.mFloatBuffer.put(this.mBufferData);
            GLES20.glBufferData(34962, this.mByteBuffer.capacity(), this.mByteBuffer, this.mUsage);
            return;
        }
        BufferUtils.put(this.mByteBuffer, this.mBufferData, this.mBufferData.length, 0);
        GLES20.glBufferData(34962, this.mByteBuffer.limit(), this.mByteBuffer, this.mUsage);
    }
}
