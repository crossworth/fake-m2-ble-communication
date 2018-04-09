package org.andengine.opengl.font;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.SparseArray;
import java.util.ArrayList;
import org.andengine.opengl.font.exception.FontException;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.map.SparseArrayUtils;
import org.andengine.util.color.Color;
import org.andengine.util.math.MathUtils;

public class Font implements IFont {
    protected static final int LETTER_TEXTURE_PADDING = 1;
    private final Paint mBackgroundPaint;
    protected final Canvas mCanvas;
    private int mCurrentTextureX;
    private int mCurrentTextureY;
    private int mCurrentTextureYHeightMax;
    private final FontManager mFontManager;
    protected final FontMetrics mFontMetrics;
    private final ArrayList<Letter> mLettersPendingToBeDrawnToTexture;
    private final SparseArray<Letter> mManagedCharacterToLetterMap;
    protected final Paint mPaint;
    protected final Rect mTextBounds;
    protected final float[] mTextWidthContainer;
    private final ITexture mTexture;
    private final int mTextureHeight;
    private final int mTextureWidth;

    public Font(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, Color pColor) {
        this(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColor.getARGBPackedInt());
    }

    public Font(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColorARGBPackedInt) {
        this.mCurrentTextureX = 1;
        this.mCurrentTextureY = 1;
        this.mManagedCharacterToLetterMap = new SparseArray();
        this.mLettersPendingToBeDrawnToTexture = new ArrayList();
        this.mCanvas = new Canvas();
        this.mTextBounds = new Rect();
        this.mTextWidthContainer = new float[1];
        this.mFontManager = pFontManager;
        this.mTexture = pTexture;
        this.mTextureWidth = pTexture.getWidth();
        this.mTextureHeight = pTexture.getHeight();
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setColor(Color.TRANSPARENT_ARGB_PACKED_INT);
        this.mBackgroundPaint.setStyle(Style.FILL);
        this.mPaint = new Paint();
        this.mPaint.setTypeface(pTypeface);
        this.mPaint.setColor(pColorARGBPackedInt);
        this.mPaint.setTextSize(pSize);
        this.mPaint.setAntiAlias(pAntiAlias);
        this.mFontMetrics = this.mPaint.getFontMetrics();
    }

    public float getLeading() {
        return this.mFontMetrics.leading;
    }

    public float getAscent() {
        return this.mFontMetrics.ascent;
    }

    public float getDescent() {
        return this.mFontMetrics.descent;
    }

    public ITexture getTexture() {
        return this.mTexture;
    }

    public void load() {
        this.mTexture.load();
        this.mFontManager.loadFont(this);
    }

    public void unload() {
        this.mTexture.unload();
        this.mFontManager.unloadFont(this);
    }

    public float getLineHeight() {
        return (-getAscent()) + getDescent();
    }

    public synchronized Letter getLetter(char pCharacter) throws FontException {
        Letter letter;
        letter = (Letter) this.mManagedCharacterToLetterMap.get(pCharacter);
        if (letter == null) {
            letter = createLetter(pCharacter);
            this.mLettersPendingToBeDrawnToTexture.add(letter);
            this.mManagedCharacterToLetterMap.put(pCharacter, letter);
        }
        return letter;
    }

    public synchronized void invalidateLetters() {
        ArrayList<Letter> lettersPendingToBeDrawnToTexture = this.mLettersPendingToBeDrawnToTexture;
        SparseArray<Letter> managedCharacterToLetterMap = this.mManagedCharacterToLetterMap;
        for (int i = managedCharacterToLetterMap.size() - 1; i >= 0; i--) {
            lettersPendingToBeDrawnToTexture.add(managedCharacterToLetterMap.valueAt(i));
        }
    }

    private float getLetterAdvance(String pCharacterAsString) {
        this.mPaint.getTextWidths(pCharacterAsString, this.mTextWidthContainer);
        return this.mTextWidthContainer[0];
    }

    protected Bitmap getLetterBitmap(Letter pLetter) throws FontException {
        String characterAsString = String.valueOf(pLetter.mCharacter);
        Bitmap bitmap = Bitmap.createBitmap(pLetter.mWidth + 2, pLetter.mHeight + 2, Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmap);
        this.mCanvas.drawRect(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight(), this.mBackgroundPaint);
        drawLetter(characterAsString, -pLetter.mOffsetX, -(pLetter.mOffsetY + getAscent()));
        return bitmap;
    }

    protected void drawLetter(String pCharacterAsString, float pLeft, float pTop) {
        this.mCanvas.drawText(pCharacterAsString, pLeft + 1.0f, 1.0f + pTop, this.mPaint);
    }

    public void prepareLetters(char... pCharacters) throws FontException {
        for (char character : pCharacters) {
            getLetter(character);
        }
    }

    private Letter createLetter(char pCharacter) throws FontException {
        String characterAsString = String.valueOf(pCharacter);
        float textureWidth = (float) this.mTextureWidth;
        float textureHeight = (float) this.mTextureHeight;
        updateTextBounds(characterAsString);
        int letterLeft = this.mTextBounds.left;
        int letterTop = this.mTextBounds.top;
        int letterWidth = this.mTextBounds.width();
        int letterHeight = this.mTextBounds.height();
        float advance = getLetterAdvance(characterAsString);
        boolean whitespace = Character.isWhitespace(pCharacter) || letterWidth == 0 || letterHeight == 0;
        if (whitespace) {
            return new Letter(pCharacter, advance);
        }
        if (((float) ((this.mCurrentTextureX + 1) + letterWidth)) >= textureWidth) {
            this.mCurrentTextureX = 0;
            this.mCurrentTextureY += this.mCurrentTextureYHeightMax + 2;
            this.mCurrentTextureYHeightMax = 0;
        }
        if (((float) (this.mCurrentTextureY + letterHeight)) >= textureHeight) {
            throw new FontException("Not enough space for " + Letter.class.getSimpleName() + ": '" + pCharacter + "' on the " + this.mTexture.getClass().getSimpleName() + ". Existing Letters: " + SparseArrayUtils.toString(this.mManagedCharacterToLetterMap));
        }
        this.mCurrentTextureYHeightMax = Math.max(letterHeight, this.mCurrentTextureYHeightMax);
        this.mCurrentTextureX++;
        Letter letter = new Letter(pCharacter, this.mCurrentTextureX - 1, this.mCurrentTextureY - 1, letterWidth, letterHeight, (float) letterLeft, ((float) letterTop) - getAscent(), advance, ((float) this.mCurrentTextureX) / textureWidth, ((float) this.mCurrentTextureY) / textureHeight, ((float) (this.mCurrentTextureX + letterWidth)) / textureWidth, ((float) (this.mCurrentTextureY + letterHeight)) / textureHeight);
        this.mCurrentTextureX += letterWidth + 1;
        return letter;
    }

    protected void updateTextBounds(String pCharacterAsString) {
        this.mPaint.getTextBounds(pCharacterAsString, 0, 1, this.mTextBounds);
    }

    public synchronized void update(GLState pGLState) {
        if (this.mTexture.isLoadedToHardware()) {
            ArrayList<Letter> lettersPendingToBeDrawnToTexture = this.mLettersPendingToBeDrawnToTexture;
            if (lettersPendingToBeDrawnToTexture.size() > 0) {
                this.mTexture.bind(pGLState);
                PixelFormat pixelFormat = this.mTexture.getPixelFormat();
                boolean preMultipyAlpha = this.mTexture.getTextureOptions().mPreMultiplyAlpha;
                for (int i = lettersPendingToBeDrawnToTexture.size() - 1; i >= 0; i--) {
                    Letter letter = (Letter) lettersPendingToBeDrawnToTexture.get(i);
                    if (!letter.isWhitespace()) {
                        boolean useDefaultAlignment;
                        Bitmap bitmap = getLetterBitmap(letter);
                        if (MathUtils.isPowerOfTwo(bitmap.getWidth()) && MathUtils.isPowerOfTwo(bitmap.getHeight()) && pixelFormat == PixelFormat.RGBA_8888) {
                            useDefaultAlignment = true;
                        } else {
                            useDefaultAlignment = false;
                        }
                        if (!useDefaultAlignment) {
                            GLES20.glPixelStorei(3317, 1);
                        }
                        if (preMultipyAlpha) {
                            GLUtils.texSubImage2D(3553, 0, letter.mTextureX, letter.mTextureY, bitmap);
                        } else {
                            pGLState.glTexSubImage2D(3553, 0, letter.mTextureX, letter.mTextureY, bitmap, pixelFormat);
                        }
                        if (!useDefaultAlignment) {
                            GLES20.glPixelStorei(3317, 4);
                        }
                        bitmap.recycle();
                    }
                }
                lettersPendingToBeDrawnToTexture.clear();
                System.gc();
            }
        }
    }
}
