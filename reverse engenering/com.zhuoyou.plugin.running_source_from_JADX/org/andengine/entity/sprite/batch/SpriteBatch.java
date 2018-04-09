package org.andengine.entity.sprite.batch;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.vbo.HighPerformanceSpriteBatchVertexBufferObject;
import org.andengine.entity.sprite.batch.vbo.ISpriteBatchVertexBufferObject;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.color.ColorUtils;

public class SpriteBatch extends Shape {
    public static final int COLOR_INDEX = 2;
    public static final int SPRITE_SIZE = 30;
    public static final int TEXTURECOORDINATES_INDEX_U = 3;
    public static final int TEXTURECOORDINATES_INDEX_V = 4;
    private static final Transformation TRANSFORATION_TMP = new Transformation();
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(3).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(1, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, 5121, true).add(3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, 5126, false).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 5;
    public static final int VERTICES_PER_SPRITE = 6;
    private static final float[] VERTICES_TMP = new float[8];
    protected final int mCapacity;
    protected int mIndex;
    protected final ISpriteBatchVertexBufferObject mSpriteBatchVertexBufferObject;
    protected ITexture mTexture;
    protected int mVertices;

    public SpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pTexture, pCapacity, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public SpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public SpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pTexture, pCapacity, new HighPerformanceSpriteBatchVertexBufferObject(pVertexBufferObjectManager, pCapacity * 30, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    public SpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pTexture, pCapacity, (ISpriteBatchVertexBufferObject) new HighPerformanceSpriteBatchVertexBufferObject(pVertexBufferObjectManager, pCapacity * 30, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    public SpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pTexture, pCapacity, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public SpriteBatch(float pX, float pY, ITexture pTexture, VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, ShaderProgram pShaderProgram) {
        this(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public SpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pTexture, pCapacity, new HighPerformanceSpriteBatchVertexBufferObject(pVertexBufferObjectManager, pCapacity * 30, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
    }

    public SpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pTexture, pCapacity, (ISpriteBatchVertexBufferObject) new HighPerformanceSpriteBatchVertexBufferObject(pVertexBufferObjectManager, pCapacity * 30, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
    }

    public SpriteBatch(ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject) {
        this(pTexture, pCapacity, pSpriteBatchVertexBufferObject, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    public SpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject) {
        this(pX, pY, pTexture, pCapacity, pSpriteBatchVertexBufferObject, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    public SpriteBatch(ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject, ShaderProgram pShaderProgram) {
        this(0.0f, 0.0f, pTexture, pCapacity, pSpriteBatchVertexBufferObject, pShaderProgram);
    }

    public SpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pShaderProgram);
        this.mTexture = pTexture;
        this.mCapacity = pCapacity;
        this.mSpriteBatchVertexBufferObject = pSpriteBatchVertexBufferObject;
        setBlendingEnabled(true);
        initBlendFunction(this.mTexture);
    }

    public int getIndex() {
        return this.mIndex;
    }

    public ITexture getTexture() {
        return this.mTexture;
    }

    public void setTexture(ITexture pTexture) {
        this.mTexture = pTexture;
    }

    public void setIndex(int pIndex) {
        assertCapacity(pIndex);
        this.mIndex = pIndex;
        this.mSpriteBatchVertexBufferObject.setBufferDataOffset(pIndex * 30);
    }

    public ISpriteBatchVertexBufferObject getVertexBufferObject() {
        return this.mSpriteBatchVertexBufferObject;
    }

    public boolean collidesWith(IShape pOtherShape) {
        return false;
    }

    public boolean contains(float pX, float pY) {
        return false;
    }

    protected void onUpdateVertices() {
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        if (this.mBlendingEnabled) {
            pGLState.enableBlend();
            pGLState.blendFunction(this.mBlendFunctionSource, this.mBlendFunctionDestination);
        }
        this.mTexture.bind(pGLState);
        this.mSpriteBatchVertexBufferObject.bind(pGLState, this.mShaderProgram);
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        begin();
        this.mSpriteBatchVertexBufferObject.draw(4, this.mVertices);
        end();
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        this.mSpriteBatchVertexBufferObject.unbind(pGLState, this.mShaderProgram);
        if (this.mBlendingEnabled) {
            pGLState.disableBlend();
        }
        super.postDraw(pGLState, pCamera);
    }

    public void reset() {
        super.reset();
        initBlendFunction(this.mTexture);
    }

    public void dispose() {
        super.dispose();
        if (this.mSpriteBatchVertexBufferObject != null && this.mSpriteBatchVertexBufferObject.isAutoDispose() && !this.mSpriteBatchVertexBufferObject.isDisposed()) {
            this.mSpriteBatchVertexBufferObject.dispose();
        }
    }

    protected void begin() {
    }

    protected void end() {
    }

    public void draw(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRed, float pGreen, float pBlue, float pAlpha) {
        assertCapacity();
        assertTexture(pTextureRegion);
        add(pTextureRegion, pX, pY, pWidth, pHeight, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRed, float pGreen, float pBlue, float pAlpha) {
        add(pTextureRegion, pX, pY, pWidth, pHeight, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void draw(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pColorABGRPackedInt) {
        assertCapacity();
        assertTexture(pTextureRegion);
        add(pTextureRegion, pX, pY, pWidth, pHeight, pColorABGRPackedInt);
        this.mIndex++;
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pColorABGRPackedInt) {
        add(pTextureRegion, pX, pY, pWidth, pHeight, pColorABGRPackedInt);
        this.mIndex++;
    }

    public void draw(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pRed, float pGreen, float pBlue, float pAlpha) {
        assertCapacity();
        assertTexture(pTextureRegion);
        add(pTextureRegion, pX, pY, pWidth, pHeight, pRotation, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pRed, float pGreen, float pBlue, float pAlpha) {
        add(pTextureRegion, pX, pY, pWidth, pHeight, pRotation, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void draw(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY, float pRed, float pGreen, float pBlue, float pAlpha) {
        assertCapacity();
        assertTexture(pTextureRegion);
        add(pTextureRegion, pX, pY, pWidth, pHeight, pScaleX, pScaleY, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY, float pRed, float pGreen, float pBlue, float pAlpha) {
        add(pTextureRegion, pX, pY, pWidth, pHeight, pScaleX, pScaleY, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void draw(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY, float pRed, float pGreen, float pBlue, float pAlpha) {
        assertCapacity();
        assertTexture(pTextureRegion);
        add(pTextureRegion, pX, pY, pWidth, pHeight, pRotation, pScaleX, pScaleY, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY, float pRed, float pGreen, float pBlue, float pAlpha) {
        add(pTextureRegion, pX, pY, pWidth, pHeight, pRotation, pScaleX, pScaleY, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void draw(ITextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, float pRed, float pGreen, float pBlue, float pAlpha) {
        assertCapacity();
        assertTexture(pTextureRegion);
        addInner(pTextureRegion, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, float pRed, float pGreen, float pBlue, float pAlpha) {
        addInner(pTextureRegion, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, pRed, pGreen, pBlue, pAlpha);
        this.mIndex++;
    }

    public void draw(Sprite pSprite) {
        draw(pSprite.getTextureRegion(), (IEntity) pSprite, pSprite.getWidth(), pSprite.getHeight(), pSprite.getColor().getABGRPackedFloat());
    }

    public void drawWithoutChecks(Sprite pSprite) {
        drawWithoutChecks(pSprite.getTextureRegion(), (IEntity) pSprite, pSprite.getWidth(), pSprite.getHeight(), pSprite.getColor().getABGRPackedFloat());
    }

    public void draw(Sprite pSprite, float pRed, float pGreen, float pBlue, float pAlpha) {
        draw(pSprite.getTextureRegion(), pSprite, pSprite.getWidth(), pSprite.getHeight(), pRed, pGreen, pBlue, pAlpha);
    }

    public void drawWithoutChecks(Sprite pSprite, float pRed, float pGreen, float pBlue, float pAlpha) {
        drawWithoutChecks(pSprite.getTextureRegion(), pSprite, pSprite.getWidth(), pSprite.getHeight(), pRed, pGreen, pBlue, pAlpha);
    }

    public void draw(Sprite pSprite, float pColorABGRPackedInt) {
        draw(pSprite.getTextureRegion(), (IEntity) pSprite, pSprite.getWidth(), pSprite.getHeight(), pColorABGRPackedInt);
    }

    public void drawWithoutChecks(Sprite pSprite, float pColorABGRPackedInt) {
        drawWithoutChecks(pSprite.getTextureRegion(), (IEntity) pSprite, pSprite.getWidth(), pSprite.getHeight(), pColorABGRPackedInt);
    }

    public void draw(ITextureRegion pTextureRegion, IEntity pEntity, float pWidth, float pHeight) {
        draw(pTextureRegion, pEntity, pWidth, pHeight, pEntity.getColor().getABGRPackedFloat());
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, IEntity pEntity, float pWidth, float pHeight) {
        drawWithoutChecks(pTextureRegion, pEntity, pWidth, pHeight, pEntity.getColor().getABGRPackedFloat());
    }

    public void draw(ITextureRegion pTextureRegion, IEntity pEntity, float pWidth, float pHeight, float pRed, float pGreen, float pBlue, float pAlpha) {
        if (pEntity.isVisible()) {
            assertCapacity();
            assertTexture(pTextureRegion);
            if (pEntity.isRotatedOrScaledOrSkewed()) {
                add(pTextureRegion, pWidth, pHeight, pEntity.getLocalToParentTransformation(), pRed, pGreen, pBlue, pAlpha);
            } else {
                add(pTextureRegion, pEntity.getX(), pEntity.getY(), pWidth, pHeight, pRed, pGreen, pBlue, pAlpha);
            }
            this.mIndex++;
        }
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, IEntity pEntity, float pWidth, float pHeight, float pRed, float pGreen, float pBlue, float pAlpha) {
        if (pEntity.isVisible()) {
            if (pEntity.isRotatedOrScaledOrSkewed()) {
                add(pTextureRegion, pWidth, pHeight, pEntity.getLocalToParentTransformation(), pRed, pGreen, pBlue, pAlpha);
            } else {
                add(pTextureRegion, pEntity.getX(), pEntity.getY(), pWidth, pHeight, pRed, pGreen, pBlue, pAlpha);
            }
            this.mIndex++;
        }
    }

    public void draw(ITextureRegion pTextureRegion, IEntity pEntity, float pWidth, float pHeight, float pColorABGRPackedInt) {
        if (pEntity.isVisible()) {
            assertCapacity();
            assertTexture(pTextureRegion);
            if (pEntity.isRotatedOrScaledOrSkewed()) {
                addWithPackedColor(pTextureRegion, pWidth, pHeight, pEntity.getLocalToParentTransformation(), pColorABGRPackedInt);
            } else {
                addWithPackedColor(pTextureRegion, pEntity.getX(), pEntity.getY(), pWidth, pHeight, pColorABGRPackedInt);
            }
            this.mIndex++;
        }
    }

    public void drawWithoutChecks(ITextureRegion pTextureRegion, IEntity pEntity, float pWidth, float pHeight, float pColorABGRPackedInt) {
        if (pEntity.isVisible()) {
            if (pEntity.isRotatedOrScaledOrSkewed()) {
                addWithPackedColor(pTextureRegion, pWidth, pHeight, pEntity.getLocalToParentTransformation(), pColorABGRPackedInt);
            } else {
                addWithPackedColor(pTextureRegion, pEntity.getX(), pEntity.getY(), pWidth, pHeight, pColorABGRPackedInt);
            }
            this.mIndex++;
        }
    }

    public void submit() {
        onSubmit();
    }

    protected void onSubmit() {
        this.mVertices = this.mIndex * 6;
        this.mSpriteBatchVertexBufferObject.setDirtyOnHardware();
        this.mIndex = 0;
        this.mSpriteBatchVertexBufferObject.setBufferDataOffset(0);
    }

    private void assertCapacity(int pIndex) {
        if (pIndex >= this.mCapacity) {
            throw new IllegalStateException("This supplied pIndex: '" + pIndex + "' is exceeding the capacity: '" + this.mCapacity + "' of this SpriteBatch!");
        }
    }

    private void assertCapacity() {
        if (this.mIndex == this.mCapacity) {
            throw new IllegalStateException("This SpriteBatch has already reached its capacity (" + this.mCapacity + ") !");
        }
    }

    protected void assertTexture(ITextureRegion pTextureRegion) {
        if (pTextureRegion.getTexture() != this.mTexture) {
            throw new IllegalArgumentException("The supplied Texture does match the Texture of this SpriteBatch!");
        }
    }

    protected void add(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pRed, float pGreen, float pBlue, float pAlpha) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postRotate(pRotation);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        add(pTextureRegion, pWidth, pHeight, TRANSFORATION_TMP, pRed, pGreen, pBlue, pAlpha);
    }

    protected void addWithPackedColor(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pColorABGRPackedInt) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postRotate(pRotation);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        addWithPackedColor(pTextureRegion, pWidth, pHeight, TRANSFORATION_TMP, pColorABGRPackedInt);
    }

    protected void add(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY, float pRed, float pGreen, float pBlue, float pAlpha) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postScale(pScaleX, pScaleY);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        add(pTextureRegion, pWidth, pHeight, TRANSFORATION_TMP, pRed, pGreen, pBlue, pAlpha);
    }

    protected void addWithPackedColor(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY, float pColorABGRPackedInt) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postScale(pScaleX, pScaleY);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        addWithPackedColor(pTextureRegion, pWidth, pHeight, TRANSFORATION_TMP, pColorABGRPackedInt);
    }

    protected void add(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY, float pRed, float pGreen, float pBlue, float pAlpha) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postScale(pScaleX, pScaleY);
        TRANSFORATION_TMP.postRotate(pRotation);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        add(pTextureRegion, pWidth, pHeight, TRANSFORATION_TMP, pRed, pGreen, pBlue, pAlpha);
    }

    protected void addWithPackedColor(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY, float pColorABGRPackedInt) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postScale(pScaleX, pScaleY);
        TRANSFORATION_TMP.postRotate(pRotation);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        addWithPackedColor(pTextureRegion, pWidth, pHeight, TRANSFORATION_TMP, pColorABGRPackedInt);
    }

    protected void add(ITextureRegion pTextureRegion, float pWidth, float pHeight, Transformation pTransformation, float pRed, float pGreen, float pBlue, float pAlpha) {
        VERTICES_TMP[0] = 0.0f;
        VERTICES_TMP[1] = 0.0f;
        VERTICES_TMP[2] = 0.0f;
        VERTICES_TMP[3] = pHeight;
        VERTICES_TMP[4] = pWidth;
        VERTICES_TMP[5] = 0.0f;
        VERTICES_TMP[6] = pWidth;
        VERTICES_TMP[7] = pHeight;
        pTransformation.transform(VERTICES_TMP);
        addInner(pTextureRegion, VERTICES_TMP[0], VERTICES_TMP[1], VERTICES_TMP[2], VERTICES_TMP[3], VERTICES_TMP[4], VERTICES_TMP[5], VERTICES_TMP[6], VERTICES_TMP[7], pRed, pGreen, pBlue, pAlpha);
    }

    protected void addWithPackedColor(ITextureRegion pTextureRegion, float pWidth, float pHeight, Transformation pTransformation, float pColorABGRPackedInt) {
        VERTICES_TMP[0] = 0.0f;
        VERTICES_TMP[1] = 0.0f;
        VERTICES_TMP[2] = 0.0f;
        VERTICES_TMP[3] = pHeight;
        VERTICES_TMP[4] = pWidth;
        VERTICES_TMP[5] = 0.0f;
        VERTICES_TMP[6] = pWidth;
        VERTICES_TMP[7] = pHeight;
        pTransformation.transform(VERTICES_TMP);
        this.mSpriteBatchVertexBufferObject.addWithPackedColor(pTextureRegion, VERTICES_TMP[0], VERTICES_TMP[1], VERTICES_TMP[2], VERTICES_TMP[3], VERTICES_TMP[4], VERTICES_TMP[5], VERTICES_TMP[6], VERTICES_TMP[7], pColorABGRPackedInt);
    }

    protected void add(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRed, float pGreen, float pBlue, float pAlpha) {
        addInner(pTextureRegion, pX, pY, pX + pWidth, pY + pHeight, pRed, pGreen, pBlue, pAlpha);
    }

    protected void add(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pColorABGRPackedInt) {
        this.mSpriteBatchVertexBufferObject.addWithPackedColor(pTextureRegion, pX, pY, pX + pWidth, pY + pHeight, pColorABGRPackedInt);
    }

    protected void addWithPackedColor(ITextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pColorABGRPackedInt) {
        this.mSpriteBatchVertexBufferObject.addWithPackedColor(pTextureRegion, pX, pY, pX + pWidth, pY + pHeight, pColorABGRPackedInt);
    }

    private void addInner(ITextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mSpriteBatchVertexBufferObject.addWithPackedColor(pTextureRegion, pX1, pY1, pX2, pY2, ColorUtils.convertRGBAToABGRPackedFloat(pRed, pGreen, pBlue, pAlpha));
    }

    private void addInner(ITextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mSpriteBatchVertexBufferObject.addWithPackedColor(pTextureRegion, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, ColorUtils.convertRGBAToABGRPackedFloat(pRed, pGreen, pBlue, pAlpha));
    }
}
