package org.andengine.opengl.texture.region;

import org.andengine.util.adt.map.Library;

public class TextureRegionLibrary extends Library<ITextureRegion> {
    public TextureRegionLibrary(int pInitialCapacity) {
        super(pInitialCapacity);
    }

    public TextureRegion get(int pID) {
        return (TextureRegion) super.get(pID);
    }

    public TiledTextureRegion getTiled(int pID) {
        return (TiledTextureRegion) this.mItems.get(pID);
    }
}
