package org.andengine.opengl.texture.atlas.source;

public interface ITextureAtlasSource {
    ITextureAtlasSource deepCopy();

    int getTextureHeight();

    int getTextureWidth();

    int getTextureX();

    int getTextureY();

    void setTextureHeight(int i);

    void setTextureWidth(int i);

    void setTextureX(int i);

    void setTextureY(int i);
}
