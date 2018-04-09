package org.andengine.opengl.texture.atlas.source;

public abstract class BaseTextureAtlasSource implements ITextureAtlasSource {
    protected int mTextureHeight;
    protected int mTextureWidth;
    protected int mTextureX;
    protected int mTextureY;

    public BaseTextureAtlasSource(int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight) {
        this.mTextureX = pTextureX;
        this.mTextureY = pTextureY;
        this.mTextureWidth = pTextureWidth;
        this.mTextureHeight = pTextureHeight;
    }

    public int getTextureX() {
        return this.mTextureX;
    }

    public int getTextureY() {
        return this.mTextureY;
    }

    public void setTextureX(int pTextureX) {
        this.mTextureX = pTextureX;
    }

    public void setTextureY(int pTextureY) {
        this.mTextureY = pTextureY;
    }

    public int getTextureWidth() {
        return this.mTextureWidth;
    }

    public int getTextureHeight() {
        return this.mTextureHeight;
    }

    public void setTextureWidth(int pTextureWidth) {
        this.mTextureWidth = pTextureWidth;
    }

    public void setTextureHeight(int pTextureHeight) {
        this.mTextureHeight = pTextureHeight;
    }

    public String toString() {
        return getClass().getSimpleName() + "( " + getTextureWidth() + "x" + getTextureHeight() + " @ " + this.mTextureX + "/" + this.mTextureY + " )";
    }
}
