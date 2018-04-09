package org.andengine.opengl.vbo;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.util.BufferUtils;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.util.IDisposable.AlreadyDisposedException;

public abstract class ZeroMemoryVertexBufferObject implements IVertexBufferObject {
    protected final boolean mAutoDispose;
    protected final int mCapacity;
    protected boolean mDirtyOnHardware = true;
    protected boolean mDisposed;
    protected int mHardwareBufferID = -1;
    protected final int mUsage;
    protected final VertexBufferObjectAttributes mVertexBufferObjectAttributes;
    protected final VertexBufferObjectManager mVertexBufferObjectManager;

    protected abstract void onPopulateBufferData(ByteBuffer byteBuffer);

    public ZeroMemoryVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        this.mVertexBufferObjectManager = pVertexBufferObjectManager;
        this.mCapacity = pCapacity;
        this.mUsage = pDrawType.getUsage();
        this.mAutoDispose = pAutoDispose;
        this.mVertexBufferObjectAttributes = pVertexBufferObjectAttributes;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return this.mVertexBufferObjectManager;
    }

    public boolean isDisposed() {
        return this.mDisposed;
    }

    public boolean isAutoDispose() {
        return this.mAutoDispose;
    }

    public int getHardwareBufferID() {
        return this.mHardwareBufferID;
    }

    public boolean isLoadedToHardware() {
        return this.mHardwareBufferID != -1;
    }

    public void setNotLoadedToHardware() {
        this.mHardwareBufferID = -1;
        this.mDirtyOnHardware = true;
    }

    public boolean isDirtyOnHardware() {
        return this.mDirtyOnHardware;
    }

    public void setDirtyOnHardware() {
        this.mDirtyOnHardware = true;
    }

    public int getCapacity() {
        return this.mCapacity;
    }

    public int getByteCapacity() {
        return this.mCapacity * 4;
    }

    public int getHeapMemoryByteSize() {
        return 0;
    }

    public int getNativeHeapMemoryByteSize() {
        return 0;
    }

    public int getGPUMemoryByteSize() {
        if (isLoadedToHardware()) {
            return getByteCapacity();
        }
        return 0;
    }

    public void bind(GLState pGLState) {
        if (this.mHardwareBufferID == -1) {
            loadToHardware(pGLState);
            this.mVertexBufferObjectManager.onVertexBufferObjectLoaded(this);
        }
        pGLState.bindArrayBuffer(this.mHardwareBufferID);
        if (this.mDirtyOnHardware) {
            ByteBuffer byteBuffer = null;
            try {
                byteBuffer = aquireByteBuffer();
                onPopulateBufferData(byteBuffer);
                GLES20.glBufferData(34962, byteBuffer.limit(), byteBuffer, this.mUsage);
                this.mDirtyOnHardware = false;
            } finally {
                if (byteBuffer != null) {
                    releaseByteBuffer(byteBuffer);
                }
            }
        }
    }

    public void bind(GLState pGLState, ShaderProgram pShaderProgram) {
        bind(pGLState);
        pShaderProgram.bind(pGLState, this.mVertexBufferObjectAttributes);
    }

    public void unbind(GLState pGLState, ShaderProgram pShaderProgram) {
        pShaderProgram.unbind(pGLState);
    }

    public void unloadFromHardware(GLState pGLState) {
        pGLState.deleteArrayBuffer(this.mHardwareBufferID);
        this.mHardwareBufferID = -1;
    }

    public void draw(int pPrimitiveType, int pCount) {
        GLES20.glDrawArrays(pPrimitiveType, 0, pCount);
    }

    public void draw(int pPrimitiveType, int pOffset, int pCount) {
        GLES20.glDrawArrays(pPrimitiveType, pOffset, pCount);
    }

    public void dispose() {
        if (this.mDisposed) {
            throw new AlreadyDisposedException();
        }
        this.mDisposed = true;
        this.mVertexBufferObjectManager.onUnloadVertexBufferObject(this);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (!this.mDisposed) {
            dispose();
        }
    }

    private void loadToHardware(GLState pGLState) {
        this.mHardwareBufferID = pGLState.generateBuffer();
        this.mDirtyOnHardware = true;
    }

    protected ByteBuffer aquireByteBuffer() {
        ByteBuffer byteBuffer = BufferUtils.allocateDirectByteBuffer(getByteCapacity());
        byteBuffer.order(ByteOrder.nativeOrder());
        return byteBuffer;
    }

    protected void releaseByteBuffer(ByteBuffer byteBuffer) {
        BufferUtils.freeDirectByteBuffer(byteBuffer);
    }
}
