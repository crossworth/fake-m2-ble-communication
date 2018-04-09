package org.andengine.util.texturepack;

import java.util.HashMap;

public class TexturePackLibrary {
    private final HashMap<String, TexturePack> mTexturePackMapping = new HashMap();
    private final HashMap<String, TexturePackTextureRegion> mTexturePackTextureRegionSourceMapping = new HashMap();

    public void put(String pID, TexturePack pTexturePack) {
        this.mTexturePackMapping.put(pID, pTexturePack);
        this.mTexturePackTextureRegionSourceMapping.putAll(pTexturePack.getTexturePackTextureRegionLibrary().getSourceMapping());
    }

    public TexturePackTextureRegion getTexturePackTextureRegion(String pTexturePackTextureRegionSource) {
        return (TexturePackTextureRegion) this.mTexturePackTextureRegionSourceMapping.get(pTexturePackTextureRegionSource);
    }
}
