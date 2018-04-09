package org.andengine.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;

public class TextureRegion extends BaseTextureRegion {
    private static final float SCALE_DEFAULT = 1.0f;
    protected final boolean mRotated;
    protected final float mScale;
    protected float mTextureHeight;
    protected float mTextureWidth;
    protected float mTextureX;
    protected float mTextureY;
    protected float mU;
    protected float mU2;
    protected float mV;
    protected float mV2;

    public TextureRegion(ITexture pTexture, float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight) {
        this(pTexture, pTextureX, pTextureY, pTextureWidth, pTextureHeight, false);
    }

    public TextureRegion(ITexture pTexture, float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight, boolean pRotated) {
        this(pTexture, pTextureX, pTextureY, pTextureWidth, pTextureHeight, 1.0f, pRotated);
    }

    public TextureRegion(ITexture pTexture, float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight, float pScale) {
        this(pTexture, pTextureX, pTextureY, pTextureWidth, pTextureHeight, pScale, false);
    }

    public TextureRegion(ITexture pTexture, float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight, float pScale, boolean pRotated) {
        super(pTexture);
        this.mTextureX = pTextureX;
        this.mTextureY = pTextureY;
        if (pRotated) {
            this.mRotated = true;
            this.mTextureWidth = pTextureHeight;
            this.mTextureHeight = pTextureWidth;
        } else {
            this.mRotated = false;
            this.mTextureWidth = pTextureWidth;
            this.mTextureHeight = pTextureHeight;
        }
        this.mScale = pScale;
        updateUV();
    }

    public TextureRegion deepCopy() {
        if (this.mRotated) {
            return new TextureRegion(this.mTexture, this.mTextureX, this.mTextureY, this.mTextureHeight, this.mTextureWidth, this.mScale, this.mRotated);
        }
        return new TextureRegion(this.mTexture, this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight, this.mScale, this.mRotated);
    }

    public float getTextureX() {
        return this.mTextureX;
    }

    public float getTextureY() {
        return this.mTextureY;
    }

    public void setTextureX(float pTextureX) {
        this.mTextureX = pTextureX;
        updateUV();
    }

    public void setTextureY(float pTextureY) {
        this.mTextureY = pTextureY;
        updateUV();
    }

    public void setTexturePosition(float pTextureX, float pTextureY) {
        this.mTextureX = pTextureX;
        this.mTextureY = pTextureY;
        updateUV();
    }

    public float getWidth() {
        if (this.mRotated) {
            return this.mTextureHeight * this.mScale;
        }
        return this.mTextureWidth * this.mScale;
    }

    public float getHeight() {
        if (this.mRotated) {
            return this.mTextureWidth * this.mScale;
        }
        return this.mTextureHeight * this.mScale;
    }

    public void setTextureWidth(float pTextureWidth) {
        this.mTextureWidth = pTextureWidth;
        updateUV();
    }

    public void setTextureHeight(float pTextureHeight) {
        this.mTextureHeight = pTextureHeight;
        updateUV();
    }

    public void setTextureSize(float pTextureWidth, float pTextureHeight) {
        this.mTextureWidth = pTextureWidth;
        this.mTextureHeight = pTextureHeight;
        updateUV();
    }

    public void set(float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight) {
        this.mTextureX = pTextureX;
        this.mTextureY = pTextureY;
        this.mTextureWidth = pTextureWidth;
        this.mTextureHeight = pTextureHeight;
        updateUV();
    }

    public float getU() {
        return this.mU;
    }

    public float getU2() {
        return this.mU2;
    }

    public float getV() {
        return this.mV;
    }

    public float getV2() {
        return this.mV2;
    }

    public boolean isScaled() {
        return this.mScale != 1.0f;
    }

    public float getScale() {
        return this.mScale;
    }

    public boolean isRotated() {
        return this.mRotated;
    }

    public void updateUV() {
        ITexture texture = this.mTexture;
        float textureWidth = (float) texture.getWidth();
        float textureHeight = (float) texture.getHeight();
        float x = getTextureX();
        float y = getTextureY();
        this.mU = x / textureWidth;
        this.mU2 = (this.mTextureWidth + x) / textureWidth;
        this.mV = y / textureHeight;
        this.mV2 = (this.mTextureHeight + y) / textureHeight;
    }
}
