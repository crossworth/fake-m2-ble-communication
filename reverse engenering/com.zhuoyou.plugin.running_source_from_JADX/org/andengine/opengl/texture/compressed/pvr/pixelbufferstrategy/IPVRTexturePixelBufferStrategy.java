package org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.compressed.pvr.PVRTexture;

public interface IPVRTexturePixelBufferStrategy {

    public interface IPVRTexturePixelBufferStrategyBufferManager {
        ByteBuffer getPixelBuffer(int i, int i2) throws IOException;
    }

    void loadPVRTextureData(IPVRTexturePixelBufferStrategyBufferManager iPVRTexturePixelBufferStrategyBufferManager, int i, int i2, int i3, PixelFormat pixelFormat, int i4, int i5, int i6) throws IOException;

    IPVRTexturePixelBufferStrategyBufferManager newPVRTexturePixelBufferStrategyManager(PVRTexture pVRTexture) throws IOException;
}
