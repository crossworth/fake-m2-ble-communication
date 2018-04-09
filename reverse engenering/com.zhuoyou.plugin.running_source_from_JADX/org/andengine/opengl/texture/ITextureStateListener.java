package org.andengine.opengl.texture;

public interface ITextureStateListener {
    void onLoadedToHardware(ITexture iTexture);

    void onUnloadedFromHardware(ITexture iTexture);
}
