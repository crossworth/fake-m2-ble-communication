package org.andengine.util.animationpack;

import org.andengine.util.texturepack.TexturePackLibrary;

public class AnimationPack {
    private final AnimationPackTiledTextureRegionLibrary mAnimationPackTiledTextureRegionLibrary;
    private final TexturePackLibrary mTexturePackLibrary;

    public AnimationPack(TexturePackLibrary pTexturePackLibrary, AnimationPackTiledTextureRegionLibrary pAnimationPackTiledTextureRegionLibrary) {
        this.mTexturePackLibrary = pTexturePackLibrary;
        this.mAnimationPackTiledTextureRegionLibrary = pAnimationPackTiledTextureRegionLibrary;
    }

    public TexturePackLibrary getTexturePackLibrary() {
        return this.mTexturePackLibrary;
    }

    public AnimationPackTiledTextureRegionLibrary getAnimationPackAnimationDataLibrary() {
        return this.mAnimationPackTiledTextureRegionLibrary;
    }
}
