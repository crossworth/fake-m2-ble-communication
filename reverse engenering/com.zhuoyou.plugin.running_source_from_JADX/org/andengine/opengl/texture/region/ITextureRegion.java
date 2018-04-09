package org.andengine.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;

public interface ITextureRegion {
    ITextureRegion deepCopy();

    float getHeight();

    float getScale();

    ITexture getTexture();

    float getTextureX();

    float getTextureY();

    float getU();

    float getU2();

    float getV();

    float getV2();

    float getWidth();

    boolean isRotated();

    boolean isScaled();

    void set(float f, float f2, float f3, float f4);

    void setTextureHeight(float f);

    void setTexturePosition(float f, float f2);

    void setTextureSize(float f, float f2);

    void setTextureWidth(float f);

    void setTextureX(float f);

    void setTextureY(float f);
}
