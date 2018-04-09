package org.andengine.util.animationpack;

import org.andengine.entity.sprite.AnimationData;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class AnimationPackTiledTextureRegion extends TiledTextureRegion {
    private final AnimationData mAnimationData;
    private final String mAnimationName;

    public AnimationPackTiledTextureRegion(String pAnimationName, long[] pFrameDurations, int pLoopCount, ITexture pTexture, ITextureRegion... pTextureRegions) {
        super(pTexture, pTextureRegions);
        this.mAnimationName = pAnimationName;
        int frameCount = pFrameDurations.length;
        int[] frames = new int[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = i;
        }
        this.mAnimationData = new AnimationData(pFrameDurations, frames, pLoopCount);
    }

    public String getAnimationName() {
        return this.mAnimationName;
    }

    public AnimationData getAnimationData() {
        return this.mAnimationData;
    }
}
