package org.andengine.opengl.texture;

import java.io.IOException;
import org.andengine.opengl.util.GLState;

public interface ITexture {
    void bind(GLState gLState);

    void bind(GLState gLState, int i);

    int getHardwareTextureID();

    int getHeight();

    PixelFormat getPixelFormat();

    TextureOptions getTextureOptions();

    ITextureStateListener getTextureStateListener();

    int getWidth();

    boolean hasTextureStateListener();

    boolean isLoadedToHardware();

    boolean isUpdateOnHardwareNeeded();

    void load();

    void load(GLState gLState) throws IOException;

    void loadToHardware(GLState gLState) throws IOException;

    void reloadToHardware(GLState gLState) throws IOException;

    void setNotLoadedToHardware();

    void setTextureStateListener(ITextureStateListener iTextureStateListener);

    void setUpdateOnHardwareNeeded(boolean z);

    void unload();

    void unload(GLState gLState);

    void unloadFromHardware(GLState gLState);
}
