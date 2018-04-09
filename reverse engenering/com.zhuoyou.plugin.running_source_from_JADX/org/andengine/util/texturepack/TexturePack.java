package org.andengine.util.texturepack;

import org.andengine.opengl.texture.ITexture;

public class TexturePack {
    private final ITexture mTexture;
    private final TexturePackTextureRegionLibrary mTexturePackTextureRegionLibrary;

    public TexturePack(ITexture pTexture, TexturePackTextureRegionLibrary pTexturePackTextureRegionLibrary) {
        this.mTexture = pTexture;
        this.mTexturePackTextureRegionLibrary = pTexturePackTextureRegionLibrary;
    }

    public ITexture getTexture() {
        return this.mTexture;
    }

    public TexturePackTextureRegionLibrary getTexturePackTextureRegionLibrary() {
        return this.mTexturePackTextureRegionLibrary;
    }

    public void loadTexture() {
        this.mTexture.load();
    }

    public void unloadTexture() {
        this.mTexture.unload();
    }
}
