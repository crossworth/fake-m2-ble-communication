package org.andengine.opengl.font;

import android.util.SparseIntArray;

public class Letter {
    public final float mAdvance;
    public final char mCharacter;
    public final int mHeight;
    private SparseIntArray mKernings;
    public final float mOffsetX;
    public final float mOffsetY;
    public final int mTextureX;
    public final int mTextureY;
    public final float mU;
    public final float mU2;
    public final float mV;
    public final float mV2;
    private final boolean mWhitespace;
    public final int mWidth;

    Letter(char pCharacter, float pAdvance) {
        this(pCharacter, true, 0, 0, 0, 0, 0.0f, 0.0f, pAdvance, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    Letter(char pCharacter, int pTextureX, int pTextureY, int pWidth, int pHeight, float pOffsetX, float pOffsetY, float pAdvance, float pU, float pV, float pU2, float pV2) {
        this(pCharacter, false, pTextureX, pTextureY, pWidth, pHeight, pOffsetX, pOffsetY, pAdvance, pU, pV, pU2, pV2);
    }

    private Letter(char pCharacter, boolean pWhitespace, int pTextureX, int pTextureY, int pWidth, int pHeight, float pOffsetX, float pOffsetY, float pAdvance, float pU, float pV, float pU2, float pV2) {
        this.mCharacter = pCharacter;
        this.mWhitespace = pWhitespace;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        this.mTextureX = pTextureX;
        this.mTextureY = pTextureY;
        this.mOffsetX = pOffsetX;
        this.mOffsetY = pOffsetY;
        this.mAdvance = pAdvance;
        this.mU = pU;
        this.mV = pV;
        this.mU2 = pU2;
        this.mV2 = pV2;
    }

    public int getKerning(int pCharacter) {
        if (this.mKernings == null) {
            return 0;
        }
        return this.mKernings.get(pCharacter, 0);
    }

    public boolean isWhitespace() {
        return this.mWhitespace;
    }

    public int hashCode() {
        return this.mCharacter + 31;
    }

    public boolean equals(Object pObject) {
        if (this == pObject) {
            return true;
        }
        if (pObject == null) {
            return false;
        }
        if (getClass() != pObject.getClass()) {
            return false;
        }
        if (this.mCharacter != ((Letter) pObject).mCharacter) {
            return false;
        }
        return true;
    }

    public String toString() {
        return getClass().getSimpleName() + "[Character=" + this.mCharacter + ", Whitespace=" + this.mWhitespace + ", TextureX=" + this.mTextureX + ", TextureY=" + this.mTextureY + ", Width=" + this.mWidth + ", Height=" + this.mHeight + ", OffsetX=" + this.mOffsetX + ", OffsetY=" + this.mOffsetY + ", Advance=" + this.mAdvance + ", U=" + this.mU + ", V=" + this.mV + ", U2=" + this.mU2 + ", V2=" + this.mV2 + ", Kernings=" + this.mKernings + "]";
    }

    void addKerning(int pCharacter, int pKerning) {
        if (this.mKernings == null) {
            this.mKernings = new SparseIntArray();
        }
        this.mKernings.put(pCharacter, pKerning);
    }
}
