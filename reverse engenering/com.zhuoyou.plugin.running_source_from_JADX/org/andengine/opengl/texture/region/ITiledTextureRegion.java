package org.andengine.opengl.texture.region;

public interface ITiledTextureRegion extends ITextureRegion {
    ITiledTextureRegion deepCopy();

    int getCurrentTileIndex();

    float getHeight(int i);

    float getScale(int i);

    ITextureRegion getTextureRegion(int i);

    float getTextureX(int i);

    float getTextureY(int i);

    int getTileCount();

    float getU(int i);

    float getU2(int i);

    float getV(int i);

    float getV2(int i);

    float getWidth(int i);

    boolean isRotated(int i);

    boolean isScaled(int i);

    void nextTile();

    void set(int i, float f, float f2, float f3, float f4);

    void setCurrentTileIndex(int i);

    void setTextureHeight(int i, float f);

    void setTexturePosition(int i, float f, float f2);

    void setTextureSize(int i, float f, float f2);

    void setTextureWidth(int i, float f);

    void setTextureX(int i, float f);

    void setTextureY(int i, float f);
}
