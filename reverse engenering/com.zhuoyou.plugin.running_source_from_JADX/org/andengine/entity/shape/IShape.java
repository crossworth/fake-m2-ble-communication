package org.andengine.entity.shape;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public interface IShape extends IEntity, ITouchArea {
    public static final int BLENDFUNCTION_DESTINATION_DEFAULT = 771;
    public static final int BLENDFUNCTION_DESTINATION_PREMULTIPLYALPHA_DEFAULT = 771;
    public static final int BLENDFUNCTION_SOURCE_DEFAULT = 770;
    public static final int BLENDFUNCTION_SOURCE_PREMULTIPLYALPHA_DEFAULT = 1;

    boolean collidesWith(IShape iShape);

    int getBlendFunctionDestination();

    int getBlendFunctionSource();

    ShaderProgram getShaderProgram();

    IVertexBufferObject getVertexBufferObject();

    VertexBufferObjectManager getVertexBufferObjectManager();

    boolean isBlendingEnabled();

    void setBlendFunction(int i, int i2);

    void setBlendFunctionDestination(int i);

    void setBlendFunctionSource(int i);

    void setBlendingEnabled(boolean z);

    void setShaderProgram(ShaderProgram shaderProgram);
}
