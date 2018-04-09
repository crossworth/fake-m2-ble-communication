package org.andengine.util.animationpack;

import java.util.HashMap;

public class AnimationPackTiledTextureRegionLibrary {
    private final HashMap<String, AnimationPackTiledTextureRegion> mAnimationPackTiledTextureRegionMapping = new HashMap();

    public void put(AnimationPackTiledTextureRegion pAnimationPackTiledTextureRegion) {
        this.mAnimationPackTiledTextureRegionMapping.put(pAnimationPackTiledTextureRegion.getAnimationName(), pAnimationPackTiledTextureRegion);
    }

    public AnimationPackTiledTextureRegion get(String pAnimationName) {
        return (AnimationPackTiledTextureRegion) this.mAnimationPackTiledTextureRegionMapping.get(pAnimationName);
    }
}
