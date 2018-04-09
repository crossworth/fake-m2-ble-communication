package org.andengine.opengl.texture;

import android.opengl.GLES20;
import java.nio.FloatBuffer;
import org.andengine.opengl.shader.PositionTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

public class TextureWarmUpVertexBufferObject extends VertexBufferObject {
    public static final int TEXTURECOORDINATES_INDEX_U = 2;
    public static final int TEXTURECOORDINATES_INDEX_V = 3;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(2).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, 5126, false).build();
    public static final int VERTEXBUFFEROBJECT_SIZE = 12;
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 4;
    public static final int VERTICES_PER_VERTEXBUFFEROBJECT_SIZE = 3;
    protected final FloatBuffer mFloatBuffer = this.mByteBuffer.asFloatBuffer();

    public TextureWarmUpVertexBufferObject() {
        super(null, 12, DrawType.STATIC, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT);
        this.mFloatBuffer.put(0, 0.0f);
        this.mFloatBuffer.put(1, 0.0f);
        this.mFloatBuffer.put(2, 0.0f);
        this.mFloatBuffer.put(3, 0.0f);
        this.mFloatBuffer.put(4, 1.0f);
        this.mFloatBuffer.put(5, 0.0f);
        this.mFloatBuffer.put(6, 1.0f);
        this.mFloatBuffer.put(7, 0.0f);
        this.mFloatBuffer.put(8, 0.0f);
        this.mFloatBuffer.put(9, 1.0f);
        this.mFloatBuffer.put(10, 0.0f);
        this.mFloatBuffer.put(11, 1.0f);
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

    public void warmup(GLState pGLState, ITexture pTexture) {
        pTexture.bind(pGLState);
        bind(pGLState, PositionTextureCoordinatesShaderProgram.getInstance());
        pGLState.pushModelViewGLMatrix();
        pGLState.loadModelViewGLMatrixIdentity();
        pGLState.translateModelViewGLMatrixf(1000000.0f, 1000000.0f, 0.0f);
        pGLState.scaleModelViewGLMatrixf(1.0E-4f, 1.0E-4f, 0);
        draw(4, 3);
        pGLState.popModelViewGLMatrix();
        unbind(pGLState, PositionTextureCoordinatesShaderProgram.getInstance());
    }
}
