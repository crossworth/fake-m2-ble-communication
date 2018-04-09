package org.andengine.opengl.texture;

import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

public class DebugTextureStateListener<T extends ITextureAtlasSource> implements ITextureStateListener {
    public void onLoadedToHardware(ITexture pTexture) {
    }

    public void onUnloadedFromHardware(ITexture pTexture) {
    }
}
