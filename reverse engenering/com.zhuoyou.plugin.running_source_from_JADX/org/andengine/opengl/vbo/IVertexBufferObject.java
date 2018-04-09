package org.andengine.opengl.vbo;

import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.util.GLState;
import org.andengine.util.IDisposable;

public interface IVertexBufferObject extends IDisposable {
    public static final int HARDWARE_BUFFER_ID_INVALID = -1;

    void bind(GLState gLState);

    void bind(GLState gLState, ShaderProgram shaderProgram);

    void draw(int i, int i2);

    void draw(int i, int i2, int i3);

    int getByteCapacity();

    int getCapacity();

    int getGPUMemoryByteSize();

    int getHardwareBufferID();

    int getHeapMemoryByteSize();

    int getNativeHeapMemoryByteSize();

    VertexBufferObjectManager getVertexBufferObjectManager();

    boolean isAutoDispose();

    boolean isDirtyOnHardware();

    boolean isLoadedToHardware();

    void setDirtyOnHardware();

    void setNotLoadedToHardware();

    void unbind(GLState gLState, ShaderProgram shaderProgram);

    void unloadFromHardware(GLState gLState);
}
