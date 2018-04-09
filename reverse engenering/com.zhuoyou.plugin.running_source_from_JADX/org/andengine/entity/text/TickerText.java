package org.andengine.entity.text;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.text.exception.OutOfCharactersException;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

public class TickerText extends Text {
    private int mCharactersVisible;
    private float mDuration = (((float) this.mCharactersToDraw) * this.mTickerTextOptions.mCharactersPerSecond);
    private float mSecondsElapsed;
    private final TickerTextOptions mTickerTextOptions;

    public static class TickerTextOptions extends TextOptions {
        float mCharactersPerSecond;
        boolean mReverse;

        public TickerTextOptions(float pCharactersPerSecond) {
            this(pCharactersPerSecond, false);
        }

        public TickerTextOptions(float pCharactersPerSecond, boolean pReverse) {
            this(HorizontalAlign.LEFT, pCharactersPerSecond, pReverse);
        }

        public TickerTextOptions(HorizontalAlign pHorizontalAlign, float pCharactersPerSecond) {
            this(AutoWrap.NONE, 0.0f, pHorizontalAlign, 0.0f, pCharactersPerSecond, false);
        }

        public TickerTextOptions(HorizontalAlign pHorizontalAlign, float pCharactersPerSecond, boolean pReverse) {
            this(AutoWrap.NONE, 0.0f, pHorizontalAlign, 0.0f, pCharactersPerSecond, pReverse);
        }

        public TickerTextOptions(AutoWrap pAutoWrap, float pAutoWrapWidth, HorizontalAlign pHorizontalAlign, float pCharactersPerSecond) {
            this(pAutoWrap, pAutoWrapWidth, pHorizontalAlign, 0.0f, pCharactersPerSecond, false);
        }

        public TickerTextOptions(AutoWrap pAutoWrap, float pAutoWrapWidth, HorizontalAlign pHorizontalAlign, float pLeading, float pCharactersPerSecond) {
            this(pAutoWrap, pAutoWrapWidth, pHorizontalAlign, pLeading, pCharactersPerSecond, false);
        }

        public TickerTextOptions(AutoWrap pAutoWrap, float pAutoWrapWidth, HorizontalAlign pHorizontalAlign, float pLeading, float pCharactersPerSecond, boolean pReverse) {
            super(pAutoWrap, pAutoWrapWidth, pHorizontalAlign, pLeading);
            this.mCharactersPerSecond = pCharactersPerSecond;
            this.mReverse = pReverse;
        }

        public float getCharactersPerSecond() {
            return this.mCharactersPerSecond;
        }

        public void setCharactersPerSecond(float pCharactersPerSecond) {
            this.mCharactersPerSecond = pCharactersPerSecond;
        }

        public boolean isReverse() {
            return this.mReverse;
        }

        public void setReverse(boolean pReverse) {
            this.mReverse = pReverse;
        }
    }

    public TickerText(float pX, float pY, IFont pFont, String pText, TickerTextOptions pTickerTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, (CharSequence) pText, (TextOptions) pTickerTextOptions, pVertexBufferObjectManager);
        this.mTickerTextOptions = pTickerTextOptions;
    }

    public TickerTextOptions getTextOptions() {
        return (TickerTextOptions) super.getTextOptions();
    }

    public boolean isReverse() {
        return getTextOptions().mReverse;
    }

    public void setReverse(boolean pReverse) {
        this.mTickerTextOptions.mReverse = pReverse;
    }

    public float getCharactersPerSecond() {
        return this.mTickerTextOptions.mCharactersPerSecond;
    }

    public void setCharactersPerSecond(float pCharactersPerSecond) {
        this.mTickerTextOptions.mCharactersPerSecond = pCharactersPerSecond;
        this.mDuration = ((float) this.mCharactersToDraw) * pCharactersPerSecond;
    }

    public int getCharactersVisible() {
        return this.mCharactersVisible;
    }

    public void setText(CharSequence pText) throws OutOfCharactersException {
        super.setText(pText);
        if (this.mTickerTextOptions != null) {
            this.mDuration = ((float) this.mCharactersToDraw) * this.mTickerTextOptions.mCharactersPerSecond;
        }
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (this.mTickerTextOptions.mReverse) {
            if (this.mCharactersVisible < this.mCharactersToDraw) {
                this.mSecondsElapsed = Math.max(0.0f, this.mSecondsElapsed - pSecondsElapsed);
                this.mCharactersVisible = (int) (this.mSecondsElapsed * this.mTickerTextOptions.mCharactersPerSecond);
            }
        } else if (this.mCharactersVisible < this.mCharactersToDraw) {
            this.mSecondsElapsed = Math.min(this.mDuration, this.mSecondsElapsed + pSecondsElapsed);
            this.mCharactersVisible = (int) (this.mSecondsElapsed * this.mTickerTextOptions.mCharactersPerSecond);
        }
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        this.mTextVertexBufferObject.draw(4, this.mCharactersVisible * 6);
    }

    public void reset() {
        super.reset();
        this.mCharactersVisible = 0;
        this.mSecondsElapsed = 0.0f;
    }
}
