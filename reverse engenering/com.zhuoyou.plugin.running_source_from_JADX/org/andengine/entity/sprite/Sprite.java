package org.andengine.entity.sprite;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.vbo.HighPerformanceSpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

public class Sprite extends RectangularShape {
    public static final int COLOR_INDEX = 2;
    public static final int SPRITE_SIZE = 20;
    public static final int TEXTURECOORDINATES_INDEX_U = 3;
    public static final int TEXTURECOORDINATES_INDEX_V = 4;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(3).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(1, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, 5121, true).add(3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, 5126, false).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 5;
    public static final int VERTICES_PER_SPRITE = 4;
    protected boolean mFlippedHorizontal;
    protected boolean mFlippedVertical;
    protected final ISpriteVertexBufferObject mSpriteVertexBufferObject;
    protected final ITextureRegion mTextureRegion;

    public Sprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Sprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public Sprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType);
    }

    public Sprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Sprite(float pX, float pY, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pVertexBufferObject) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObject);
    }

    public Sprite(float pX, float pY, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pVertexBufferObject, ShaderProgram pShaderProgram) {
        this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObject, pShaderProgram);
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, pDrawType, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, (ISpriteVertexBufferObject) new HighPerformanceSpriteVertexBufferObject(pVertexBufferObjectManager, 20, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pSpriteVertexBufferObject) {
        this(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pWidth, pHeight, pShaderProgram);
        this.mTextureRegion = pTextureRegion;
        this.mSpriteVertexBufferObject = pSpriteVertexBufferObject;
        setBlendingEnabled(true);
        initBlendFunction(pTextureRegion);
        onUpdateVertices();
        onUpdateColor();
        onUpdateTextureCoordinates();
    }

    public ITextureRegion getTextureRegion() {
        return this.mTextureRegion;
    }

    public boolean isFlippedHorizontal() {
        return this.mFlippedHorizontal;
    }

    public void setFlippedHorizontal(boolean pFlippedHorizontal) {
        if (this.mFlippedHorizontal != pFlippedHorizontal) {
            this.mFlippedHorizontal = pFlippedHorizontal;
            onUpdateTextureCoordinates();
        }
    }

    public boolean isFlippedVertical() {
        return this.mFlippedVertical;
    }

    public void setFlippedVertical(boolean pFlippedVertical) {
        if (this.mFlippedVertical != pFlippedVertical) {
            this.mFlippedVertical = pFlippedVertical;
            onUpdateTextureCoordinates();
        }
    }

    public void setFlipped(boolean pFlippedHorizontal, boolean pFlippedVertical) {
        if (this.mFlippedHorizontal != pFlippedHorizontal || this.mFlippedVertical != pFlippedVertical) {
            this.mFlippedHorizontal = pFlippedHorizontal;
            this.mFlippedVertical = pFlippedVertical;
            onUpdateTextureCoordinates();
        }
    }

    public ISpriteVertexBufferObject getVertexBufferObject() {
        return this.mSpriteVertexBufferObject;
    }

    public void reset() {
        super.reset();
        initBlendFunction(getTextureRegion().getTexture());
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        getTextureRegion().getTexture().bind(pGLState);
        this.mSpriteVertexBufferObject.bind(pGLState, this.mShaderProgram);
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        this.mSpriteVertexBufferObject.draw(5, 4);
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        this.mSpriteVertexBufferObject.unbind(pGLState, this.mShaderProgram);
        super.postDraw(pGLState, pCamera);
    }

    protected void onUpdateVertices() {
        this.mSpriteVertexBufferObject.onUpdateVertices(this);
    }

    protected void onUpdateColor() {
        this.mSpriteVertexBufferObject.onUpdateColor(this);
    }

    protected void onUpdateTextureCoordinates() {
        this.mSpriteVertexBufferObject.onUpdateTextureCoordinates(this);
    }
}
