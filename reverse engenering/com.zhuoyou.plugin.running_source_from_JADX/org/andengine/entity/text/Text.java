package org.andengine.entity.text;

import java.util.ArrayList;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.text.exception.OutOfCharactersException;
import org.andengine.entity.text.vbo.HighPerformanceTextVertexBufferObject;
import org.andengine.entity.text.vbo.ITextVertexBufferObject;
import org.andengine.opengl.font.FontUtils;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.list.FloatArrayList;
import org.andengine.util.adt.list.IFloatList;

public class Text extends RectangularShape {
    public static final int COLOR_INDEX = 2;
    public static final float LEADING_DEFAULT = 0.0f;
    public static final int LETTER_SIZE = 30;
    public static final int TEXTURECOORDINATES_INDEX_U = 3;
    public static final int TEXTURECOORDINATES_INDEX_V = 4;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(3).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(1, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, 5121, true).add(3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, 5126, false).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 5;
    public static final int VERTEX_STRIDE = 20;
    public static final int VERTICES_PER_LETTER = 6;
    protected final int mCharactersMaximum;
    protected int mCharactersToDraw;
    protected final IFont mFont;
    protected float mLineAlignmentWidth;
    protected float mLineWidthMaximum;
    protected IFloatList mLineWidths;
    protected ArrayList<CharSequence> mLines;
    protected CharSequence mText;
    protected TextOptions mTextOptions;
    protected final ITextVertexBufferObject mTextVertexBufferObject;
    protected final int mVertexCount;
    protected int mVertexCountToDraw;

    public Text(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pFont, pText, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pFont, pText, new TextOptions(), pVertexBufferObjectManager, pDrawType);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, new TextOptions(), pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pFont, pText, pText.length(), pTextOptions, pVertexBufferObjectManager, pDrawType);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, pText.length(), pTextOptions, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pFont, pText, pCharactersMaximum, new TextOptions(), pVertexBufferObjectManager, pDrawType);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, pCharactersMaximum, new TextOptions(), pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, (ITextVertexBufferObject) new HighPerformanceTextVertexBufferObject(pVertexBufferObjectManager, pCharactersMaximum * 30, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        this(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, (ITextVertexBufferObject) new HighPerformanceTextVertexBufferObject(pVertexBufferObjectManager, pCharactersMaximum * 30, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, ITextVertexBufferObject pTextVertexBufferObject) {
        this(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pTextVertexBufferObject, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    public Text(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, ITextVertexBufferObject pTextVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, 0.0f, 0.0f, pShaderProgram);
        this.mLines = new ArrayList(1);
        this.mLineWidths = new FloatArrayList(1);
        this.mFont = pFont;
        this.mTextOptions = pTextOptions;
        this.mCharactersMaximum = pCharactersMaximum;
        this.mVertexCount = this.mCharactersMaximum * 6;
        this.mTextVertexBufferObject = pTextVertexBufferObject;
        onUpdateColor();
        setText(pText);
        setBlendingEnabled(true);
        initBlendFunction(this.mFont.getTexture());
    }

    public IFont getFont() {
        return this.mFont;
    }

    public int getCharactersMaximum() {
        return this.mCharactersMaximum;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public void setText(CharSequence pText) throws OutOfCharactersException {
        this.mText = pText;
        IFont font = this.mFont;
        this.mLines.clear();
        this.mLineWidths.clear();
        if (this.mTextOptions.mAutoWrap == AutoWrap.NONE) {
            this.mLines = (ArrayList) FontUtils.splitLines(this.mText, this.mLines);
        } else {
            this.mLines = (ArrayList) FontUtils.splitLines(this.mFont, this.mText, this.mLines, this.mTextOptions.mAutoWrap, this.mTextOptions.mAutoWrapWidth);
        }
        int lineCount = this.mLines.size();
        float maximumLineWidth = 0.0f;
        for (int i = 0; i < lineCount; i++) {
            float lineWidth = FontUtils.measureText(font, (CharSequence) this.mLines.get(i));
            maximumLineWidth = Math.max(maximumLineWidth, lineWidth);
            this.mLineWidths.add(lineWidth);
        }
        this.mLineWidthMaximum = maximumLineWidth;
        if (this.mTextOptions.mAutoWrap == AutoWrap.NONE) {
            this.mLineAlignmentWidth = this.mLineWidthMaximum;
        } else {
            this.mLineAlignmentWidth = this.mTextOptions.mAutoWrapWidth;
        }
        this.mWidth = this.mLineAlignmentWidth;
        this.mHeight = (((float) lineCount) * font.getLineHeight()) + (((float) (lineCount - 1)) * this.mTextOptions.mLeading);
        this.mRotationCenterX = this.mWidth * 0.5f;
        this.mRotationCenterY = this.mHeight * 0.5f;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
        onUpdateVertices();
    }

    public ArrayList<CharSequence> getLines() {
        return this.mLines;
    }

    public IFloatList getLineWidths() {
        return this.mLineWidths;
    }

    public float getLineAlignmentWidth() {
        return this.mLineAlignmentWidth;
    }

    public float getLineWidthMaximum() {
        return this.mLineWidthMaximum;
    }

    public float getLeading() {
        return this.mTextOptions.mLeading;
    }

    public void setLeading(float pLeading) {
        this.mTextOptions.mLeading = pLeading;
        invalidateText();
    }

    public HorizontalAlign getHorizontalAlign() {
        return this.mTextOptions.mHorizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign pHorizontalAlign) {
        this.mTextOptions.mHorizontalAlign = pHorizontalAlign;
        invalidateText();
    }

    public AutoWrap getAutoWrap() {
        return this.mTextOptions.mAutoWrap;
    }

    public void setAutoWrap(AutoWrap pAutoWrap) {
        this.mTextOptions.mAutoWrap = pAutoWrap;
        invalidateText();
    }

    public float getAutoWrapWidth() {
        return this.mTextOptions.mAutoWrapWidth;
    }

    public void setAutoWrapWidth(float pAutoWrapWidth) {
        this.mTextOptions.mAutoWrapWidth = pAutoWrapWidth;
        invalidateText();
    }

    public TextOptions getTextOptions() {
        return this.mTextOptions;
    }

    public void setTextOptions(TextOptions pTextOptions) {
        this.mTextOptions = pTextOptions;
    }

    public void setCharactersToDraw(int pCharactersToDraw) {
        if (pCharactersToDraw > this.mCharactersMaximum) {
            throw new OutOfCharactersException("Characters: maximum: '" + this.mCharactersMaximum + "' required: '" + pCharactersToDraw + "'.");
        }
        this.mCharactersToDraw = pCharactersToDraw;
        this.mVertexCountToDraw = pCharactersToDraw * 6;
    }

    public ITextVertexBufferObject getVertexBufferObject() {
        return this.mTextVertexBufferObject;
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        this.mFont.getTexture().bind(pGLState);
        this.mTextVertexBufferObject.bind(pGLState, this.mShaderProgram);
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        this.mTextVertexBufferObject.draw(4, this.mVertexCountToDraw);
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        this.mTextVertexBufferObject.unbind(pGLState, this.mShaderProgram);
        super.postDraw(pGLState, pCamera);
    }

    protected void onUpdateColor() {
        this.mTextVertexBufferObject.onUpdateColor(this);
    }

    protected void onUpdateVertices() {
        this.mTextVertexBufferObject.onUpdateVertices(this);
    }

    public void invalidateText() {
        setText(this.mText);
    }
}
