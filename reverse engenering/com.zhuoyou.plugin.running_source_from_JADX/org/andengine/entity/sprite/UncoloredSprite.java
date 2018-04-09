package org.andengine.entity.sprite;

import org.andengine.entity.sprite.vbo.HighPerformanceUncoloredSpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.IUncoloredSpriteVertexBufferObject;
import org.andengine.opengl.shader.PositionTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

public class UncoloredSprite extends Sprite {
    public static final int SPRITE_SIZE = 16;
    public static final int TEXTURECOORDINATES_INDEX_U = 2;
    public static final int TEXTURECOORDINATES_INDEX_V = 3;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(2).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, 5126, false).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 4;
    public static final int VERTICES_PER_SPRITE = 4;

    public UncoloredSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public UncoloredSprite(float pX, float pY, ITextureRegion pTextureRegion, ShaderProgram pShaderProgram, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public UncoloredSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType);
    }

    public UncoloredSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public UncoloredSprite(float pX, float pY, ITextureRegion pTextureRegion, IUncoloredSpriteVertexBufferObject pUncoloredSpriteVertexBufferObject) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pUncoloredSpriteVertexBufferObject);
    }

    public UncoloredSprite(float pX, float pY, ITextureRegion pTextureRegion, IUncoloredSpriteVertexBufferObject pUncoloredSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pUncoloredSpriteVertexBufferObject, pShaderProgram);
    }

    public UncoloredSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public UncoloredSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public UncoloredSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, (IUncoloredSpriteVertexBufferObject) new HighPerformanceUncoloredSpriteVertexBufferObject(pVertexBufferObjectManager, 16, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    public UncoloredSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, (IUncoloredSpriteVertexBufferObject) new HighPerformanceUncoloredSpriteVertexBufferObject(pVertexBufferObjectManager, 16, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
    }

    public UncoloredSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, IUncoloredSpriteVertexBufferObject pUncoloredSpriteVertexBufferObject) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pUncoloredSpriteVertexBufferObject, PositionTextureCoordinatesShaderProgram.getInstance());
    }

    public UncoloredSprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, IUncoloredSpriteVertexBufferObject pUncoloredSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pWidth, pHeight, pTextureRegion, (ISpriteVertexBufferObject) pUncoloredSpriteVertexBufferObject, pShaderProgram);
    }

    protected void onUpdateColor() {
    }
}
