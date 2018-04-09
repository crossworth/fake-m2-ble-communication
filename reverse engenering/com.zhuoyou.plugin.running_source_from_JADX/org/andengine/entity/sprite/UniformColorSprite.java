package org.andengine.entity.sprite;

import android.opengl.GLES20;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.vbo.HighPerformanceUniformColorSpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.IUniformColorSpriteVertexBufferObject;
import org.andengine.opengl.shader.PositionTextureCoordinatesUniformColorShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

public class UniformColorSprite extends Sprite {
    public static final int SPRITE_SIZE = 16;
    public static final int TEXTURECOORDINATES_INDEX_U = 2;
    public static final int TEXTURECOORDINATES_INDEX_V = 3;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(2).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, 5126, false).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 4;
    public static final int VERTICES_PER_SPRITE = 4;

    public UniformColorSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public UniformColorSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public UniformColorSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType);
    }

    public UniformColorSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public UniformColorSprite(float pX, float pY, ITextureRegion pTextureRegion, IUniformColorSpriteVertexBufferObject pVertexBufferObject) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObject);
    }

    public UniformColorSprite(float pX, float pY, ITextureRegion pTextureRegion, IUniformColorSpriteVertexBufferObject pVertexBufferObject, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObject, pShaderProgram);
    }

    public UniformColorSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public UniformColorSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public UniformColorSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, pDrawType, PositionTextureCoordinatesUniformColorShaderProgram.getInstance());
    }

    public UniformColorSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, (IUniformColorSpriteVertexBufferObject) new HighPerformanceUniformColorSpriteVertexBufferObject(pVertexBufferObjectManager, 16, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
    }

    public UniformColorSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, IUniformColorSpriteVertexBufferObject pUniformColorSpriteVertexBufferObject) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pUniformColorSpriteVertexBufferObject, PositionTextureCoordinatesUniformColorShaderProgram.getInstance());
    }

    public UniformColorSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, IUniformColorSpriteVertexBufferObject pUniformColorSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pWidth, pHeight, pTextureRegion, (ISpriteVertexBufferObject) pUniformColorSpriteVertexBufferObject, pShaderProgram);
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        GLES20.glUniform4f(PositionTextureCoordinatesUniformColorShaderProgram.sUniformColorLocation, this.mColor.getRed(), this.mColor.getGreen(), this.mColor.getBlue(), this.mColor.getAlpha());
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        this.mSpriteVertexBufferObject.draw(5, 4);
    }
}
