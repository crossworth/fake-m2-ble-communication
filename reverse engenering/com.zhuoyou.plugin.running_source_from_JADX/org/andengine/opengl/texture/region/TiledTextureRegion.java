package org.andengine.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;

public class TiledTextureRegion extends BaseTextureRegion implements ITiledTextureRegion {
    protected int mCurrentTileIndex;
    protected final ITextureRegion[] mTextureRegions;
    protected final int mTileCount;

    public TiledTextureRegion(ITexture pTexture, ITextureRegion... pTextureRegions) {
        this(pTexture, true, pTextureRegions);
    }

    public TiledTextureRegion(ITexture pTexture, boolean pPerformSameTextureSanityCheck, ITextureRegion... pTextureRegions) {
        super(pTexture);
        this.mTextureRegions = pTextureRegions;
        this.mTileCount = this.mTextureRegions.length;
        if (pPerformSameTextureSanityCheck) {
            for (int i = this.mTileCount - 1; i >= 0; i--) {
                if (pTextureRegions[i].getTexture() != pTexture) {
                    throw new IllegalArgumentException("The " + ITextureRegion.class.getSimpleName() + ": '" + pTextureRegions[i].toString() + "' at index: '" + i + "' is not on the same " + ITexture.class.getSimpleName() + ": '" + pTextureRegions[i].getTexture().toString() + "' as the supplied " + ITexture.class.getSimpleName() + ": '" + pTexture.toString() + "'.");
                }
            }
        }
    }

    public static TiledTextureRegion create(ITexture pTexture, int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight, int pTileColumns, int pTileRows) {
        return create(pTexture, pTextureX, pTextureY, pTextureWidth, pTextureHeight, pTileColumns, pTileRows, false);
    }

    public static TiledTextureRegion create(ITexture pTexture, int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight, int pTileColumns, int pTileRows, boolean pRotated) {
        ITextureRegion[] textureRegions = new ITextureRegion[(pTileColumns * pTileRows)];
        int tileWidth = pTextureWidth / pTileColumns;
        int tileHeight = pTextureHeight / pTileRows;
        for (int tileColumn = 0; tileColumn < pTileColumns; tileColumn++) {
            for (int tileRow = 0; tileRow < pTileRows; tileRow++) {
                ITexture iTexture = pTexture;
                textureRegions[(tileRow * pTileColumns) + tileColumn] = new TextureRegion(iTexture, (float) (pTextureX + (tileColumn * tileWidth)), (float) (pTextureY + (tileRow * tileHeight)), (float) tileWidth, (float) tileHeight, pRotated);
            }
        }
        return new TiledTextureRegion(pTexture, false, textureRegions);
    }

    public TiledTextureRegion deepCopy() {
        int tileCount = this.mTileCount;
        ITextureRegion[] textureRegions = new ITextureRegion[tileCount];
        for (int i = 0; i < tileCount; i++) {
            textureRegions[i] = this.mTextureRegions[i].deepCopy();
        }
        return new TiledTextureRegion(this.mTexture, false, textureRegions);
    }

    public int getCurrentTileIndex() {
        return this.mCurrentTileIndex;
    }

    public void setCurrentTileIndex(int pCurrentTileIndex) {
        this.mCurrentTileIndex = pCurrentTileIndex;
    }

    public void nextTile() {
        this.mCurrentTileIndex++;
        if (this.mCurrentTileIndex >= this.mTileCount) {
            this.mCurrentTileIndex %= this.mTileCount;
        }
    }

    public ITextureRegion getTextureRegion(int pTileIndex) {
        return this.mTextureRegions[pTileIndex];
    }

    public int getTileCount() {
        return this.mTileCount;
    }

    public float getTextureX() {
        return this.mTextureRegions[this.mCurrentTileIndex].getTextureX();
    }

    public float getTextureX(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getTextureX();
    }

    public float getTextureY() {
        return this.mTextureRegions[this.mCurrentTileIndex].getTextureY();
    }

    public float getTextureY(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getTextureY();
    }

    public void setTextureX(float pTextureX) {
        this.mTextureRegions[this.mCurrentTileIndex].setTextureX(pTextureX);
    }

    public void setTextureX(int pTileIndex, float pTextureX) {
        this.mTextureRegions[pTileIndex].setTextureX(pTextureX);
    }

    public void setTextureY(float pTextureY) {
        this.mTextureRegions[this.mCurrentTileIndex].setTextureY(pTextureY);
    }

    public void setTextureY(int pTileIndex, float pTextureY) {
        this.mTextureRegions[pTileIndex].setTextureY(pTextureY);
    }

    public void setTexturePosition(float pTextureX, float pTextureY) {
        this.mTextureRegions[this.mCurrentTileIndex].setTexturePosition(pTextureX, pTextureY);
    }

    public void setTexturePosition(int pTileIndex, float pTextureX, float pTextureY) {
        this.mTextureRegions[pTileIndex].setTexturePosition(pTextureX, pTextureY);
    }

    public float getWidth() {
        return this.mTextureRegions[this.mCurrentTileIndex].getWidth();
    }

    public float getWidth(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getWidth();
    }

    public float getHeight() {
        return this.mTextureRegions[this.mCurrentTileIndex].getHeight();
    }

    public float getHeight(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getHeight();
    }

    public void setTextureWidth(float pTextureWidth) {
        this.mTextureRegions[this.mCurrentTileIndex].setTextureWidth(pTextureWidth);
    }

    public void setTextureWidth(int pTileIndex, float pTextureWidth) {
        this.mTextureRegions[pTileIndex].setTextureWidth(pTextureWidth);
    }

    public void setTextureHeight(float pTextureHeight) {
        this.mTextureRegions[this.mCurrentTileIndex].setTextureHeight(pTextureHeight);
    }

    public void setTextureHeight(int pTileIndex, float pTextureHeight) {
        this.mTextureRegions[pTileIndex].setTextureHeight(pTextureHeight);
    }

    public void setTextureSize(float pTextureWidth, float pTextureHeight) {
        this.mTextureRegions[this.mCurrentTileIndex].setTextureSize(pTextureWidth, pTextureHeight);
    }

    public void setTextureSize(int pTileIndex, float pTextureWidth, float pTextureHeight) {
        this.mTextureRegions[pTileIndex].setTextureSize(pTextureWidth, pTextureHeight);
    }

    public void set(float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight) {
        this.mTextureRegions[this.mCurrentTileIndex].set(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
    }

    public void set(int pTileIndex, float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight) {
        this.mTextureRegions[pTileIndex].set(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
    }

    public float getU() {
        return this.mTextureRegions[this.mCurrentTileIndex].getU();
    }

    public float getU(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getU();
    }

    public float getV() {
        return this.mTextureRegions[this.mCurrentTileIndex].getV();
    }

    public float getV(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getV();
    }

    public float getU2() {
        return this.mTextureRegions[this.mCurrentTileIndex].getU2();
    }

    public float getU2(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getU2();
    }

    public float getV2() {
        return this.mTextureRegions[this.mCurrentTileIndex].getV2();
    }

    public float getV2(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getV2();
    }

    public boolean isScaled() {
        return this.mTextureRegions[this.mCurrentTileIndex].isScaled();
    }

    public boolean isScaled(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].isScaled();
    }

    public float getScale() {
        return this.mTextureRegions[this.mCurrentTileIndex].getScale();
    }

    public float getScale(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].getScale();
    }

    public boolean isRotated() {
        return this.mTextureRegions[this.mCurrentTileIndex].isRotated();
    }

    public boolean isRotated(int pTileIndex) {
        return this.mTextureRegions[pTileIndex].isRotated();
    }
}
