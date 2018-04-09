package org.andengine.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;

public abstract class BaseTextureRegion implements ITextureRegion {
    protected final ITexture mTexture;

    public abstract ITextureRegion deepCopy();

    public BaseTextureRegion(ITexture pTexture) {
        this.mTexture = pTexture;
    }

    public ITexture getTexture() {
        return this.mTexture;
    }
}
