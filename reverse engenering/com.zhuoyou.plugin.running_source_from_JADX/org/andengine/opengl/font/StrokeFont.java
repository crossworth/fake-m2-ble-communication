package org.andengine.opengl.font;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import org.andengine.opengl.texture.ITexture;
import org.andengine.util.color.Color;

public class StrokeFont extends Font {
    private final boolean mStrokeOnly;
    private final Paint mStrokePaint;
    private final float mStrokeWidth;

    public StrokeFont(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, Color pColor, float pStrokeWidth, Color pStrokeColor) {
        this(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColor.getARGBPackedInt(), pStrokeWidth, pStrokeColor.getARGBPackedInt());
    }

    public StrokeFont(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColorARGBPackedInt, float pStrokeWidth, int pStrokeColorARGBPackedInt) {
        this(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColorARGBPackedInt, pStrokeWidth, pStrokeColorARGBPackedInt, false);
    }

    public StrokeFont(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, Color pColor, float pStrokeWidth, Color pStrokeColor, boolean pStrokeOnly) {
        this(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColor.getARGBPackedInt(), pStrokeWidth, pStrokeColor.getARGBPackedInt(), pStrokeOnly);
    }

    public StrokeFont(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColorARGBPackedInt, float pStrokeWidth, int pStrokeColorARGBPackedInt, boolean pStrokeOnly) {
        super(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColorARGBPackedInt);
        this.mStrokeWidth = pStrokeWidth;
        this.mStrokePaint = new Paint();
        this.mStrokePaint.setTypeface(pTypeface);
        this.mStrokePaint.setStyle(Style.STROKE);
        this.mStrokePaint.setStrokeWidth(pStrokeWidth);
        this.mStrokePaint.setColor(pStrokeColorARGBPackedInt);
        this.mStrokePaint.setTextSize(pSize);
        this.mStrokePaint.setAntiAlias(pAntiAlias);
        this.mStrokeOnly = pStrokeOnly;
    }

    protected void updateTextBounds(String pCharacterAsString) {
        this.mStrokePaint.getTextBounds(pCharacterAsString, 0, 1, this.mTextBounds);
        int inset = -((int) Math.floor((double) (this.mStrokeWidth * 0.5f)));
        this.mTextBounds.inset(inset, inset);
    }

    protected void drawLetter(String pCharacterAsString, float pLeft, float pTop) {
        if (!this.mStrokeOnly) {
            super.drawLetter(pCharacterAsString, pLeft, pTop);
        }
        this.mCanvas.drawText(pCharacterAsString, pLeft + 1.0f, 1.0f + pTop, this.mStrokePaint);
    }
}
