package org.andengine.util.texturepack;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.TextureRegion;

public class TexturePackTextureRegion extends TextureRegion {
    private final int mID;
    private final String mSource;
    private final int mSourceHeight;
    private final int mSourceWidth;
    private final int mSourceX;
    private final int mSourceY;
    private final boolean mTrimmed;

    public TexturePackTextureRegion(ITexture pTexture, int pX, int pY, int pWidth, int pHeight, int pID, String pSource, boolean pRotated, boolean pTrimmed, int pSourceX, int pSourceY, int pSourceWidth, int pSourceHeight) {
        super(pTexture, (float) pX, (float) pY, (float) pWidth, (float) pHeight, pRotated);
        this.mID = pID;
        this.mSource = pSource;
        this.mTrimmed = pTrimmed;
        this.mSourceX = pSourceX;
        this.mSourceY = pSourceY;
        this.mSourceWidth = pSourceWidth;
        this.mSourceHeight = pSourceHeight;
    }

    public int getID() {
        return this.mID;
    }

    public String getSource() {
        return this.mSource;
    }

    public boolean isTrimmed() {
        return this.mTrimmed;
    }

    public int getSourceX() {
        return this.mSourceX;
    }

    public int getSourceY() {
        return this.mSourceY;
    }

    public int getSourceWidth() {
        return this.mSourceWidth;
    }

    public int getSourceHeight() {
        return this.mSourceHeight;
    }
}
